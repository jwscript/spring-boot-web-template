package com.hello.hellospring.study.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class FileTest {
    @Test
    @DisplayName("file 정보 출력")
    void fileTest01() {
        File file = new File("fileReader.txt");
        printFileInfo(file);
    }

    private void printFileInfo(File file) {
        String fileName = file.getName();
        System.out.println("경로를 제외한 파일명 = " + fileName);

        int pos = fileName.lastIndexOf(".");
        System.out.println("pos = " + pos);

        System.out.println("확장자를 제외한 파일이름 = " + fileName.substring(0, pos));
        System.out.println("확장자 = " + fileName.substring(pos + 1));

        System.out.println("경로를 포함한 파일 이름 = " + file.getPath()); // 파일명만 집어넣으면 앞의 경로가 없을 수 있음.
        System.out.println("파일의 절대 경로 = " + file.getAbsolutePath());
        System.out.println("파일이 속해 있는 디렉토리 = " + file.getParent()); // 파일명만 집어넣으면 디렉토리가 null.

        /*
         * File.pathSeparator : OS에서 사용하는 경로 구분자. (윈도우: ";", 유닉스: ":")
         * File.pathSeparatorChar : OS에서 사용하는 경로 구분자. (윈도우: ';', 유닉스: ':')
         */
        System.out.println("File.pathSeparator = " + File.pathSeparator);
        System.out.println("File.pathSeparatorChar = " + File.pathSeparatorChar);

        /*
         * File.separator : OS에서 사용하는 이름 구분자. (윈도우: "\", 유닉스: "/")
         * File.separatorChar : OS에서 사용하는 이름 구분자. (윈도우: '\', 유닉스: '/')
         */
        System.out.println("File.separator = " + File.separator);
        System.out.println("File.separatorChar = " + File.separatorChar);

        System.out.println("user.dir = " + System.getProperty("user.dir")); // 현재 프로젝트 경로?
    }

    @Test
    @DisplayName("요청한 경로에 있는 파일과 디렉토리 출력")
    void fileTest02() {
        File directory = new File("./"); // 현재 프로젝트 ROOT 경로

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("유효하지 않은 디렉토리입니다.");
            System.exit(0);
        }

        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            System.out.println(files[i].isDirectory() ? "[" + fileName + "]" : fileName);
        }
    }

    @Test
    @DisplayName("내부 파일과 폴더를 선택한 기준에 따라 정렬하기")
    void fileTest03() {
        final char option = 'n'; // [T, t, L, l, N, n]
        String currentDir = System.getProperty("user.dir");
        File dir = new File(currentDir);
        File[] files = dir.listFiles(); // 디렉토리의 파일과 디렉토리 목록을 반환한다.

        Comparator comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                long time1 = ((File) o1).lastModified();
                long time2 = ((File) o2).lastModified();

                long length1 = ((File) o1).length();
                long length2 = ((File) o2).length();

                String name1 = ((File) o1).getName().toLowerCase();
                String name2 = ((File) o2).getName().toLowerCase();

                int result = 0;

                switch (option) {
                    case 't': // 수정된 시간 오름차순
                        if (time1 - time2 > 0) result = 1;
                        else if (time1 - time2 == 0) result = 0;
                        else if (time1 - time2 < 0) result = -1;
                        break;
                    case 'T': // 수정된 시간 내림차순
                        if (time1 - time2 > 0) result = -1;
                        else if (time1 - time2 == 0) result = 0;
                        else if (time1 - time2 < 0) result = 1;
                        break;
                    case 'l': // 파일 크기 오름차순
                        if (length1 - length2 > 0) result = 1;
                        else if (length1 - length2 == 0) result = 0;
                        else if (length1 - length2 < 0) result = -1;
                        break;
                    case 'L': // 파일 크기 내림차순
                        if (length1 - length2 > 0) result = -1;
                        else if (length1 - length2 == 0) result = 0;
                        else if (length1 - length2 < 0) result = 1;
                        break;
                    case 'n': // 파일명 오름차순
                        result = name1.compareTo(name2);
                        break;
                    case 'N': // 파일명 내림차순
                        result = name2.compareTo(name1);
                        break;
                    default:
                        break;
                }

                return result;
            }
        };

        Arrays.sort(files, comparator);

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String name = file.getName();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String attribute = "";
            String size = "";

            if (files[i].isDirectory()) {
                attribute = "DIR";
            } else {
                size = file.length() + "";
                attribute = file.canRead() ? "R" : " ";
                attribute += file.canWrite() ? "W" : " ";
                attribute += file.isHidden() ? "H" : " ";
            }

            System.out.printf("%s %3s %6s %s\n",
                    simpleDateFormat.format(new Date(file.lastModified())),
                    attribute,
                    size,
                    name);
        }
    }
}
