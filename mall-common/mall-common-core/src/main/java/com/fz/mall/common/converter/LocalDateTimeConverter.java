package com.fz.mall.common.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 自定义参数转换器
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {


    @Nullable
    @Override
    public LocalDateTime convert(@NonNull String source) {
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
