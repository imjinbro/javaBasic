package com.jinbro.syntax.collection;

import java.util.Stack;

/*
    [Stack]
    1) 선입후출 구조 : 먼저 들어온 것이 가장 늦게 나감
    2) 스택의 위와같은 특징을 활용하면 가장 뒤에 입력된 것이나 가장 가까운 것을 찾아내기가 쉬움
    3) JVM 스택메모리가 대표적인 예
    4) Vector 상속 클래스 : synchronized 메서드 - pop, peek, search
 */
public class StackTest {
    public static void main(String[] args) {
        Stack<Coin> coinBox = new Stack<>();
        coinBox.push(new Coin(100));
        coinBox.push(new Coin(100));
        coinBox.push(new Coin(500));
        coinBox.push(new Coin(100));
        coinBox.push(new Coin(500));

        while(!coinBox.isEmpty()){
            System.out.println("꺼낸 동전 : " + coinBox.pop().toString() +"원짜리 동전");
        }
    }
}

class Coin{
    private int value;

    public Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
