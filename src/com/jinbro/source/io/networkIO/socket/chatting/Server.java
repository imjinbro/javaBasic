package com.jinbro.source.io.networkIO.socket.chatting;
/*
    [1단계 순서도]
    1. 서버 오픈 : 바인드 포트 지정
    2. 서버 소켓 클라이언트 요청 기다림
    3. 클라이언트 서버(호스트 IP와 액세스 포트)액세스 요청
    4. 서버 - 클라이언트 각각 소켓 생성 : 대기방 입장한 것
    5. 서버는 accept() 블로킹이 풀린 후 소켓을 얻게됨
    6. 대기실에서 할 수 있는 일(프로토콜)
    (1) 대기실 인원들과 채팅(일반 채팅 메세지만)
    (2) 방 만들기
    (3) 방 참여하기


    [12/4]
    (1) Server - UserInfo, Client - User 책임분할
    - 이전 코드는 Client-User 책임이 하나의 클래스에 모여있어서 코드가 더러웠음;;
    - Server-UserInfo 클래스는 원래 나눠져있었으나 UserInfo가 Server의 역할까지 하는 코드로 작성되어있었음

    (2) 메서드 오버로딩
    - 첫번째 메서드에서 두번째 메서드 오버로딩할 때 파라미터 순서는 지켜줘야함

    (3) inputData, splitMessage, handleMessage 나누기
    - 읽기스트림에서 데이터를 읽고 작업, 처리 전 메세지를 분할하는 작업(프로토콜, 메세지), 처리하는 작업 메서드 분할

    (4) Map<K,V> 사용
    - String[] 과 같은 배열은 순서 약속을 딱딱 지켜야하니깐 K-V set으로 지정하는게 더 나을 것 같아서 Map 사용
    - 써보면서 공부하려고 쓰는 것도 있고
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        Server server = new Server(9991, "지뇽이월드");
        server.start();
    }

    private ServerSocket serverSocket;
    private final int servicePort;
    private String serviceName;
    private boolean isStart;

    private ExecutorService threadPool;
    private Vector<UserInfo> userList;

    public Server(int servicePort, String serviceName) {
        this.servicePort = servicePort;
        this.serviceName = serviceName;
        isStart = false;

        threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        userList = new Vector<>();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(servicePort);
            isStart = true;

            readyConnect();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[서버오류]");
            //클라이언트 각각 소켓 없애고 서버소켓까지 없애기
        }
    }

    public void readyConnect(){
        System.out.println("[서버시작]");
        Runnable connTask = () -> {
            while(true){
                try {
                    Socket socket = serverSocket.accept();
                    UserInfo userInfo = new UserInfo(socket);
                    inputMessage(userInfo); //클라이언트 연결되면 각 읽기작업 쓰레드로 동작하도록
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.submit(connTask);
    }

    public void stop(){

    }

    public void inputMessage(UserInfo userInfo){
        Runnable readTask = () -> {
            while(true){
                DataInputStream dis = userInfo.getDis();
                try {
                    //Map<key-value> : protocol-msg
                    String data = dis.readUTF();
                    Map<String, String> splitData = splitMessage(data);
                    handleMessage(userInfo, splitData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.submit(readTask);
    }

    public Map<String, String> splitMessage(String data){
        /* 클라이언트에서 받은 데이터 -> 프로토콜, 메세지 스플릿(배열말고 key-value Map을 사용하는게 더 나을듯함) */
        Map<String, String> splitData = new HashMap<>();

        if(data.charAt(0) == '/'){
            StringTokenizer stz = new StringTokenizer(new StringTokenizer(data, "/").nextToken(), " ");
            splitData.put(stz.nextToken(), stz.nextToken());
        } else {
            splitData.put("chat", data);
        }
        return splitData;
    }

    public void handleMessage(UserInfo userInfo, Map<String, String> splitData){
        for(Map.Entry<String,String> entry : splitData.entrySet()){
            String protocol = entry.getKey();
            switch (protocol){
                case "chat":
                    outputMessage("["+ userInfo.getName() +"] : " + entry.getValue());
                    break;

                case "setname":
                    if(userInfo.getName() == null){
                        //아이디 중복체크하는 메서드도 추가할 수 있겠지?
                        String name = entry.getValue();
                        userInfo.setName(name);
                        outputMessage("[" + name +"] 님 대기실 입장");
                        outputMessage("[" + name +"] 님 " + serviceName +"에 접속되었습니다" ,userInfo);
                        userList.add(userInfo);
                        System.out.println("[현재 접속자 수] : " + userList.size());
                    } else {
                        outputMessage("이미 설정되어있습니다.", userInfo);//캐시로 변경할 수 있도록 하던지 ㅎㅎㅎㅎㅎ
                    }
                    break;
            }
        }
    }

    public void outputMessage(String msg){
        /* 모두에게 서버가 보낼 때 */
        for(UserInfo user : userList){
            DataOutputStream dos = user.getDos();
            try {
                dos.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void outputMessage(String msg, UserInfo userInfo){
        /* 특정인에게 서버가 보낼 때 */
        DataOutputStream dos = userInfo.getDos();
        try {
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/********** 클라이언트 각각의 소켓, 스트림을 관리클래스 ************/
class UserInfo{
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

