package com.jinbro.source.stdapi.wrapper;

public class WrapperTest {

    /*
        1) Byte
        2) Short
        3) Character
        4) Integer
        5) Float
        6) Long
        7) Double
     */

    public static void main(String[] args) {
        /* 박싱하기 */
        Integer num = new Integer(10);
        Integer num2 = Integer.valueOf(10); // static, return type Integer
        Integer num3 = 20; //자동 박싱, java5~


        /* 언박싱
           1) 내부 데이터를 다른 타입으로 꺼낼 수 있음 : 내부적으로 캐스트 연산자 사용
           2) 자동 언박싱도 됨 : 3번째 줄, java5~
        */
        double data = num.doubleValue();
        System.out.println(data);
        int data2 = num;


        /* parseInt : primitive 타입(int)으로 변경 */
        try{
            System.out.println(Integer.parseInt("1"));
        } catch(NumberFormatException e){
            System.out.println(e.getMessage());
        }


        /* 주의할 점
           1)내부 데이터를 비교할 때 ==, != 연산자를 그냥 사용해서는 안됨
           - 좁은 범위 내에서는 비교가 가능하지만 어떤 값이 저장될지 모르는 상태일 때는 equals 사용
           => Object.equals를 오버라이딩함 : primitive type 변환하고 값만 비교
        */
        Integer compare1 = new Integer(1);
        Integer compare2 = new Integer(1);

        System.out.println(compare1 == compare2); // false
        System.out.println(compare1.equals(compare2)); // true
    }
}
