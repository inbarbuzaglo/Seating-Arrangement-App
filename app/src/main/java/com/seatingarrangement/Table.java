package com.seatingarrangement;

import android.widget.Button;
import android.widget.EditText;

public class Table {


    int id; //table number
    String unique_id; //table id in db
    Button btn;
    int event_id;


    public Table() {

       // id_dynamic++;
      //  this.id=id_dynamic;

    }
    public Table(String uniqe) {
        this.unique_id=uniqe;
     //  id_dynamic++;
     //   this.id=id_dynamic;

    }

    public int setId(int id) {
        return this.id = id;
    }

    public int getId(int id) {
       return this.id = id;
    }

    public String getTable(){
        return this.unique_id+"";
    }

}