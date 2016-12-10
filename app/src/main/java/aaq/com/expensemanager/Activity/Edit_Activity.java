package aaq.com.expensemanager.Activity;

import aaq.com.expensemanager.Helper.DataHelper;
import aaq.com.expensemanager.Manager.DataManager;
import aaq.com.expensemanager.Pojo.Entity;
import aaq.com.expensemanager.R;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

public class Edit_Activity extends AppCompatActivity {
    EditText amount_et;
    String[] catag;
    Spinner catag_spin;
    Entity e;
    long i = 0;
    Intent intent;
    String samount;
    Button save_btn;
    String scatag;
    String stitle;
    String stype;
    EditText title_et;
    String[] type;
    Spinner type_spin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        this.i = getIntent().getLongExtra("key", 0);
        Cursor cursor = DataHelper.getInstance().getUserDetailByID(this.i);
        if (cursor.moveToFirst()) {
            this.title_et.setText(cursor.getString(cursor.getColumnIndex(DataManager.KEY_TITLE)));
            this.amount_et.setText(cursor.getString(cursor.getColumnIndex(DataManager.KEY_AMOUNT)));
            this.type_spin.setSelection(spinmatch(this.type, cursor.getString(cursor.getColumnIndex(DataManager.KEY_TYPE))) - 1);
            this.catag_spin.setSelection(spinmatch(this.catag, cursor.getString(cursor.getColumnIndex(DataManager.KEY_CATEGORY))) - 1);
        }
        this.save_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (Edit_Activity.this.validate()) {
                    Edit_Activity.this.samount = Edit_Activity.this.amount_et.getText().toString();
                    Edit_Activity.this.stitle = Edit_Activity.this.title_et.getText().toString();
                    Edit_Activity.this.stype = Edit_Activity.this.type_spin.getSelectedItem().toString();
                    Edit_Activity.this.scatag = Edit_Activity.this.catag_spin.getSelectedItem().toString();
                    Edit_Activity.this.e.setTitle(Edit_Activity.this.stitle);
                    Edit_Activity.this.e.setAmount(Edit_Activity.this.samount);
                    Edit_Activity.this.e.setCategory(Edit_Activity.this.scatag);
                    Edit_Activity.this.e.setType(Edit_Activity.this.stype);
                    if (DataHelper.getInstance().updateUserDetail(Edit_Activity.this.e, Long.valueOf(Edit_Activity.this.i)) > 0) {
                        Toast.makeText(Edit_Activity.this.getApplicationContext(), "successfully edited!", 1).show();
                        Edit_Activity.this.intent = new Intent(Edit_Activity.this, MainActivity.class);
                        Edit_Activity.this.startActivity(Edit_Activity.this.intent);
                        return;
                    }
                    Toast.makeText(Edit_Activity.this.getApplicationContext(), "edit failed!", 1).show();
                }
            }
        });
    }

    int spinmatch(String[] arr, String match) {
        int i = 0;
        for (String s : arr) {
            i++;
            if (s.equals(match)) {
                return i;
            }
        }
        return 0;
    }

    void init() {
        this.title_et = (EditText) findViewById(R.id.title_et);
        this.amount_et = (EditText) findViewById(R.id.amount_et);
        this.type_spin = (Spinner) findViewById(R.id.type_spinner);
        this.catag_spin = (Spinner) findViewById(R.id.catag_spinner);
        this.save_btn = (Button) findViewById(R.id.save_btn);
        this.catag = getResources().getStringArray(R.array.category);
        this.type = getResources().getStringArray(R.array.type);
        this.e = new Entity();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
    }

    boolean validate() {
        if (this.title_et.length() <= 0) {
            this.title_et.setError("Please fill");
            this.title_et.requestFocus();
            return false;
        } else if (this.amount_et.length() > 0) {
            return true;
        } else {
            this.amount_et.setError("Please fill");
            this.amount_et.requestFocus();
            return false;
        }
    }
}
