/*
    [예외처리]

    [예외]
    1) 사용자의 잘못된 조작
    2) 개발자의 잘못된 코딩

    - 위 2가지 이유로 인해서 발생하는 오류를 예외(exception)라 함, jvm 불능되는 오류를 에러라 함
    - 예외가 발생하면 응용프로그램이 종료됨
    - 아래는 예외의 종류
    1) 일반 예외 : 컴파일 과정에서 체크되는 예외, 코드 상(컴파일 과정에서 처리)에서 발생하는 예외
    2) 실행 예외 : 컴파일 과정에서 체크되지않는 예외, 런타임 떄 발생하는 예외
    3) 어플리케이션 예외 : 사용자정의 예외(1, 2번 중 하나에 속해짐), 아래에서 어떻게 만드는지 설명


    - 자바는 예외를 객체로 관리함 : 예외 발생 시 예외 객체 생성(최상위 : java.lang.Exception)
    1) 일반 예외 : Exception 상속, RuntimeException 상속X
    2) 실행 예외 : RuntimeException 상속(Exception 상속)


    - 예외
    1) 일반 예외 : 컴파일 과정에서 예외처리코드가 없을 때 컴파일 오류 발생
    - ClassNotFound : 리플렉션을 사용할 때 없는 클래스를 로딩하려고 할 때
    - IO : 입출력할 때 오류 발생

    2) 실행 예외 : 컴파일러가 체크하지않음, 경험에의해 예외처리코드를 작성해야함
    - NullPointer : 스택변수가 참조하는 객체가 없음에도 호출될 때
    - ArrayIndexOutOfBounds : 인덱스 범위를 초과하여 사용할 때
    - NumberFormat : 숫자로 변환할 수 없는 문자가 포함되어있을 때(parseInt, parseDouble 등)
    - ClassCast : 객체 간 아무런 관계도 아닌데 캐스팅, 상위 인스턴스 주소를 하위 클래스 타입 변수에 저장할 때(instanceof 예방)


    - 실행예외 확인
    1) 익셉션 클래스네임 입력 후 ctrl + 클릭으로 클래스 상속관계 파악하기


    [예외처리]
    - 예외가 발생했을 때 응용프로그램이 곧바로 종료되지않고 유지될 수 있도록 처리하는 것
    - 예외처리 코드에서 예외 발생 시 생성되는 예외 객체를 이용할 수 있음
    1) try : 실행코드 작성
    2) catch
    - 예외가 발생했을 때 어떤 처리를 할 것인지 작성
    - 다중 예외처리 : catch(Exception e)로 처리하거나 여러개 예외 catch를 1개 try에 걸수도
    - 상세하게 예외처리를 하기위해 다중 예외처리 시 주의사항 : 상위 예외 클래스가 아래에 위치해야함(아니면 상위 예외에 다걸림)
    - e.getMessage() : 예외발생 시 전달하는 메세지를 얻을 수 있음
    - e.printStackTrace() : 어느 지점에서 예외발생했는지 콘솔 출력(메서드 스택프레임)

    3) finally : 예외와 상관없이 반드시 실행되어야하는 코드 작성, 생략 가능함
    (코드)


    [리소스 예외처리 - java7~]
    1) 다중예외처리 : catch(ArrayIndexOutBoundsException | NumberFormatException e)로 처리
    2) 리소스 객체 사용 시 예외 발생여부와 상관없이 리소스 스트림 닫기
    - 리소스 객체란? 데이터를 읽고 쓰는 객체, 각종 입출력 스트림, 소켓 등
    - try- with-resources 사용
    (코드)

    - 사용조건, 조건에 맞는지 찾아보기
    1) 리소스 클래스가 AutoCloseable 인터페이스를 구현하고 있어야함 : try-with-resources가 close() 자동호출
    2) AutoCloseable 구현 클래스 찾기 : API 문서 검색 > All Known Implementing Classes 리스트

    - AutoCloseable close() 호출 확인
    (코드)


    [예외처리 - 떠넘기기]
    - 예외가 발생할 것으로 예상되는 메서드, 생성자에서 예외를 처리하지않고 호출한 곳에서 처리하도록 떠넘기는 것
    - throws : 호출한 곳으로 예외 발생 시 떠넘기기
    - 떠넘기기

    - 메서드 뒷부분에 throws 떠넘길 예외클래스 를 적어줌
    - 예외클래스를 모두 적지않고 Exception 최상위 클래스를 적어주면 간단
    - 호출할 쪽에서 예외처리를 해야함
    - main()도 throws 가능 : jvm이 예외처리 -> 콘솔출력이 예외처리, 사용하지않는 것이 좋음


    [어플리케이션예외 - 사용자정의예외]
    - RuntimeException 상속여부에 따라 일반예외, 실행예외 결정
    - 생성자 선언만 포함
    (코드)


    [추가]
    1) try - catch - finally는 각각 { .. } : 로컬 변수


 */

package com.jinbro.source.exception;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExceptionTest {

    public static void main(String[] args){

        int cnt = 0;

        try {
            Class car = Class.forName("com.jinbro.source.polymorphism.Car");

        }catch (ClassNotFoundException e){
            System.out.println("해당 클래스가 존재하지않습니다");
        }


        //runtime exception
        String str = null;
        try{
            System.out.println(str);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        } finally {
            cnt++;
        }

        //runtime exception
        char[] cArr = new char[3];
        try{
            System.out.println(cArr[3]);
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("초과하셨습니다");
        } finally {
            cnt++;
        }

        //runtime exception
        String str2 = "1234a";
        try{
            int num = Integer.parseInt(str2);
        } catch(NumberFormatException e){
            System.out.println("변환할 수 없습니다");
        } finally {
            cnt++;
        }

        //runtime exception
        try{
            Animal animal = new Animal();
            Dog dog = (Dog)animal;
        } catch(ClassCastException e){
            System.out.println("잘못된 캐스팅입니다");
        } finally {
            cnt++;
        }

        System.out.println(cnt);


        //multi catch(1)
        try{
            String msg = null;
            System.out.println(msg);
        } catch (Exception e){
            System.out.println("category : " + e.getMessage());
        }


        //multi catch(2)
        try{

        } catch(NullPointerException e){

        } catch(NumberFormatException e){

        } catch(ClassCastException e){

        }



        // java7~ multi catch
        try{

        } catch(ClassCastException | NumberFormatException | ArrayIndexOutOfBoundsException e){

        } catch(Exception e) {

        }



        // ~java6 리소스 객체 사용할 때 예외처리
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("file.txt");
        } catch (IOException e){

        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch(IOException e){

                }
            }
        }

        // java7~ 리소스 객체 사용할 때 예외처리(1) : try-with-resources
        try(FileInputStream fis1 = new FileInputStream("file.txt")){

        } catch (IOException e){

        }

        // java7~ 리소스 객체 사용할 때 예외처리(2) : try-with-resources
        try(FileInputStream fis2 = new FileInputStream("input.txt");
            FileOutputStream fos = new FileOutputStream("output.txt"))
        {
            /* try */
        } catch(IOException e){

        }


        // 사용자정의예외 테스트
        try{
            throw new JinbroException("진브로 예외발생!");
        }catch(JinbroException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}

class Animal {

}

class Dog extends Animal {
    void Bark(){

    }
}


class XFileInputStream implements AutoCloseable{

    public static void main(String[] args) {
        try(XFileInputStream xis = new XFileInputStream("jinbro.txt")){
            xis.read();
            throw new Exception(); //강제예외발생
        }catch(Exception e){
            System.out.println("예외발생!");
        }
    }

    String file;

    public XFileInputStream(String name){
        file = name;
    }

    public void read(){
        System.out.println(file + " 읽기 시작");
    }


    @Override
    public void close() throws Exception {
        System.out.println("읽기 종료");
    }
}
//예외가 발생하면 close()가 자동실행
