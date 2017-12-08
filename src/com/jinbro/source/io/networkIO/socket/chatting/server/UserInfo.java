package com.jinbro.source.io.networkIO.socket.chatting.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/********** 클라이언트 각각의 소켓, 스트림을 관리클래스 : 소켓 생성되면 UserInfo 관리클래스에서 관리 ************/
public class UserInfo{
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String name = null;

    public UserInfo(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(this.socket.getInputStream());
            dos = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataInputStream getDis(){
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }
}
/****************************************************************************/


