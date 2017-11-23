package com.jinbro.basic.oop.rule;

/*
    [의존역전원칙]
    - 배경이 뭐야?
    (1) 객체지향은 객체와 객체 사이에 협력으로 기능을 완성해나가는 것 : 그러다보면 객체 내부 코드에서 다른 객체 협력 요청을 하게됨
    (2) 내부에서 협력 요청하게되는 것을 의존이라함 : 의존역전원칙은 이런 의존을 모델링 할 때에도 막 하지말고 원칙을 가지고 하자는 것

    - 결론
    (1) 자주 변하거나 개별적인 것에 의존하는 모델링하지마 : 상위 개념(abstract, interface)에 의존해
    (2) 변경에 유연한 시스템을 만들자
 */

public class DependencyInversion {
    public static void main(String[] args) {
        Toy robot = new Robot();
        Kid jinbro = new Kid();

        jinbro.setMyToy(robot);
    }
}

class Kid{
    private Toy myToy;

    public void setMyToy(Toy myToy) {
        this.myToy = myToy;
    }
}

abstract class Toy{

}

class Robot extends Toy{

}

class RCCar extends Toy {

}


