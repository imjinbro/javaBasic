package com.jinbro.basic.object.oop.modeling;

/*
    [집합관계]
    - 전체와 부분 관계

    - 두가지가 있음
    (1) 혼자서는 인스턴스 생성x 부품을 모으는 전체부분이 생성될 때 내부적으로 생성, 전체가 사라지면 모두 안쓰도록
    (2) 전체 객체가 사라져도 객체가 사라지지않는 방법
 */
public class Gathering {

}

class Computer{
    private MainBoard mb;
    private Cpu cpu;
    private Memory memory;
    private PowerSupply power;

    /* (1) */
    public Computer() {
        mb = new MainBoard();
        cpu = new Cpu();
        memory = new Memory();
        power = new PowerSupply();
    }

    /* (2) */
    public Computer(MainBoard mb, Cpu cpu, Memory memory, PowerSupply power) {
        this.mb = mb;
        this.cpu = cpu;
        this.memory = memory;
        this.power = power;
    }
}
class MainBoard{ }
class Cpu{}
class Memory{}
class PowerSupply{}
