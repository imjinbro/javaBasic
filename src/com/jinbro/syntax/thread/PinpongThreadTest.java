package com.jinbro.syntax.thread;

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


        /* 다른 예제 */
        Account account = Account.getAccount();

        Customer jinbro = new Customer("jinbro", account);
        Customer jinhyung = new Customer("jinhyung", account);
        jinbro.start();
        jinhyung.start();
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



class Account {
    private static Account account = new Account();
    private int money;

    private Account() {
        money = 1000000;
    }

    private int getMoney() {
        return money;
    }

    public synchronized void setMoney(String toName, int money) {
        notify(); //일시정지 상태인 쓰레드를 Runnable로(1개, 다수를 하려면 notifyAll)
        try {
            wait(); //Runnable 상태인 쓰레드를 일시정지 상태로
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.money = money;
        System.out.println("송금자 : " + toName + ", " + getMoney() + "원");
    }

    public static Account getAccount(){
        return account;
    }
}

class Customer extends Thread {

    private String name;
    private Account to;

    public Customer(String name, Account account) {
        this.name = name;
        this.to = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i += 100) {
            to.setMoney(name, i);
        }
    }
}