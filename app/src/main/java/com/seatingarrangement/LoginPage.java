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
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginBtn;
    private Button mRegisterBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final Map<String, ArrayList<String>> mUserPermission = new HashMap<String, ArrayList<String>>();;
    private String userId;
    private String mPermission;

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

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginPage.this, RegistrationPage.class);
                LoginPage.this.startActivity(i);
            }
        });

        mAuth = FirebaseAuth.getInstance();
//        mUserPermission = new HashMap<String, ArrayList<String>>();

        ref = FirebaseDatabase.getInstance().getReference().child("User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    User u = child.getValue(User.class);
                    if(u.getmPermission().equals("admin")){
                        if(!mUserPermission.containsKey("admin")){
                            mUserPermission.put("admin", new ArrayList<String>());
                        }
                        mUserPermission.get("admin").add(child.getKey());
                    }else if(u.getmPermission().equals("subAdmin")){
                        if(!mUserPermission.containsKey("subAdmin")){
                            mUserPermission.put("subAdmin", new ArrayList<String>());
                        }
                        mUserPermission.get("subAdmin").add(child.getKey());
                    }else if(u.getmPermission().equals("readOnly")){
                        if(!mUserPermission.containsKey("readOnly")){
                            mUserPermission.put("readOnly", new ArrayList<String>());
                        }
                        mUserPermission.get("readOnly").add(child.getKey());
                    }
                    Log.d("###", "child.getKey(): " + child.getKey());
                    Log.d("###", "child.getKey(): " + u.getmPermission());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void attemptLogin(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            showError("Please enter email and password.");
            return;
        }
        Toast.makeText(this, "login...", Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Seating Arrangment", "OnComplete: " + task.isSuccessful());
                Log.d("Seating Arrangment", "mAuth: " + mAuth);
                Log.d("Seating Arrangment", "mAuth.getCurrentUser(): " + mAuth.getCurrentUser());
                userId = mAuth.getCurrentUser().getUid();
                Log.d("~~~userId", userId);
                if(!task.isSuccessful()){
                    Log.d("Seating Arrangment", "problem signing in: " + task.getException());
                    showError("There was a problem signing in");
                }else{
                    Intent i = new Intent();
                    Log.d("###", "mUserPermission: " + mUserPermission);
                    if(mUserPermission.get("admin") != null && mUserPermission.get("admin").contains(userId)){
                        i.setClass(LoginPage.this, SuperAdminPage.class);
                        Log.d("~~login", "as admin");
                    }else if(mUserPermission.get("admin") != null && mUserPermission.get("subAdmin").contains(userId)){
                        i.setClass(LoginPage.this, AdminPage.class);
                        Log.d("~~login", "as subAdmin");
                    }else if(mUserPermission.get("admin") != null && mUserPermission.get("readOnly").contains(userId)){
                        i.setClass(LoginPage.this, AdminPage.class);
                        Log.d("~~login", "as readOnly");
                    }
                    LoginPage.this.startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        ref = FirebaseDatabase.getInstance().getReference().child("User");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot child : dataSnapshot.getChildren()){
//                    User u = child.getValue(User.class);
//                    if(u.getmPermission().equals("admin")){
//                        if(!mUserPermission.containsKey("admin")){
//                            mUserPermission.put("admin", new ArrayList<String>());
//                        }
//                    }else if(u.getmPermission().equals("subAdmin")){
//                        if(!mUserPermission.containsKey("subAdmin")){
//                            mUserPermission.put("subAdmin", new ArrayList<String>());
//                        }
//                        mUserPermission.get("subAdmin").add(child.getKey());
//                    }else if(u.getmPermission().equals("readOnly")){
//                        if(!mUserPermission.containsKey("readOnly")){
//                            mUserPermission.put("readOnly", new ArrayList<String>());
//                        }
//                        mUserPermission.get("readOnly").add(child.getKey());
//                    }
//                    Log.d("###", "child.getKey(): " + child.getKey());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void showError(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
