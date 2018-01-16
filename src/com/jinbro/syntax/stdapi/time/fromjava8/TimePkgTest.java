package com.jinbro.syntax.stdapi.time.fromjava8;

/*
    - 날짜와 시간과 관련해서 권장하지않는 방법을 대체하고 기능을 확장한 새로운 패키지
    - 정보 얻기 + 세팅(변경) + 연산 + 파싱 모두 한 패키지 내에 있음 : 이전에는 각각 패키지에 있었던 것들을 모아놓음
    - 파싱해서 타입 변환시킴 : 타입의 메서드 사용
    (1) TimeFormat : http://docs.oracle.com/javase/8/docs/api/ DateTimeFormatter 참고
    (2) 사용자 지정 포맷을 사용해도 됨
    (3) 특정 타입으로 변환시키는 것은 특정 타입이 가지는 메서드를 사용하기위해서
    (4) 특정 타입을 String으로 변환시킬 수 있음 : 포맷에 맞게
*/

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

public class TimePkgTest {
    public static void main(String[] args) {

        /* LocalDate : 로컬 날짜 클래스 */
        System.out.println(LocalDate.now().toString());
        LocalDate targetDate = LocalDate.of(2017, 1, 1);
        System.out.println(targetDate.toString());
        LocalDate now = LocalDate.now();
        System.out.println(now.getMonth());
        System.out.println(now.minusMonths(1).getMonth());

        //문자열을 파싱해서 LocalDate형으로 변환 : LocalDate의 메서드를 사용할 수 있음
        System.out.println(LocalDate.parse("2017.09.09", DateTimeFormatter.ofPattern("yyyy.MM.dd")).toString());


        /* LocalTime : 로컬 시간 클래스 */
        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime.toString());
        System.out.println(nowTime.withHour(10)); // 특정 시로 변경
        //String으로 변경 : 특정 포맷으로 포맷팅해서 변환
        System.out.println(nowTime.format(DateTimeFormatter.ofPattern("HH시 mm분")));


        /* LocalDateTime : 로컬 날짜 및 시간 관련 클래스 */
        System.out.println(LocalDateTime.now().toString());


        /* ZonedDateTime : 특정 타임존 날짜 및 시간 클래스, ZoneID로 정보를 얻어오는 클래스와 TimeZone 관련 클래스 함께 사용 */
        String[] list = TimeZone.getAvailableIDs();
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println("Zone ID : " + zdt.getZone());
        System.out.println(ZonedDateTime.now(ZoneId.of(list[0])).toString());


        /* Instant : 특정 시점의 타임스탬프 클래스, 두 시점 간에 우선순위를 따질 때 사용 */
        Instant ts1 = Instant.now();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Instant ts2 = Instant.now(); //Instant형 인스턴스

        if(ts1.isBefore(ts2)){
            /* 앞일 떄 */
        } else if(ts1.isAfter(ts2)){
            /* 뒤일 때 */
        } else {
            /* 같을 떄 */
        }

        System.out.println(ts1.until(ts2, ChronoUnit.NANOS));

    }
}
