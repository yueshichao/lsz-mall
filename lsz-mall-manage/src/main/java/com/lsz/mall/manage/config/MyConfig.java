package com.lsz.mall.manage.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.lsz.mall.base.entity.ServiceException;
import com.lsz.mall.base.util.CustomThreadFactory;
import com.lsz.mall.manage.util.JwtTokenUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class MyConfig {


    // mybatis-plus分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        return jwtTokenUtil;
    }

    // redis
    @Value("${spring.redis.host}")
    String redisHost;

    @Value("${spring.redis.port}")
    Integer redisPort;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setDatabase(0)
                .setAddress("redis://" + redisHost + ":" + redisPort);
        config.setCodec(fastJsonCodec());
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

    static {
        // autoType异常，打开会降低安全性
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    // fastjson序列化
    // https://blog.csdn.net/miaochenfly/article/details/116457365
    public BaseCodec fastJsonCodec() {
        return new BaseCodec() {
            private final Encoder encoder = in -> {
                ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
                try {
                    ByteBufOutputStream os = new ByteBufOutputStream(out);
                    JSON.writeJSONString(os, in, SerializerFeature.WriteClassName);
                    return os.buffer();
                } catch (IOException e) {
                    out.release();
                    throw e;
                } catch (Exception e) {
                    out.release();
                    throw new IOException(e);
                }
            };

            private final Decoder<Object> decoder = (buf, state) ->
                    JSON.parseObject(new ByteBufInputStream(buf), Object.class);

            @Override
            public Decoder<Object> getValueDecoder() {
                return decoder;
            }

            @Override
            public Encoder getValueEncoder() {
                return encoder;
            }
        };
    }

    // 延迟删除的线程池
    @Bean("delayDeletedExecutor")
    public ThreadPoolExecutor delayDeletedExecutor() {
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("delay-delete");
        ThreadPoolExecutor delayDeletedExecutor = new ThreadPoolExecutor(
                1, 2,
                1, TimeUnit.HOURS,
                new ArrayBlockingQueue<>(200),
                customThreadFactory,
                (r, executor1) -> {
                    log.error("无线程资源！");
                });
        // 可优化，合并任务
        return delayDeletedExecutor;
    }

    // fastjson作为response返回的序列化方式
    private static List<MediaType> supportedMediaTypes = new ArrayList<>(17);

    static {
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        SerializerFeature[] featureXX = new SerializerFeature[]{SerializerFeature.BrowserSecure, SerializerFeature.DisableCircularReferenceDetect};
        config.setSerializerFeatures(featureXX);
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastConverter.setFastJsonConfig(config);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        return fastConverter;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate temp = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        SerializerFeature[] featureXX = new SerializerFeature[]{SerializerFeature.BrowserSecure, SerializerFeature.DisableCircularReferenceDetect};
        config.setSerializerFeatures(featureXX);
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(converter);
        temp.setMessageConverters(converters);

        return temp;
    }

}
