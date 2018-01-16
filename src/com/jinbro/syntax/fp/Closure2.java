package com.jinbro.syntax.fp;
/*
    [단순히 syntactic sugar 아니네요]
    - 단순하게 컴파일 타임 때 Anonymous class 형태로 바뀌지않음

    1) 스코프가 다름 : 자체스코프 가지나 안가지나
    - 익명클래스는 외부 인스턴스 필드에 접근 스코프 액세스 메서드도 만들어줌 : access$000 (getter), 접근할 방법이 없으니깐

    2) 파일 생성이 달라(클래스 - 인스턴스 생성 달라) : 익명클래스 수만큼 클래스 파일 추가, 람다는 따로 생성안하고 람다레시피 추가
    - 디컴파일 : javap -c -p 파일명.class (Anonymous 디컴파일 : 파일명 $ 앞에 \)
    - 람다식 : 클래스(정보)를 가지고 와서 생성X
    (1) 람다레시피(Lambda MetaFactory 추가)를 가지고 런타임에 클래스, 객체 생성한대요
    (2) 똑같은 것 요구하면 캐시해놨다가 그대로 사용 : 빨라요
 */
public class Closure2 {

    private int number = 900;

    public static void main(String[] args) {
        Closure2 c = new Closure2();
        c.test();
    }

    private void test(){
        //int number = 100;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            }
        };
        runnable.run();


        Runnable runnable2 = () -> System.out.println(number);
        runnable2.run();
    }
}



