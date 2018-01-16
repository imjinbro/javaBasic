package com.jinbro.syntax.polymorphism;

public class Person {

    private String name;

    public Person(String name){
        this.name = name;
    }

    public static void main(String[] args) {
        /*
            이렇게 선언하는 장점이 뭐가 있는거야? 어떤 특징이 있어?
            1) getName은 Person의 메서드지만 Jinbro가 getName을 상속받아 다르게 동작하도록 하면서
            2) 오버라이딩하지않은 Person의 메서드를 그대로 사용함
            3) 대신 Jinbro에만 있는 메서드는 사용못함 : Person 형변환되었기 때문에
         */
        Person p1 = new Jinbro("jinbro1");
        p1.getName(); //Jinbro.getName()
        p1.sayHello(); //Person.sayHello()

        Jinhyung jinhyung = new Jinhyung("jinhyung");
        Person p2 = jinhyung;
        System.out.println(jinhyung == p2); // true

    }

    public String getName() {
        return name;
    }

    public void sayHello(){
        System.out.println("Say Hello");
    }
}


class Jinbro extends Person implements Barista, Customer{

    public static void main(String[] args) {
        Jinbro jinbro = new Jinbro("jinbro2");
        jinbro.makeCoffee();
    }

    public Jinbro(String name) {
        super(name);
    }

    @Override
    public void order() {
        //같은 역할(타입)이지만 다르게 동작(메서드 내부가 다름)
        System.out.println("아메리카노요");
    }

    @Override
    public String getName() {
        return super.getName() + "입니다";
    }

    @Override
    public void makeCoffee() {

    }
}


class Jinhyung extends Person implements Customer {

    public Jinhyung(String name) {
        super(name);
    }

    @Override
    public void order() {
        System.out.println("딸기스무디!");
    }
}


interface Customer{
    public void order();
}

interface Barista{
    public void makeCoffee();
}
