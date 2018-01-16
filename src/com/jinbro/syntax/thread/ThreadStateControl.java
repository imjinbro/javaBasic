package com.jinbro.syntax.thread;
/*
    [쓰레드 상태제어 메서드]
    0) 상태 : NEW - Runnable <--- (일시정지될수도) ---> Running - Terminated
    1) sleep
    2) yield
    3) join
    4) notify(notifyAll), wait
    5) stop flag 사용 혹은 interrupt

*/
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



        /*
            [interrupt]
            - 쓰레드 정상타임이 아닌 비정상타임에 정상적으로 종료를 하기위해서 사용하는 메서드
            - 쓰레드가 일시정지 상태이어야함 : 대기나 실행상태일 때는 발생하지않음 - 그래서 Thread run에서 Thread.sleep을 넣음
            - 일시정지 상태였을 때 적용되는 것이지만 interrupt 한번 시켜놓으면 interrupt 대기 중 : 일시정지 되는 즉시 정지
            - Thread.interrupted() : 쓰레드 정적 메서드로 쓰레드 객체 내부에서 사용하면 됨, 적용된 상태일 때에 run 종료되도록 해도 됨
            - thC.isInterrupted() : 쓰레드 객체가 인터럽트 적용된 상태인지 알 수 있음(일시정지 상태가 아니더라도 적용되었는지)
        */
        Thread thC = new ThreadC();
        thC.start();

        //1초 후 종료
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thC.interrupt();


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

/* interrupt 사용해보기위한 쓰레드 */
class ThreadC extends Thread {

    @Override
    public void run() {
        try{
            while(true){
                if(Thread.interrupted()){
                    break;
                }

                System.out.println("실행 중");
                Thread.sleep(100);
            }
        } catch(InterruptedException e){
            e.printStackTrace(); //로그에
        }

        System.out.println("쓰레드 종료");
    }
}
