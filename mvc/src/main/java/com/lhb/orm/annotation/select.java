package com.lhb.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface select {

    abstract String sql() default "";

    abstract Class<?> return_type();

    String dataSourceName() default "datasource";

    String type() default "SELECT";
}
