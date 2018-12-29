package com.seatingarrangement;

import android.widget.Button;
import android.widget.EditText;

public class Table {

    private  static int dynamic_id;
    int id; //table number
    String unique_id; //table id in db
    Button btn;
    int event_id;



    public Table() {

        dynamic_id++;
        this.id=dynamic_id;

    }
    public Table(String uniqe) {
        this.unique_id=uniqe;

    }

    public void setId(int id) {
         this.id=id;
    }

    public int getId() {
       return this.id;
    }

    public String getTable(){
        return this.unique_id+this.id+"";
    }


    public int getnumber( EditText ed )
    {

        this.dynamic_id=Integer.parseInt(ed.getText().toString());
        setId(this.dynamic_id);

        return this.dynamic_id;
    }
}
