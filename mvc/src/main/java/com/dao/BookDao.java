package com.dao;

import java.util.List;

import com.entiy.Book;
import com.lhb.orm.annotation.insert;
import com.lhb.orm.annotation.select;
import com.lhb.orm.base.BaseEntity;

public interface BookDao {

    List<Book> getList(String sql, Class<?> clazz, BaseEntity baseEntity);

    @select(sql = "select * from book ", return_type = Book.class)
    List<Book> getList(BaseEntity baseEntity);

    @insert(sql = "insert into book(book_id,book_name) value(#{book_id},#{book_name}) ")
    int add(Book book);
}
