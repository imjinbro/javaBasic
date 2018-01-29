package com.jinbro.review.syntax.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStream {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos;
        FileInputStream fis;

        File target = new File("/Users/jinbro/Desktop/프로그래밍 기초/ioTest/hello_world.txt");

        fos = new FileOutputStream(target);
        byte[] data = "Hello".getBytes();
        fos.write(data);
        fos.flush();

        fis = new FileInputStream(target);
        byte[] buffer = new byte[10];
        fis.read(buffer);

        for(byte b : buffer){
            System.out.print((char)b);
        }

        fos.close();
        fis.close();
    }
}
