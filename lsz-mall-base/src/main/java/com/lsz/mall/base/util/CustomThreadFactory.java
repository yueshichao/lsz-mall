package com.lsz.mall.base.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class CustomThreadFactory implements ThreadFactory {

    private String namePrefix;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();

    public CustomThreadFactory(String name) {
        this.namePrefix = name + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = defaultThreadFactory.newThread(r);
        t.setName(namePrefix + threadNumber.getAndIncrement());
        return t;
    }

}
