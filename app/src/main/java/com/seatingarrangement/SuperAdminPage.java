package com.seatingarrangement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuperAdminPage extends AppCompatActivity {

    private Button newEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_page);

        newEvent = (Button) findViewById(R.id.createNewEvent);
        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuperAdminPage.this, CreateNewEvent.class);
                SuperAdminPage.this.startActivity(i);
            }
        });
    }
}
