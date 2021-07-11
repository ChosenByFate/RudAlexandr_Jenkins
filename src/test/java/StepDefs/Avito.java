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
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
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
        Select categories = new Select(driver.findElement(By.id("category")));
        categories.selectByVisibleText(category.getCategory());
    }

    @И("^в поле поиска введено значение ([А-я]+)$")
    public void вПолеПоискаВведеноЗначениеПринтер(String product) {
        WebElement search = driver.findElement(By.xpath("//input[@data-marker=\"search-form/suggest\"]"));
        search.sendKeys(product);
    }

    @Тогда("кликнуть по выпадающему списку региона")
    public void кликнутьПоВыпадающемуСпискуРегиона() {
        WebElement sity = driver.findElement(By.xpath("//div[@data-marker=\"search-form/region\"]"));
        sity.click();
    }

    @Тогда("^в поле регион введено значение ([А-я]+)$")
    public void вПолеРегионВведеноЗначениеВладивосток(String city) {
        WebElement sityFind = driver.findElement(By.xpath("//input[@placeholder=\"Город, регион или Россия\"]"));
        sityFind.sendKeys(city);

        List<WebElement> sitySelect = driver.findElements(By.xpath("//strong"));
        sitySelect.get(0).click();
    }

    @И("нажата кнопка показать объявления")
    public void нажатаКнопкаПоказатьОбъявления() {
        WebElement btnFind = driver.findElement(By.xpath("//button[@data-marker=\"popup-location/save-button\"]"));
        btnFind.click();
    }

    @Тогда("^открылась страница результаты по запросу ([А-я]+)$")
    public void открыласьСтраницаРезультатыПоЗапросуПринтер(String product) {
        
    }

    @И("активирован чекбокс только с фотографией")
    public void активированЧекбоксТолькоСФотографией() {
        WebElement chkBox = driver.findElement(By.xpath("//label[@data-marker=\"delivery-filter\"]" +
                "/input[@type=\"checkbox\"]"));
        if (!chkBox.isSelected() && chkBox.isEnabled()) {
            chkBox.sendKeys(Keys.SPACE);
            WebElement btnFind2 = driver.findElement(By.xpath("//button[@data-marker=\"search-filters/submit-button\"]"));
            btnFind2.click();
        }
    }

    @ParameterType(".*")
    public Order parseOrder(String order) {
        order = order.replace(" ", "_");
        return Order.valueOf(order);
    }

    @И("в выпадающем списке сортировка выбрано значение {parseOrder}")
    public void вВыпадающемСпискеСортировкаВыбраноЗначениеДороже(Order order) {
        Select price = new Select(driver.findElement(By.cssSelector("[class^=\"sort-select\"]>[class^=\"select-select\"]")));
        price.selectByIndex(order.getId());
    }

    @И("в консоль выведено значение названия и цены {int} первых товаров")
    public void вКонсольВыведеноЗначениеНазванияИЦеныПервыхТоваров(int count) {
        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@data-marker=\"item\"]"));
        for (int i = 0; i < count; ++i) {
            WebElement printerName = searchResults.get(i).findElement(By.cssSelector("[data-marker^=\"item-title\"]"));
            System.out.println("Printer: " + printerName.getText());
            WebElement printerPrice = searchResults.get(i).findElement(By.cssSelector("[class^=\"price-text-\"]"));
            System.out.println("Price: " + printerPrice.getText());
        }

        driver.quit();
    }
}
