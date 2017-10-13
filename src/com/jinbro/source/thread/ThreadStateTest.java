package com.jinbro.source.thread;

public class ThreadStateTest {
    public static void main(String[] args) {
        Thread th1 = new StatePrintThread(new TestThread());
        th1.start();
    }
}


/* 리스너 달아두고 생명주기에 맞춰 어떤 것들을 할 수 있지않을까 */
class StatePrintThread extends Thread{

    private Thread target;

    StatePrintThread(Thread target) {
        this.target = target;
    }

    @Override
    public void run() {
        while(true) {
            Thread.State state = target.getState();
            System.out.println("타겟 쓰레드 상태 : " + state);

            if (state == State.NEW){
                target.start();
            } else if (state == State.TERMINATED){
                break;
            }
        }
    }

}

class TestThread extends Thread{

    @Override
    public void run() {
        for(int i=0; i<100000; i++){}
    }
}