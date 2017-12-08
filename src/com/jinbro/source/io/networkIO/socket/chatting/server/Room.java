package com.jinbro.source.io.networkIO.socket.chatting.server;

import java.util.Vector;

public class Room {
    private String name;
    private Vector<UserInfo> roomUserList;

    public Room(String name) {
        this.name = name;

        roomUserList = new Vector<>();
    }

    public String getName() {
        return name;
    }

    public void addUser(UserInfo userInfo){
        roomUserList.add(userInfo);
    }
}
