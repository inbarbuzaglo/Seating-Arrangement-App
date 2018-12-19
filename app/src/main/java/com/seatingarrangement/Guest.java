package com.seatingarrangement;

public class Guest {

    static int id_dynamic=1;
    String f_name;
    String l_name;
    int id;
    int amount;

    Guest(String first, String last, int am){
        this.f_name=first;
        this.l_name=last;
        this.amount=am;
        this.id=id_dynamic++;
    }

    public String getF_name() {
        return f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public int getAmount() {
        return amount;
    }

    public String getGuest(){
        return this.f_name+" "+this.l_name+" "+this.amount;
    }

}
