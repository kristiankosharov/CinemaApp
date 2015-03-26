package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;

import Helpers.SessionManager;

/**
 * Created by kristian on 15-3-26.
 */
public class TakePictureFragment extends Fragment implements View.OnClickListener {

    private ImageButton takePicture, gallery;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private ImageView userAvatar;
    private static final int RESULT_LOAD_IMAGE = 2;
    private SessionManager sm;
    private RelativeLayout master;

    TakePictureFragment(ImageView userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_image_fragment, container, false);

        sm = new SessionManager(getActivity());
        takePicture = (ImageButton) view.findViewById(R.id.take_picture);
        gallery = (ImageButton) view.findViewById(R.id.gallery);
        master = (RelativeLayout) view.findViewById(R.id.container);
        master.setOnClickListener(this);
        takePicture.setOnClickListener(this);
        gallery.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_picture:
                takePhoto();
                break;
            case R.id.gallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), RESULT_LOAD_IMAGE);
                break;
            case R.id.container:
                getFragmentManager().popBackStack();
                break;
        }
    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        Toast.makeText(getActivity(), imageUri.toString(), Toast.LENGTH_LONG).show();
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    sm.setMyProfileAvatarCapturePath(imageUri.toString());
                    sm.setMyProfileAvatarPath(null);
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
//                    ImageView imageView = (ImageView) findViewById(R.id.user_icon);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        float density = getActivity().getResources().getDisplayMetrics().density;
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (120 * density), (int) (120 * density));
                        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        params.setMargins(0, (int) (30 * density), 0, (int) (30 * density));
                        userAvatar.setLayoutParams(params);
                        userAvatar.setImageBitmap(bitmap);
                        Log.d("Image Directory", selectedImage.toString());
                        getFragmentManager().popBackStack();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
                break;
            case RESULT_LOAD_IMAGE:

                Uri selectedImageUri = data.getData();
//                String selectedImagePath = getPath(selectedImageUri);
                float density = getActivity().getResources().getDisplayMetrics().density;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (120 * density), (int) (120 * density));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.setMargins(0, (int) (30 * density), 0, (int) (30 * density));
                userAvatar.setLayoutParams(params);
                sm.setMyProfileAvatarPath(selectedImageUri.toString());
                sm.setMyProfileAvatarCapturePath(null);
                userAvatar.setImageURI(selectedImageUri);
                getFragmentManager().popBackStack();
                break;
        }
    }
}
