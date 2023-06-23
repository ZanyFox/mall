package com.fz.mall.cart;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class CartServiceTests {

    @Test
    public void testReduce() {

        List<Integer> list = null;
//
//        list.add(1);
//        list.add(2);
//        list.add(null);


        Integer count = Optional.ofNullable(list)
                .orElse(Collections.emptyList())
                .stream().filter(Objects::nonNull)
                .reduce(0, Integer::sum);
        System.out.println(count);

        BigDecimal decimal = new BigDecimal(0);
        BigDecimal add = decimal.add(new BigDecimal("2.4534563643"));
        System.out.println(add);
    }
}
