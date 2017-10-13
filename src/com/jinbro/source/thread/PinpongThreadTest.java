package com.jinbro.source.thread;

public class PinpongThreadTest {
    public static void main(String[] args) {
        /*
            데이터 생산자가 데이터를 1번 쓰고, 소비자가 1번 읽고 번갈아가면서 그래서 핑퐁!
            1) 두 쓰레드가 정확히 공유 자원을 번갈아가면서 쓰기위해
            2) 두 쓰레드는 두 기능일 수도, 두 접근일 수도...
        */
        DataBox db = new DataBox();
        Producer pd = new Producer(db);
        Consumer cs = new Consumer(db);

        pd.start();
        cs.start();
    }
}


class DataBox {

    private int data =0;

    synchronized public int getData() { //객체가 잠금됨

        System.out.println("Consumer Thread : " + this.data);
        notify();

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return data;
    }

    synchronized public void setData(int data) {
        this.data = data;
        System.out.println("Producer Thread : " + this.data);

        /* getData 사용할 수 있도록 일시정지상태로 */
        notify();
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Producer extends Thread {

    private DataBox db;

    public Producer(DataBox db){
        this.db = db;
    }

    @Override
    public void run() {
        for(int i=0; i<5; i++){
            //데이터를 5번씀
            db.setData(i);
        }
    }
}

class Consumer extends Thread {
    private DataBox db;

    public Consumer(DataBox db){
        this.db = db;
    }

    @Override
    public void run() {
        for(int i=0; i<5; i++){
            db.getData();
        }
    }
}