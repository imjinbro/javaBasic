package com.jinbro.syntax.stdapi.system;


public class SystemTest {
    public static void main(String[] args) {

        /* exit : 프로세스 종료, 0은 정상종료 0이외의 값 비정상종료
        System.exit(0); */

        /* setSecurityManager : exit()가 실행되면 SecurityManager checkExit 메서드 자동호출 - 특정값에 대한 처리 가능 */
        System.setSecurityManager(new SecurityManager(){
            @Override
            public void checkExit(int status) {
                if(status == 5){
                    throw new SecurityException();
                }
            }
        });

        try{
            System.exit(5); //이전에는 어떤 값을 넣어도 종료되었지만 지금은 익셉션 발생(처리하면 종료되지않음)
        } catch(SecurityException e){
            System.out.println(e.getMessage());
        }


        /* gc
            1) 가비지컬렉터 처리 요청 : 바로 실행하지않음
            2) 힙영역 객체 주소값이 스택영역 변수에 저장되어있지않으면 jvm은 메모리 부족, cpu 한가할 때 할당한 메모리 회수해감
            3) 쓰레기 생길 때마다 처리 요청한다면 오히려 메모리 낭비(메모리 열악한 환경에서 사용) */
        Member m1 = new Member(1);
        m1 = null;
        System.gc(); // 메모리 해제 전 Member.finalize() 실행 : 언제 처리될지 모름


        /* currentTimeMillis : 시스템 시간을 1/1000초로 리턴, 주로 실행시간을 측정하기위해서 사용(현재 - 이전) */
        long before = 0;
        for(int i=0; i<3; i++){
            if(i == 0){
                before = System.currentTimeMillis();
                continue;
            }

            long gap = System.currentTimeMillis() - before;
            System.out.println(gap);
        }

        /* nanoTime : 시스템 시간을 1/109초로 리턴, 주로 실행시간을 측정하기위해서 사용(현재 - 이전) */
        long time1 = System.nanoTime();

        int cnt = 0;
        for(int i=0; i<300; i++){
            cnt += i;
        }

        long time2 = System.nanoTime();
        System.out.println(time2 - time1 + "나노초 소요됨");


        /* getProperty(key)
            1) 운영체제, jvm 시스템 속성값을 얻어올 수 있음 : 클라이언트 시스템 속성에따라 분기처리(?)
            2) key(파라미터) - value(리턴값) 구성
            3) 일부 key는 permission denied에 의한 예외 발생
            4) System.getProperties() : HashTable<T, T> 타입, key - value 객체 리턴*/
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("os.name"));


        /* getenv(key)
            1) 운영체제에서 제공하는 환경변수(value)를 읽어옴(프로그램에 제공 목적)
            2) 내부적으로 SecurityManager로 퍼미션 체크함

            getenv()
            1) 환경변수(key-value) 목록 */
        try {
            //access denied, 퍼미션 어떻게 주는지도
            System.out.println(System.getenv());
            System.out.println(System.getenv("JAVA_HOME"));
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}

class Member{

    private int num;

    public Member(int num){
        this.num = num;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this.num +"이 가비지컬렉터에 의해 제거되었습니다");
    }
}



