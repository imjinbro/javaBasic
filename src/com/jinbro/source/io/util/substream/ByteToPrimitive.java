package com.jinbro.source.io.util.substream;

import java.io.*;

/*
    [보조스트림 : byte 단위 스트림 -> primitive type 단위 스트림]
    - 바이트 단위 -> 기본데이터타입(boolean, short, char, int, long, float, double)
    - 각각 데이터 크기가 다르기때문에 타입에 잘 맞춰야함
 */
public class ByteToPrimitive {
    public static void main(String[] args) {
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        FileInputStream fis = null;
        DataInputStream dis = null;

        try {
           File primitiveDat = new File("/Users/jinbro/Desktop/ioTest/primitive.txt");
            if(!primitiveDat.exists()){
                primitiveDat.createNewFile();
            }

            fos = new FileOutputStream(primitiveDat);
            dos = new DataOutputStream(fos);

            dos.writeUTF("박진형");
            dos.writeDouble(175.1);
            dos.writeBoolean(true);

            dos.writeUTF("진브로");
            dos.writeDouble(175.1);
            dos.writeBoolean(true);

            dos.flush();



            fis = new FileInputStream(primitiveDat);
            dis = new DataInputStream(fis);


            for(int i=0; i<2; i++){
                String name = dis.readUTF();
                double height = dis.readDouble();
                boolean isGone = dis.readBoolean();

                System.out.println(name + ", " + height + ", " + isGone);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                dos.close();
                fis.close();
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
