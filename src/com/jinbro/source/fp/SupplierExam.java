package com.jinbro.source.fp;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/* lazy evaluation */
public class SupplierExam {
    public static void main(String[] args) {
        method1(true, () -> "맞습니다 맞고요");
        method1(false, () -> "맞습니다 맞고요");

        method2(11, () -> true);
    }

    static void method1(boolean flag, Supplier<String> supplier){
        if(flag){
            System.out.println(supplier.get());
        }
    }

    static void method2(int starter, BooleanSupplier supplier){
        while(starter < 10){
            starter++;
        }

        System.out.println("완료됨(상태 : " + supplier.getAsBoolean() + ")");
    }
}
