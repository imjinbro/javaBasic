package com.jinbro.syntax.stdapi.format;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
    - Date 객체와 함께 사용
*/
public class SimpleDateFormatTest {
    public static void main(String[] args) {

        /*
            인스턴스 생성 시 포맷 패턴
            1) y: 년
            2) M: 월
            3) d: 일
            4) D: 일(365일)
            5) E: 요일
            6) a: 오전/오후
            7) H: 시(24시간제)
            8) h: 시(12시간제)
            9) m: 분
            10) s: 초

            그 외 년의 몇번째 주, 월의 몇번째 주 등이 있음

        */
        String[] pattern = {
                "yy년 M월 d일 H시 m분",
                "D번째 날"
        };

        SimpleDateFormat sdf = null;
        Date now = null;
        for(int i=0; i<pattern.length; i++){
            sdf = new SimpleDateFormat(pattern[i]);
            now = new Date();

            System.out.println(sdf.format(now));
        }

    }
}
