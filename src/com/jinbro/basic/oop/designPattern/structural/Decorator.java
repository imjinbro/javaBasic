package com.jinbro.basic.oop.designPattern.structural;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
    [문제상황]
    - 사용하려고 하는 기능이 기본 기능만 있는게 아니라 추가기능이 있고 더 늘어나거나 줄어들 수 있는 상황
    (1) 기능을 사용하는 쪽(클라이언트)에서는 일관된 코드로 기능만 변경하고싶음 : 기능을 일반화시킴(abstract, interface)
    (2) 기본 + 추가 기능 대비해서 extends 관계 만들었음

    - 추가기능이 늘어날 때마다 기본 + 추가기능1 + 추가기능2 처럼 조합해서 만들어야하는 클래스들이 늘어남
    (1) 데커레이터 패턴 : 기본 기능은 기본 기능으로 쓰면서 기능은 확장하는 패턴


    [해결 - 데커레이터 패턴] : 조합 클래스는 만들지않지만 조합을 만들거나 없앨 수 있음
    (1) 기능을 상속하는 추가기능 클래스(일반화)를 만듬(확장)
    (2) 기능 호출 메서드를 구현함
    - 기능 클래스를 상속한 기본 기능 메서드 호출

    (3) 추가기능 클래스를 상속하는 OOO 추가기능 클래스를 만듬
    - 추가기능 클래스(상위) 기능 메서드 호출(super) + 추가 기능 클래스만의 추가기능 메서드 호출


    [결론]
    - 변경 가능성이 높은 것을 일반화(상속관계 만들기)시키되 가지치기를 많이할 것 같으면 데커레이터 패턴 적용해서 디자인하기
 */
public class Decorator { }

class Client{
    public static void main(String[] args) {
        //기본기능만
        Ability basic = new BasicAbility();
        basic.doIt();

        //기본기능 + 추가기능1
        Ability extend1 = new FirstAdditionalAbility(basic);
        extend1.doIt();

        //기본기능 + 추가기능2
        Ability extend2 = new SecondAdditionalAbility(basic);
        extend2.doIt();

        //기본 + 추가1 + 추가2 : 조합, 조합 클래스 만들지않아도
        Ability extend3 = new SecondAdditionalAbility(extend1);
        extend3.doIt();

        //동적으로 기능 없애기
        extend3 = new SecondAdditionalAbility(basic);
        extend3.doIt();
    }
}

abstract class Ability{
    abstract void doIt();
}

class BasicAbility extends Ability{

    @Override
    public void doIt() {
        System.out.println("기본적으로 해야할 것(기본기능)");
    }
}

abstract class AdditionalAbility extends Ability{
    private Ability ability;

    public AdditionalAbility(Ability ability) {
        this.ability = ability;
    }

    @Override
    public void doIt() {
        /* 기본기능 호출 */
        ability.doIt();
    }
}

class FirstAdditionalAbility extends AdditionalAbility{

    public FirstAdditionalAbility(Ability ability) {
        super(ability);
    }

    @Override
    public void doIt() {
        super.doIt();
        /* 추가 기능 */
        todoExtends();
    }

    private void todoExtends(){
        System.out.println("확장기능1");
    }
}

class SecondAdditionalAbility extends AdditionalAbility{

    public SecondAdditionalAbility(Ability ability) {
        super(ability);
    }

    @Override
    public void doIt() {
        super.doIt();
        /* 추가 기능 */
        todoExtends();
    }

    private void todoExtends(){
        System.out.println("확장기능2");
    }
}


/* 콘텐츠 프로세서 사용예시 : 중첩시켜서 여러개의 기능을 동적으로 달거나 없앨 수 있음 */
class ContentClient{
    public static void main(String[] args) {
        Article article = new Article("안녕하세요");
        Map<String, String> strMap = new HashMap<>();

        //기본
        BasicContent basicContent = new BasicContent();
        strMap.put("basic", basicContent.process(article));

        //기본형 + 추가형(시큐어)
        SecureContent secureContent = new SecureContent(basicContent);
        strMap.put("basic_secure", secureContent.process(article));

        //기본형 + 추가형(시큐어 + 더블)
        DoublyContent doublyContent = new DoublyContent(secureContent);
        strMap.put("basic_secure_doubly", doublyContent.process(article));


        Set<Map.Entry<String, String>> entrySet = strMap.entrySet();
        for(Map.Entry<String, String> entry : entrySet){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* 콘텐츠 */
class Article{
    private String content;

    public Article(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

/* 콘텐츠 처리기 */
abstract class ContentProcessor{
    abstract public String process(Article article);
}

class BasicContent extends ContentProcessor{

    @Override
    public String process(Article article) {
        return article.getContent();
    }
}

abstract class ExternalContent extends ContentProcessor{
    private ContentProcessor processor;

    public ExternalContent(ContentProcessor processor) {
        this.processor = processor;
    }

    @Override
    public String process(Article article) {
        return processor.process(article);
    }
}

class SecureContent extends ExternalContent{

    public SecureContent(ContentProcessor processor) {
        super(processor);
    }

    @Override
    public String process(Article article) {
        return encrypt(super.process(article));
    }

    private String encrypt(String content){
        return content + " encrypted";
    }
}

class DoublyContent extends ExternalContent{

    public DoublyContent(ContentProcessor processor) {
        super(processor);
    }

    @Override
    public String process(Article article) {
        return doubly(super.process(article));
    }

    private String doubly(String content){
        return content + " " + content;
    }
}

/* 차 + 컴포넌트 예시 : 여러 조합을 조합 클래스 별도로 만들 필요없음(Car 코드 변경할 필요도 없음 - 컴포넌트 묶어주기때문에) */
class CarClient{
    public static void main(String[] args) {
        System.out.println("==================");
        System.out.println("기본 옵션으로 살게요");
        BasicCarComponent optionBasic = new BasicCarComponent();
        Car myCar = new Car(optionBasic);
        myCar.getInfo();
        myCar.getPrice();

        System.out.println("==================");
        System.out.println("아! 네비 업그레이드 해주세요");
        Navi optionNavi = new Navi(optionBasic);
        myCar = new Car(optionNavi);
        myCar.getInfo();
        myCar.getPrice();

        System.out.println("==================");
        System.out.println("아 죄송한데! 에어백도 추가할게요");
        AirBag optionAirbagNavi = new AirBag(optionNavi);
        myCar = new Car(optionAirbagNavi);
        myCar.getInfo();
        myCar.getPrice();


        System.out.println("==================");
        System.out.println("아 진짜 죄송한데! 네비는 없어도 될 것 같아요");
        AirBag optionAirbag = new AirBag(optionBasic);
        myCar = new Car(optionAirbag);
        myCar.getInfo();
        myCar.getPrice();
    }
}

/* 차 */
class Car{
    private CarComponent component;

    public Car(CarComponent component) {
        this.component = component;
    }

    public void getInfo(){
        System.out.println(component.getInfo()); // 차 자체 정보도 있어야겠지만 우선
    }

    public void getPrice(){
        System.out.println(component.getPrice()); // 차 자체 값도 있어야겠지만 우선
    }
}

/* 차 옵션 */
abstract class CarComponent{
    abstract public String getInfo();
    abstract public int getPrice();
}

class BasicCarComponent extends CarComponent{

    @Override
    public String getInfo() {
        return "기본 세트";
    }

    @Override
    public int getPrice() {
        return 100000;
    }
}

class AdditionalCarComponent extends CarComponent{
    private CarComponent carComponent;

    public AdditionalCarComponent(CarComponent carComponent) {
        this.carComponent = carComponent;
    }

    @Override
    public String getInfo() {
        return carComponent.getInfo();
    }

    @Override
    public int getPrice() {
        return carComponent.getPrice();
    }
}

class AirBag extends AdditionalCarComponent{

    public AirBag(CarComponent carComponent) {
        super(carComponent);
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", " + getAirBagInfo();
    }

    public String getAirBagInfo(){
        return "에어백 : 에붸붸뷉";
    }

    @Override
    public int getPrice() {
        return super.getPrice() + getAirBagPrice();
    }

    public int getAirBagPrice(){
        return 200000;
    }
}

class Navi extends AdditionalCarComponent{

    public Navi(CarComponent carComponent) {
        super(carComponent);
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", " + getNaviInfo();
    }

    public String getNaviInfo(){
        return "네비게이션 : 쏼라쏼라";
    }

    @Override
    public int getPrice() {
        return super.getPrice() + getNaviPrice();
    }

    public int getNaviPrice(){
        return 300000;
    }
}

