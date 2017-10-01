package com.jinbro.source.stdapi.classclass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ClassTest{

    private String name;

    public ClassTest(String name){
        this.name = name;
    }

    public static void main(String[] args) {

        /*** 리플렉션 : 로드되어있는 클래스의 정보를 얻음 ***/
        /* getClass() : 인스턴스 생성했을 때 사용가능 */
        ClassTest t1 = new ClassTest("jinbro");
        Class clazz = t1.getClass();

        System.out.println(clazz.getName());

        /* forName() : 인스턴스 생성하지않고도 로드되어있는 클래스를 가져올 수 있음 */
        try{
            clazz = Class.forName("com.jinbro.source.stdapi.classclass.ClassTest");
            Method[] methods1 = clazz.getMethods(); //public까지만 가져옴

            for(Method m : methods1){
                System.out.println("메서드 목록(Object 포함) : "+m.getName());
            }

            Constructor[] constructors = clazz.getConstructors();
            System.out.println(constructors.length); // or for문 c.getParameterCount()

            /* getDeclaredConstructors, getDeclaredFields, getDeclaredMethods
               - 상속을 제외한 클래스에 선언된 생성자, 필드, 메서드만 가져옴 : 기본적으로 상속관계를 가지는 Object로 테스트
             */
            Method[] methods2 = clazz.getDeclaredMethods();
            for(Method m : methods2){
                System.out.println("메서드 목록(Object 미포함) : "+m.getName());
            }


        } catch(ClassNotFoundException e){
            System.out.println("해당 클래스는 로드되어있지않습니다");
        }
        /****************************************/

        /* newInstance
            1) 동적으로 객체 생성, 런타임에 임의클래스 인스턴스 생성
            2) 기본 생성자를 호출하게되어있음 : 기본생성자를 만들어야함 */


        try{
            /* Object 타입이므로 강제형변환하거나 공통된 필드 - 메서드만 사용
                1) 완전 모르는 경우가 아니라 하나의 인터페이스를 구현하고 있는 두개 이상의 클래스 중 1개의 인스턴스를 생성할 때
                => 인터페이스 타입으로 강제형변환 후 메서드 호출하면 둘 중 생성된 인스턴스의 오버라이딩 메서드가 호출됨 */
            Class clazz1 = Class.forName("com.jinbro.source.stdapi.classclass.Member");
            Score s = (Score)clazz1.newInstance();
            s.setScore(100);
            System.out.println(s.getScore());


            /* 기본 생성자 없이 newInstance로 동적 객체 생성
            Constructor[] constructors = clazz1.getConstructors();
            for(Constructor constructor : constructors){

            } */
        }catch(ClassNotFoundException e){
            System.out.println("로드된 클래스 아님");
        }catch(IllegalAccessException e){
            System.out.println("접근제어자에 의해 예외발생");
        }catch(InstantiationException e){
            System.out.println("추상클래스, 인터페이스, 기본 생성자없는 경우 예외 발생");
       }


    }
}

interface Score {
    public void setScore(int num);
    public int getScore();
}

class Member implements Score {
    private int num;
    private int score;

    public Member() {
        this(10);

    }

    public Member(int num){
        this.num = num;
        this.score = 0;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "Member{" +
                "num=" + num +
                '}';
    }

    @Override
    public void setScore(int num) {
        this.score += num;
    }

    @Override
    public int getScore() {
        return this.score;
    }
}
