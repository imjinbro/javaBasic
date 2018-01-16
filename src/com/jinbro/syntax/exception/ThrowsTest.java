package com.jinbro.syntax.exception;

import java.io.IOException;

public class ThrowsTest {

    public static void main(String[] args) {
        try{
            ThrowsTest.method1(null);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void method1(String msg) throws IOException, NullPointerException{
        String str = msg;
        System.out.println(str);
    }
}
