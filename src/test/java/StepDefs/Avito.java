package StepDefs;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Avito {
    WebDriver driver;

    @Step("открыт ресурс авито")
    @Пусть("открыт ресурс авито")
    public void открытРесурсАвито() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://avito.ru");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @ParameterType(".*")
    public Category parseCategory(String category) {
        category = category.replace(" ", "_");
        category = category.replace(",", "");
        return Category.valueOf(category);
    }

    @Step("в выпадающем списке категорий выбрана {category}")
    @И("в выпадающем списке категорий выбрана {parseCategory}")
    public void вВыпадающемСпискеКатегорийВыбранаОртехника(Category category) {
        By by = By.id("category");
        Select categories = new Select(driver.findElement(by));
        categories.selectByVisibleText(category.getCategory());

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @Step("в поле поиска введено значение {product}")
    @И("^в поле поиска введено значение ([А-я]+)$")
    public void вПолеПоискаВведеноЗначениеПринтер(String product) {
        By by = By.xpath("//input[@data-marker=\"search-form/suggest\"]");
        WebElement search = driver.findElement(by);
        search.sendKeys(product);

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @Step("кликнуть по выпадающему списку региона")
    @Тогда("кликнуть по выпадающему списку региона")
    public void кликнутьПоВыпадающемуСпискуРегиона() {
        By by = By.xpath("//div[@data-marker=\"search-form/region\"]");
        WebElement city = driver.findElement(by);
        city.click();

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @Step("в поле регион введено значение {city}")
    @Тогда("^в поле регион введено значение ([А-я]+)$")
    public void вПолеРегионВведеноЗначениеВладивосток(String city) {
        By by = By.xpath("//input[@placeholder=\"Город, регион или Россия\"]");
        WebElement cityFind = driver.findElement(by);
        cityFind.sendKeys(city);

        by = By.xpath("//strong");
        List<WebElement> citySelect = driver.findElements(by);
        citySelect.get(0).click();

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @Step("нажата кнопка показать объявления")
    @И("нажата кнопка показать объявления")
    public void нажатаКнопкаПоказатьОбъявления() {
        By by = By.xpath("//button[@data-marker=\"popup-location/save-button\"]");
        WebElement btnFind = driver.findElement(by);
        btnFind.click();

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @Step("открылась страница результаты по запросу {product}")
    @Тогда("^открылась страница результаты по запросу ([А-я]+)$")
    public void открыласьСтраницаРезультатыПоЗапросуПринтер(String product) {
        By by = By.cssSelector("[data-marker=\"page-title/text\"]");
        WebElement searchQuery = driver.findElement(by);
        assert searchQuery.getText().contains("«" + product + "»");

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @Step("активирован чекбокс только с фотографией")
    @И("активирован чекбокс только с фотографией")
    public void активированЧекбоксТолькоСФотографией() {
        By by = By.xpath("//input[@data-marker=\"search-form/with-images\"]");
        WebElement chkBox = driver.findElement(by);
        if (!chkBox.isSelected() && chkBox.isEnabled()) {
            chkBox.sendKeys(Keys.SPACE);
            chkBox.sendKeys(Keys.PAGE_DOWN);
            by = By.xpath("//button[@data-marker=\"search-filters/submit-button\"]");
            driver.findElement(by).click();
        }

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @ParameterType(".*")
    public Order parseOrder(String order) {
        order = order.replace(" ", "_");
        return Order.valueOf(order);
    }

    @Step("в выпадающем списке сортировка выбрано значение {order}")
    @И("в выпадающем списке сортировка выбрано значение {parseOrder}")
    public void вВыпадающемСпискеСортировкаВыбраноЗначениеДороже(Order order) {
        By by = By.cssSelector("[class^=\"sort-select\"]>[class^=\"select-select\"]");
        Select price = new Select(driver.findElement(by));
        price.selectByIndex(order.getId());

        Screenshot s = new AShot().takeScreenshot(driver);
        screenshot(s);
    }

    @Step("в консоль выведено значение названия и цены {count} первых товаров")
    @И("в консоль выведено значение названия и цены {int} первых товаров")
    public void вКонсольВыведеноЗначениеНазванияИЦеныПервыхТоваров(int count) {
        By by = By.xpath("//div[@data-marker=\"item\"]");
        List<WebElement> searchResults = driver.findElements(by);
        assert count <= searchResults.size() : "An attempt to output " + count + " records, " +
                "but " + searchResults.size() + " are available.";
        for (int i = 0; i < count; ++i) {
            by = By.cssSelector("[data-marker^=\"item-title\"]");
            WebElement printerName = searchResults.get(i).findElement(by);
            System.out.println("Printer: " + printerName.getText());
            by = By.cssSelector("[class^=\"price-text-\"]");
            WebElement printerPrice = searchResults.get(i).findElement(by);
            System.out.println("Price: " + printerPrice.getText());
        }

        Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
        screenshot(s);

        driver.quit();
    }

    @Attachment(value = "Screenshot", type = "img/png")
    public byte[] screenshot(Screenshot screenshot) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(screenshot.getImage(), "PNG", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
