package com.jinbro.review.syntax.object;

public class ObjectCreator {

    private ObjectCreator(){ }

    /* 생성자 역할을 하는 static 메서드 장점1 : 코드 가독성을 높임(메서드 호출 시 생성자보다 어떤 역할을 하는지 바로 알 수 있음 */
    public static Object createObject(){
        return null;
    }

    /* 생성자 역할을 하는 static 메서드 장점2 : 캐시해둔 것처럼 미리 만들어둔 인스턴스를 리턴해도되고, 여러개 만들 필요없이 하나로 돌려쓴다면 바로 리턴해도되고 */
    public static Boolean valueOf(boolean b){
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    /* 생성자 역할을 하는 static 메서드 장점3 : 생성자처럼 하나의 타입이 아니라 리턴 타입의 확장/구현(extends, implements)타입 인스턴스도 리턴 가능 - enum을 사용해서 어떤 것을 생성할지 구분가능 */
    public static Throwable createInstance(){
        return null;
    }

}

/* public으로 선언되어있지않음 : package-private */
interface Throwable {
    public abstract void doThrow();
}

enum ThrowableKind{

}


