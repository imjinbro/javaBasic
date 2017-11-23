package com.jinbro.basic.oop.rule;

import java.util.Vector;

/*
    [단일책임원칙]
    - 객체지향프로그램 모델링 원칙에서 책임 기본단위는 객체 : 객체는 해야할, 할 수 있는 일이 1가지여야함
    - 하나의 객체가 만능이 아니라 역할을 분할 : 추상화할 때 역할을 분할(분업)
    - 모든 것을 다 맡고 있다면 어느 한 부분이 변경될 때마다 코드 변경 : 그게 다른 역할, 코드에 영향을 미칠 수 있음

    - 하나의 책임이 여러개의 클래스에 분산되어있는 경우도 있음 : 모으는 작업을 산탄총 수술이라한대
    (1) 책임에 대해 변경해야할 점이 생기면 분산되어있는 클래스 모두 수술해야함
    (2) 하나로 모을 필요가 있음
    (3) 대표적인 경우가 로깅, 보안, 트랜잭션과 같은 것이라 하는데 : 아직 잘 모르겠음!
    - 특정 메서드의 로그를 데이터베이스에 저장하게 되어있음
    - 이를 파일에 저장하도록 변경하려고 할 때 특정 메서드 모두 찾고 모두 변경해줘야함
    - 하나의 객체로 모두 통일해서 내부적으로만 변경되고 외부 인터페이스는 그대로 두도록 : 찾는 것까지는 같으나 내부 코드가 뭐던지 상관없음
    - System.out.println이 여러개 모인 형태였겠지만 -> Log.e("") 이렇게 만드는 걸 말하겠지? 특정 책임을 수행하는 객체 만들기

    - 책임을 명확하게해서 헷갈리지않게끔하고 관리하기도 편하도록 하자!
 */
public class SingleResponsibility {

}

class Student{
    private String num;
    private Vector<Course> courses;

    public void addCourse(Course course){
        courses.add(course);
    }
}

class Course{
    private String num;
    private Vector<Student> students;
}

class StudentDAO {
    public void studentSave(Student student){ }

    public Student studentLoad(){
        /* DB 요청 - 응답 작업 */
        return null;
    }
}
