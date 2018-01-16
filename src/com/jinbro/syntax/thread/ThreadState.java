package com.jinbro.syntax.thread;

public class ThreadState {
    public static void main(String[] args) {
        ObserverThread observer = new ObserverThread(new TargetThread());
        observer.start();
    }
}
class TargetThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0; i<10000000; i++){ }
    }
}


class ObserverThread extends Thread{

    private Thread target;//디펜던시

    public ObserverThread(Thread target) {
        this.target = target;
    }

    @Override
    public void run() {
        System.out.println("옵저버 시작");

         while(true){
             if(target.getState() == State.NEW){
                 System.out.println("타겟 쓰레드가 생성되었습니다(RUNNABLE)"); //객체생성
                 target.start();
             } else if(target.getState() == State.RUNNABLE){
                 System.out.println("타겟 쓰레드가 대기상태로 전환됨");
             } else if(target.getState() == State.TERMINATED){
                 System.out.println("타겟 쓰레드가 종료되었습니다");
                 break;
             }

             /*
                [또다른 상태 - 일시정지 관련]
                1) State.WAITING : 다른 쓰레드가 통지할 때까지 기다리는 상태(????)
                2) State.TIMEDWAITING : 주어진 시간동안 기다리는 상태(Thread.sleep에 의해)
                3) State.BLOCKED : 사용하고자 하는 객체의 락이 풀릴 때까지 기다리는 상태(synchronized 영역 다른 쓰레드 접근 - 공유객체 사용)
              */
         }

         System.out.println("옵저버 끝");
    }
}
