package com.jinbro.basic.tdd;

import junit.framework.TestCase;

public class TestPrinter extends TestCase{
    public void testPrint(){
        FakePrinter printer = new FakePrinter();

        TestPrinter test = new TestPrinter();
        test.doSomething(printer);

        assertEquals("Test", printer.getMsg());
    }

    public void doSomething(Printer printer){
        String msg = "Test";
        printer.print(msg);
    }
}

interface Printer{
    void print(String msg);
}

class RealPrinter implements Printer{
    private static RealPrinter printer = new RealPrinter();
    private RealPrinter() { }

    public static RealPrinter getPrinter(){
        return printer;
    }

    @Override
    public synchronized void print(String msg) {
        /* 실제로 프린트하는 과정 : I/O */
    }
}

class FakePrinter implements Printer{
    private String msg;

    @Override
    public void print(String msg) {
        /* 데이터 전달 테스트 : 테스트 코드 내부는 확인하는 부분에 초점을 맞추면 됨 */
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
