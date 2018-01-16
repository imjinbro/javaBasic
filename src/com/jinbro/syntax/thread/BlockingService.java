package com.jinbro.syntax.thread;

import java.util.concurrent.*;

public class BlockingService {
    public static void main(String[] args) {

        /*
            [작업이 완료된 순서대로 통보]
            - CompletionService : 구현객체는 ExecutorCompletionService
            - 작업처리요청 + 완료 순서대로 Future 객체 얻기
         */
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService completionService = new ExecutorCompletionService(executorService);

        /* 쓰레드풀 작업처리요청 */
        completionService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum =0;
                for(int i=0; i<100000; i++){ sum+=i; }

                return sum;
            }
        });


        /*
            - poll : 완료된 작업이 없다면 즉시 null 리턴
            - take : 완료된 작업이 없다면 있을 떄까지 쓰레드 블로킹됨 : 결과 get() 할 떄까지 쓰레드 블로킹

            - 더이상 확인할 작업이 없다고 판단될 경우 take()를 사용할 떄 while문 탈출하고 쓰레드 종료시켜줘야
         */
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Future<Integer> future = completionService.take();
                        System.out.println((int)future.get());
                    } catch (InterruptedException | ExecutionException e) {
                        break;
                    }
                }
            }
        });

        executorService.shutdown();
    }
}



