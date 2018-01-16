package com.jinbro.syntax.primitive;

public class Variable {

    public static void main(String[] args) {

        /* 범위 초과 */
        byte var1 = 127;
        var1 += 1;
        System.out.println(var1);

        /* unicode */
        char var2 = 65;
        System.out.println(var2);

        /* search unicode */
        char var3 = 'c';
        int var4 = var3;
        System.out.println(var4);


        /* String */
        String var5 = "박진형";
        System.out.println(var5);

        /* char empty */
        char var6 = 32;
        System.out.println(var6);


        /* long을 사용할 땐 값 뒤에 L을 */
        long var7 = 2416464413121L;
        System.out.println(var7);


        /* float 사용할 땐 뒤에 f를 : 기본 연산 타입이 double */
        float var8 = 3.14f;
        System.out.println(var8);


        /* boolean */
        boolean var9 = true;
        if(var9){
            System.out.println("TRUE");
        } else{
            System.out.println("FALSE");
        }


        /* 자동형변환 : 작은 메모리 크기 -> 큰 메모리 크기 타입으로 자동 변환(값 손실x) */
        byte var10 = 10;
        int var11 = var10; // 00000000 00000000 00000000 00001010 (1byte -> 4byte)

        /* 자동형변환2 : int -> float, 같은 크기이지만 float이 더 넓은 범위 표현 */
        int var12 = 10;
        float var13 = var12;
        System.out.println(var13);

        char var14 = 'a';
        int var15 = var14;
        System.out.println(var15);

        int var16 = 10;
        byte var17 = (byte)var16;
        System.out.println(var17);

        double var18 = 3.14;
        int var19 = (int)var18;
        System.out.println(var19);

        int var20 = 126;
        if(Byte.MIN_VALUE < var20 || Byte.MAX_VALUE > var20){
            byte var21 = (byte)var20;
            System.out.println(var21);
        }

        /* float : 가수 부분 23비트, int 32비트 - 표현범위가 좁아 값 손실 가능성이 있음, double 52비트 */
        int var21 = 123456780;
        int var22 = 123456780;

        float var23 = var21;
        var21 = (int)var23;
        System.out.println("결과값 : " + (var21-var22));


        double var24 = 10.5;
        int var25 = 5;
        int var26 = var25 + (int)var24;
        System.out.println(var26);


        byte var27 = 10;
        int result = var27 + var27;


        /* 부호 연산자 : int */
        short var28 = 100;
        int var29 = -var28;

        /* 증감연산자 */
        int var30 = 10;
        int var31 = 10;
        int result2 = ++var30 + 10; // 21
        int result3 = var31++ + 10; // 20
        System.out.println(result2 + ", " + result3);

        /* 비트 반전 연산자 */
        int var32 = 100;
        System.out.println(Integer.toBinaryString(var32)); //앞이 0이면 모두 생략(붙여줘야함)
        System.out.println(Integer.toBinaryString(~var32));


        /* 연산자는 각각 연산 */
        int var33 = 10, var34 = 4;
        double var35 = var33 / var34;
        double var36 = (double)var33 / var34;
        System.out.println(var35 + ", " + var36);


        /* 문자 연산 */
        char var37 = 'a' + 1;
        //char var38 = var37 + 1; 요로코롬 연산하면 int로 변환


        /* 연산 전 데이터타입 일치 */
        float var38 = 1f;
        double var39 = 10;
        double var40 = var38 + var39;


        /* 문자열 */
        String var41 = "진형";
        String var42 = "진형";
        String var43 = new String("진형");
        System.out.println(var41 == var43);
        System.out.println(var41.equals(var43));

        boolean var45 = true;
        boolean var46 = false;

        if(var45^var46){
            System.out.println("TRUE + FALSE");
        } else {
            System.out.println("!!!");
        }

        char var47 = 'a';
        String msg = (var47>64) ? "TRUE" : "FALSE";
        System.out.println(msg + ", " + (int)var47);



        int var48 = 615;
        System.out.println((var48/100)*100);


        double var49 = 5.0;
        double var50 = 0.0;

        double var51 = var49%var50;

        if(Double.isNaN(var51)){
            System.out.println(var51);
        } else {

        }
    }
}
