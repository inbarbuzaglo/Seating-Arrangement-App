package com.seatingarrangement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventInfo{

    private String mName;
    private String mAdminid;

    public EventInfo(){
    }
    public EventInfo(String name, String id){
        mName = name;
        mAdminid = id;
    }

    public String getmAdminid() {
        return mAdminid;
    }

    public void setmAdminid(String mAdminid) {
        this.mAdminid = mAdminid;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
