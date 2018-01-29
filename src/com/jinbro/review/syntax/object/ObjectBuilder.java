package com.jinbro.review.syntax.object;

public class ObjectBuilder {

}

class BeforeBuilderFirst{
    private int name; //필수
    private int num; //필수
    private int carNum; //선택
    private String addr; //선택

    /* 상황 대비 생성자를 여러개 만들어야함 */
    public BeforeBuilderFirst(int name, int num) {
        this.name = name;
        this.num = num;
    }

    public BeforeBuilderFirst(int name, int num, int carNum) {
        this.name = name;
        this.num = num;
        this.carNum = carNum;
    }

    public BeforeBuilderFirst(int name, int num, int carNum, String addr) {
        this.name = name;
        this.num = num;
        this.carNum = carNum;
        this.addr = addr;
    }
}

/* 자바빈 패턴 : 디폴트 생성자 -> set 메서드로 설정(변경에 취약, 그 점을 막기위한 코드가 필요) */
class BeforeBuilderSecond{
    private int name;
    private int num;
    private int carNum;
    private String addr;

    public BeforeBuilderSecond() {
    }

    public void setName(int name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
