package com.jinbro.syntax.polymorphism;

public class Driver {

    public void drive(Vehicle vehicle){
        //Sedan, Suv 모두 몰 수 있다!!!
        vehicle.run();

        if(vehicle instanceof Sedan){
            Sedan sedan = (Sedan)vehicle;
            System.out.println(sedan.getColor());
        }
    }
}

class Vehicle {
    public void run(){
        System.out.println("달립니다!");
    }
}


class Sedan extends Vehicle {

    private String color = "black";

    @Override
    public void run() {
        System.out.println("조용조용조용하지만 빠르게 달립니다");
    }

    public String getColor() {
        return color;
    }
}


class Suv extends Vehicle {
    @Override
    public void run() {
        System.out.println("거칠지만 더 빠르게 달립니다");
    }
}
