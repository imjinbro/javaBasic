package com.jinbro.basic.object;

/*
    [OOP]
    - 단순히 논리 분할을 넘어서서 프로그램의 기능을 위해서 객체가 어떤 행동을 한다 수준까지 발전
    - 객체 간의 협력이라는 관점으로 프로그래밍함

    [객체]
    1) 프로그램을 구성하는 구성원
    2) 관계지향적(협력하기도하고 밀접하게 연관되기도하고), 고유하고 개인적임(상태, 행동), 종류로 묶여짐(타입)


    [자바에서 OOP]
    1) 클래스(인터페이스)는 자바에서 객체지향 프로그래밍을 하기위한 도구
    - 추상화 도구 : A라는 객체는 이런 상태와 메서드를 가진다(인터페이스는 메서드만), 분류 카테고리 만들기, 모델링
    - 실체화 : A타입 인스턴스, 구체적인 상태값(private)을 가짐, 카테고리에 속하는 실체
    - 코드 : Member jinbro = new Member(), Member 타입인 새로운 인스턴스를 생성해서 jinbro Member 타입 변수에 인스턴스 주소값 저장




    [잘못된 지식]
    1) 붕어빵틀 붕어빵 = new 붕어빵틀() - (X)
    - 붕어빵은 붕어빵이다 : 붕어빵이라는 타입이 있고, 그 타입에 속한 인스턴스가 있는 것
    - 붕어빵 학교앞붕어빵 = new 붕어빵() - (O)
 */

public class WhatIsObject {
    public static void main(String[] args) {
        Mouse miki = new Mouse("미키", 85);
        Mouse jerry = new Mouse("제리", 73);

        System.out.println(Mouse.getCountOfTail());
    }
}

class Mouse{
    private String name;
    private int age;

    /* T자 메모리 : 공통된 값을 가지는 속성이라 static 영역에 할당 - 저장되도록, 인스턴스면 힙에 하나씩 다... */
    private static int countOfTail = 1;

    public Mouse(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static int getCountOfTail() {
        return countOfTail;
    }

    void sing(){

    }
}



