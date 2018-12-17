package com.seatingarrangement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button register;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email = (EditText) findViewById(R.id.etLoginEmail);
        password = (EditText) findViewById(R.id.etLoginPassword);
        login = (Button) findViewById(R.id.loginButton);
        register = (Button) findViewById(R.id.registrationButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPage.this, RegistrationPage.class);
                LoginPage.this.startActivity(i);
            }
        });
    }

    public void attemptLogin(){
        String theEmail = email.getText().toString();
        String thePassword = password.getText().toString();

        if(TextUtils.isEmpty(theEmail) || TextUtils.isEmpty(thePassword)){
            return;
        }
        Toast.makeText(this, "login...", Toast.LENGTH_SHORT);
        auth.signInWithEmailAndPassword(theEmail, thePassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Seating Arrangment", "OnComplete: " + task.isSuccessful());
                if(!task.isSuccessful()){
                    Log.d("Seating Arrangment", "problem signing in: " + task.getException());
                    showError("There was a problem signing in");
                }else{
                    //TODO: check permission an go to correct activity
                    Intent i = new Intent(LoginPage.this, TempActivity.class);
                    finish();
                    LoginPage.this.startActivity(i);
                }

            }
        });
    }

    public void showError(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
