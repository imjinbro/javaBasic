/*
    [표준 API]

    [API]
    - Application Programming Interface
    - 자바 어플리케이션 프로그래밍에 있어서 필요한 꾸러미들을 제공해줌
    - 라이브러리
    - 맥 기준 : /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/rt.jar 포함
    - API 문서 : http://docs.oracle.com/javase/8/docs/api/


    [API 패키지]
    1) java.lang : 문법 뿌리
    - 기본적인 클래스 패키지
    - Object : 클래스의 최상위 클래스
    - System : jvm, 표준입출력, 가비지컬렉터
    - Class : 리플렉션
    - String
    - StringBuffer, StringBuilder : 문자열 조작
    - Math : 수학 관련
    - Wrapper : primitive type data 객체 생성 등


    2) java.util : 유틸리티 제공, 패키지별로 기능에 따라 나눠놨음 키야, 좋은 유틸리티가 많은듯
    - Objects : Object의 메서드에서 사용하면 유틸리티
    - Arrays : Array의 메서드에서 사용하면 좋을 유틸리티
    - Calendar, Date
    - StringTokenizer : 특정 문자 구분된 문자열 뽑아낼 때
    - Random
    - Comparator : 비교 대상, 비교 로직 구현할 수 있음(interface라 구현해야함)


    [API 살펴보기] : 어떤 메서드인지(어떤 유틸리티인지) 써보기, 외우는 것보다 어떤 기능이 있는지 알면 나중에 활용하기 좋으니
    1) java.lang.Object
    - 최상위 클래스 : 아무런 관계를 갖지않는 객체타입은 암묵적으로 extends Object
    - 모든 클래스가 extends함 : Object method를 그냥 사용, 오버라이딩할 수도(메서드가 어떻게 돌아가는지 파악)
    (코드)

    (1) equals
    (2) hashCode
    (3) toString
    (4) clone
    (5) finalize
    (코드)


    2) java.util.Objects
    - Object 메서드에서 사용하면 좋을 유틸리티, 오버라이딩 말고도 사용해도 상관은 없음

    (1) compare
    (2) equals, deepEquals
    (3) hash
    (4) isNull, nonNull, requireNonNone
    (5) toString
    (코드)


    3) java.lang.System
    - jvm 위에서 실행되기때문에 운영체제의 모든 기능을 이용할 순 없지만 System으로 운영체제 일부 기능을 사용할 수 있음

    (1) exit
    (2) gc - java.lang.Object.finalize
    (3) currentTimeMillis / nanoTime
    (4) getProperty / getProperties
    (코드)


    4) java.lang.Class
    - 로드된 클래스, 인터페이스 정보(클래스 풀네임, 메서드, 필드 등)를 Class로 관리함
    - java.lang.reflect : 로드된 클래스에 대한 처리(java.lang.Class와 함께)
    => 클래스 정보를 찾아와서 담을 수 있는 객체타입 제공 등

    (1) getClass / forName
    (2) getDeclaredMethods - Fields - Constructors
    (3) newInstance
    (코드)


    5) java.lang.String
    - char[]를 래핑
    - immutable Object : 힙영역에 1번 생성되면 값을 변경할 수 없음
    => 힙 영역에서 변경이 불가하다는 것 : 최적화(같은 문자열 == 같은 객체 - 공유), 안전성
    => 새로운 객체를 생성해서 참조 객체주소값을 변경하는 것
    => 보통 final 클래스로 선언 : 상속해서 Immutable 속성을 없애버릴 수도 있음

    - Charset 신경쓰기 : UTF-8, EUC-KR에 따라 한글 인코딩 길이가 다름




    6) java.util.StringTokenizer
    - String의 유틸리티
    - 정규표현식이 아닌 특정 delimiter를 기준으로 문자열을 분할 유틸리티
    (코드)


    7) java.lang.StringBuffer, java.lang.StringBuilder
    - String
    (1) immutable Object : 같은 문자열이면서 각각 객체가 생성된다면 메모리 손실(그래서 immutable 이라는데 설계 이유 더 찾아보기)
    (2) 변경 작업이 많다면 String 인스턴스를 그만큼 생성함 : 메모리 손실

    - StringBuffer
    (1) String처럼 문자열 일부 변경 요청에 변경한 결과를 새로운 문자열을 만들어 리턴하지않음
    (2) 내부 버퍼(내부 코드를 보면 char[capacity] 인스턴스 생성할 때 크기 지정가능)에 문자열을 저장해두고 내부에서 변경작업을 함
    - Buffer나 Builder나 자동적으로 버퍼사이즈를 늘림 : 같은 AbstractStringBuilder를 상속받고있음

    (3) 버퍼란
    - 두 개체 사이의 완충재 역할 : 완충기억장치
    - 임시 저장 공간 : 사이즈가 한정되어있음
    - FIFO 구조(큐) : 먼저 들어온 데이터가 먼저 나감

    (4) StringBuffer의 메서드는 synchronized 처리 - 동기화 처리, 멀티쓰레드에 안전(쓰레드 공부하면서 이해해보도록 합시다)

    - StringBuilder
    (1) StringBuffer와 사용방식, 내부구조는 같음
    (2) 멀티쓰레드에 안전한지 그렇지 않은지 차이 : 멀티쓰레드 환경이라면 StringBuffer를, 싱글쓰레드 환경이라면 StringBuilder







*/

package com.jinbro.source.stdapi;


public class ApiMain {
    /* 암묵적으로 extends Object */
    public static void main(String[] args) {

    }
}
