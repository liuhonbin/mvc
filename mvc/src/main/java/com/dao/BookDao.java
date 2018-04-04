package com.dao;

import java.util.List;

import com.entiy.Book;
import com.lhb.orm.annotation.Sql;
import com.lhb.orm.base.BaseEntity;

public interface BookDao {

	List<Book> getList(String sql, Class<?> clazz, BaseEntity baseEntity);

	@Sql(sql = "select * from book ", return_type = Book.class)
	List<Book> getList(BaseEntity baseEntity);
}
