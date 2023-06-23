package com.fz.mall.common.pojo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;


public class BaseEntity implements Serializable {

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return "createTime=" + createTime +
                ", updateTime=" + updateTime ;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
