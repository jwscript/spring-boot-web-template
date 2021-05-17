/**
 * 문자 기반의 스트림
 */
package com.hello.hellospring.study.inoutStream;

import org.junit.jupiter.api.Test;

import java.io.*;

public class FileReaderTest {

    @Test
    void fileReaderTest01() {
        try {
            String fileName = "fileReader.txt";
            int data = 0;

            // 방법 1. FileInputStream을 사용하여 내용을 출력하는 방법.
            FileInputStream fileInputStream = new FileInputStream(fileName);
            /*
             * fileInputStream.read() 에서 한 글자를 읽으면 그 문자의 ASCII code를 반환한다.
             */
            while ((data = fileInputStream.read()) != -1) {
                System.out.print((char) data);
            }
            fileInputStream.close();

            System.out.println("\n-------------------------------");

            // 방법 2. FileReader를 사용하여 내용을 출력하는 방법.
            FileReader fileReader = new FileReader(fileName); // 문자를 하나 단위로 읽을 수 있는 문자 스트림
            while ((data = fileReader.read()) != -1) {
                System.out.print((char) data);
            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 위 예저는 fileReader.txt에 있는 문자를 '하나씩' 읽어서 출력한다.
         * 효율이 너무 떨어짐.
         */
    }

    @Test
    void fileReaderTest02() {
        try {
            String fileName = "fileReader.txt";
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader); // 데이터를 라인 단위로 읽을 수 있게 만들어주는 문자 기반의 보조 스트림

            String line = "";
            /*
             * 한 줄 씩 읽어서 line에 그 문장을 담는다.
             */
            for (int i = 1; (line = bufferedReader.readLine()) != null; i++) {
                if (line.indexOf(";") != -1) {
                    System.out.println(i + ": " + line);
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void inputStreamReaderTest01() {
        /**
         * 바이트 기반의 스트림 (InputStream)을 문자 기반 스트림으로 변경해주는 InputStreamReader, OutputStreamWriter.
         * InputStreamReader와 OutputStreamWriter는 변환 시켜주는 역할을 하는 듯.
         *
         */
        try {
            String fileName = "fileReader.txt";
            int data = 0;
            FileInputStream fileInputStream = new FileInputStream(fileName); // (바이트 기반) FileInputStream 생성
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream); // (바이트 -> 문자 기반) InputStreamReader 생성
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // (문자 기반) 데이터를 라인 단위로 읽을 수 있게 만들어주는 BufferedReader 생성

            String line = "";
            /*
             * 한 줄 씩 읽어서 line에 그 문장을 담는다.
             */
            for (int i = 1; (line = bufferedReader.readLine()) != null; i++) {
                if (line.indexOf(";") != -1) {
                    System.out.println(i + ": " + line);
                }
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
