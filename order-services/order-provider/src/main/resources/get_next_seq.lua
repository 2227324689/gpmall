local function get_max_seq()
    local key = tostring(KEYS[1])
    local incr_amoutt = tonumber(KEYS[2])
    local seq = tostring(KEYS[3])
    local month_in_seconds = 24 * 60 * 60 * 30
    if (1 == redis.call('setnx', key, seq))
    then
        redis.call('expire', key, month_in_seconds)
        return seq
    else
        local prev_seq = redis.call('get', key)
        if (prev_seq < seq)
        then
            redis.call('set', key, seq)
            return seq
        else
            redis.call('incrby', key, incr_amoutt)
            return redis.call('get', key)
        end
    end
end

return get_max_seq()