package com.jinbro.source.io.networkIO.socket.chatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Client jinbro = new Client("jinbro","",9991);
        jinbro.connect();
        jinbro.sendMessage("안녕하세요!");

        Client jimin = new Client("jimin","",9991);
        jimin.connect();
        jimin.sendMessage("안녕!");
    }

    private Socket socket;

    private String userName;
    private String HOST_IP;
    private final int ACCESS_PORT;


    private DataInputStream dis;
    private DataOutputStream dos;

    public Client(String userName, String HOST_IP, int ACCESS_PORT) {
        this.userName = userName;
        this.HOST_IP = "127.0.0.1";
        this.ACCESS_PORT = ACCESS_PORT;
    }

    public void connect(){
        try {
            socket = new Socket(HOST_IP, ACCESS_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionInit();
        receiveMessage();
    }

    public void connectionInit(){
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            sendMessage(userName); //최초 유저 이름 전송
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage(){
        Runnable task = () -> {
            while(true) {
                try {
                    String msg = dis.readUTF();
                    //서버로부터 받은 메세지처리

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread receiveThread = new Thread(task);
        receiveThread.start();
    }

    public void sendMessage(String msg){
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
