package com.uukeshov.findu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String LOG_TAG = "myinstaLog";
    private AdView mAdView;
    RestClient rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**/
        Calendar cal = Calendar.getInstance();

        Intent intent = new Intent(this, MyService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // schedule for every 30 minutes

        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1800000, pintent);
        /**/

        mAdView = (AdView) findViewById(R.id.ad_view);
        Log.d(LOG_TAG, "Start advertisment");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("820DF03930E923D579F0E0C77AD7C340")
                .build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutCreator_btn:
                Intent it = new Intent(this, AboutCreatorActivity.class);
                startActivity(it);
                break;
            case R.id.faq_btn:
                Intent faq = new Intent(this, FAQActivity.class);
                startActivity(faq);
                break;
            case R.id.map_btn:
                Intent map = new Intent(this, ShowCurrentLocationActivity.class);
                startActivity(map);
                break;
            case R.id.sendinfo_btn:
                Log.d(LOG_TAG, "1");
                rc = new RestClient();
                rc.StartReq();
                break;
        }
    }
}