package com.gpmall.order.utils;/**
 * Created by mic on 2019/8/1.
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    public GlobalIdGeneratorUtil() throws IOException {

    }
    @PostConstruct
    private void init() throws Exception {
        Path filePath=Paths.get(Thread.currentThread().getContextClassLoader().getResource("get_next_seq.lua").toURI());
        byte[] script;
        try {
            script = Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("读取文件出错, path: {}", filePath);
            throw e;
        }
        sha1 = redissonClient.getScript().scriptLoad(new String(script));
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
        Future<String> maxSeq= redissonClient.getScript().evalShaAsync(RScript.Mode.READ_WRITE,sha1, RScript.ReturnType.VALUE,keys);
        return maxSeq.get();
    }
}
