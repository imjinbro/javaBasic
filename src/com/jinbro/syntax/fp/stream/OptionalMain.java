package com.jinbro.syntax.fp.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
    [Optional]
    1) Stream 객체의 메서드 중 리턴타입이 Optional인 것이 있는데 메서드 처리 결과로 결과값이 존재할 떄와 하지않을 때 구분하기위해 사용
    2) NoSuchElementException 발생x : 대신 값만 얻기위해서 isPresent()로 체크해줘야함
    - 아니면 orElse()로 디폴트값 지정
    - 아니면 값이 존재할 때만 바로 출력될 수 있도록 람다식을 넘길 수 있는 ifPresent() 사용
 */
public class OptionalMain {
    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(6,8,4,5,1,3,4,6);

        System.out.println( "1000보다 큰 값 : " +
            numbers.stream()
                   .filter(i -> i > 1000)
                   .findFirst()
        );

        Optional optional = numbers.stream()
                                   .filter(i -> i > 1000)
                                   .findFirst();
        if(optional.isPresent()){
            System.out.println("있어요");
        } else {
            System.out.println("없어요");
        }


        System.out.println("없으면 999 : " +
        numbers.stream()
               .filter(i -> i > 1000)
               .findFirst()
               .orElse(999)
        );


        numbers.stream()
            .filter(i -> i > 1000)
            .findFirst()
            .ifPresent(i -> System.out.println(i));


        System.out.println(
            numbers.stream()
                   .reduce((a,b) -> a+b)
        );

        System.out.println(
            numbers.stream()
                .reduce(1, (a,b) -> a*b)
        );
    }

}
