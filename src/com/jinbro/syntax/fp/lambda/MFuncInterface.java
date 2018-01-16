package com.jinbro.syntax.fp.lambda;

/*
    [직접 함수형인터페이스를 만들어서 패턴 익혀보기 : 람다식의 장점을 느껴보자]
    1) abstract method를 1개만 가지는 인터페이스 : 객체로 생성되지만 1개의 함수처럼
    2) @FunctionalInterface 어노테이션을 붙여놓으면 abstract 메서드가 1개인지 컴파일 타임 체크
    - 단 default, static 메서드는 체크X : java8부터 추가가능

    3) 람다식을 사용할 수 있음 : 익명메서드, 추론이 가능하기때문에(그자리가 어떤 객체 타입의 메서드인지)
    - 내부적으로는 익명클래스구현과 같다고함

    4) 람다식을 사용하면 그떄마다 필요한 함수를 갈아끼우는 효과
    - 파라미터 개수만 맞다면 : 타입파라미터를 사용하면 타입은 생각안해도되니깐
 */

import java.math.BigDecimal;
import java.util.function.Supplier;

@FunctionalInterface
public interface MFuncInterface<T, R> {
    R apply(T data);
    //R print(T data); : 2개의 abstract 메서드가 있으면 람다식을 사용할 수 없음 : 어떤 메서드인지 구분X
}

@FunctionalInterface
interface BigDecimalToCurrency{
    /* NONE-Generic : 명확한 경우에는 <T> 타입파라미터를 받지않고 명시적으로 지정해놓음 : 람다식을 생성하는 쪽에서 쓰면 추론 */
    String toCurrency(BigDecimal value);
}


class MFuncInterfaceUse {

    public static void main(String[] args) {
        /*
        method("Jinhyung", new MFuncInterface<String, String>() {
            @Override
            public String apply(String data) {
                return "Hello! " + data;
            }
        });
        */

        //개수만 같지 전혀 다른 메서드
        method("Jinbro", s -> "Hello! " + s);
        method("박진형", s -> s + "님 저희 지점을 방문해주셔서 감사합니다");
        method(3, s -> s + " 2 1 0 땡!");
        method(3, s -> s + "^2 = " + s*s);


        hello(true, () -> "Jinbro");
        hello(false, () -> "Jinbro");


        BigDecimalToCurrency bigDecimalToCurrency = bd -> "$" + bd.toString();
        System.out.println(bigDecimalToCurrency.toCurrency(new BigDecimal(120.00)));

    }

    static <T> void method(T data, MFuncInterface<T, String> func){
        //T는 타입파라미터를 받고(추론), R은 String 고정, 내부적으로 func의 apply 메서드 사용
        System.out.println(func.apply(data));
    }





    //내부적으로는 어떻게 사용되나 테스트해봄 : Supplier의 get()
    static void hello(boolean isMember, Supplier<String> getName){
        if(isMember){
            System.out.println(getName.get() + "님 환영합니다");
        } else {
            System.out.println("회원가입이나 로그인을 해야합니다");
        }
    }
}
