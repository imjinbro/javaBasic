package com.jinbro.source.thread;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
    [블로킹서비스와 다른 콜백서비스]
    - Future인스턴스.get할 떄까지 쓰레드 블로킹하지않고, 작업처리완료/예외발생하면 콜백메서드 실행

*/
public class CallbackService {

    public static void main(String[] args) {
        CallbackService service = new CallbackService();
        service.doWork("123", "삼이일");
        service.doWork("123", "321");

        service.endWork();
    }

    private ExecutorService executorService;
    private CompletionHandler<Integer, Void> callback = new CompletionHandler<Integer, Void>() {
        /*
            - java.nio.channels.CompletionHandler
            - CompletionHandler<V, A> : V - 결과값 타입, A - 첨부값의 타입(콜백메서드 결과값 이외에 추가적으로 전달하는 객체)
            - 첨부값이 필요없을 때는 Void

            - 콜백메서드 같은 메서드 목록을 제공 : run 실행하면서 마지막에 메서드 호출 - 그런 콜백 효과?
            - 작업처리와 작업처리완료결과를 나누지않음 : 쓰레드풀의 하나의 쓰레드 점유
         */
        @Override
        public void completed(Integer result, Void attachment) {
            System.out.println("[성공 : " + result + " ]");
        }

        @Override
        public void failed(Throwable exc, Void attachment) { //Throwable 예외타입
            System.out.println("[실패 : " + exc.toString() + " ]");
        }
    };

    public CallbackService() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void doWork(String x, String y){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try{
                    int X = Integer.parseInt(x);
                    int Y = Integer.parseInt(y);
                    callback.completed(X, null);
                } catch (Exception e){
                    callback.failed(e, null);
                }
            }
        };

        executorService.submit(task);
    }

    public void endWork(){
        executorService.shutdown();
    }
}
