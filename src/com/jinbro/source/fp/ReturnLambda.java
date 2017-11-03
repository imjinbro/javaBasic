package com.jinbro.source.fp;

public class ReturnLambda {

    String name = "outer";
    static int cnt = 0;

    public static void main(String[] args) {

        /*
           1) num은 final : 외부 메서드 스택프레임 제거되는데, mFuncInterface 타입 객체는 계속 존재 : 계속적인 참조를 위해서 값 복사
           2) 람다식은 보통 메서드 내부에서 사용되는데, 외부 메서드의 로컬 변수를 사용할 때 그런 것
           - 클래스 변수는 상관없음
         */
        int num = 30;
        //printName(1, data -> "입력받은 데이터 : " + data + ", local var : " + num++); num++ 안됨
        printName(1, data -> "입력받은 데이터 : " + data + ", class var : " + (++cnt));

        /* 리턴문만 지정한다면 return은 빠져도됨 */
        printName(1, data -> "입력받은 데이터 : " + data);

        // 람다식은 인터페이스 타입의 메서드 구현부를 정의함
        printName(true, data -> "맞나요 : " + data);
    }

    public static <T> void printName(T data, mFuncInterface<T, String> func){
        /* mFuncInterface <- () 람다식 타겟 타입 */
        System.out.println(func.getData(data)); // 실제 메서드 호출
    }
}

@FunctionalInterface
interface mFuncInterface<T, R>{ //유연한 타입처리를 위해서 제네릭을 사용함
    R getData(T data);
}
