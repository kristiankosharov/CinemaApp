package mycinemaapp.com.mycinemaapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import Helpers.RoundedImageView;
import Helpers.SessionManager;

/**
 * Created by kristian on 15-2-27.
 */
public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private TextView profileName, profileEmail;
    private SessionManager sm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        sm = new SessionManager(getActivity());
        profileImage = (ImageView) v.findViewById(R.id.profile_image);
        profileName = (TextView) v.findViewById(R.id.user_name);
        profileEmail = (TextView) v.findViewById(R.id.user_email);

        if (sm.getMyProfileAvatarPath() != null) {
            Toast.makeText(getActivity(), "Gallery:" + sm.getMyProfileAvatarPath(), Toast.LENGTH_LONG).show();
            loadAvatar(sm.getMyProfileAvatarPath());
        }
        if (sm.getMyProfileAvatarCapturePath() != null) {
            Toast.makeText(getActivity(), "Capture:" + sm.getMyProfileAvatarCapturePath(), Toast.LENGTH_LONG).show();
            loadAvatar(sm.getMyProfileAvatarCapturePath());
        }
        if (sm.getFacebookLogin()) {
            loadAvatar("https://graph.facebook.com/" + sm.getFacebookUserId() + "/picture?type=large");
        }

        profileName.setText(sm.getUserNames());
        profileEmail.setText(sm.getEmail());

        return v;
    }

    private void loadAvatar(String path) {

        profileImage.setImageBitmap(null);
        profileImage.destroyDrawingCache();

        Picasso.with(getActivity())
                .load(path)
                .transform(new RoundedImageView())
                .noPlaceholder()
                .into(profileImage);
    }
}
