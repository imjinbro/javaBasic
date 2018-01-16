package com.jinbro.syntax.io.networkIO.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/*
    [TCP/IP 소켓통신 클라이언트]
    - connect() : 서버와 연결될 때까지 쓰레드 블로킹(주의)
    - 도메인 접속 : InetSocketAddress("도메인", 포트)
 */

public class SocketClient {
    public static void main(String[] args) {
        Socket clientSocket = null;

        try {
            clientSocket = new Socket();
            System.out.println("[연결요청]");
            clientSocket.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("[연결됨]");


            /* 데이터 통신 */
            OutputStream os = clientSocket.getOutputStream();
            String msg = "안녕하세요";
            os.write(msg.getBytes("UTF-8"));
            System.out.println("[데이터 발신] : " + msg);


            InputStream is = clientSocket.getInputStream();
            byte[] inputBytes = new byte[100];
            is.read(inputBytes);
            System.out.println("[데이터 수신] : " + new String(inputBytes));


            os.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
