package com.seatingarrangement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuperAdminPage extends AppCompatActivity {


    private DatabaseReference mRef;
    private Button newEvent;
    private ListView mListView;

    private ArrayList<String> mArrayList;
    private ArrayAdapter<String> mAdapter;
    private Map<String, String> mEventIdToName;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_page);

        mRef = FirebaseDatabase.getInstance().getReference().child("Event");
        mArrayList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mArrayList);
        mEventIdToName = new HashMap<>();

        newEvent = findViewById(R.id.newEventBtn);
        mListView = findViewById(R.id.eventsListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
                // Then you start a new Activity via Intent
                Intent i = new Intent();
                Log.d("~~~" , "d " + mEventIdToName);
                Log.d("~~~" , "d " + mEventIdToName.keySet());
                String idKey = null;
                for(String key : mEventIdToName.keySet()){
                    if(mEventIdToName.get(key).equals(mArrayList.get(position))){
                        idKey = key;
                        break;
                    }
                }
                if(idKey != null){
                    i.putExtra("key", idKey);
                    mEventIdToName.remove(idKey);
                    i.setClass(SuperAdminPage.this, Event.class);
                    SuperAdminPage.this.startActivity(i);
                }
            }
        });


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<EventInfo> events = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EventInfo e = snapshot.getValue(EventInfo.class);
                    System.out.println(e.getmName());
                    mEventIdToName.put(snapshot.getKey(), e.getmName());
                    events.add(e);
                }
                for(EventInfo ev : events){
                    mArrayList.add(ev.getmName());
                    Log.d("~~~", "ev.getmName"+ev.getmName());
                }
                mListView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuperAdminPage.this, CreateEvent.class);
                finish();
                SuperAdminPage.this.startActivity(i);
            }
        });
    }
}