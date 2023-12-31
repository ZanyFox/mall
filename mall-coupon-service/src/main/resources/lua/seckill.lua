---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by fan.
--- DateTime: 2023/6/14 19:23
---

-- 库存key
local stockKey = KEYS[1]
-- 用户已购买数key
local userPurchaseKey = KEYS[2]
-- 用户想要购买的数量
local purchaseCount = tonumber(ARGV[1])
-- 购买限制
local purchaseLimit = tonumber(ARGV[2])
-- 秒杀的过期时间
local expireTime = tonumber(ARGV[3])

if (tonumber(redis.call('get', userPurchaseKey)) or 0) + purchaseCount > purchaseLimit then
    -- 超出购买限制
    return 2
end

if (tonumber(redis.call('get', stockKey)) or 0) < purchaseCount then
    -- 库存不足
    return 1
end

redis.call('incrby', stockKey, -purchaseCount)
redis.call('incrby', userPurchaseKey, purchaseCount)
if redis.call('ttl', userPurchaseKey) == -1 then
    redis.call('expire', userPurchaseKey, expireTime + 10)
end
return 0
