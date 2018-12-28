package com.seatingarrangement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

//        FileInputStream serviceAccount = null;
//        try {
//            serviceAccount = new FileInputStream("file:///android_asset/seatingarrangement-fbdde-firebase-adminsdk-1wtj6-a65ae82bf1.json");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        FirebaseOptions options = null;
//        try {
//            options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://seatingarrangement-fbdde.firebaseio.com")
//                    .build();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        FirebaseApp.initializeApp(options);

        final Button goToLogin = (Button) findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainPage.this, LoginPage.class);
                MainPage.this.startActivity(i);
            }
        });
    }
}
