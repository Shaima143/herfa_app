package com.herfa.android.herfa;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private CircleImageView profileImage;
    private ImageView cameraIcon;
    private final int IMAGE_CAPTURE_REQUEST_CODE=1;
    private final int SELECT_FILE =2;
    private Bitmap bmp, profile_photo;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            View view;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(ProfileScreen.this, LevelsScreen.class);
                    startActivity(i);
                    break;

                case R.id.navigation_market:
                    break;

                case R.id.navigation_achievements:
                    break;

                case R.id.navigation_profile:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        setTitle(R.string.profile);

        firebaseAuth = FirebaseAuth.getInstance();

        profileImage = (CircleImageView)findViewById(R.id.profile_image);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        bottomNavigationView.setOnNavigationItemReselectedListener
//                ((BottomNavigationView.OnNavigationItemReselectedListener) mOnNavigationItemSelectedListener);
//        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.action_sign_out){
            final String[] items = {String.valueOf(R.string.signout),String.valueOf(R.string.cancel)};

            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileScreen.this);
            builder.setTitle(R.string.sign_out_from_herfa);
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

}
