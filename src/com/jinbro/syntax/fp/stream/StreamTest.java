package com.jinbro.syntax.fp.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamTest {
    public static void main(String[] args) {
        /* before stream */
        List<String> list = Arrays.asList("홍길동", "신용권", "김자바");
        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


        /* after stream : iterable(내부반복자, 직접 하나씩 가져오지않아도 내부적으로 구현되어져있음) */
        list.stream()
            .forEach(str -> System.out.println(str));


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.stream()
               .filter(number -> number % 2 == 0)
               .map(number -> number * number)
               .collect(Collectors.toList())
               .forEach(number -> System.out.print(number + " "));


        System.out.println();

        final List<File> files = Arrays.asList(new File("안녕하세요"), new File("가장 중요한 것"), new File("내일 발표"));
        files.stream()
             .forEach(file -> System.out.println(file.getName()));

        System.out.println("파일 수(첫글자 안) : " +
            files.stream()
                 .map(file -> file.getName())
                 .filter(name -> name.startsWith("안")) // == 을 사용할 때 조심할 것,
                 .count()
        );


        System.out.println( "1부터 100까지 합 : " +
            IntStream.rangeClosed(0, 100)
                     .sum()
        );


        /* lazy evaluation : 최종 오퍼레이션 전까지는 처리를 안하고 있다가 나중에 필요한만큼씩 왔다리갔다리 처리 - 리턴이 Stream */

        files.stream()
             .distinct() //Object.equals(obj) true, false
             .forEach(file -> System.out.println(file.getName()));


        files.stream()
              .peek(file -> file.setName("운영팀_" + file.getName()))
              .forEach(file -> System.out.println(file.getName()));


        final int[] nums = {5,7,4,2,4,6,8,4,2};
        Arrays.stream(nums)
              .distinct()
              .forEach(i -> System.out.println(i));


        Arrays.stream(nums)
              .asDoubleStream()
              .filter(d -> d > 4.0)
              .forEach(d -> System.out.println(d));


        List<Student> students = Arrays.asList(
            new Student("지희", SEX.FEMALE),
            new Student("지미", SEX.FEMALE),
            new Student("지우", SEX.MALE)
        );

        System.out.println("우리반 여자아이들 이름 목록 : " +
            students.stream()
                    .filter(student -> student.getSex().equals(SEX.FEMALE))
                    .map(Student::getName)
                    .collect(Collectors.toList())
        );


        //Map<K,V> : Collectors.groupingBy( () ) - classifier( <K> )만 지정, <V>는 Object<T>
        students.stream()
            .filter(student -> student.getSex().equals(SEX.MALE))
            .collect(Collectors.groupingBy(student -> student.getSex()))
            .forEach((s,o) -> System.out.println("[" + s + "] : " + o.size() + "명"));

        //Map<K,V> : Collectors.groupingBy( (), () ) - classifier( <K> ), value ( <V> ) 지정
        System.out.println(
            students.stream()
                    .collect(Collectors.groupingBy(student -> student.getSex(), Collectors.counting()))
        );

    }
}

class Student{
    private String name;
    private final SEX sex;

    public Student(String name, SEX sex) {
        this.name = name;
        this.sex = sex;
    }

    public SEX getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }
}

enum SEX {
    MALE, FEMALE
}




class File{
    private String name;
    private FileUploader fileUploader;

    public File(String name) {
        this.name = name;
        fileUploader = FileUploader.getInstance();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean upload(){
        return fileUploader.upload();
    }
}

class FileUploader{
    private static FileUploader fileUploader = new FileUploader();

    private FileUploader(){
    }

    public static FileUploader getInstance(){
        return fileUploader;
    }

    public boolean upload(){
        return true;
    }
}