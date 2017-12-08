package com.jinbro.source.io.networkIO.socket.chatting.server;

import java.util.Vector;
/*
    [프로토콜]
    1) ok/상세커맨드/데이터 : 데이터는 2개일 때가 있음(커맨드요청유저/메세지)
    2) err/상세커맨드

    [상세커맨드]
    1) chat : 채팅, 커맨드 입력 관련
    2) usrname : 유저명 설정(유저 초기 접속)
    3) roomname : 방이름 설정(방 만들 때)

 */

/********** 메세지 처리 핸들러 : 처리 부분이 코드가 길어서 바뀔 때마다 찾기 어려울 것 같아서 따로 분리 ************/
public interface MessageHandler{
    void handle(Server server, String msgSource, UserInfo userInfo);
}

//대기실, 특정 방이 있어서 아마 구분해야할 듯
class ChatMessageHandler implements MessageHandler{
    private static ChatMessageHandler handler = new ChatMessageHandler();
    public static ChatMessageHandler getInstance(){
        return handler;
    }

    @Override
    public void handle(Server server, String userMsg, UserInfo userInfo) {
        Vector<UserInfo> userList = server.getWaitingUserList();
        if(userList.size() < 2){
            server.outputMessage("err/chat", userInfo);
        } else {
            server.outputMessage("ok/chat/"+ userInfo.getName() + "/" + userMsg);
        }
    }
}

class SettingNameMessageHandler implements MessageHandler{
    private static SettingNameMessageHandler handler = new SettingNameMessageHandler();
    public static SettingNameMessageHandler getInstance() {
        return handler;
    }

    @Override
    public void handle(Server server, String userName, UserInfo userInfo) {
        if(userInfo.getName() == null){
            /* 중복 체크 */
            userInfo.setName(userName);
            server.getWaitingUserList().add(userInfo);
            server.outputMessage("ok/usrname/"+userInfo.getName());
        } else {
            server.outputMessage("err/usrname", userInfo);
        }
    }
}

class RoomCreateMessageHandler implements MessageHandler{
    private static RoomCreateMessageHandler handler = new RoomCreateMessageHandler();
    public static RoomCreateMessageHandler getInstance() {
        return handler;
    }

    @Override
    public void handle(Server server, String roomName, UserInfo userInfo) {
        Vector<Room> roomList = server.getRoomList();

        boolean isExist = false;
        for(Room room : roomList){
            if(room.getName().equals(roomName)){
                isExist = true;
                server.outputMessage("err/roomname", userInfo);
                break;
            }
        }

        if(isExist){
            return;
        }

        Room newRoom = new Room(roomName);
        roomList.add(newRoom);
        server.getWaitingUserList().remove(userInfo);
        newRoom.addUser(userInfo);
        server.outputMessage("ok/usrname/"+newRoom.getName(), userInfo);
    }
}


class ErrorMessageHandler implements MessageHandler{
    private static ErrorMessageHandler handler = new ErrorMessageHandler();
    public static ErrorMessageHandler getInstance(){
        return handler;
    }

    @Override
    public void handle(Server server, String msgSource, UserInfo userInfo) {
        server.outputMessage("err/chat", userInfo);
    }
}
