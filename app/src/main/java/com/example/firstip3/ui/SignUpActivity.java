package com.example.firstip3.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstip3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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

    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progDialogAuth;

    private String personName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        crAcc.setOnClickListener(this);
        log.setOnClickListener(this);

        authProtocol = FirebaseAuth.getInstance();
        signUpAuthStateListener();

        progressDialogFeature();

    }

    private void progressDialogFeature(){
        progDialogAuth = new ProgressDialog(this);
        progDialogAuth.setTitle("Wait....");
        progDialogAuth.setMessage("Authentification avec firebase...");
        progDialogAuth.setCancelable(false);

    }


    private void signUpAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

        };
    }

    private boolean emailCredential(String addrEmail){
        boolean isEmailValid = (addrEmail != null && Patterns.EMAIL_ADDRESS.matcher(addrEmail).matches());
        if(!isEmailValid){
            email2.setError("the email is invalid");
            return false;
        }
        return isEmailValid;
    }

    private boolean nameCredential(String username){
        if(username.equals("")){
            name.setError("name can't be empty");
            return false;
        }
        return true;
    }

    private boolean passwordCredential(String password, String confirmPassword){
        if(password.length()<6){
            pass2.setError("password must have more than 6 characters");
            return false;

        }else if(!password.equals(confirmPassword)){
            pass2.setError("passwords do not match");
            return false;
        }
        return true;
    }


    private void register() {

        personName = name.getText().toString().trim();

        final String newName = name.getText().toString().trim();
        final String newEmail = email2.getText().toString().trim();
        String newPassword = pass2.getText().toString().trim();
        String newConfirmPassword = confPass.getText().toString().trim();

        boolean vraiEmail= emailCredential(newEmail);
        boolean vraiName= nameCredential(personName);
        boolean vraiPassword= passwordCredential(newPassword, newConfirmPassword);

        if(!vraiEmail || !vraiName || !vraiPassword) return;
        progDialogAuth.show();

        authProtocol.createUserWithEmailAndPassword(newEmail, newPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progDialogAuth.dismiss();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Authentication successful");
                            viewUserProfile(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void viewUserProfile(final FirebaseUser person){
        UserProfileChangeRequest addUserName = new UserProfileChangeRequest.Builder()
                .setDisplayName(personName)
                .build();

        person.updateProfile(addUserName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, person.getDisplayName());
                        }
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
