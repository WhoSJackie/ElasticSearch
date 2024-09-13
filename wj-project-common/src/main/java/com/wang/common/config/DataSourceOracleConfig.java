package com.wang.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.wang.common.constants.Constants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@RefreshScope
@Configuration
@MapperScan(basePackages = DataSourceOracleConfig.PACKAGE,sqlSessionTemplateRef="oracleSqlSessionTemplate")
public class DataSourceOracleConfig {

    public  static final String PACKAGE = "com.wang.business.dao";

    @Value("${datasource.oracle.driver}")
    private String driver;

    @Value("${datasource.oracle.url}")
    private String url;

    @Value("${datasource.oracle.username}")
    private String username;

    @Value("${datasource.oracle.password}")
    private String password;

    @Bean("oracleDataSource")
    DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName("oracle-datasource");
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(10);
        dataSource.setValidationQuery("select '1' from dual");
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }


    @Bean("oracleSqlSessionFactory")
    public SqlSessionFactory oracleSqlSessionFactory(@Qualifier("oracleDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/wang/business/dao/*.xml"));
        // sqlSessionFactory.setTypeAliasesPackage(typeAlias);
        return sqlSessionFactory.getObject();
    }


    @Bean(name = "oracleSqlSessionTemplate")
    public SqlSessionTemplate oracleSqlSessionTemplate(@Qualifier("oracleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
