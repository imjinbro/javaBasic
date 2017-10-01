package com.jinbro.source.control;

import java.io.IOException;
import java.util.Scanner;

public class Control {

    public static void main(String[] args) throws IOException {

        /* if */
        if(true){
            System.out.println("TRUE");
        } else {
            System.out.println("FALSE");
        }

        /* switch */
        int num = (int)(Math.random()*6) + 1; // 1<= num < 7
        String msg = "";
        switch (num){
            case 6:
                msg = "왕입니다요";
                break;

            case 5:
                msg = "오오오오오";
                break;

            case 4:
                msg = "까비요";
                break;

            case 3:
                msg = "중간부터 합시다";
                break;

            case 2:
                msg = "ㅎㅎㅎㅎㅎ..";
                break;

            case 1:
                msg = "똑같은 사람 나오길.....";
                break;

            /* default 생략가능 */
            default:

                break;
        }
        System.out.println(msg);

        /* switch2 */
        char str =  'a';
        switch (str){
            case 'a':

                break;

            case 'b':

                break;

            case 'c':

                break;
        }

         /* switch3 */
        String position = "사장";
        switch (position){
            case "사장":

                break;

            case "부장":

                break;

            case "과장":

                break;
        }


        /* for */
        for(int i=0, j=100; i<30 && j>=50; i++, j--){
            System.out.println(i +", " + j);
        }


        /* for2 */
        for(float i=0.1f; i<=1.0f; i+= 0.1f){
            System.out.println(i);
        }


        /* while */
        int num2 = 0;
        while(true){
            if(num2>10){
                break;
            }
            System.out.println(num2);
            num2 += 1;
        }


        /* while2 */
        int level = 0;
        int keyCode = 0;
        boolean gameOn = true;

        while(gameOn){
            /* enter키 입력 받았을 때 다시 출력하지않기 */
            if(keyCode != 13 && keyCode != 10) {
                System.out.println("============================");
                System.out.println("1. 난이도 상승 | 2. 난이도 하락 | 3. 끝내기");
                System.out.println("============================");
                System.out.print("선택 : ");
            }

            keyCode = System.in.read(); //keyPress read

            switch (keyCode){
                case 49:
                    level += 1;
                    break;

                case 50:
                    if(level<1){
                        System.out.println("\n가장 낮은 난이도입니다.");
                    } else {
                        level -= 1;
                    }
                    break;

                case 51:
                    gameOn = false;
                    break;
            }
        }

        System.out.println("게임 종료");


        /* do-while */
        String inputStr = "";
        do{
            Scanner scanner = new Scanner(System.in);
            //콘솔에서 입력받은 문자열을 한번에 읽음, 원래 System.in.read()는 한번에 하나의 문자만 읽음
            //System.in : 바이트 단위로 읽음
            inputStr = scanner.nextLine();
            System.out.println(inputStr);

        }while(!inputStr.equals("quit"));


        /* break label */
        Exam: for(int i=2; i<10; i++){
            for(int j=1; j<10; j++){
                if(i==9){
                    System.out.println("ㅂㅇ");
                    break Exam;
                }
            }
        }


        /* continue : 2의 배수만 출력 안되게 */
        for(int i=0; i<=20; i++){
            if(i%2 ==0){
                continue;
            }

            System.out.println(i);
        }

    }
}
