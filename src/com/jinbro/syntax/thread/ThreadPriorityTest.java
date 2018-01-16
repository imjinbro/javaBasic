package com.jinbro.syntax.thread;

public class ThreadPriorityTest {
    public static void main(String[] args) {
        for(int i=1; i<=10; i++){
            Thread th = new CalcThread(i);

            if(i==10){
                th.setPriority(Thread.MAX_PRIORITY);
            } else {
                th.setPriority(Thread.MIN_PRIORITY);
            }

            /* 나머지는 5할당 */
            th.start();
        }
    }
}

class CalcThread extends Thread{

    CalcThread(int i) {
        this.setName("쓰레드" +i);
    }

    @Override
    public void run() {
        for(int i=0; i<2000000000; i++){ }
        System.out.println(this.getName() + " 연산 끝");
    }
}


