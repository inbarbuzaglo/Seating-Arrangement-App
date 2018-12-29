package com.seatingarrangement;

class User {

    String mFirstName;
    String mLastName;
    String mEmail;
    String mPermission;
    String mEventId;

    public User(){

    }

    public  User(String firstName, String lastName, String email, String permission){
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPermission = permission;
    } public  User(String firstName, String lastName, String email, String permission, String eventId){
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mPermission = permission;
        mEventId = eventId;
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

    public String getmEventId() {
        return mEventId;
    }

    public void setmEventId(String mEventId) {
        this.mEventId = mEventId;
    }
}
