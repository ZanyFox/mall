package com.fz.mall.goods;


import com.fz.mall.goods.constant.SpuStatusEnum;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;



public class SomeTest {

    @Test
    public void testArray() {

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        String[] strings = list.toArray(new String[0]);
        System.out.println(Arrays.toString(strings));

    }

    @Test
    public void testPoint() {
        System.out.println(File.pathSeparator);
    }

    @Test
    public void testByte() {

        Byte b = -5;
        Integer i = 108;
        System.out.println();
    }

    @Test
    public void testStream() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        List<String> collect = list.stream().map(integer -> integer + "hhhh").collect(Collectors.toList());
        System.out.println(collect);

    }

    @Test
    public void testCompare() {
        Integer i1 = 1;
        Integer i2 = null;
        int compare = Objects.compare(i1, i2, Comparator.nullsLast(Comparator.comparingInt(value -> value)));
        System.out.println(compare);
    }

    @Test
    public void testEnum() {
        testEnum1(SpuStatusEnum.values()[0]);
    }

  public   void testEnum1(SpuStatusEnum spuStatusEnum) {

        switch (spuStatusEnum) {
            case CREATED:
                System.out.println(SpuStatusEnum.CREATED);
                break;
            case SALE:
                System.out.println(SpuStatusEnum.SALE);
                break;
            case NOT_SALE:
                System.out.println(SpuStatusEnum.NOT_SALE);
                break;

        }
    }


}
