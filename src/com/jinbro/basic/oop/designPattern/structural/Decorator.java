package com.jinbro.basic.oop.designPattern.structural;

/*
    [문제상황]
    - 사용하려고 하는 기능이 기본 기능만 있는게 아니라 추가기능이 있고 더 늘어나거나 줄어들 수 있는 상황
    (1) 기능을 사용하는 쪽(클라이언트)에서는 일관된 코드로 기능만 변경하고싶음 : 기능을 일반화시킴(abstract, interface)
    (2) 기본 + 추가 기능 대비해서 extends 관계 만들었음

    - 추가기능이 늘어날 때마다 기본 + 추가기능1 + 추가기능2 처럼 조합해서 만들어야하는 클래스들이 늘어남
    (1) 데커레이터 패턴 : 기본 기능은 기본 기능으로 쓰면서 기능은 확장하는 패턴


    [해결 - 데커레이터 패턴] : 조합 클래스는 만들지않지만 조합을 만들거나 없앨 수 있음
    (1) 기능을 상속하는 추가기능 클래스(일반화)를 만듬(확장)
    (2) 기능 호출 메서드를 구현함
    - 기능 클래스를 상속한 기본 기능 메서드 호출

    (3) 추가기능 클래스를 상속하는 OOO 추가기능 클래스를 만듬
    - 추가기능 클래스(상위) 기능 메서드 호출(super) + 추가 기능 클래스만의 추가기능 메서드 호출
 */
public class Decorator { }

class Client{
    public static void main(String[] args) {
        //기본기능만
        Ability basic = new BasicAbility();
        basic.doIt();

        //기본기능 + 추가기능1
        Ability extend1 = new FirstAdditionalAbility(basic);
        extend1.doIt();

        //기본기능 + 추가기능2
        Ability extend2 = new SecondAdditionalAbility(basic);
        extend2.doIt();

        //기본 + 추가1 + 추가2 : 조합, 조합 클래스 만들지않아도
        Ability extend3 = new SecondAdditionalAbility(extend1);
        extend3.doIt();

        //동적으로 기능 없애기
        extend3 = new SecondAdditionalAbility(basic);
        extend3.doIt();
    }
}

abstract class Ability{
    abstract void doIt();
}

class BasicAbility extends Ability{

    @Override
    public void doIt() {
        System.out.println("기본적으로 해야할 것(기본기능)");
    }
}

abstract class AdditionalAbility extends Ability{
    private Ability ability;

    public AdditionalAbility(Ability ability) {
        this.ability = ability;
    }

    @Override
    public void doIt() {
        /* 기본기능 호출 */
        ability.doIt();
    }
}

class FirstAdditionalAbility extends AdditionalAbility{

    public FirstAdditionalAbility(Ability ability) {
        super(ability);
    }

    @Override
    public void doIt() {
        super.doIt();
        /* 추가 기능 */
        todoExtends();
    }

    private void todoExtends(){
        System.out.println("확장기능1");
    }
}

class SecondAdditionalAbility extends AdditionalAbility{

    public SecondAdditionalAbility(Ability ability) {
        super(ability);
    }

    @Override
    public void doIt() {
        super.doIt();
        /* 추가 기능 */
        todoExtends();
    }

    private void todoExtends(){
        System.out.println("확장기능2");
    }
}

