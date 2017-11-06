package com.jinbro.source.collection;

/*
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
    (1) 이진트리기반 Map : key-value(Map.Entry)쌍 같이 저장
    (2) key를 기준으로 정렬
    - key로 사용할 객체에 Comparable 구현하거나 따로 Comparator 구현 객체를 만들어야함


    (3) Set<Map.Entry<T,R> val> 키 목록 얻기위해서
 */
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MapTest {
    public static void main(String[] args) {
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
        for (Map.Entry<String, Board> entry : entries) {
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

         /* 요금표 트리맵<요금,교통수단> : 중첩시킬수도 있겠지, 정렬 - 검색 목적이라 key에 요금을 */
        TreeMap<Integer, String> fee = new TreeMap<>();
        fee.put(1300, "Bus");
        fee.put(3500, "Taxi");
        fee.put(1485, "Car");
        fee.put(1200, "Subway");

        System.out.println("기본 요금이 가장 싼 교통 수단과 요금 : " + fee.firstEntry());
        final Set<Map.Entry<Integer,String>> feeSet = fee.entrySet();
        System.out.println("==== 요금 목록 ====");
        for(Map.Entry<?, ?> entry : feeSet){
            System.out.println("[ " + entry.getValue() + " ] " + entry.getKey());
        }

        //내림차순 정렬된 키 목록을 얻어올 수도 있음
        NavigableSet<Integer> keys = fee.descendingKeySet();
        for(int i : keys){
            System.out.println(i);
        }

        /* TreeMap<단어, 수록페이지> */
        TreeMap<String, Integer> dictionary = new TreeMap<>();
        dictionary.put("apple", 10);
        dictionary.put("forever", 40);
        dictionary.put("evermore", 30);
        dictionary.put("zero", 370);
        dictionary.put("banana", 15);

        Set<Map.Entry<String, Integer>> searchList = dictionary.subMap("a", true,"c", true).entrySet();
        for(Map.Entry<?, ?> entry : searchList){
            System.out.println("["+ entry.getKey() + "] : " + entry.getValue() +"페이지 수록");
        }
    }
}
