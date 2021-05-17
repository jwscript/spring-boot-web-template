/**
 * 바이트 기반의 스트림
 */
package com.hello.hellospring.study.inoutStream;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ByteInoutStreamTest {
    @Test
    void byteInoutStreamTest01() {
        byte[] inSrc = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] outSrc = null;

        byte[] temp = new byte[4];

        ByteArrayInputStream input = new ByteArrayInputStream(inSrc);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            while (input.available() > 0) {
                input.read(temp);
                output.write(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        outSrc = output.toByteArray();

        System.out.println("Input Source : " + Arrays.toString(inSrc));
        System.out.println("Output Source : " + Arrays.toString(outSrc));

        /**
         * 1.
         * input.read(temp)은 현재 input에서 데이터를 읽어서 temp에 저장한다.
         * 이후, input.available()을 해보면 temp의 length 만큼 줄어들어 있다.
         * ex) 10 -> 6 -> 2 -> 0
         * ※ 주의 사항
         * 마지막에 input에 2개의 데이터만 있는 경우에 input.read(temp)를 하면
         * temp[0], temp[1]은 2개의 값이 써지지만
         * temp[2], temp[3]에는 기존에 남아있던 데이터가 그대로 활용된다.
         *
         * 2.
         * output.write(temp)를 통해 temp에 있는 데이터를 output에 저장한다.
         *
         * 3. 출력 결과
         * OutputSource에는 [
         * 0, 1, 2, 3,
         * 4, 5, 6, 7,
         * 8, 9, 6, 7
         * ]
         * 마지막의 6과 7은 temp[2], temp[3]이 변경되지 않아서 발생한 결과임.
         */
    }

    @Test
    void byteInoutStreamTest02() {
        byte[] inSrc = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] outSrc = null;

        byte[] temp = new byte[4];

        ByteArrayInputStream input = new ByteArrayInputStream(inSrc);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            while (input.available() > 0) {
                int readLength = input.read(temp);
                output.write(temp, 0, readLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        outSrc = output.toByteArray();

        System.out.println("Input Source : " + Arrays.toString(inSrc));
        System.out.println("Output Source : " + Arrays.toString(outSrc));

        /**
         * 1.
         * input.read(temp)은 현재 input에서 데이터를 읽어서 temp에 저장한다.
         * 이후, input.available()을 해보면 temp의 length 만큼 줄어들어 있다.
         * ex) 10 -> 6 -> 2 -> 0
         * ※ 주의 사항
         * 마지막에 input에 2개의 데이터만 있는 경우에
         * input.read(temp)를 하면서 반환한 readLength에 의해
         * inoutTest01과는 다른 결과가 나옴.
         *
         * 2.
         * output.write(temp, 0, readLength)를 통해
         * temp에 있는 데이터를 index 0부터 readLength개의 데이터를 output에 저장한다.
         *
         * 3. 출력 결과
         * OutputSource에는 [
         * 0, 1, 2, 3,
         * 4, 5, 6, 7,
         * 8, 9
         * ]
         */
    }
}
