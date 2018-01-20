package com.jinbro.review.modeling;

public class Singleton {
}

class Printer {
    private static Printer printer = new Printer();
    private Printer(){ }

    public static Printer getPrinter() {
        return printer;
    }

    public synchronized void print(String message){
        if(isInvalidMessage(message)){
            return;
        }
        /* 작업 */
    }

    private boolean isInvalidMessage(String message){
        return message.isEmpty();
    }
}

