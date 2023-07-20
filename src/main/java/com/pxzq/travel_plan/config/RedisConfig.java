package com.pxzq.travel_plan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis对json序列化处理
 */
@Configuration
public class RedisConfig {


    //    @Bean
//    public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//        // 配置连接工厂
//        template.setConnectionFactory(redisConnectionFactory);
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper om = new ObjectMapper();
//        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jacksonSeial.setObjectMapper(om);
//
//        // 值采用json序列化
//        template.setValueSerializer(jacksonSeial);
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//
//        // 设置hash key 和value序列化模式
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jacksonSeial);
//        template.afterPropertiesSet();
//        return template;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        //1.创建
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        //json序列化工具
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        //设置key的序列化
        //StringRedisSerializer UTF_8 = new StringRedisSerializer(StandardCharsets.UTF_8);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        //设置值的value
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        return redisTemplate;
    }

}
