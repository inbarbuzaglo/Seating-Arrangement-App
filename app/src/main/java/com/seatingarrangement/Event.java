package com.seatingarrangement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_SHORT;

public class Event extends AppCompatActivity {

    private Button mDelete;
    private Button mNewUser;
    private String mId;
    private DatabaseReference mRef;
    private Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Bundle extras = getIntent().getExtras();
        mId = extras.getString("key");

        mRef = FirebaseDatabase.getInstance().getReference();


        mDelete = findViewById(R.id.deleteEvent);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("~~~", "f" + mId);
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mRef.child(mId).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError != null){
                                    Log.d("~~~", "Error: " + databaseError.getMessage());
                                }
                            }
                        });
                        Intent i = new Intent(Event.this, SuperAdminPage.class);
                        finish();
                        Event.this.startActivity(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//                mRef.child(mId).removeValue();
//                Intent i = new Intent(Event.this, SuperAdminPage.class);
//                finish();
//                Event.this.startActivity(i);
            }
        });
        d = new Dialog(Event.this);
        mNewUser = findViewById(R.id.newSubAdmin);
        mNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.setTitle("New Customer");
                d.setContentView(R.layout.dialog_new_sub_admin);
                d.show();

                Button mCreateBtn = d.findViewById(R.id.dialogCreateUser);

                mCreateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attemptRegitration();
                        d.cancel();
                    }
                });
            }
        });
    }
    public void attemptRegitration(){
        EditText mEmail = d.findViewById(R.id.dialogEmail);
        EditText mPassword = d.findViewById(R.id.dialogPassword);


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

    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private boolean isPasswordValid(String pass){
        EditText mConfirmPassword = d.findViewById(R.id.dialogConfirmPassword);
        String confirmPass = mConfirmPassword.getText().toString();

        return pass.length() >= 4 && confirmPass.equals(pass);
    }
    private void createFirebaseUser(){
        EditText mFirsName = d.findViewById(R.id.dialogFirstName);
        EditText mLastName = d.findViewById(R.id.dialogLastName);
        final String firstName = mFirsName.getText().toString();
        final String lastName = mLastName.getText().toString();
        EditText mEmail = d.findViewById(R.id.dialogEmail);
        EditText mPassword = d.findViewById(R.id.dialogPassword);
        final String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        Log.d("Seating Arrangment", "in createFirebaseUser method: ");
        Log.d("Seating Arrangment", "theEmail: " + email);
        Log.d("Seating Arrangment", "thePassword: " + password);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Seating Arrangment", "create user onComplete: " + task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.d("Seating Arrangment", "user creation failed.");
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Log.d("Seating Arrangment","Failed Registration: "+e.getMessage());
                    showErrorDialog("Registration faild.");
                }else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    User u = new User(firstName, lastName, email, "subAdmin", mId);
                    ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("User").child(auth.getCurrentUser().getUid()).setValue(u);
                    Toast.makeText(Event.this,"dfg", Toast.LENGTH_LONG).show();
//                    saveUserName();
//                    Intent i = new Intent(RegistrationPage.this, LoginPage.class);
//                    finish();
//                    RegistrationPage.this.startActivity(i);
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
