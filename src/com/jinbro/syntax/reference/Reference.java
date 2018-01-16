package com.jinbro.syntax.reference;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Scanner;

public class Reference {

    public static void main(String[] args) {

        /* Run > Edit Configurations > Program Arguments > 파라미터 설정 */
        if(args.length != 2){
            System.out.println("전달된 매개변수 개수가 2개가 아닌데");
            System.exit(0);
        }

        System.out.println(Integer.parseInt(args[0]));



        /* Stack */
        int a = 5; // stack -> a = 5

        if(a==5){
            int b = 6;
            int c = 7; // stack -> a = 5, b = 6, c = 7
        }

        int d = 8; // stack -> a = 5, d = 8


        /* e.printStackTrace
        try{
            int num = 0;
            int num2 = 5;

            System.out.println(num2/num);

            //throw new Exception(); 강제 예외발생
        } catch (Exception e){
            e.printStackTrace();
        }*/


        /* 참조타입 비교 : 참조하고있는 객체주소 비교 */
        String str = "안녕하세요";
        String str2 = str;
        System.out.println(str == str2);


        /* String : 같은 문자열 리터럴이면 같은 주소값 참조 */
        String str3 = "jinbro";
        String str4 = "jinbro";
        System.out.println(str3 == str4);

        String str5 = new String("jinbro");
        System.out.println(str3 == str5);
        System.out.println(str5.equals(str3));


        /* 배열 선언, 생성 따로 하는 방법이 정해져있음
        int arr[];
        arr = {1,2,3,4,5}; */

        int arr[];
        arr = new int[]{1,2,3,4,5};


        int result = add(new int[]{1,2,3,4,5});
        System.out.println(result);


        /* array copy(1) */
        int oldArr[] = {1,2,3};
        int newArr[] = new int[5];

        for(int i=0; i<oldArr.length; i++){
            newArr[i] = oldArr[i];
        }


        /* array copy(2) */
        int newArr2[] = new int[6];
        System.arraycopy(oldArr, 0, newArr2, 0, oldArr.length);

        for(int i=0; i<newArr2.length; i++){
            System.out.println("newArr2[" + i + "] : "+ newArr2[i]);
        }

        oldArr = null;


        /* new Array default initialize */
        char arr2[] = new char[5];
        System.out.println(arr2[3]);

        int arr3[] = new int[5];
        System.out.println(arr3[3]);

        String arr4[] = new String[5];
        System.out.println(arr4[3]);

        double arr5[] = new double[10];
        for(int i=0; i<arr5.length; i++){
            arr5[i] = i; //자동형변환
        }
        //System.out.println(arr5[11]); ArrayIndexOutOfBoundException


        /* reference type Array */
        String[] strArray = new String[3];
        strArray[0] = "JAVA";
        strArray[1] = "JAVA";
        strArray[2] = new String("JAVA");
        System.out.println(strArray[0] == strArray[2]); //false
        System.out.println(strArray[0].equals(strArray[2])); //true


        /* for */
        int scores[] = {96, 72, 93, 91, 88};
        int sum = 0;
        for(int score: scores){
            sum += score;
        }
        System.out.println(sum);
        System.out.println("평균 : " + (double)sum/scores.length);


        /* enum */
        Week today = Week.월요일;
        System.out.println(today == Week.월요일);

        switch (today){
            case 월요일:
                System.out.println("월요일입니다");
                break;

            case 화요일:

                break;

            case 수요일:

                break;

            case 목요일:

                break;

            case 금요일:

                break;

            case 토요일:

                break;

            case 일요일:

                break;
        }


        /* Calendar + enum */
        Calendar now = Calendar.getInstance();
        Week today2 = null;

        int week = now.get(Calendar.DAY_OF_WEEK);

        switch (week){
            case 1:
                today2 = Week.일요일;
                break;

            case 2:
                today2 = Week.월요일;
                break;

            case 3:
                today2 = Week.화요일;
                break;

            case 4:
                today2 = Week.수요일;
                break;

            case 5:
                today2 = Week.목요일;
                break;

            case 6:
                today2 = Week.금요일;
                break;

            case 7:
                today2 = Week.토요일;
                break;
        }

        System.out.println("오늘은 " + today2.name());
        System.out.println("오늘은 " + today2);
        System.out.println("오늘이 열거객체 순서(0부터 index 시작): " + today2.ordinal());
        System.out.println("월요일과 오늘을 비교했을 때 순번 차이(요일 차이 계산) : " + today2.compareTo(Week.금요일));
        System.out.println("오늘은(외부입력을 받아 찾을 때 사용) " + Week.valueOf("목요일"));

        Week[] days = Week.values(); // days(stack), Week.values -> return array(heap)
        for(Week day: days){
            System.out.println("Week List : " + day);
        }


        int max = 0;
        int[] array = {1,5,3,8,2};

        for(int i=0; i<array.length; i++){
            if(max < array[i]) {
                max = array[i];
            }
        }
        System.out.println(max);
    }

    static int add(int scores[]){

        int sum = 0;

        for(int i=0; i<scores.length; i++){
            sum += scores[i];
        }

        return sum;
    }

    public enum Week{
        월요일,
        화요일,
        수요일,
        목요일,
        금요일,
        토요일,
        일요일
    }
}
