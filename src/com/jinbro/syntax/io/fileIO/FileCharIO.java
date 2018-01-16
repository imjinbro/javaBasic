package com.jinbro.syntax.io.fileIO;

import java.io.*;

/*
    [File 클래스 + char 단위 스트림 사용]
    1) 파일 정보 가져오기, 생성, 삭제 등 : File 클래스
    2) char 단위 입출력 : 스트림 - FileReader, FileWriter
    - 문자 단위 : 문자(텍스트 파일)에만 특화되어있음
 */
public class FileCharIO {
    public static void main(String[] args) {
        File inputFile = new File("/Users/jinbro/Desktop/ioTest/input.txt");
        File outputFile = new File("/Users/jinbro/Desktop/ioTest/output.txt");
        FileReader reader = null;
        FileWriter writer = null;

        try {
            reader = new FileReader(inputFile);

            /* 문자 1개씩 읽고 콘솔에 출력하기 */
            int readData;
            while((readData = reader.read()) != -1){
                System.out.print((char)readData);
            }


            /* char[] 만큼 읽고 콘솔에 출력 */
            reader = new FileReader(inputFile);
            writer = new FileWriter(outputFile);

            int readDataLen;
            char[] data = new char[100];
            while((readDataLen = reader.read(data)) != -1){
                writer.write(data);
            }

            writer.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
