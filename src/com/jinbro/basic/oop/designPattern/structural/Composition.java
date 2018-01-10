package com.jinbro.basic.oop.designPattern.structural;

import java.util.ArrayList;
import java.util.List;

/*
    [기존 코드 문제점]
    - 객체 간 composition 관계일 때 부분(part) 종류가 추가될 때마다 전체(whole)에 메서드 추가를 해줘야함 : OCP 위반
    - 구체적인 것에 의존 : DIP 위반

    [개선하기 - 컴포지션 패턴]
    - 부분(part)을 일반화(extends, 상속관계, 캡슐화)하기 : ComputerComponent
    - 큰 개념에 의존하도록 : 일반화된 컴포넌트
    - 컴포넌트의 종류가 늘어나더라도(확장) 전체(whole)의 코드는 변경되지않음
    - 전체(whole)도 component로 넣어서 동일 인터페이스로 클라이언트에서 요청할 수 있게하자는 것이 책 내용인데 일단 적용은 안함
 */
public class Composition {
    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.addComponent(new Keyboard(5000, 100));
        computer.addComponent(new Monitor(30000, 190));
        computer.addComponent(new Body(50000, 250));

        System.out.println(computer.getPrice() + ", " + computer.getPower());
    }
}

class Computer{
    private List<ComputerComponent> componentList = new ArrayList<>();

    public void addComponent(ComputerComponent component){
        if(isAlreadyAdd(component)){
            return;
        }
        componentList.add(component);
    }

    public void removeComponent(ComputerComponent component){
        if(!isAlreadyAdd(component)){
            return;
        }
        componentList.remove(component);
    }

    private boolean isAlreadyAdd(ComputerComponent component){
        return componentList.contains(component);
    }

    public int getPrice(){
        return componentList.stream()
                     .mapToInt(ComputerComponent::getPrice)
                     .sum();
    }

    public int getPower(){
        return componentList.stream()
            .mapToInt(ComputerComponent::getPower)
            .sum();
    }
}

abstract class ComputerComponent{
    private int price;
    private int power; //소비전력량

    public ComputerComponent(int price, int power) {
        this.price = price;
        this.power = power;
    }

    public int getPrice(){
        return getComponentPrice(price);
    }

    protected abstract int getComponentPrice(int price);
    protected abstract int getComponentPower(int power);

    public int getPower(){
        return getComponentPower(power);
    }
}


class Keyboard extends ComputerComponent {
    public Keyboard(int price, int power) {
        super(price, power);
    }

    @Override
    protected int getComponentPrice(int price) {
        return price;
    }

    @Override
    protected int getComponentPower(int power) {
        return power;
    }
}

class Body extends ComputerComponent{
    public Body(int price, int power) {
        super(price, power);
    }

    @Override
    protected int getComponentPrice(int price) {
        return price;
    }

    @Override
    protected int getComponentPower(int power) {
        return power;
    }
}

class Monitor extends ComputerComponent{
    public Monitor(int price, int power) {
        super(price, power);
    }

    @Override
    protected int getComponentPrice(int price) {
        return price;
    }

    @Override
    protected int getComponentPower(int power) {
        return power;
    }
}