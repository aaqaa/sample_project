package aaq.com.expensemanager.Helper;

import aaq.com.expensemanager.Manager.DataManager;
import aaq.com.expensemanager.Pojo.Entity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

public class DataHelper {
    private static DataHelper instance;
    Context cx;
    private SQLiteDatabase db;
    private DataManager dm;
    Entity entity;
    private ArrayList<Entity> resultList;

    public DataHelper(Context cx) {
        instance = this;
        this.cx = cx;
        this.dm = new DataManager(cx, DataManager.DATABASE_NAME, null, 1);
    }

    public static DataHelper getInstance() {
        return instance;
    }

    public void open() {
        this.db = this.dm.getWritableDatabase();
    }

    public void close() {
        this.db.close();
    }

    public void read() {
        this.db = this.dm.getReadableDatabase();
    }

    public void delete() {
        open();
        this.db.delete(DataManager.TABLE_USERTABLE, null, null);
        close();
    }

    public int getLastNo() {
        int i = 0;
        read();
        Cursor cur = this.db.rawQuery("select * from user_data order by _id desc LIMIT 1", null);
        if (cur.moveToFirst()) {
            i = cur.getInt(0);
        }
        close();
        return i;
    }

    public int getNoOfRecord() {
        int i = 0;
        read();
        Cursor cur = this.db.rawQuery("select count(*) from user_data ", null);
        if (cur.moveToFirst()) {
            i = cur.getInt(0);
        }
        close();
        return i;
    }

    public ArrayList<Entity> getResults() {
        open();
        this.resultList = new ArrayList();
        Cursor c = getAlldata();
        while (c.moveToNext()) {
            try {
                this.entity = new Entity();
                this.entity.setTitle(c.getString(c.getColumnIndex(DataManager.KEY_TITLE)));
                this.entity.setCategory(c.getString(c.getColumnIndex(DataManager.KEY_CATEGORY)));
                this.entity.setType(c.getString(c.getColumnIndex(DataManager.KEY_TYPE)));
                this.entity.setAmount(c.getString(c.getColumnIndex(DataManager.KEY_AMOUNT)));
                this.resultList.add(this.entity);
            } catch (Exception e) {
                Log.e("Data", "Error " + e.toString());
            }
        }
        c.close();
        close();
        return this.resultList;
    }

    public Cursor getUserDetailByID(long id) {
        read();
        Cursor cur = this.db.rawQuery("select * from user_data where _id='" + id + "'", null);
        if (cur.moveToFirst()) {
            return cur;
        }
        close();
        return null;
    }

    public int updateUserDetail(Entity en, Long id) {
        open();
        try {
            ContentValues values = new ContentValues();
            values.put(DataManager.KEY_TITLE, en.getTitle());
            values.put(DataManager.KEY_AMOUNT, en.getAmount());
            values.put(DataManager.KEY_CATEGORY, en.getCategory());
            values.put(DataManager.KEY_TYPE, en.getType());
            return this.db.update(DataManager.TABLE_USERTABLE, values, "_id=" + id, null);
        } catch (Exception e) {
            Log.e("data", e.getMessage());
            close();
            return 0;
        }
    }

    public int DeleteUserDetailbyTitle(String title) {
        int i = 0;
        open();
        try {
            i = this.db.delete(DataManager.TABLE_USERTABLE, "title = ?", new String[]{title});
        } catch (Exception e) {
            Log.e("data", e.getMessage());
            close();
        }
        return i;
    }

    public int DeleteUserDetailbyID(int id) {
        open();
        String sid = Integer.toString(id + 1);
        try {
            if (this.db.delete(DataManager.TABLE_USERTABLE, "_id = ?", new String[]{sid}) > 0) {
                Log.e("data", "removed " + ((Entity) this.resultList.remove(id)).getTitle());
                return 1;
            }
        } catch (Exception e) {
            Log.e("data", e.getMessage());
        }
        close();
        return 0;
    }

    public long insert(Entity s) {
        open();
        ContentValues values = new ContentValues();
        values.put(DataManager.KEY_TITLE, s.getTitle());
        values.put(DataManager.KEY_AMOUNT, s.getAmount());
        values.put(DataManager.KEY_TYPE, s.getType());
        values.put(DataManager.KEY_CATEGORY, s.getCategory());
        try {
            return this.db.insert(DataManager.TABLE_USERTABLE, null, values);
        } catch (Exception e) {
            Log.e("data", e.getMessage());
            close();
            return 0;
        }
    }

    public long insertthroughlist(ArrayList s) {
        open();
        long i = 0;
        int j = 0;
        while (j < s.size()) {
            try {
                Entity e = (Entity) s.get(j);
                ContentValues values = new ContentValues();
                values.put(DataManager.KEY_TITLE, e.getTitle());
                values.put(DataManager.KEY_AMOUNT, e.getAmount());
                values.put(DataManager.KEY_TYPE, e.getType());
                values.put(DataManager.KEY_CATEGORY, e.getCategory());
                i = this.db.insert(DataManager.TABLE_USERTABLE, null, values);
                j++;
            } catch (Exception ee) {
                Log.e("data", ee.getMessage());
                close();
                return 0;
            }
        }
        return i;
    }

    public Cursor getAlldata() {
        read();
        return this.db.rawQuery("select * from user_data", null);
    }

    public Cursor gettypedata(String type) {
        read();
        Cursor cur = this.db.rawQuery("select * from user_data where type='" + type + "'", null);
        return cur != null ? cur : null;
    }
}
