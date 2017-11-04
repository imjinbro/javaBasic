package com.jinbro.source.collection;

/*
    [컬렉션 프레임워크]
    - 객체 자료구조를 API로 제공함 : 인터페이스와 인터페이스 구현 클래스 제공
    - 주요 인터페이스
    1) List : 선형자료구조(순서O), 중복O
    2) Set : 순서X, 중복X
    3) Map : key-value 쌍으로 저장, 순서X, key는 중복되면 X / value는 중복되어도 상관X
    0) Collection > List, Set : List와 Set은 Collection으로 묶임
    - 같은 메서드가 많으니 하나의 타입(Collection)으로 묶음 : 타입에 대한 의미랄까

    -제네릭 사용 : 타입인자 넘겨주지않으면 기본적으로 Object 타입, 캐스팅/안전성을 위해서 사용하는 것이

    [List]
    1) 선형으로 자료(객체)를 저장, 객체 참조값(메모리주소, null 저장할 경우 없는 것으로)만 저장함
    2) List<E> 구현 객체
    - ArrayList<E>
    (1) 내부적으로 배열을 사용하고, 최초 크기 초과할 경우 더 큰 크기의 새로운 배열을 생성함(비효율적일 때가)
    (2) 중간 삭제, 추가 할 때 밀고 당기기를 구현하지않아도 알아서 해줌 : 그러나 비효율적이라는 것
    (3) 검색과 마지막 추가라면 LinkedList 보다 효율적 : 인덱스 검색, 배열 마지막 인덱스에 저장만 해주면되니깐


    - Vector<E>
    (1) ArrayList와 동일한 내부구조
    (2) 차이점 : synchronized 처리된 메서드 - 쓰레드 세이프(멀티쓰레드 공유객체에 접근 시 먼저 접근한 객체에 접근권한)


    - LinkedList<E>
    (1) List를 구현한 객체지만 ArrayList/Vector와는 전혀 다른 내부구조
    (2) 앞 뒤 노드(데이터를 저장한 객체)를 연결한 형태 : 객체 간의 연결
    (3) 중간 삭제, 추가 작업을 하더라도 인덱스를 뒤로 밀거나 앞당기거나 하지않고 연결된 부분만 끊음
    - 잦은 삽입/삭제 작업에는 효율적 : 특히 중간작업
    - 탐색 작업에는 상대적으로 비효율적 : 연결된 형태여서 하나씩 타고 넘어가면서 맞는지 확인


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


    [Map]
    1) 순서유지X, key-value(둘 다 객체)쌍으로 저장, key는 중복되면 안됨
    2) 제네릭 타입 : Map 인스턴스 생성할 때 타입 인자를 받아서 지정
    3) key - value 목록을 얻는 방법은 예제 코드에
    4) Map<E> 구현객체
    - HashMap
    (1) key : hashCode, equals로 같은 key인지 확인, 주로 String을 사용
    (2) Entry : 저장된 단위로 보면 됨

    - HashTable
    (1) HashMap과 동일하지만 ArrayList - Vector처럼 메서드 동기화 지원 여부 차이
    (2) 멀티쓰레드 환경에서는 HashTable 사용

    - Properties
    (1) HashTable 하위 클래스 : HashTable<String,String> 버젼이라고 볼 수 있음
    (2) K,V를 String으로 제한함 : 제네릭타입이 아니라 String 고정
    (3) 유일 key에 대한 정보를 저장 -> 옵션정보, db 연결정보, 다국어 정보 파일(프로퍼티 파일)을 읽어볼 때 사용한다고함
    - 프로퍼티파일(.properties) : 키=값 으로 구성, 한글은 유니코드로 변환 저장, 사용하는 클래스와 같은 경로에 저장

    (4) Properties 메서드가 많아서 Map 타입으로 변수 선언하지않고 Properties로 선언하는게

    - LinkedHashMap
    - TreeMap


    [Iterator]
    1) 자료구조에 저장된 데이터(객체)를 가져오는 표준 방법
    - 저장되어있는 객체를 하나씩 가져올 수 있음, 여러 메서드들을 제공함
    - Collection 구현객체의 iterator, listIterator 메서드로 Iterator 객체를 얻을 수 있음 : 메서드 사용
    - 굳이 Iterator를 사용하지않아도 ForEach문으로 가능 : Collection<T>를 파라미터로 설정한 forEach 내장 메서드

    2) Collection Interface(타입) 구현 객체만 사용할 수 있음 : 같은 기능을 하는...묶음
    - List
    - Set

 */


import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class CollectionMain {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        //List<String> list = Arrays.asList("1", "2");
        list.add("1");
        list.add("2");
        list.remove(0);

        /* forEach문 사용해서 바로 리스트에 저장된 객체꺼내서 사용 */
        for(String str : list){
            System.out.println(str);
        }

        System.out.println(list.get(0));


        List<Board> posts = new Vector<>();
        posts.add(new Board("제목1", "글쓴이A", "내용"));
        posts.add(new Board("제목2", "글쓴이B", "내용"));
        posts.add(new Board("제목3", "글쓴이C", "내용"));

        /* 첫번째로 저장되어있는 글을 가져와서 수정작업을 하는데... 다른 쓰레드에서 요청을 하면 Blocking */
        Board target = posts.get(0);
        target.setContent("내용ㅋㄷㅋㄷ");


        /* String 오버라이딩 */
        String str1 = "문자열A";
        String str2 = "문자열A";
        System.out.println(str1.hashCode() + ", " + str2.hashCode());
        System.out.println(str1.equals(str2));

        /* Object 선언되어있는 그대로 */
        Board postA = new Board("제목", "글쓴이", "내용");
        Board postB = new Board("제목", "글쓴이", "내용");
        System.out.println(postA.hashCode() + ", " + postB.hashCode());
        System.out.println(postA.equals(postB));


        /* Iterator 객체를 사용하는 것과 forEach문을 사용하는 것 */
        Iterator<Board> iterator = posts.listIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().getSubject());
        }

        for(final Board post : posts){
            System.out.println(post.getContent());
        }


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

//        members.add(null); //null도 추가됨 (그러나 forEach와 같이 돌 때 null 처리를 확실하게 해줘야, size에 불포함
        System.out.println(members.size());


        /* 람다식으로 print 부분 교체해가면서 사용하기 */
        printCollection2(members, member -> "해시코드 : " + member.hashCode());
        printCollection2(members, member -> "회원정보 : " + member.toString());


        Map<String, Board> boards = new HashMap<>();
        /* key 1개 당 1개의 데이터를 저장할 수 있나봄 : 덮어쓰기됨 */
        boards.put("jinbro", new Board("제목A", "글쓴이A", "내용A"));
        boards.put("jinbro", new Board("제목B", "글쓴이A", "내용B"));
        boards.put("jinbro", new Board("제목C", "글쓴이A", "내용C"));

        Board board = boards.get("jinbro");
        System.out.println(board.getContent());

        /* Map 키 목록 얻고 값 목록 얻기 1번째 방법  */
        System.out.println("======= 키 - 값 목록 첫번째 방법(key 얻고 value 탐색) =======");
        Set<String> keySet = boards.keySet();
        Iterator<String> iterator2 = keySet.iterator();
        while(iterator2.hasNext()){
            String key = iterator2.next();
            System.out.print("키 : " + key + ", ");
            System.out.print("값 : " + boards.get(key).getContent() + "\n");
        }

        System.out.println("======= 키 - 값 목록 두번째 방법 : 한꺼번에 얻기(Map.Entry) =======");
        Set<Map.Entry<String,Board>> entries = boards.entrySet();
        Iterator<Map.Entry<String,Board>> entryIterator = entries.iterator();
        while(entryIterator.hasNext()){
            Map.Entry<String,Board> entry = entryIterator.next();

            String key = entry.getKey();
            System.out.print("키 : " + key + ", ");
            System.out.print("값 : " + boards.get(key).getContent() + "\n");
        }


        System.out.println("====== 프로퍼티 파일 읽기 ======");
        /* 프로퍼티 파일 로드 -> 설정키=값 <String,String> 읽어오기 */
        Properties properties = new Properties();
        try {
            properties.load(
                new FileReader("/Library/Java/JavaVirtualMachines/jdk-9.0.1.jdk/Contents/Home/lib/javafx.properties")
            );

            /*
                [파일 경로 찾기]
                1) 클래스 경로로 찾기 : 클래스명.class.getResource("프로퍼티파일명.properties").getPath();
                - 경로에 한글이 있을 경우 : URLDecoder.decode(path, "utf-8")
                - new FileReader(path)

                2) 클래스 경로로 찾지만 다른 경로에 있을 때 : 상대적인 경로로 찾기
                - 클래스명.class.getResource("상대경로/프로퍼티파일명.properties").getPath();
             */

            String javafxVersion = properties.getProperty("javafx.version");
            System.out.println("javafx version : " + javafxVersion);
        } catch (IOException e) {
            System.out.println("파일을 찾지 못함");
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

class Member{
    private String id;

    public Member() { }

    public Member(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == this.hashCode();
    }


    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                '}';
    }
}

class Board{
    private String subject;
    private String author;
    private String content;

    public Board() { }

    public Board(String subject, String author, String content) {
        this.subject = subject;
        this.author = author;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
