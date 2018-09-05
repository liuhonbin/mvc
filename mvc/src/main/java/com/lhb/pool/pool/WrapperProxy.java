package com.lhb.pool.pool;

import java.sql.Wrapper;
import java.util.Map;

public interface WrapperProxy extends Wrapper {

	long getId();

	Object getRawObject();

	int getAttributesSize();

	void clearAttributes();

	Map<String, Object> getAttributes();

	Object getAttribute(String key);

	void putAttribute(String key, Object value);
}
