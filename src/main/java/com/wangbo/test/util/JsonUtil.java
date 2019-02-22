package com.wangbo.test.util;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonUtil {

	@SuppressWarnings("unchecked")
	public static <T> T getValue(JsonNode jsonNode, Class<T> clazz) {
		Assert.notNull(clazz, "clazz值不能为null");
		T t = null;
		switch (clazz.getCanonicalName()) {
		case "java.lang.Integer":
			t = (T) (jsonNode != null && !jsonNode.isNull() && jsonNode.canConvertToInt() ? jsonNode.asInt() : null);
			break;
		case "java.lang.Long":
			t = (T) (jsonNode != null && !jsonNode.isNull() && jsonNode.canConvertToLong() ? jsonNode.asLong() : null);
			break;
		case "java.lang.String":
			t = (T) (jsonNode != null && !jsonNode.isNull() ? jsonNode.asText() : null);
			if (StringUtils.isEmpty(t) || "null".equals(t)) {
				t = null;
			}
			break;
		case "java.lang.Float":
			t = (T) (jsonNode != null && !jsonNode.isNull() && jsonNode.isDouble()  ? jsonNode.floatValue() : null);
			break;
		case "java.lang.Double":
			t = (T) (jsonNode != null && !jsonNode.isNull() && jsonNode.isDouble() ? jsonNode.asDouble() : null);
			break;
		case "java.lang.Boolean":
			t = (T) (jsonNode != null && !jsonNode.isNull() && jsonNode.isBoolean() ? jsonNode.asBoolean() : null);
			break;

		default:
			break;
		}
		return t;
	}
}
