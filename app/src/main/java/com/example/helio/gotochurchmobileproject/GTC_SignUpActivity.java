package com.example.helio.gotochurchmobileproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GTC_SignUpActivity extends AppCompatActivity {

    private static final int NEW_ADDRESS_CODE = 1;

    private View.OnClickListener buttonAddressOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GTC_SignUpActivity.this, GTC_NewAddressActivity.class);
            startActivityForResult(intent, NEW_ADDRESS_CODE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtc__sign_up);

        findViewById(R.id.imageButton_address).setOnClickListener(buttonAddressOnClick);
    }

}
