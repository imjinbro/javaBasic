/*
    [중첩클래스]
    - 관계를 밀접하게 나타냄
    - 하나의 클래스 파일로 만들 정도로 광범위하게 쓰이지않을 때 하나의 클래스 내에 중첩시킴

    1) 필드로 선언된 중첩 클래스
    - static : 클래스의 static 필드인 클래스 - static 필드니깐 인스턴스 생성없이
    - instance : 클래스의 instance 필드인 클래스 - 바깥 클래스 인스턴스 생성해야

    2) 메서드에 중첩된 클래스(local class)
    - 메서드 내부에서만 사용되기때문에 접근제어자 필요없음 : 메서드 내에서 인스턴스 생성 - 사용
    - 스택프레임 지역변수 배열 포함(?), 중첩 클래스는 런타임 상수풀에 어떻게 기록될까..?


    [중첩클래스 사용 시 주의해야할 점]
    1) instance field class : static 메서드에서 인스턴스 생성 불가, static field/method 가질 수 없음
    2) static field class : 바깥 클래스 instance 필드/메서드 사용불가 - instance 생성없이 사용가능해서
    3) local class
    - 메서드의 로컬변수나 매개변수(외부변수)가 final로 선언되어져있어야함 : 자바8부터는 컴파일과정에서 알아서 선언됨
    => 외부변수를 참조하는 방식이 참조가 아니라 복사 : 복사된 값이 변경되면 어떻게? 그래서 final로 막았다는 고수님들의 이야기
    => 외부변수는 메서드의 로컬변수, 매개변수
    => 이 부분은

*/

package com.jinbro.syntax.nested;

public class Monster {

    private String kind;

    public Monster(String kind){
        this.kind = kind;
        new Brain().think();
    }

    public void walk(){
        /* 예제가 뜬금없지만 */
        class DownloadThread extends Thread{
            /* 생략 */
        }
        DownloadThread thread = new DownloadThread();
        thread.start();
    }


    public class Brain {
        public void think(){
            System.out.println(kind +"이(가) 생각합니다");
        }
    }

    public static class Eat{
        public void feed(){
            System.out.println("누군가 우걱우걱 하겠지");
        }
    }
}
