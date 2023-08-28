package com.zero.demos.seata.msb.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/**
 * @author ylxb2
 */
@Configuration
@ConfigurationProperties(prefix = "spring")
@MapperScan(basePackages = {"com.zero.demos.seata.msb.acl.repository"}, sqlSessionFactoryRef="sqlSessionFactory")
public class DataSourceConfig {

    @NestedConfigurationProperty
    private Datasource datasource;
    @NestedConfigurationProperty
    private Mybatis mybatis;

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public Mybatis getMybatis() {
        return mybatis;
    }

    public void setMybatis(Mybatis mybatis) {
        this.mybatis = mybatis;
    }

    @Bean(name="dataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(datasource.getUrl());
        dataSource.setUsername(datasource.getUsername());
        dataSource.setPassword(datasource.getPassword());
        dataSource.setDriverClassName(datasource.getDriverClassName());
        dataSource.setInitialSize(datasource.getInitialSize());
        dataSource.setMinIdle(datasource.getMinIdle());
        dataSource.setMaxActive(datasource.getMaxActive());
        dataSource.setMaxWait(datasource.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(datasource.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(datasource.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(datasource.getValidationQuery());
        dataSource.setQueryTimeout(datasource.getQueryTimeout());
        dataSource.setPoolPreparedStatements(datasource.isPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(datasource.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            dataSource.setFilters(datasource.getFilters());
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        dataSource.setConnectionProperties(datasource.getConnectionProperties());
        return dataSource;
    }

    @Lookup("dataSource")
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:/mappers/*.xml"));
        return bean.getObject();
    }

    @Lookup("dataSource")
    @Bean(name="dataSourceTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @ConfigurationProperties(prefix = "spring.mybatis")
    public static class Mybatis {
        private String mapperLocation;
        private String[] typeAliasesPackage;
        private String[] typeHandlersPackage;

        public String getMapperLocation() {
            return mapperLocation;
        }

        public void setMapperLocation(String mapperLocation) {
            this.mapperLocation = mapperLocation;
        }

        public String[] getTypeAliasesPackage() {
            return typeAliasesPackage;
        }

        public void setTypeAliasesPackage(String[] typeAliasesPackage) {
            this.typeAliasesPackage = typeAliasesPackage;
        }

        public String[] getTypeHandlersPackage() {
            return typeHandlersPackage;
        }

        public void setTypeHandlersPackage(String[] typeHandlersPackage) {
            this.typeHandlersPackage = typeHandlersPackage;
        }
    }

    @ConfigurationProperties(prefix = "spring.datasource")
    public static class Datasource {
        private String driverClassName;
        private String url;
        private String username;
        private String password;

        private int initialSize;

        private int minIdle;

        private int maxActive = DruidDataSource.DEFAULT_MAX_ACTIVE_SIZE;

        private int maxWait;

        private int timeBetweenEvictionRunsMillis;

        private int minEvictableIdleTimeMillis = 60000;

        private String validationQuery;

        private int queryTimeout;

        private boolean testWhileIdle;

        private boolean testOnBorrow;

        private boolean testOnReturn;

        private boolean poolPreparedStatements;

        private int maxPoolPreparedStatementPerConnectionSize;

        private String filters;

        private String connectionProperties;

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getInitialSize() {
            return initialSize;
        }

        public void setInitialSize(int initialSize) {
            this.initialSize = initialSize;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }

        public int getTimeBetweenEvictionRunsMillis() {
            return timeBetweenEvictionRunsMillis;
        }

        public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        }

        public int getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        public String getValidationQuery() {
            return validationQuery;
        }

        public void setValidationQuery(String validationQuery) {
            this.validationQuery = validationQuery;
        }

        public int getQueryTimeout() {
            return queryTimeout;
        }

        public void setQueryTimeout(int queryTimeout) {
            this.queryTimeout = queryTimeout;
        }

        public boolean isTestWhileIdle() {
            return testWhileIdle;
        }

        public void setTestWhileIdle(boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
        }

        public boolean isTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public boolean isTestOnReturn() {
            return testOnReturn;
        }

        public void setTestOnReturn(boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
        }

        public boolean isPoolPreparedStatements() {
            return poolPreparedStatements;
        }

        public void setPoolPreparedStatements(boolean poolPreparedStatements) {
            this.poolPreparedStatements = poolPreparedStatements;
        }

        public int getMaxPoolPreparedStatementPerConnectionSize() {
            return maxPoolPreparedStatementPerConnectionSize;
        }

        public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
            this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
        }

        public String getFilters() {
            return filters;
        }

        public void setFilters(String filters) {
            this.filters = filters;
        }

        public String getConnectionProperties() {
            return connectionProperties;
        }

        public void setConnectionProperties(String connectionProperties) {
            this.connectionProperties = connectionProperties;
        }
    }
}
