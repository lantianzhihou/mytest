package com.wangbo.test.util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wangbo.test.util.JsonUtil;

public class JacksonTest {
    // Aa、Bb、Cc、Dd、Ee、Ff、Gg、Hh、Ii、Jj、Kk、Ll、Mm、Nn、Oo、Pp、Qq、Rr、Ss、Tt、Uu、Vv、Ww、Xx、Yy、Zz
	public static void main(String[] args) {
        // Collator instance = Collator.getInstance(Locale.CHINA);
        // Set<String> strSet = new TreeSet<>((str1, str2) -> {
        // int result = instance.compare(str1, str2);
        // System.out.println(str1 + "-" + str2 + "===result:" + result);
        // return result;
        // });
        // strSet = new HashSet<>();
        // strSet.add("啊");
        // strSet.add("比");
        // strSet.add("散");
        // strSet.add("死");
        // strSet.add("时");
        // strSet.add("中");
        // System.out.println(strSet);
        String abc = null;
        System.out.println(Pattern.matches("(.*)nihao(.*)", abc));
	}
	
	@Test
	public void constructJsonNode() throws IOException {
		ObjectNode resultNode = JsonNodeFactory.instance.objectNode();
		resultNode.put("alarmSource", "cloudwalk");
		resultNode.put("alarmType", "label_person");
		resultNode.put("threshold", 12.7F);
		resultNode.put("score", 156L);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ArrayNode arrNode = JsonNodeFactory.instance.arrayNode();
		SystemInfo systemInfo = new SystemInfo();
		systemInfo.setId(2);
		systemInfo.setIp("192.12.5.9");
		systemInfo.setPassword("asda");
		systemInfo.setBooleanNum(true);
		systemInfo.setFloatNum(12.7F);
		systemInfo.setLongNum(1200L);
		arrNode.add(objectMapper.readTree(objectMapper.writeValueAsString(systemInfo)));
		
		resultNode.set("list", arrNode);
		System.out.println(objectMapper.writeValueAsString(resultNode));
		System.out.println(JsonUtil.getValue(resultNode.get("list").get(0).get("floatNum"), Float.class));
		System.out.println(JsonUtil.getValue(resultNode.get("list").get(0).get("booleanNum"), Boolean.class));
		System.out.println(JsonUtil.getValue(resultNode.get("list").get(0).get("longNum"), Long.class));
	}
	
	@Test
	public void jsonToMap1() throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("car", "1231A");
		map.put("person", "175");
		ConnectParam connectParam = new ConnectParam();
		connectParam.setAction("replace");
		connectParam.setRequest(map);
		
		SystemInfo systemInfo = new SystemInfo();
		systemInfo.setId(2);
		systemInfo.setIp("192.12.5.9");
		systemInfo.setPassword("asda");
		
		WaitingConnectInfo connectInfo = new WaitingConnectInfo();
		connectInfo.setParam(connectParam);
		connectInfo.setSystemInfo(systemInfo);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(connectInfo);
		System.out.println(json);
		
		Map<?, ?> mapValue = mapper.readValue(json, Map.class);
		System.out.println(mapValue);
	}
	
	@Test
	public void jsonToMap2() throws JsonParseException, JsonMappingException, IOException {
		SystemInfo systemInfo1 = new SystemInfo();
		systemInfo1.setId(1);
		systemInfo1.setIp("192.12.5.9");
		systemInfo1.setPassword("asda");
		
		SystemInfo systemInfo2 = new SystemInfo();
		systemInfo2.setId(2);
		systemInfo2.setIp("192.12.5.2");
		systemInfo2.setPassword("asda2");
		Set<SystemInfo> set = new HashSet<SystemInfo>();
		set.add(systemInfo1);
		set.add(systemInfo2);
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "person");
		map.put("data", set);
		String json = mapper.writeValueAsString(map);
		System.out.println(json);
		
		Map<?, ?> mapValue = mapper.readValue(json, Map.class);
		@SuppressWarnings("unchecked")
		List<Object> list = (ArrayList<Object>)mapValue.get("data");
		System.out.println(mapper.writeValueAsString(list));
	}
	
	@Test
	public void foreTest() {
		int[] arr = new int[]{1,2,3,4,4};
		
		for (int i : arr) {
			
			System.out.println(arr[i - 1]);
			if (i == 2) {
				continue;
			}
			System.out.println("====");
		}
	}
	
	@Test
	public void testJsonNode() throws IOException {
		SystemInfo systemInfo1 = new SystemInfo();
		systemInfo1.setId(1);
		systemInfo1.setIp("192.12.5.9");
		systemInfo1.setPassword("asda");
		
		SystemInfo systemInfo2 = new SystemInfo();
		systemInfo2.setId(2);
		systemInfo2.setIp("192.12.5.2");
		systemInfo2.setPassword("asda2");
		Set<SystemInfo> set = new HashSet<SystemInfo>();
		set.add(systemInfo1);
		set.add(systemInfo2);
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", "person");
		map.put("data", set);
		String json = mapper.writeValueAsString(map);
		
		JsonNode jsonNode = mapper.readTree(json);
		System.out.println(jsonNode);
		List<JsonNode> parents = jsonNode.findParents("id");
		//[{"id":1,"sysType":null,"sysName":null,"ip":"192.12.5.9","port":null,"path":null,"account":null,"password":"asda"}, 
		//{"id":2,"sysType":null,"sysName":null,"ip":"192.12.5.2","port":null,"path":null,"account":null,"password":"asda2"}]
		System.out.println(parents);
		
		JsonNode jsonNode2 = jsonNode.get("data");
		List<String> ids = jsonNode2.findValuesAsText("id");
		System.out.println(ids);
		//[1, 2]
		// ARRAY,BINARY, BOOLEAN, MISSING, NULL, NUMBER,OBJECT, POJO,STRING
		System.out.println(jsonNode2.getNodeType());
		//ARRAY
		
		
		JsonNode jsonNode3 = jsonNode2.get(1);
		System.out.println(jsonNode3);
		//{"id":2,"sysType":null,"sysName":null,"ip":"192.12.5.2","port":null,"path":null,"account":null,"password":"asda2"}
		String id = jsonNode3.get("id").asText("3");
		System.out.println(id);
		
		if (jsonNode3.isObject()) {
			//object
			ObjectNode objectNode = (ObjectNode)jsonNode3;
			System.out.println(objectNode.remove("id").asText());
			objectNode.put("key", "1231");
			
			System.out.println(objectNode.toString());
		}
	}
}
