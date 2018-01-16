package com.jinbro.syntax.stdapi.math;

import java.util.Random;

public class MathRandomTest {
    public static void main(String[] args) {
        // random : 0.0 <=  < 1.0 랜덤 double 리턴, 커스텀해서 사용
        double random =  Math.random();
        System.out.println(random);

        // 랜덤주사위 눈
        int num = (int)(Math.random() * 6) + 1;
        System.out.println(num);


        // Random : 같은 시드값인 경우 같은 난수 생성
        Random random1 = new Random(1234);
        Random random2 = new Random(1234);

        int[] arr1 = new int[6];
        int[] arr2 = new int[6];

        for(int i=0; i<arr1.length; i++){
            arr1[i] = random1.nextInt(10); //bound 지정없으면 32비트 정수 표현범위 다 포함
            arr2[i] = random2.nextInt(10); //bound 지정하면 0<= < n (int만)
        }

        for(int i=0; i<arr1.length; i++){
            System.out.println(arr1[i] + ", " + arr2[i]);
        }


    }
}
