package com.wang.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.wang.common.Constants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = DataSourceConfig.PACKAGE,sqlSessionTemplateRef="sqlSessionTemplate")
public class DataSourceConfig {

    public  static final String PACKAGE = "com.wang.business.mysqldao";


    @Primary
    @Bean("dataSource")
    DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName("mysql-datasource");
        dataSource.setDriverClassName(Constants.MYSQLDRIVER);
        dataSource.setUrl(Constants.MYSQLURL);
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(10);
        dataSource.setValidationQuery("select '1' ");
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }

    @Primary
    @Bean("sqlSessionFactory")
    public SqlSessionFactory zeusSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/wang/mysqldao/*.xml"));
        // sqlSessionFactory.setTypeAliasesPackage(typeAlias);
        return sqlSessionFactory.getObject();
    }

    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate zeusSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
