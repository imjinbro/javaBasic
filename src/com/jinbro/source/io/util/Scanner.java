package com.jinbro.source.io.util;


/*
    [Scanner]
    - 다양한 입력소스, 입력 데이터를 다룰 수 있는 유틸리티 클래스 : 데이터 받기 - 여러 타입으로 캐스팅까지
    - 생성자 인자로 File, System, Path 를 넘기면 됨 : 입력소스
 */
public class Scanner {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while(scanner.hasNext()){
            System.out.println(scanner.next());
        }
    }
}
