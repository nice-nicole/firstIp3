package com.example.firstip3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.name) EditText name;
    @BindView(R.id.email2) EditText email2;
    @BindView(R.id.pass2) EditText pass2;

    @BindView(R.id.crAcc) Button crAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        crAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == crAcc){
            Intent goToMain2= new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(goToMain2);
            finish();
        }
    }
}
