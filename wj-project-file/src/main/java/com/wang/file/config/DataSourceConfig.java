package com.wang.file.config;

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
@MapperScan(basePackages = DataSourceConfig.PACKAGE,sqlSessionTemplateRef="mysqlFileSessionTemplate")
public class DataSourceConfig {

    public  static final String PACKAGE = "com.wang.file.dao";

    @Value("${datasource.filemysql.driver}")
    private String driver;

    @Value("${datasource.filemysql.url}")
    private String url;

    @Value("${datasource.filemysql.username}")
    private String username;

    @Value("${datasource.filemysql.password}")
    private String password;


    @Bean("fileDataSource")
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


    @Bean("mysqlFileSessionFactory")
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("fileDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/wang/file/dao/*.xml"));
        // sqlSessionFactory.setTypeAliasesPackage(typeAlias);
        return sqlSessionFactory.getObject();
    }


    @Bean(name = "mysqlFileSessionTemplate")
    public SqlSessionTemplate mysqlSessionTemplate(@Qualifier("mysqlFileSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
