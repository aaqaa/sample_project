package aaq.com.expensemanager.Manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "demo";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TYPE = "type";
    public static final String TABLE_USERTABLE = "user_data";

    public DataManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERTABLE = "create table user_data (_id INTEGER PRIMARY KEY,title text ,amount text ,type text ,category text )";
        Log.e("table", CREATE_USERTABLE);
        db.execSQL(CREATE_USERTABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int paramInt1, int paramInt2) {
        db.execSQL("DROP TABLE IF EXISTS user_data");
        onCreate(db);
    }
}
