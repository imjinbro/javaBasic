package com.jinbro.basic.oop.principle;

/*
    [캡슐화]
    - 객체 간의 결합도를 낮추는 객체지향 설계 원리 : 결합도가 높을수록 변경에 유연하지못함
    - 기능이 돌아가는 내부는 은닉하고 외부에서 기능을 요청하는 인터페이스(메서드) 공개
    - 굳이 Stack에 push할 때 내부에서 어떤 자료구조가 사용되는지 알 필요X : 인터페이스만 호출하면 됨
    - 자바 : 접근제어자

 */
public class Encapsulation {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(10);
        stack.push(5);
    }
}


class ArrayStack{
    private final int capacity;
    private int[] stack;

    private int top;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        stack = new int[capacity];

        top = 0;
    }

    public boolean isFull(){
        return top == capacity;
    }

    public boolean isEmpty(){
        return top == 0;
    }

    public void push(int data){
        if(isFull()){
            System.out.println("꽉 찼어요");
        }

        stack[top++] = data;
    }

    public int pop(){
        if(isEmpty()){
            System.out.println("비었어요");
            return -1;
        }

        return stack[top--];
    }

    public int peek(){
        if(isEmpty()){
            System.out.println("비었어요");
            return -1;
        }

        return stack[top];
    }
}


