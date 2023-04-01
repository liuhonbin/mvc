package com.lhb.orm.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lhb.orm.base.BaseEntity;

/**
 * project url:https://github.com/liuhonbin/ORM
 *
 * @author 26391
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
    public static String sqlPrament(String sql, Object obj) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str = null;
        if (obj != null) {
            // 将传参对象转变为map集合
            Map<String, String> p = obj2Map(obj);
            for (Entry<String, String> entry : p.entrySet()) {
                String key = entry.getKey();
                Pattern pt = Pattern.compile("#\\{\\s*" + key + "\\s*\\}");
                Matcher m = pt.matcher(sql);
                str = m.replaceAll("'" + p.get(key) + "'");
                sql = str;
            }
        } else {
            return sql;
        }
        return str;
    }

    private static Map<String, String> obj2Map(Object obj) {
        Map<String, String> map = new HashMap<String, String>();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            varName = varName.toLowerCase();// 将key置为小写，默认为对象的属性
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null)
                    map.put(varName, o.toString());
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

}
