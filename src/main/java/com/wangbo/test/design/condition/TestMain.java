package com.wangbo.test.design.condition;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMain {
    public static void main(String[] args) throws JsonProcessingException {
        SearchImageParam param = new SearchImageParam();
        param.setBeginTime(new Date());
        param.setEndTime(new Date());
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(param));
    }
}
