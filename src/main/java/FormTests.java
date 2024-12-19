import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class FormTests {
    public static void main(String[] args) throws URISyntaxException {
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        WebDriver driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Открытие HTML-файла
        URL resourceUrl = FormTests.class.getClassLoader().getResource("qa-test.html");
        String filePath = resourceUrl.toURI().getPath();
        driver.get("file://" + filePath);

        // Тест 1: Успешная авторизация
        testSuccessfulAuthorization(driver);

        // Тест 2: Ошибка валидации на форме авторизации
        testAuthorizationValidation(driver);

        // Тест 3: Успешное заполнение анкеты
        testSuccessfulFormSubmission(driver);

        // Тест 4: Ошибки валидации анкеты
        testFormValidationErrors(driver);

        // Тест 5:
        testMultipleCheckboxSelection(driver);

        driver.quit();
    }

    public static void doFormAuth(WebDriver driver) {
        driver.findElement(By.id("loginEmail")).sendKeys("test@protei.ru");
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();
    }


    public static void testSuccessfulAuthorization(WebDriver driver) {
        doFormAuth(driver);

        WebElement form = driver.findElement(By.id("inputsPage"));
        if (form.isDisplayed()) {
            System.out.println("Тест успешной авторизации прошел успешно.");
        } else {
            System.out.println("Тест успешной авторизации провален.");
        }
    }

    public static void testAuthorizationValidation(WebDriver driver) {
        driver.navigate().refresh();

        driver.findElement(By.id("loginEmail")).sendKeys("invalid-email");
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();

        WebElement error = driver.findElement(By.id("emailFormatError"));
        if (error.isDisplayed()) {
            System.out.println("Тест валидации почты прошел успешно.");
        } else {
            System.out.println("Тест валидации почты провален.");
        }
    }

    public static void testSuccessfulFormSubmission(WebDriver driver) {
        driver.navigate().refresh();

        // Авторизация
        doFormAuth(driver);

        // Заполнение анкеты
        driver.findElement(By.id("dataEmail")).sendKeys("test@mail.com");
        driver.findElement(By.id("dataName")).sendKeys("Андрей Проценко");
        Select genderSelect = new Select(driver.findElement(By.id("dataGender")));
        genderSelect.selectByVisibleText("Мужской");
        driver.findElement(By.id("dataCheck11")).click();
        driver.findElement(By.id("dataSelect21")).click();
        driver.findElement(By.id("dataSend")).click();

        // Проверка добавления строки
        WebElement row = driver.findElement(By.xpath("//table[@id='dataTable']//tbody/tr"));
        if (row.isDisplayed()) {
            System.out.println("Тест успешного заполнения анкеты прошел успешно.");
        } else {
            System.out.println("Тест успешного заполнения анкеты провален.");
        }
    }

    public static void testFormValidationErrors(WebDriver driver) {
        driver.navigate().refresh();

        // Авторизация
        doFormAuth(driver);

        // Пустое имя
        driver.findElement(By.id("dataEmail")).sendKeys("example@domain.com");
        driver.findElement(By.id("dataName")).clear();
        driver.findElement(By.id("dataSend")).click();

        WebElement error = driver.findElement(By.id("blankNameError"));
        if (error.isDisplayed()) {
            System.out.println("Тест валидации пустого имени прошел успешно.");
        } else {
            System.out.println("Тест валидации пустого имени провален.");
        }
    }

    public static void testMultipleCheckboxSelection(WebDriver driver) {
        driver.navigate().refresh();

        // Авторизация
        doFormAuth(driver);

        // Заполнение анкеты
        driver.findElement(By.id("dataEmail")).sendKeys("example@domain.com");
        driver.findElement(By.id("dataName")).sendKeys("Иван Иванов");
        driver.findElement(By.id("dataCheck11")).click();
        driver.findElement(By.id("dataCheck12")).click();
        driver.findElement(By.id("dataSend")).click();

        // Проверка добавленной строки
        WebElement row = driver.findElement(By.xpath("//table[@id='dataTable']//tbody/tr"));
        String text = row.getText();
        if (text.contains("1.1, 1.2")) {
            System.out.println("Тест на выбор нескольких вариантов прошел успешно.");
        } else {
            System.out.println("Тест на выбор нескольких вариантов провален.");
        }
    }
}

