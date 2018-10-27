package com.example.nirav.eventplanning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Design extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        final Button seeDescription = (Button) findViewById(R.id.btnDescription);

        seeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToTableInfo = new Intent(Design.this, TableInfo.class);
                Design.this.startActivity(goToTableInfo);
            }
        });
    }
}
