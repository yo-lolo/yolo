package com.example.myapplication.util;

public class SimContacts {
    String name;
    String number;
    String emails;
    String _id;

    public SimContacts(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public SimContacts() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "SimContacts{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", emails='" + emails + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}
