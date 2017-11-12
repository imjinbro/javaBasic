package com.jinbro.basic.principleoop;

/*
    [자바]
    - 객체지향 프로그래밍 도구


    [객체지향 프로그램 설계를 위한 원칙]
    - 더 객체지향답게 설계하기위한 원칙
    - 5가지 원칙 : SOLID

    1) SRP(Single Responsibility Principle)
    - 여러가지 역할을 하는 클래스를 1가지 역할만 하는 클래스로 분할하기 : 추상화 과정
    - 클래스, 속성, 메서드 적용 : 1가지를 넘어가는 존재 이유를 가지고 있으면 안됨
    - 리팩토링할 때 기능에서 특정 역할 부분이 잘못되었다면 그 부분을 담당하는 곳만 조지면 됨


    2) OCP(Open Close Principle)
    - 자신이 확장되는 것은 되나 외부 변경이 되도록 해서는 안되게 설계하기
    - 타입 계층화(표준화 extends implements) 해야함에도 불구하고 단일 클래스로 사용하면 안됨
    - 예시 : JDBC
    (1) JDBC를 사용하는 클라이언트는 어떤 데이터베이스든 간에 같은 동작을 사용
    (2) JDBC 인터페이스(매니저)가 있기때문에 어떤 데이터베이스든 간에 동작은 똑같이 사용



 */


public class PrincipleMain {
    public static void main(String[] args) {

    }
}
