package com.jinbro.source.io.networkIO.socket.chatting.client;

public interface MessageHandler {
    void handler(String msg);
}

class OKMessageHandler implements MessageHandler{

    @Override
    public void handler(String msg) {
        System.out.println(msg);
    }
}

class ErrMessageHandler implements MessageHandler{

    @Override
    public void handler(String msg) {
        System.out.println(msg);
    }
}
