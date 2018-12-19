package com.seatingarrangement;


import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class TableInfo extends ListActivity {


    /** Note that here we are inheriting ListActivity class instead of Activity class **/


        /** Items entered by the user is stored in this ArrayList variable */
        ArrayList<String> al = new ArrayList<String>();


        /** Declaring an ArrayAdapter to set items to ListView */
        ArrayAdapter<String> adapter;



        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            /** Setting a custom layout for the list activity */

            /**GET GUEST LIST FROM DB BY TABLE ID(RECIVED FROM DESIGN VIEW) AND INIT ARRAYLIST WITH THIS LIST*/


            setContentView(R.layout.activity_table_info);
            ListView lv;
            lv= getListView();



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
                    EditText editf =  findViewById(R.id.txtItem1);
                    EditText editl =  findViewById(R.id.txtItem2);
                    EditText editnum =  findViewById(R.id.editText2);
                    String value= editnum.getText().toString();
                    int guestsnum=Integer.parseInt(value);
                    Guest g=new Guest(editf.getText().toString(),editl.getText().toString(),guestsnum);
                    al.add(g.getGuest());
                    editf.setText("");
                    editl.setText("");
                    editnum.setText("");
                    adapter.notifyDataSetChanged();
                }

            };


            btn.setOnClickListener(listener);

            /** Setting the adapter to the ListView */
          //



        }
    }


