package com.jinbro.syntax.collection;

import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

public class CollectionTest4 {
    /* Tree류는 변수 타입도 Tree로 해줘야 메서드 사용할 수 있음, 객체의 필드로 우선순위비교 : Comparable/Comparator 구현 */
    public static void main(String[] args) {
        Library busanjingu = new Library();
        busanjingu.addBook(new Book("이것이 자바다", "1q2w3e4r5t"));
        busanjingu.addBook(new Book("스프링 입문을 위한 자바 객체지향의 원리와 이해", "6y7u8i9o"));
        busanjingu.addBook(new Book("열혈자료구조", "5t6y7u"));
        busanjingu.addBook(new Book("가장 쉬운 하스켈 책", "3e4r5t"));

        busanjingu.getFirst();
        busanjingu.getLast();
    }

}

class Library{
    private TreeSet<Book> books = new TreeSet<>();

    public boolean addBook(Book newBook){
        return books.add(newBook);
    }

    public void getBookList(){
        if(books.isEmpty()){
            return;
        }

        System.out.println("========== [책 정보] ==========");
        Iterator<Book> iterator = books.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
        System.out.println("=============================");
    }

    public void getFirst(){
        System.out.println("첫번째 서적 : " + books.first());
    }

    public void getLast(){
        System.out.println("마지막 서적 : " + books.last());
    }

}


class Book implements Comparable<Book> {
    private String name;
    private String isbn;

    public Book(String name, String isbn) {
        this.name = name;
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public String getIsbn() {
        return isbn;
    }


    @Override
    public int compareTo(Book o) {
        if(name.compareTo(o.getName()) > 0){
            return 1;
        } else if(name.compareTo(o.getName()) < 0){
            return -1;
        } else {
            return 0;
        }
    }


    @Override
    public boolean equals(Object o) {
        Book other = (Book)o;
        return isbn == other.getIsbn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}