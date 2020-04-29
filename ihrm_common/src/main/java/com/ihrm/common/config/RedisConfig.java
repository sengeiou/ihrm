package com.ihrm.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {
//    /**
//     * 缓存对象集合中，缓存是以 key-value 形式保存的。
//     * 当不指定缓存的 key 时，SpringBoot 会使用 SimpleKeyGenerator 生成 key。
//     */
////  @Bean
//    public KeyGenerator wiselyKeyGenerator()
//    {
//        // key前缀，用于区分不同项目的缓存，建议每个项目单独设置
//        final String PRE_KEY = "company";
//        final char sp = ':';
//        return new KeyGenerator()
//        {
//            @Override
//            public Object generate(Object target, Method method, Object... params)
//            {
//                StringBuilder sb = new StringBuilder();
//                sb.append(PRE_KEY);
//                sb.append(sp);
//                sb.append(target.getClass().getSimpleName());
//                sb.append(sp);
//                sb.append(method.getName());
//                for (Object obj : params)
//                {
//                    sb.append(sp);
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory)
//    {
//        // 更改值的序列化方式，否则在Redis可视化软件中会显示乱码。默认为JdkSerializationRedisSerializer
//        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
//                .fromSerializer(new GenericJackson2JsonRedisSerializer());
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration
//                .defaultCacheConfig()
//                .serializeValuesWith(pair)      // 设置序列化方式
//                .entryTtl(Duration.ofHours(1)); // 设置过期时间
//
//        return RedisCacheManager
//                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
//                .cacheDefaults(defaultCacheConfig).build();
//    }

    /**
     * 缓存对象集合中，缓存是以 key-value 形式保存的。当不指定缓存的 key 时，SpringBoot 会使用 SimpleKeyGenerator 生成 key。
     *
     * @return
     */
    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }

//    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
//        return new RedisCacheManager(redisTemplate);
//    }

    /**
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        		.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(buildjackson2JsonRedisSerializer())).entryTtl(Duration.of(1, ChronoUnit.DAYS));
        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory).cacheDefaults(redisCacheConfiguration).build();

        return redisCacheManager;
    }
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration(){
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
//        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).entryTtl(Duration.ofDays(30));
//        return configuration;
//    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setValueSerializer(buildjackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
    
    /**
     * 通过自定义配置构建Redis的Json序列化器
     * @return Jackson2JsonRedisSerializer对象
     */
    private static Jackson2JsonRedisSerializer<Object> buildjackson2JsonRedisSerializer(){
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new MyObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
    
    static class MyObjectMapper extends ObjectMapper {
        private static final long serialVersionUID = 1L;
 
        public MyObjectMapper() {
            super();
            // 去掉各种@JsonSerialize注解的解析
            this.configure(MapperFeature.USE_ANNOTATIONS, false);
            // 只针对非空的值进行序列化
            this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 将类型序列化到属性json字符串中
//            this.enableDefaultTyping(DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
            // 对于找不到匹配属性的时候忽略报错
            this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 不包含任何属性的bean也不报错
            this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        }
     }

}
