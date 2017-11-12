package com.jinbro.basic.principleoop;

public class SRP {
    public static void main(String[] args) {
        Player minho = new BaseballPlayer();
        Player jisung = new FootballPlayer();

        minho.play();
        jisung.play();
    }
}

/* 하나의 플레이어가 모든 종목선수 역할을 다 책임진다면 분기문이 그만큼 많아짐 */
abstract class Player {

    abstract void play();
    /*private boolean isBaseball;
    private boolean isFootball;
    private boolean isVallyball;

    public void play(){
        if(isBaseball){

        } else if(isFootball){

        } else {

        }
    }*/
}

class BaseballPlayer extends Player{

    @Override
    void play() {

    }
}

class FootballPlayer extends Player{

    @Override
    void play() {

    }
}


abstract class Developer{
    abstract void develop(Runnable task);

    /*
        void makeJSP();
        void makeJSscript();
        void setUpWAS();

        1개의 클래스가 여러 책임을 지고 있다면 특정 인스턴스가 쓰지않는 메서드가 늘어남
     */
}

class FrontEndDeveloper extends Developer{

    @Override
    public void develop(Runnable task) {

    }
}

class ServerDeveloper extends Developer{

    @Override
    public void develop(Runnable task) {

    }
}


