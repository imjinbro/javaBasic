package com.jinbro.basic.oop.designPattern.behaviroal;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/*
    [스테이트 패턴 - 책임 또는 알고리즘 분배 : 변경에 유연하게]
    - 객체는 행동에 따라 상태가 변하고, 상태에 따라 행동도 변함
    (1) 상태는 객체의 필드로 표현됨
    (2) 행동할 때 상태를 체크해야함 : if ~ else, switch 등 분기문 사용이 많음
    (3) 새로운 상태가 추가되거나 상태가 수정, 변경되었을 때 : 상태 변화를 줄 수 있는 메서드 모두가 수정됨(분기문, 내용 모두)
    (4) 번잡한 코드 완성

    - 스테이트 패턴 : 변경가능성이 많은 상태값을 클래스로 분리, 상태 변경, 상태 관련 처리는 클래스 전담
    (1) 변경이 적은 abstract, interface 로 정의된 개념에 의존
    (2) 변경 가능성이 높은 것은 따로 분리해서 캡슐화 : 사용하지만 내부 동작은 거기서 알아서

    - 결론 : 변경 가능성이 높은 것은 모듈로 개발(OCP)
    (1) 상태에 따라 처리하는 것을 if~else로 구현해두면 코드가 지저분해지니깐 따로 분리
    (2) 상태별로 따로 구현해두고 상위 개념 연관 구현 : 변경에도 유연하고 context 코드도 깔끔해짐
    (3) 상태에 따라 어떻게 처리
 */
public class State {
    public static void main(String[] args) {
        /* state 이외 변경해야할 점 : 콘솔기반 IO 가능하도록, 유저 추가해서 운영하는 것 */
        DVDRoom dvdRoom = new DVDRoom("지뇽이 dvd 대여가게");
        dvdRoom.addDVD("나홀로집에");
        dvdRoom.addDVD("꾼");
        dvdRoom.addDVD("범죄도시");

        dvdRoom.addDVD("나홀로집에");
        dvdRoom.lendDVD("나홀로집에");
        dvdRoom.lendDVD("나홀로집에");
        dvdRoom.reserveDVD("나홀로집에");
        dvdRoom.lendDVD("나홀로집에");
        dvdRoom.returnDVD("나홀로집에");
        dvdRoom.reserveDVD("나홀로집에");
    }
}

class DVDRoom{
    private String storeName;
    private Map<String, DVD> dvdList;

    public DVDRoom(String storeName) {
        this.storeName = storeName;
        dvdList = new HashMap<>(); //같은 이름 중복 불가, 개수까지는 안함
    }

    public void addDVD(String dvdName){
        if(isExistDVD(dvdName)){
            System.out.println("관리 리스트에 존재하는 DVD입니다");
        } else {
            dvdList.put(dvdName, new DVD(dvdName));
            System.out.println("관리 리스트에 해당 DVD가 추가되었습니다 : " + dvdName);
        }
    }

    public void subtractDVD(String dvdName){
        if(isExistDVD(dvdName)){
            dvdList.remove(dvdName);
            System.out.println("관리 리스트에서 해당 DVD를 삭제하였습니다");
        } else{
            System.out.println("관리 리스트에 존재하지않는 DVD입니다");
        }
    }

    public void lendDVD(String dvdName){
        DVD dvd = searchDVD(dvdName);
        if(dvd != null){
            dvd.lend();
        }
    }

    public void returnDVD(String dvdName){
        DVD dvd = searchDVD(dvdName);
        if(dvd != null){
            dvd.giveBack();
        }
    }

    public void reserveDVD(String dvdName){
        DVD dvd = searchDVD(dvdName);
        if(dvd != null){
            dvd.reserve();
        }
    }

    public boolean isExistDVD(String dvdName){
        boolean isExist = false;
        if(dvdList.containsKey(dvdName)){
            isExist = true;
        }
        return isExist;
    }

    public DVD searchDVD(String dvdName){
        DVD dvd = null;
        if(dvdList.containsKey(dvdName)){
            dvd = dvdList.get(dvdName);
        }
        return dvd;
    }
}

class DVD{
    private String name;
    private DVDState state;

    public DVD(String name) {
        this.name = name;
        this.state = AvailableDVD.getInstance();
    }

    public void lend(){
        state.lend(this);
    }

    public void giveBack(){
        state.giveBack(this);
    }

    public void reserve(){
        state.reserve(this);
    }

    public void setState(DVDState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }
}

interface DVDState{
    void lend(DVD dvd);
    void giveBack(DVD dvd);
    void reserve(DVD dvd);
}

class AvailableDVD implements DVDState{
    private static AvailableDVD availableDVD = new AvailableDVD();

    public static AvailableDVD getInstance() {
        return availableDVD;
    }

    @Override
    public void lend(DVD dvd) {
        dvd.setState(LendDVD.getInstance());
        System.out.println("대여되었습니다");
    }

    @Override
    public void giveBack(DVD dvd) {
        System.out.println("대여 가능");
    }

    @Override
    public void reserve(DVD dvd) {
        System.out.println("바로 대여 가능(예약불가)");
    }
}

class LendDVD implements DVDState{
    private static LendDVD lendDVD = new LendDVD();

    public static LendDVD getInstance(){
        return lendDVD;
    }

    @Override
    public void lend(DVD dvd) {
        System.out.println("대여중입니다(예약가능)");
    }

    @Override
    public void giveBack(DVD dvd) {
        dvd.setState(AvailableDVD.getInstance());
        System.out.printf("반납되었습니다");
    }

    @Override
    public void reserve(DVD dvd) {
        dvd.setState(ReservedDVD.getInstance());
        System.out.println("예약되었습니다");
    }
}

class ReservedDVD implements DVDState{
    private static ReservedDVD reserveDVD = new ReservedDVD();

    public static ReservedDVD getInstance(){
        return reserveDVD;
    }

    @Override
    public void lend(DVD dvd) {
        System.out.println("대여 중 - 예약대기자 있음");
    }

    @Override
    public void giveBack(DVD dvd) {
        dvd.setState(KeepDVD.getInstance());
        System.out.println("예약자님에게 연락해야함");
    }

    @Override
    public void reserve(DVD dvd) {
        System.out.println("이미 예약되어있음");
    }
}

/* 대여 중이고, 예약 중인 도서가 반납되면 예약자에게 반납되기위해서 3일동안 킵 됨 */
class KeepDVD implements DVDState{
    private static KeepDVD keepDVD = new KeepDVD();

    private static final int EXPIRE = 3;
    private int startDay;

    public static KeepDVD getInstance(){
        keepDVD.setStartDay(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        return keepDVD;
    }

    private void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    private int getStartDay() {
        return startDay;
    }

    @Override
    public void lend(DVD dvd) {
        int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if(today-startDay > 3){
            dvd.setState(AvailableDVD.getInstance());
            dvd.lend();
        } else{
            /* 원래는 이용자 이름까지 받아서 비교해보고 빌려줘야함 */
            dvd.setState(LendDVD.getInstance());
            System.out.println("예약자님에게 대여되었습니다");
        }
    }

    @Override
    public void giveBack(DVD dvd) { }

    @Override
    public void reserve(DVD dvd) {
        System.out.println("예약자 대여 대기중 : 대기까지 " +  ((EXPIRE + startDay) - startDay) + "일 남음(기간 내 대여하지않으면 빌릴 수 있음)");
    }
}



class Light{
    /*private int state;
    private static final int ON = 0;
    private static final int OFF = 1;
    private static final int SLEEPING = 2;  상태값의 추가 -> 상태 변화에 영향을 주는 메서드 모두 수정*/

    private LightState state;

    public Light() {
        //state = OFF;
        state = OFF.getOff();
    }

    public void setState(LightState state){
        this.state = state;
    }

    public void btnOnPush(){
        state.btnOnPush(this);
    }

    public void btnSleepPush(){
        state.btnSlipPush(this);
    }

    public void btnOffPush(){
        state.btnOffPush(this);
    }

    /*public void btnOnPush(){
        if(state == ON) {
            System.out.println("이미 켜져있음");
        } else if(state == SLEEPING) {

        } else {
            state = ON;
            System.out.println("켜짐");
        }
    }

    public void btnOffPush(){
        if(state == OFF) {
            System.out.println("이미 꺼져있음");
        } else {
            state = OFF;
            System.out.println("꺼짐");
        }
    }

    public void btnSleepPush(){

    }*/
}

interface LightState{
    public void btnOnPush(Light light);
    public void btnSlipPush(Light light);
    public void btnOffPush(Light light);
}

class ON implements LightState{
    /* 켜질 때마다 새로운 ON 인스턴스 생성 막기 */
    private static ON on = new ON();
    private ON() { }

    public static ON getOn() {
        return on;
    }

    @Override
    public void btnOnPush(Light light) {
        System.out.println("반응없음");
    }

    @Override
    public void btnSlipPush(Light light) {
        light.setState(SLEEP.getSleep());
        System.out.println("불 취침모드");
    }

    @Override
    public void btnOffPush(Light light) {
        light.setState(OFF.getOff());
        System.out.println("불 꺼짐");
    }
}

class OFF implements LightState{

    private static OFF off = new OFF();
    private OFF() { }

    public static OFF getOff() {
        return off;
    }

    @Override
    public void btnOnPush(Light light) {
        light.setState(ON.getOn());
        System.out.println("불 켜짐");
    }

    @Override
    public void btnSlipPush(Light light) {
        light.setState(SLEEP.getSleep());
        System.out.println("불 취침모드");
    }

    @Override
    public void btnOffPush(Light light) {
        System.out.println("반응없음");
    }
}

class SLEEP implements LightState{

    private static SLEEP sleep = new SLEEP();
    private SLEEP() { }

    public static SLEEP getSleep() {
        return sleep;
    }

    @Override
    public void btnOnPush(Light light) {
        light.setState(ON.getOn());
        System.out.println("불 밝게 켜짐");
    }

    @Override
    public void btnSlipPush(Light light) {
        System.out.println("반응없음");
    }

    @Override
    public void btnOffPush(Light light) {
        light.setState(OFF.getOff());
        System.out.println("불 꺼짐");
    }
}

class Client {
    public static void main(String[] args) {
        Light light = new Light();
        light.btnOffPush();
        light.btnOnPush();
        light.btnSleepPush();
        light.btnOffPush();
    }
}
