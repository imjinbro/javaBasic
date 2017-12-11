package com.jinbro.basic.oop.designPattern.behaviroal;
/*
    [기존코드 문제점]
    - 프린트 형태가 바뀌거나 내용이 바뀐다면 새로운 클래스를 만들거나 기존 클래스를 엎고 다시 만들어야함

    [해결점 1] : 출력 대상이 1개일 때 - 스트래티지패턴
    (1) 프린트 상위 개념을 만듬(일반화시키기) : abstract, interface
    - 상위개념을 상속해서 다양한 방식, Out 대상들을 만들어냄
    - ScoreRecord 입장에서는 프린트를 사용하는 것이므로 상위개념 타입 변수로 선언해두고 set으로 바꿔 끼우면서 사용하면됨

    [해결점 2] : 여러 대상일 때 한꺼번에 - 옵저버 패턴(변경되었을 때 모든 대상에 통보하는 방식)
    (1) 여러 대상을 일반화 : 스트래티지 패턴과 같음
    - 여러 대상이 알림을 받고 최신 정보로 업데이트해야함 : update()

    (2) 여러 대상을 관리할 수 있는 클래스 필요

    [결론]
    - 옵저버 패턴은 하나의 클래스 변화에 여러 클래스가 관심을 가질 때 적용
    - 프린트 방식이 바뀌더라도(추가,수정,삭제) ScoreRecord 코드 변경이 되어서는 안됨
    - 개별 클래스 직접 참조 : 그 부분이 바뀌거나 변경해야할 필요성이 생기면 변경점이 많아짐
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ScoreRecordClient{
    public static void main(String[] args) {
        ScoreRecord record = new ScoreRecord();
        DataSheetView dataSheetView = new DataSheetView(record, 2);
        MinMaxView minMaxView = new MinMaxView(record);
        StaticsView averageView = new StaticsView(record);

        record.attach(dataSheetView);
        record.attach(minMaxView);
        record.attach(averageView);

        int score = 10;
        for(int i=1; i<=5; i++) {
            record.addScore(score * i);
        }
    }
}

class Subject{ //옵저버 관리 클래스
    List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void detach(Observer observer){
        if(observers.contains(observer)){
            observers.remove(observer);
        }
    }

    public void notifyObserver(){
        for(Observer observer : observers){
            observer.update();
        }
    }
}

class ScoreRecord extends Subject{ // 관리 + 확장기능(스코어 기록)
    private List<Integer> scores = new ArrayList<>();

    public void addScore(int score){
        //점수가 추가될 때마다 attach 되어있는 옵저버 update 호출
        scores.add(score);
        notifyObserver();
    }

    public List<Integer> getScores() {
        return scores;
    }
}

public interface Observer{
    void update();
}

class DataSheetView implements Observer{
    private ScoreRecord scoreRecord;
    private int displayLimit;

    public DataSheetView(ScoreRecord scoreRecord, int displayLimit) {
        this.scoreRecord = scoreRecord;
        this.displayLimit = displayLimit;
    }

    @Override
    public void update() {
        displayDataSheet(scoreRecord.getScores(), displayLimit);
    }

    private void displayDataSheet(List<Integer> scores, int displayLimit){
        for(int i=0; i<displayLimit && i < scores.size(); i++){
            System.out.println((i+1)+"번째 기록 : " + scores.get(i));
        }
    }
}

class MinMaxView implements Observer{
    private ScoreRecord scoreRecord;
    public MinMaxView(ScoreRecord scoreRecord) {
        this.scoreRecord = scoreRecord;
    }

    @Override
    public void update() {
        displayMinMax(scoreRecord.getScores());
    }

    private void displayMinMax(List<Integer> scores){
        int min = Collections.min(scores, null);
        int max = Collections.max(scores, null);
        System.out.println("min : " + min + ", " + "max : " + max);
    }
}

class StaticsView implements Observer{
    private ScoreRecord scoreRecord;

    public StaticsView(ScoreRecord scoreRecord) {
        this.scoreRecord = scoreRecord;
    }

    @Override
    public void update() {
        displayAverage(scoreRecord.getScores());
    }

    private void displayAverage(List<Integer> scores){
        int size = scores.size();
        int sum = 0;
        for(int score : scores){
            sum += score;
        }
        System.out.println("평균 점수 : " + (sum/size));
    }
}



/*
[기존 코드]
- 문제점 : 출력 내용이 바뀌거나 Out 대상이 바뀔 경우에 이렇게 다시 클래스를 만들고 ScoreRecord 코드 변경까지해야함

class RecordClient{
    public static void main(String[] args) {
        ScoreRecord record = new ScoreRecord();
        DataSheetView view = new DataSheetView(record, 10);
        record.setDataSheetView(view);

        int target = 10;
        for(int i=1; i<=5; i++){
            record.addScore(target*i);
        }
    }
}

class ScoreRecord{
    private List<Integer> scores = new ArrayList<>();
    private DataSheetView dataSheetView;
    private DataSheetMaxView dataSheetMaxView;

    public void setDataSheetView(DataSheetView dataSheetView) {
        this.dataSheetView = dataSheetView;
    }

    public void addScore(int score){
        scores.add(score);
        dataSheetView.update();


    }

    public List<Integer> getScores() {
        return scores;
    }
}

class DataSheetView{
    private ScoreRecord scoreRecord;
    private int viewCnt;

    public DataSheetView(ScoreRecord scoreRecord, int viewCnt) {
        this.scoreRecord = scoreRecord;
        this.viewCnt = viewCnt;
    }

    public void update(){
        List<Integer> record = scoreRecord.getScores();
        displayRecord(record, viewCnt); //viewCnt번 만큼 점수표를 출력
    }

    public void displayRecord(List<Integer> record, int viewCnt){
        System.out.print("List of " + viewCnt + " entries: ");
        for(int i=0; i<viewCnt && i<record.size(); i++){
            System.out.println(record.get(i));
        }
        System.out.println();
    }
}

class DataSheetMaxView{
    private ScoreRecord scoreRecord;

    public DataSheetMaxView(ScoreRecord scoreRecord) {
        this.scoreRecord = scoreRecord;
    }

    public void update(){
        List<Integer> record = scoreRecord.getScores();
        displayMaxRecord(record;
    }

    public void displayMaxRecord(List<Integer> record){
        System.out.print("List of MAX entry: ");
        int max = Collections.max(record);
        System.out.println(max);
        System.out.println();
    }
}
*/
