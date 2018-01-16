package com.jinbro.syntax.nested;

public class NestedTest {
    public static void main(String[] args) {
        Monster m1 = new Monster("Skelleton");
        Monster.Eat m2 = new Monster.Eat();
        m2.feed();

    }
}
