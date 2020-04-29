package com.ihrm.company.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@ConditionalOnClass({ElasticsearchTemplate.class})
public class NettyEnviromentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

//        Properties properties = new Properties();
//        properties.put("es.set.netty.runtime.available.processors","false");
//        PropertiesPropertySource source = new PropertiesPropertySource("es-netty-property", properties);
        System.setProperty("es.set.netty.runtime.available.processors","false");
//        MutablePropertySources propertySources = environment.getPropertySources();
//
//        propertySources.addFirst(source);
    }
}
