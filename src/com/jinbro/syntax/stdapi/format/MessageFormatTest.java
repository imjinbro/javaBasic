package com.jinbro.syntax.stdapi.format;

/*
    - 문자열 형식 포맷팅하기
    - 네트워크할 때는 json이지! : gson을 쓰자! 간단하게 json으로 포맷팅해준다! 역포맷팅도 된다!
    - SQL문 작성할 때 편하겠음

*/

import java.text.MessageFormat;

public class MessageFormatTest {
    public static void main(String[] args) {
        String id = "1234";
        String name = "박진형";

        String txt = "회원 ID : {0} \n회원 이름 : {1}";
        String msg = MessageFormat.format(txt, id, name);
        System.out.println(msg);
    }
}
