package com.jinbro.source.fp.stream;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/*
    [Stream parallel - 병렬 프로그래밍]
    1) 1개 코어 집적도만 높이는게 아니라 코어를 여러개 다는 방식으로
    - 듀얼, 쿼드, 옥타.....
    - 여러개 코어를 활용하는 프로그래밍 : 병렬 프로그래밍

    2) 자바의 기존 방식대로 병렬프로그래밍한다면 side effect가 많이 생겨서 FP 요소들을 넣음
    - 공유되는 자원의 상태를 여기저기서 변경하면서 예상치 못한 결과가 나올 위험이 큼

    3) Stream의 parallel() 이나 기존 컬렉션.parallelStream()
    - 시스템 코어가 많을수록 더 빠른 처리
    - 내부적으로

 */
public class StreamParellel {
    public static void main(String[] args) {

        int[] sum = { 0 };
        IntStream.range(0, 5000)
                 .forEach(i -> sum[0] += i);
        System.out.println("single thread sum :"+ sum[0]);


        int[] sum2 = { 0 }; //일종의 상태값 : A 쓰레드가 값을 가지고 연산을 하는 와중에 B쓰레드에서 연산한 값이 저장됨
        IntStream.range(0, 5000)
            .parallel()
            .forEach(i -> sum2[0] += i);
        System.out.println("parallel sum (with side effect) : " + sum2[0]);


        System.out.println("parallel sum (without side effect) : " +
            IntStream.range(0, 5000)
                     .parallel()
                     .sum()
        );



        final long singleStart = System.currentTimeMillis();
        Arrays.asList(1,2,3,4,5,6,7,8,9)
            .stream()
            .map( i -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return i;
            })
            .forEach(i -> System.out.println(i));

        System.out.println("time(single) : " + (System.currentTimeMillis() - singleStart));


        final long parallelStart = System.currentTimeMillis();
        Arrays.asList(1,2,3,4,5,6,7,8,9)
              .parallelStream()
              .map( i -> {
                  try {
                      TimeUnit.SECONDS.sleep(1);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  return i;
              })
              .forEach(i -> System.out.println(i));
        System.out.println("time(parallel) : " + (System.currentTimeMillis() - parallelStart));


        /* 사용 코어 개수 제한하기 : 0(1개), 1(2개), 3(4개), 7(8개) */
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "0");
        final long parallelLimitStart = System.currentTimeMillis();
        Arrays.asList(3,4,5,6,7,8,9,10,11)
              .parallelStream()
              .map( i -> {
                  try {
                      TimeUnit.SECONDS.sleep(1);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }

                  return i;
              })
              .forEach(i -> System.out.println(i));
        System.out.println("time(parallel) : " + (System.currentTimeMillis() - parallelLimitStart));
    }
}
