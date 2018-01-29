package com.jinbro.review.syntax.io;

import java.io.*;
import java.util.Base64;

/*
    IO 기본 단위 : byte, char
    입출력 대상 / 입출력 단위
 */

public class SubStream {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /* buffer : 입출력 횟수를 줄임, 버퍼에 쌓았다가 한번에 */
        FileOutputStream fos;
        FileInputStream fis;

        File target = new File("/Users/jinbro/Desktop/프로그래밍 기초/ioTest/hello_world.txt");

        fis = new FileInputStream(target);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        System.out.println(br.readLine());


        /* serialize(자바 시스템 간, 타입 맞춤화) - deserialize */
        Client jinbro = new Client("jinbro", 27, "우리집");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(jinbro);
        System.out.println(baos.toString() + " : " + Base64.getEncoder().encodeToString(baos.toByteArray()));


        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Client readClient = (Client)ois.readObject();
        System.out.println(readClient.toString());
    }

}

class Client implements Serializable{
    private String name;
    private int age;

    private transient String addr; //serialize : null

    public Client(String name, int age, String addr) {
        this.name = name;
        this.age = age;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return String.format("Member{name='%s', age='%d', addr='%s'}", name, age, addr);
    }
}
