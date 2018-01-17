package com.jinbro.review.modeling;

public class Strategy { }

interface Role{
    void pay(Customer customer);
}

class General implements Role{
    private static General role = new General();
    private General(){ }

    public static General getRole(){
        return role;
    }

    @Override
    public void pay(Customer customer) {
        /* GENERAL STRATEGY */
    }
}

class VIP implements Role{
    private static VIP role = new VIP();
    private VIP(){ }

    public static VIP getRole(){
        return role;
    }

    @Override
    public void pay(Customer customer) {
        /* VIP STRATEGY */
    }
}

class Customer{
    private Role customerRole = General.getRole(); //default role

    /* 전략 변경 */
    public void setCustomerRole(Role customerRole) {
        this.customerRole = customerRole;
    }

    /* Role이 추가되더라도 pay() 라는 인터페이스는 그대로 */
    public void buy(){
        customerRole.pay(this);
    }
}
