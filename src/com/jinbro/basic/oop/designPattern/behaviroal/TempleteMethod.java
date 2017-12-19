package com.jinbro.basic.oop.designPattern.behaviroal;

public class TempleteMethod { }

/*
    [기존 코드 문제점]
    - 모터의 종류가 추가될 경우 코드 중복

    [문제 해결 그리고 문제 그리고 문제 해결]
    1) 일반화시키기(extends 모델링)로 코드 중복 피하기
    - 각각 다른 부분은? 각각이 구현하면 되잖아? 근데 부분적으로 중복되는 코드가 있어

    2) 부분적으로 중복되는 코드 없애기
    - 해당 부분을 abstract 메서드로 만들어서 각각이 구현하게끔


    [템플릿 메서드 패턴]
    - extends 클래스 관계에서 중복되는 부분만 각각이 구현하도록 함 : 내용만 다르지 행동한다는 것은 같음
    - extends(상속 혹은 확장) + 다형성
    - 템플릿메서드 : move, 훅메서드 : moveMotor(각각 오버라이딩 될 필요가 있는 메서드)
 */
abstract class Motor{
    protected Door door;
    private MotorStatus motorStatus;

    public Motor(Door door) {
        this.door = door;
        motorStatus = MotorStatus.STOPPED;
    }

    protected void setMotorStatus(MotorStatus motorStatus) {
        this.motorStatus = motorStatus;
    }

    public MotorStatus getMotorStatus() {
        return motorStatus;
    }

    protected boolean isMoved(){
        return getMotorStatus().equals(MotorStatus.MOVING);
    }

    public void move(Direction direction) {
        if (isMoved()) {
            return;
        }
        door.close();
        setMotorStatus(MotorStatus.MOVING);

        //다른 부분은 구현 클래스가 각각 구현하도록 : move() 메서드 전체로 보면 부분만 따로 구현
        moveMotor();
    }

    abstract protected void moveMotor();
}

class HyundaiMotor extends Motor{
    public HyundaiMotor(Door door) {
        super(door);
    }

    @Override
    protected void moveMotor() {
        System.out.println("현대모터");
        System.out.println("무    브");
        System.out.println("무    브");
    }

    /*public void move(Direction direction) {
        if (isMoved()) {
            return;
        }
        door.close(); //나는 상태에 관계없이 명령을하지만 도어가 알아서 처리를 함
        setMotorStatus(MotorStatus.MOVING); //여기까진 모든 모터가 다 똑같은데

        //여기부터만 다름
        moveHyundaiMotor();
    }

    private void moveHyundaiMotor(){
        System.out.println("현대모터");
        System.out.println("무    브");
        System.out.println("무    브");
    }*/
}

class SamsungMotor extends Motor{
    public SamsungMotor(Door door) {
        super(door);
    }

    @Override
    protected void moveMotor() {
        System.out.println("삼성모터 무브무브");
    }

    /*public void move(Direction direction) {
        if (isMoved()) {
            return;
        }
        door.close();
        setMotorStatus(MotorStatus.MOVING);

        //여기부터만 다름
        playSamsungMotor();
    }

    private void playSamsungMotor(){
        System.out.println("삼성모터 무브무브");
    }*/
}

class Door{
    private DoorStatus status;

    public Door(DoorStatus status){
        this.status = status;
    }

    private void setDoorStatus(DoorStatus status){
        this.status = status;
    }

    public void open(){
        if(!isOpened()){
            setDoorStatus(DoorStatus.OPENED);
        }
    }

    public void close(){
        if(isOpened()){
            setDoorStatus(DoorStatus.CLOSED);
        }
    }

    private boolean isOpened(){
        return getDoorStatus().equals(DoorStatus.OPENED);
    }

    public DoorStatus getDoorStatus(){
        return status;
    }
}



enum Direction{
    UP,
    DOWN
}

enum MotorStatus{
    MOVING,
    STOPPED
}

enum DoorStatus{
    OPENED,
    CLOSED
}

