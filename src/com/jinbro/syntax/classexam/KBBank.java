package com.jinbro.syntax.classexam;

public class KBBank implements Bank {

    private String account_pwd;

    private int money;


    public KBBank() {
        /* 원래 기본정보가 있어야하지만 그냥 */
    }

    @Override
    public int check() {
        return money;
    }

    @Override
    public void deposit(String pwd, int amount) {

        if(!pwd.equals(account_pwd)){
            return;
        }

        money += amount;
        System.out.println("고갱님의 계좌에 " + amount + "원이 입금되었습니다");
        System.out.println("총 계좌 금액" + money + "입니다");
    }

    @Override
    public void withdraw(String pwd, int amount) {
        if(!pwd.equals(account_pwd) || money < amount){
            return ;
        }

        money -= amount;
        System.out.println("고갱님의 계좌에 " + amount + "원이 출금되었습니다");
        System.out.println("총 계좌 금액" + money + "입니다");
    }


}
