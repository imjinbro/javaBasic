package com.jinbro.source.interfacexam;

class MultiInterfaceExam{
    public static void main(String[] args) {
        ChildInterface it = new MultiInterface();
        it.method1();
        it.method2();
        it.method3();

        ParentInterface it2 = new MultiInterface();
        it2.method2();
        it2.method3();
    }
}

public class MultiInterface implements ChildInterface {

    @Override
    public void method1() {

    }

    @Override
    public void method2() {

    }
}

interface ChildInterface extends ParentInterface {
    public abstract void method1();
}

interface ParentInterface {
    public abstract void method2();
    public default void method3(){
        /*
            상위인터페이스의 default 메서드 처리
            1) 하위 인터페이스에서 재정의 - default -> 구현 클래스에서 재정의 or 사용
            2) 하위 인터페이스에서 재정의 - abstract -> 구현 클래스에서 구현
            3) 구현 클래스에서 재정의 - 사용
         */

    }
}
