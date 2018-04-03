package com.lhb.orm.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lhb.orm.base.BaseEntity;


/**
 * project url:https://github.com/liuhonbin/ORM
 * 
 * @author 26391
 *
 */
public class SqlUtil {

	/**
	 * sql 语句参数处理 #{XXXX}方式处理
	 * 
	 * @param sql
	 * @param baseEntity
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static String sqlPrament(String sql, BaseEntity baseEntity) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String str = null;
		if (baseEntity != null) {
			// 将传参对象转变为map集合
			Map<String, Object> p = baseEntity.conditionParam();
			for (Entry<String, Object> entry : p.entrySet()) {
				String key = entry.getKey();
				Pattern pt = Pattern.compile("#\\{\\s*" + key + "\\s*\\}");
				Matcher m = pt.matcher(sql);
				str = m.replaceAll("'" + p.get(key).toString() + "'");
				sql = str;
			}
		} else {
			return sql;
		}
		return str;
	}

}
