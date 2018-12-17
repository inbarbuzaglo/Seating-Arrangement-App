package com.seatingarrangement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuthException;

import org.w3c.dom.Text;

public class RegistrationPage extends AppCompatActivity {
    public static final String PREFERENCES = "seattingArrangmentPrefs";
    public static final String USER_NAME = "username";

    private EditText userName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button signUpButton;

    //Firebase instance variable
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        userName = (EditText) findViewById(R.id.etRegistrationUserName);
        email = (EditText) findViewById(R.id.etRegistrationEmail);
        password = (EditText) findViewById(R.id.etRegistrationPassword);
        confirmPassword = (EditText) findViewById(R.id.etRegistrationConfirmPassword);
        signUpButton = (Button) findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegitration();
            }
        });

        auth = FirebaseAuth.getInstance();
    }

    private void attemptRegitration(){
        email.setError(null);
        password.setError(null);

        String theEmail = email.getText().toString();
        String thePassword = password.getText().toString();

        boolean cancel = false;
        View focusView = null;
        Log.d("Seating Arrangment", "thePassword: " + thePassword);
        Log.d("Seating Arrangment", "TextUtils.isEmpty(thePassword): " + TextUtils.isEmpty(thePassword));
        if(TextUtils.isEmpty(thePassword)){
            password.setError("this field is required");
            focusView = password;
            cancel = true;
        }else if(!isPasswordValid(thePassword)){
            password.setError("password too short or does not match");
            focusView = password;
            cancel = true;
        }

        if(TextUtils.isEmpty(theEmail)){
            email.setError("this field is required");
            focusView = email;
            cancel = true;
        }else if(!isEmailValid(theEmail)){
            email.setError("invalid email address");
            focusView = email;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }else{
            //TODO call create FirebaseUser() here
            createFirebaseUser();
        }
    }

    //email validation
    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    //password validation
    private boolean isPasswordValid(String pass){

        String confirmPass = confirmPassword.getText().toString();

        if(pass.length() < 4 || !confirmPass.equals(pass)){
//            Toast.makeText(RegistrationPage.this, "הסיסמא חייבת להכיל לפחות 4 תווים.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void createFirebaseUser(){
        String theEmail = email.getText().toString();
        String thePassword = password.getText().toString();
        Log.d("Seating Arrangment", "in createFirebaseUser method: ");
        Log.d("Seating Arrangment", "theEmail: " + theEmail);
        Log.d("Seating Arrangment", "thePassword: " + thePassword);
        auth.createUserWithEmailAndPassword(theEmail, thePassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Seating Arrangment", "create user onComplete: " + task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.d("Seating Arrangment", "user creation failed.");
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Log.d("Seating Arrangment","Failed Registration: "+e.getMessage());
                    showErrorDialog("Registration faild.");
                }else{
                    saveUserName();
                    Intent i = new Intent(RegistrationPage.this, LoginPage.class);
                    finish();
                    RegistrationPage.this.startActivity(i);
                }
            }
        });
    }

    private void showErrorDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Error!")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void saveUserName(){
        String theUserName = userName.getText().toString();
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, 0);
        prefs.edit().putString(USER_NAME, theUserName).apply();
    }
}
