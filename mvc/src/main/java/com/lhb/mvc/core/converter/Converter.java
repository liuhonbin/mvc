package com.lhb.mvc.core.converter;

public interface Converter<S, T> {

	T convert(S source);

}