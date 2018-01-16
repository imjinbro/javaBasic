package com.jinbro.syntax.fp.stream;

/*
    [Stream parallel 주의할 점]
    1) 값을 가지고 있고 그때마다 생산할 때 parallel 사용하면 기다리고 확인하고한다고 더 느려요
    - 순서 보장없이 여러 쓰레드가 연산하기때문에
    - 특정 메서드 처리가 오래 걸리는 처리일 때 사용하는 것이 좋음

    2) Stream은 재사용 불가
    - terminate operation을 사용하면 끝

 */

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamParallelBadCase {
    public static void main(String[] args) {

    }

    /* bad case */
    public static long iterativeSum(long n){
        long result = 0;
        for(long i=1; i<=n; i++){
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n){
        return Stream.iterate(1L, i -> i+1).limit(n).reduce(0L, (i1, i2) -> i1+i2);
    }

    public static long parallelSum(long n){
        /* 이전 값을 가지고 있어야하는데 없으니깐 병렬처리가..... 기다립니다 */
        return Stream.iterate(1L, i -> i+1).limit(n).parallel().reduce(0L, (i1, i2) -> i1+i2);
    }

    public static long rangedSum(long n){
        return LongStream.rangeClosed(1, n).reduce(0L, (i1, i2) -> i1+i2);
    }

    public static long parallelRangedSum(long n){
        return LongStream.rangeClosed(1,n).parallel().reduce(0L, (i1, i2) -> i1+i2);
    }
}
