package com.jinbro.syntax.fp.lambda;

/*
    [제약사항]
    1) 람다식을 쓴다면 최소한 인터페이스 타입 객체가 생성될 때 타입파라미터가 있어야함
    - 아무런 정보없이 람다식을 사용하면 타입추론이 어려워서 컴파일 단계에서 에러남
    - 함수형 인터페이스의 메서드가 제네릭 메서드인 경우 : 호출될 때 비로소 타입을.....

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
