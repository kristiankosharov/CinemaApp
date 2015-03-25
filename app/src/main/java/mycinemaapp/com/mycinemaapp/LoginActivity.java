package mycinemaapp.com.mycinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

import java.util.Arrays;

import Helpers.SessionManager;

/**
 * Created by kristian on 15-3-25.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    private SessionManager sm;
    private ImageButton facebook, manual;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    private UiLifecycleHelper uiHelper;
    private static final String TAG = "MainFragment";
    private RelativeLayout master;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

        sm = new SessionManager(this);

        facebook = (ImageButton) findViewById(R.id.facebook);
        manual = (ImageButton) findViewById(R.id.email);
        master = (RelativeLayout) findViewById(R.id.container);
        facebook.setOnClickListener(this);
        manual.setOnClickListener(this);
        master.setOnClickListener(this);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
//            sm.setFacebookLogin(true);
//            Toast.makeText(this, "zashtoo", Toast.LENGTH_LONG).show();
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
                Session session = Session.getActiveSession();
                if (session != null) {
                    if (!session.isOpened() && !session.isClosed()) {
                        session.openForRead(new Session.OpenRequest(this).setPermissions(Arrays.asList("public_profile")).setCallback(statusCallback));
                    } else {
                        Session.openActiveSession(this, true, statusCallback);
                    }
                }
                break;
            case R.id.email:
                Intent intent = new Intent(this, ManualLoginActivity.class);
                startActivity(intent);
                break;
            case R.id.container:
                onBackPressed();
                break;
        }
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            // Respond to session state changes, ex: updating the view
            Log.d("STATE", state + "");
            if (state.equals(SessionState.OPENED)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
