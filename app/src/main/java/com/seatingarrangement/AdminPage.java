package com.seatingarrangement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminPage extends AppCompatActivity {

    private Button tables;
    private DatabaseReference mRef;
    private String mEventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        mRef = FirebaseDatabase.getInstance().getReference().child("User");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = dataSnapshot.child(FirebaseAuth.getInstance().getUid()).getValue(User.class);
                Log.d("****", " u.getmEventId(): " + u.getmEventId());
                mEventId = u.getmEventId();
                Log.d("****", " u.getmEventId(): " + u.getmEventId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tables = (Button) findViewById(R.id.tableList);
        tables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminPage.this, Design.class);
                i.putExtra("eventId", mEventId);
                AdminPage.this.startActivity(i);
            }
        });
    }
}
