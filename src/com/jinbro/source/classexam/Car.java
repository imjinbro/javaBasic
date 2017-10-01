package com.jinbro.source.classexam;

/* *는 하위패키지를 포함하지않음 */
import com.jinbro.test.*;

public class Car {


   /* static field */
   public static final int YEAR;
   static {
       YEAR = 100;
   }

    /* instance field */
    private int speed = 10;
    private String color = "black";
    final String name;

    public Car(){
        this(0, "");
    }

    /* method overloading */
    public Car(int speed){
        this(speed, "");
    }

    public Car(int speed, String color){
        if(speed > 10){
            this.speed = speed;
        }

        if(!color.equals("")){
            this.color = color;
        }

        this.name = "jinbro";
    }

    @Override
    public String toString() {
        return "com.jinbro.source.classexam.Car{" + "speed=" + speed + ", color='" + color + '\'' +'}';
    }

    public void getCarList(String[] list){
        for(int i=0; i<list.length; i++){
            System.out.println(list[i]);
        }
    }

    /* 위와 같은 메서드이기때문에 중복 에러가 발생 : 컴파일x
    public void getCarList(String...list){
        for(int i=0; i<list.length; i++){
            System.out.println(list[i]);
        }
    } */

    public void getCarList(int[] list){
        for(int i=0; i<list.length; i++){
            System.out.println(list[i]);
        }
    }

    /* com.jinbro.source.Main 클래스에서 사용 - 흐름 제어(메인쓰레드), 실행 클래스에서 제어함 - 나머지 API import */
    public static void main(String[] args) {
        Car car = new Car();
        System.out.println(car.toString());
        car.getCarList(new String[]{"차1", "차2", "차3"});
        car.getCarList(new int[]{1, 2, 3});


        /* public access */
        Test t1 = new Test();
        System.out.println(t1.toString());


        /* Sigleton */
        Sigleton s1 = Sigleton.getInstance();
        Sigleton s2 = Sigleton.getInstance();

        if(s1 != null || s1==s2){
            System.out.println("두 변수는 같은 객체를 참조하고 있습니다");
        }
    }
}

/* 컴파일 에러 - .java와 같은 이름의 class만 public으로 선언
public class Person {

} */

/* default */
class Person{
}
