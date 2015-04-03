package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import helpers.SessionManager;

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
    private static int counter = 0;
    private String mCurrentPhotoPath;

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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, TAKE_PICTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    // Save Image To Gallery
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File f = new File(mCurrentPhotoPath);
                    Uri contentUri = Uri.fromFile(f);
                    mediaScanIntent.setData(contentUri);
                    getActivity().sendBroadcast(mediaScanIntent);

                    float density = getActivity().getResources().getDisplayMetrics().density;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (120 * density), (int) (120 * density));
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.setMargins(0, (int) (30 * density), 0, (int) (30 * density));
                    userAvatar.setLayoutParams(params);
//                    userAvatar.setImageURI(null);

                    try {
                        userAvatar.destroyDrawingCache();
                        userAvatar.setImageResource(0);
                        userAvatar.setImageBitmap(decodeUri(contentUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    }
                    sm.setMyProfileAvatarCapturePath(contentUri.toString());
                    getFragmentManager().popBackStack();
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
//                sm.setMyProfileAvatarPath(null);
                sm.setMyProfileAvatarPath(selectedImageUri.toString());
                sm.setMyProfileAvatarCapturePath(null);
                try {
                    userAvatar.destroyDrawingCache();
                    userAvatar.setImageResource(0);
                    userAvatar.setImageBitmap(decodeUri(selectedImageUri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                getFragmentManager().popBackStack();
                break;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",     /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
    }
}
