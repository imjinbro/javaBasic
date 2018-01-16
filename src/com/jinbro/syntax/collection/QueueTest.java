package com.jinbro.syntax.collection;

/*
    [Queue]
    1) 선입선출 구조 : 먼저 들어온 것이 먼저 나가는 특징을 가짐
    2) 쓰레드풀의 작업 큐 : Runnable, Callable 객체를 저장해뒀다가 쓰레드에 저장된 순서대로 바인딩해서 작업처리
    3) Queue는 인터페이스 : 큐를 구현한 대표적인 클래스가 링크드리스트(Collection > List - LinkedList)
 */

import java.util.LinkedList;
import java.util.Queue;

public class QueueTest {
    public static void main(String[] args) {
        Queue<Message> messageQueue = new LinkedList<>();
        messageQueue.offer(new Message("send", "jinbro"));
        messageQueue.offer(new Message("delete", "jinbro"));
        messageQueue.offer(new Message("quit", "jinbro"));

        while(!messageQueue.isEmpty()){
            System.out.println(messageQueue.poll().toString());
        }

    }
}

class Message {
    private String cmd;
    private String to;

    public Message(String cmd, String to) {
        this.cmd = cmd;
        this.to = to;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Message{" +
                "cmd='" + cmd + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
