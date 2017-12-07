package com.jinbro.basic.oop.designPattern.behaviroal;

/*
    [커맨드 패턴]
    - 개별 클래스에 의존하지않고 상위개념(abstract, interface)에 의존 : 확장이 다른 클래스 코드 영향을 주면 안됨
    - 하나의 버튼 클릭 기능으로 여러개의 동작을 구현할 때
    (1) 클릭이라는 행동을 가진 명령 집합(abstract, interface)을 만들기 : 다형성
    (2) 커맨드를 여러개로 만들되 하나의 집합, 이를 실행시킬 클래스 : 실행시키는 클래스 코드 변경하지않고도 커맨드 변경가능
 */
class CommandExam{
    public static void main(String[] args) {
        Lamp myLamp = new Lamp();
        Button btn = new Button(new LampOnCommand(myLamp));
        btn.press();

        btn.setTarget(new Alarm());
        btn.press();
    }
}

public interface Command{
    void execute();
}

/* state 패턴으로도 */
class Lamp {
    public void turnOn(){
        System.out.println("lamp on");
    }

    public void turnOff(){
        System.out.println("lamp off");
    }
}

class LampOnCommand implements Command{
    private Lamp lamp;

    public LampOnCommand(Lamp lamp) {
        this.lamp = lamp;
    }

    @Override
    public void execute() {
        lamp.turnOn();
    }
}

class LampOffCommand implements Command{
    private Lamp lamp;

    public LampOffCommand(Lamp lamp) {
        this.lamp = lamp;
    }

    @Override
    public void execute() {
        lamp.turnOff();
    }
}


class Alarm implements Command{
    @Override
    public void execute() {
        System.out.println("알람꺼짐");
    }
}

class Button{
    private Command target;

    public Button(Command target) {
        this.target = target;
    }

    public void setTarget(Command target) {
        this.target = target;
    }

    public void press(){
        target.execute();
    }
}