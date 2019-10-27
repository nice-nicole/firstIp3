package com.example.firstip3.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firstip3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.crAcc2) TextView crAccount;
    @BindView(R.id.loginBtn) Button loginBtn;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.email) EditText loginEmail;



    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAuth authProtocol;

    private ProgressDialog progDialog;

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
        };

        loadingWithProgressDialog();
    }

    private void loadingWithProgressDialog(){
        progDialog = new ProgressDialog(this);
        progDialog.setTitle("wait...");
        progDialog.setMessage("Authentification avec firebase...");
        progDialog.setCancelable(false);
    }

    private void signinUsignPassword(){


        final String newEmail = loginEmail.getText().toString().trim();
        String newPassword = pass.getText().toString().trim();

        if(newPassword.equals("")){
            pass.setError("You must have a password...");
            return;

        }
        progDialog.show();
        authProtocol.signInWithEmailAndPassword(newEmail,newPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v){
        if(v == crAccount){
            Intent goToSignUp= new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(goToSignUp);
            finish();
        }

        if(v == loginBtn){
            signinUsignPassword();
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
