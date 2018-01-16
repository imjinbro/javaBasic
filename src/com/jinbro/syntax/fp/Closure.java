package com.jinbro.syntax.fp;

/*
    [람다식]
    1) 익명메서드
    2) 단순히 익명클래스 구현이지는 않음 : 내부적인 차이가 있음
    - 자체 스코프없이 확장해서 사용 : 스코프 확장은 가능(외부값 복사), 확장이 자유로와
    (1) this는 함수형인터페이스타입 인스턴스가 아니라 lambda를 정의한 인스턴스 this
*/

public class Closure {
    public static void main(String[] args) {
        Closure c = new Closure();
        c.test1();
        c.test2();
        c.test3();
        c.test4();
    }

    private int number = 990;

    /* 클로저 변수는 둘다 final */
    private void test1(){
        /*
            [non-local variable(free variable)]
            - close-over 스코프 확장
            - final 선언안해도 final : java8부터(7전까지 final로 선언해줘야)
            - 멀티쓰레드가 어떻게 쓸지 모르니 final로 만들었다
            - 값 자체를 캡쳐함
          */
        final int number = 100;
        int[] arr = {100};

        testClosure("Anonymous class", new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
                //System.out.println(arr[0]++);
            }
        });


        testClosure("Lambda expression", () -> System.out.println(number));
    }

    /* this 차이(스코프 차이) */
    public void test2(){
        testClosure("Anonymous class", new Runnable() {
            @Override
            public void run() {
                //여기서 this는 new Runnable 익명클래스 인스턴스, 위에 있는 number가 없었으면 Closure number를
                System.out.println(Closure.this.number); //지저분하지만
                System.out.println(this.toString());
            }
        });


        //얘는 됨(차이) : this는 Runnable이 아니라 lambda를 가진 클래스 인스턴스의 this
        testClosure("Lambda expression", () -> System.out.println(this.number));
        testClosure("Lambda expression", () -> System.out.println(this.toString()));
    }


    /* 같은 이름의 오버로딩된 메서드가 있을 때 구분O/X */
    public void test3(){
        testClosure("Anonymous class", new Runnable() {
            @Override
            public void run() {
                //System.out.println("Closure.toString()" + toString("Test")); 구분을 못한대(shadowing이라한대)
                System.out.println("Closure.toString()" + toString());
            }
        });

        testClosure("Lambda expression", () -> System.out.println(toString("Test")));
    }


    /* 스코프 차이 : 자체 this O/X */
    public void test4(){
        int number = 100;

        testClosure("Anonymous class", new Runnable() {
            @Override
            public void run() {
                int number = 50;
                System.out.println("Closure.toString()" + number); //내부 number밖에 못봄
            }
        });

        testClosure("Lambda expression", () -> {
            //int number = 50; 자체 스코프가 없기때문에 외부 확장 -> number를 가지고 중복 assignment 판단(final)
            System.out.println(number);
        });
    }


    public <T> String toString(T value){
        return "The value is " + String.valueOf(value);
    }

    @Override
    public String toString() {
        return "Closure{" +
            "number=" + number +
            '}';
    }


    private static void testClosure(final String name, final Runnable runnable){
        System.out.println("========= " + name + " =========");
        runnable.run();
    }
}
