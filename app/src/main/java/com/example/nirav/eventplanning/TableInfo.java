package com.example.nirav.eventplanning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TableInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_info);

        final Button comment = (Button) findViewById(R.id.etComment);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToComment = new Intent(TableInfo.this, Comment.class);

                TableInfo.this.startActivity(goToComment);
            }
        });
    }
}
