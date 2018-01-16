package com.jinbro.syntax.collection;

/*
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
 */

import java.util.*;

public class ListTest {
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
