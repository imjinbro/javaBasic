package com.jinbro.syntax.generic;

/*
    [제네릭 제한]
    - 모든 타입을 파라미터로 받아들이지않고 제한두는 것
    - 설정레벨 : 클래스, 인터페이스 or 메서드

    1) T extends Number : Upper Bound
    - Number나 Number를 상속하는 객체타입만 타입 파라미터로 사용가능
    - 여러 조건을 줄 수도 있음 : T extends Number & Comparable
    => 인터페이스의 경우 여러개, 클래스 1개


    [자바 추가]
    1) (&) : Intersection type, 람다식에도 쓰여요

*/

import java.util.*;

public class GenericBound {

    /* Comparable 구현 객체타입만 : compareTo 메서드 구현 */
    static <T extends Comparable> long greaterThan(T[] data, T target){
        return Arrays.stream(data).filter( s ->  s.compareTo(target) > 0).count();
    }


    public static void main(String[] args) {
        //Boxing 시키는 이유 : 객체가 제공하는 메서드 사용
        System.out.println(greaterThan(new Integer[]{1,2,3,4}, 3));

    }


}