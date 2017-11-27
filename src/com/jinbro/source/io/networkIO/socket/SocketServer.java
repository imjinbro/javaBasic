package com.jinbro.source.io.networkIO.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
    [TCP/IP 소켓통신 서버]
    - 특정 IP, 도메인 : new InetSocketAddress(도메인, 포트)
    - accept() : 쓰레드 블로킹(대기상태) - 클라이언트 요청이 있을 때까지 쓰레드 블로킹(주의)
    - read() : 클라이언트에서 데이터를 보내기 전까지 쓰레드 블로킹
    - write() : 클라이언트로 데이터 보내기 전까지 쓰레드 블로킹
    - 포트 재사용할 수 있도록 닫아주기
 */

public class SocketServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", 5001));

            while(true){
                System.out.println("[연결대기]");
                Socket socket = serverSocket.accept();

                InetSocketAddress socketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
                System.out.println("[연결수락] : " + socketAddress.getHostName() + ":" +socketAddress.getPort());

                InputStream is = socket.getInputStream();
                byte[] inputBytes = new byte[100];
                is.read(inputBytes);


                System.out.println("[데이터 수신] : " + new String(inputBytes));


                OutputStream os = socket.getOutputStream();

                String msg = "반가워요!";
                os.write(msg.getBytes("UTF-8"));
                os.flush();
                System.out.println("[데이터 발신] : " + msg);


                is.close();
                os.close();
            }


        }catch (IOException e){
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
