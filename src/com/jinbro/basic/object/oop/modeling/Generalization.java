package com.jinbro.basic.object.oop.modeling;

/*
    [일반화관계]
    - 한 클래스가 다른 클래스를 포함하는 상위 개념일 때 : 하위 is a kind of 상위
    - extends 로 관계표현
 */
public class Generalization {

}

abstract class HomeAppliances{
    private int serialNo;
    private String manufactuer;
    private int year;

    public HomeAppliances(int serialNo, String manufactuer, int year) {
        this.serialNo = serialNo;
        this.manufactuer = manufactuer;
        this.year = year;
    }

    abstract void turnOn();
    abstract void turnOff();
}

class Tv extends HomeAppliances{

    public Tv(int serialNo, String manufactuer, int year) {
        super(serialNo, manufactuer, year);
    }

    @Override
    void turnOn() {

    }

    @Override
    void turnOff() {

    }
}

class MicrowaveOven extends HomeAppliances{

    public MicrowaveOven(int serialNo, String manufactuer, int year) {
        super(serialNo, manufactuer, year);
    }

    @Override
    void turnOn() {

    }

    @Override
    void turnOff() {

    }
}