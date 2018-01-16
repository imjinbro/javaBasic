package com.jinbro.syntax.polymorphism;

public class Car {
    Tire frontLeftTire;
    Tire frontRightTire;
    Tire backLeftTire;
    Tire backRightTire;

    public Car(){
        frontLeftTire = new HankookTire(1000);
        /*
            1) Tire 타입을 HankookTire로 변경하지않아도됨 : Tire를 상속받았다면 모두 가능
            2) 한국타이어의 성능을 맛볼 수 있지
         */
    }

}

class Tire {

    private int maxRotation;

    public Tire(int maxRotation){
        this.maxRotation = maxRotation;
    }

    public void roll(){
        System.out.println("바퀴가 굴러갑니다");
    }
}

class HankookTire extends Tire {

    /* 1000회 더! */
    public HankookTire(int maxRotation) {
        super(maxRotation + 1000);
    }

    @Override
    public void roll() {
        /* 한국타이어 마음대로 움직인다! */
        System.out.println("바퀴가 굴러가요오오오!!!!");
    }
}
