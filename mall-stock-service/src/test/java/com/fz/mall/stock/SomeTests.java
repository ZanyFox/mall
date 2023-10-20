package com.fz.mall.stock;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.junit.Test;

public class SomeTests {

    @Test
    public void testUUID() {

        System.out.println(IdWorker.get32UUID());
    }
}
