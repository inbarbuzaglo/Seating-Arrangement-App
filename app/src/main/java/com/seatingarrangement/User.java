package com.seatingarrangement;

class User {

    String mFirstName;
    String mLastName;
    String mEmail;
    String mPermission;

    public User(){

    }

    public  User(String firstName, String lastName, String email, String permission){
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPermission = permission;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPermission() {
        return mPermission;
    }

    public void setmPermission(String mPermission) {
        this.mPermission = mPermission;
    }
}
