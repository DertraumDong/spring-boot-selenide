package com.htw.springbootselenide.dev;

import com.alibaba.fastjson.JSONObject;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.htw.springbootselenide.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class userLoginService {
    private Logger LOGGER = LoggerFactory.getLogger(userLoginService.class);

    // 配置信息
    String url = "";
    String name = "";
    String password = "";
    String clientName = "";

    // 工作单数量范围
    int billMaxNum = 100;
    int billMinNum = 100;

    // 应收费用数量
    int receivableCostMaxNum = 2;
    int receivableCostMinNum = 1;
    // 应付费用数量
    int payCostMaxNum = 2;
    int payCostMinNum = 1;

    String dev = "test3";

    /**
     * 初始化配置
     * @throws Exception ex
     */
    private void loadInfo() throws Exception {
        String jsonStr = JsonUtil.readJsonFile("D:\\IdeaProjects\\spring-boot-selenide\\src\\main\\resources\\name.json");
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONObject jo = (JSONObject) jsonObject.get(dev);
        name = (String) jo.get("name");
        password = (String) jo.get("password");
        url = (String) jo.get("url");
        clientName = (String) jo.get("clientName");
        if(StringUtils.isBlank(name) || StringUtils.isBlank(password) || StringUtils.isBlank(url) || StringUtils.isBlank(clientName)){
            throw new Exception("配置为空");
        }
    }

    @Test
    public void userCanLoginByUsername() throws Exception {
        StopWatch stopWatch = new StopWatch("批量生成工作单");
        int createNum = 0 ;
        try {
            stopWatch.start("读取配置");
            // 配置
            Configuration.browser = "chrome";
            Configuration.holdBrowserOpen = true;
            System.setProperty("webdriver.chrome.driver", "D:\\IdeaProjects\\spring-boot-selenide\\src\\main\\resources\\chromedriver.exe");
            this.loadInfo();
            stopWatch.stop();
            stopWatch.start("开启网站，登录");
            open(url);
            // 登录
            Selenide.Wait().until(visibilityOfElementLocated(By.id("from_submit")));
            $(By.name("userName")).setValue(name);
            $(By.name("password")).setValue(password);
            $(By.id("login")).click();
            LOGGER.info("登录成功");
            sleep(2000);
            stopWatch.stop();
            stopWatch.start("生成工作单");
            // 选择菜单
            $(By.xpath("//*[@id=\"MainNav\"]/div[5]/div[1]/a")).click();
            $(By.xpath("//*[@id=\"MainNav\"]/div[5]/div[2]/div[1]/div[1]/a")).click();
            $(By.xpath("//*[@id=\"MainNav\"]/div[5]/div[2]/div[1]/div[2]/div[1]/div")).click();
            LOGGER.info("选择菜单-海运工作单");
            int size = random(billMaxNum,billMinNum);
            LOGGER.info("欲生成{}个工作单",size);
            for(int i = 0 ; i < size ; i++){
                String str = "第"+(i+1)+"个工作单";
                StopWatch sw = new StopWatch(str);
                sw.start("生成……");
                $(By.xpath("//*[@id=\"iframeWrap\"]/div[2]/iframe")).waitUntil(Condition.exist,2000).click();
                String iframe = $(By.xpath("//*[@id=\"iframeWrap\"]/div[2]/iframe")).getAttribute("name");
                Selenide.switchTo().frame(iframe);
                LOGGER.info("{},切换创建工作单页面",str);
                $(By.xpath("//*[@id=\"subList_create\"]")).click();
                sleep(1000);
                switchTo().defaultContent();
                String iframe1 = $(By.xpath("//*[@id=\"iframeWrap\"]/div[3]/iframe")).getAttribute("name");
                Selenide.switchTo().frame(iframe1);

                sw.stop();
                sw.start("填写工作单资料");

                $(By.xpath("//*[@id=\"seaBasicForm\"]/div[2]/div[1]/div[1]/div/label/input[2]")).setValue(clientName);
                $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist,2000);
                sleep(500);
                $(By.xpath("/html/body/div[2]")).click();

                $(By.xpath("//*[@id=\"seaBasicForm\"]/div[2]/div[1]/div[5]/div/div/label/input[2]")).click();
                $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist,2000);
                $(By.xpath("/html/body/div[2]")).click();

                $(By.xpath("//*[@id=\"seaBasicForm\"]/div[3]/div[1]/div[2]/div/div[3]/div/div[1]/label/input[2]")).click();
                $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist,2000);
                $(By.xpath("/html/body/div[2]")).click();

                $(By.xpath("//*[@id=\"seaBasicForm\"]/div[3]/div[1]/div[2]/div/div[4]/div/div[1]/label/input[2]")).click();
                $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist,2000);
                $(By.xpath("/html/body/div[2]")).click();
                // ETD
                $(By.xpath("//*[@id=\"seaBasicForm\"]/div[3]/div[1]/div[2]/div/div[9]/div[1]/input")).click();
                $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist,2000);
                $(By.xpath("//*[@id=\"layui-laydate3\"]/div[2]/div/span[2]")).click();

                $(By.xpath("//*[@id=\"seaBasicForm\"]/div[2]/div[1]/div[18]/div/div/div/input")).click();
                $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist,2000);
                $(By.xpath("/html/body/div[2]")).click();


                sleep(500);
                $(By.xpath("//*[@id=\"seaBasicForm\"]/div[1]/div/div[3]")).click();
                LOGGER.info("{},单据保存",str);

                sleep(500);
                switchTo().defaultContent();
                $(By.xpath("//*[@id=\"serialNumberSelect\"]")).should(exist);
                $(By.xpath("//*[@id=\"serialNumbers\"]/li[2]/a")).waitUntil(Condition.exist,2000).click();
                LOGGER.info("{},选择编码成功",str);

                sw.stop();
                sw.start("生成费用");

                $(By.xpath("//*[@id=\"iframeWrap\"]/div[3]/iframe")).waitUntil(Condition.exist,2000).click();
                String iframeEdit = $(By.xpath("//*[@id=\"iframeWrap\"]/div[3]/iframe")).getAttribute("name");
                Selenide.switchTo().frame(iframeEdit);
                LOGGER.info("{},跳转工作单成功！",str);

                sleep(500);
                $(By.xpath("//*[@id=\"tabTitle\"]/li[6]")).click();
                LOGGER.info("{},跳转费用管理！",str);

                // 应收
                sleep(500);
                // 费用
                int costNum = random(receivableCostMaxNum,receivableCostMinNum);
                LOGGER.info("{},生成应收费用数量：{}",str,costNum);
                for (int j = 0; j < costNum; j++) {
                    $(By.xpath("//*[@id=\"receivableTable\"]/div[1]/div/table/thead/tr/th[2]/div/i")).click();
                    String tr = j==0?"tr":"tr["+(j+1)+"]";
                    $(By.xpath("//*[@id=\"receivableTable\"]/div[2]/table/tbody/"+tr+"/td[6]/div/label/input[2]")).click();
                    $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist,2000);
                    $(By.xpath("/html/body/div[2]")).click();
                    $(By.xpath("//*[@id=\"receivableTable\"]/div[2]/table/tbody/"+tr+"/td[8]/div/input")).setValue(buildMoney(600,200));
                }
                sleep(500);

                // 应付
                costNum = random(payCostMaxNum,payCostMinNum);
                LOGGER.info("{},生成应付费用数量：{}",str,costNum);
                for (int j = 0; j < costNum; j++) {
                    $(By.xpath("//*[@id=\"payableTable\"]/div[1]/div/table/thead/tr/th[2]/div/i")).click();
                    sleep(500);
                    String tr = j==0?"tr":"tr["+(j+1)+"]";
                    // 结算对象
                    $(By.xpath("//*[@id=\"payableTable\"]/div[2]/table/tbody/"+tr+"/td[4]/div/label/input[2]")).setValue(clientName);
                    sleep(500);
                    $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist,2000);
                    $(By.xpath("/html/body/div[2]")).click();
                    // 费用
                    $(By.xpath("//*[@id=\"payableTable\"]/div[2]/table/tbody/"+tr+"/td[5]/div/label/input[2]")).click();
                    $(By.xpath("/html/body/div[2]")).waitUntil(Condition.exist, 2000);
                    $(By.xpath("/html/body/div[2]")).click();
                    $(By.xpath("//*[@id=\"payableTable\"]/div[2]/table/tbody/"+tr+"/td[7]/div/input")).setValue(buildMoney(300, 100));
                }
                // 保存费用操作
                $(By.xpath("//*[@id=\"saveBtn\"]")).click();
                LOGGER.info("{},保存费用成功",str);
                sleep(2000);

                switchTo().defaultContent();
                LOGGER.info("{},回到主页面",str);
                $(By.xpath("//*[@id=\"navbarTab\"]/li[3]/i")).click();
                LOGGER.info("{},关闭页签",str);
                sw.stop();
                LOGGER.info(sw.prettyPrint());
                createNum++;
            }
            stopWatch.stop();
            stopWatch.start("结束");

        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage());
        }finally {
            LOGGER.info("批量创建工作单完成，已创建数量: {} 个",createNum);
            LOGGER.info("完成时间：{} 秒",stopWatch.getTotalTimeSeconds());
            LOGGER.info(stopWatch.prettyPrint());

        }
    }

    /**
     * 生成随机费用
     * @param max 最大值
     * @param min 最小值
     * @return 费用
     */
    private static String buildMoney(int max,int min){
        int num = (int) (Math.random()*(max-min)+min);
        return String.valueOf(num);
    }

    /**
     * 生成随机数字
     * @param max 最大值
     * @param min 最小值
     * @return 随机数
     */
    private static int random(int max,int min){
        return (int) (Math.random()*(max-min)+min);
    }

}
