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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegistrationPage extends AppCompatActivity {
    public static final String PREFERENCES = "seattingArrangmentPrefs";
    public static final String USER_NAME = "username";

    private EditText mFirsName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mSignUpButton;

    //Firebase instance variable
    private FirebaseAuth auth;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        mFirsName = findViewById(R.id.etFirstName);
        mLastName = findViewById(R.id.etLastName);
        mEmail = findViewById(R.id.etRegisterEmail);
        mPassword = findViewById(R.id.etRegisterPassword);
        mConfirmPassword = findViewById(R.id.etRegisterConfirmPassword);

        mSignUpButton = findViewById(R.id.signupButton);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegitration();
            }
        });

        auth = FirebaseAuth.getInstance();
    }

    private void attemptRegitration(){
        mEmail.setError(null);
        mPassword.setError(null);

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;
        Log.d("Seating Arrangment", "thePassword: " + password);
        Log.d("Seating Arrangment", "TextUtils.isEmpty(thePassword): " + TextUtils.isEmpty(password));
        if(TextUtils.isEmpty(password)){
            mPassword.setError("this field is required");
            focusView = mPassword;
            cancel = true;
        }else if(!isPasswordValid(password)){
            mPassword.setError("password too short or does not match");
            focusView = mPassword;
            cancel = true;
        }

        if(TextUtils.isEmpty(email)){
            mEmail.setError("this field is required");
            focusView = mEmail;
            cancel = true;
        }else if(!isEmailValid(email)){
            mEmail.setError("invalid email address");
            focusView = mEmail;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }else{
            createFirebaseUser();
        }
    }

    //email validation
    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    //password validation
    private boolean isPasswordValid(String pass){

        String confirmPass = mConfirmPassword.getText().toString();

        return pass.length() >= 4 && confirmPass.equals(pass);
    }

    private void createFirebaseUser(){
        final String firstName = mFirsName.getText().toString();
        final String lastName = mLastName.getText().toString();
        final String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        Log.d("Seating Arrangment", "in createFirebaseUser method: ");
        Log.d("Seating Arrangment", "theEmail: " + email);
        Log.d("Seating Arrangment", "thePassword: " + password);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Seating Arrangment", "create user onComplete: " + task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.d("Seating Arrangment", "user creation failed.");
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Log.d("Seating Arrangment","Failed Registration: "+e.getMessage());
                    showErrorDialog("Registration faild.");
                }else{

                    User u = new User(firstName, lastName, email, "admin");
                    ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("User").child(auth.getCurrentUser().getUid()).setValue(u);
//                    saveUserName();
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

}