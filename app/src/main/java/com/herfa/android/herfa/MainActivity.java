package com.herfa.android.herfa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements
        LevelsFragment.OnFragmentInteractionListener,
        CraftsFragment.OnFragmentInteractionListener,
        LevelFragment.OnFragmentInteractionListener,
        UserProfileFragment.OnFragmentInteractionListener,
        AchievementsFragment.OnFragmentInteractionListener,
        MarketFragment.OnFragmentInteractionListener,
        BottomNavigationHomeFragment.OnFragmentInteractionListener,
        BackButton.OnFragmentInteractionListener,
        ProductDetailsFragment.OnFragmentInteractionListener,
        AboutUsFragment.OnFragmentInteractionListener,
        ContactUsFragment.OnFragmentInteractionListener,
        AchievementDetailsFragment.OnFragmentInteractionListener{

    private FirebaseAuth firebaseAuth;
    Context context;
    Toolbar toolbar;
    BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(getString(R.string.app_name));


//        toolbar.setTitle(getString(R.string.app_name));
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

         fragment=new CraftsFragment();
         fragmentTransaction =getSupportFragmentManager().beginTransaction();
         fragmentTransaction.replace(R.id.main_container,fragment);
         fragmentTransaction.addToBackStack(fragmentTransaction.getClass().getSimpleName());
         fragmentTransaction.commit();

        firebaseAuth = FirebaseAuth.getInstance();
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

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

        //if(id == R.id.sign_out){
//            firebaseAuth.signOut();
//
//            Fragment fragment = new SignUpFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_container, fragment, fragment.getClass().getSimpleName()).commit();
//            finish();

        //}
        return super.onOptionsItemSelected(item);
    }



//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        //drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }


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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void backButtonLevel (View view){
        super.onBackPressed();
    }

    public void backButtonMarket (View view){
        getSupportActionBar().show();
        fragment = new CraftsFragment();
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.addToBackStack(fragmentTransaction.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void backPressedUserProfile (View view){
        getSupportActionBar().show();
        fragment = new CraftsFragment();
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.addToBackStack(fragmentTransaction.getClass().getSimpleName());
        fragmentTransaction.commit();
    }


    public void backButtonAchiev (View view){
        getSupportActionBar().show();
        fragment = new CraftsFragment();
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.addToBackStack(fragmentTransaction.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void backButtonAbout (View view){
//        getSupportActionBar().show();
//        fragment = new CraftsFragment();
//        fragmentTransaction=getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.main_container,fragment);
//        fragmentTransaction.addToBackStack(fragmentTransaction.getClass().getSimpleName());
//        fragmentTransaction.commit();
    }





    public void onBackPressed(View view) {
        //onBackPressed();
        getSupportActionBar().show();
        fragment = new CraftsFragment();
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.addToBackStack(fragmentTransaction.getClass().getSimpleName());
        fragmentTransaction.commit();

    }
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    toolbar.setTitle("Shop");
//                    fragment = new LevelsFragment();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_market:
//                    toolbar.setTitle("My Gifts");
//                    fragment = new MarketFragment();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_achievements:
//                    toolbar.setTitle("Cart");
//                    fragment=new AchievementsFragment();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_profile:
//                    toolbar.setTitle("Profile");
//                    fragment=new UserProfileFragment();
//                    loadFragment(fragment);
//                    return true;
//            }
//
//            return false;
//        }
//    };
//
//
//
//    private void loadFragment(Fragment fragment) {
//        // load fragment
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}
