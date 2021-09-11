package com.htw.springbootselenide.test;

import com.alibaba.fastjson.JSONObject;
import com.htw.springbootselenide.util.JsonUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

public class Test {
    private static String buildMoney(){
//        Random r = new Random(1);
//        int num = r.nextInt(100);
        int max=200,min=50;
        int num = (int) (Math.random()*(max-min)+min);
        return String.valueOf(num);
    }

//    public static void main(String[] args) {
//        String jsonStr = JsonUtil.readJsonFile("D:\\IdeaProjects\\spring-boot-selenide\\src\\main\\resources\\name.json");
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        JSONObject jo = (JSONObject) jsonObject.get("test2");
//        String name = (String) jo.get("name");
//        String password = (String) jo.get("password");
//        String url = (String) jo.get("url");
//        String clientName = (String) jo.get("clientName");
//        System.out.println(name + " " +password + " " + url + " " + clientName);
//    }

    public static void main(String[] args) {
        double d1 = 8906665749333336.12;
        System.out.println(d1);
        DecimalFormat fmt = new DecimalFormat("###############0.00");
        System.out.println(fmt.format(d1));
        double d2 = 49333336.12;
        System.out.println(d2);
        double d3 = 9333336.12;
        System.out.println(d3);

        System.out.println("-------------------------");

        Double d11 = 8906665749333336.12;
        System.out.println(d11);
        DecimalFormat fmt1 = new DecimalFormat("###############0.00");
        System.out.println(fmt1.format(d11));
        Double d22 = 49333336.00;
        System.out.println(d22);
        Double d33 = 9333336.00;
        System.out.println(d33);

        System.out.println("----------------------------");

        BigDecimal bigDecimal = new BigDecimal("8906665749333336.12");
        System.out.println(bigDecimal.toString());
    }
}
