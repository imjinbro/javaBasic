package com.jinbro.source.fp;

/*
    [FP]
    - 프로그램은 함수와 함수로 이뤄져있다 : 필요한 함수만 가져다쓰면 됨

    1) 함수
    - 순서있는 명령의 모임 개념X, 수학의 함수 : input -> output
    - 표현식(expression) : input -> output

    2) 람다
    - 추상화된 표현 : 함수형 인터페이스, 단 하나의 abstract 메서드를 가진 인터페이스만, 컴파일러 추론
    - 내부적으로는 인터페이스 익명 구현을 함수 1개처럼 표현 : 추상회된 표현
    - 인터페이스타입변수명.메서드명(), 외부적으로는 1개의 함수


    [FP를 OOP에서 왜?]



    [표준 API 제공 함수형 인터페이스]
    - java.util.function
    - @FunctionalInterface : 단 1개의 abstract 메서드만을 갖도록 강제함

    1) Function<T,P> : <T> -> <P>
    2) Consumer<T> : <T> -> Void
    3) Predicate<T> : <T> -> Boolean
    4) Supplier<T> : lazy evaluation

*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FPExample {
    public static void main(String[] args) {

        /*
        [Function<T,P>.apply]
        1) input String -> output Integer, 같은 타입, 인풋과 같은 아웃풋 데이터라면 Identity function이라 함

        2) 람다 적용 전
        final Function<String, Integer> parse = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };

         */
        final Function<String, Integer> parse = (s) -> Integer.parseInt(s);
        System.out.println(parse.apply("1"));



        /*
         [Consumer<T>.accept]
         1) input 데이터<T>가 있지만 output 데이터는 없음

         2) 람다 적용 전
         Consumer<String> sayHello = new Consumer<String>() {
            @Override
            public void accept(String name) {
                System.out.println("Hello " + name + "!");
            }
         };
        */
        Consumer<String> sayHello =  name -> System.out.println("Hello " + name + "!");
        sayHello.accept("Jinbro");


        /*
        [Predicate<T>.test]
        1) T 타입 파라미터 인풋 데이터를 받은 후 어떤 조건에 의해 true/false 결과 리턴
        2) 리턴타입까지 이미 boolean으로 정해져있기때문에 굳이 return 뭐 이렇게 안해도됨
        3) 필터함수를 바꿔끼워서 같은 input으로 다른 함수를 적용해서 다른 output을 만들 수 있음
        - input은 immutable

        4) 아래 static 메서드 filter 보기

        Predicate<Integer> isEven = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                if(integer%2 == 0){
                    return true;
                }
                return false;
            }
        }

         */
        Predicate<Integer> isEven = integer -> integer%2 == 0;
        Predicate<Integer> isPositive = integer -> integer > 0;

        final List<Integer> numbers = Arrays.asList(1,2,3,-4,-5);
        System.out.println(filter(numbers, isEven));
        System.out.println(filter(numbers, isPositive));
        System.out.println(filter(numbers, i -> (i+4 == 0))); //그자리에서 만들 수 있음, 추론


        /*
            [Supplier<T>]
            1) lazy evaluation 가능 : 결과값이 필요할 때까지 계산을 늦추는 것
            - 선택적으로 실행되어야하는 메서드 : 조건에 따라 실행될 필요가 있고 실행하지않아도 될 때
            - 내부적으로 함수형인터페이스를 구현한 객체를 넘겨줌 : 그래서 lazy evaluation이 됨

            2) 불필요한 컴퓨팅 파워낭비를 줄여주지
         */
        Supplier<String> helloSupplier = () -> "Hello";
        System.out.println(helloSupplier.get() + " World");

        long start = System.currentTimeMillis();
        printIfInvalidIdx(0, () -> "jinbro");
        /*
            호출하는 시점에 getExpensiveVal()가 실행되기때문에 3초씩 걸림 : -1이어서 굳이 실행할 필요없는데 곧바로 실행
            printIfInvalidIdx(-1, getExpensiveVal());
            printIfInvalidIdx(-1, getExpensiveVal());
         */
        printIfInvalidIdx(-1, () -> "jinbro");
        printIfInvalidIdx(-2, () -> "jinbro");
        System.out.println("수행 시간 : " + (System.currentTimeMillis() - start) / 1000 + "초");

    }

    /*
    [Supplier로 바꾸기 전 코드]
    static String getExpensiveVal(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Jinbro";
    }

    static void printIfInvalidIdx(int idx, String value){
        if(idx >= 0){
            System.out.println(value);
        } else {
            System.out.println("Invalid");
        }
    }
    */

    static void printIfInvalidIdx(int idx, Supplier<String> valueSupplier){
        if(idx >= 0){
            System.out.println(valueSupplier.get());
        } else {
            System.out.println("Invalid");
        }
    }



    /*
        [filter 갈아끼워가면서 사용하기]
        다른 비교 함수를 넣어서 여러번 아래 코드를 만들면 보일러플레이트 코드가 많아지니깐 하나의 메서드로
        List<Integer> positiveNums = new ArrayList<>();
        for(Integer num : numbers){
            if(isPositive.test(num)){
                positiveNums.add(num);
            }
        }
        System.out.println("양수 : " + positiveNums);
        */
    static <T> List<T> filter(List<T> list, Predicate<T> filter){
        List<T> result = new ArrayList<>();
        for(T input : list){
            if(filter.test(input)){
                result.add(input);
            }
        }
        return result;
    }

}
