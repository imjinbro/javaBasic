package com.jinbro.source.io.charstream;

import java.io.*;

/*
    [Reader]
    - 문자(char) 단위로 데이터를 읽어올 떄 사용하는 스트림
    - 메서드
    (1) read() : 스트림으로부터 문자를 1개 읽어옴
    (2) read(char[] cbuf) : 스트림으로부터 읽어온 문자들을 cbuf에 저장, 읽은 수를 리턴
    (3) read(char[] cbuf, int off, int len) : cbuf[off]부터 len개 읽은 것을 저장, 읽은 수 리턴
    (4) close() : 입력스트림 닫기


    [Writer]
    - 문자(char) 단위로 데이터를 출력할 때 사용하는 스트림 : 내부 버퍼에 쌓아뒀다가 순서대로 출력
    - 메서드
    (1) write(int c) : int값에서 끝에있는 2바이트(1개 문자)를 출력
    (2) write(char[] cbuf) : cbuf 만큼 출력
    (3) write(char[] cbuf, int off, int len) : cbuf[off]부터 len개 출력
    (4) write(String str) : str 출력(String은 char[])
    (5) write(String str, int off, int len) : str.charAt(off)부터 len개 출력
    (6) flush() : 버퍼 잔류 데이터(char)를 모두 출력
    (7) close() : 문자 출력 스트림 닫기


 */
public class CharStream {
    public static void main(String[] args) {
        Reader fr = null;
        Writer fw = null;

        try {
            fr = new FileReader("/Users/jinbro/Desktop/ioTest/inputtest.txt");
            fw = new FileWriter("/Users/jinbro/Desktop/ioTest/outputtest.txt");

            int readData;
            char[] cbuf = new char[100];

//            while((readData = fr.read()) != -1){
//                System.out.println((char)readData);
//            }
            int cnt = 0;
            int readCharLen;
            while((readCharLen = fr.read(cbuf)) != -1){
                System.out.println(++cnt);
                fw.write(cbuf);
            }

            fw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
