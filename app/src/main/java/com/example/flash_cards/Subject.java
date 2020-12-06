package com.example.flash_cards;

public class Subject {
    private String math;
    //private long id;

    public Subject() {
    }

    public Subject(String subjectname) {
        this.math = subjectname;
        //this.id = id;
    }

    public String getMath() {
        return math;
    }

    public void setMath(String math) {
        this.math = math;
    }

    //public long getId() {
       // return id;
    //}

   // public void setId(long id) {
    //    this.id = id;
   // }
}
