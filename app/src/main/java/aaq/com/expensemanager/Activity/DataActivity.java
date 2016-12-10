package aaq.com.expensemanager.Activity;

import aaq.com.expensemanager.Helper.DataHelper;
import aaq.com.expensemanager.Manager.DataManager;
import aaq.com.expensemanager.R;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.roger.match.library.BuildConfig;

public class DataActivity extends AppCompatActivity {
    TextView amount_tv;
    TextView catag_tv;
    private Button edit_btn;
    long i;
    Intent intent;
    TextView title_tv;
    TextView type_tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        init();
        this.i = getIntent().getLongExtra("key", 0);
        Cursor cursor = DataHelper.getInstance().getUserDetailByID(this.i);
        Log.e("id", this.i + "     Data");
        Log.e("id", cursor + "     Datacursor");
        if (cursor != null) {
            this.title_tv.setText(BuildConfig.FLAVOR + cursor.getString(cursor.getColumnIndex(DataManager.KEY_TITLE)));
            this.amount_tv.append(BuildConfig.FLAVOR + cursor.getString(cursor.getColumnIndex(DataManager.KEY_AMOUNT)));
            this.type_tv.append(BuildConfig.FLAVOR + cursor.getString(cursor.getColumnIndex(DataManager.KEY_TYPE)));
            this.catag_tv.append(BuildConfig.FLAVOR + cursor.getString(cursor.getColumnIndex(DataManager.KEY_CATEGORY)));
        }
        this.edit_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DataActivity.this.intent = new Intent(DataActivity.this, Edit_Activity.class);
                DataActivity.this.intent.putExtra("key", DataActivity.this.i);
                DataActivity.this.startActivity(DataActivity.this.intent);
            }
        });
    }

    public void onBackPressed() {
        this.intent = new Intent(this, MainActivity.class);
        startActivity(this.intent);
        super.onBackPressed();
    }

    void init() {
        this.title_tv = (TextView) findViewById(R.id.title_tv);
        this.amount_tv = (TextView) findViewById(R.id.amount_tv);
        this.type_tv = (TextView) findViewById(R.id.type_tv);
        this.catag_tv = (TextView) findViewById(R.id.catag_tv);
        this.edit_btn = (Button) findViewById(R.id.edit_btn);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
    }
}
