package com.jinbro.source.generic;

public class GenericExam01 {
    public static void main(String[] args) {

    }
}

class Course<T>{

    private String name;
    private T[] students;

    private int addNum;

    public Course(String name, int capacity) {
        this.name = name;
        students = (T[])new Object[capacity];
        addNum = 0;
    }

    public String getName(){
        return name;
    }

    public T[] getStudentList(){
        return students;
    }

    public void addStudent(T student){
        if(addNum == students.length){
            System.out.println("정원 초과");
            return;
        }

        students[addNum] = student;
        addNum++;
    }

}
