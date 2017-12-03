package com.jinbro.source.io.networkIO.socket.chatting;

import java.io.*;
import java.net.Socket;

/* 서버접속기(클라이언트), 유저 정보 클래스 갈라놓기 */
public class Client {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        User user = null;
        try {
            System.out.print("아이디를 입력하세요 : ");
            String userName = br.readLine();

            user = new User(userName);
            user.connect(); /* 아이디 체킹하는 메서드 추가하기 : 서버와 연동해서 하는게 */

            while(true){ //지금은 콘솔이라서 따로 쓰레드를 돌리지않음
                user.outputMessage(br.readLine());
            }

        } catch (IOException e) {
            System.out.println("[접속 오류]");
            e.printStackTrace();
        } finally {
            user.disconnect();
            try {
                br.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}

class User {
    private Socket socket;
    private String host;
    private int port;

    private DataInputStream dis;
    private DataOutputStream dos;

    private String name;

    public User(String name) {
        this.name = name;
        this.host = "127.0.0.1"; //호스트 ip, 액세스포트 임시 고정
        this.port = 9991;
    }

    public void connect(){
        try {
            socket = new Socket(host, port);
            connected(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connected(Socket socket){
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            inputMessage();

            //초기 이름 설정되는 것을 한번만 되게끔 해야하는데, 여기는 서버에서 프로토콜 처리를 하면 안될듯한데..
            outputMessage("/setName "+getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){

    }

    public void inputMessage(){
        new Thread(() -> {
            while(true){
                try {
                    String msg = dis.readUTF();
                    System.out.println(msg); //프로토콜,메세지 나누기

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

    public String getName() {
        return name;
    }
}
