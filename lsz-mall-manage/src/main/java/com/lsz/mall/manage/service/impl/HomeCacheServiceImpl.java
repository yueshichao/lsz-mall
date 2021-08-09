package com.lsz.mall.manage.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.lsz.mall.manage.service.HomeCacheService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class HomeCacheServiceImpl implements HomeCacheService {

    @Autowired
    RedissonClient redisson;

    @Resource(name = "delayDeletedExecutor")
    ThreadPoolExecutor delayDeletedExecutor;


    @Override
    public void deleteHomeInfoCache() {
        String key = "mall:home:infos";
        RBucket<Object> homeInfoBucket = redisson.getBucket(key);
        delayDeletedExecutor.execute(() -> {
            ThreadUtil.sleep(1000);
            log.debug("删除key = {}", key);
            homeInfoBucket.delete();
        });
    }


}
