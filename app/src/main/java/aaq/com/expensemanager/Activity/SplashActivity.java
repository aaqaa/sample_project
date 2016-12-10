package aaq.com.expensemanager.Activity;

import aaq.com.expensemanager.Helper.DataHelper;
import aaq.com.expensemanager.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Intent i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash);
        DataHelper dataHelper = new DataHelper(this);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.i = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(SplashActivity.this.i);
                SplashActivity.this.finish();
            }
        }, 3000);
    }
}
