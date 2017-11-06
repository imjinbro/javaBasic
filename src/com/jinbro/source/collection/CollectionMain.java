package com.jinbro.source.collection;

/*
    [컬렉션 프레임워크]
    - 객체 자료구조를 API로 제공함 : 인터페이스와 인터페이스 구현 클래스 제공
    - 주요 인터페이스
    1) List : 선형자료구조(순서O), 중복O
    2) Set : 순서X, 중복X
    3) Map : key-value 쌍으로 저장, 순서X, key는 중복되면 X / value는 중복되어도 상관X
    0) Collection 인터페이스 > List, Set : List와 Set은 Collection으로 묶임
    - 같은 메서드가 많으니 하나의 타입(Collection)으로 묶음 : 타입에 대한 의미랄까
    - 제네릭 사용 : 타입인자 넘겨주지않으면 기본적으로 Object 타입, 캐스팅/안전성을 위해서 사용하는 것이


    [Iterator : Collection 인터페이스 구현객체만 - List, Set]
    1) 자료구조에 저장된 데이터(객체)를 가져오는 표준 방법
    - 저장되어있는 객체를 하나씩 가져올 수 있음, 여러 메서드들을 제공함
    - Collection 구현객체의 iterator, listIterator 메서드로 Iterator 객체를 얻을 수 있음 : 메서드 사용
    - ForEach로 간단하게 루프돌릴 수 있음 : Collection<T>를 파라미터로 설정한 forEach 내장 메서드

    2) Collection Interface(타입) 구현 객체만 사용할 수 있음 : 같은 기능을 하는...묶음
    - List
    - Set

    3) Iterator 메서드만 사용할 것이라면 ? 와일드카드를 사용하면 됨
    - 타입이 그렇게 중요하지않다면


    [Comparable, Comparator]
    1) 레퍼런스 타입 정렬 기준 구현 메서드 제공 인터페이스
    - compareTo(Object o) : Comparable
    - compare(Object o1, Object o2) : Comparator

    2) TreeSet, TreeMap의 경우는 반드시 구현한 객체를 타입파라미터로
 */

public class CollectionMain {
    public static void main(String[] args) {
        System.out.println("설명, 코드는 각 .java : List / Set / Map");
    }
}


