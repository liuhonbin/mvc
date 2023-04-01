package com.lhb.pool;

/**
 * <p>Copyright： Copyright (c) 2017</p>
 * <p>Company： 熠道大数据</p>
 *
 * @author liuhonbin
 * @ClassName: DatabaseConfiguration
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2018年4月24日
 */
public class DatabaseConfiguration {

    private String username;
    private String password;
    private String driverClass;
    private String jdbcUrl;
    private int initialSize = 0;
    private int minIdle = 0;
    private int maxIdle = 0;
    private int maxActive = 0;
    private long maxWait = 0;


    public DatabaseConfiguration() {
        // TODO Auto-generated constructor stub
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

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
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

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

}
