package com.udhaar.udhaar;

/**
 * Created by Architkansal on 16-04-2016.
 */

public class Contacts {

    //private variables
    int _id;
    String _name;
    String _phone_number;
    int _money;
    String _time;

    // Empty constructor
    public Contacts(){

    }
    // constructor
    public Contacts(int id, String name, String _phone_number,int money){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._money=money;
    }

    public Contacts(int id, String name, String _phone_number,int money ,String time){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._money=money;
        this._time = time;
    }

    public Contacts(String name, String _phone_number,int money){
        this._name = name;
        this._phone_number = _phone_number;
        this._money=money;
    }

    // constructor
    public Contacts(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
        this._money=0;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public int getMoney(){
        return this._money;
    }


    public String getTime(){
        return this._time;
    }


    public void setTime(String time) {  this._time = time;  }


    // setting id
    public void setMoney(int money){
        this._money = money;
    }
}
