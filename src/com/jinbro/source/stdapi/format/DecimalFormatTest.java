package com.jinbro.source.stdapi.format;

import java.text.DecimalFormat;

public class DecimalFormatTest {
    public static void main(String[] args) {
        /*
            - 다양한 숫자 관련 포맷
            1) 통화 기호 : 유니코드 + 단위 표현
        */
        double num = 12345.67;
        DecimalFormat df = null;
        String[] pattern = {
                "0", "0.0", "#,###",
                "\u00A4 #,###"
        };


        for(int i=0; i<pattern.length; i++){
            df = new DecimalFormat(pattern[i]);
            System.out.println(df.format(num));
        }
    }
}
