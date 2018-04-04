package com.test;

import java.util.List;

import com.dao.BookDao;
import com.entiy.Book;
import com.lhb.mvc.annotation.Controller;
import com.lhb.mvc.annotation.RequestMapping;
import com.lhb.mvc.annotation.ResponseBody;
import com.lhb.orm.util.JdbcLhbTemplate;

@Controller
@RequestMapping("/test")
public class TestController {

	private BookDao dao = JdbcLhbTemplate.executeProxyCgbli(BookDao.class);

	@RequestMapping("test.do")
	@ResponseBody
	public List<Book> tets() {
		Book book = new Book();
		book.setBook_name("我的测试数据68");
		List<Book> list = dao.getList(book);
		return list;
	}

}
