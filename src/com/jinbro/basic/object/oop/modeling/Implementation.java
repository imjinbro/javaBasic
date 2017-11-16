package com.jinbro.basic.object.oop.modeling;

/*
    [인터페이스 - 실체화 관계]
    - 인터페이스 : 책임(역할)의 타입화
    - 공통되는 능력과 그 능력을 사용할 수 있는 객체 관계 : is able to 관계
 */
public class Implementation {
    public static void main(String[] args) {
        //다형성
        Flyable plane = new Plane();
        Flyable gugu = new Bird();

        plane.fly();
        gugu.fly();
    }
}

interface Flyable{
    void fly();
}

class Bird implements Flyable{
    @Override
    public void fly() {

    }
}

class Plane implements Flyable{
    @Override
    public void fly() {

    }
}



