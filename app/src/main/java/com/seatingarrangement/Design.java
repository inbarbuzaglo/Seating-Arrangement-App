package com.seatingarrangement;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
/** ID DOESNT INSERT TO DB */
import static android.content.ContentValues.TAG;

public class Design extends AppCompatActivity {

    ArrayList<String> table_list = new ArrayList<String>();
    private DatabaseReference db;
    static int btn_id = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        db = FirebaseDatabase.getInstance().getReference("Table");
        Button add = (Button) findViewById(R.id.addTable);
        EditText  ed = (EditText) findViewById(R.id.edit);




        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** event_id in db*/
          //      Bundle extras = getIntent().getExtras();
           //     final int eventID = extras.getInt("event_id");

                AddTable();

            }


        };
        add.setOnClickListener(listener);

    }

    private void AddTable() {


        /**add new Table to db with unique id*/
        String TableId = db.push().getKey();
        final Table T = new Table(TableId);
        db.child(TableId).setValue(T);
        table_list.add(T.getTable());


        EditText ed = (EditText) findViewById(R.id.edit);
        T.getnumber(ed);


        /** find tablelayout */
        TableLayout ll = (TableLayout) findViewById(R.id.linear);

        /** create new row */
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        /** create a new button to be in the row content */
        final Button myButton = new Button(this);
        myButton.setText("" + T.getId()+ "");
        Drawable d = Drawable.createFromPath("@mipmap/ic_launcher_foreground");
        myButton.setBackgroundResource(R.mipmap.ic_launcher_foreground);
        myButton.setId(T.getId());
        myButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        /** add button to a row */
        tr.addView(myButton);
        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tr.setPadding(20, 20, 20, 20);

        /** add row to table_layout */
        ll.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));


        /** Each button taking to table info */
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Design.this, TableInfo.class);
                in.putExtra("table_id", T.getId());
                startActivity(in);

            }
        };
        myButton.setOnClickListener(listener1);

/** REMOVE BUTTON */
myButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                myButton.setVisibility(View.GONE);
                table_list.remove(myButton);
                return true;
            }
        });
    }



    /**Read tables from db.*/

    private DatabaseReference mDatabase;
    private ValueEventListener postListener;


    public void ReadFromFireBase(final int eventID) {
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                /**problem here:*/
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    Table T1 = childSnapshot.getValue(Table.class);
                    if(T1.event_id==eventID) {
                        if (!table_list.contains(T1.getTable())) {
                            table_list.add(T1.getTable());

                        }
                    }

                }



            }

            /** Exception DB*/
            @Override
            public void onCancelled(DatabaseError databaseError) {
// Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
// ...
            }
        };
        db.addValueEventListener(postListener);
    }
/**
    public int getnumber()
    {

        Table T2=new Table();
        int num=T2.getId();
        EditText  ed = (EditText) findViewById(R.id.edit);
        num=Integer.parseInt(ed.getText().toString());
        T2.setId(num);

        return num;
    }
 */
}
