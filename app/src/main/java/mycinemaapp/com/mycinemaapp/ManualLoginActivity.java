package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import Helpers.SessionManager;

/**
 * Created by kristian on 15-3-25.
 */
public class ManualLoginActivity extends Activity implements View.OnClickListener {

    private ImageView userPicture, back;
    private EditText names, email, password;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private Button logIn;
    private TextView signIn, createAccount, terms;
    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_login_activity);
        sm = new SessionManager(this);
        userPicture = (ImageView) findViewById(R.id.user_icon);
        names = (EditText) findViewById(R.id.names);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pass);
        logIn = (Button) findViewById(R.id.log_in);
        back = (ImageView) findViewById(R.id.back);
        terms = (TextView) findViewById(R.id.terms);
        signIn = (TextView) findViewById(R.id.sign_in);
        createAccount = (TextView) findViewById(R.id.create_account);
        signIn.setOnClickListener(this);
        createAccount.setOnClickListener(this);
        terms.setOnClickListener(this);
        back.setOnClickListener(this);
        logIn.setOnClickListener(this);
        sm.setUserNames(names.getText().toString());
//        float density = getResources().getDisplayMetrics().density;
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (120 * density), (int) (120 * density));
//        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        params.addRule(RelativeLayout.ABOVE, R.id.names);
//        params.setMargins(0, 0, 0, (int) (20 * density));
//        userPicture.setLayoutParams(params);
        userPicture.setOnClickListener(this);


//        File photo = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pic.jpg");
//        Toast.makeText(this, photo.toString(), Toast.LENGTH_LONG).show();
//        if (photo != null) {
//            userPicture.setImageURI(Uri.fromFile(photo));
//        } else {
//            userPicture.setImageResource(R.drawable.add_picture);
//        }
//        Picasso.with(this)
//                .load("http://hdwallpapersmart.com/wp-content/uploads/2014/01/Funny-Nature-Hd-Wallpapers.jpg")
//                .transform(new RoundedImageView())
//                .into(userPicture);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_icon:
                hideKeyboard(this);
                TakePictureFragment takePictureFragment = new TakePictureFragment(userPicture);
                FragmentTransaction takePictureTransaction = getFragmentManager().beginTransaction();
                takePictureTransaction.addToBackStack("Take Picture Fragment");
                takePictureTransaction.add(R.id.fragment_container, takePictureFragment);
                takePictureTransaction.commit();
                break;
            case R.id.log_in:
                String namesString = names.getText().toString();
                String emailString = email.getText().toString();
                String passString = password.getText().toString();

                if (validateLoginElements(namesString, emailString, passString)) {
                    sm.setUserNames(namesString);
                    sm.setEmail(emailString);
                    sm.setUserPassword(passString);
                    sm.setRemember(true);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.sign_in:
                signIn.setTextColor(Color.WHITE);
                createAccount.setTextColor(getResources().getColor(R.color.light_gray));

                userPicture.setVisibility(View.INVISIBLE);
                names.setVisibility(View.GONE);
                terms.setText(getResources().getString(R.string.forgot_password));
                break;
            case R.id.create_account:
                createAccount.setTextColor(Color.WHITE);
                signIn.setTextColor(getResources().getColor(R.color.light_gray));

                userPicture.setVisibility(View.VISIBLE);
                names.setVisibility(View.VISIBLE);
                terms.setText(getResources().getString(R.string.terms));
                break;
            case R.id.terms:
                break;
        }

    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.user_icon);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        imageView.setImageBitmap(bitmap);
                        Log.d("Image Directory", selectedImage.toString());
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    public boolean validateLoginElements(String namesString, String emailString, String passString) {
        boolean check = true;
        // If email is empty or not matching of pattern set error on EditText
        if (emailString.isEmpty()
                || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailString)
                .matches()) {
            email.setError("Please input correct e-mail!");
            check = false;
        }
        // If password is empty or smaller than 4 symbols set error on EditText
        if (passString.isEmpty() || passString.length() < 4) {
            password.setError("Input password at least 4 symbols!");
            check = false;
        }
        // If names is empty or smaller than 2 symbols set error on EditText
        if (namesString.isEmpty() || namesString.length() < 2) {
            names.setError("Input at least 2 symbols!");
            check = false;
        }

        return check;
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
