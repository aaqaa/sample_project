package aaq.com.expensemanager.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceHelper {
    public static final String IS_LOGIN = "pref_is_login";
    public static final String PREF_APP_VERSION = "appVersion";
    public static final String PREF_CROUTONS_DISABLED = "pref_croutons_disabled";
    public static final String PREF_DIALOG_ID = "dialog_id";
    public static final String PREF_IMPORT_INITIALIZED = "import_initialized";
    public static final String PREF_IS_LOGINED = "is_logined";
    public static final String PREF_IS_SUBSCRIBED_ON_SERVER = "subscribed_on_server";
    public static final String PREF_JOINED_TO_ALL_DIALOGS = "joined_to_all_dialogs";
    public static final String PREF_LOGIN_TYPE = "login_type";
    public static final String PREF_MISSED_MESSAGE = "missed_message";
    public static final String PREF_PUSH_MESSAGE = "message";
    public static final String PREF_PUSH_MESSAGE_DIALOG_ID = "push_dialog_id";
    public static final String PREF_PUSH_MESSAGE_NEED_TO_OPEN_DIALOG = "push_need_to_open_dialog";
    public static final String PREF_PUSH_MESSAGE_USER_ID = "push_user_id";
    public static final String PREF_PUSH_NOTIFICATIONS = "push_notifications";
    public static final String PREF_PUSH_NOTIFICATIONS_ON_LOGOUT = "push_notifications_on_logout";
    public static final String PREF_RECEIVE_PUSH = "receive_push";
    public static final String PREF_REG_ID = "registration_id";
    public static final String PREF_REG_USER_ID = "registered_push_user";
    public static final String PREF_REMEMBER_ME = "remember_me";
    public static final String PREF_SESSION_TOKEN = "session_token";
    public static final String PREF_SIGN_UP_INITIALIZED = "sign_up_initialized";
    public static final String PREF_STATUS = "status";
    public static final String PREF_USER_AGREEMENT = "user_agreement";
    public static final String PREF_USER_EMAIL = "email";
    public static final String PREF_USER_FULL_NAME = "full_name";
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    public static final String PREF_USER_PASSWORD = "password";
    private static PreferenceHelper instance;
    private final Editor editor = this.sharedPreferences.edit();
    private final SharedPreferences sharedPreferences;

    public static PreferenceHelper getPreferenceHelper() {
        return instance;
    }

    public PreferenceHelper(Context context) {
        instance = this;
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
    }

    public void delete(String key) {
        if (this.sharedPreferences.contains(key)) {
            this.editor.remove(key).commit();
        }
    }

    public void savePref(String key, Object value) {
        delete(key);
        if (value instanceof Boolean) {
            this.editor.putBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Integer) {
            this.editor.putInt(key, ((Integer) value).intValue());
        } else if (value instanceof Float) {
            this.editor.putFloat(key, ((Float) value).floatValue());
        } else if (value instanceof Long) {
            this.editor.putLong(key, ((Long) value).longValue());
        } else if (value instanceof String) {
            this.editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            this.editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }
        this.editor.commit();
    }

    public <T> T getPref(String key) {
        return this.sharedPreferences.getAll().get(key);
    }

    public <T> T getPref(String key, T defValue) {
        T returnValue = this.sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }

    public boolean isPrefExists(String key) {
        return this.sharedPreferences.contains(key);
    }
}
