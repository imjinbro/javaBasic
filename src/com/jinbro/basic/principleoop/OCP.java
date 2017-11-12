package com.jinbro.basic.principleoop;

public class OCP {
    public static void main(String[] args) {

    }
}

class Driver{

    private Car myCar;
    //private Matiz myCar = new Matiz();
    /*
        이 상태에서 myCar가 쏘나타로 변경한다면
        Sonanta newMyCar = Sonata(); 설정을 할 것인가?
        놉 인터페이스를 하나 만들어서(확장 - Open) 운전자가 변화에 영향을 받지않도록(Close)할 수 있음
     */

    public Driver() {

    }

    public void setMyCar(Car myCar) {
        this.myCar = myCar;
    }

    public void go(){
        myCar.drive();
    }
}

abstract class Car {
    public abstract void drive();
}

class Matiz extends Car {

    @Override
    public void drive(){
        System.out.println("마티즈 운전 붕붕");
    }
}

class Sonata extends Car {
    @Override
    public void drive(){
        System.out.println("쏘나타가 나가신다 길을 비켜라");
    }
}
