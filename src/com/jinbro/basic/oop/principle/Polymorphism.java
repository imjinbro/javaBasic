package com.jinbro.basic.oop.principle;

/*
    [다형성]
    - 각 객체가 같은 요청을 수행하더라도 다르게 행동함 : 각자의 방식
    - 일반화관계(상속)과 연계되어 큰 힘을 발휘
    - 코드를 간결하게 : 각각 다르게 동작하도록 미리 만들어놓음
    - 변화에도 유연 : 일반화관계에서 상위타입.메서드() 하더라도 실제 인스턴스의 메서드()가 동작
 */
public class Polymorphism {
    public static void main(String[] args) {
        Pet dangdang = new Dog();
        dangdang.sing();

        Pet ribon = new Cat();
        ribon.sing();
    }
}


abstract class Pet{
    abstract void sing();
}

class Dog extends Pet{
    @Override
    void sing() {
        System.out.println("bark");
    }
}

class Cat extends Pet{
    @Override
    void sing() {
        System.out.println("meow");
    }
}
