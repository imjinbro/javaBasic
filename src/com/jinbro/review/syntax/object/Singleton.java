package com.jinbro.review.syntax.object;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Singleton {
    public static void main(String[] args){
        Constructor<?> constructor = Printer.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        try {
            Printer printer = (Printer)constructor.newInstance();
            printer.print();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        Scanner.INSTANCE.scan();
    }
}

/* 진정한 싱글턴 */
enum Scanner{
    INSTANCE;

    public void scan(){
        System.out.println("scan!!!");
    }
}

/* AccesibleObject.setAccesible 메서드(리플렉션) -> private 생성자 호출 가능 */
class Printer{
    private static final Printer printer = new Printer();
    private Printer(){}

    public static Printer getPrinter(){
        return printer;
    }

    public void print(){
        System.out.println("print!!");
    }
}
