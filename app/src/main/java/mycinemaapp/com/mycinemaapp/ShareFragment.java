package mycinemaapp.com.mycinemaapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by kristian on 15-3-20.
 */
public class ShareFragment extends Fragment implements View.OnClickListener {

    private ImageButton facebook, twitter, email, sms;
    private RelativeLayout container;
    private UiLifecycleHelper uiHelper;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_layout, container, false);
        facebook = (ImageButton) view.findViewById(R.id.facebook);
        twitter = (ImageButton) view.findViewById(R.id.twitter);
        email = (ImageButton) view.findViewById(R.id.email);
        sms = (ImageButton) view.findViewById(R.id.sms);
        sms.setOnClickListener(this);
        facebook.setOnClickListener(this);
        email.setOnClickListener(this);
        twitter.setOnClickListener(this);
        container = (RelativeLayout) view.findViewById(R.id.container);
        container.setOnClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        uiHelper = new UiLifecycleHelper(getActivity(), null);
        uiHelper.onCreate(savedInstanceState);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.container:
                getFragmentManager().popBackStack();
                break;
            case R.id.sms:
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body", "your desired message");
                startActivity(smsIntent);
                break;
            case R.id.email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
            case R.id.facebook:
                new AsyncTask().execute("");

                break;
            case R.id.twitter:

                String tweetUrl =
                        String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                                urlEncode("Tweet text"), urlEncode("https://www.google.fi/"));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));


//                Intent tweetIntent = new Intent(Intent.ACTION_SEND);
//                tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
//                tweetIntent.setType("text/plain");

                if (havePackage("com.twitter")) {
                    intent.setPackage("com.twitter.android");
                    startActivity(intent);
                } else {
                    Intent i = new Intent();
                    i.putExtra(Intent.EXTRA_TEXT, "blabla");
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://twitter.com/intent/tweet?text=message&via=profileName"));
                    startActivity(i);
                    Toast.makeText(getActivity(), "Twitter app isn't found", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d("urlEncode", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

    private Intent makeIntentToFacebook(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String urlToShare = "http://facebook.com";

        if (havePackage("com.facebook")) {
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
                    .setLink(message)
                    .build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(intent);
        }

        return intent;
    }

    private boolean havePackage(String namePackage) {
        boolean facebookAppFound = false;
//        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ApplicationInfo> pkgAppsList = getActivity().getPackageManager().getInstalledApplications(0);

        for (ApplicationInfo info : pkgAppsList) {
            if (info.packageName.toLowerCase().contains(namePackage)) {
//                mainIntent.setPackage(info.packageName);
                facebookAppFound = true;
                break;
            }
        }

        return facebookAppFound;
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
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

    class AsyncTask extends android.os.AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            makeIntentToFacebook("http://facebook.com");
            return "Execute";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
        }
    }
}
