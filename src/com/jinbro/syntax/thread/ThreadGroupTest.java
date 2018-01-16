package com.jinbro.syntax.thread;

import java.util.Map;
import java.util.Set;

public class ThreadGroupTest {
    public static void main(String[] args) {
        /* 쓰레드 그룹명 얻기 */
        System.out.println(Thread.currentThread().getThreadGroup().getName());

        /* 프로세스 내에서 실행하는 모든 쓰레드 정보 : Map<쓰레드객체, 쓰레드객체 상태기록> */
        Map<Thread, StackTraceElement[]> all = Thread.getAllStackTraces();
        Set<Thread> threads = all.keySet();
        for(Thread thread : threads){
            System.out.println("쓰레드명 : " + thread.getName() +
                               ", 쓰레드 그룹 : " + thread.getThreadGroup().getName() +
                               ", 데몬쓰레드 여부 : " + thread.isDaemon());
        }


        /* 스레드 그룹 생성 및 지정 */
        ThreadGroup gpDownload = new ThreadGroup("download"); //상위 그룹을 명시해주지않으면 현재 쓰레드가 속한 그룹이 자동지정
        MusicDownload th1 = new MusicDownload(gpDownload, "thread1"); //그룹 지정
        MusicDownload th2 = new MusicDownload(gpDownload, "thread2");

        th1.start();
        th2.start();

        System.out.println("#####################################");
        System.out.println("[gpDownload 쓰레드그룹에 속한 쓰레드 정보 : 실행되고있는 쓰레드]");
        gpDownload.list(); // [쓰레드명,우선순위,그룹명]
        System.out.println("#####################################");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gpDownload.interrupt(); //th1.interrupt(), th2.interrupt() 각각 호출안해줘도됨
    }
}

class MusicDownload extends Thread {

    public MusicDownload(ThreadGroup group, String name) {
        super(group, name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getThreadGroup().getName());

        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted");
            }
        }
    }
}

