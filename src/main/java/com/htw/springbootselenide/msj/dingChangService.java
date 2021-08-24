package com.htw.springbootselenide.msj;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class dingChangService {
    private Logger LOGGER = LoggerFactory.getLogger(dingChangService.class);

    @Test
    public void userCanLoginByUsername(){
        Configuration.browser = "chrome";
        Configuration.holdBrowserOpen = true;

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin-liu\\Desktop\\报表sql\\chromedriver.exe");

        sleep(2000);
        $(By.xpath("//*[@id=\"coiPage-1\"]/div[2]/button[3]")).should(Condition.exist);
        $(By.xpath("//*[@id=\"coiPage-1\"]/div[2]/button[3]")).click();
        sleep(1000);

        sleep(1000);

        sleep(1000);
        $("#login-form").find("button").click();
        sleep(1000);
        LOGGER.info("登录");
        // 遇到验证码需要手动点击
        LOGGER.info("等待输入验证码");
        $(By.xpath("/html/body/div[3]/div[2]/button")).waitUntil(Condition.exist,60000).click();
        $(By.xpath("//*[@id=\"ign-header\"]/div[2]/div[1]/div/a")).click();
        LOGGER.info("进入价格页面");
        sleep(1000);
        $(By.xpath("//*[@id=\"main\"]/div/div[3]/div/div/div/div[1]/div/div[3]/a")).click();
        LOGGER.info("进入即时价格页面");

    }
}
