package com.seatingarrangement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TableInfo extends AppCompatActivity {

    private Button newTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_info);

        newTable = (Button) findViewById(R.id.createNewTable);
        newTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TableInfo.this, Design.class);
                TableInfo.this.startActivity(i);
            }
        });
    }
}
