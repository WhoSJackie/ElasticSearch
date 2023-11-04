package com.wang.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.wang.Constants;
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
@MapperScan(basePackages = DataSourceOracleConfig.PACKAGE,sqlSessionTemplateRef="oracleSqlSessionTemplate")
public class DataSourceOracleConfig {

    public  static final String PACKAGE = "com.wang.dao";



    @Bean("oracleDataSource")
    DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName("oracle-datasource");
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
        dataSource.setUsername("krdt");
        dataSource.setPassword("1");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(10);
        dataSource.setValidationQuery("select '1' from dual");
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }


    @Bean("oracleSqlSessionFactory")
    public SqlSessionFactory zeusSqlSessionFactory(@Qualifier("oracleDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/wang/dao/*.xml"));
        // sqlSessionFactory.setTypeAliasesPackage(typeAlias);
        return sqlSessionFactory.getObject();
    }


    @Bean(name = "oracleSqlSessionTemplate")
    public SqlSessionTemplate zeusSqlSessionTemplate(@Qualifier("oracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
