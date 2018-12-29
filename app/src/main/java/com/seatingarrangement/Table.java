package com.seatingarrangement;

import android.widget.Button;
import android.widget.EditText;

public class Table {

    String id; //table number
    String unique_id; //table id in db
    String event_id;

    Button btn;

    public Table(String ID,String u_id , String e_id) {

        this.id = ID;
        this.unique_id = u_id;
        this.event_id = e_id;

    }
    public Table() {



    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public String getnumber(EditText ed )
    {

        this.id=ed.getText().toString();
        setId(this.id);

        return this.id;
    }
}
