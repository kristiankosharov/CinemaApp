package mycinemaapp.com.mycinemaapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import database.User;
import database.UsersDataSource;
import helpers.RoundedImageView;
import helpers.SessionManager;

/**
 * Created by kristian on 15-2-27.
 */
public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private TextView profileName, profileEmail;
    private SessionManager sm;
    private RoundedImageView roundedImageView = new RoundedImageView();
    private UsersDataSource datasource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        sm = new SessionManager(getActivity());
        datasource = new UsersDataSource(getActivity());
        datasource.open();

        List<User> values = datasource.getAllComments();

        profileImage = (ImageView) v.findViewById(R.id.profile_image);
        profileName = (TextView) v.findViewById(R.id.user_name);
        profileEmail = (TextView) v.findViewById(R.id.user_email);

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getUserEmail().equals(sm.getEmail())) {
                if (values.get(i).getImagePath() != null && !values.get(i).getImagePath().equals("")) {
                    loadAvatar(values.get(i).getImagePath());
                } else {
                    profileImage.setImageResource(R.drawable.add_picture);
                }
                profileName.setText(values.get(i).getUserName());
                profileEmail.setText(values.get(i).getUserEmail());
            }
        }
//        if (sm.getFacebookLogin()) {
//            loadAvatar("https://graph.facebook.com/" + sm.getFacebookUserId() + "/picture?type=large");
//        }

        return v;
    }

    private void loadAvatar(String path) {

        profileImage.setImageBitmap(null);
        profileImage.destroyDrawingCache();

        Picasso.with(getActivity())
                .load(path)
                .noPlaceholder()
                .transform(roundedImageView)
                .into(profileImage);
    }
}
