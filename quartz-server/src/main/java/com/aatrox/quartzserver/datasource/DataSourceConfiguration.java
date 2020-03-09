package com.aatrox.quartzserver.datasource;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:datasource.properties")
public class DataSourceConfiguration {
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.maxActive}")
    private int maxActive;
    @Value("${jdbc.maxIdel}")
    private int maxIdel;
    @Value("${jdbc.maxWait}")
    private long maxWait;
    @Value("${jdbc.driver}")
    private String driver;

    @Bean(name = "dataSource")
    public DataSource dataSourceTarget() throws SQLException {
        DruidDataSource dataSourceDefault = new DruidDataSource();
        dataSourceDefault.setDriverClassName(driver);
        dataSourceDefault.setUrl(url);
        dataSourceDefault.setUsername(username);
        dataSourceDefault.setPassword(password);
        dataSourceDefault.setMaxIdle(maxIdel);
        dataSourceDefault.setValidationQuery("SELECT 1");
        dataSourceDefault.setTestOnBorrow(true);
        dataSourceDefault.setPoolPreparedStatements(true);
        dataSourceDefault.setMaxPoolPreparedStatementPerConnectionSize(20);
        //使用P6时才使用的，不是的话，直接返回dataSourceDefault
        return dataSourceDefault;
    }

}
