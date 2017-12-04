package com.jinbro.source.io.networkIO.socket.chatting;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 9991);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("아이디를 입력해주세요 : ");

        try {
            client.connect(reader.readLine());

            while(true){
                client.outputMessage(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User user;

    private boolean isConn;
    private String host;
    private int port;
    private Socket socket;

    private DataInputStream dis;
    private DataOutputStream dos;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.isConn = false;
    }

    public void connect(String userName){
        try {
            user = new User(userName); //통신을 먼저해서 아이디 체킹하고 결과에 따라서 유저 생성하던지 아니면 빠꾸
            socket = new Socket(host, port);
            connected(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connected(Socket socket){
        try{
            isConn = true;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            inputMessage(); //백그라운드에서 서버로부터 오는 데이터 읽기
            outputMessage("/setname " + user.getName());
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("[접속오류]");
            //스트림, 소켓 모두 닫기
        }
    }

    public void disconnect(){

    }

    public boolean isConnect(){
        if(isConn){
            System.out.println(user.getName() +"님 클라이언트 프로그램을 통해서 접속하고있음");
        }
        return isConn;
    }

    public void inputMessage(){
        new Thread(() -> {
            while(true){
                try {
                    String msg = dis.readUTF(); //프로토콜,메세지 나누기
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void outputMessage(String msg){
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
