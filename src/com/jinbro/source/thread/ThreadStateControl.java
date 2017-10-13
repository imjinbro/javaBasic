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
            //sleep : 실행 중인 쓰레드 일시정지 상태 : 시간이 지나면 RUNNABLE 쓰레드
            Thread.sleep(100);
        }catch(InterruptedException e){
            //일시정지 상태에서 RUNNABLE 상태로 가기 전에 Thread객체.interrupt()가 호출되면 실행
            e.printStackTrace();
        }

        //yield : 실행대기상태로, thread2만 실행
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


        /*
            [wait, notify, notifyAll] : 일시정지상태와 실행가능상태로 확실하게 쓰레드 교대 실행하게끔하기, Object 메서드임
            1) 공유객체의 동기화 메서드나 동기화 블록 내에서만 사용할 수 있음
            - 공유객체의 자원(동기화)을 사용할 때 A쓰레드가 사용하고 있으면 B쓰레드가 사용못함 : 번갈아가면서 작업할 수 있도록
            - wait, notify는 확실하게 번갈아가면서 1번씩 작업할 수 있도록, 공유 객체의 자원을
         */
        WorkObject shared = new WorkObject();
        ThreadA thA = new ThreadA(shared);
        ThreadB thB = new ThreadB(shared);

        thA.start();
        thB.start();

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

class WorkObject {

    synchronized void methodA(){
        System.out.println("Thread A 처리");
        notify(); // 일시정지 상태인 Thread B 쓰레드를 Runnable 상태로

        try {
            wait(); // Running 상태인 Thread A 쓰레드를 일시정지 상태로
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void methodB(){
        System.out.println("Thread B 처리");
        notify();

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadA extends Thread{

    private WorkObject obj;

    ThreadA(WorkObject obj){
        this.obj = obj;
    }

    @Override
    public void run() {
        for(int i=0; i<5; i++){
            obj.methodA();
        }
    }
}

class ThreadB extends Thread{

    private WorkObject obj;

    ThreadB(WorkObject obj){
        this.obj = obj;
    }

    @Override
    public void run() {
        for(int i=0; i<5; i++){
            obj.methodB();
        }
    }
}
