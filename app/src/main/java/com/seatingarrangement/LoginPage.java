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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginBtn;
    private Button mRegisterBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userId;
    private userInfo uInfo;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mEmail = findViewById(R.id.etLoginEmail);
        mPassword = findViewById(R.id.etLoginPassword);
        mLoginBtn = findViewById(R.id.loginButton);
        mRegisterBtn = findViewById(R.id.registrationButton);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        ref = FirebaseDatabase.getInstance().getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };
        mAuth = FirebaseAuth.getInstance();
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPage.this, RegistrationPage.class);
                LoginPage.this.startActivity(i);
            }
        });
    }

    public void attemptLogin(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            return;
        }
        Toast.makeText(this, "login...", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Seating Arrangment", "OnComplete: " + task.isSuccessful());
                userId = mAuth.getCurrentUser().getUid();
                Log.d("~~~userId", userId);
                if(!task.isSuccessful()){
                    Log.d("Seating Arrangment", "problem signing in: " + task.getException());
                    showError("There was a problem signing in");
                }else{

//                    Intent i;
//                    Log.d("~~~mPermission", uInfo.getmPermission());
//                    if(uInfo.getmPermission().equals("admin")){
//                        i = new Intent(LoginPage.this, SuperAdminPage.class);
//                        Log.d("~~login", "as admin");
//                    }else if(uInfo.getmPermission().equals("subAd11min")){
//                        i = new Intent(LoginPage.this, AdminPage.class);
//                        Log.d("~~login", "as subAdmin");
//                    }else{
//                        i = new Intent(LoginPage.this, AdminPage.class);
//                        Log.d("~~login", "as readOnly");
//                    }
//                    finish();
//                    if(i != null){
//                        LoginPage.this.startActivity(i);
//                    }
                }
            }
        });

        Intent i = new Intent(LoginPage.this, SuperAdminPage.class);
        finish();
        LoginPage.this.startActivity(i);
//        if(flag){
            ref = FirebaseDatabase.getInstance().getReference();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("~~~", "onDataChange1");
                    getUserInfo(dataSnapshot);
                    Log.d("~~~", "onDataChange2");
                    Intent i;
                    Log.d("~~~mPermission", uInfo.getmPermission());
                    if(uInfo.getmPermission().equals("admin")){
                        i = new Intent(LoginPage.this, SuperAdminPage.class);
                        Log.d("~~login", "as admin");
                    }else if(uInfo.getmPermission().equals("subAd11min")){
                        i = new Intent(LoginPage.this, AdminPage.class);
                        Log.d("~~login", "as subAdmin");
                    }else{
                        i = new Intent(LoginPage.this, AdminPage.class);
                        Log.d("~~login", "as readOnly");
                    }
                    finish();
                    if(i != null){
                        LoginPage.this.startActivity(i);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void getUserInfo(DataSnapshot dataSnapshot){
        Log.d("~~~", "getUserInfo");
        uInfo = new userInfo();
        uInfo.setmFirstName(dataSnapshot.child(userId).getValue(userInfo.class).getmFirstName());
        uInfo.setmLastName(dataSnapshot.child(userId).getValue(userInfo.class).getmLastName());
        uInfo.setmEmail(dataSnapshot.child(userId).getValue(userInfo.class).getmEmail());
        uInfo.setmPermission(dataSnapshot.child(userId).getValue(userInfo.class).getmPermission());
        Log.d("~~~", "END getUserInfo");
    }

    public void showError(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



    public class userInfo{
        String mFirstName;
        String mLastName;
        String mEmail;
        String mPermission;

        public userInfo(){

        }

        public String getmFirstName() {
            return mFirstName;
        }

        public void setmFirstName(String mFirstName) {
            this.mFirstName = mFirstName;
        }

        public String getmLastName() {
            return mLastName;
        }

        public void setmLastName(String mLastName) {
            this.mLastName = mLastName;
        }

        public String getmEmail() {
            return mEmail;
        }

        public void setmEmail(String mEmail) {
            this.mEmail = mEmail;
        }

        public String getmPermission() {
            return mPermission;
        }

        public void setmPermission(String mPermission) {
            this.mPermission = mPermission;
        }
    }
}
