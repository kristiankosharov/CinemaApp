package Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String GLOBAL_SETTINGS = "global";
    private static final String USER_NAMES = "user.names";
    private static final String USER_EMAIL = "user.email";
    private static final String USER_PASSWORD = "user.password";
    private static final String USER_MANUAL_LOGIN = "user.manual.login";
    private static final String USER_REMEMBER_ME = "user.remember.me";
    private static final String USER_FACEBOOK_LOGIN = "user.facebook.login";
    private static final String MYPROFILE_AVATAR_PATH_GALLERY = "user.myprofile.avatar.path";
    private static final String MYPROFILE_AVATAR_CAPTURE_PATH = "user.myprofile.capture.path";
    private static final String FACEBOOK_USER_ID = "facebook.user.id";

    private SharedPreferences sp;

    public SessionManager(Context context) {
        sp = context
                .getSharedPreferences(GLOBAL_SETTINGS, Context.MODE_PRIVATE);
    }

    /* GET SET First Name*/
    public String getUserNames() {
        return sp.getString(USER_NAMES, null);
    }

    public void setUserNames(String names) {
        sp.edit().putString(USER_NAMES, names).commit();
    }

    /* GET SET Email*/
    public String getEmail() {
        return sp.getString(USER_EMAIL, null);
    }

    public void setEmail(String email) {
        sp.edit().putString(USER_EMAIL, email).commit();
    }

    /* GET SET Password*/
    public String getUserPassword() {
        return sp.getString(USER_PASSWORD, null);
    }

    public void setUserPassword(String pass) {
        sp.edit().putString(USER_PASSWORD, pass).commit();
    }


    /* GET SET Manual Login*/
    public boolean getManualLogin() {
        return sp.getBoolean(USER_MANUAL_LOGIN, false);
    }

    public void setManualLogin(boolean manualLogin) {
        sp.edit().putBoolean(USER_MANUAL_LOGIN, manualLogin).commit();
    }

    /* GET SET RememberMe*/
    public boolean getRemember() {
        return sp.getBoolean(USER_REMEMBER_ME, false);
    }

    public void setRemember(boolean rememberMe) {
        sp.edit().putBoolean(USER_REMEMBER_ME, rememberMe).commit();
    }

    /* GET SET Facebook Login*/
    public boolean getFacebookLogin() {
        return sp.getBoolean(USER_MANUAL_LOGIN, false);
    }

    public void setFacebookLogin(boolean manualLogin) {
        sp.edit().putBoolean(USER_MANUAL_LOGIN, manualLogin).commit();
    }

    /* GET SET MyProfile Avatar image from gallery */
    public String getMyProfileAvatarPath() {
        return sp.getString(MYPROFILE_AVATAR_PATH_GALLERY, null);
    }

    public void setMyProfileAvatarPath(String galleryPath) {
        sp.edit().putString(MYPROFILE_AVATAR_PATH_GALLERY, galleryPath).commit();
    }

    /* GET SET MyProfile Avatar campture image */
    public String getMyProfileAvatarCapturePath() {
        return sp.getString(MYPROFILE_AVATAR_CAPTURE_PATH, null);
    }

    public void setMyProfileAvatarCapturePath(String captureImage) {
        sp.edit().putString(MYPROFILE_AVATAR_CAPTURE_PATH, captureImage).commit();
    }

    /* GET SET Facebook User id */
    public String getFacebookUserId() {
        return sp.getString(FACEBOOK_USER_ID, null);
    }

    public void setFacebookUserId(String userId) {
        sp.edit().putString(FACEBOOK_USER_ID, userId).commit();
    }

    // Clear all values from SharedPreferences
    public void logOut() {
        sp.edit().clear().commit();
    }

}