package com.jinbro.syntax.thread;

public class MultiThreadTest {
    public static void main(String[] args) {
        Calc calc = new Calc();

        //1000으로 설정했지만 500으로 출력 : 같은 객체 참조 - user2가 변경한 값이...
        User jinbro = new User();
        jinbro.setCalc(calc, 10);
        jinbro.start();

        User jinhyung = new User();
        jinhyung.setCalc(calc, 1000);
        jinhyung.start();

    }
}

class Calc {

    private int num = 0;

    synchronized void setNum(int data){ //synchronized 선언과 그렇지않을 때 차이
        num = data;

        try{
            //쓰레드를 잠시 중단시킴, 그사이 다른 쓰레드가 와서 세팅하고있음
            Thread.sleep(2000);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }

        /* 동기화블록, this는 동기화 대상 객체(공유 객체), 블록 내는 1개의 쓰레드 처리가 완료되어야 다른 쓰레드가 객체 접근, 블록 실행
        synchronized (this){
            num = data;

            try{

            } catch{

            }

            System.out.println
        } */

        System.out.println(Thread.currentThread().getName() + " : " + num);
    }

    /* 얘는 어떻게 처리되어야할까 : set과 get 처리는 별개라 */
    int getNum() {
        return num;
    }
}

class User extends Thread{

    private Calc calc;
    private int num;

    void setCalc(Calc calc, int num){
        this.calc = calc;
        this.num = num;
    }

    @Override
    public void run() {
        calc.setNum(num);
    }
}
