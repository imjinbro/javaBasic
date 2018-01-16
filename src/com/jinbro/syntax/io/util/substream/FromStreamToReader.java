package com.jinbro.syntax.io.util.substream;

import java.io.*;

/*
    [보조스트림 : 바이트 입출력을 문자 입출력으로]
    - InputStream, OutputStream 대상 데이터가 텍스트인 경우 문자 입출력으로 변경하는 것이 효율적
 */

public class FromStreamToReader {
    public static void main(String[] args) {
        Reader reader = null;
        FileOutputStream fos = null;
        Writer writer = null;

        try {
            reader = new InputStreamReader(System.in); //System.in == InputStream
            char[] data = new char[100];
            if(reader.read(data) != -1){
                System.out.println(new String(data));
            }


            fos = new FileOutputStream("/Users/jinbro/Desktop/ioTest/output.txt", true);
            writer = new OutputStreamWriter(fos);

            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                fos.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
