package com.seatingarrangement;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Comment extends AppCompatActivity {

    private EditText subject;
    private EditText details;
    private EditText from;
    private Button send_your_comment;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        from = (EditText) findViewById(R.id.From);
        subject = (EditText) findViewById(R.id.theSubjecttext);
        details = (EditText) findViewById(R.id.theDetailstext);
        send_your_comment = (Button) findViewById(R.id.Send);

        db = FirebaseDatabase.getInstance().getReference();

        send_your_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data_from = from.getText().toString();
                String data_subject = subject.getText().toString();
                String data_details = details.getText().toString();

                Intent intent = new Intent(Comment.this, TempActivity.class);

                intent.putExtra("data_from", data_from);
                intent.putExtra("data_subject", data_subject);
                intent.putExtra("data_details", data_details);

                startActivity(intent);
                //   db.child("comment").push().setValue(from);
                // finish();




            }
        });

    }

}