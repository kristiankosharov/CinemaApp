package mycinemaapp.com.mycinemaapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import Helpers.ImageCacheManager;

/**
 * Created by kristian on 15-2-27.
 */
public class ProfileFragment extends Fragment {

    private NetworkImageView profileImage;
    private TextView profileName,profileEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        profileImage = (NetworkImageView) v.findViewById(R.id.profile_image);
        profileName = (TextView) v.findViewById(R.id.user_name);
        profileEmail = (TextView) v.findViewById(R.id.user_email);

        profileImage.setImageUrl("https://pbs.twimg.com/profile_images/604644048/sign051.gif", ImageCacheManager.getInstance().getImageLoader());
        profileImage.setDefaultImageResId(R.drawable.example);

        profileName.setText("Kristian Kosharov");
        profileEmail.setText("kristiann.n@abv.bg");

        return v;
    }
}
