package com.jinbro.syntax.extendexam;

public class Account {

    /* private field */
    private String account_num;
    private int money;
    /* public field */


    public Account() {
        this.account_num = "123412313123";
        this.money = 0;
    }

    public String getAccount_num() {
        return account_num;
    }

    public int getMoney() {
        return money;
    }

    protected void setMoney(int money) {
        this.money = money;
    }
}
