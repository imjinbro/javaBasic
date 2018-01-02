package com.jinbro.basic.oop.designPattern.creational;

import java.util.*;

/*
    [팩토리 메서드 패턴]
    - 스트래티지 패턴처럼 여러가지 방법이 존재하거나 존재할 것으로 예상될 때 사용함
    - 여러가지 방법들을 미리 생성해두고(팩토리 클래스의 메서드 통해서) 꺼내 쓰는 방식
    - 결론 : 전략과 사용하는 쪽을 분리해둠 - 전략이 어떻게 변경되더라도 사용하는 쪽에서는 상관X
 */
public class FactoryMethod {
    public static void main(String[] args) {
        SchedulerFactory.addScheduler(SchedullerID.THROUGHPUT, ThroughputScheduler.getScheduler());
        SchedulerFactory.addScheduler(SchedullerID.RESPONSE_TIME, ResponseTimeScheduler.getScheduler());

        ElevatorManager manager = new ElevatorManager(5, SchedullerID.THROUGHPUT);
        manager.requestElevator(5, Direction.UP);

        manager.setSchedullerID(SchedullerID.RESPONSE_TIME);
        manager.requestElevator(6, Direction.DOWN);
    }
}


/*
    [예시 코드]
    매니저를 통해서 요청을 받으면 스케쥴러에 어떤 엘리베이터 컨트롤러를 사용할지 요청을 해서 결과를 받으면 그 엘리베이터 컨트롤러 조작
*/
class ElevatorController{
    private int id;
    private int floor;

    public ElevatorController(int id) {
        this.id = id;
        this.floor = 1; //기본 1층
    }

    public void gotoFloor(int floor){
        setFloor(floor);
    }

    private void setFloor(int floor) {
        /* 상태에 따라 처리하도록 */
        this.floor = floor;
        System.out.println("[" + id + "] : " + floor + "로 이동되었습니다.");
    }

    public int getFloor() {
        return floor;
    }

    public int getId() {
        return id;
    }
}

interface ElevatorScheduler{
    int selectElevatorCtrl(ElevatorManager manager, int destination, Direction direction);
}

class ThroughputScheduler implements ElevatorScheduler{
    private static ThroughputScheduler scheduler = new ThroughputScheduler();
    private ThroughputScheduler(){}

    public static ThroughputScheduler getScheduler(){
        return scheduler;
    }

    /* 변경사항이 많을 부분 */
    @Override
    public int selectElevatorCtrl(ElevatorManager manager, int destination, Direction direction){ //return elevatorctrl id
        ArrayList<ElevatorController> controllers = manager.getCtrlList();
        if(!isExistCtrl(controllers)){
            throw new RuntimeException("제어 중인 엘리베이터가 없습니다");
        }

        System.out.println("Throughput");
        return 0;
    }

    private boolean isExistCtrl(ArrayList<?> controllerList){
        return controllerList.size() != 0;
    }
}

class ResponseTimeScheduler implements ElevatorScheduler {
    private static ResponseTimeScheduler scheduler = new ResponseTimeScheduler();
    private ResponseTimeScheduler(){}

    public static ResponseTimeScheduler getScheduler() {
        return scheduler;
    }

    @Override
    public int selectElevatorCtrl(ElevatorManager manager, int destination, Direction direction) {
        System.out.println("responseTime");
        return 0;
    }
}

/*
    - 직접 스케쥴러를 생성하지않고 스케쥴러 팩토리에 요청해서 스케쥴러 인스턴스를 리턴받아 사용
    - 스트래티지 패턴처럼 여러 전략을 사용하는 쪽에서 바로 set하지않고 ID값으로 팩토리에 요청해서 ID에 해당하는 객체를 리턴받는 방식
*/
class SchedulerFactory{
    private static Map<SchedullerID, ElevatorScheduler> schedulerList = new HashMap<>();

    public static void addScheduler(SchedullerID schedulerID, ElevatorScheduler scheduler){
        if(isExist(schedulerID)){
            System.out.println("이미 추가되어져있습니다.");
            return;
        }
        schedulerList.put(schedulerID, scheduler);
    }

    public static ElevatorScheduler getScheduler(SchedullerID schedulerID){
        if(!isExist(schedulerID)){
            throw new NullPointerException("존재하지않는 스케쥴러");
        }

        return schedulerList.get(schedulerID);
    }

    private static boolean isExist(SchedullerID schedullerID){
        return schedulerList.containsKey(schedullerID);
    }
}

enum SchedullerID{
    THROUGHPUT,
    RESPONSE_TIME
}

class ElevatorManager{
    private ArrayList<ElevatorController> ctrlList = new ArrayList<>();
    //private ThroughputScheduler scheduler = new ThroughputScheduler(); 고정된 방법
    private SchedullerID schedullerID;

    public ElevatorManager(int ctrlNum, SchedullerID schedullerID){
        initCtrl(ctrlNum);
        setSchedullerID(schedullerID);
    }

    private void initCtrl(int ctrlNum){
        for(int i=0; i<ctrlNum; i++){
            ctrlList.add(new ElevatorController(i));
        }
    }

    public void setSchedullerID(SchedullerID schedullerID){
        this.schedullerID = schedullerID;
    }

    private ElevatorScheduler getScheduler(SchedullerID schedullerID){
        return SchedulerFactory.getScheduler(schedullerID);
    }

    public void requestElevator(int destination, Direction direction){
        ElevatorScheduler scheduler = getScheduler(schedullerID);
        int ctrlElevatorId = scheduler.selectElevatorCtrl(this, destination, direction);

        ElevatorController elevatorController = ctrlList.get(ctrlElevatorId);
        elevatorController.gotoFloor(destination);
    }

    public ArrayList<ElevatorController> getCtrlList(){
        return ctrlList;
    }
}


/****************** 연습문제 : 스테이트 + 템플릿메서드 + 팩토리메서드(차 상태 인스턴스를 관리 : 다양할 수 있기때문, 상태 인스턴스는 싱글턴 - 동기화처리) ******************/
class Car{
    private int speed = 0;
    private CarState carState;
    private CarStateFactory factory = CarStateFactory.getFactory();

    public Car() {
        this.carState = factory.getCarState(this, CarStateID.NORMAL);
    }

    public void setCarState(CarStateID carStateID) {
        //인자로 넘겨줄 때 직접 생성하지않고 enum을 던져주면 알아서 CarStateFactory에서 return
        carState = factory.getCarState(this, carStateID);
    }

    //각 상태에 따라 스피드업, 다운이 정해짐
    public void speedUp(int speed){
        carState.speedUp(speed);
    }

    public void speedDown(int speed){
        carState.speedDown(speed);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

enum CarStateID {
    NORMAL,
    LIMP
}

class CarStateFactory{
    private static CarStateFactory factory = new CarStateFactory();
    private Set<CarState> stateSet;
    private CarStateFactory(){
        stateSet = new HashSet<>();
    }

    public static CarStateFactory getFactory() {
        return factory;
    }

    private boolean isExist(CarState state){
        return stateSet.contains(state);
    }

    public void addCarSate(CarState state){
        if(isExist(state)){
            return;
        }

        stateSet.add(state);
    }

    public void removeCarState(CarState state){
        if(!isExist(state)){
            return;
        }

        stateSet.remove(state);
    }

    /* 각각 인스턴스를 얻어오는 과정이 다를 수 있어서 팩토리를 사용함 */
    public CarState getCarState(Car car, CarStateID stateID){
        CarState state = null;

        switch(stateID){
            case NORMAL:
                state = NormalCarState.getState(car);
                break;

            case LIMP:
                state = LimpCarState.getState(car);
                break;
        }

        return state;
    }
}

abstract class CarState{
    protected Car car;

    public CarState(Car car) {
        this.car = car;
    }

    private boolean isNegativeSpeed(int speed){
        return speed < 0;
    }

    public abstract void stateSpeedUp(int speed);
    public void speedUp(int speed){
        if(isNegativeSpeed(speed)){
            return;
        }
        stateSpeedDown(speed);
    }

    public abstract void stateSpeedDown(int speed);
    public void speedDown(int speed){
        if(isNegativeSpeed(speed)){
            return;
        }
        stateSpeedDown(speed);
    }
}

class NormalCarState extends CarState{
    private static NormalCarState state;
    private NormalCarState(Car car) {
        super(car);
    }

    public synchronized static NormalCarState getState(Car car){
        if(state == null){
            new NormalCarState(car);
        }

        return state;
    }

    @Override
    public synchronized void stateSpeedUp(int speed) {
        car.setSpeed(speed);
    }

    @Override
    public synchronized void stateSpeedDown(int speed) {
        car.setSpeed(speed);
    }


}

class LimpCarState extends CarState{
    private static LimpCarState state;
    private static final int LIMIT_SPEED = 60;
    private LimpCarState(Car car) {
        super(car);
    }

    public synchronized static LimpCarState getState(Car car){
        if(state == null){
            state = new LimpCarState(car);
        }

        return state;
    }

    private synchronized boolean isLimitSpeed(int speed){
        return speed > LIMIT_SPEED;
    }

    @Override
    public synchronized void stateSpeedUp(int speed) {
        if(isLimitSpeed(speed)){
            car.setSpeed(LIMIT_SPEED);
            return;
        }

        car.setSpeed(speed);
    }

    @Override
    public synchronized void stateSpeedDown(int speed) {
        car.setSpeed(speed);
    }

}



/*************** 연습문제 : 팩토리메서드 패턴 + 템플릿메서드 패턴 : 생성관리자 + 중복되는 부분과 각각의 부분 처리 ***************/
enum Direction{
    UP,
    DOWN
}


enum MotorStatus{
    MOVING,
    STOPPED
}

enum MotorMaker{
    HYUNDAI,
    LG
}

class MotorFactory{
    private Map<MotorMaker, Motor> motorMap = new HashMap<>();

    private boolean isExist(MotorMaker maker){
        return motorMap.containsKey(maker);
    }

    public void addMotor(MotorMaker maker, Motor motor){ //생성해두고 관리하거나 생성방식 여러가지를 여기서 정의해두거나
        if(isExist(maker)){
            return;
        }

        /*switch (maker){
            case HYUNDAI:
                //각각의 생성방식
                break;

            case LG:
                //각각의 생성방식
                break;
        }*/
        motorMap.put(maker, motor);
    }

    public void removeMotor(MotorMaker maker){
        if(!isExist(maker)){
            throw new NullPointerException("리스트에 없음");
        }
        motorMap.remove(maker);
    }

    public Motor getMotor(MotorMaker maker){
        if(!isExist(maker)){
            throw new NullPointerException("리스트에 없음");
        }
        return motorMap.get(maker);
    }
}

abstract class Motor{
    private MotorStatus motorStatus;

    public Motor() {
        this.motorStatus = MotorStatus.STOPPED;
    }

    public MotorStatus getMotorStatus() {
        return motorStatus;
    }

    private void setMotorStatus(MotorStatus motorStatus) {
        this.motorStatus = motorStatus;
    }

    private boolean isStopped(){
        return motorStatus.equals(MotorStatus.STOPPED);
    }

    public void move(Direction direction){
        if(!isStopped()){
            return;
        }

        setMotorStatus(MotorStatus.MOVING);
        moveMotor(direction);

    }

    protected abstract void moveMotor(Direction direction);


    public void stop(){
        if(isStopped()){
            return;
        }
        setMotorStatus(MotorStatus.STOPPED);
    }
}

class LGMotor extends Motor{
    private static LGMotor motor = new LGMotor();
    private LGMotor(){}

    public static LGMotor getMotor() {
        return motor;
    }

    @Override
    protected void moveMotor(Direction direction) {
        System.out.println("LG Motor : " + direction);
    }
}

class HyundaiMotor extends Motor{
    private static HyundaiMotor motor = new HyundaiMotor();
    private HyundaiMotor(){}

    public static HyundaiMotor getMotor(){
        return motor;
    }

    @Override
    protected void moveMotor(Direction direction) {
        System.out.println("Hyundai Motor : " + direction);
    }
}

class Elavator{
    private int id;
    private int floor = 1; //default
    private Motor motor;
    private ElevatorPrinter printer;

    public Elavator(int id, Motor motor, ElevatorPrinter printer) {
        this.id = id;
        this.motor = motor;
        this.printer = printer;
    }

    private boolean isInValidFloor(int floor){
        return getFloor() == floor;
    }

    private Direction getDirection(int floor){
        if(getFloor() > floor){
            return Direction.DOWN;
        }

        return Direction.UP;
    }

    public void gotoFloor(int floor){
        if(isInValidFloor(floor)){
            return;
        }

        motor.moveMotor(getDirection(floor));
        setFloor(floor);
        motor.stop();
        printer.print("[" + id + "] : " + getFloor() + "층으로 이동됨");
    }

    public int getFloor() {
        return floor;
    }

    private void setFloor(int floor) {
        this.floor = floor;
    }

    public void setPrinter(ElevatorPrinter printer) {
        this.printer = printer;
    }
}

interface ElevatorPrinter{
    void print(String message);
}

class ConsoleElevatorPrinter implements ElevatorPrinter{
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}