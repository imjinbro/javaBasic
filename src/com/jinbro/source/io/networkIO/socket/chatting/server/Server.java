package com.jinbro.source.io.networkIO.socket.chatting.server;
/*
    [구현한 것]
    1. 서버 오픈 : 바인드 포트 지정
    2. 서버 소켓 클라이언트 요청 기다림
    3. 클라이언트 서버(호스트 IP와 액세스 포트)액세스 요청
    4. 서버 - 클라이언트 각각 소켓 생성 : 대기방 입장
    5. 서버는 accept() 블로킹이 풀린 후 소켓을 얻게됨
    6. 대기실에서 할 수 있는 일
    - 데이터 통신 프로토콜 정함
    - 대기실 인원들과 채팅(일반 채팅 메세지까지)


    [구현할 것]
    1. 클라이언트 메세지 핸들러 구현 : 프로토콜에 의해 구성된 메세지 어떻게 처리할건가
    2. 방 구현 : 만들기까지는 했는데 클라이언트 메세지 핸들러 구현해야해서 잠시 중단


    [12/8]
    (1) 메세지 처리 부분 따로 집합(interface)으로 만듬
    - interface를 구현하는데 각각 처리에 맞게 구현
    - 상황에 따라 구현 클래스 인스턴스 get해서 handle메서드 호출 : 싱글턴

    (2) 메서드
    - 리턴하지않고 내부 호출로 상황해결된다면 굳이 리턴타입 설정하지말고 메서드 호출 인자로 넘겨주기
    - 내부 호출만 사용할 것이라면(외부 인터페이스로 사용할 것 아니면) 접근제어 private

    (3) 파일 분리
    - 관련 패키지 만들고 1개의 소스파일에 있던 클래스들을 분리

    (4) 클라이언트 수신 메세지 또한 프로토콜 기반 처리
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
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

    private ExecutorService threadPool;
    private Vector<UserInfo> waitingUserList;
    private Vector<Room> roomList;

    public Server(int servicePort, String serviceName) {
        this.servicePort = servicePort;
        this.serviceName = serviceName;

        threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        waitingUserList = new Vector<>();
        roomList = new Vector<>();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(servicePort);
            connect();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[서버오류]");
            //클라이언트 각각 소켓 없애고 서버소켓까지 없애기
        }
    }

    public void stop(){

    }

    public void connect(){
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

    public Vector<UserInfo> getWaitingUserList(){
        return waitingUserList;
    }

    public Vector<Room> getRoomList(){
        return roomList;
    }

    /* 유저마다 리스너를 열어놔야함 : 유저 -> 서버 */
    public void inputMessage(UserInfo userInfo){
        Runnable readTask = () -> {
            while(true){
                DataInputStream dis = userInfo.getDis();
                try {
                    String data = dis.readUTF();
                    handleMessage(userInfo, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.submit(readTask);
    }

    private void handleMessage(UserInfo userInfo, String inputData){
        /*
            입력예시 - 여기서는 프로토콜 + 메세지가 제대로 전달되었는지만 체크함
             (공백) : 길이가 0
            안녕하세요
            /
            /명령어
            /명령어 메세지
         */
        if(inputData.length() < 1){
            processMessage(userInfo, "wrong", "");
            return;
        }

        if(inputData.charAt(0) != '/') {
            processMessage(userInfo, "chat", inputData);
            return;
        }

        StringTokenizer stz = new StringTokenizer(inputData, "/");
        if(stz.countTokens() == 0) {
            processMessage(userInfo, "wrong", "");
            return;
        }

        String splitTarget = stz.nextToken();
        stz = new StringTokenizer(splitTarget, " ");
        if(stz.countTokens() > 1){
            processMessage(userInfo, stz.nextToken(), stz.nextToken());
            return;
        } else {
            processMessage(userInfo, "wrong", "");
            return;
        }
    }

    private void processMessage(UserInfo userInfo, String todo, String msgSrc){
        MessageHandler handler = null;
        switch (todo){
            case "chat":
                handler = ChatMessageHandler.getInstance();
                break;

            case "setname":
                handler = SettingNameMessageHandler.getInstance();
                break;

            case "create":
                handler = RoomCreateMessageHandler.getInstance();
                break;

            default:
                handler = ErrorMessageHandler.getInstance();
        }
        handler.handle(this, msgSrc, userInfo);
    }

    /* 서버 -> 유저 */
    public void outputMessage(String msg){
        /* 모두에게 서버가 보낼 때 */
        for(UserInfo user : waitingUserList){
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