/*
    [객체지향과 상속]

    [상속 기본개념]
    (1) 상속이란? 객체 관계를 중심으로 설명
    - 객체 간의 관계를 설정하는 것 : 여러 관계가 있지만 서로 속하는 관계로 설정함
    - 서로 속한다 : 상위와 하위(파생, 포함)관계로 설정하는 것을 말함
    - 예 : 타이어 > 한국타이어, 금호타이어


    (2) 상속을 구체화한 자바 문법 : extends, implements, Override
    (3) 상속 범위
    - private를 제외한 접근제어자


    (4) extends를 사용해보자
    (코드 Account와 KBAccount)


    (5) super와 this의 차이
    - super : 자신의 상위 객체(타입 - 클래스)를 지칭
    1) super() - 생성자, 매개변수가 있을 땐 super(매개변수)
    2) super.필드/메서드

    - this : 자신을 지칭


    (6) 설계의 중요성
    - 설계가 정말로 중요함
    - 객체 간의 관계를 설정해두고 코딩을 합시다
    - 무작정 Cat, Dog, Lion... 만들기 시작하면 중복 코드 어쩔......


    (7) final 키워드의 의미와 final가 적용된 클래스와 메서드
    - final : 마지막!
    - final 클래스 : 아무런 관계도 맺지않는 객체 타입을 뜻함, 즉 상속하지않음
    - final 메서드 : 오버라이딩 허용하지않겠다는 것
    - final 클래스 예시 : 자바 표준 API - String, Integer 등 extends 할 수 없음


    (8) 다시 살펴보자 접근제어자
    - public < protected < default < private
    - 적용대상과 범위


    [객체지향과 상속]
    (1) 다형성과 상속(extends, implements, Override)
    - 다형성 : 하나의 타입이 여러 형태로 동작할 수 있다는 성질
    - 다형성
    1) 객체타입에서의 다형성 : 여러 역할을 가질 수 있음
    - extends, implements 사용
    - A 카페에서는 손님, B 카페에서는 바리스타


    2) 메서드의 다형성 : 같은 타입을 구현하는 객체타입이지만 각각 다르게 동작할 수 있음
    - 같은 Tire를 상속하고 Tire의 roll 메서드를 오버라이딩하지만 HankookTire, KumhoTire는 각각 다른 내부동작(성능)을 가짐
    - 같은 객체 타입에서 파생됐지만 각각의 특성을 가짐


    - 자바 syntax 다형성 구현 지원
    1) abstract, interface - extends, implements + Override
    - 하나의 객체타입이 여러개의 객체타입을 구현할 수 있음 : 다양한 역할을 할 수 있다 이말이지!
    (코드 - Person)

    - abstract : 공통된 특성(필드, 메서드)을 뽑아 만든 클래스, 역할의 표준화(코드 줄이기, 통일)
    (코드 - Phone)

    => 우리가 new 키워드로 인스턴스는 만들지못함 : super를 호출하는 것 보면 abstract 키워드를 쓰면 내부적으로는 싱글턴인가...?
    => 생성자는 가져야함 : 내부적으로 추상클래스 인스턴스 생성한다고하니깐
    => extends로 상속할 수 있음 : 역할 부여받음
    => 공통된 특성을 가지고, 공통된 행동(메서드 내부처리)를 가질 수도 있고 다르게 할 수도 있음(abstract 메서드 - 오버라이딩)


    - interface : abstract에서 필드는 빼고 공통된 메서드를 뽑아서 만듬
    => 뒤에서 더 살펴보겠음


    2) 상위 객체타입 변수가 하위 객체타입 인스턴스의 주소값을 참조할 수 있도록 지원함
    - 하위 객체타입 인스턴스가 상위 객체타입으로 자동형변환 : 주소값 그대로
    => 상위 객체타입 필드, 메서드 + 오버라이딩 메서드만 사용 가능 : 공통된 것만
    => 자동형변환된 이후 상위 -> 하위로 강제형변환(캐스팅)할 수 있음(반드시 이때만 가능) : 다시 사용가능함
    => 객체 instanceof 객체타입 : 캐스팅 전에 자동형변환된 객체타입이 맞는지, 그 타입이 맞는지 확인가능
    => 잘못된 캐스팅 에러 : ClassCastException 예외 발생


    - 필드 타입 표준화하면서 다양한 동작을 지원함
    => Tire를 상속받는 하위 타입이라면 모두 참조하도록 가능함, 인스턴스(하위)타입만 변경되지 Tire다
    => 같은 Tire를 상속받고 메서드 오버라이딩하지만 각 타이어에 따라 동작함
    (코드 - Car)


    - 메서드의 매개변수 타입 표준화하면서 다양한 동작을 지원
    (코드 - Driver)
    => Vehicle이 가진 같은 메서드를 사용하지만 Sedan / Suv에 따라 다르게 동작하게


    - 정리
    1) 다형성은 하나의 타입이 여러 역할이나 행동처럼 여러 모습을 가질 수 있다는 것
    2) 자바의 다형성 구현
    - 여러 역할, 행동을 가질 수 있게 지원하는 것 : abstract, interface, Override
    - 상속 관계 간에 자동형변환 된다는 것 : 필드 타입, 매개변수 타입 표준화

 */

package com.jinbro.syntax.extendexam;

public class KBAccount extends Account {

    /************* main *************/
    public static void main(String[] args) {
        KBAccount c1 = new KBAccount("박진형");
        c1.setMoney(100);
        System.out.println(c1.getAccount_num());
    }


    /************* KBAccount code *************/
    private String name;

    public KBAccount(String name) {
        super();
        this.name = name;
    }

    @Override
    public int getMoney() {
        /* 확인과 관련한 로직 수행 -> 상위 객체에 요청 -> 돈 가져왕 */

        return super.getMoney();
    }

    @Override
    protected void setMoney(int money) {
        /* 확인과 관련한 로직 수행 -> 상위 객체에 요청 -> 수정 */

        super.setMoney(money);
    }
}
