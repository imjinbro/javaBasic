package com.jinbro.source.io.util.substream;

import java.io.*;

/*
    [보조스트림 : 객체 입출력하기]
    - 객체를 출력하기위해서는 출력 가능한 형태로 만들어야함
    (1) 객체는 문자가 아님 : byte 기반 스트림
    (2) 출력가능한 형태 만들기 : 객체 데이터(필드만)를 일렬로 늘어선 형태인 연속적 바이트로 변경 - serialization
    (3) ObjectOutputStream

    - 객체 입력
    (1) 일렬 형태인 연속적인 바이트를 다시 객체로 변경 : deserialization
    (2) ObjectInputStream : 캐스팅까지 해줘야함


    - 입력받을 때 데이터 타입을 제대로 알아야 캐스팅이 가능해서 동일한 데이터에서 쓰는게
    (1) 클래스가 변경되어서도 안됨 : serialize 된 그대로(InvalidClassException 발생 - serialVersionUID가 다름)
    (2) 변경을 해야한다면 명시적으로 serialVersionUID 를 선언, 값 저장하면됨 : long 타입
    - serialVersionUID 값 생성기 : /jdk디렉토리/bin/serialver -> serialver 클래스패스 클래스
    - 변경 전 기존 필드를 변경하면 deserialize 할 때 ClassCastException 발생함


    - Serializable 인터페이스를 구현한 클래스만 입출력 데이터로 사용가능함
    (1) 필드만 직렬화 : 메서드 제외
    (2) transient 가 붙어있는 필드는 직렬화되지않음 : deserialize 했을 때 데이터 없음


    - 상위클래스 Serializable 구현X 하위클래스 Serializable 구현O : 상위클래스 필드를 serialize 시키고싶다면
    (1) 상위클래스 Serializable 구현하도록 하기
    (2) 하위클래스에서 상위클래스 필드를
    (3) 반대상황은 extends 관계라 똑같이 serialize


    - serialize 는 언제쓰고 안쓰는게 좋을까?
    (1) 객체를 입출력할 때
    (2) 자주 변경되는 데이터는 자바 API serialize 하지않는 것이 좋음 : json이나 csv 와 같은 다른 직렬화
    - 적절하게 사용하는 것이 좋음 : serialize, deserialize 하기 편한게 자바 직렬화

 */
public class ObjSerialization {
    public static void main(String[] args) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fos = new FileOutputStream("/Users/jinbro/Desktop/ioTest/object.txt");
            oos = new ObjectOutputStream(fos);

            //¨Ì sr +com.jinbro.source.io.util.substream.StudentÕrA !:Ò L namet Ljava/lang/String;xpt jinbro
            oos.writeObject(new Student("jinbro", 26, 1L));
            oos.flush();


            fis = new FileInputStream("/Users/jinbro/Desktop/ioTest/object.txt");
            ois = new ObjectInputStream(fis);

            Student jinbro = (Student) ois.readObject();
            System.out.println(jinbro.getName());
            System.out.println(jinbro.getId()); // null


            /* 다른 포맷 직렬화 : csv - 콤마(,)로 구분, 라이브러리 - Apache Commons CSV, opencsv */
            String csv = String.format("%s,%d", jinbro.getName(), jinbro.getAge());
            System.out.println(csv);


            /* 다른 포맷 직렬화 : json - 오버헤드가 적음(데이터만), 라이브러리 - GSON, Jackson */
            String json = String.format("{\"name\":\"%s\",\"age\":%d}", jinbro.getName(), jinbro.getAge());
            System.out.println(json);


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Student implements Serializable{
    //private static final long serialVersionUID = 값;

    private String name;
    private int age;
    transient private Long id;

    public Student(String name, int age, Long id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
            "name='" + name + '\'' +
            '}';
    }
}