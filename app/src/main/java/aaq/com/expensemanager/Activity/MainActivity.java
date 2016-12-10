package aaq.com.expensemanager.Activity;

import aaq.com.expensemanager.Adapter.CustomAdapter;
import aaq.com.expensemanager.Helper.DataHelper;
import aaq.com.expensemanager.Manager.DataManager;
import aaq.com.expensemanager.Pojo.Entity;
import aaq.com.expensemanager.R;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CustomAdapter adapter;
    Context context;
    Cursor cursor;
    ArrayList<String> datalist;
    boolean doubleBackToExitPressedOnce = false;
    ArrayList<Entity> entity_list;
    ListView entry_list;
    TextView exp_tv;
    Double expense_value;
    private Intent i;
    TextView income_tv;
    Double income_value;
    TextView sav_tv;
    Double saving_value = Double.valueOf(0.0d);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        this.cursor = DataHelper.getInstance().gettypedata("Income");
        if (this.cursor.moveToFirst()) {
            this.income_value = Double.valueOf(this.income_value.doubleValue() + Double.parseDouble(this.cursor.getString(this.cursor.getColumnIndex(DataManager.KEY_AMOUNT))));
            while (this.cursor.moveToNext()) {
                this.income_value = Double.valueOf(this.income_value.doubleValue() + Double.parseDouble(this.cursor.getString(this.cursor.getColumnIndex(DataManager.KEY_AMOUNT))));
            }
        }
        this.cursor = DataHelper.getInstance().gettypedata("Expense");
        if (this.cursor.moveToFirst()) {
            this.expense_value = Double.valueOf(this.expense_value.doubleValue() + Double.parseDouble(this.cursor.getString(this.cursor.getColumnIndex(DataManager.KEY_AMOUNT))));
            while (this.cursor.moveToNext()) {
                this.expense_value = Double.valueOf(this.expense_value.doubleValue() + Double.parseDouble(this.cursor.getString(this.cursor.getColumnIndex(DataManager.KEY_AMOUNT))));
            }
        }
        this.saving_value = Double.valueOf(this.income_value.doubleValue() - this.expense_value.doubleValue());
        this.income_tv.append(this.income_value.toString());
        this.exp_tv.append(this.expense_value.toString());
        this.sav_tv.append(this.saving_value.toString());
        this.entity_list = DataHelper.getInstance().getResults();
        if (this.datalist.size() <= 0) {
            for (int i = 0; i < this.entity_list.size(); i++) {
                this.datalist.add(((Entity) this.entity_list.get(i)).getTitle());
            }
        }
        this.adapter = new CustomAdapter(this, this.datalist);
        this.entry_list.setAdapter(this.adapter);
        this.entry_list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.e("id", id + "     main");
                Log.e("id", position + "     position");
                MainActivity.this.i = new Intent(MainActivity.this, DataActivity.class);
                MainActivity.this.i.putExtra("key", 1 + id);
                MainActivity.this.startActivity(MainActivity.this.i);
                MainActivity.this.finish();
            }
        });
        this.entry_list.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View arg1, final int pos, long id) {
                new Builder(MainActivity.this.context).setTitle("Delete entry").setMessage("Are you sure you want to delete this entry?").setPositiveButton(17039379, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (DataHelper.getInstance().DeleteUserDetailbyID(pos) > 0) {
                            MainActivity.this.entity_list = DataHelper.getInstance().getResults();
                            MainActivity.this.adapter.remove(pos);
                            MainActivity.this.adapter.notifyDataSetChanged();
                            DataHelper.getInstance().delete();
                            DataHelper.getInstance().insertthroughlist(MainActivity.this.entity_list);
                            Toast.makeText(MainActivity.this.getApplicationContext(), "Successfully deleted!", 1).show();
                            MainActivity.this.i = new Intent(MainActivity.this, MainActivity.class);
                            MainActivity.this.startActivity(MainActivity.this.i);
                            MainActivity.this.finish();
                            return;
                        }
                        Toast.makeText(MainActivity.this.getApplicationContext(), "unable to delete!", 1).show();
                    }
                }).setNegativeButton(17039369, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(17301543).show();
                return true;
            }
        });
    }

    void init() {
        this.context = this;
        this.income_tv = (TextView) findViewById(R.id.income_tv);
        this.exp_tv = (TextView) findViewById(R.id.expense_tv);
        this.sav_tv = (TextView) findViewById(R.id.sav_tv);
        this.entry_list = (ListView) findViewById(R.id.list_view_entry);
        this.datalist = new ArrayList();
        this.expense_value = Double.valueOf(0.0d);
        this.income_value = Double.valueOf(0.0d);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plus_menu_item /*2131558514*/:
                this.i = new Intent(this, EntryActivity.class);
                startActivity(this.i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Double click to exit :)", 0).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
