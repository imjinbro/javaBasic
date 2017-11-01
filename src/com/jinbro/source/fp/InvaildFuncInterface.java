package com.jinbro.source.fp;

/*
    [제약사항]
    1) 제네릭 메서드를 사용한다면 람다식 타입 추론X
    - 생성하는 과정에서 타입 추론 정보가 부족
    - 메서드 호출될 떄 비로소 파라미터, 리턴 타입을 파악할 수 있음


 */

@FunctionalInterface
public interface InvaildFuncInterface {
    <T> String print(T value);
}

class InvalidFuncInterfaceUse{
    public static void main(String[] args) {

        /* 호출할 때 value가 비로소 어떤 타입인지 알 수 있음 : 추론 불가 */
        //getPrint(1, s -> s.toString());
    }

    public static <T> void getPrint(T value, InvaildFuncInterface invalidFuncInterfaceUse){
        System.out.println(invalidFuncInterfaceUse.print(value));
    }
}
