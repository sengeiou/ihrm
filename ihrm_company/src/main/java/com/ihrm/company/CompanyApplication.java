package com.ihrm.company;

import com.ihrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//1.配置spring boot的包扫描
@SpringBootApplication(scanBasePackages = {"com.ihrm"})
//2.配置jpa注释的扫描
@EntityScan("com.ihrm.domain.company")
@EnableJpaRepositories(basePackages = "com.ihrm.company.repository")
@EnableElasticsearchRepositories(basePackages = "com.ihrm.company.es")
@EnableSwagger2
@EnableCaching
public class CompanyApplication {
    /**
     * 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}

