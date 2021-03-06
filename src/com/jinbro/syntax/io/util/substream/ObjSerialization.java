package com.jinbro.syntax.io.util.substream;

import java.io.*;

/*
    [보조스트림 : 객체 입출력하기 - serialize/deserialize]
    - serialize란?
    (1) 객체를 출력하기에 알맞은, 프로토콜에 맞는 포멧(일렬 형태)으로 변환하는 작업
    (2) 다른 시스템에 객체 데이터(정확히 필드)를 전송하기위함


    - deserialize란?
    (1) serialize된 객체(프로그램으로 입력받은)를 다시 자바 객체로 바꾸는 작업


    - serialize 하는 방법
    (1) csv : ,로 필드를 구분함
    (2) json : 불필요한 데이터가 적음, 가볍다, 라이브러리(Gson 등)
    (3) 자바 serialize API : 연속적인 바이트 형태, 자바 기반 시스템끼리 주고 받을 때 유용함(deserialize API로 곧바로 객체형태로)
    - ObjectOutputStream : serialize
    - ObjectInputStream : deserialize
    - 변경사항이 잦다면 csv나 json serialize 고려를


    - 주의할 점
    (1) 클래스가 변경되면 안됨 : serialize 된 그대로(InvalidClassException 발생 - serialVersionUID가 다름)
    (2) 변경을 해야한다면 명시적으로 serialVersionUID 를 선언, 값 저장하면됨 : long 타입
    - serialVersionUID 값 생성기 : /jdk디렉토리/bin/serialver -> serialver 클래스패스 클래스
    - 변경 전 기존 필드를 변경하면 deserialize 할 때 ClassCastException 발생함
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

            //¨Ì sr +com.jinbro.syntax.io.util.substream.StudentÕrA !:Ò L namet Ljava/lang/String;xpt jinbro
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