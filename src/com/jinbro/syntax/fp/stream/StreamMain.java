package com.jinbro.syntax.fp.stream;

/*
    [Steam api]
    1) Iterator(Iterable 할 수 있는) + 컬렉션 Builder : interface
    - abstract, default, static 메서드를 가짐
    - 함수(자바에서는 함수형인터페이스 메서드)를 컬렉션에 적용 후 원하는 결과값을, 으로 리턴


    [장점]
    1) 깔끔한 코드
    - 함수형 프로그래밍 : 필요한 함수를 람다식으로 만들어서 그때그때 지정

    2) 필요한 만큼만 코드 수행, 필요할 때 처리
    - lazy evaluation : 결과를 요청하기 전까지 하지않다가 처리함
    - 제어문을 쓰지않더라도 필요한만큼까지만 코드 실행 -> 평가 : 불필요한 연산을 하지않음, 목적 달성까지만

    3) null을 직접적으로 다루지않음
    - 결과를 얻을 때의 타입은 Optional 타입 : 안정적인 타입, null 예외발생안함


    [자바]
    1) CharSequence, String, StringBuilder
    2) 오토박싱,언박싱 그리고 integer 캐시
    3) Iterable
    4) 컬렉션 프레임워크 : 레퍼런스 타입 저장
    5) System 객체 : 시스템(jvm) 설정
    6) 쓰레드 제어 : 자바 병렬 프로그래밍, 쓰레드풀(쓰레드 + 작업 큐 + 작업)

*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamMain {
    public static void main(String[] args) {
        /* Stream 사용 안한 코드 */
        final List<Integer> data = Arrays.asList(1,2,3,4,5,6,7,8);
        Integer result = null;
        for(final Integer i : data){
            if(i > 3 && i < 9){
                final Integer newNumber = i * 2;

                if(newNumber > 10){
                    result = newNumber;
                    break;
                }
            }
        }

        System.out.println("함수형 인터페이스, Stream 미적용 : " + result);


        /*
            [Stream API 사용하지않고 함수형인터페이스만 사용한 코드]
            1) 재사용 가능하다는 장점 : 표준 1개 만들어두고 적용 함수를 모듈화
            2) null 처리
            3) 계속해서 결과값 assign : 함수를 중첩시켜서 하나의 변수에 assign 해도 되지만
            4) 1개 함수에 대해서 모든 수 처리 : lazy evaluation 하지않음, Stream 관점에서 불필요한 처리를 함
        */
        final List<Integer> greaterThan3 = filter(Arrays.asList(1,2,3,4,5,6,7,8), i-> i > 3);
        final List<Integer> lessThan9 = filter(greaterThan3, i-> i < 9);
        final List<Integer> doubled = map(lessThan9, i-> i * 2);
        final List<Integer> greaterThan10 = filter(doubled, i-> i > 10);
        System.out.println("함수형 인터페이스(함수)만 활용(불필요한 요소까지 다 연산) : " + greaterThan10);



        /*
            [Stream API까지 사용해서 Array build한 코드]
            1) Optinal 타입 사용
            - 비었을 때 null 아님
            - null이더라도 널포인터익셉션 발생 안함 : Optional.empty 리턴(안정적인 처리)
        */
        System.out.println("Stream 적용(1) : " +
            Stream.of(1,2,3,4,5)
                  .filter(i -> i > 3 && i < 9)
                  .map(i -> i *2)
                  .filter(i -> i > 10)
                  .findFirst());


        /* 필요한만큼 실행하는 것을 보기위해서 sout을 붙여둠  */
        AtomicInteger cnt = new AtomicInteger(0);

        System.out.println("Stream 적용(2) : " +
            IntStream.of(1,2,3,4,5,6,7,8,9,10)
                .filter(i -> {
                    System.out.println("==== filter(1) 처리 시작 " + cnt.addAndGet(1) + "회 ====");
                    System.out.println(i);
                    return i > 3;
                })
                .filter(i -> {
                    System.out.println("==== filter(2) 처리 시작 " + cnt.addAndGet(1) + "회 ====");
                    System.out.println(i);
                    return i < 9;
                })
                .map(i -> {
                    System.out.println("==== map 처리 시작 " + cnt.addAndGet(1) + "회 ====");
                    System.out.println(i);
                    return i *2;
                })
                .filter(i -> {
                    System.out.println("==== filter(3) 처리 시작 " + cnt.addAndGet(1) + "회 ====");
                    System.out.println(i);
                    return i > 10;
                })
                .findFirst());

    }


    /* Stream은 쓰지않지만 모던 자바로 작성 */
    private static <T> List<T> filter(List<T> list, Predicate<T> predicate){
        final List<T> result = new ArrayList<>();

        for(final T t : list){
            if(predicate.test(t)){
                result.add(t);
            }
        }

        return result;
    }

    private static <T,R> List<R> map(List<T> list, Function<T,R> mapper){
        final List<R> result = new ArrayList<>();

        for(final T t : list){
            result.add(mapper.apply(t));
        }

        return result;
    }
}
