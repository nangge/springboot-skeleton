-- 秒杀
-- 接收参数
local secKillKey = KEYS[1]
local userId = KEYS[2]
local usersKey = secKillKey..":users"
local secKillNum = ARGV[1]

-- 秒杀不存在
if redis.call('exists', secKillKey) ~= 1 then
    return -1
end

-- 判断用户是否已经成功秒杀过该商品
local userExists = redis.call('sismember', usersKey, userId)
if tonumber(userExists, 10) == 1 then
    return -2
end

-- 库存是否充足
local left = redis.call('get', secKillKey)
if tonumber(left, 10) <= 0 or left-secKillNum<0 then
    return -3
else
    --扣库存，并将抢购成功的用户放入集合中
    redis.call('decrby', secKillKey, secKillNum)
    redis.call('sadd', usersKey, userId)
end
return 1