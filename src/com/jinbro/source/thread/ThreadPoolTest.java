package com.jinbro.source.thread;

import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args) {
        /*
            [newCachedThreadPool]
            - (초기 생성하는 쓰레드수, 사용 후 쓰레드 풀에서 최소로 유지해야하는 쓰레드 수(코어 쓰레드 수), 쓰레드풀에 저장되는 최대 쓰레드수)
            - 초기 생성, 증가때 사용한 쓰레드 풀에서 최소 유지수가 0인 풀
            - 쓰레드 추가 후 60초동안 사용되지않는다면 풀에서 제거
            - 최대값으로 주는 만큼 최대쓰레드 수가 설정되지만 환경에따라 한계가 지어짐
        */
        ExecutorService executorService1 = Executors.newCachedThreadPool();


        /*
            [newFixedThreadPool]
            - 초기 생성하는 쓰레드 수 0개, 증가 후 사용하지않는 쓰레드 풀에서 유지되는 수(코어 쓰레드 수)는 생성자에서 넘겨받은 수, 최대 수도 넘겨받은 값
            - 생성자로 넘겨받는 값은 Runtime.getRuntime().availableProcessors() : jvm
            => Returns the number of processors available to the Java virtual machine : API docs

            - Runtime.getRuntime() 객체로 실행환경(jvm) 정보얻을 수 있음(싱글톤)


            [번외 - 코어와 쓰레드]
            - 코어 : 물리적인 공간인 CPU 칩 내 작공간을 둔 개념(멀티코어는 여러개의 작업공간)
            - 쓰레드 : 코어에서의 작업공간(흐름)
            - 1코어당 1쓰레드
            - 하이퍼쓰레드 기술로 1코어당 2쓰레드
        */
        ExecutorService executorService2 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().availableProcessors());


        /*
           [new ThreadPoolExecutor]
           - 직접 쓰레드풀 설정
           - new Cached/FixedThreadPool 모두 내부적으로 new ThreadPoolExecutor
        */
        ExecutorService executorService3 = new ThreadPoolExecutor(
                3,
                100,
                120L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        //코어 쓰레드 개수, 최대 쓰레드 개수, 놀고있는 시간(풀에서 제거타임), 놀고있는 시간단위, 작업큐(쓰레드 큐)



        /*
            [쓰레드풀 쓰레드 종료]
            1) shutdown(): 쓰레드풀 내에서 작업 중인 쓰레드 뿐 아니라 대기하고있는 모든 작업을 처리 후 종료
            2) awaitTermination(long, TimeUnit)
            - shutdown 호출 후 호출
            - 정해진 시간(파라미터) 내 모든 작업처리를 완료하면 true, 작업처리를 완료하지못하면 현재 처리 중 작업 interrupt 호출하고 false 리턴

            3) shutDownNow() : 현재 처리 중인 쓰레드 interrupt, 리턴타입으로 List<Runnable> 작업 큐에 있는 미처리 작업 목록
            - 남아있는 작업을 처리하느냐 마느냐에 따라 1,2번이냐 3번이냐
         */


        //쓰레드풀 쓰레드 종료처리까지 해줘야 끝
        executorService1.shutdownNow();
        executorService2.shutdownNow();
        executorService3.shutdown();
        try {
            executorService3.awaitTermination(100L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        /*
            [쓰레드풀에 들어갈 작업]
            - 작업큐에서 Runnable, Callable 객체를 가져와 run(), call() 메서드 실행
            - Callable의 call()과 Runnable의 run() 차이는 쓰레드가 작업 처리 후 리턴 값을 가지느냐 마느냐

            1) Runnable
            - 작업처리 완료 후 리턴값X

            2) Callable
            - 작업처리 완료 후 리턴값O
         */
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                ThreadPoolExecutor executor = (ThreadPoolExecutor)executorService1;
                System.out.println("풀 사이즈 : " + executor.getPoolSize() +
                                   ", 쓰레드명 : " + Thread.currentThread().getName());
            }
        };

        Callable task2 = new Callable() {
            @Override
            public Integer call() throws Exception {
                int num = 0;

                System.out.println(Thread.currentThread().getName());
                for(int i=1; i<=10000000; i++){
                    num += i;
                }
                return num;
            }
        };

        /*
            [쓰레드풀에 작업 넣기 - 처리요청]
            - 앞에서 ExecutorService(쓰레드풀 - 쓰레드 생성, 추가), Runnable/Callable(작업)을 알아봄
            - 쓰레드풀에 작업 처리 요청을 하는 방법(쓰레드풀 쓰레드 사용하기)은 2가지

            1) execute
            - Runnable을 작업 큐에 저장
            - 작업 처리 결과를 받지못함
            - 작업 처리 도중 예외가 발생하면 쓰레드 종료 -> 쓰레드풀에서 쓰레드 제거 -> 새로운 쓰레드 생성


            2) submit
            - Runnable, Callable을 작업 큐에 저장
            - 리턴된 Future 객체의 메서드를 통해 작업 처리 결과를 얻을 수 있음
            - 작업 처리 도중 예외 발생 쓰레드 종료X - 쓰레드풀에서 다음 작업 처리를 위해서 재활용
        */


        /* execute로 쓰레드풀 쓰레드 - 작업 처리 : 예외 일부러 발생시키기 */
        ExecutorService executorService = Executors.newFixedThreadPool(2); //최대 2

        //10개의 작업 생성 -> 쓰레드 풀 쓰레드 - 작업 처리하도록
        for(int i=0; i<10; i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    //ThreadPoolExecutor가 ExecutorService 구현 객체
                    ThreadPoolExecutor executor = (ThreadPoolExecutor)executorService;
                    int poolSize = executor.getPoolSize();
                    String threadName = Thread.currentThread().getName();

                    System.out.println("[총 쓰레드 개수 : " + poolSize + "] 작업쓰레드 이름 : " + threadName);

                    int temp = Integer.parseInt("삼"); //예외 발생시키기p
                }
            };

            executorService.execute(runnable);
            //Integer.parseInt("삼") -> NumberFormatException 발생 -> 쓰레드 종료, 풀에서 제거 -> 새로운 쓰레드 생성 -> 풀 추가
            //pool-4-thread-번호 가 바뀌는 것을 볼 수 있음 : 풀에 존재하는 쓰레드 최대 개수는 2로 변하지않음(쓰레드만 계속 생성되서 추가)
            //executorService.submit을 사용하면 생성해놓은 쓰레드 2개를 가지고 풀에서 작업처리를 함


            try {
                Thread.sleep(10); //콘솔에 System.out.println("메세지")가 찍힐 수 있도록
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();


    }
}
