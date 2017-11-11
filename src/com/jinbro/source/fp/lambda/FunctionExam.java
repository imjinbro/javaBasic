package com.jinbro.source.fp.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/*
    [타입 변화에 따라 미리 만들어져있는 인터페이스있음]
    - BiFunction<T,U,R> : T와 U를 R로
    - DoubleFunction<R> : double을 R로
    - IntFunction
    - IntToDoubleFunction : int를 double로
    .
    .
    .

    [FP]
    1) 함수형인터페이스 만듬
    2) 인터페이스의 메서드 정의 : 람다식
    3) 메서드를 사용하려고 하는데에서 인터페이스타입변수.메서드명() 호출 끝
 */

/* T 타입 -> R타입 변환, 메서드 표준화 */
public class FunctionExam {
    public static void main(String[] args) {

        List<Clothes> list = Arrays.asList(new Clothes(13), new Clothes(5), new Clothes(8));
        List<DiscountedClothes> dcList = match(list, clothes -> new DiscountedClothes((int)(clothes.getPrice() * 0.8))); // T -> R 정보
        System.out.println(dcList);


        /*
            [단순하게 두 파라미터로 출력만 해준다면 Math::max 로 추상화할 수 있음]
            1) 단 파라미터 개수가 똑같아야함
            2) static 메서드를 사용할 경우에는 클래스::메서드명
            3) instance 메서드를 사용하는 경우에는 인스턴스생성하고 인스턴스변수명::메서드
            4) 클래스::new 하면 생성자도 사용가능 - lazy Evaluation으로 생성자 호출해도되고
            - 함수형인터페이스<T, T> : 타입 개수와 타입을 보고 추론해서 생성자를 호출함
            - 디폴트 생성자만 호출하지않음
        */
        BiConsumer<Integer, Integer> biConsumer = Math::max; //메서드 정의
        biConsumer.accept(1,2); //메서드 실제 호출

        BiConsumer<Integer, Integer> biConsumer1 = (x,y) -> System.out.println(Math.max(x,y));
        biConsumer1.accept(1, 5);



        Member[] members = {
                new Member(90, 96),
                new Member(95, 93),
        };

        //function.apply를 람다식 대체한다고 생각하면 쉬움 : (member) : apply(member), -> Math::getMathScore : apply 구현부
        double englishAvg = sum(members, member -> member.getEnglishScore());
        System.out.println(englishAvg);

        double mathaAvg = sum(members, Member::getMathScore);
        System.out.println(mathaAvg);
    }

    static <T,R> List<R> match(List<T> list, Function<T, R> func){
        //<T,R> : match(list, clothes -> new DiscountedClothes((int)(clothes.getPrice() * 0.8))) 여기서 알 수 있음
        //list : T /  -> new DiscountedClothes : R
        List<R> result = new ArrayList<>();

        for(final T t : list) {
            //func.apply부분은 람다식으로 정의된 것으로 대체됨
            result.add(func.apply(t));
        }

        return result;
    }

    static <T> Double sum(T[] data, Function<T, Integer> function){
        //Function<T,R> 타입파라미터 2개가 필요하지만 1개는 이미 확정되어있으니깐 1개만 받아도 상관없음
        int sum = 0;
        int length = data.length;

        for(T t : data){
            sum += function.apply(t);
        }

        return (double)sum/length;
    }
}

class Member {
    private int englishScore;
    private int mathScore;

    public Member() {
    }

    public Member(int englishScore, int mathScore) {
        this.englishScore = englishScore;
        this.mathScore = mathScore;
    }

    public int getEnglishScore() {
        return englishScore;
    }

    public void setEnglishScore(int englishScore) {
        this.englishScore = englishScore;
    }

    public int getMathScore() {
        return mathScore;
    }

    public void setMathScore(int mathScore) {
        this.mathScore = mathScore;
    }
}

class Clothes {

    private int price;

    public Clothes() {
    }

    public Clothes(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Clothes{}";
    }
}

class DiscountedClothes extends Clothes{

    public DiscountedClothes() {
    }

    public DiscountedClothes(int price) {
        super(price);
    }

    @Override
    public String toString() {
        return "DiscountedClothes{}";
    }
}
