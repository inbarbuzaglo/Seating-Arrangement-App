package com.example.nirav.eventplanning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText userName = (EditText) findViewById(R.id.etUserName);
        final EditText password = (EditText) findViewById(R.id.etPassword);

        final Button login = (Button) findViewById(R.id.btnLogin);
        final Button register = (Button) findViewById(R.id.btnRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPlans = new Intent(Login.this, Design.class);
                Login.this.startActivity(goToPlans);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(Login.this, Register.class);
                Login.this.startActivity(goToRegister);
            }
        });
    }
}
