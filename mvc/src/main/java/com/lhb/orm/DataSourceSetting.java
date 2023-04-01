package com.lhb.orm;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.lhb.core.ApplicationContext;
import com.lhb.core.exception.BeansException;
import com.lhb.core.factory.Bean4Obtain;
import com.lhb.core.factory.BeanFactory;

public final class DataSourceSetting {

    private BeanFactory factory = new Bean4Obtain();

    private DataSource master;

    private DataSource slave;

    private Map<String, DataSource> sqlDataSource = new HashMap<String, DataSource>();

    public DataSourceSetting() {

    }

    public DataSource getMaster() {
        return master;
    }

    public void setMaster(DataSource master, Object... sqlType) {
        this.master = master;
        for (Object sqlType2 : sqlType) {
            sqlDataSource.put(sqlType2.toString(), this.master);
        }
    }

    public DataSource getSlave() {
        return slave;
    }

    public void setSlave(DataSource slave, Object... sqlType) {
        this.slave = slave;
        for (Object sqlType2 : sqlType) {
            sqlDataSource.put(sqlType2.toString(), this.slave);
        }
    }

    public DataSource getDataSource(String type) throws RuntimeException {
        if (sqlDataSource.get(type) == null) {
            throw new RuntimeException("It doesn't exist type:'" + type + "'");
        }
        return sqlDataSource.get(type);
    }

}
