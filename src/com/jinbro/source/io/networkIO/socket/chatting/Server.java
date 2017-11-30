package com.jinbro.source.io.networkIO.socket.chatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
    [콘솔 기반 다중접속 채팅방 : 쓰레드풀 멀티쓰레드 채팅 서버 - 클라이언트]

    [한 것]
    (1) accept(접속 여러명), read/write(메세지 읽으면서,보내면서 다른 것 할 수 있기) 멀티쓰레드(쓰레드풀) 처리
    - accept() : 여러 사람의 접속을 위해서 별도 쓰레드 돌아가야함
    - read() : 계속해서 돌아가야함 : 계속 리시버를 돌려야하므로
    - write() :

    (2) 서버 쪽에서 유저 관리를 위해서 내부 클래스 만듬
    - 각각에 대한 서버 소켓, I/O 스트림, 유저명


    [해야할 것]
    (1) 프로토콜 정하기 : 통신 규칙
    (2) 소켓 연결 끊겼을 때 에러 처리 작성하기
    (3) 다시 처음부터 모델링하기
    (4) 접속기를 각각 따로 만들고 있는데 하나의 클래스로 정리해서 테스트 용이하게 만들기
    - 네트워크 부분과 정보 부분 분리시키기
 */
public class Server {
    public static void main(String[] args) {
        Server server = new Server(9991);
        server.start();
    }

    private ExecutorService threadPool;
    private ServerSocket serverSocket;
    private Socket socket;
    private final int BIND_PORT;

    private Vector<UserInfo> userList;

    public Server(int BIND_PORT) {
        threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.BIND_PORT = BIND_PORT;
        userList = new Vector<>();
    }

    public void start(){
        try {
            serverSocket = new ServerSocket(BIND_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        connect();
    }

    public void connect(){
        Runnable task = () -> {
            while(true) {
                try {
                    System.out.println("[접속 대기중]");
                    socket = serverSocket.accept();

                    /* 접속된 이후 */
                    System.out.println("[접속]");
                    UserInfo user = new UserInfo(socket);
                    user.start();

                    userList.add(user);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.submit(task);
    }


    class UserInfo extends Thread{
        private Socket user_socket;
        private DataInputStream dis;
        private DataOutputStream dos;

        private String userName;

        public UserInfo(Socket socket) {
            this.user_socket = socket;
            connectionInit();
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

        public void connectionInit(){
            try {
                dis = new DataInputStream(user_socket.getInputStream());
                dos = new DataOutputStream(user_socket.getOutputStream());
                setUserName(dis.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
           receiveMessage();
        }

        public void receiveMessage(){
            while(true){
                try {
                    //해당 유저로부터 받은 메세지를 다른 클라이언트(소켓)로 쏴줘야지
                    String msg = dis.readUTF();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String msg){

        }
    }

}
