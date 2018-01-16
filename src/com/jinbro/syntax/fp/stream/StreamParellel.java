package com.jinbro.syntax.fp.stream;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/*
    [동시성과 병렬성]
    - 멀티쓰레드의 동작방식이라는 점은 같음

    1) 동시성 : 하나의 코어에서 멀티쓰레드를 돌림, 번갈아가면서(워낙 빨라서 병렬성처럼 보임)
    2) 병렬성 : 멀티코어로 작업을 처리함, 각 쓰레드로


    [병렬성]
    1) 데이터 병렬성(data parallelism) : 전체 데이터를 여러 서브데이터로 쪼개고 병렬처리 후 하나로 모음(Stream parallel)
    - 스트림 병렬처리 : ForkJoin 프레임워크, fork(쪼개고), join(합침)
    - 각각 코어 처리, 코어 내부 쓰레드풀(ForkJoinPool)로 멀티쓰레드 관리(내부에서 더 쪼개서)

    2) 작업 병렬성(task parallelism) : 서로 다른 작업을 병렬 처리하는 것


    [Stream parallel - 병렬 프로그래밍]
    1) 1개 코어 집적도만 높이는게 아니라 코어를 여러개 다는 방식으로
    - 듀얼, 쿼드, 옥타.....
    - 여러개 코어를 활용하는 프로그래밍 : 병렬 프로그래밍

    2) 자바의 기존 방식대로 병렬프로그래밍한다면 side effect가 많이 생겨서 FP 요소들을 넣음
    - 공유되는 자원의 상태를 여기저기서 변경하면서 예상치 못한 결과가 나올 위험이 큼

    3) Stream의 parallel() 이나 기존 컬렉션.parallelStream()
    - 시스템 코어가 많을수록 더 빠른 처리
    - 내부적으로

    4) 무조건 빠른처리를 하지않음
    - 처리 요소수가 적고 실행당 처리시간이 얼마안되는 처리일 경우 오히려 느림 : 싱글코어보다
    - 인덱스가 있어서 쪼개기 쉬운 컬렉션보다 링크드리스트(객체 연결)형태는 상대적으로 느림
    - 싱글코어 CPU인 경우 순차적으로 1개씩 처리하는게 더 나음 : parallel하면 동시성 작업이 됨

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
