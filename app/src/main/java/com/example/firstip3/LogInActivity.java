package com.example.firstip3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.crAcc2) TextView crAccount;
    @BindView(R.id.loginBtn)
    Button loginBtn;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAuth authProtocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ButterKnife.bind(this);
        crAccount.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        authProtocol= FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser person = firebaseAuth.getCurrentUser();
                 if(person != null){
                     Intent goToMainAct = new Intent(LogInActivity.this, MainActivity.class);
                     goToMainAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(goToMainAct);
                     finish();
                 }
            }
        }
    }

    @Override
    public void onClick(View v){
        if(v == crAccount){
            Intent goToSignUp= new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(goToSignUp);
            finish();
        }

        if(v == loginBtn){
            Intent goToMain = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(goToMain);
            finish();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        authProtocol.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            authProtocol.removeAuthStateListener(mAuthListener);
        }
    }

}
