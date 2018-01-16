package com.jinbro.syntax.io.util.substream;

import java.io.*;

/*
    [보조스트림 : 버퍼를 둠으로써 입출력 횟수를 줄여 성능향상]
    - 입출력은 속도가 가장 느린 장치 속도를 따라감 : CPU, 메모리가 아무리 뛰어나도 하드디스크 입출력이 늦으면 프로그램 속도는 그 속도
    - 느릴 수도 있는 장치에 여러번 입출력하지않고 쌓아뒀다가 1번에 처리함으로서 입출력 횟수를 낮춰 속도 향상을 꿰하는 방법
    - 1000바이트 데이터 출력
    (1) 1000번에 걸쳐 1바이트씩 보내는 것
    (2) 1000바이트를 쌓아뒀다가 1번에 보내는 것 : 버퍼

    - 바이트, 문자 스트림 모두 지원
    - 예시 파일 스트림에서 최소 5배, 최대 20배까지 성능차이가 남
 */
public class BufferToImprove {
    public static void main(String[] args) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        FileOutputStream fos = null;
        BufferedOutputStream  bos = null;

        try {
            /* Buffer를 두지 않았을 때 */
            fis = new FileInputStream("/Users/jinbro/Desktop/imjinbro.github.io/assets/images/default_author_cover.jpg");
            fos = new FileOutputStream("/Users/jinbro/Desktop/ioTest/copy_default_author_cover.jpg");
            int readByte;
            long start = System.currentTimeMillis();
            while((readByte = fis.read()) != -1){
                fos.write(readByte);
            }
            System.out.println("중간 버퍼 X : " + (System.currentTimeMillis() - start) + "ms");


            /* Buffer를 두었을 때 : 외부 입력소스로부터 직접 프로그램이 읽지않고 버퍼에 저장해뒀다가 한꺼번에 읽음 */
            fis = new FileInputStream("/Users/jinbro/Desktop/imjinbro.github.io/assets/images/default_author_cover.jpg");
            fos = new FileOutputStream("/Users/jinbro/Desktop/ioTest/copy2_default_author_cover.jpg");
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);
            start = System.currentTimeMillis();

            while((readByte = bis.read()) != -1){
                bos.write(readByte);
            }
            bos.flush();
            System.out.println("중간 버퍼 O : " + (System.currentTimeMillis() - start) + "ms");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


//