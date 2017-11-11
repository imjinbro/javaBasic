package com.jinbro.basic.memory;

/*
    [자바 프로그래밍]
    1) jdk로 자바 프로그램 개발 : api, javac, jvm(개발 테스트)
    2) jre가 설치된 환경에서 자바프로그램 실행 : jvm
    - 운영체제로부터 메모리 할당받고 jvm T메모리 구조 만듬


    [jvm 메모리 구조]
    1) T 메모리구조
    - static : 클래스 - JVM 자바프로그램 실행 전 java.lang 패키지 클래스, import 자바 클래스, 사용 class 정보 로딩
    - stack : 메서드 - 메서드 스택 프레임 생성, 인자 공간 할당, 지역변수 공간 및 값 할당
    - heap : 인스턴스 - 생성된 객체가 저장된 공간


    [static - 클래스]
    1) 메모리 스태틱 영역 : 해당 클래스가 사용되기 시작하는 부분에서 로딩 프로그램 종료될 떄까지는 그대로 로드된 상태
    - 자바 기본 패키지(java.lang) 로드
    - 자바 프로그램에서 사용하는 패키지(클래스) 로드
    - 클래스 변수/메서드 로드 : 인스턴스 메서드는 메모리 절약을 위해서 1개만 생성함, this를 넘겨받도록 코드 변경됨


    [stack 영역 - 스택프레임]
    1) 메모리 스택영역
    2) 스택프레임이란
    - 메서드 고유 공간
    - 메서드 간의 데이터 공유 방법은 파라미터, 리턴으로 : primitive / reference type에 따라 다름
    - 전역변수도 데이터 공유 방법이긴 하지만 남발하면 독이됨 : 변경시키는 코드들을 모두 쫓아가야함, 상수값 지정하는 것이

    3) 타입에 따라 다른 데이터 주고 받기 : call by value, call by reference
    - primitive : 값 복사, 전혀 다른 값(call by value)
    - reference : 주소 복사, 같은 객체(call by reference)
    (1) 레퍼런스 타입 변수에 null이나 다른 주소값을 저장한다고하더라도 이전 인스턴스가 null이 되거나 하지않음
     T a = new T();
     T b = a; //주소값을 복사한 것임, call by reference와 value의 차이는 어떤 값이냐지 다른 과정이 아님
     a = null; //a 변수에 아무것도 저장하지않는다는 것일 뿐 저장되어있던 객체주소가 가리키는 객체가 사라지지않음
     b.toString(); //멀쩡하게 출력



    [멀티쓰레드 환경 T메모리]
    1) 스택 영역
    - N개 쓰레드만큼 N개로 분할해서 사용함

   2) 스태틱 영역, 힙 영역
   - 쓰레드 공유 : 쓰레드 간 데이터 공유
   - 위험성 : 스태틱, 힙을 공유하기때문에 A쓰레드가 데이터를 변경하고 있는데, B쓰레드가 접근한다면 처음 데이터와는 다른 값을 가져올 수도
   - 위험성때문에 멀티쓰레드 처리를 구현할 때 공유 데이터를 사용한다면 synchronized 처리를 확실하게

   0) 멀티프로세스는 T메모리 구조를 N개 프로세스만큼 가짐
   - 프로세스는 프로그램을 별개로 실행한 형태 : 고유의 메모리공간, 데이터 주고받기 불가능
   - 일부 다중처리를 위해서 멀티프로세스를 한다면 낭비
 */
public class TStructure {

    private static final int GV = 10;
    //static int gv;

    public static void main(String[] args) {
        Test t = new Test("jinbro");
        int data = 10;

        change(t, data);

        System.out.println("main stack frame : " + t.name);
        System.out.println("main stack frame : " + data);



    }

    /* change(call by reference : 주소값 복사, call by value : 값만 복사) */
    static void change(Test t, int target){
        t.name = "안녕";
        target += 10;

        System.out.println("change stack frame : " + t.name);
        System.out.println("change stack frame " + target);
    }

}


class Test {
    String name;

    Test(String name) {
        this.name = name;
    }
}