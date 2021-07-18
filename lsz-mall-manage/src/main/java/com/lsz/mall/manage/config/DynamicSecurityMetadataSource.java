package com.lsz.mall.manage.config;

import cn.hutool.core.util.URLUtil;
import com.lsz.mall.base.entity.AdminResource;
import com.lsz.mall.manage.service.AdminResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 动态权限数据源，用于获取动态权限规则
 */
@Slf4j
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, CommandLineRunner/*使用此接口，在容器启动结束再初始化数据*/ {

    private static ConcurrentMap<String, ConfigAttribute> configAttributeMap = new ConcurrentHashMap<>();

    @Autowired
    private AdminResourceService resourceService;

    @Override
    public void run(String... args) throws Exception {
        log.info("CommandLineRunner callback~~~");
        loadDataSource();
    }

    // 该bean构造时，flyway可能还没执行，可能resource表还不存在
    @PostConstruct
    public void loadDataSource() {
        List<AdminResource> resourceList = resourceService.listAll();
        for (AdminResource resource : resourceList) {
            configAttributeMap.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
        }
    }

    public void clearDataSource() {
        configAttributeMap.clear();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeMap == null) this.loadDataSource();
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        //获取当前访问的路径
        String url = ((FilterInvocation) o).getRequestUrl();
        String path = URLUtil.getPath(url);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        //获取访问该路径所需资源
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }
        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
