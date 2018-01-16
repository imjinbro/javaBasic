package com.jinbro.syntax.stdapi.string;

import java.util.StringTokenizer;

public class StringTokenizerTest {

    public static void main(String[] args) {

        String target = "가, 나, 다, 라, 마, 바";
        StringTokenizer st = new StringTokenizer(target, ", ", false);

        /* countTokens, hasMoreTokens
           1) 델리미터에 의해 끊어진 문자열을 얻어올 떄 체크
           2) 무작정 꺼내오기부터한다면(nextToken) NoSuchElementException 발생 위험
        */
        int cnt = st.countTokens();
        System.out.println(cnt);

        while(st.hasMoreTokens()){
            System.out.println(st.nextToken());
        }
    }
}
