package com.jinbro.basic.oop.designPattern.behaviroal;

/*
    [스트래티지 패턴 : 책임 또는 알고리즘 분배 - 변경에 유연하도록]
    - 문제점
    (1) move, attack 메서드는 변경될 가능성이 많음 : 태권V나 아톰의 이동, 공격방법이 바뀌었을 때마다 코딩을 해줘야함
    (2) move, attack 메서드는 로봇 종류가 늘어날 때마다 중복 코드가 발생할 가능성도 큼

    - 해결방법 : 변경 가능성이 높은 부분을 따로 떼냄(모듈화)
    (1) move, attack(변경사항이 많은, 중복코드가 많은) 메서드를 클래스로 캡슐화
    (2) 객체지향 모델링 원칙 OCP, DIP

    - 정리 : 여러 종류(알고리즘)가 나올 법한 것은 따로 클래스 만들기 - abstract, interface
 */

public class Strategy {
    public static void main(String[] args) {
        Robot myRobot = new TaekwonV("나의 태권V");
        myRobot.setMoving(new FlyingMove());
        myRobot.move();

        myRobot.setMoving(new WalkingMove());
        myRobot.move();
    }
}

interface Movable{
    void move();
}

class FlyingMove implements Movable{
    @Override
    public void move() {
        System.out.println("Flying~~~");
    }
}

class WalkingMove implements Movable{
    @Override
    public void move() {
        System.out.println("뚜벅뚜벅");
    }
}

interface Attackable{
    void attack();
}

class PunchAttack implements Attackable{
    @Override
    public void attack() {
        System.out.println("펀치!");
    }
}

abstract class Robot{
    private String name;

    private Movable moving;
    private Attackable attacking;

    public Robot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMoving(Movable moving) {
        this.moving = moving;
    }

    public void setAttacking(Attackable attacking) {
        this.attacking = attacking;
    }

    /*    abstract void attck();
            abstract void move();*/
    public void attack(){
        attacking.attack();
    }

    public void move(){
        moving.move();
    }
}

class TaekwonV extends Robot{

    public TaekwonV(String name) {
        super(name);
    }

   /* @Override
    void attck() {

    }

    @Override
    void move() {

    }*/
}

class Atom extends Robot{

    public Atom(String name) {
        super(name);
    }

   /* @Override
    void attck() {

    }

    @Override
    void move() {

    }*/
}

abstract class PricePolicy{
    protected double rate;

    public PricePolicy(double rate) {
        this.rate = rate;
    }

    abstract int calc(int price);
}

class MemberPrice extends PricePolicy{

    public MemberPrice(double rate) {
        super(rate);
    }

    @Override
    int calc(int price) {
        return (int)(rate * price);
    }
}

class OrdinaryPrice extends PricePolicy{

    public OrdinaryPrice(double rate) {
        super(rate);
    }

    @Override
    int calc(int price) {
        return 0;
    }
}

class OlderBookPrice extends PricePolicy{

    public OlderBookPrice(double rate) {
        super(rate);
    }

    @Override
    int calc(int price) {
        return 0;
    }
}

class Member{
    private String name;
    private int totalPrice;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int price) {
        totalPrice += price;
    }
}

class Rental{
    private Member member;
    private Book book;
    private int quantity;

    private PricePolicy priceCalulator;

    public Rental(Member member, Book book, int quantity) {
        this.member = member;
        this.book = book;
        this.quantity = quantity;

        /* 알고리즘 추가 부분 : 멤버(누적금액)에 따라서, 책(10년 이상)에 따라서 */
    }

    public void setPriceCalulator(PricePolicy priceCalulator) {
        this.priceCalulator = priceCalulator;
    }

    public void order(){
        member.setTotalPrice(
            priceCalulator.calc(book.getPrice()) * quantity
        );
    }
}

class Book{
    private String name;
    private String date;

    private int price;

    public Book(String name, String date, int price) {
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}


