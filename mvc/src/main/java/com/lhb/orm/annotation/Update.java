package com.lhb.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // ע�����class�ֽ����ļ��д��ڣ�������ʱ����ͨ�������ȡ��
@Target({ElementType.METHOD})//����ע�������Ŀ��**���÷�Χ�ֶΡ�ö�ٵĳ���/����
@Documented//˵����ע�⽫��������javadoc��
public @interface Update {
   
	abstract String sql() default ""; 
	
}
