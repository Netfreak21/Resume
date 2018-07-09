package com.example.lokesh.keepmemanaged;

/**
 * Created by lokesh on 29/6/18.
 */

public class User {
    public String username,college,year,cource,passforfun;
    User(){

    }
    User(String name,String clg,String year,String cource,String pass)
    {
        this.username=name;
        this.college=clg;
        this.year=year;
        this.cource=cource;
        this.passforfun=pass;
    }
}
