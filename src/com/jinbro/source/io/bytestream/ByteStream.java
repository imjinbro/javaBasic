package com.jinbro.source.io.bytestream;

/*
    [InputStream]
    - byte 단위로 입력값을 읽어올 때 사용함 : 도착지가 프로그램
    - 메서드
    (1) read() : 1byte씩 읽고 리턴값은 int(4byte 중 1byte만 데이터 저장)로 함, 읽은 바이트를 리턴
    - int인 이유 : 읽어온 값이 0 ~ 255(자바 byte는 -128 ~ 127)라서, eof는 -1이라

    (2) read(byte[] bytes) : byte[] 배열 크기만큼 1번에 읽음, 대량의 데이터를 읽어올 때, 읽은 바이트 수를 리턴
    (3) read(byte[] b, int off, int len) : byte[off]부터 len개 저장, 읽은 바이트 수를 리턴
    (4) close : 입력스트림 닫기, 사용한 시스템자원을 돌려줌


    [OutputStream]
    - byte 단위로 출력하기위해서 사용함 : 출발지가 프로그램
    - 메서드
    (1) write(int b) : 출력스트림으로 1바이트씩 보냄(int에서 끝 1byte만)
    (2) write(byte[] b) : 출력스트림으로 바이트배열 b의 모든 바이트를 보냄
    (3) write(byte[] b, int off, int len) : b[off]부터 len개 보냄
    (4) flush() : 버퍼에 잔류하는 모든 바이트를 출력
    - 내부에 작은 버퍼가 있음 : 출력되기 전 버퍼에 쌓아뒀다가 순서대로 출력
    - 쌓여있는 버퍼를 비워버리는 역할

    (5) close() : 출력스트림 닫기, 사용한 시스템자원을 돌려줌
 */
import java.io.*;

public class ByteStream {
    public static void main(String[] args) {
        InputStream fis = null;
        OutputStream fos = null;

        try {
            fis = new FileInputStream("/Users/jinbro/Desktop/ioTest/inputtest.txt");
            fos = new FileOutputStream("/Users/jinbro/Desktop/ioTest/outputtest.txt");
//            int readByte;
//            while((readByte = is.read()) != -1){
//                os.write(readByte);
//            }

            int readByteNo;
            byte[] readBytes = new byte[100];
            while((readByteNo = fis.read(readBytes)) != -1){
                fos.write(readBytes);
            }

            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
