package com.jinbro.syntax.stdapi.arrays;

import java.util.Arrays;
import java.util.Comparator;

public class ArraysTest {
    public static void main(String[] args) {
        int[] numArr = {1,2,3,4,5};

        //copyOf : 지정 크기만큼 배열 복사, 원래 배열보다 더 커도됨, 내부적으로는 System.arraycopy()
        int[] newArr = Arrays.copyOf(numArr, 10);
        for(int i : newArr){
            System.out.println(i);
        }
        //System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length);


        //copyOfRange : 지정 인덱스 n ~ m까지 배열 복사
        newArr = Arrays.copyOfRange(numArr, 0, 5);
        for(int i : newArr){
            System.out.println(i);
        }


        /*
            deepEquals : 참조형 타입 배열 비교할 때 사용
            1) reference type 요소 비교
            2) 같은 Object[]를 참조하고있다면 바로 true
            3) 길이가 다르거나 한쪽이 null 일 경우 바로 false
            4) 길이가 같고 두 쪽다 null이 아닐 경우 Object 요소 같은 주소값인지 비교 후 결과
        */
        //primitive type과 Wrapper 인스턴스 차이
        Integer[] arr1 = {1,2,3,4,5};
        Integer[] arr2 = {1,2,3,4,5};
        boolean isSame = Arrays.deepEquals(arr1, arr2);
        System.out.println(isSame);


        /*
            equals
            1) primitive type 비교까지 함
            2) 동작은 deepEquals와 같음

            -) deepEquals와 equals의 차이는 deepCopy와 thinCopy 차이와 비슷
         */
        isSame = Arrays.equals(arr1, arr2);
        System.out.println(isSame);


        /*
            fill
            1) 특정값으로 배열 초기화할 때
            2) fill 이외에도 초기화해주지않으면 알아서 기본값으로 채움
         */
        int[] numArr2 = new int[10];
        Arrays.fill(numArr2, 10);
        for(int i : numArr2){
            System.out.println(i);
        }


        /* sort
           1) 배열 요소를 오름차순으로 정렬, 내부적으로 루프중첩시켜서 돌리는데.....;;
           - 정렬 최적화 알고리즘을 익힙시다

           2) 레퍼런스 타입 정렬 기준 필드, 정렬 기준(오름, 내림차순)
           - java.util.Comparator, java.util.Comparable

           3) 새로운 배열 리턴하지않고 파라미터 배열을 변경함 : 리턴타입 void임
        */
        int[] numArr3 = {6,3,1,4,3,2,1};
        Arrays.sort(numArr3);
        for(int i : numArr3){
            System.out.println(i);
        }

        Student s1 = new Student(1);
        Student s2 = new Student(2);
        Student s3 = new Student(6);
        Student s4 = new Student(5);
        Student s5 = new Student(3);
        Student s6 = new Student(7);
        Student[] students = {s1, s2, s3, s4, s5, s6};

        Arrays.sort(students);

        StringBuffer result = new StringBuffer().append("번호순(오름차순) : ");
        for (Student s : students){
            result.append(s.getNum() + " ");
        }
        System.out.println(result);


        /*
            binarySearch
            1) 메서드명 그대로 바이너리써치를 함, 반으로 나눠서 타겟 찾기 : 2개로 분할(Binary)
            2) 써치하기 전에 쏘팅해둬야함
            - 중간을 잘라서 초과, 미만을 가지고 완전탐색보다 더 적게 탐색함
            - 레퍼런스의 경우 소팅할 때 특정 필드를 기준으로 소팅
         */
        int res = Arrays.binarySearch(students, s3); // 소팅하고나서 몇번째에 속할까?
        System.out.println(res+1 + "번째에 있습니다"); // index+1



        // toString : 배열 요소 출력, toString을 보기 좋은 형태로 오버라이딩해서 씀
        System.out.println(Arrays.toString(numArr3));


    }
}

/* java.util.Comparable : binarySearch0에서 Comparable */
class Student implements Comparable<Student> {
    private int num;

    public Student(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public int compareTo(Student compare) {
        /* 0번째 인덱스부터 : 0과 1비교(파라미터 Student가 뒷 순서)

           [정렬 기준]
           1) 오름차순 : 앞 인덱스가 뒷 인덱스보다 작을 때 -1
           2) 내림차순 : 앞 인덱스가 뒷 인덱스보다 작을 때 1
         */
        if(num < compare.getNum()){
            return -1;
        } else if(num == compare.getNum()){
            return 0;
        }
        return 1;
    }
}


/* java.util.Comparator interface implements */
class ArrCompare implements Comparator<Student> {

    /* 오름차순 : o1 자리가 더 앞선 자리고, 작을 때 -1 리턴하면(인덱스와 리턴하는 값의 관계) */
    @Override
    public int compare(Student o1, Student o2) {
        if(o1.getNum() < o2.getNum()){
            return -1;
        } else if(o1.getNum() == o2.getNum()){
            return 0;
        }

        return 1;
    }
}
