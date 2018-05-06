package com.herfa.android.herfa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        final ImageView carpentry = (ImageView)findViewById(R.id.image_carpentry);
        ImageView blacksmith = (ImageView)findViewById(R.id.image_blacksmithing);
        ImageView sewing = (ImageView)findViewById(R.id.image_sewing);
        ImageView pottery = (ImageView)findViewById(R.id.image_pottery);
        ImageView dagger = (ImageView)findViewById(R.id.image_dagger);
        ImageView halwa = (ImageView)findViewById(R.id.image_halwa);

        carpentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, LevelsScreen.class);
                startActivity(i);
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //Add locale for language change
            Locale current = getResources().getConfiguration().locale;

            if(current.getLanguage().equalsIgnoreCase(Constants.AR)){
                changeLangLocale(Constants.EN);
            }
            else{
                changeLangLocale(Constants.AR);
            }
            Intent i = getIntent();
            finish();
            startActivity(i);
        }

        if(id == R.id.action_about_us){
            Toast.makeText(HomeActivity.this, R.string.about_us, Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.action_contact_us){
            Toast.makeText(HomeActivity.this, R.string.contact_us, Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.action_sign_out){
            final String[] items = {String.valueOf(R.string.signout),String.valueOf(R.string.cancel)};

            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Sign out from Herfa app?");
            builder.setPositiveButton(R.string.sign_out, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    firebaseAuth.signOut();
                    finish();
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }

        return super.onOptionsItemSelected(item);
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
