package com.jinbro.source.fp;

import java.util.function.Consumer;

public class ConsumerExam {

    public static void main(String[] args) {
        Consumer<Student> consumer1 = person -> System.out.println("학생의 이름 : " + person.getName());
        Consumer<Student> consumer2 = person -> System.out.println("학생 번호 : " + person.getNumber());

        /* 2개를 실행할 때 : andThen 사용 */
        Consumer<Student> total = consumer1.andThen(consumer2);
        info(new Student("jinbro", 1), total);
    }

    static <T> void info(T t, Consumer<T> consumer){
        consumer.accept(t);
    }
}

class Student {
    private String name;
    private int number;

    public Student() { }

    public Student(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
