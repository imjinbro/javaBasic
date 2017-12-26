package com.jinbro.basic.oop.designPattern.creational;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

enum Direction{
    UP,
    DOWN
}
