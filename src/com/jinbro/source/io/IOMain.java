package com.jinbro.source.io;

/*
    [I/O]
    - 데이터를 외부에서 읽고, 외부로 출력하는 작업 : Input, Output
    - IO : 키보드, 모니터, 파일, 네트워크
    - 자바 IO
    (1) 스트림을 통해서 입출력


    [스트림이란]
    - 단일방향으로 연속적이게 흘러가는 형태 : 출발지에서 나와서 도착지로 들어감
    - 프로그램이 출발지냐 도착지냐에 따라 스트림 종류가 다름
    (1) 도착지 : 입력 스트림(InputStream)
    (2) 출발지 : 출력 스트림(OutputStream)

    - 프로그램이 네트워크 상의 다른 프로그램과 데이터 교환을 위해 두 스트림이 모두 필요 : 단방향!


    [자바 I/O]
    - java.io 패키지 내 API 제공 : 다양한 I/O 클래스 제공
    - 입출력 스트림 대상에 따라 세분화 : 파일, 콘솔, 네트워크 상 프로그램
    - 데이터 타입(단위)에 따라 세분화 : Byte, Char
    (1) 바이트 기반 스트림 : 이미지, 멀티미디어, 문자 등 모든 종류의 데이터(XXXInputStream - OutputStream)
    (2) 문자 기반 스트림 : 문자만 주고 받을 수 있도록 특화됨(XXXReader - Writer)

 */
public class IOMain {
    public static void main(String[] args) {

    }
}
