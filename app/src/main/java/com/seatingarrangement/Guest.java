package com.seatingarrangement;

public class Guest {


    String f_name;
    String l_name;
    String G_id;
    int amount;
    int table_id;



    public Guest(String first, String last, int am,int tableid,String Guestid){
        this.f_name=first;
        this.l_name=last;
        this.amount=am;
        this.table_id=tableid;
        this.G_id=Guestid;

    }

    public Guest(){

    }

    public String getF_name() {
        return f_name;
    }

    public String getL_name() {
        return l_name;
    }



    public void set_Guest(String first, String last, int am, int tableid) {
        this.f_name = first;
        this.l_name = last;
        this.amount = am;
        this.table_id = tableid;
    }

    public int getAmount() {
        return amount;
    }

    public String getGuest(){
        return this.f_name+" "+this.l_name+" "+this.amount;
    }

}
