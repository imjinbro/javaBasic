package com.jinbro.syntax.io.fileIO;

import java.io.*;

/*
    [File 클래스 + byte 단위 스트림 사용]
    1) 파일 정보 가져오기, 생성, 삭제 등 : File 클래스
    2) byte 단위 입출력 : 스트림 - FileInputStream, FileOutputStream
    - 바이트 단위 : 이미지, 오디오, 텍스트 파일 등 모든 종류의 파일
    - 한글을 포함한 유니코드(2byte)문자는 byte[]로 처리해야함 : 기본 InputStream.read()는 1byte씩 읽어오기떄문에
 */
public class FileByteIO {
    public static void main(String[] args) {
        /* File 클래스 */
        File file = new File("/Users/jinbro/Desktop/ioTest/input.txt");
        if(file.exists()){
            System.out.println("포함된 디렉토리 : " + file.getParent());
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //특정 파일말고 디렉토리 지정도 가능함
        file = new File("/Users/jinbro/Desktop/ioTest");
        if(file.isDirectory()){
            File[] fileList = file.listFiles();

            System.out.println("====== [ "+ file.getPath() + " 포함 파일(" + fileList.length +") ] ======");
            for(File f : fileList){
                System.out.println(f.getName());
            }
            System.out.println("====================================================");

        }


        /* File Stream : byte 타입 입출력 */
        FileInputStream fis = null;
        FileOutputStream fos = null;

        File inputFile = new File("/Users/jinbro/Desktop/ioTest/input.txt");
        File outputFile = new File("/Users/jinbro/Desktop/ioTest/output.txt");

        try {
            fis = new FileInputStream(inputFile);
            //fos = new FileOutputStream(outputFile, true); 두번째 인자 : 기존 내용에 추가 여부 boolean
            fos = new FileOutputStream(outputFile);

            int readByteNo;
            byte[] data = new byte[100];

            while((readByteNo = fis.read(data)) != -1){
                System.out.write(data); //콘솔 출력
                fos.write(data); //복붙
            }
            fos.flush();


        } catch (IOException e){
            System.out.println(e.getMessage());
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
