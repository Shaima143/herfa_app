package com.herfa.android.herfa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class TermsAndConditions extends AppCompatActivity {

    CheckBox checkBox_terms;
    WebView webView;
    Button button_accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        checkBox_terms = (CheckBox)findViewById(R.id.CheckBoxtTerms);
        webView = (WebView)findViewById(R.id.terms);
        button_accept = (Button)findViewById(R.id.buttonAccept);

        webView.loadUrl("file:///android_asset/terms.html");

        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox_terms.isChecked()){
                    //check the preference
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_FILE_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Constants.TERMS_AND_CONDITIONS_ACCEPTED, true);
                    editor.commit();

                    Intent i = new Intent(TermsAndConditions.this, LoginActivity.class);
                    startActivity(i);
                    Toast.makeText(TermsAndConditions.this, "You have accepted the terms and conditions", Toast.LENGTH_LONG).show();
                }

                else{
                    Toast.makeText(TermsAndConditions.this, "Please accept the terms and conditions to continue using thea app", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
