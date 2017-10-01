package com.jinbro.source.polymorphism;

public abstract class Phone {

    /* interface는 public static final 선언만 가능 */
    private String sound = "텔렐렐렐렐 ";


    public void tell(){
        System.out.println(sound + "전화를 겁니다");
    }

    public abstract void sms();
}

class SmartPhone extends Phone {

    public static void main(String[] args) {
        SmartPhone sp = new SmartPhone();
        sp.tell();
    }

    /* SmartPhone에만 있는 기능 */
    public void goAppStore(){

    }

    /* Phone 역할을 한다면 공통적으로 가지는 기능 */
    @Override
    public void tell() {
        //super.tell();
        System.out.println("스마트폰 스마트폰 텔렐렐렐");
    }

    @Override
    public void sms() {
        /* 각각 다르게 작성할 수 있음 */
    }
}
