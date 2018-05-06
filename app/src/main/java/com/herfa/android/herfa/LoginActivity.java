package com.herfa.android.herfa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login);

        firebaseAuth = FirebaseAuth.getInstance();

        final EditText email = (EditText) findViewById(R.id.editTextEmail);
        final EditText pwd = (EditText) findViewById(R.id.editTextPassword);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        Button btnNewUser = (Button) findViewById(R.id.buttonNewUser);
        Button btnForgotPwd = (Button) findViewById(R.id.buttonForgotPassword);


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

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                        pwd.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd = new ProgressDialog(LoginActivity.this);
                            pd.setMessage(getString(R.string.logging_in));
                            pd.show();


                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);

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

            }
        });

    }


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






}
