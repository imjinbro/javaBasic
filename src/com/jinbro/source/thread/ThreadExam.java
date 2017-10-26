package com.jinbro.source.thread;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExam {

    public static void main(String[] args) {
        Thread mv = new MovieThread();
        mv.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mv.interrupt();


        /* callback service : multi thread - thread pool */
        DeliveryCustomer jinbro = new DeliveryCustomer("jinbro");
        DeliveryCustomer jinhyung = new DeliveryCustomer("jinhyung");
        DeliveryService baemin = new DeliveryService();

        baemin.call(jinbro);
        baemin.call(jinhyung);
        baemin.close();
    }

}


class MovieThread extends Thread{
    @Override
    public void run() {
        System.out.println("동영상을 재생합니다");

        while (true) {
            System.out.println("동영상 재생 중");
            try{
                Thread.sleep(1);
            }catch (InterruptedException e) {
                System.out.println("동영상을 종료함");
                break;
            }

            /* 또는
            if(this.isInterrupted()){
                break;
            }
            */
        }

        System.out.println("안녕");
    }
}


/* 콜백서비스 */
class DeliveryService {
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private CompletionHandler<String, Void> callback = new CompletionHandler<String, Void>() {
        @Override
        public void completed(String name, Void attachment) {
            System.out.println("[주문완료] " + name + "님께 배달예정입니다");
        }

        @Override
        public void failed(Throwable exc, Void attachment) {
            System.out.println("[주문실패]" + exc.getMessage()); //에러메세지에 따라 처리해야겠지만...
        }
    };

    //내부적으로는 쓰레드풀에서 쓰레드와 작업을 이어서 사용하고, 완료되면 완료처리 객체 메서드 호출
    public void call(DeliveryCustomer customer){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("주문 중입니다");
                    callback.completed(customer.getName(), null);
                    //조건에 따라서 callback.failed 외쳐야지
                }catch(Exception e){
                    callback.failed(e, null);
                }

            }
        });
    }

    public void close(){
        executorService.shutdown();
    }
}

class DeliveryCustomer {
    private String name;

    public DeliveryCustomer(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}


