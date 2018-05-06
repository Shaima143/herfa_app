package com.herfa.android.herfa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        if((getSharedPreferences(Constants.LANG_FILE, MODE_PRIVATE).getString(Constants.LANG, null) !=null
                && getSharedPreferences(Constants.LANG_FILE, MODE_PRIVATE).
                getString(Constants.LANG,null).equalsIgnoreCase(Constants.AR))){
            changeLangLocale(Constants.AR);
        }
        else{
            changeLangLocale(Constants.EN);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_FILE_NAME, MODE_PRIVATE);
                boolean b = sharedPreferences.getBoolean(Constants.TERMS_AND_CONDITIONS_ACCEPTED, false); //default value is false if key is false

                if(b){ //if b is true -- second time (if user accepted terms and conditions
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();



                }
                else{ //if b is false -- first time (if user did not accept terms and conditions
                    Intent intent = new Intent(SplashScreen.this, TermsAndConditions.class);
                    startActivity(intent);
                    finish();
                }
            }
        },4000);
    }


    public void changeLangLocale(String en) {
        Locale myLocale = new Locale(en);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LANG_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.LANG,en);
        editor.commit();
    }

}
