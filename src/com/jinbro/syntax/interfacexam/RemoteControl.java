/*
    [인터페이스]
    1) 객체간의 상호작용 요청 목록, "인터페이스를 구현하는 객체는 이런 책임들을 진다"는 목록
    2) 이런 것을 할 수 있어 그러니 요청해(인터페이스) 목록
    - 이름 표준화
    - 내부만 변경하면 됨 : 구현부만 변경
    - 자료구조에서 표준 타입으로 관리할 수 있음

    3) 인터페이스와 implements 클래스 == 다형성
    - 객체 다형성 : 다양한 역할을 할 수 있음, 손님인 동시에 바리스타도 될 수 있음
    - 메서드 다형성 : 구현객체에 따라 메서드 동작은 다양함, 선언부는 같지만
    - 필드 다형성 : 표준화된 타입(interface)로 선언, 참조하는 구현객체에 따라 실행 결과 다양
    - 매개변수 다형성 : 표준화된 타입(interface)로 선언, 인자 구현객체에 따라 실행 결과 다양
    => 자동형변환됨, 자동형변환 이후 캐스트 연산자로 다시 형변환할 수 있음 : 구현객체에만 존재하는 메서드 사용가능해짐
    => 다시 형변환을 할 때 instanceof를 사용하는 것이 좋음 : 자동형변환 된다는 것과 형변환 다시 할 수 있음을 알자

    4) 자바8 인터페이스의 변화 : 인터페이스 목록 작성 뿐 아니라 구현 메서드까지 가질 수 있게됨
    - 추상메서드만 가질 수 있었지만 default, static 메서드를 가질 수 있음 : 뭔지는 아래 보기
    - 틀이 아닌 구현까지
    => 추상클래스 vs 인터페이스1: http://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
    => 추상클래스 vs 인터페이스2 : https://okky.kr/article/352436
    => 추상클래스 - is a / 인터페이스 : has a


    [인터페이스로 작성할 수 있는 코드]
    1) 상수
    - 컴파일 타임에서 생성되고 초기화되는 상수만 선언가능

    2) abstract 메서드
    - 선언만하고 구현부는 없음
    - implements 클래스에서 메서드 동작 구현해야함 : 다형성

    3) default 메서드
    - abstact와 다르게 인터페이스에서 구현할 수 있음 : abstract가 아니라서 구현 클래스에서 따로 구현X
    - 인스턴스 메서드기 때문에 구현객체(implements 클래스 인스턴스)가 있어야 사용가능
    - 사용할 구현객체만 호출해서 쓰면 됨 : 인터페이스 구현 클래스 모두 코드 변경하지않아도 됨
    - 자바8부터 작성할 수 있음

    4) static 메서드
    - 인스턴스를 생성하지않고 인터페이스 메서드(공용 메서드)를 사용할 수 있음
    - 자바8부터 작성할 수 있음


    [인터페이스를 만들고 써보자]
    1) implements 클래스 : 객체타입 역할 부여, 메서드 내부 구현 - 잊지말자 다형성
    (코드)
    - default 메서드 : 인터페이스 구현객체가 기본적으로 가지는 메서드(인스턴스 메서드 == 구현객체가 있어야함)
    => 오버라이딩 가능

    - static 메서드 : 인터페이스명.메서드명으로 사용가능(구현하지않은 클래스에서도 사용가능함)
    - abstact 메서드 : implements 클래스에서 구현하기
    - 인터페이스형 변수 : 타입 표준화 - 다형성
    1) 구현객체 주소값 저장
    2) 클래스 내에서 필드, 매개변수, 로컬변수 사용


    2) 익명 구현
    (코드)
    - 임시(1회성)로 사용하면서 쓰는 곳마다 처리가 다를 때 : 영구적으로 구현해야하는 경우말고
    1) 임시 작업 쓰레드 만들기 : Runnable
    2) UI 이벤트 처리

    - implements 클래스 만들지않고 즉시 구현하고 사용
    - 자바8 람다식에서 사용한다고하는데 뒤에서 만나겠지!
    - 일반클래스 : extends 클래스없이 사용할 수 있지만 굳이..?
    - 컴파일러에 의해서 .class 파일이 생성됨 : 익명구현사용클래스명$번호.class


    [인터페이스 작성시 알아둬야할 것]
    1) public static final을 생략하더라도 컴파일 과정에서 자동적으로 붙음
    2) abstract를 생략하더라도 추상메서드라면 컴파일 과정에서 abstract 붙음
    3) 메서드는 기본적으로 public을 가지기때문에 더 좁은 접근제어자 사용x
    4) 객체는 여러개의 역할을 할 수 있음 : interface로 다중상속을 받을 수 있음
    - 이름 중복 조심

    5) 인터페이스는 인터페이스끼리 상속 구현됨 : 여러 인터페이스를 상속할 수 있음
    - 구현객체는 인터페이스의 모든 메서드를 구현해야함
    - 상위 인터페이스를 변수 타입에 구현객체 주소값을 저장할 경우 하위인터페이스 메서드를 사용할 수 없음
    (코드)

 */

package com.jinbro.syntax.interfacexam;

class RemoteControlExample {
    public static void main(String[] args) {
        /* RemoteControl 변수 => TV 인스턴스 주소값 저장(자동형변환 - 다형성) */
        RemoteControl tv = new TV();
        exam(tv); // TV의 run - 다형성
        if(tv instanceof TV){
            TV reTv = (TV)tv;
            reTv.castExam();
        }


        RemoteControl radio = new Radio();
        radio.tooltip(); // default method custom
        exam(radio); // Radio의 run - 다형성

        RemoteControl.hi(); //static


        RemoteControl[] rcs = {
            new TV(),
            new Radio()
        };

        for(RemoteControl rc : rcs){
            rc.turnOff();
        }
    }

    public static void exam(RemoteControl rc){
        /* 매개변수 다형성 */
        rc.turnOff();

        if(rc instanceof TV){
            System.out.println("TV 객체 임다");
        } else {
            System.out.println("Radio 객체 임다");
        }
    }

}

public interface RemoteControl {

    public default void tooltip(){
        System.out.println("this is tooltip!");
    }

    public static void hi(){
        System.out.println("hi");
    }

    public abstract void turnOff();
}

interface Searchable {
    public abstract void turnOff();
}

class TV implements RemoteControl, Searchable{

    @Override
    public void turnOff() {
        System.out.println("TV turn off");
    }

    public void castExam(){
        System.out.println("TV로 형변환됨 : 그래서 이 메서드 호출할 수 있었음");
    }

    /* TV는 tooltip(default method)를 구현하지않음 : default라서 */
}

class Radio implements RemoteControl{

    @Override
    public void turnOff() {
        System.out.println("Radio turn off");
    }

    @Override
    public void tooltip() {
        System.out.println("Radio : this is custom default method!!");
    }
}


/* 1회성 인터페이스를 구현하는 클래스를 만들지않고 1회용 객체 만들어 사용하기 */
class Anonymous {
    public static void main(String[] args) {
        RemoteControl rc = new RemoteControl() {
            @Override
            public void turnOff() {
                System.out.println("시스템을 종료합니다");
            }
        };
        rc.turnOff();
        rc.tooltip(); //interface instance method

    }
}
