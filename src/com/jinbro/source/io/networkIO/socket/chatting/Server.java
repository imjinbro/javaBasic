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


    [12/3]
    (1) StringTokenizer
    - 델리미터가 존재하지않아도 입력된 값이 있으면 1개의 토큰으로

    (2) switch
    - default -> break; 사용하지않음

    (3) 코드 파악
    - 메인 메서드를 먼저본다 : 어떤 흐름으로 코드가 실행되는가, 어떤 객체가 연관되어있는가, 메서드가 무엇인지 하나씩 파악하기

    (4) 프로토콜
    - 코드 짜기 전에 필요한 프로토콜에는 어떤 것이 있고, 프로토콜과 메세지 구분자 뭘로, 순서 어떻게 배치할건지 먼저 정해야
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
        Server server = new Server("jinbroWorld");
        server.start();
    }

    private String serviceName;
    private ExecutorService threadPool; //서버 쓰레드풀

    private ServerSocket serverSocket;
    private final int BIND_PORT = 9991;

    private Vector<User> clientList; //유저별 관리가 필요함

    public Server(String serviceName) {
        try {
            this.serviceName = serviceName;
            serverSocket = new ServerSocket(BIND_PORT);
            threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            clientList = new Vector<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        Runnable connectTask = () -> {
            while(true) {
                try {
                    System.out.println("[연결대기]");
                    Socket socket = serverSocket.accept();

                    /* 연결 후 */
                    connected(socket);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        threadPool.submit(connectTask);
    }

    public void connected(Socket socket){
        System.out.println("[연결됨]");
        User user = new User(socket);
    }

    public void noticeAllUser(String msg){
        for(User user : clientList){
            user.outputMessage(msg);
        }
    }

    public void close(){

    }


    /********** 클라이언트 각각의 소켓, 스트림을 관리하기위한 내부 클래스(UserInfo 포괄적인 클래스이름으로 변경하기) ************/
    class User {
        private Socket userSocket;
        private DataInputStream dis;
        private DataOutputStream dos;

        private String userName;

        public User(Socket socket) {
            userSocket = socket;

            try {
                dis = new DataInputStream(userSocket.getInputStream());
                dos = new DataOutputStream(userSocket.getOutputStream());
                inputMessage(); //소켓, 인풋스트림 백그라운드 루프 가동
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* 클라이언트로부터 입력 데이터 처리 : 클라이언트 -> 서버 입력 */
        public void inputMessage(){
            Runnable inputReceiverTask = () -> {
                while(true) {
                    try {
                        String inputData = dis.readUTF();
                        handleMessage(inputData); // 프로토콜/메세지 처리 메서드
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            threadPool.submit(inputReceiverTask);
        }

        public void handleMessage(String inputData){
            if(inputData.charAt(0) == '/'){
                StringTokenizer stz = new StringTokenizer(
                    new StringTokenizer(inputData, "/").nextToken(), " "
                );
                String protocol = stz.nextToken();
                String msg = stz.nextToken();

                /*"
                /이름설정 OOO"; -> 딱 1번만 되도록, 중복 체크
                /이름변경 OOO"; -> 중복 체크
                /방만들기 방이름";
                "/방참여 방이름";
                "그냥 메세지입니다";
                */
                switch (protocol){
                    case "setName":
                        setUserName(msg);
                        noticeAllUser(getUserName() + "님께서 대기실에 입장하였습니다");
                        outputMessage(serviceName + "에 입장되었습니다");
                        clientList.add(this);
                        break;

                    default:
                        outputMessage("유효하지않은 명령입니다");
                }
            } else {
                noticeAllUser("[" + getUserName() + "] " + inputData);
            }
        }

        /*
            클라이언트에 데이터 출력 : 서버 -> 클라이언트 출력
            어떤 트리거에 의해서 발생하는 이벤트 : accept(), read()는 상대방의 행위를 기다리는 것이라 별도 쓰레드 처리
        */
        public void outputMessage(String msg){
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
    /****************************************************************************/

}


