package com.jinbro.basic.oop.designPattern.creational;

import java.util.LinkedList;
import java.util.Queue;

/*
    [싱글턴 패턴 - 인스턴스 생성 과정을 캡슐화, 공유 인스턴스 만들기]
    - 리소스를 공유하는 개념 : 자리마다 프린터를 놓지않고 네트워크를 형성해서 하나의 프린터를 공유함
    - (멀티쓰레드 + 공유 객체)를 다룰 때 의도치않은 결과가 나올 수 있음
    (1) 프린터 인스턴스가 여러개 생성되거나
    (2) 상태값이 중간에 변경되어 의도치않은 방향으로 흘러가거나
    (3) 해결 : 인스턴스를 상태값(null 체크)에 의해 생성하지않고 미리 생성(클래스 변수)해놓거나 메서드를 synchronized 선언해두거나

 */
public class Singleton {

    private static final int NUMS = 5;

    public static void main(String[] args) {
        TicketManager manager = TicketManager.getManager();
        manager.setLIMIT(3);

        Customer[] customers = new Customer[NUMS];
        for(int i=0; i<NUMS; i++){
            customers[i] = new Customer();
            customers[i].start();
        }
    }
}

class User extends Thread{
    private String name;

    public User(String name) {
        super(name);
    }

    @Override
    public void run() {
        PrinterManager manager = PrinterManager.getManager();

    }
}

interface Printable {
    void print(String msg);
}

class ConsolePrinter implements Printable{
    @Override
    public void print(String msg) {
        System.out.println("[출력] : " + msg);
    }
}

class PrinterManager{
    private static PrinterManager manager = new PrinterManager();
    private PrinterManager() { }

    private static final int LIMIT = 3;
    private static Queue<Printable> printers = new LinkedList<>();

    public synchronized static PrinterManager getManager(){
        if(manager == null){
            for(int i=0; i<LIMIT; i++){
                printers.add(new ConsolePrinter());
            }
        }
        return manager;
    }

    public synchronized void getPrinter(){

    }

    public boolean isAvaliable(){
        return printers.isEmpty();
    }
}

/* 싱글턴 대신에 정적클래스(static 메서드, 필드) 사용 : cnt static 필드 컴파일 타임에 바인딩 */
class Scanner{
    private static int cnt = 0;

    public synchronized static void scan(String target){
        System.out.println("[스캔 완료] : " + cnt);
    }
}


class Customer extends Thread{

    private Ticket myTicket;

    @Override
    public void run() {
        TicketManager manager = TicketManager.getManager();
        myTicket = manager.getTicket();
        System.out.println("[티켓번호] : " + myTicket.getSerialNum());
    }
}

class TicketManager{
    private static TicketManager machine = new TicketManager();

    private static int LIMIT;
    private static int cnt = 0;

    private TicketManager(){ }

    public static TicketManager getManager(){
        return machine;
    }

    public synchronized void setLIMIT(int LIMIT) {
        TicketManager.LIMIT = LIMIT;
    }

    public synchronized Ticket getTicket(){
        Ticket ticket;

        if(LIMIT == cnt){
            ticket = new NullTicket();
        } else {
            ticket = new NormalTicket(++cnt);
        }

        return ticket;
    }
}

interface Ticket{
    public int getSerialNum();
}

class NormalTicket implements Ticket{
    private int serialNum;

    public NormalTicket(int serialNum) {
        this.serialNum = serialNum;
    }

    @Override
    public int getSerialNum() {
        return serialNum;
    }
}

class NullTicket implements Ticket{
    @Override
    public int getSerialNum() {
        return 0;
    }
}

