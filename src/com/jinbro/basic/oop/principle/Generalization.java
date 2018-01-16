package com.jinbro.basic.oop.principle;

/*
    [상속(일반화타입)]
    - 총칭(일반화된 타입)을 만들어내는 것 : 하위 is a kind of 상위
    (1) 사과, 바나나, 배 각각의 객체를 과일 이라는 객체로
    (2) 과일이라는 상위개념에서 파생되는 하위개념을 만들어냄 : 재사용 + 확장(extends)

    - 추가되더라도 상위개념 객체로 통합해서 부르면 되니깐 변경에도 유연 : 간단한 관계 처리
    - 주의할 점
    (1) 기능 재사용하려고 상속하면 안됨 : 모델링 연관 - 의존 관계를 활용(사용만 하자)
    (2) 하나의 인스턴스가 두개의 역할을 수행하게끔 모델링 하면 안됨 : 두개를 섞어서 1개의 경우로 만들어야함
    - 예를 들어 Non-Local과 Local을 구분하는 Area와 VIP, Ordinary를 구분하는 Member
    - 문제해결 - 통합 : 이럴 경우 둘을 섞어 Member가 상위고 그에서 파생되는 4가지를 만드는 것이 좋음

    (3) 상위 - 하위는 is a kind of : 역할수행관계X(사람 - 회사원/택시기사), 세분화(멤버 - VIP/Ordinary)
 */
public class Generalization {

}

/* 이렇게 간편화 시킬 수 있구나라는 점 */
class Customer{
    private int money;
    private Member member;
    //private Area area; - 지역민과 지역민 아닌 것을 이렇게 나누지않고 Member로 통합

    public void order(Item item){
        int money = member.order(item.getMoney()); //할인
    }
}


class Item{
    private int money;

    public int getMoney() {
        return money;
    }
}

abstract class Member{
    abstract int order(int money);
}

class VIPLocal extends Member{

    @Override
    int order(int money) {
        return money; //할인률 적용
    }
}

class VIPNonLocal extends Member{

    @Override
    int order(int money) {
        return money; //할인률 적용
    }
}

class OrdinaryLocal extends Member{

    @Override
    int order(int money) {
        return money;
    }
}

class OrdinaryNonLocal extends Member{

    @Override
    int order(int money) {
        return money;
    }
}



class Song {
    private int price;
    private SaleMode mode;


    public Song(int price, SaleMode mode) {
        this.price = price;
        this.mode = mode;
    }

    public int getPrice(){
        return mode.calculator(price);
    }
}

abstract class SaleMode{
    abstract int calculator(int price);
}

class OnSale extends SaleMode{

    @Override
    int calculator(int price) {
        return (int)(price * 0.1);
    }
}

class TodayEvent extends SaleMode {

    @Override
    int calculator(int price) {
        return (int)(price * 0.3);
    }
}


