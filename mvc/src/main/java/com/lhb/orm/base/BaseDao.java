package com.lhb.orm.base;

import java.util.List;

import javax.sql.DataSource;

public interface BaseDao {

    <T> int add(String sql, Object baseEntity);

    <T> int add(String sql, List<Object> list);

    <T> List<T> getList(String sql, Class<T> clazz, Object baseEntity);

    <T> T queryForObject(String sql, Class<T> clazz, Object baseEntity);

    int update(String sql, Object baseEntity);

    int delete(String sql, Object baseEntity);

    void setDataSource(DataSource dataSource) throws RuntimeException;
}
