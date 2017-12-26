package com.jinbro.basic.oop.designPattern.behaviroal;

import java.util.ArrayList;
import java.util.List;

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


/*
    [템플릿 메서드 패턴 연습문제]
    - 리포트 생성 여러가지일 때 어떻게 할 것인가? : 공통되는 부분과 다른 부분 처리
    - 리포트 IO 여러가지 방법에 대해서는 고려하지않음
*/
class Customer{
    private String name;
    private int score;

    public Customer(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score){
        setScore(score);
    }

    private void setScore(int score) {
        this.score += score;
    }
}

class CustomerAdmin {
    public static void main(String[] args) {
        Customer customer1 = new Customer("jinhyung");
        Customer customer2 = new Customer("jinbro");

        ComplexReportGenerator complexReportGenerator = new ComplexReportGenerator();
        SimpleReportGenerator simpleReportGenerator = new SimpleReportGenerator();

        CustomerAdmin admin = CustomerAdmin.getAdmin();
        admin.addCustomer(customer1);
        admin.addCustomer(customer2);

        admin.getReportAboutCustomer(); //제너레이터를 설정하지않고 리포트를 요청할 경우 설정 메세지 출력

        System.out.println("=============");

        /* 점수 합계 리포트 */
        admin.setReportGenerator(complexReportGenerator);
        admin.getReportAboutCustomer();

        System.out.println("=============");

        /* 일반 리포트 */
        admin.setReportGenerator(simpleReportGenerator);
        admin.getReportAboutCustomer();
    }

    private ReportGenerator reportGenerator; //계속해서 변경됨
    private ArrayList<Customer> customerList = new ArrayList<>();
    private static CustomerAdmin admin = new CustomerAdmin();

    private CustomerAdmin(){}

    public static CustomerAdmin getAdmin() {
        return admin;
    }

    public void addCustomer(Customer customer){
        if(NullProcessor.isNull(customer)){
            NullProcessor.process("고객 정보가 잘못됨");
        }

        if(isExistCustomer(customer)){
            System.out.println("이미 추가된 고객입니다");
            return;
        }

        customerList.add(customer);
    }

    public void removeCustomer(Customer customer){
        if(NullProcessor.isNull(customer)){
            NullProcessor.process("고객 정보가 잘못됨");
        }

        if(!isExistCustomer(customer)){
            System.out.println("존재하지않는 고객입니다.");
            return;
        }
        customerList.remove(customer);
    }

    public void setReportGenerator(ReportGenerator reportGenerator){
        this.reportGenerator = reportGenerator;
    }

    public void getReportAboutCustomer(){
        if(NullProcessor.isNull(reportGenerator)){
            System.out.println("리포트 제너레이터를 설정해주세요");
            return;
        }

        System.out.println(reportGenerator.generate(getCustomerList()));
    }

    private ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    private boolean isExistCustomer(Customer customer){
        return customerList.contains(customer);
    }
}



abstract class ReportGenerator{
    public String generate(List<Customer> customerList){
        /* 공통된 부분 */
        StringBuilder builder = new StringBuilder();
        builder.append("고객 수 : ").append(customerList.size()).append("\n");

        for(Customer customer : customerList){
            builder.append(customer.getName())
                .append(" : ")
                .append(customer.getScore())
                .append("\n");
        }
        builder.append(specialReport(customerList));

        return builder.toString();
    }

    abstract public String specialReport(List<Customer> customerList);
}

class SimpleReportGenerator extends ReportGenerator{

    @Override
    public String specialReport(List<Customer> customerList) {
        return "";
    }
}

class ComplexReportGenerator extends ReportGenerator{

    @Override
    public String specialReport(List<Customer> customerList) {
        StringBuilder builder = new StringBuilder();
        int scoreSum = 0;
        for(Customer customer : customerList){
            scoreSum += customer.getScore();
        }
        builder.append("점수 합계 : ")
               .append(scoreSum);
        return builder.toString();
    }
}

class NullProcessor{

    public static boolean isNull(Object obj){
        return obj == null;
    }

    public static void process(String msg){
        throw new NullPointerException(msg);
    }
}



/* [템플릿메서드 연습문제2]
   - 팩토리메서드로 풀었던 문제를 템플릿메서드 방식으로 풀어봄
   - 같은 곳은 같은대로 다른 곳만 각각 구현해서 호출만 똑같이
 */

class ElevatorCtrl{
    private int id;
    private int floor;

    public ElevatorCtrl(int id) {
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
    int selectElevatorCtrl(ElevatorManager manager, int destination, Course course);
}

class ThroughputScheduler implements ElevatorScheduler{
    private static ThroughputScheduler scheduler = new ThroughputScheduler();
    private ThroughputScheduler(){}

    public static ThroughputScheduler getScheduler(){
        return scheduler;
    }

    /* 변경사항이 많을 부분 */
    @Override
    public int selectElevatorCtrl(ElevatorManager manager, int destination, Course course){ //return elevatorctrl id
        ArrayList<ElevatorCtrl> controllers = manager.getCtrlList();
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
    public int selectElevatorCtrl(ElevatorManager manager, int destination, Course course) {
        System.out.println("responseTime");
        return 0;
    }
}


abstract class ElevatorManager{
    private ArrayList<ElevatorCtrl> ctrlList = new ArrayList<>();

    public ElevatorManager(int ctrlNum){
        initCtrl(ctrlNum);
    }

    private void initCtrl(int ctrlNum){
        for(int i=0; i<ctrlNum; i++){
            ctrlList.add(new ElevatorCtrl(i));
        }
    }

    abstract public ElevatorScheduler getScheduler();

    public void requestElevator(int destination, Course course){
        ElevatorScheduler scheduler = getScheduler();
        int ctrlElevatorId = scheduler.selectElevatorCtrl(this, destination, course);

        ElevatorCtrl elevatorController = ctrlList.get(ctrlElevatorId);
        elevatorController.gotoFloor(destination);
    }

    public ArrayList<ElevatorCtrl> getCtrlList(){
        return ctrlList;
    }
}

class ElevatorManagerWithThroughScheduling extends ElevatorManager {
    public ElevatorManagerWithThroughScheduling(int ctrlNum) {
        super(ctrlNum);
    }

    @Override
    public ElevatorScheduler getScheduler() {
        /* 자기만의 스케쥴러 */
        ElevatorScheduler scheduler = ThroughputScheduler.getScheduler();
        return scheduler;
    }
}

class ElevatorManagerWithResponseTimeScheduling extends ElevatorManager {
    public ElevatorManagerWithResponseTimeScheduling(int ctrlNum) {
        super(ctrlNum);
    }

    @Override
    public ElevatorScheduler getScheduler() {
        ElevatorScheduler scheduler = ResponseTimeScheduler.getScheduler();
        return scheduler;
    }
}

enum Course{
    UP,
    DOWN
}
