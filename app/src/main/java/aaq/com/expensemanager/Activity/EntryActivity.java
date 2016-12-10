package aaq.com.expensemanager.Activity;

import aaq.com.expensemanager.Helper.DataHelper;
import aaq.com.expensemanager.Pojo.Entity;
import aaq.com.expensemanager.R;
import android.content.Intent;
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

public class EntryActivity extends AppCompatActivity {
    Button add_btn;
    EditText amount_et;
    Spinner catag_spin;
    Intent i;
    private String samount;
    private String scatag;
    private String stitle;
    private String stype;
    EditText title_et;
    Spinner type_spin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        init();
        this.add_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EntryActivity.this.validate()) {
                    EntryActivity.this.stitle = EntryActivity.this.title_et.getText().toString();
                    EntryActivity.this.samount = EntryActivity.this.amount_et.getText().toString();
                    EntryActivity.this.scatag = EntryActivity.this.catag_spin.getSelectedItem().toString();
                    EntryActivity.this.stype = EntryActivity.this.type_spin.getSelectedItem().toString();
                    Entity ob = new Entity();
                    ob.setTitle(EntryActivity.this.stitle);
                    ob.setAmount(EntryActivity.this.samount);
                    ob.setType(EntryActivity.this.stype);
                    ob.setCategory(EntryActivity.this.scatag);
                    if (DataHelper.getInstance().insert(ob) > 0) {
                        Toast.makeText(EntryActivity.this.getApplicationContext(), "Data Saved!", 1).show();
                        EntryActivity.this.i = new Intent(EntryActivity.this, MainActivity.class);
                        EntryActivity.this.startActivity(EntryActivity.this.i);
                        return;
                    }
                    Toast.makeText(EntryActivity.this.getApplicationContext(), "Something went wrong!", 1).show();
                }
            }
        });
    }

    void init() {
        this.title_et = (EditText) findViewById(R.id.title_et);
        this.amount_et = (EditText) findViewById(R.id.amount_et);
        this.type_spin = (Spinner) findViewById(R.id.type_spinner);
        this.catag_spin = (Spinner) findViewById(R.id.catag_spinner);
        this.add_btn = (Button) findViewById(R.id.add_btn);
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

    public void onBackPressed() {
        super.onBackPressed();
        this.i = new Intent(this, MainActivity.class);
        startActivity(this.i);
        finish();
    }
}
