package com.jinbro.syntax.classexam;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface Jinbro {
    //primitive, reference(string + enum + 사용자정의) 데이터
    public String value(); //default element (String return type abstract method)
    public String name();
    public int age() default 0;
}

/*
    [다음 스텝]
    1) 리플렉션 정의
    - 런타임 때 클래스의 정보를 얻는 것인가? 로더로 이미 돌아가있는 클래스의 정보를 얻는 것( http://namocom.tistory.com/383 )
    - Class 클래스 : http://devyongsik.tistory.com/292
    - https://brunch.co.kr/@kd4/8
    - http://www.nextree.co.kr/p5864/

    2) 어노테이션 내부 원리
    3) 어노테이션 활용 정리하기
 */
