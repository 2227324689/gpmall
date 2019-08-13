package com.gpmall.comment.utils;/**
 * Created by mic on 2019/8/1.
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.redisson.RedissonScript;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/8/1-下午9:40
 */
@Slf4j
@Component
public class GlobalIdGeneratorUtil {


    private static final FastDateFormat seqDateFormat = FastDateFormat.getInstance("yyMMddHHmmssSSS");

    @Autowired
    RedissonClient redissonClient;

    private String keyName;

    private int incrby;

    private String sha1;

    private String luaScript="local function get_max_seq()\n" +
            "    local key = tostring(KEYS[1])\n" +
            "    local incr_amoutt = tonumber(KEYS[2])\n" +
            "    local seq = tostring(KEYS[3])\n" +
            "    local month_in_seconds = 24 * 60 * 60 * 30\n" +
            "    if (1 == redis.call('setnx', key, seq))\n" +
            "    then\n" +
            "        redis.call('expire', key, month_in_seconds)\n" +
            "        return seq\n" +
            "    else\n" +
            "        local prev_seq = redis.call('get', key)\n" +
            "        if (prev_seq < seq)\n" +
            "        then\n" +
            "            redis.call('set', key, seq)\n" +
            "            return seq\n" +
            "        else\n" +
            "            redis.call('incrby', key, incr_amoutt)\n" +
            "            return redis.call('get', key)\n" +
            "        end\n" +
            "    end\n" +
            "end\n" +
            "return get_max_seq()";

    public GlobalIdGeneratorUtil() throws IOException {

    }
    @PostConstruct
    private void init() throws Exception {
        sha1 = redissonClient.getScript().scriptLoad(luaScript);
    }

    public String getNextSeq(String keyName, int incrby) {
        if(StringUtils.isBlank(keyName)||incrby<0) {
            throw new RuntimeException("参数不正确");
        }
        this.keyName=keyName;
        this.incrby=incrby;
        try {
            return getMaxSeq();
        }catch (Exception e){//如果redis出现故障，则采用uuid
            e.printStackTrace();
            return UUID.randomUUID().toString().replace("-","");
        }
    }

    private String generateSeq() {
        String seqDate = seqDateFormat.format(System.currentTimeMillis());
        String candidateSeq = new StringBuilder(17).append(seqDate).append(RandomStringUtils.randomNumeric(2)).toString();
        return candidateSeq;
    }

    public String getMaxSeq() throws ExecutionException, InterruptedException {
        List<Object> keys= Arrays.asList(keyName,incrby,generateSeq());
        RedissonScript rScript=(RedissonScript) redissonClient.getScript();
        //这里遇到一个bug，默认情况下使用evalSha，不加Codec属性时，会报错。这个错误很神奇。花了3个小时才搞定。
        Long seqNext=rScript.evalSha(RScript.Mode.READ_ONLY, JsonJacksonCodec.INSTANCE,sha1, RScript.ReturnType.VALUE,keys);
        return seqNext.toString();
    }
}
