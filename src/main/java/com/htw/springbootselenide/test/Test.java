package com.htw.springbootselenide.test;

import com.alibaba.fastjson.JSONObject;
import com.htw.springbootselenide.util.JsonUtil;

import java.util.Random;

public class Test {
    private static String buildMoney(){
//        Random r = new Random(1);
//        int num = r.nextInt(100);
        int max=200,min=50;
        int num = (int) (Math.random()*(max-min)+min);
        return String.valueOf(num);
    }

    public static void main(String[] args) {
        String jsonStr = JsonUtil.readJsonFile("D:\\IdeaProjects\\spring-boot-selenide\\src\\main\\resources\\name.json");
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONObject jo = (JSONObject) jsonObject.get("test2");
        String name = (String) jo.get("name");
        String password = (String) jo.get("password");
        String url = (String) jo.get("url");
        String clientName = (String) jo.get("clientName");
        System.out.println(name + " " +password + " " + url + " " + clientName);
    }
}
