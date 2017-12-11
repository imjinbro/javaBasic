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


        TV tv = new TV();
        Command power = new PowerCommand(tv);
        Command mute = new MuteCommand(tv);

        TwoButtonController controller = new TwoButtonController(power, mute);
        controller.setButton2Command(power);
        controller.button1Pressed();
        controller.button1Pressed();
        controller.button2Pressed();
        controller.button1Pressed();

        controller.setButton1Command(mute);
        controller.setButton2Command(power);
        controller.button1Pressed();
        controller.button2Pressed();
        controller.button1Pressed();
        controller.button1Pressed();
        controller.button2Pressed();
        controller.button1Pressed();
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

/***************** 연습문제 *****************/
//중간 컨트롤러 + 커맨드(실제 TV 컨트롤 - 동작) + TV(컨트롤 대상) 형태
class TwoButtonController{
    private Command c1;
    private Command c2;

    public TwoButtonController(Command c1, Command c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public void setButton1Command(Command c1){
        this.c1 = c1;
    }

    public void setButton2Command(Command c2){
        this.c2 = c2;
    }

    public void button1Pressed(){
        c1.execute();
    }

    public void button2Pressed(){
        c2.execute();
    }
}

class MuteCommand implements Command{
    private TV tv;

    public MuteCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.mute();
    }
}

class PowerCommand implements Command{
    private TV tv;

    public PowerCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.power();
    }
}

class TV{
    private boolean mute;
    private boolean power;

    public TV() {
        mute = false;
        power = false;
    }

    public void mute(){
        if(!power){
            return;
        }
        mute = !mute;
        System.out.println("음소거 " + mute);
    }

    public void power(){
        power = !power;
        if(power){
            System.out.println("파워 ON");
        } else {
            System.out.println("파워 OFF");
        }
    }
}