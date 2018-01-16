package com.jinbro.syntax.io.util.substream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/*
    [보조스트림 : 여러 타입 출력을 편하게]
    - PrintStream / PrintWriter : 바이트, 문자 단위 출력을 다양한 타입으로 할 수 있게 도와줌
 */
public class Printer {
    public static void main(String[] args) {
        FileOutputStream fos = null;
        PrintStream ps = null;

        try {
            fos = new FileOutputStream("/Users/jinbro/Desktop/ioTest/print.txt", true);
            ps = new PrintStream(fos);

            ps.println("편하게 출력하시면 됩니다");
            ps.println("이렇게 줄 바꿈도 메서드로 할 수도 있어요");
            ps.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                ps.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
