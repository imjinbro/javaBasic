package com.jinbro.syntax.thread;

/*

    [프로세스와 쓰레드 그리고 멀티테스킹]
    1) 프로세스 : 프로그램이 메모리를 할당받아 코드를 실행한 것
    - 1개의 프로그램이 여러개 켜져있으면 각각의 프로세스 : 독립적

    2) 쓰레드 : 1가지 작업을 처리하는 실행 흐름

    3) 멀티테스킹 : 동시에 2개 이상의 작업을 처리하는 것
    - 멀티프로세스 OR 멀티쓰레드
    - 멀티프로세스
    (1) 각각의 프로세스는 독립적이기때문에 하나의 프로세스가 잘못되어도 다른 프로세스 실행에는 영향X
    (2) 프로세스는 프로그램을 실행을 위해 동일하게 메모리 공간을 여러번 할당받으므로 그만큼 낭비일 수 있음
    (3) 독립적인 메모리를 사용하므로 각 프로세스 간 데이터 공유가 어려움


    - 멀티쓰레드
    (1) 다른 갈래의 흐름을 만듬 : 메모리 관점에서 stack 영역은 독립, static / heap은 공유(쓰레드 간 데이터 전달이 쉬움)
    (이미지)

    (2) 같은 프로세스 내에서 흐름만 여러개로 만든 것이므로 하나의 쓰레드가 잘못되면 다른 쓰레드도 영향(예외처리 잘할 것)
    (3) 같은 프로세스 내에서 흐름을 생성하는 것이므로 프로그램 실행 메모리 할당은 단 1번
    (4) 메인쓰레드로 처리하다가 병렬처리가 필요하다면 쓰레드를 생성해서 병렬처리 : 작업쓰레드라 함
    (5) 작업 중인 쓰레드가 1개라도 있다면 프로세스 종료되지않음
    (6) 설계부터 : 어떤 작업을 병렬처리할 것인가


    [자바 멀티쓰레드]
    1) 쓰레드 종류
    - 메인쓰레드
    (1) 프로세스 : 1개의 메인쓰레드를 가짐
    (2) main() 메서드 처리 : }를 만나고 종료되면 쓰레드 종료 -> 프로세스 종료

    - 작업쓰레드 :Thread 인스턴스를 생성하거나 Thread를 상속받는 하위클래스를 만들고 생성할 수 있음
    (1) 직접 생성
    - 생성자 인자로 Runnable : Thread로 실행할 수 있는 코드를 가진 객체(Interface 타입)
    - Interface Runnable 구현 : 익명 or 구현 클래스 만들기
    - 작업 코드를 가진 객체와 쓰레드 객체를 나눈 것 : 역할 구분

    (2) Thread 상속 클래스 만들기
    - Thread도 Runnable을 구현한 클래스 : run()을 가지고 있음
    - 하위 클래스에서 run()을 오버라이딩하고 Thread 타입 변수에 넣고 run() 호출하면 작업 코드 실행


    2) 멀티쓰레드 - 쓰레드 이름
    - 디버깅 목적
    - 자동적으로 Thread-n 설정, 세팅 가능
    - 쓰레드 객체에 달아주는 것(Runnable X)


    3) 멀티쓰레드 - 쓰레드 우선순위 : 쓰레드 개수 > 코어 개수 일 때 어떤 순서에 의해 쓰레드를 동시성으로 실행할 것인가
    - 서버 스펙(CPU) 체크

    - 코어 : CPU 연산 담당
    - 클럭 : CPU 연산 속도, 코어는 1개인데 연산량이 많아지면 발열과 전력소비가 커짐, 그래서 멀티코어

    - 동시성 : 1개의 코어에서 멀티쓰레드가 번갈아가며 실행(병렬성처럼 보임)
    - 병렬성 : 멀티코어에서 개별쓰레드가 동시에 실행

    - 쓰레드 스케쥴링 : 쓰레드 개수가 더 많을 때 우선순위를 정함 - 동시성 실행
    (1) 우선순위 : 우선순위가 높은 쓰레드가 실행상태를 오래가짐
    - 개발자가 우선순위를 정해줌 : 개발자 제어
    - 쓰레드인스턴스.setPriority로 정함 : 우선순위 관련 Thread 멤버상수가 있기도함
    - 1<= priority <= 10 : 모두 부여하지않으면 순환할당, 일부만 부여하지않으면 5로 할당
    - 최소 5개 이상의 쓰레드가 실행될 때 영향을 준다함
    - 대량 작업 쓰레드에 높은 우선순위 할당

    (2) 순환할당 : 정해진 시간만큼 쓰레드를 실행하고 다른 쓰레드로 작업을 옮김
    - jvm에 의해 정해짐 : 개발자 제어X


    4) 멀티쓰레드 - 멀티쓰레드 프로그램 위험성과 동기화 : mutex 접근제어 매커니즘
    - 공통 자원에 대한 동시접근이 문제가 될 수 있음
    - 특정 쓰레드가 작업하고 있는 코드(메서드, 블록)라면 접근하지못하도록 동기화 예약어 사용 : synchronized
    - 이런 코드 영역을 임계영역(critical section)이라 함 : 임계영역 지정을 위한 예약어 synchronized
    - synchronized로 선언된 블록이나 메서드를 쓰레드가 실행하면 다른쓰레드가 접근하지못하도록 객체를 잠금(?)
    - 대기상태가 됨 : 꼭 필요한 부분에서만 동기화 예약어 사용할 것, 아니면 쓸데없이 다른 쓰레드가 대기상태로


    5) 멀티쓰레드 - 쓰레드 상태 : 현재실행쓰레드.getState() > 열거상수 리턴(State 관련)
    - 쓰레드는 start()하면 바로 실행되는게 아니라 실행대기상태로 됨 : Runnable 상태
    - 스케쥴링에 의해 실행대기상태 쓰레드가 CPU 점유 -> run 메서드 실행 : Running 상태
    - 실행 상태에서 run()을 모두 실행하기 전에 스케쥴링에 의해 다시 대기상태로 갈 수 있음 : Runnable(번갈아가면서 쓰레드 실행)
    - run() 내부 코드 모두 실행 끝나면 종료 상태 : Terminated 상태
    - run() 실행 중 경우에 따라서 일시정지 상태 : 대기상태가 아니라 쓰레드 실행할 수 없는 상태
    - 전체 상태 : NEW -> RUNNABLE -> RUNNING -> TERMINATED, +PAUSE(상황 해결해야 RUNNABLE로)


    6) 멀티쓰레드 - 쓰레드 상태 제어, 번갈아서 실행되는 RUNNABLE 쓰레드의 상태를 제어
    (1) sleep : 현재 실행 중인 쓰레드 일시정지상태로, 일정시간 이후 대기상태로
    (2) yield : 다른 쓰레드에게 실행 양보, 번갈아가면서 실행되는데 현재 실행 쓰레드가 다른 쓰레드에게 실행을 넘겨줌
    - RUNNABLE 상태 쓰레드 중 yield를 호출한다면 다른 RUNNABLE 쓰레드를 실행(더 많은 실행)
    - 특정 상태일 때 yield를 호출하고 있다가, 특정 상태가 다시 변경되면 처리 코드 흐름으로
    - 쓰레드 스케줄링에서 빠지지않으나 쓰레드 흐름이 이어나가지않고 다른 쓰레드가 실행기회를 얻음

    (3) join : 다른쓰레드가 해당 쓰레드가 종료될 떄까지 기다림
    - 쓰레드가 종료되기까지 다른 쓰레드가 기다려야하는 상황일 떄 사용 : 해당 쓰레드 처리 후 특정값이 필요할 떄

    (4) notify, wait, notify
    - 같은 공유객체를 사용할 때 공유객체의 자원(메서드, 블록)이 synchronized라면 A쓰레드가 사용 중이면 B는 대기
    - A와 B 쓰레드는 공유객체 자원을 정확하게 서로 번갈아가면서 쓰는게 아니라 스케줄링에 의해 번갈아가면서 쓰게됨
    - 정확하게 자원을 반반 딱 쓰게끔 하기위해서 위의 메서드를 사용함 : Object의 메서드임
    - synchronized 메서드나 블록 내에서만 사용가능
    - Runnable은 스케줄링에 의해 실행될 수 있고, 일시정지 상태는 실행할 수 없는 상태

    (5) stop 플래그 or interrupt
    - 쓰레드 Running(혹은 Runnable) 상태 즉, run() 메서드 실행이 완전히 끝나지않은 상태에서 종료할 때 사용하는 방법
    - stop() 메서드를 제공했지만 deprecated 된 메서드 : 쓰레드가 중간에 급작스럽게 소멸될 경우 쓰레드 사용에 필요한 자원들이 찌꺼기화 될 수 있어서
    - stop 플래그 : run()을 돌리다가 특정 상태에 이르렀을 때 run 종료가 되도록 코드를 짜는 것(ThreadStateControl > ExamThread run 참고)
    - interrupt : 쓰레드가 일시정지 상태에 있을 때 호출하면 익셉션 발생 -> run() 메서드 정상종료
    => Thread.interrupted 혹은 쓰레드인스턴스.isInterrupted 로 적용되었는지 알 수 있음


    7) 데몬쓰레드
    - 메인쓰레드 작업을 돕는 역할을 수행하는 쓰레드
    - 메인쓰레드가 종료되면 데몬 쓰레드는 강제 종료 : 작업쓰레드와는 다른점, 워드프로세서 주기적으로 자동 저장, 음악플레이어 음악 재생 등
    - 메인쓰레드 : 워드프로세스 음악 플레이어, JVM(프로세스의 메인쓰레드)
    - setDaemon : 메서드로 데몬쓰레드 지정
    - isDaemon : 데몬쓰레드인지 확인


    8) 멀티쓰레드 - 쓰레드 그룹
    - 관련된 쓰레드를 묶어 관리하는 목적으로 사용
    (1) JVM 실행
    (2) system 쓰레드 그룹 생성
    (3) JVM 운영에 필요한 쓰레드 생성 후 system 그룹에 추가
    (4) system > main 쓰레드 그룹 생성
    (5) main 그룹에 메인쓰레드 추가

    - 반드시 그룹에 속함 : 명시적으로 지정해주지않으면 쓰레드를 생성한 쓰레드와 같은 그룹에 포함(main)
    - Thread.getAllStackTraces : 프로세스 내에서 사용되는 쓰레드를 모두 얻을 수 있음, 쓰레드의 그룹 조사하기
    - 왜 사용?
    1) 한꺼번에 관리
    2) 한꺼번에 적용
    - interrupt : 예외처리는 각각 생성, 일시정지 상태에서 안전종료를 위한
    - 상태 제어 관련 나머지 메서드는 deprecated : 쓰레드 안전성을 위해서
    - setDaemon : 그룹에 속한 쓰레드 데몬 적용


    9) 멀티쓰레드 - 쓰레드풀 : java.util.concurrent 패키지
    - 작업 병렬처리가 많아지면(쓰레드 개체 수 증가) 쓰레드 생성, 쓰레드 스케쥴링으로 인해 급격하게 성능이 저하됨
    - 적정 개수의 쓰레드를 미리 만들어두고 풀에 저장해뒀다가 사용하는 것 : 급격한 성능저하를 막기위한 방법(미리 만들어두기), 재사용
    - 작업 개수와 상관없이 쓰레드 개수를 정해두고 작업을 벙렬 처리함 : 자원 재활용
    - 쓰레드풀 : 큐로 구현(FIFO)
    - java.util.concurrent.ExecutorService 인터페이스 : 구현객체 얻기(쓰레드풀, Executors 객체의 static method)
    - 쓰레드 관리 쓰레드 : 쓰레드풀 쓰레드, 데몬쓰레드가 아니기때문에 쓰레드풀 쓰레드 종료해주지않으면 프로세스 종료가 안됨
    - 쓰레드풀 + 쓰레드(ExecutorService - ThreadPoolExecutor) / 작업(Runnable, Callable) / 작업처리 결과얻기(Future)

*/

public class ThreadTest {
    public static void main(String[] args) {
        /* 메인쓰레드 실행 */
        System.out.println("메인쓰레드 처리");


        /* 작업쓰레드 생성, 실행 */
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println("작업쓰레드 처리");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //Runnable task = new Task();  ->  th = new Thread(task);
        th.start(); // 쓰레드 start() -> run() 실행


        /* 작업쓰레드 생성, 실행 */
        Thread th2 = new WorkThread();
        th2.setName("하위클래스 쓰레드");
        th2.start(); //WorkThread의 run 실행

        Thread th3 = new Thread(){
            @Override
            public void run() {
                System.out.println("작업쓰레드3 처리");
            }
        };
        th3.start();


        /* 람다표현식(java8)으로 추상화하기 : Runnable 타입 객체가 넘어올 것으로 컴파일러가 추론할 수 있음 - 단일 메서드 인터페이스(함수형 인터페이스 구현) */
        Thread th4 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("ㅎㅇ");
        });
        th4.start(); // 작업쓰레드가 Runnable run() 실행

    }
}

class Task implements Runnable {

    @Override
    public void run() {
        /* 작업쓰레드가 실행할 코드 */
    }
}

class WorkThread extends Thread{
    @Override
    public void run() {
        /* 현재 작업쓰레드 */
        System.out.println("작업쓰레드2 처리");
        System.out.println("현재 작업쓰레드 : " + Thread.currentThread().getName());
    }
}
