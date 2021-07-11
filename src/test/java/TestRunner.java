import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions (
        features = "src/test/resources/features", // Путь до feature-файлов.
        glue = "StepDefs",    // Название пакета с шагами.
        tags = "@1" // Теги, по которым будут запускаться сценарии.
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
