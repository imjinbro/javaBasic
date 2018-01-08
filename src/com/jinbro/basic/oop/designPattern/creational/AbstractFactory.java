package com.jinbro.basic.oop.designPattern.creational;

/*
    [기존 팩토리메서드 패턴 설계 문제점]
    - 부품 종류가 추가될 때마다 해당 부품의 팩토리를 만들어야함
    - 여러가지 방식이 아니라 일관성있게 인스턴스를 생성한다면 오히려 코드만 더 증가하는 꼴 : switch case 증가

    [추상팩토리 패턴 설계]
    - 하나의 팩토리를 통해서(ID값만 던져주고 팩토리를 얻어오면) create부품()메서드로 얻어올 수 있음
    - 필요에 따라 팩토리를 변경해서 다른 것을 가져올 수도 있음
    - 일관된 인터페이스랄까...?
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



/***************** 연습문제 : 일관된 인터페이스로 만들기 요청(ID값만 주면 하나의 인스턴스에서 원하는 부품 얻을 수 있음) *****************/
class Navi{
    //NaviFactory 하나의 사용 예시 : naviID만 다르게 주면 각각의 부품을 요청할 수 있음
    private GPS gps;
    private PathFinder pathFinder;
    private Screen screen;

    public Navi(NaviID naviID) {
        initNavi(naviID);
    }

    private void initNavi(NaviID naviID){
        NaviFactory factory = NaviFactory.getFactory(naviID);
        gps = factory.createGPS();
        pathFinder = factory.createPathFinder();
        screen = factory.createScreen();
    }
}

enum NaviID{
    TEST,
    CHEAP,
    EXPENSIVE
}

/*  새로운 모델이 생겼을 때 일일이 부품별로 ID 대응을 하지않고(switch) 추상팩토리에서만 대응해주면 됨
    public Screen createScreen(NaviID naviID){
            Screen screen = null;
            switch(naviID){
                case CHEAP:

                    break;

                case EXPENSIVE:

                    break;

                //추가되면 추가되는대로 부품별로 모두 추가해줘야함
            }

            return screen;
        }
*/
abstract class NaviFactory{
    public static NaviFactory getFactory(NaviID naviID){
        NaviFactory factory = null;
        switch (naviID){
            case CHEAP:
                factory = CheapNaviFactory.getInstance();
                break;

            case EXPENSIVE:
                factory = ExpensiveNaviFactory.getInstance();
                break;

            case TEST:
                factory = TestNaviFactory.getInstance();
                break;
        }

        return factory;
    }

    public abstract PathFinder createPathFinder();
    public abstract GPS createGPS();
    public abstract Screen createScreen();
}

class CheapNaviFactory extends NaviFactory{
    private static CheapNaviFactory factory = new CheapNaviFactory();
    private CheapNaviFactory(){}

    public static CheapNaviFactory getInstance() {
        return factory;
    }


    @Override
    public PathFinder createPathFinder() {
        return new CheapPathFinder();
    }

    @Override
    public GPS createGPS() {
        return new CheapGPS();
    }

    @Override
    public Screen createScreen() {
        return new SDScreen();
    }
}

class ExpensiveNaviFactory extends NaviFactory{
    private static ExpensiveNaviFactory factory = new ExpensiveNaviFactory();
    private ExpensiveNaviFactory(){}

    public static ExpensiveNaviFactory getInstance() {
        return factory;
    }


    @Override
    public PathFinder createPathFinder() {
        return new ExpensivePathFinder();
    }

    @Override
    public GPS createGPS() {
        return new ExpensiveGPS();
    }

    @Override
    public Screen createScreen() {
        return new HDScreen();
    }
}

class TestNaviFactory extends NaviFactory{
    private static TestNaviFactory factory = new TestNaviFactory();
    private TestNaviFactory(){}

    public static TestNaviFactory getInstance() {
        return factory;
    }


    @Override
    public PathFinder createPathFinder() {
        return new TestPathFinder();
    }

    @Override
    public GPS createGPS() {
        return new TestGPS();
    }

    @Override
    public Screen createScreen() {
        return new TestScreen();
    }
}



abstract class PathFinder{

}

class CheapPathFinder extends PathFinder{

}

class ExpensivePathFinder extends PathFinder{

}

class TestPathFinder extends PathFinder{

}


abstract class Screen{

}

class SDScreen extends Screen{

}

class HDScreen extends Screen{

}

class TestScreen extends Screen{

}


abstract class GPS{

}

class CheapGPS extends GPS{

}

class ExpensiveGPS extends GPS{

}

class TestGPS extends GPS{

}


