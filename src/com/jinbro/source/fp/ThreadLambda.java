package com.jinbro.source.fp;

public class ThreadLambda {
    public static void main(String[] args) {

        /*
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }); */

        // Runnable 구현 객체자리 : Runnable.run() 추론 - 람다식
        Thread th = new Thread( () -> System.out.println("ㅎㅇ") );
        th.start();
    }
}

