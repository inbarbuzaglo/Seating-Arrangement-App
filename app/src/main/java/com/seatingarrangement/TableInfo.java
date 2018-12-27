package com.seatingarrangement;


import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class TableInfo extends ListActivity {


    /** Note that here we are inheriting ListActivity class instead of Activity class **/


    /**
     * Items entered by the user is stored in this ArrayList variable
     */
    ArrayList<String> al = new ArrayList<String>();
    int id;
    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    ArrayAdapter<String> adapter;
    DatabaseReference db;
    DatabaseReference db_table;
    //  int id_table = 1; //recive from design view onclick

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Setting a custom layout for the list activity */

        /**GET GUEST LIST FROM DB BY TABLE ID(RECIVED FROM DESIGN VIEW) AND INIT ARRAYLIST WITH THIS LIST*/

        db = FirebaseDatabase.getInstance().getReference("Guest");
        db_table = FirebaseDatabase.getInstance().getReference("Table");
        setContentView(R.layout.activity_table_info);
        ListView lv;
        lv = getListView();


        Bundle extras = getIntent().getExtras();
        final int tableID = extras.getInt("table_id");
        TextView tv = (TextView) findViewById(R.id.TableNum);

        tv.setText(String.valueOf(tableID));

        ReadFromFireBase(tableID);
        /** Reference to the button of the layout main.xml */
        Button btn = (Button) findViewById(R.id.add_list_btn);

        /** Defining the ArrayAdapter to set items to ListView */

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);

        lv.setAdapter(adapter);
        /** Setting the event listener for the add button */

        /** Defining a click event listener for the button "Add" */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addguest(tableID);
            }

        };
        btn.setOnClickListener(listener);

        /**Remove item from list view bu long click*/
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                al.remove(position);
                adapter.notifyDataSetChanged();

                Query query = db.child("id").orderByChild("guest").equalTo("lili ll 2");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                Toast.makeText(TableInfo.this, "Item Deleted", Toast.LENGTH_LONG).show();
                return true;
            }

        });




    }


    private void Addguest(int TableID) {
        EditText editf = findViewById(R.id.txtItem1);
        EditText editl = findViewById(R.id.txtItem2);
        EditText editnum = findViewById(R.id.editText2);
        String value = editnum.getText().toString();
        int guestsnum = Integer.parseInt(value);


        /**add new guest to db with unique id*/
        String GuestId = db.push().getKey();
        Guest g = new Guest(editf.getText().toString(), editl.getText().toString(), guestsnum,TableID,GuestId);
        db.child(GuestId).setValue(g);


        /**add new guest to arraylist*/
        al.add(g.getGuest());
        editf.setText("");
        editl.setText("");
        editnum.setText("");
        adapter.notifyDataSetChanged();

    }

    private DatabaseReference mDatabase;
    private ValueEventListener postListener;


    public void ReadFromFireBase(final int tableID) {
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                /**problem here:*/
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    // String table=Integer.toString(tableID);

                    Guest g1 = childSnapshot.getValue(Guest.class);
                    if(g1.table_id==tableID) {
                        if (!al.contains(g1.getGuest())) {
                            al.add(g1.getGuest());
                        }
                    }

                }



            }

            @Override
            public void onCancelled (DatabaseError databaseError){
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        }

        ;
        db.addValueEventListener(postListener);
    }
}





