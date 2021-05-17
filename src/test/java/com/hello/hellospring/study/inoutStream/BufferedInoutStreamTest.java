/**
 * 스트림의 입출력 효율을 높이기 위해 버퍼를 사용하는 보조스트림.
 * 1 바이트씩 입출력하는 것 보다는
 * 버퍼(바이트 배열)를 이용해서 한 번에 여러 바이트를 입출력하는 것이 빠르기 때문.
 */
package com.hello.hellospring.study.inoutStream;

import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedInoutStreamTest {

    @Test
    void bufferedInoutStreamTest01() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("sample.txt");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 5);

            for (int i = '1'; i <= '9'; i++) {
                bufferedOutputStream.write(i);
            }

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 먼저, FileOutputStream의 파라미터로 파일명을 넣으면 이 프로젝트의 ROOT 경로에서 파일이 생성된다.
         *
         * 이 예제에서는
         * new BufferedOutputStream(fileOutputStream, 5);
         * 를 통하여 지정된 크기 5 byte의 버퍼를 갖는 인스턴스를 생성한다.
         * ★★★ BufferedOutStream의 인스턴스는 5 byte가 가득 채워져야 버퍼의 모든 내용을 출력 소스에 출력한다.
         * 즉, 5개가 다 채워지지 않으면 출력 소스에 출력하지 못할 수 있다.
         */
    }

    @Test
    void bufferedInoutStreamTest02() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("sample.txt");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 5);

            for (int i = '1'; i <= '9'; i++) {
                bufferedOutputStream.write(i);
            }

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 먼저, FileOutputStream의 파라미터로 파일명을 넣으면 이 프로젝트의 ROOT 경로에서 파일이 생성된다.
         *
         * 이 예제에서는
         * new BufferedOutputStream(fileOutputStream, 5);
         * 를 통하여 지정된 크기 5 byte의 버퍼를 갖는 인스턴스를 생성한다.
         * ★★★ BufferedOutStream의 인스턴스는 5 byte가 가득 채워져야 버퍼의 모든 내용을 출력 소스에 출력한다.
         * 즉, 5개가 다 채워지지 않으면 출력 소스에 출력하지 못할 수 있다.
         */
    }
}
