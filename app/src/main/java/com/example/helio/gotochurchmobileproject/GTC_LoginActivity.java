package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GTC_LoginActivity extends AppCompatActivity {


    private View.OnClickListener onClickListenerSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentSignUp = new Intent(GTC_LoginActivity.this, GTC_SignUpActivity.class);
            startActivity(intentSignUp);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__login);

        findViewById(R.id.button_signUp).setOnClickListener(onClickListenerSignUp);
    }
}