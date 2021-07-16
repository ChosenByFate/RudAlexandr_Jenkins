package StepDefs;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Avito {
    WebDriver driver;

    @Пусть("открыт ресурс авито")
    public void открытРесурсАвито() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://avito.ru");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @ParameterType(".*")
    public Category parseCategory(String category) {
        category = category.replace(" ", "_");
        category = category.replace(",", "");
        return Category.valueOf(category);
    }

    @И("в выпадающем списке категорий выбрана {parseCategory}")
    public void вВыпадающемСпискеКатегорийВыбранаОртехника(Category category) {
        By by = By.id("category");
        Select categories = new Select(driver.findElement(by));
        categories.selectByVisibleText(category.getCategory());
    }

    @И("^в поле поиска введено значение ([А-я]+)$")
    public void вПолеПоискаВведеноЗначениеПринтер(String product) {
        By by = By.xpath("//input[@data-marker=\"search-form/suggest\"]");
        WebElement search = driver.findElement(by);
        search.sendKeys(product);
    }

    @Тогда("кликнуть по выпадающему списку региона")
    public void кликнутьПоВыпадающемуСпискуРегиона() {
        By by = By.xpath("//div[@data-marker=\"search-form/region\"]");
        WebElement city = driver.findElement(by);
        city.click();
    }

    @Тогда("^в поле регион введено значение ([А-я]+)$")
    public void вПолеРегионВведеноЗначениеВладивосток(String city) {
        By by = By.xpath("//input[@placeholder=\"Город, регион или Россия\"]");
        WebElement cityFind = driver.findElement(by);
        cityFind.sendKeys(city);

        by = By.xpath("//strong");
        List<WebElement> citySelect = driver.findElements(by);
        citySelect.get(0).click();
    }

    @И("нажата кнопка показать объявления")
    public void нажатаКнопкаПоказатьОбъявления() {
        By by = By.xpath("//button[@data-marker=\"popup-location/save-button\"]");
        WebElement btnFind = driver.findElement(by);
        btnFind.click();
    }

    @Тогда("^открылась страница результаты по запросу ([А-я]+)$")
    public void открыласьСтраницаРезультатыПоЗапросуПринтер(String product) {
        By by = By.cssSelector("[data-marker=\"page-title/text\"]");
        WebElement searchQuery = driver.findElement(by);
        assert searchQuery.getText().contains("«" + product + "»");
    }

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
    }

    @ParameterType(".*")
    public Order parseOrder(String order) {
        order = order.replace(" ", "_");
        return Order.valueOf(order);
    }

    @И("в выпадающем списке сортировка выбрано значение {parseOrder}")
    public void вВыпадающемСпискеСортировкаВыбраноЗначениеДороже(Order order) {
        By by = By.cssSelector("[class^=\"sort-select\"]>[class^=\"select-select\"]");
        Select price = new Select(driver.findElement(by));
        price.selectByIndex(order.getId());
    }

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

        driver.quit();
    }
}
