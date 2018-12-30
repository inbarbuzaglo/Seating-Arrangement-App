package com.seatingarrangement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Event extends AppCompatActivity {

    private Button mDelete;
    private String mId;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Bundle extras = getIntent().getExtras();
        mId = extras.getString("key");

        mRef = FirebaseDatabase.getInstance().getReference();

        mDelete = findViewById(R.id.deleteEvent);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("~~~", "f" + mId);
                mRef.child(mId).removeValue();
                Intent i = new Intent(Event.this, SuperAdminPage.class);
                finish();
                Event.this.startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("~~~", "override");
        Intent i = new Intent(Event.this, SuperAdminPage.class);
        finish();
        Event.this.startActivity(i);
    }
}
