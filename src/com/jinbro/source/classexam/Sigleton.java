package com.jinbro.source.classexam;

public class Sigleton {

    private static final Sigleton obj = new Sigleton();

    private Sigleton(){

    }


    public static Sigleton getInstance(){
        return Sigleton.obj;
    }
}
