package com.herfa.android.herfa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.Locale;

import jp.wasabeef.blurry.Blurry;

public class LoginActivity extends AppCompatActivity implements TextWatcher,CompoundButton.OnCheckedChangeListener {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;

    EditText email, pwd;
    Button btnLogin, btnNewUser, btnForgotPwd;
    CheckBox rememberMeCheckbox;

    BlurImageView blurImageView;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";




//    public static String  PREFS_NAME="mypre";
//    public static String PREF_EMAIL="email";
//    public static String PREF_PASSWORD="password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setTitle(getString(R.string.login));

//        Blurry.with(LoginActivity.this).radius(25)
//                .sampling(1)
//                .color(Color.argb(66, 0, 255, 255))
//                .into((R.drawable.backg));



            firebaseAuth = FirebaseAuth.getInstance();
        //String Uemail = firebaseAuth.getCurrentUser().getEmail();

//        if(Uemail!= null ){
//            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(i);
//
//            //Toast.makeText(LoginActivity.this, Uemail, Toast.LENGTH_LONG).show();
//        }

        DatabaseReference databaseReference;

        email = findViewById(R.id.editTextEmail);
        pwd = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        btnNewUser = findViewById(R.id.buttonNewUser);
        btnForgotPwd = findViewById(R.id.buttonForgotPassword);
        rememberMeCheckbox = findViewById(R.id.checkBoxRememberMe);

        blurImageView = findViewById(R.id.BlurImageView);
        blurImageView.setBlur(5);


        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rememberMeCheckbox.setChecked(true);
        else
            rememberMeCheckbox.setChecked(false);

        email.setText(sharedPreferences.getString(KEY_EMAIL,""));
        pwd.setText(sharedPreferences.getString(KEY_PASS,""));

        email.addTextChangedListener( LoginActivity.this);
        pwd.addTextChangedListener(LoginActivity.this);
        rememberMeCheckbox.setOnCheckedChangeListener(LoginActivity.this);



//        final String Useremail="myemail";
//        final String Userpassword="mypassword";




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().length() == 0) {
                    email.setError(getString(R.string.pleaseEnterEmail));
                    return;
                }
                if (pwd.getText().toString().length() == 0) {
                    pwd.setError(getString(R.string.please_enter_pwd));
                    return;
                }

//                if(rememeberMeCheckbox.isChecked()){
//                    rememberMe(email.getText().toString(),pwd.getText().toString());
//                }


                String UserEmail = email.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                        pwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd = new ProgressDialog(LoginActivity.this);
                            pd.setMessage(getString(R.string.logging_in));
                            pd.show();

                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            final FirebaseDatabase firebaseDatabase;
                            final DatabaseReference databaseReference;

                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference("UserInfo");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                        if (snap.getKey().equals(user.getUid())) {
                                            databaseReference.child(snap.getKey()).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    SignUpInfo info = dataSnapshot.getValue(SignUpInfo.class);
                                                    String uname = info.getUsername();

                                                    Toast.makeText(LoginActivity.this, getString(R.string.Welcome_back) + " " + uname, Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                            //String uname = info.getUsername();
                            //Toast.makeText(LoginActivity.this, getString(R.string.Welcome_back)+uname, Toast.LENGTH_SHORT).show();


                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            managePrefs();

                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });



        btnForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserEmail = email.getText().toString();

                if(email.getText().toString().length()==0){
                    email.setError(getString(R.string.pleaseEnterEmail));
                }


                else{
                    firebaseAuth.sendPasswordResetEmail(UserEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Check your email to reset password",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Failed to send reset password email",Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });
    }


//        btnForgotPwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String UserEmail = email.getText().toString();
//                if (email.getText().toString().length() == 0) {
//                    email.setError(getString(R.string.please_provide_email));
//                } else {
//                    firebaseAuth.sendPasswordResetEmail(UserEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                        }
//                    });
//
//                }
//            }
//        });


    @Override
    protected void onResume() {
        final EditText email = (EditText) findViewById(R.id.editTextEmail);
        final EditText pwd = (EditText) findViewById(R.id.editTextPassword);
        email.setText("");
        pwd.setText("");
        super.onResume();
        //pd.dismiss();
    }

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
            //Add locale for language change
            Locale current = getResources().getConfiguration().locale;

            if (current.getLanguage().equalsIgnoreCase(Constants.AR)) {
                changeLangLocale(Constants.EN);
            } else {
                changeLangLocale(Constants.AR);
            }
            Intent i = getIntent();
            finish();
            startActivity(i);
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
        editor.putString(Constants.LANG, en);
        editor.commit();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
       managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       managePrefs();
    }


    private void managePrefs(){
        if(rememberMeCheckbox.isChecked()){
            editor.putString(KEY_EMAIL, email.getText().toString().trim());
            editor.putString(KEY_PASS, pwd.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_EMAIL);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }


//    public void onStart(){
//        super.onStart();
//        //read username and password from SharedPreferences
//        getUser();
//    }
//

//    public void getUser(){
//        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
//        String Useremail = pref.getString(PREF_EMAIL, null);
//        String Userpassword = pref.getString(PREF_PASSWORD, null);
//
////        if (username != null || password != null) {
////            //directly show logout form
////            showLogout(username);
////        }
//    }
//
//    public void rememberMe(String email, String password){
//        //save username and password in SharedPreferences
//        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
//                .edit()
//                .putString(PREF_EMAIL,email)
//                .putString(PREF_PASSWORD,password)
//                .commit();
//    }

}
