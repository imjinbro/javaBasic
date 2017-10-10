package com.jinbro.source.stdapi.time;
/*
   [Date + SimpleDateFormat]
    1) 시스템의 날짜, 시간을 읽을 수 있는 유틸리티
    2) 날짜를 주고 받을 때 주로 사용
    3) java.text.SimpleDateFormat 메서드로 특정 포맷 출력 가능 : 패턴 지정해줘야함
    - Format 관련 패키지 : java.text

*/

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) {

        //다양한 생성자가 있지만 권장하지않는 어노테이션(Deprecated)가 붙어있음
        Date now = new Date();
        System.out.println(now.toString());

        SimpleDateFormat sdf = new SimpleDateFormat("YY년 MM월 dd일 hh시 mm분");
        String formatNow = sdf.format(now);
        System.out.println(formatNow);


    }
}





class Member implements Comparator{

    /* 레퍼런스 비교 */
    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}



