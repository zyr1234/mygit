package com.geovis.jg.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

	static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		mapper.enable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);
		
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

		// mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,
		// true);

		
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	public static <T> List<T> deserializeList(String json, Class<T> type) {
		if (json != null && !json.isEmpty()) {
			try {

				// JavaType
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, type);

				List<T> list = mapper.readValue(json, javaType);

				return list;
			} catch (IOException e) {
				LoggerFactory.getLogger("default").error(e.getMessage());
			}

		}

		return null;
	}

	public static <T> T deserialize(String json, Class<T> type) {
		if (json == null)
			return null;

		try {

			T t = mapper.readValue(json,type);

			return t;
		} catch (IOException e) {
			LoggerFactory.getLogger("default").error(e.getMessage());
		}

		return null;
	}
	
	
//	public static <T>  Result<T>  deserialize1(String json, Class<T> type) {
//		Result<T> result=new Result<T>();
//		
//		if (json == null)
//			{
//				result.setUnSuccess("反序列化对象为空");
//				return result;
//			}
//
//		try {
//
//			T t = mapper.readValue(json,type);
//
//			result.setSuccess(t);
//
//			return result;
//		} catch (IOException e) {
//			LoggerFactory.getLogger("default").error(e.getMessage());
//			
//			result.setUnSuccess(e.getMessage());
//		}
//
//		return null;
//	}
	
	
	public static <T> T deserialize(String json, TypeReference<?> type) {
		if (json == null)
			return null;

		try {

			T t = mapper.readValue(json,type);

			return t;
		} catch (IOException e) {
			LoggerFactory.getLogger("default").error(e.getMessage());
		}

		return null;
	}
	
	
	public static ObjectMapper getObjectMapper()
	{
		return mapper;
	}
	
	public static <T> T convertObject(Object data, TypeReference<?> type)
	{
		T t=mapper.convertValue(data, type);
		return t;
	}

	
	public static <T> String serialize(List<T> list) {
		if (null == list || list.size() == 0)
			return null;

		try {
			String str = mapper.writeValueAsString(list);
			return str;
		} catch (JsonProcessingException e) {
			LoggerFactory.getLogger("default").error(e.getMessage());
		}
		return "";
	}

	public static <T> String serialize(T t) {
		if (t == null)
			return null;

		try {
			String str = mapper.writeValueAsString(t);

			return str;
		} catch (JsonProcessingException e) {
			LoggerFactory.getLogger("default").error(e.getMessage());
		}

		return "";
	}

}

