package com.lhb.mvc.core.converter;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;

public class HttpMessageConverter implements Converter<Object, Object> {

	@Override
	public Object convert(Object source) {
		if(source == null) {
		   return null;
		}
		if(source.getClass().getName().equals("java.util.ArrayList")) {
			return JSONArray.toJSON(source);
		}else if(source.getClass().getName().equals("java.lang.String")) {
			return source;
		}
		return source;
	}

	public static void main(String[] args) {
//		List<String> list = new ArrayList<String>();
//		Object o = list;
//		System.out.println(o.getClass().getName());
		
		String baseUrl = "/test";
		String url = "test.do";
		System.out.println(url = (baseUrl + "/" + url).replaceAll("(/\\*/)|(/+)", "/"));
	}
}
