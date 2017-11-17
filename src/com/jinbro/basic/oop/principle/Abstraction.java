package com.jinbro.basic.oop.principle;
/*
    [추상화]
    - 프로그램에 필요한 역할을 만듬 : 객체를 타입화(그룹화)시킴
    - 프로그램에서 필요한 속성, 메서드으로만 구성
    - 자바 : class, interface(인터페이스는 메서드만)

    - 추상화가 없었다면? 지동차로 지칭할 수 있는 것을 각각의 개체로 따로 지칭하는 코드를 작성해야함

 */
public class Abstraction {

}

abstract class Transportation{
    abstract void drive();
}

class Bus extends Transportation{
    @Override
    void drive() {

    }
}

class Subway extends Transportation{
    @Override
    void drive() {

    }
}
