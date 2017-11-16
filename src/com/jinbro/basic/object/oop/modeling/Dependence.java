package com.jinbro.basic.object.oop.modeling;

/*
    [의존관계]
    - 연관관계와는 비슷하지만 짧게 다른 객체를 사용함
    1) 연산의 인자로 사용
    2) 메서드 내부의 지역객체로 참조

    - 연관관계는 연관된 하나의 객체를 쭉
 */

public class Dependence {
}

/* Person이 이미 다른 곳에 있어서 */
class Owner {
    private Car car; //연관관계

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}

class Car{
    //단방향 연관이라 private Owner owner; 필요없음

    // 의존관계
    public void fillGas(GasPump gasPump){
        gasPump.getGas(1000);
    }
}

class GasPump{

    public void getGas(int amount){

    }
}

