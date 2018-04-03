package com.test;

import com.mvc.annotation.Controller;
import com.mvc.annotation.RequestMapping;
import com.mvc.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("test.do")
	@ResponseBody
	public String tets() {
		return "../index.jsp";
	}

	@RequestMapping("test1do")
	@ResponseBody
	public String tets1() {
		return "../index.jsp";
	}

	@RequestMapping("test2.do")
	@ResponseBody
	public String tets2() {
		return "../index.jsp";
	}

	@RequestMapping("test3.do")
	@ResponseBody
	public String tets3() {
		return "../index.jsp";
	}

	@RequestMapping("test4.do")
	@ResponseBody
	public String tets4() {
		return "../index.jsp";
	}
}
