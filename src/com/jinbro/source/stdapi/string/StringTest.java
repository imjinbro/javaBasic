package com.jinbro.source.stdapi.string;

import java.io.UnsupportedEncodingException;

public class StringTest {
    public static void main(String[] args) {

        /* byte[]을 String으로 : 네트워크 통신 데이터 byte[](String의 생성자는 여러개) */
        byte[] data = {72, 101, 108, 108, 111};
        String msg = new String(data);//byte -> char unicode
        System.out.println(msg);
        /**** 주의할점 : 알파벳은 1바이트, 한글을 포함한 영어 이외 언어는 2바이트 ****/


        //charAt(idx) : 문자열 중 특정 index 문자를 리턴함, String은 char[]를 래핑
        System.out.println(msg.charAt(0));


        /* equals
           1) 문자열의 equals는 객체가 가진 데이터값(문자열 요소)을 비교함 : String에서 equals를 오버라이딩
           2) 문자열 값을 비교할 때 ==을 쓰면 참조 객체 주소 비교가 되어버려서 원치않은 결과가 리턴됨
        */
        String msg2 = new String("Hello");
        System.out.println(msg.equals(msg2)); // true


        /* getBytes
           1) byte[]로 리턴
           2) 네트워크 전송용, 문자열 암호화
         */
        String msg3 = "안녕하세요";
        byte[] data3 = null;
        byte[] data4 = null;

        try {
            //한글 인코딩 Charset에 따라서 길이가 다름
            data3 = msg3.getBytes("UTF-8");
            data4 = msg3.getBytes("EUC-KR");
            //EUC-KR, 배열 초기화 크기를 유동적으로, 지정해주지않으면 시스템 기본 Charset을 사용함
        /*
            [데이터가 왔다리 갔다리할 때 Charset을 신경써야한다]
            EUC-KR : 한글 2바이트, UTF-8 : 한글 3바이트 - 어떤 차이일지 찾아보기
            - 공간 상으로는 EUC-KR이 좋은 것 같은데 다시 UTF-8 가고 있다던데
            - https://groups.google.com/forum/#!topic/clojure-kr/R1cRgy9Zugk

            1) Unicode : 세상 모든 문자를 나타내는 코드, 2바이트로 표현 - /u0000 ~ /uffff
            1-2) utf-8 : 1~4바이트 가변길이 인코딩 방식(기존 아스키코드를 사용하던, 1바이트로 알파벳을 나타낼 수 있었던 영어권 나라...)
            - 웹표준을 관리하는 W3C에서 권장
            - 한글은 3바이트
            - 자바 String 객체 : UTF-16

            2) EUC-KR : 한국에서 한글을 표현하기위해서 사용한 인코딩 방식

          */
            System.out.println("UTF-8 leng : " + data3.length + ",  EUC-KR leng : " + data4.length);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(int i=0; i<data3.length; i++){
            System.out.println(data3[i]);
        }


        /*
            indexOf
            1) 주어진 문자열(파라미터)의 위치(인덱스)를 리턴
            2) 없으면 -1
            3) 파라미터 문자열이 모두 일치해야 0을 리턴
         */
        int idx = msg.indexOf("lo");
        if(idx < 0){
            System.out.println("없어요");
        } else {
            System.out.println("찾았어요");
        }


        //length : 문자열 길이 리턴(공백도 문자열이다 : \u0009
        System.out.println(msg.length());


        /*
            replace
            1) target 부분을 replacement로 대치한 새로운 문자열 리턴, Pattern, Matcher 클래스(java.util.regex)사용
            2) 파라미터 타입은 CharSequence : String이 구현했음, CharSequence 인터페이스 메서드 목록보기
         */
        String newMsg = msg.replace("H", "h");
        System.out.println(msg + ", " + newMsg);


        // substring : 원하는 위치까지, 원하는 위치부터 잘라낸 문자열을 리턴함(인덱스 잘못지정하면 익셉션 발생)
        try{
            String splitMsg = msg.substring(10);
        } catch(StringIndexOutOfBoundsException e){
            /* String 전용 익셉션 : IndexOutOfBoundsException 상속 */
            System.out.println(e.getMessage());
        }


        /*
            toLowerCase, toUpperCase
            1) 문자열을 소문자 혹은 대문자로 변경해서 새로운 문자열로
            2)
        */
        String upperMsg= msg.toUpperCase();
        String lowerMsg = msg.toLowerCase();
        System.out.println("원래 : " + msg + ", 대문자 : " + upperMsg + ", 소문자 : " + lowerMsg);


        //trim : 앞 뒤 공백문자 없애줌
        String trimMsg = " 안녕하세요    ".trim();
        System.out.println(trimMsg);


        //valueOf : primitive val -> String, static method
        int num = 10;
        String trans = String.valueOf(num);
        System.out.println(trans);


    }
}
