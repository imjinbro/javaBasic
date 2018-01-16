package com.jinbro.syntax.collection;

/*
    [Set]
    1) 순서 유지(in-out 순서 다름)없고 객체 중복 저장X : 주머니에 넣어놓은 것과 같음
    2) List와 달리 index로 관리하지않음 : 객체 비교를 통해 동일한 것이 있는지 삭제할건지 명령
    3) Set<E> 구현객체
    - HashSet
    (1) 동일한 객체 저장X
    (2) hashCode() : 저장하려는객체.hashCode() 호출 결과와 저장되어있는객체.hashCode() 비교
    (3) equals() : hashCode 비교 결과 동일한 해시코드가 있다면 equals로 비교함(true인 경우 저장X)
    (4) String : 같은 문자열인 경우 hashCode 동일한 해시코드, equals true가 나오도록 오버라이딩 해놓음
    (5) 내부적으로 HashMap을 사용 : 데이터를 key로 저장
    (6) 동기화X

    - LinkedHashSet
    - TreeSet
    (1) 이진트리 : 계층관계 표현(자동 정렬), 일렬로된 자료구조보다 더 빠른 탐색 속도(O(lgN) 정도)
    (2) TreeSet만 가진 메서드를 사용하기위해 인스턴스 주소값을 TreeSet 타입 변수에 저장해야
    (3) 오름차순 정렬(기본)
    - 객체에 Comparable 구현하거나 따로 Comparator 구현 객체를 만들어야함
    - 내림차순 정렬된 TreeSet을 반환받을 수 있는 메서드가 있음 : 리턴타입은 NavigableSet<T>
    - 내림차순 정렬 Iterator도 리턴받을 수 있음

    (4) 정렬 - 검색에 특화되어있음 : 관련 메서드가 많음
 */
import java.util.*;
import java.util.function.Function;

public class SetTest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("Java");
        set.add("Java");
        set.add("Java");
        set.add(new String("Java"));
        set.add("Java"); //hashCode -> equals
        set.add("Spring");
        set.add("Android");

        System.out.println(set.size());

        Iterator<String> iterator1 = set.iterator();
        while(iterator1.hasNext()){
            System.out.println(iterator1.next());
        }

        System.out.println("===== Collection<T> : List<T>, Set<T> Print=====");
        printCollection(set);


        /*
            [같은 id를 지정하면 추가안되도록 자료구조 차원에서 막아버리기? : 물론 이렇게 안씀..]
            - hashCode와 equals 오버라이딩을 봐야함
            -  Objects.hashCode(필드) : 필드값으로 해시코드를 만들어냄
        */
        Set<Member> members = new HashSet<>();
        members.add(new Member("jinbro"));
        members.add(new Member("jinbro"));

//      members.add(null); //null도 추가됨 (그러나 forEach와 같이 돌 때 null 처리를 확실하게 해줘야, size에 불포함
        System.out.println(members.size());


        /* 람다식으로 print 부분 교체해가면서 사용하기 */
        printCollection2(members, member -> "해시코드 : " + member.hashCode());
        printCollection2(members, member -> "회원정보 : " + member.toString());

        TreeSet<Character> stringTreeSet = new TreeSet<>();
        stringTreeSet.add('a');
        stringTreeSet.add('c');
        stringTreeSet.add('d');
        stringTreeSet.add('e');
        stringTreeSet.add('b');

        System.out.println(stringTreeSet.first());
        System.out.println(stringTreeSet.last());
        System.out.println(stringTreeSet.pollFirst());

        TreeSet<Integer> scores = new TreeSet<>();
        scores.add(95);
        scores.add(97);
        scores.add(85);
        scores.add(70);
        scores.add(89);
        scores.add(68);
        scores.add(94);

        System.out.println("가장 낮은 점수 : " + scores.first());
        System.out.println("가장 높은 점수 : " + scores.last());
        System.out.println("95점 보다 낮은 점수 중 가장 가까운 것 : " + scores.lower(95));
        System.out.println("95점 이상인 점수 중 가장 가까운 것 : " + scores.ceiling(95));
        //System.out.println("가장 낮은 점수를 트리에서 꺼냄 : " + scores.pollFirst());
        System.out.println("범위 검색 : " + scores.headSet(95, true));

        //와일드카드 : Integer를 활용하는 것이 아니라 Iterator의 메서드만 사용할 것이라서 어떤 타입이와도 상관없음
        Iterator<?> iterator3 = scores.descendingIterator();
        System.out.println("내림차순 정렬 후 가장 낮은 우선순위에 있는 점수 : " + iterator3.next());

        NavigableSet<Integer> descendingScores = scores.descendingSet();
        System.out.println("우선순위가 가장 낮은 점수 : " + descendingScores.first());

        TreeSet<String> words = new TreeSet<>();
        words.add("forever"); words.add("apple"); words.add("ever"); words.add("orange"); words.add("base");
        System.out.println(words.subSet("a", true,"f", true));


        /* Comparable - compareTo 정렬 */
        TreeSet<Person> pTree = new TreeSet<>();
        pTree.add(new Person("jinbro", 20));
        pTree.add(new Person("jinbro", 26));
        pTree.add(new Person("kim", 37));
        pTree.add(new Person("momo", 21));
        pTree.add(new Person("dahyun", 19));

        System.out.println(pTree.first().toString());


        /* Comparator 정렬 */
        TreeSet<Fruit> fruitTree = new TreeSet<>(new DescendingComparator());
        fruitTree.add(new Fruit("사과", 1000));
        fruitTree.add(new Fruit("딸기", 5000));
        fruitTree.add(new Fruit("배", 1500));
        fruitTree.add(new Fruit("오렌지", 2500));

        System.out.println("==== [싼 과일부터 출력] ====");
        for(Fruit f : fruitTree){
            System.out.println(f.getName());
        }
    }



    static <T> void printCollection(Collection<T> data){
        for(T t : data){
            System.out.println(t);
        }
    }

    static <T> void printCollection2(Collection<T> data, Function<T, String> function){
        for(T t : data){
            System.out.println(function.apply(t));
        }
    }
}

/* none implements Comparable */
class Fruit {
    private String name;
    private int price;

    public Fruit(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

class DescendingComparator implements Comparator<Fruit> {
    @Override
    public int compare(Fruit o1, Fruit o2) {
        /* o1 앞자리, o2 뒷자리 : 가격 낮을 때 -1 - 오름차순 */
        if(o1.getPrice() < o2.getPrice()){
            return -1;
        } else if(o1.getPrice() > o2.getPrice()){
            return 1;
        } else {
            return 0;
        }
    }
}


/* implements Comparable */
class Person implements Comparable<Person> {

    public static void main(String[] args) {
        Person jinbro = new Person("jinbro", 20);
        Person jinhyung = new Person("jinhyung", 27);

        System.out.println(jinbro.compareTo(jinhyung) > 0 ? "상대적으로 늙었어요ㅠ" : "상대적으로 어려요");
    }

    private String name;
    private int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person person) {
        if(age > person.age){
            return 1;
        } else if(this.age < person.age){
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
