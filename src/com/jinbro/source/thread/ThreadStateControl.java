package com.jinbro.source.thread;

public class ThreadStateControl {
    public static void main(String[] args) {
        ExamThread thread1 = new ExamThread();
        ExamThread thread2 = new ExamThread();

        //번갈아가면서 쓰레드 실행할 것임
        thread1.start();
        thread2.start();

        int num = 10;
        try{
            // 실행 중인 쓰레드 일시정지 상태 : 시간이 지나면 RUNNABLE 쓰레드
            Thread.sleep(100);
        }catch(InterruptedException e){
            //일시정지 상태에서 RUNNABLE 상태로 가기 전에 Thread객체.interrupt()가 호출되면 실행
            e.printStackTrace();
        }

        //실행대기상태로, thread2만 실행
        thread1.work = false;
        try{ Thread.sleep(100); } catch(InterruptedException e){ }

        //다시 실행상태로
        thread1.work = true;
        try{ Thread.sleep(100); } catch(InterruptedException e){ }


        //쓰레드 종료
        thread1.stop = true;
        thread2.stop = true;


        //join : 쓰레드 종료를 기다림, 아래는 SumThread가 종료되기전까지 메인쓰레드가 종료되지않도록
        SumThread sum = new SumThread();
        sum.start();


        try {
            sum.join(); //join이 없다면 작업쓰레드 종료 기다리지않고 메인쓰레드 종료
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("합계 : " + sum.getSum());

    }
}

class ExamThread extends Thread{

    boolean stop = false;
    boolean work = true;

    @Override
    public void run() {
        while(!stop){
            if(work){
                System.out.println("쓰레드 작업 : " + this.getName());
            } else{
                System.out.println("대기상태");
                Thread.yield();
            }
        }
    }
}

class SumThread extends Thread{

    private int sum = 0;

    public int getSum() {
        return sum;
    }


    @Override
    public void run() {
        for(int i=1; i<=100; i++){
            sum += i;
        }
    }
}
