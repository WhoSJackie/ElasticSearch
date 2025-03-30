package com.wang.business.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@RefreshScope
@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE,sqlSessionTemplateRef="mysqlSessionTemplate")
public class DataSourceConfig {

    public  static final String PACKAGE = "com.wang.business.mysqldao";

    @Value("${datasource.mysql.driver}")
    private String driver;

    @Value("${datasource.mysql.url}")
    private String url;

    @Value("${datasource.mysql.username}")
    private String username;

    @Value("${datasource.mysql.password}")
    private String password;


    @Primary
    @Bean("dataSource")
    DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName("mysql-datasource");
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(10);
        dataSource.setValidationQuery("select '1' ");
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }

    @Primary
    @Bean("mysqlSessionFactory")
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/wang/business/mysqldao/*.xml"));
        // sqlSessionFactory.setTypeAliasesPackage(typeAlias);
        return sqlSessionFactory.getObject();
    }

    @Primary
    @Bean(name = "mysqlSessionTemplate")
    public SqlSessionTemplate mysqlSessionTemplate(@Qualifier("mysqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
