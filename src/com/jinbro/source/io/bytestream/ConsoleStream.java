package com.jinbro.source.io.bytestream;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/*
    [콘솔 IO]
    - 시스템을 사용하기위해 키보드로부터 입력받고 모니터로 출력하는 소프트웨어
    - 기본 1byte 단위 : InputStream, OutputStream


    [System.in]
    - 콘솔로부터 입력받을 때 사용하는 스트림 : System 객체 내 static InputStream
    - 키보드로 입력받은 데이터를 읽음 : 리턴타입 int - 10진수 아스키코드 저장(0 ~ 255, 알파벳, 아라비아 숫자, 특수기호 매칭)
    - 1byte인 알파벳, 아라비아숫자, 특수기호는 read()로 읽기 가능하지만 한글포함 유니코드 2byte는 read(byte[] b) 사용


    [System.out]
    - 콘솔로 출력할 때 사용하는 스트림 : System 객체 내 static PrintStream(extends OutputStream)
    - OutputStream extends인 PrintStream : 더 편한 메서드

 */
public class ConsoleStream {
    public static void main(String[] args) {
        InputStream in = System.in;
        PrintStream out = System.out;

        try {
//            char inputKey = (char)in.read();
//            if(inputKey >= 'a' && inputKey <= 'z'){
//                System.out.println(inputKey);
//            }


            for(byte b=48; b<58; b++){
                out.write(b);
            }

            out.write(13); // 1byte

            for(byte b=97; b<123; b++){
                out.write(b);
            }

            out.write(13);

            out.write("가나다라마바사아자차카타파하".getBytes()); //2byte -> byte[] b
            out.flush();


            /* PrintStream 메서드 */
            out.println("jinbro"); // out.write(13)까지 포함, flush()까지


//            int readByteLen;
//            byte[] bytes = new byte[10]; //0부터
//            while((readByteLen = in.read(bytes)) != -1){
//                System.out.println(new String(bytes, 0, readByteLen-1)); //엔터키를 누르면 10까지 입력
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
