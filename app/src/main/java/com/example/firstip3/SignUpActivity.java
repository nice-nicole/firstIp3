package com.example.firstip3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.name) EditText name;
    @BindView(R.id.email2) EditText email2;
    @BindView(R.id.pass2) EditText pass2;
    @BindView(R.id.confPass) EditText confPass;

    @BindView(R.id.crAcc) Button crAcc;
    @BindView(R.id.log)
    TextView log;

    private FirebaseAuth authProtocol;

    public static final String TAG= SignUpActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        crAcc.setOnClickListener(this);
        log.setOnClickListener(this);

        createAuthStateListener();
    }


    private void register() {
        final String newName = name.getText().toString().trim();
        final String newEmail2 = email2.getText().toString().trim();
        final String newPass2 = pass2.getText().toString().trim();
        final String newConfPass = confPass.getText().toString().trim();

        authProtocol.createUserWithEmailAndPassword(newEmail2, newPass2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v){
        if(v == crAcc){
            Intent goToMain2= new Intent(SignUpActivity.this, MainActivity.class);
            register();
        }

        if(v == log){
            Intent goToLogin= new Intent(SignUpActivity.this, LogInActivity.class);
            goToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToLogin);
            finish();
        }
    }
}
