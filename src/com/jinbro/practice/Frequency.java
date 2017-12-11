package com.jinbro.practice;

public class Frequency {
    public static void main(String[] args) {
        analysis("hello coding world!");
        analysis("jinbro hi!!");
    }

    public static void analysis(String msg){
        char[] data = removeWhiteSpace(msg);
        System.out.println(msg);
        System.out.println("전체길이 : " + msg.length());

        int[] frequency = new int[26];
        for(char c : data){
            if(c >= 'a' && c <= 'z') {
                frequency[c - 'a']++;
            }
        }

        char alphabet = 'a';
        for(int cnt : frequency){
            if(cnt != 0) {
                System.out.println(alphabet + " : " + cnt);
            }
            alphabet++;
        }
    }

    public static char[] removeWhiteSpace(String msg){
        return msg.replace(" ", "").toCharArray();
    }
}
