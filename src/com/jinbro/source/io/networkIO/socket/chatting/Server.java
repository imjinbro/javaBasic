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


    [12/5]
    (1) 경우의수 나열
    - if ~ else 로 다 나열하지않고 if return을 써서 경우의수 하나씩 처리해나가는 방식으로 코딩하니깐 훨씬 깔끔하다

    (2) 프로토콜을 스테이트 패턴 적용해주면 어떨까?
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
        /*
            입력예시 - 여기서는 프로토콜 + 메세지가 제대로 전달되었는지만 체크함
             (공백) : 길이가 0
            안녕하세요
            /
            /명령어
            /명령어 메세지
         */
        Map<String, String> splitData = new HashMap<>();

        if(data.length() < 1){
            splitData.put("wrong", "");
            return splitData;
        }

        if(data.charAt(0) != '/') {
            splitData.put("chat", data);
            return splitData;
        }

        StringTokenizer stz = new StringTokenizer(data, "/");
        if(stz.countTokens() == 0) {
            splitData.put("wrong", "");
            return splitData;
        }

        String splitTarget = stz.nextToken();
        stz = new StringTokenizer(splitTarget, " ");
        if(stz.countTokens() > 1){
            splitData.put(stz.nextToken(), stz.nextToken());
        } else {
            splitData.put("wrong", "");
        }

        return splitData;
    }

    public void handleMessage(UserInfo userInfo, Map<String, String> splitData){
        for(Map.Entry<String,String> entry : splitData.entrySet()){
            String protocol = entry.getKey();
            switch (protocol){
                case "chat":
                    if(userList.size() < 2){
                        outputMessage("대화 대상이 없습니다", userList.firstElement());
                    } else {
                        outputMessage("["+ userInfo.getName() +"] : " + entry.getValue());
                    }
                    break;

                case "setname":
                    if(userInfo.getName() == null){
                        //아이디 중복체크하는 메서드도 추가할 수 있겠지?
                        String name = entry.getValue();
                        userInfo.setName(name);
                        outputMessage("[" + name +"]님 대기실 입장");
                        outputMessage("[" + name +"]님 " + serviceName +"에 접속되었습니다" ,userInfo);
                        userList.add(userInfo);
                        System.out.println("[현재 접속자 수] : " + userList.size());
                    } else {
                        outputMessage("이미 설정되어있습니다", userInfo);//캐시로 변경할 수 있도록 하던지 ㅎㅎㅎㅎㅎ
                    }
                    break;

                default:
                    outputMessage("잘못된 명령어입니다", userInfo);
            }
        }
    }

    public void outputMessage(String msg){
        /* 모두에게 서버가 보낼 때 */
        for(UserInfo user : userList){
            DataOutputStream dos = user.getDos();
            try {
                dos.writeUTF(msg);
                dos.flush();
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
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/********** 클라이언트 각각의 소켓, 스트림을 관리클래스 : 소켓 생성되면 UserInfo 관리클래스에서 관리 ************/
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

