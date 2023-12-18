package com.green.greengram3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Exam1 {


    @Test
    @DisplayName("테스트1") // 어노테이션 따로 안주면 메서드 이름인 test1이 뜸(콘솔창 왼쪽)
    public void test1() {
        System.out.println("test1");
        int sum = 2 + 2;
        Assertions.assertEquals(4, sum); // 기대하는 값, 실제 값
    }

    @Test
    @DisplayName("테스트2")
    public void test2() {
        System.out.println("test2");
        int multi = 2 * 3;
        Assertions.assertEquals(6, multi);
    }

    @Test
    public void test3() {
        System.out.println("test3");
        Assertions.assertEquals(4, MyUtils.sum(2, 2));
        Assertions.assertEquals(5, MyUtils.sum(2, 3));
        Assertions.assertEquals(15, MyUtils.sum(12, 3));
        Assertions.assertEquals(18, MyUtils.sum(5, 13));
    }

    @Test
    public void test4() {
        MyUtils myMulti = new MyUtils();
        Assertions.assertEquals(6, myMulti.multi(2, 3));
        Assertions.assertEquals(5, myMulti.multi(5, 1));
        Assertions.assertEquals(15, myMulti.multi(5, 3));
        Assertions.assertEquals(27, myMulti.multi(3, 9));
    }
}
