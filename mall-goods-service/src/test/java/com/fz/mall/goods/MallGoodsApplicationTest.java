package com.fz.mall.goods;


import com.aliyun.oss.OSS;
import com.fz.mall.goods.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MallGoodsApplicationTest {


    @Autowired
    private OSS ossClient;


    @Autowired
    private BrandService brandService;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    @Test
    public void testUploadAliyun() throws IOException {
        ossClient.putObject(bucket, "test.png", Files.newInputStream(Paths.get("E:\\img\\meizhi\\a058336ely1h2dqso6p8xj21o02yonpe.jpg")));
        ossClient.shutdown();
    }

}
