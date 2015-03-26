package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.util.ArrayList;
import java.util.List;

import Helpers.SessionManager;

/**
 * Created by kristian on 15-3-25.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private SessionManager sm;
    private ImageButton facebook, manual;
//    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    private UiLifecycleHelper uiHelper;
    private static final String TAG = "MainFragment";
    private RelativeLayout master;
    private static final int REQUEST_CODE = 1;

//    LoginFragment(Activity context) {
//        mContext = context;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        sm = new SessionManager(getActivity());

        facebook = (ImageButton) view.findViewById(R.id.facebook);
        manual = (ImageButton) view.findViewById(R.id.email);
        master = (RelativeLayout) view.findViewById(R.id.master);
        facebook.setOnClickListener(this);
        manual.setOnClickListener(this);
        master.setOnClickListener(this);


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (session.isOpened()) {

            Log.d(TAG, "Logged in...");
        } else if (state.isClosed()) {
//            sm.setFacebookLogin(false);
            Log.d(TAG, "Logged out...");
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public void onResume() {
        Session session = Session.getActiveSession();

        if (session != null &&
                (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
        getFragmentManager().popBackStack();
        Log.d("FACEBOOK", resultCode + "");
        uiHelper.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.facebook:

                List<String> permissions = new ArrayList<String>();
                permissions.add("email");
                permissions.add("public_profile");

                openActiveSession(getActivity(), true, new Session.StatusCallback() {
                    @Override
                    public void call(Session session, SessionState state, Exception exception) {
                        if (session.isOpened()) {
                            //make request to the /me API
                            Log.e("sessionopened", "true");
                            Request.newMeRequest(session, new Request.GraphUserCallback() {
                                @Override
                                public void onCompleted(GraphUser user, Response response) {
                                    if (user != null) {
                                        sm.setRemember(true);
                                        sm.setFacebookLogin(true);
                                        sm.setFacebookUserId(user.getId());
                                        sm.setUserNames(user.getName());
                                        sm.setEmail(user.getProperty("email").toString());
                                        getActivity().getFragmentManager().popBackStack();
                                    }
                                }
                            }).executeAsync();
                        }
                    }
                }, permissions);

//                Session session = Session.getActiveSession();
//                if (session != null) {
//                    if (!session.isOpened() && !session.isClosed()) {
//                        session.openForRead(new Session.OpenRequest(mContext).setPermissions(Arrays.asList("public_profile", "email")).setCallback(statusCallback));
//                    } else {
//                        Session.openActiveSession(mContext, true, statusCallback);
//                    }
//                }
                break;
            case R.id.email:
                Intent intent = new Intent(getActivity(), ManualLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.master:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private static Session openActiveSession(Activity activity, boolean allowLoginUI, Session.StatusCallback callback, List<String> permissions) {
        Session.OpenRequest openRequest = new Session.OpenRequest(activity).setPermissions(permissions).setCallback(callback);
        Session session = new Session.Builder(activity).build();
        if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) || allowLoginUI) {
            Session.setActiveSession(session);
            session.openForRead(openRequest);
            return session;
        }
        return null;
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            Log.d("USER", session + "");
            if (session.isOpened()) {
                getFragmentManager().popBackStack();
            }
        }
    }
}
