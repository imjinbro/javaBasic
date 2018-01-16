package com.jinbro.syntax.collection;

/*
    [동기화 컬렉션 : 동기화메서드 사용]
    - 멀티쓰레딩 환경에서 synchronized는 매우 중요
    - 싱글쓰레드 처리를 하는데에도 동기화 메서드를 사용한다면 낭비

    1) Vector(synchronized ArrayList), HashTable(synchronized HashMap)
    2) 나머지 컬렉션의 메서드를 동기화 메서드로 래핑하는 방법
    - Collections.synchronizedXXX : 컬렉션프레임워크 유틸리티 클래스
    - XXX : List / Set / Map


    [부분잠금 컬렉션 : 병렬처리, 동기화블록 사용]
    - 동기화를 메서드로 지정하지않고 필요한 그 부분만 동기화 영역으로 처리해서 조금 더 효율적인 처리를 할 수 있도록 함
    - 메서드 처리 중 동기화 부분이 필요한 부분만 블록처리한 컬렉션
 */
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SynchronizedCF {
    public static void main(String[] args) {
        List<String> synchronizedList =  Collections.synchronizedList(new ArrayList<>());
        Set<Integer> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());

        Map<String, Character> concurrentHashMap = new ConcurrentHashMap<>();
        Queue<String> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    }
}
