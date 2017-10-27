package com.jinbro.source.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WildCard {
    public static void main(String[] args) {
        List<?> list = new ArrayList<>(); //와 List<? extends Object> list = new ArrayList<>() 와 동일함 생략

        List<Integer> numList = Arrays.asList(1,2,3,4,5);
        //printList(numList); <- List<Object>와 List<Integer> 관계 X
        printList(Arrays.asList(1,2,3,4,5)); // 할 경우에는 Object로 1,2,3,4,5가 변환되어서 저장

        printList2(Arrays.asList(1,2,3,4,5));
        printList2(Arrays.asList("1","2","3","4","5"));
    }

    static void printList(List<Object> list){
        list.forEach(s -> System.out.println(s.toString()));
    }

    static void printList2(List<?> list){ // ? extends Object 입니다
        list.forEach(s -> System.out.println(s.toString()));
    }
}
