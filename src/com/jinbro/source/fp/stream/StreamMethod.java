package com.jinbro.source.fp.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    [Stream 메서드 종류]
    1) intermediate Operation Method
    - Stream 타입을 인스턴스를 리턴하는 메서드 : 레퍼런스 타입을 저장하는 컬렉션(primitive type 오토박싱)
    - 메서드 체이닝을 통해서 계속해서 어떤 작업을 함 : intermediate operation method 체이닝

    2) Terminate Operation Method
    - intermediate operation method 적용 리스트에서 결과값을 가져오는 메서드
    - 이제 위에 적용시켜놨던 것들 시작! 을 알림
    - 새로운 컬렉션으로 결과값을 리턴할 수도 있음 : Collectors 유틸리티 클래스


    [알아둘 점]
    (1) filter 적용할 때 레퍼런스 같은지 비교 하고싶다면 equals 사용하기 : == 주의하기
    - 자바 오토박싱 Integer 캐시 : -128 ~ 127까지는 캐시가 되어서 같은 오브젝트로, 범위를 벗어나면 다른 오브젝트로 오토박싱

 */
public class StreamMethod {

    public static void main(String[] args) {
        System.out.println( "결과 : " +
        Stream.of(1,2,3,4,5)
              .filter(i -> i > 1) //intermediate operation method : 중간 연산
              .filter(i -> i < 4) //intermediate operation method
              .findFirst() //terminate operation method : 끝냄
        );


        /* 기존 컬렉션 -> Stream */
        List<Integer> money = Arrays.asList(5,6,3,4,5);
        System.out.println(
        money.stream()
               .filter(i -> i > 4)
               .map(i -> i * 2)
               .map(i -> "$" + i)
               .collect(Collectors.toList()) //terminate operation
        );


        System.out.println(
            money.stream()
                .filter(i -> i > 4)
                .map(i -> i * 2)
                .map(i -> "$" + i)
                .collect(Collectors.toSet())
        );


        System.out.println(
            money.stream()
                .filter(i -> i > 4)
                .map(i -> i * 2)
                .map(i -> "$" + i)
                .distinct()
                .collect(Collectors.joining(", ", "{", "}"))
                //CharSequence : char 배열을 읽을 수 있는 메서드 인터페이스 - String, StringBuilder가 구현객체
        );


        System.out.println(
            money.stream()
                .filter(i -> i > 4)
                .map(i -> i * 2)
                .map(i -> "$" + i)
                .distinct()
                .collect(Collectors.toList())
                //순서 + 중복제거
        );


        System.out.println(
            Stream.of(1,2,3,4,5)
                     .filter(i -> i == 3) // -128 ~ 127까지는 캐시해서 같은 오브젝트로 취급
                     .filter(i -> i == 129) // 메모리 레퍼런스 체크함(==) : 레퍼런스 타입으로 int 오토박싱(new Integer(3))
                     .findFirst()
        );


        System.out.println(
            Stream.of(1,2,3,4,5)
                .filter(i -> i.equals(3))
                .findFirst()
        );


        System.out.println(
            Stream.of(1,2,3,4,5)
                .filter(i -> i > 3)
                .count()
        );


    }
}
