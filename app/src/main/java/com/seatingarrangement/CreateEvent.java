package com.seatingarrangement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateEvent extends AppCompatActivity {

    private EditText mEventName;
    private Button mCreateEvent;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mEventName = findViewById(R.id.eventName);
        mCreateEvent = findViewById(R.id.createNewEvent);
        mCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEventName.getText().toString();
                Log.d("~~~", name);
                String adminId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.d("~~~", adminId);
                EventInfo e = new EventInfo(name, adminId);
                Log.d("~~~", "e" + e.toString());
                ref = FirebaseDatabase.getInstance().getReference();
                String key = ref.child("Event").push().getKey();
                Log.d("~~~", "key" + key);
//                Map<String, EventInfo> events = new HashMap<>();
//                events.put(key, e);
//                ref.child("Event").
                ref.child("Event").child(key).setValue(e, new DatabaseReference.CompletionListener() {
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        Log.d("~~~", "Value was set. Error = " + error);
                    }});
                Intent i = new Intent(CreateEvent.this, SuperAdminPage.class);
                Log.d("~~~", "Intent");
                finish();
                CreateEvent.this.startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Log.d("~~~", "override");
        Intent i = new Intent(CreateEvent.this, SuperAdminPage.class);
        finish();
        CreateEvent.this.startActivity(i);
    }
}
