package com.jinbro.basic.oop.rule;

/*
    [인터페이스 분리 원칙]
    - 혹여나 다양한 역할을 하는 1개의 객체를 만들어야할 때 특정 기능만을 사용할 수 있는 인터페이스를 앞에둬서 제한을 둠
    - 그럼 단일책임원칙은?
    (1) 비대한 클래스가 하나 만들어지지만 인터페이스로 제한점을 둔다 : 특정 책임만 수행하도록
 */
public class InterfaceSegregation {
    public static void main(String[] args) {
        Printer printer = new AllinOne();
        printer.print();
    }
}

interface Printer {
    void print();
}

interface Scanner{
    void copy();
}


class AllinOne implements Printer, Scanner{

    @Override
    public void print() {

    }

    @Override
    public void copy() {

    }
}
