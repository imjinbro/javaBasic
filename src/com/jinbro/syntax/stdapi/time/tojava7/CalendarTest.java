package com.jinbro.syntax.stdapi.time.tojava7;

/*
    [Calendar + TimeZone]
    1) 날짜와 시간을 얻기위한 유틸리티
    2) abstract 클래스 : 인스턴스 생성X, extends -> 구현 클래스 인스턴스 생성
    - 국가, 문화, 지역마다 날짜, 시간 계산 방법이 다르기때문에 계산에 필요한 메서드, 필드만 선언되어있음 : abstract인 이유
    - 하위 클래스에서 구현하도록 되어있음 : 특별한 역법 사용하지않는다면 getInstance로 Calendar 타입 인스턴스 얻을 수 있음
    - getInstance 인자로 locale 지정해주면 특정 위치에 따른 시간, 포맷 설정 : Timezone 객체 사용

    3) Date 와 Calendar 차이
    - Date : 시스템의 시간 정보를 그대로 가져오는 목적이라면
    - Calendar : 관련 값(상수 필드)을 가져와서 직접 구현
*/

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarTest {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();

        //날짜 계산에 필요한 필드는 static final int로 선언
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(cal.get(Calendar.HOUR));
        System.out.println(cal.get(Calendar.AM_PM)); // AM: 0, PM: 1

        /* 요일마다 이벤트! */
        int week = Calendar.DAY_OF_WEEK;
        switch (week){
            case Calendar.MONDAY:

                break;

            case Calendar.TUESDAY:

                break;

            case Calendar.WEDNESDAY:

                break;
        }

        /* 시스템 변경 없이 : 사용 가능한 로케일 리스트 요청 */
        String[] list = TimeZone.getAvailableIDs();
        for(String element : list){
            System.out.println(element);
        }

        //Date 인스턴스 얻는 getTime()을 쓰면 시스템 시간을 얻어옴
        Calendar rome = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
        //System.out.println(rome.getTime()); (x)
        System.out.println(rome.get(Calendar.HOUR));
    }
}

