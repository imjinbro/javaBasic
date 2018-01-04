package com.jinbro.basic.oop.designPattern.creational;

/*
    [기존 팩토리메서드 패턴 설계 문제점]
    - 부품 종류가 추가될 때마다 해당 부품의 팩토리를 만들어야함
    - 여러가지 방식이 아니라 일관성있게 인스턴스를 생성한다면 오히려 코드만 더 증가하는 꼴 : switch case 증가

    [추상팩토리 패턴 설계]
    - 각 부품별로 팩토리를 만들지않고 벤더별로 팩토리를 만들 수 있게 최상위 개념의 팩토리를 만든 후 벤더별 구현하도록 함
    - 벤더가 추가될 때 해당 벤더 팩토리만 만들면 됨
    - 부품이 추가된다면 팩토리 최상위에 해당 부품 생성 메서드를 abstract로 선언해두고 각 팩토리에서 벤더에 맞게 구현

    - 깔끔한 관리랄까... 변경 적용할 때 위험성을 줄이는 패턴이랄까
    - 팩토리메서드 단점을 채워주는 패턴

 */
public class AbstractFactory {
    public static void main(String[] args) {
        /* 설계상 엘리베이터 모터가 내부에서 다른 부품 제어(요청)
        ElevatorMotorFactory motorFactory = ElevatorMotorFactory.getMotorFactory();
        ElevatorMotor motor = motorFactory.createMotor(VendorID.LG);
        motor.move();
        motor.move();
        */


    }
}


abstract class ElevatorFactory{
    /* 내부 엘리베이터 생성되는 방식을 캡슐화 */
    protected abstract Elevator createElevator();
}

class LGElevatorFactory extends ElevatorFactory{
    private static LGElevatorFactory factory = new LGElevatorFactory();
    private LGElevatorFactory(){}

    public static LGElevatorFactory getFactory() {
        return factory;
    }


    @Override
    protected Elevator createElevator() {
        return null;
    }
}

class SAMSUNGElevatorFactory extends ElevatorFactory{
    private static SAMSUNGElevatorFactory factory = new SAMSUNGElevatorFactory();
    private SAMSUNGElevatorFactory(){}

    public static SAMSUNGElevatorFactory getFactory() {
        return factory;
    }


    @Override
    protected Elevator createElevator() {
        return null;
    }
}


abstract class Elevator{
    protected ElevatorMotor motor;
    protected ElevatorDoor door;
}

class LGElevator extends Elevator{

}

class SAMSUNGElevator extends Elevator{

}

/*
enum VendorID{
    LG,
    SAMSUNG
}

class ElevatorMotorFactory{
    private static ElevatorMotorFactory motorFactory = new ElevatorMotorFactory();
    private ElevatorDoorFactory doorFactory = ElevatorDoorFactory.getDoorFactory();
    private ElevatorMotorFactory(){ }

    public static ElevatorMotorFactory getMotorFactory() {
        return motorFactory;
    }

    public ElevatorMotor createMotor(VendorID vendorID){
        ElevatorMotor motor = null;

        switch(vendorID){
            case LG :
                motor = new LGElevatorMotor(doorFactory.createDoor(vendorID));
                break;

            case SAMSUNG:
                motor = new SAMSUNGElevatorMotor(doorFactory.createDoor(vendorID));
                break;
        }

        return motor;
    }
}

class ElevatorDoorFactory{
    private static ElevatorDoorFactory doorFactory = new ElevatorDoorFactory();
    private ElevatorDoorFactory(){ }

    public static ElevatorDoorFactory getDoorFactory() {
        return doorFactory;
    }

    public ElevatorDoor createDoor(VendorID vendorID){
        ElevatorDoor door = null;

        switch(vendorID){
            case LG :
                door = new LGElevatorDoor();
                break;
        }

        return door;
    }
}*/


enum ElevatorMotorStatus{
    MOVING,
    STOPPED
}

abstract class ElevatorMotor{
    private ElevatorMotorStatus status;
    private ElevatorDoor door;

    public ElevatorMotor() {
        this.status = ElevatorMotorStatus.STOPPED;
    }

    private boolean isDoor(){
        return door != null;
    }

    public void setDoor(ElevatorDoor door){
        this.door = door;
    }

    private boolean isStop(){
        return getStatus().equals(ElevatorMotorStatus.STOPPED);
    }

    public void move(){ //문 상태(원래 닫혀있다면 아무런 반응없음), 자신의 상태 체크(움직이면 X)
        if(isDoor()){
            throw new NullPointerException("문 설정좀");
        }

        if(!isStop()){
            return;
        }

        door.close();
        setStatus(ElevatorMotorStatus.MOVING);
        moveMotor();
        stop();
    }

    private void stop(){
        if(isDoor()){
            throw new NullPointerException("문 설정좀");
        }

        if(isStop()){
            return;
        }

        setStatus(ElevatorMotorStatus.STOPPED);
        door.open();
    }

    protected abstract void moveMotor();

    private ElevatorMotorStatus getStatus() {
        return status;
    }

    private void setStatus(ElevatorMotorStatus status) {
        this.status = status;
    }


}

class LGElevatorMotor extends ElevatorMotor{

    @Override
    protected void moveMotor() {
        System.out.println("LG Motor move");
    }
}

class SAMSUNGElevatorMotor extends ElevatorMotor{

    @Override
    protected void moveMotor() {
        System.out.println("SAMSUNG Motor move");
    }
}


enum ElevatorDoorStatus{
    OPEN,
    CLOSED
}

abstract class ElevatorDoor{
    private ElevatorDoorStatus status;

    public ElevatorDoor() {
        this.status = ElevatorDoorStatus.CLOSED;
    }

    public boolean isClosed(){
        return getStatus().equals(ElevatorDoorStatus.CLOSED);
    }

    public void open(){
        if(!isClosed()){
            return;
        }

        setStatus(ElevatorDoorStatus.OPEN);
        doOpen();
    }

    protected abstract void doOpen();

    public void close(){
        if(isClosed()){
            return;
        }

        setStatus(ElevatorDoorStatus.CLOSED);
        doClose();
    }

    abstract void doClose();

    private ElevatorDoorStatus getStatus() {
        return status;
    }

    private void setStatus(ElevatorDoorStatus status) {
        this.status = status;
    }
}

class LGElevatorDoor extends ElevatorDoor{

    @Override
    protected void doOpen() {
        System.out.println("LG Door Open");
    }

    @Override
    protected void doClose() {
        System.out.println("LG Door Close");
    }
}


class SAMSUNGElevatorDoor extends ElevatorDoor{

    @Override
    protected void doOpen() {
        System.out.println("SAMSUNG Door Open");
    }

    @Override
    protected void doClose() {
        System.out.println("SAMSUNG Door Close");
    }
}
