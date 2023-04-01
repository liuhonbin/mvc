package com.lhb.pool.pool;

import java.sql.SQLException;
import java.sql.Wrapper;

public class PoolableWrapper implements Wrapper {

    private final Wrapper wrapper;

    public PoolableWrapper(Wrapper wraaper) {
        this.wrapper = wraaper;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {

        if (null == wrapper) {
            //Best to log error.
            return false;
        }

        if (iface == null) {
            return false;
        }

        if (iface == wrapper.getClass()) {
            return true;
        }

        if (iface == this.getClass()) {
            return true;
        }

        if (!(wrapper instanceof WrapperProxy)) {
            if (iface.isInstance(wrapper)) {
                return true;
            }
        }

        return wrapper.isWrapperFor(iface);
    }

    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {

        if (null == wrapper) {
            //Best to log error.
            return null;
        }

        if (iface == null) {
            return null;
        }

        if (iface == wrapper.getClass()) {
            return (T) wrapper;
        }

        if (iface == this.getClass()) {
            return (T) this;
        }

        if (!(wrapper instanceof WrapperProxy)) {
            if (iface.isInstance(wrapper)) {
                return (T) wrapper;
            }
        }


        return wrapper.unwrap(iface);
    }


}
