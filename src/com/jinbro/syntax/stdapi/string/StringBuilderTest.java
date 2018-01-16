package com.jinbro.syntax.stdapi.string;

public class StringBuilderTest {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder(30);
        sb.append('c');
        sb.append('h');
        sb.append('a');
        sb.append('r');

        sb.insert(0, 'x');
        sb.deleteCharAt(sb.length()-1);

        String result = sb.toString();
        System.out.println(result);
    }
}
