package com.lhb.pool.pool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;


public class SuperPoolConnection implements javax.sql.PooledConnection, java.sql.Connection {

    protected Connection connection;

    //这个类为单列，在此处不会重复加载，还是原来的第一次加载的对象
    private SuperPool superPool = SuperPool.getInstance();

    //连接是否关闭标志位
    boolean isClosed = false;


    //在类初始化的时候要求传入一个java.sql.Connection，这是在SuperPool.getConnection() 中进行了set
    public SuperPoolConnection(Connection connection) {
        this.connection = connection;
    }

    //这里去判断接口否关闭的方法进行了自定义的转态控制
    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    //关闭此连接时会进行连接回收操作
    public void close() throws SQLException {
        //将当前对象在已用连接集合中进行移除
        superPool.removeUse(this);
        //然后在将当前对象加到可用连接集合中
        superPool.addDuse(this);
        //次连接的转态Close转态设置为true
        isClosed = true;
        //再将连接设置为null
        connection = null;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return connection.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // TODO Auto-generated method stub
        return connection.isWrapperFor(iface);
    }

    public Statement createStatement() throws SQLException {
        // TODO Auto-generated method stub
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        // TODO Auto-generated method stub
        return connection.prepareStatement(sql);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        return connection.prepareCall(sql);
    }

    public String nativeSQL(String sql) throws SQLException {
        return connection.nativeSQL(sql);
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        // TODO Auto-generated method stub
        return connection.getMetaData();
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        connection.setReadOnly(readOnly);
    }

    public boolean isReadOnly() throws SQLException {
        return connection.isReadOnly();
    }

    public void setCatalog(String catalog) throws SQLException {
        connection.setCatalog(catalog);
    }

    public String getCatalog() throws SQLException {
        return connection.getCatalog();
    }

    public void setTransactionIsolation(int level) throws SQLException {
        connection.setTransactionIsolation(level);
    }

    public int getTransactionIsolation() throws SQLException {
        return connection.getTransactionIsolation();
    }

    public SQLWarning getWarnings() throws SQLException {
        return connection.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        connection.clearWarnings();
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return connection.createStatement(resultSetType, resultSetConcurrency);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return connection.getTypeMap();
    }

    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        connection.setTypeMap(map);
    }

    public void setHoldability(int holdability) throws SQLException {
        connection.setHoldability(holdability);
    }

    public int getHoldability() throws SQLException {
        return connection.getHoldability();
    }

    public Savepoint setSavepoint() throws SQLException {
        return connection.setSavepoint();
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        return connection.setSavepoint(name);
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        connection.rollback(savepoint);
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        connection.releaseSavepoint(savepoint);
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return connection.prepareStatement(sql, autoGeneratedKeys);
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return connection.prepareStatement(sql, columnIndexes);
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return connection.prepareStatement(sql, columnNames);
    }

    public Clob createClob() throws SQLException {
        return connection.createClob();
    }

    public Blob createBlob() throws SQLException {
        return connection.createBlob();
    }

    public NClob createNClob() throws SQLException {
        return connection.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return connection.createSQLXML();
    }

    public boolean isValid(int timeout) throws SQLException {
        return connection.isValid(timeout);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        connection.setClientInfo(name, value);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        connection.setClientInfo(properties);
    }

    public String getClientInfo(String name) throws SQLException {
        return connection.getClientInfo(name);
    }

    public Properties getClientInfo() throws SQLException {
        return connection.getClientInfo();
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return connection.createArrayOf(typeName, elements);
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return connection.createStruct(typeName, attributes);
    }

    public void setSchema(String schema) throws SQLException {
        connection.setSchema(schema);
    }

    public String getSchema() throws SQLException {
        return connection.getSchema();
    }

    public void abort(Executor executor) throws SQLException {
        connection.abort(executor);
    }

    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        connection.setNetworkTimeout(executor, milliseconds);
    }

    public int getNetworkTimeout() throws SQLException {
        return connection.getHoldability();
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void addConnectionEventListener(ConnectionEventListener listener) {
        // TODO Auto-generated method stub

    }

    public void removeConnectionEventListener(ConnectionEventListener listener) {
        // TODO Auto-generated method stub

    }

    public void addStatementEventListener(StatementEventListener listener) {
        // TODO Auto-generated method stub

    }

    public void removeStatementEventListener(StatementEventListener listener) {
        // TODO Auto-generated method stub

    }


}

