package com.jinbro.source.thread;

public class DaemonThreadTest{
    public static void main(String[] args) {
        AutoSavedThread daemon = new AutoSavedThread();
        daemon.setDaemon(true);
        daemon.start(); //메인쓰레드(main() 메서드 실행)가 종료되어 데몬쓰레드도 종료



        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class AutoSavedThread extends Thread{
    /* 1초 간격으로 자동저장하는 데몬쓰레드 */
    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("자동저장");
        }
    }
}


