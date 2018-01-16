package com.jinbro.syntax.stdapi.object;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class ObjectsTest {
    public static void main(String[] args) {
        Student s1 = new Student("jinbro", 1);
        Student s2 = new Student("jinhyung", 2);

        /* compare : 두 객체 비교, 비교 기준 java.util.Comparator<T> - interface method 구현해야함(비교 대상, 기준) */
        int result = Objects.compare(s1, s2, new StudentNumCompare());
        System.out.println(result);


        /* equals : Object.equals와 같음, 객체타입이 오버라이딩했다면 equals의 결과, 두 객체 모두 null일 때 true를 반환함;;;  */
        System.out.println(Objects.equals(s1, s2));
        System.out.println(Objects.equals(new String("123"), new String("123")));

        /* deepEquals : Array를 제외하고는 Objects.equals와 같고 Array는 Arrays.deepEquals(배열 유틸리티) */
        int[] arr1 = new int[]{1,2,3};
        int[] arr2 = new int[]{1,2,3};
        System.out.println(Objects.equals(arr1, arr2));
        System.out.println(Objects.deepEquals(arr1, arr2));

        /* hash : 파라미터를 가지고 해쉬코드를 생성함, 내부적으로 Arrays.hashCode 사용(Object.hashCode()사용) */
        int code1 = Objects.hash(new String("123"), new Integer(1));
        int code2 = Objects.hash(new String("123"), new Integer(1));
        System.out.println(code1 + ", " + code2); // 1510352 같은 해쉬코드 출력

        /* hash : Object의 hashCode() 오버라이딩할 때 사용 */
        System.out.println(s1.hashCode() + ", " + s2.hashCode());

        /* isNull : 객체 null 여부(객체 == null)이 래핑된 메서드*/
        System.out.println(Objects.isNull(null));

        /* nonNull : 객체 !null 여부(객체 != null)이 래핑된 메서드*/
        System.out.println(Objects.nonNull(null));

        /* requireNonNull
            1) 객체 null일 때 NullPointerException 예외발생시킴
            2) 예외 message 전달도 가능 : String 전달 혹은 Supplier 인터페이스 익명구현 get()메서드(람다식)
               => java.util.function : 자바8에서 추가된 인터페이스(abstract 메서드 1개) 패키지
            3) null이 아니면 객체 반환  */

        String str1 = null;
        try{
            //String str2 = Objects.requireNonNull(str1);
            String str2 = Objects.requireNonNull(str1, ()->"???");
            //람다식(1.8) : Supplier 인터페이스 익명구현(익명구현이 뭔지, 왜하는지에 대한 포스팅할 것)
        } catch(NullPointerException e){
            System.out.println(e.getMessage());
        }

        /* toString : 객체 정보 출력, 두번쨰는 객체가 null일 경우 출력될 메세지  */
        System.out.println(Objects.toString(null, "아무것도 없잖아?"));

    }
}

class Student {
    private String name;
    private int num;

    public Student(String name, int num) {
        this.name = name;
        this.num = num;
    }

    /*** getter / setter ***/
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    /***********************/

    /* Objects.hash() 사용하지않고 바로 Arrays.hashCode() 사용 */
    @Override
    public int hashCode() {

        //Objects.hash(name, num)
        return Arrays.hashCode(new Object[]{name, num});
    }
}

class StudentNumCompare implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
        if(o1.getNum() > o2.getNum()){
            return 1;
        } else if(o1.getNum() < o2.getNum()) {
            return -1;
        }
        return 0;
    }
}


