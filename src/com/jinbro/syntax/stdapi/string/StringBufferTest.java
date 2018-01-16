package com.jinbro.syntax.stdapi.string;

public class StringBufferTest {
    public static void main(String[] args) {

        /*
            StringBuffer
            1) 기본적으로 16 길이 지정 : 버퍼 사이즈 설정
            2) sb.append 등 변경 작업 결과 : this 리턴(메서드 체이닝 : 자기 자신 리턴 - 호출 - 리턴 - 호출 반복)
         */
        StringBuffer sb = new StringBuffer().append(1);

        String result = sb.toString();
        System.out.println(result);

    }
}
