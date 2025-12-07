package com.wang.ms.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
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
@MapperScan(basePackages = DataSourceConfig.PACKAGE,sqlSessionTemplateRef="msMysqlSessionTemplate")
public class DataSourceConfig {

    public static final String PACKAGE = "com.wang.ms.mysqldao";

    @Value("${datasource.msmysql.driver}")
    private String driver;

    @Value("${datasource.msmysql.url}")
    private String url;

    @Value("${datasource.msmysql.username}")
    private String username;

    @Value("${datasource.msmysql.password}")
    private String password;


    @Primary
    @Bean("msDataSource")
    DataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setName("mysql-msDatasource");
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
    @Bean("msMysqlSessionFactory")
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("msDataSource") DataSource dataSource,@Qualifier("paginationInnerInterceptor") PaginationInnerInterceptor paginationInnerInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/wang/ms/mysqldao/*.xml"));
        // sqlSessionFactory.setTypeAliasesPackage(typeAlias);
        // 分页插件的使用
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        sqlSessionFactory.setPlugins(mybatisPlusInterceptor);
        return sqlSessionFactory.getObject();
    }

    @Primary
    @Bean(name = "msMysqlSessionTemplate")
    public SqlSessionTemplate mysqlSessionTemplate(@Qualifier("msMysqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
