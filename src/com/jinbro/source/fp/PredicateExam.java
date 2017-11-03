package com.jinbro.source.fp;

import java.util.function.Predicate;

public class PredicateExam {
    public static void main(String[] args) {
        Predicate<Integer> predicate1 = data -> true;
        Predicate<Integer> predicate2 = data -> true;

        predicate1.and(predicate2);


        /*
            [두개의 Predicate 메서드를 만들어서 적용해서 최종적인 결과를 얻고싶다면]
            and() : 2가지 다 모두 true의 경우 최종적으로 true
            or() : 둘 중 하나가 true
            negate() : true라면 false, false라면 true

         */
    }


}


