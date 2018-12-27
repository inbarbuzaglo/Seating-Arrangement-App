package com.seatingarrangement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    private TextView tv1,tv2,tv3;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        String data_from = getIntent().getStringExtra("data_from");
        tv1 = (TextView) findViewById(R.id.textView1);
        tv1.setText("New Message from: "+data_from);

        String data_subject = getIntent().getStringExtra("data_subject");
        tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText("Subject: "+data_subject);
        tv2.setVisibility(View.GONE);

        String data_details = getIntent().getStringExtra("data_details");
        tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setText("Details: "+data_details);
        tv3.setVisibility(View.GONE);

        btn= (Button)  findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn.isClickable())
                {
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.VISIBLE);

                }
                else
                {
                    tv2.setVisibility(View.GONE);
                    tv3.setVisibility(View.GONE);
                }
            }
        });
    }
}