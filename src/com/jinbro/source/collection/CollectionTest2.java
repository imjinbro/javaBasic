package com.jinbro.source.collection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CollectionTest2 {
    public static void main(String[] args) {
        /* 동일한 학번이면 동일한 데이터로 취급해서 추가안됨 */
        Set<Student> students = new HashSet<>();
        students.add(new Student("jibro", 2011345678));
        students.add(new Student("jinhyung", 2011345678));

        System.out.println("[학생 정보]");
        for(Student s : students){
            System.out.println("이름 : " + s.getName() +", 학번 : " + s.getNum());
        }

        System.out.println("총 학생 수 : " + students.size());
    }
}

class Student{
    private String name;
    private long num;

    public Student(String name, long num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }


    /* Hash 자료구조 hashCode 체크 -> equals 체크 : equality 체크(객체 주소값 동일 아니라) */
    @Override
    public int hashCode() {
        return Objects.hash(num);
    }

    @Override
    public boolean equals(Object o) {
        Student other = (Student) o;
        return num == other.getNum();
    }
}
