import com.google.common.collect.ImmutableMap;
import com.thoughtworks.gauge.Step;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class StepImplementation extends BaseTest {

    FluentWait<WebDriver> wait;
    JavascriptExecutor jsDriver;
    Logger logger = LogManager.getLogger(StepImplementation.class);

    public StepImplementation() {

        wait = new FluentWait<>(appiumDriver);
        wait.withTimeout(Duration.ofSeconds(30)
                ).pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);
        jsDriver = (JavascriptExecutor) appiumDriver;
    }


    @Step("<time> saniye kadar bekle")
    public void waitForseconds(int time) throws InterruptedException {
        Thread.sleep(time * 1000);
    }

    @Step("id <id> li elemente tıkla")
    public void clickByid(String id) {
        appiumDriver.findElement(By.id(id)).click();
        logger.info("" + id + "' id'li elemente basarili bir sekilde tiklandi");
    }

    @Step("xpath <xpath> li elemente tıkla")
    public void clickByXpath(String xpath) {
        appiumDriver.findElement(By.xpath(xpath)).click();
        logger.info("" + xpath + "' xpath'li elemente basarili bir sekilde tiklandi");
    }


    private boolean isElementVisibleById(String id) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            return true;
        } catch (Exception e) {
                        return false;
        }
    }

    @Step("id <id> li element görünür durumda")
    public void verifyElementsById(String id) {
        Assert.assertTrue("Element görüntülendi", isElementVisibleById(id));
        logger.info("" + id + "' id'li element basarili bir sekilde goruntulendi");
    }


    @Step("id <id> li ementi bul ve <text> değerini yaz")
    public void sendkeysByid(String id, String text) {
        appiumDriver.findElement(By.id(id)).sendKeys(text);
        logger.info("' "+id+" '  id'li inputa  '"+text+"' degeri girildi");
    }

    @Step("Android klavyeyi kapat")
    public void closeKeyboardonAndroid() {
        appiumDriver.hideKeyboard();

    }

    @Step("Sayfayı sola kaydır")
    public void swipeLeft() {
        final int PRESS_TIME = 200; // ms

        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;
        // init screen variables
        Dimension dims = appiumDriver.manage().window().getSize();
        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
        new TouchAction(appiumDriver)
                .press(pointOptionStart)
                // a bit more reliable when we add small wait
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                .moveTo(pointOptionEnd)
                .release().perform();
        logger.info("Sayfa sola kaydirildi");

    }

    @Step("id <id> li elementi bul ve <text> alanını kontrol et")
    public void textAreacontrolById(String id, String text) {
        Assert.assertTrue("Element text değerini içermiyor", appiumDriver.findElement(By.id(id)).getText().contains(text));
        //System.out.println("gelen elementin yazsi: "+appiumDriver.findElement(By.id(id)).getText());
        logger.info("Alınan text '"+text+"' değerini içeriyor");
    }


    @Step("xpath <xpath> li elementi bul ve <text> alanını kontrol et")
    public void textAreacontrolByXpath(String xpath, String text) {
        Assert.assertTrue("Element text değerini içermiyor", appiumDriver.findElement(By.xpath(xpath)).getText().contains(text));
        logger.info("Alinan text '"+text+"' degerini iceriyor");
    }


    @Step("Android klavyeden arama tuşuna bas")
    public void clickSearchbuttonOnadnroidKeyboard() {
        ((JavascriptExecutor) appiumDriver).executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
    }

    @Step("Elementler <xpath> arasından rasgele bir tanesini seç ve tıkla")
    public void clickRandomElement(String xpath) {
        Random random = new Random();
        List<MobileElement> products = appiumDriver.findElements(By.xpath(xpath));
        int index = random.nextInt(products.size());
        products.get(index).click();
        logger.info("Urun secimi yapildi");
    }

    @Step("Sayfayı aşağı kaydır")
    public void swipeToBottom() {
        Dimension dim = appiumDriver.manage().window().getSize();
        int x_top = (int) (dim.width * 0.5);
        int y_top = (int) (dim.height * 0.8);
        int x_bottom = (int) (dim.height * 0.5);
        int y_bottom = (int) (dim.height * 0.2);

        TouchAction ts = new TouchAction(appiumDriver);
        ts.press(PointOption.point(x_top, y_top))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(x_bottom, y_bottom)).release().perform();
        logger.info("Sayfa asagi kaydirildi");
    }

}
