package com.jinbro.source.stdapi.test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Question {
    public static void main(String[] args) {
        TStudent s1 = new TStudent(10);
        TStudent s2 = new TStudent(10);

        System.out.println(s1.hashCode() + ", " + s2.hashCode());
        System.out.println(s1.equals(s2));


        try{
            Class clazz = Class.forName("com.jinbro.source.stdapi.test.TStudent");
            TStudent s3 = (TStudent)clazz.newInstance(); //기본생성자 생성해둬야..

            Method[] methods = clazz.getMethods();

        }catch(ClassNotFoundException | IllegalArgumentException | IllegalAccessException | InstantiationException e){
            System.out.println(e.getMessage());
        }


        byte[] bytes = {73, 32, 108, 111, 118, 101, 32, 121, 111, 117};
        String msg = new String(bytes);
        System.out.println(msg);


        String target = "모든 프로그램은 자바 언어로 개발될 수 있다.";
        target = target.replaceAll("자바", "Java");
        System.out.println(target);


        String str = "아이디,이름,패스워드";
        StringTokenizer stz = new StringTokenizer(str, ",");
        while(stz.hasMoreTokens()){
            System.out.println(stz.nextToken());
        }


        StringBuilder sb = new StringBuilder();
        for(int i=1; i<=100; i++){
            sb.append(i);
        }
        System.out.println(sb.toString());


        String id = "5Angel1004";
        String regex = "[a-zA-Z][a-zA-Z_0-9]{7,12}";
        boolean isMatch = Pattern.matches(regex, id);
        System.out.println(isMatch);


        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 300;
        Integer i4 = 100;
        System.out.println(i1 == i2);
        // 명세에 있는 규칙에 의해 -128 ~ 127 범위는 값비교, equals를 오버라이딩해서 비교하는 것이 좋음


        String strData1 = "200";
        int intData1 = Integer.parseInt(strData1);
        int intData2 = 150;
        String strData2 = String.valueOf(intData2);


        SimpleDateFormat sdf = new SimpleDateFormat("YYYY년 MM월 dd일 E요일 hh시 mm분"); //DD(1~365일)
        System.out.println(sdf.format(new Date()));
    }
}


class TStudent {

    private int num;

    public TStudent(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TStudent){
            TStudent compare = (TStudent)obj;
            return (compare.num == num);
        } else {
            return false;
        }
    }
}


class TMember {
    private String id;
    private String name;

    public TMember(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return id + ": " + name;
    }
}
