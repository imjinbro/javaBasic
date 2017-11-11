package com.jinbro.basic.object; /* 네임스페이스 생성 : 같은 클래스명이지만 패키지명이 다르면 다른 클래스로 */

/*
    [OOP]
    - 단순히 논리 분할을 넘어서서 프로그램의 기능을 위해서 객체가 어떤 행동을 한다 수준까지 발전
    - 객체 간의 협력이라는 관점으로 프로그래밍함

    [객체]
    1) 프로그램을 구성하는 구성원
    2) 관계지향적(협력하기도하고 밀접하게 연관되기도하고), 고유하고 개인적임(상태, 행동), 종류로 묶여짐(타입)


    [자바에서 OOP]
    1) 추상 : 클래스(인터페이스)는 자바에서 객체지향 프로그래밍을 위한 객체 타입 추상화도구
    - 추상화 도구 : A라는 객체는 이런 상태와 메서드를 가진다(인터페이스는 메서드만), 분류 카테고리 만들기, 모델링
    - 실체화 : A타입 인스턴스, 구체적인 상태값(private)을 가짐, 카테고리에 속하는 실체
    - 코드 : Member jinbro = new Member(), Member 타입인 새로운 인스턴스를 생성해서 jinbro Member 타입 변수에 인스턴스 주소값 저장
    - 인스턴스 : 분류된 타입의 실체 존재인데, 유일하다 그러니 이름 지을 때 딱 그렇게 지어서 쓸 것(위처럼)
    - 종류
    (1) 일반 클래스
    (2) abstract 클래스
    - abstract 메서드를 가짐, 상위 클래스의 인스턴스를 생성하지않고 extends 상위클래스 역할만 할 것이면 abstract가 깔끔

    (3) interface
    - abstract 클래스에서 필드를 뺀 것 : is able to에만 집중, 상수 필드는 가질 수 있음
    - 기능 타입화


    2) 상속(extends) : 분류를 세분화시키는 것
    - 동물 > 포유류, 조류 : 분류(class)된 것을 더 세분화시킴
    - inheritance(상속)이 아니라 extends(확장)이다
    - 상속관계에서 만족해야하는 문장 : 하위클래스는 상위클래스다(is a kind of, 객체지향 설계 리스코프 치환 원칙)
      Animal tiger = new Mammals() : Mammals는 Animal 성립 - Animal 타입 변수에 인스턴스 주소값 저장 가능
      Mammals animal = new Animal() : 성립x - 저장불가

    - 최상위 타입 : Object - Object의 특성을 물려받으면서 확장해나간다
    - 더이상 확장하기 않기 : class 앞 final 키워드 사용
    - 상위타입변수에 저장된 인스턴스 주소값이 어떤 하위 클래스인지 판별하기 : instanceof


    3) interface : 다중상속은 지원하지않지만 다중상속의 장점은 가져간다
    - 다중상속(extends)를 지원하면 같은 이름을 가진 메서드인 경우(오버라이딩x) 어떤 것을 실행해야할지
    - 대신 interface : 구현클래스 is able to 인터페이스, 인터페이스 할 수 있는
      자바 api : Serializable, Cloneable, Comparable, Runnable

    - 기능의 분류화 : ~할 수 있는(기능)
    - 상속 개념이 아니라 ~할 수 있는 기능 장착(implements 도구, 구현) 개념
    - 인터페이스타입변수에 인터페이스 구현 클래스의 인스턴스를 생성해서 레퍼런스값을 저장할 수 있음 : 기능의 분류
      Flyable 참새 = new Bird();

    - 인터페이스 타입변수에 저장된 인스턴스 주소값이 어떤 구현객체인지 판별하기 : instanceof

    4) 다형성 : 사용 객체에 따라 여러가지 형태를 띄며 동작
    - 오버라이딩, 오버로딩 : 프로그램 개발할 때 편의성 제공
    (1) 변수 타입 표준화할 수 있음 : 상위클래스타입 변수에 하위 클래스 인스턴스를 저장할 때 오버라이딩, 오버로딩한 메서드가 호출됨
    (2) 파라미터에 따라 새로운 이름을 가진 메서드 정의 하지않아도 됨

    - 상위클래스를 extends하는 하위클래스가 여러개 : 각각 다르게
    - 인터페이스를 implements하는 구현클래스가 여러개 : 각각 다르게


    5) 캡슐화 : 객체 간에는 협력적인 관계지만, 객체 존재는 독립적인 존재
    - 상태를 외부 객체에 공개하지않음 : 메서드의 연산에 따라 상태가 변경될 수도 있음
    - 인스턴스의 상태값에 따라 외부 인스턴스의 협력 요청을 처리할지 안할지를 따질 수 있음 : 스스로 결정
    - 자바에서는 private, protected, default, public 접근제어자로 이를 지원함
    (1) 공개해도 되는 것과 하지말아야할 것 구분
    (2) 어떤 관계에 따라 조건적으로 공개해야할 것을 구분 : 상위, 하위 관계라면...


    [잘못된 지식]
    1) 붕어빵틀 붕어빵 = new 붕어빵틀() - (X)
    - 붕어빵은 붕어빵이다 : 붕어빵이라는 타입이 있고, 그 타입에 속한 인스턴스가 있는 것
    - 붕어빵 학교앞붕어빵 = new 붕어빵() - (O)


    2) 우리가 알고 있는 상속은 상속(inheritance)이 아니라 확장(extends)이다
    - 상위 클래스가 가진 속성과 동작을 가지면서도 새로운 속성 동작을 가지는 분류를 만든 것 : 상속과 확장
    - 할아버지 > 아버지 이런 상속이 아니라 동물 > 포유류 같이 분류의 세분화이다

 */

public class WhatIsObject {
    public static void main(String[] args) {
        /* 클래스가 처음 사용될 때 로딩 : static 영역 */
        System.out.println("메인 메서드 시작");

        Mouse miki = new Mouse("미키", 85);
        Mouse jerry = new Mouse("제리", 73);

        System.out.println(Mouse.getCountOfTail());
    }
}

class Mouse{

    static {
        System.out.println("Mouse 클래스 로딩");
    }

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
        System.out.println(name +" 노래를 부릅니다");
    }
}

/*
    [우리가 아는 상속은 inheritance가 아니라 확장 extends(+ 세분화) 개념]
    - Mammals는 Animal이다(O) : 항상 그렇다, 확장된 분류고, 세분화된 분류다
    - Animal은 Mammals다(X) : 항상 그렇지않다.
*/
class Animal{

    public static void main(String[] args) {
        /*
            [Mammal은 Animal다 그래서 Mammals 인스턴스는 Animal 타입 변수에 주소값 저장이 가능하다]
            - T 메모리 상(힙)에는 Animal 인스턴스 생성 -> Mammals 인스턴스 생성 : 2개 인스턴스(상-하위)가 생성됨
            - tiger(스택) 변수는 Animal 인스턴스를 가리킴 : Animal 타입 인 것만 인지 - 캐스팅 연산해줘야 Mammals로
        */
        Animal tiger = new Mammals();
        Animal gugu = new Bird();


        /* 변수 타입은 Animal이지만 확장시킨 클래스 오버라이딩 메서드가 동작 */
        tiger.feed();
        gugu.feed();

    }

    void feed(){
    }
}

class Mammals extends Animal {

    @Override
    void feed() {
        /* 상위클래스 Animal이 가진 메서드를 상속 */
        System.out.println("포유류가 밥을 먹슴다");
    }
}


interface Flyable{
    void fly();

    final int HEIGHT = 100;
}


class Bird extends Animal implements Flyable {

    @Override
    void feed() {
        System.out.println("조류가 밥을 먹슴다");
    }

    @Override
    public void fly() {
        // HEIGHT = 300; 안됨 상수임
        System.out.println(Flyable.HEIGHT +"m 높이로 날고 있습니다");
    }
}


abstract class Alphabet {
    /* interface와는 다른 점 */
    private String name = "알파벳";

    abstract void sayMyName();
    public String getName() { return name; }
}

class A extends Alphabet{

    public A() {
        //super(); 가 생략되어져있음
    }

    @Override
    void sayMyName() {
        System.out.println(super.getName() + " A 입니다");
    }
}

