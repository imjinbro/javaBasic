package com.jinbro.source.io.networkIO;

import java.net.InetAddress;
import java.net.UnknownHostException;

/*
    [네트워크]
    - 주고 받기위해서 연결함 : 목적을 위해 연결망을 형성한 것
    - 컴퓨터 네트워크 : 데이터를 주고 받기위해서 통신매체에 의해 연결된 것
    - 인터넷은 LAN을 연결 연결해서 WAN을 형성한 것


    [서버와 클라이언트 그리고 통신]
    - 서버 : 서비스를 제공하는 프로그램
    - 클라이언트 : 서비스를 제공받는 프로그램

    - 특정 네트워크에 각각 연결된 컴퓨터의 두 프로그램이 통신하기위해서 연결, 처리 과정
    (1) 요청(request) - 수락(응답 response) : IP 주소(혹은 도메인) + 포트번호(프로그램)
    (2) 처리 요청 - 처리
    (3) 서버에서 결과를 클라이언트로 응답

    - 모델
    (1) 서버(1) : 클라이언트(N)
    (2) p2p : 서버인 동시에 클라이언트 역할, 먼저 접속 시도한 컴퓨터가 클라이언트

    - IP : java.net.InetAddress - DNS 검색 후 IP 주소를 가져오는 기능까지


    [TCP]
    - 연결지향프로토콜 : 서버와 클라이언트가 연결된 상태에서 데이터를 주고받을 때 사용하는 프로토콜
    - 장/단점 : 정확하고 안정적임 / 연결 작업이 우선되어있어야하고, 최단선이 아닐 경우 UDP보다 느림
    - 과정 : 연결 -> 데이터 처리 요청 -> 데이터 처리 -> 응답
    - 연결시키기
    (1) 서버 : java.net.ServerSocket(연결 요청 수락 - 바인딩 포트), java.net.Socket(연결 후 통신) 클래스
    (2) 클라이언트 : java.net.Socket(IP 주소, 포트 필요)

    - 데이터 주고받기 : Socket 인스턴스 InputStream, OutputStream으로(byte[])
    - 네트워크 I/O : 블로킹 방식으로 구현되어져있기때문에 서버 멀티쓰레드로 처리 : 급격한 성능저하를 위해 쓰레드풀로 완만한 성능저하
    (1) 메인쓰레드 처리 X, 여러 클라이언트 요청에 대한 서버 처리
    (2) accept(), connect(), read(), write()
 */
public class Network {
    public static void main(String[] args) {
        try {
            InetAddress local = InetAddress.getLocalHost();
            System.out.println(local.getHostAddress());

            InetAddress[] addresses = InetAddress.getAllByName("www.naver.com");
            for(InetAddress address : addresses){
                System.out.println(address.getHostAddress());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
