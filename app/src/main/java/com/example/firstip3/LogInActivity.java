package com.example.firstip3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.crAcc2) TextView crAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ButterKnife.bind(this);
        crAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == crAccount){
            Intent goToMain= new Intent(LogInActivity.this, MainActivity.class);
            startActivity(goToMain);
            finish();
        }
    }
}
