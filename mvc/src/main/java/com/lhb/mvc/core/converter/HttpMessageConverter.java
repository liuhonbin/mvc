package com.lhb.mvc.core.converter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;

public class HttpMessageConverter {

	public Object convert(Object source, HttpServletResponse response) {
		if (source == null) {
			return null;
		}
		if (source.getClass().getName().equals("java.util.ArrayList")) {
			response.addHeader("charset", "UTF-8");
			response.addHeader("ContentType", "application/json");
			return JSONArray.toJSON(source);
		} else if (source.getClass().getName().equals("java.lang.String")) {
			return source;
		}
		return source;
	}


	public static void main(String[] args) {
		// List<String> list = new ArrayList<String>();
		// Object o = list;
		// System.out.println(o.getClass().getName());
//
//		String baseUrl = "/test";
//		String url = "test.do";
//		System.out.println(url = (baseUrl + "/" + url).replaceAll("(/\\*/)|(/+)", "/"));
		System.out.println((byte)127);
	}
}
