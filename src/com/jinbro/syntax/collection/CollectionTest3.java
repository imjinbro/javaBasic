package com.jinbro.syntax.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CollectionTest3 {
    /* HashMap key - value 출력 어떻게 하나를 묻는 문제 */
    public static void main(String[] args) {
        Map<String, Integer> points = new HashMap<>();
        points.put("blue", 96);
        points.put("hong", 86);
        points.put("white", 92);

        Set<Map.Entry<String,Integer>> entries = points.entrySet();

        int sum = 0;
        int highPoint = 0;
        String highID = "";

        for(Map.Entry<String,Integer> entry : entries){
            int point = entry.getValue();
            sum += point;

            if(highPoint < point){
                highPoint = point;
                highID = entry.getKey();
            }
        }

        System.out.println("평균점수 : " + sum/points.size());
        System.out.println("최고점수 : " + highPoint);
        System.out.println("최고점수를 받은 아이디 : " + highID);
    }
}


