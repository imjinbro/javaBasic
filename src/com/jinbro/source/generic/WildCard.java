package com.jinbro.source.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WildCard {
    public static void main(String[] args) {
        List<?> list = new ArrayList<>(); //와 List<? extends Object> list = new ArrayList<>() 와 동일함 생략


        /* <T> 타입파라미터가 의미있게 reverse에서 사용되는가? : 놉 <?>로 사용해도 */
        System.out.println("중복 개수 : " + frequency(Arrays.asList(1,2,3,4,4,4,4,4,4), 4));


        /* <T> 타입파라미터가 의미있게 reverse에서 사용되는가, 캡쳐에러는 어떻게 해결하나 */
        List<Integer> list2 = Arrays.asList(1,2,3,4,5);
        reverse(list2);
        System.out.println(list2);




        //printList(numList); <- List<Object>와 List<Integer> 관계 X, 명시적일 떄는 안됨(T extends Object 혹은 ? extends Object 해야)
        // 할 경우에는 Object로 1,2,3,4,5가 변환되어서 저장
        List<Integer> numList = Arrays.asList(1,2,3,4,5);
        printList(Arrays.asList(1,2,3,4,5));
        printList2(Arrays.asList(1,2,3,4,5));
        printList2(Arrays.asList("1","2","3","4","5"));
    }

    /*
    static <T> boolean isEmpty(List<T> list){
        return list.size() == 0;
    } */

    // 굳이 데이터 타입으로 변환하지않고도 사용할 수 있는 기능 : 리스트 제공메서드만 사용하는 것
    static boolean isEmpty(List<?> list){
        return list.size() == 0;
    }


    /*
    static <T> long frequency(List<T> list, T target){
        return list.stream().filter(s -> s.equals(target)).count();
    }
    */

    //Object의 equals만 사용하면 됨
    static long frequency(List<?> list, Object target){
        return list.stream().filter(s -> s.equals(target)).count();
    }


    /*
    static <T> void reverse(List<T> list){
        List<T> temp = new ArrayList<>(list);
        int length = list.size();
        for(int i=0; i<length; i++){
            list.set(i, temp.get(list.size()-i-1));
        }
    }
    */

    /* 와일드카드 헬퍼 메서드 : ? 사용하는 부분과 제네릭 사용하는 메서드 각각 만들고 ?에서 generic으로 */
    static void reverse(List<?> list){
        reverseHelper(list);
    }

    private static <T> void reverseHelper(List<T> list){
        List<T> temp = new ArrayList<>(list);
        int length = list.size();
        for(int i=0; i<length; i++){
            list.set(i, temp.get(list.size()-i-1));
        }
    }


    static void printList(List<Object> list){ list.forEach(s -> System.out.println(s.toString())); }
    static void printList2(List<?> list){ // ? extends Object 입니다
        list.forEach(s -> System.out.println(s.toString()));
    }

}
