package com.jinbro.source.stdapi.string;

import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
        /*
            [간단한 정규표현식]
            1) [ ] : 1개의 문자, [abc] : a b c 중 1개 / [a-zA-Z] a~z, A~Z 중 1개
            2) /d : 1개의 숫자, [0-9]와 동일
            3) \s : 공백
            4) \w : 1개의 알파벳 또는 1개의 숫자, [a-zA-Z_0-9]와 동일
            5) ? : 없음 또는 1개
            6) * : 없는 또는 1개 이상
            7) + : 1개 이상
            8) {n} : n개
            9) {n,} : 최소 n개 이상
            10) {n,m} : n개에서 m개까지
            11) () : 그룹핑
            12) \. : .
            13) . : 모든 문자 중에서 1개의 문자

            폰번호 : 010-\d{4}-\d{4}
            이메일 : [a-z_0-9]+@[a-z_0-9]+\.[a-z_0-9]
        */

        // matches : 정규표현식과 값을 비교했을 때 정규표현식에 맞는 표현인지 체크해줌, static 메서드
        String emailRegex = "[a-z_0-9]+@[a-z_0-9]+\\.[a-z_0-9]+" ;
        System.out.println(Pattern.matches(emailRegex, "jinbro@likelion.org"));
    }
}
