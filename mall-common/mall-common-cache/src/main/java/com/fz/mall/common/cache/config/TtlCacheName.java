package com.fz.mall.common.cache.config;

import java.time.Duration;

/**
 * 通过 cacheName 配置 和 时间告诉缓存多久清楚一遍
 *
 * @author FrozenWatermelon
 * @date 2020/7/4
 */
public class TtlCacheName {

	private String cacheName;

	private Duration ttl;

	public TtlCacheName(String cacheName, Duration ttl) {
		this.cacheName = cacheName;
		this.ttl = ttl;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public Duration getTtl() {
		return ttl;
	}

	public void setTtl(Duration ttl) {
		this.ttl = ttl;
	}

	@Override
	public String toString() {
		return "CacheNameWithTtlBO{" + "cacheName='" + cacheName + '\'' + ", ttl=" + ttl + '}';
	}

}
