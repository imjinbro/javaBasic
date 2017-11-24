package com.jinbro.basic.oop.rule;

import java.util.ArrayList;
import java.util.Calendar;

/*
    [개방폐쇄원칙]
    - 단일책임원칙에서 잠깐 배운 산탄총수술(1개의 책임이 여러 곳에 분산된 형태)에도 녹아있음
    - 객체 모델링을 할 때 변경될 여지가 많은 부분은 모듈화(객체화 - 확장에 대비하기)시켜서 변경에 영향을 받지않도록 모델링하기

    - 예시 : 등급이 변경되더라도 Customer는 신경쓰지않아도됨
    - 변경에 유연한 시스템을 만들자
 */
public class OpenClosed {

}

class Customer{
    //private String grade;
    private Grade grade;
}


abstract class Grade{ }
class VIP extends Grade{ }
class Ordinary extends Grade{ }



class TimeReminder {
    private HourProvider hourProvider;

    public void reminder(){
        int hour = hourProvider.getHour();

        if(hour >= 22){

        } else {

        }
    }
}

interface HourProvider{
    void setHour(int hour);
    int getHour();
}

class FakeHourProvider implements HourProvider{

    private Calendar calendar;

    public FakeHourProvider() {
        calendar = Calendar.getInstance();
    }

    @Override
    public void setHour(int hour) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
    }

    @Override
    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}


class RealHourProvider implements HourProvider{

    private Calendar calendar;

    public RealHourProvider() {
        calendar = Calendar.getInstance();
    }

    @Override
    public void setHour(int hour) {
        System.out.println("설정되어있습니다.");
    }

    @Override
    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}




abstract class Print {
    abstract void print(int num);
}

class Console extends Print{

    @Override
    void print(int num) {
        System.out.println(num);
    }
}

class PayManager{
    //private ArrayList<Employee> employees; 변할 수도 있는 클래스와 직접 의존하고 있어서
    private ArrayList<Payable> payables;
    private Print printer;

    public PayManager(ArrayList<Payable> payables) {
        this.payables = payables;
    }

    public void setPrinter(Print printer) {
        this.printer = printer;
    }

    public void printPayList(){
        if(printer != null) {
            for (Payable payable : payables) {
                printer.print(payable.calcPay());
            }
        }
    }
}

interface Payable{
    int calcPay();
}


class Employee implements Payable{
    private PayCalculator payManager;

    private String id;
    private String name;

    private int workHours;
    private int overTimeHours;


    public void setPayManager(PayCalculator payManager) {
        this.payManager = payManager;
    }

    public int calcPay(){
        return payManager.calcPay(this);
    }
}


abstract class PayCalculator{
    abstract int calcPay(Employee emp);
}

class ACompanyPayCalculator extends PayCalculator{

    @Override
    int calcPay(Employee emp) {
        /* 우리 회사(A)만의 방식으로 */
        return 0;
    }
}

class BCompanyPayCalculator extends PayCalculator{

    @Override
    int calcPay(Employee emp) {
        /* 우리 회사(B)만의 방식으로 */
        return 0;
    }
}

