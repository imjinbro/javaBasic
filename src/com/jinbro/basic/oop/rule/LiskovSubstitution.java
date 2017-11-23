package com.jinbro.basic.oop.rule;

/*
    [리스코프 치환 원칙]
    - 확장(extends)할 때 변질시키지말고 확장시키
    - 상속(일반화관계, extends, is kind of) 관련 원칙 : 하위클래스는 상위클래스의 행위를 수행할 수 있어야함
    (1) 상위클래스 타입 변수에 하위클래스 인스턴스를 저장해도 의미 변화되지않도록 : 상위클래스 변수명 = new 하위클래스()
    (2) 상속관계의 클래스 행위 일관성 : 같은 행동을 한다!

    - 일관성있게 확장하기위해 상위클래스 메서드 오버라이딩하지않고 그대로 수행할 것을 권하고 있음
    (1) 물려받은 것도 그대로 쓰면서 확장할건 확장하자
    (2) 그런데 결과만 일관성(타입, 리턴데이터가 갖는 의미)있게하고 세부 내용은 각각에 맞게 오버라이딩하는게 좋지않을까?
 */
public class LiskovSubstitution {

}

class Bag{
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

class DiscountedBag extends Bag{
    private double discountedRate;

    public DiscountedBag(double discountedRate) {
        this.discountedRate = discountedRate;
    }

    @Override
    public void setPrice(int price) {
        super.setPrice((int)(price * discountedRate));
    }
}
