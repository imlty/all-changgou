package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 描述
 *
 * @author www.itheima.com
 * @version 1.0
 * @package com.changgou *
 * @since 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.goods.feign"})
@EnableElasticsearchRepositories(basePackages = "com.changgou.search.dao")
public class SearchApplication {

    public static void main(String[] args)
    {
        /*
          springboot 整合 elasticsearch 在项目启动前设置一下属性,防止报错
          解决netty冲突后初始化client时还会抛出异常
         */
//        System.setProperty("es.set.netty.runtime.avaliable.processors","false");
        SpringApplication.run(SearchApplication.class,args);
    }

}
