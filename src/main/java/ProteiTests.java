import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.net.URISyntaxException;
import java.net.URL;

public class ProteiTests {
    public static void main(String[] args) throws URISyntaxException {
        WebDriver driver = new ChromeDriver();

        // Открытие HTML-файла
        URL resourceUrl = ProteiTests.class.getClassLoader().getResource("qa-test.html");
        String filePath = resourceUrl.toURI().getPath();
        driver.get("file://" + filePath);

        // Тест 1: Корректная авторизация
        testAuthorizationCorrectSubmission(driver);

        // Тест 2: Валидация почты при авторизации
        testAuthorizationValidationInvalidEmail(driver);

        // Тест 3: Валидация пароля при авторизации
        testAuthorizationValidationWrongPassword(driver);

        // Тест 4: Валидация пустых полей при авторизации
        testAuthorizationValidationEmptyFields(driver);

        // Тест 5: Корректное заполнение формы
        testFormCorrectSubmission(driver);

        // Тест 6: Валидация пустого имени в форме
        testFormValidationBlankName(driver);

        // Тест 7: Выбор нескольких чек-боксов формы
        testFormMultipleCheckboxSelection(driver);

        // Тест 8: Выбор нескольких радиокнопок формы
        testFormMultipleRadioButtonSelection(driver);

        // Тест 9: Очистка полей после отправки формы
        testFormFieldsAndOptionsClearedAfterSubmission(driver);

        // Тест 10: Переполнение полей формы
        testFormValidationFieldsOverflow(driver);

        driver.quit();
    }

    //Метод авторизации
    public static void doFormAuth(WebDriver driver) {
        driver.findElement(By.id("loginEmail")).sendKeys("test@protei.ru");
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();
    }

    //Тест 1: Корректная авторизация
    public static void testAuthorizationCorrectSubmission(WebDriver driver) {
        // Авторизация
        doFormAuth(driver);

        //Проверка перехода на форму после авторизации
        WebElement form = driver.findElement(By.id("inputsPage"));
        if (form.isDisplayed()) {
            System.out.println("Тест корретной авторизации прошел успешно");
        } else {
            System.out.println("Тест корретной  авторизации провален");
        }
    }

    // Тест 2: Валидация почты при авторизации
    public static void testAuthorizationValidationInvalidEmail(WebDriver driver) {
        driver.navigate().refresh();

        //Заполнение данных авторизации с некорректной почтой
        driver.findElement(By.id("loginEmail")).sendKeys("invalid-email");
        driver.findElement(By.id("loginPassword")).sendKeys("test");
        driver.findElement(By.id("authButton")).click();

        //Проверка валидации некорректной почты
        WebElement error = driver.findElement(By.id("emailFormatError"));
        if (error.isDisplayed()) {
            System.out.println("Тест валидации почты прошел успешно");
        } else {
            System.out.println("Тест валидации почты провален");
        }
    }

    // Тест 3: Валидация пароля при авторизации
    public static void testAuthorizationValidationWrongPassword(WebDriver driver) {
        driver.navigate().refresh();

        //Заполнение данных авторизации с некорректным паролем
        driver.findElement(By.id("loginEmail")).sendKeys("test@protei.ru");
        driver.findElement(By.id("loginPassword")).sendKeys("123");
        driver.findElement(By.id("authButton")).click();

        //Проверка валидации некорректного пароля
        WebElement error = driver.findElement(By.id("invalidEmailPassword"));
        if (error.isDisplayed()) {
            System.out.println("Тест валидации неверного пароля прошел успешно");
        } else {
            System.out.println("Тест валидации неверного пароля провален");
        }
    }

    // Тест 4: Валидация пустых полей при авторизации
    public static void testAuthorizationValidationEmptyFields(WebDriver driver) {
        driver.navigate().refresh();

        // Отправка пустой формы
        driver.findElement(By.id("authButton")).click();

        // Проверка валидации формы с пустыми полями
        WebElement error = driver.findElement(By.id("emailFormatError"));
        if (error.isDisplayed()) {
            System.out.println("Тест валидации пустых полей авторизации прошел успешно");
        } else {
            System.out.println("Тест валидации пустых полей авторизации провален");
        }
    }

    // Тест 5: Корректное заполнение анкеты
    public static void testFormCorrectSubmission(WebDriver driver) {
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
            System.out.println("Тест корректного заполнения формы прошел успешно");
        } else {
            System.out.println("Тест корретного заполнения формы провален");
        }
    }

    // Тест 6: Валидация пустого имени в форме
    public static void testFormValidationBlankName(WebDriver driver) {
        driver.navigate().refresh();

        // Авторизация
        doFormAuth(driver);

        // Пустое имя
        driver.findElement(By.id("dataEmail")).sendKeys("test@mail.com");;
        driver.findElement(By.id("dataSend")).click();

        WebElement error = driver.findElement(By.id("blankNameError"));
        if (error.isDisplayed()) {
            System.out.println("Тест валидации пустого имени в форме прошел успешно");
        } else {
            System.out.println("Тест валидации пустого имени в форме провален");
        }
    }

    // Тест 7: Выбор нескольких чек-боксов формы
    public static void testFormMultipleCheckboxSelection(WebDriver driver) {
        driver.navigate().refresh();

        // Авторизация
        doFormAuth(driver);

        // Заполнение анкеты
        driver.findElement(By.id("dataEmail")).sendKeys("test@mail.com");
        driver.findElement(By.id("dataName")).sendKeys("Test User");
        driver.findElement(By.id("dataCheck11")).click();
        driver.findElement(By.id("dataCheck12")).click();
        driver.findElement(By.id("dataSend")).click();

        // Проверка добавленной строки
        WebElement row = driver.findElement(By.xpath("//table[@id='dataTable']//tbody/tr"));
        String text = row.getText();
        if (text.contains("1.1, 1.2")) {
            System.out.println("Тест на выбор нескольких чекбоксов прошел успешно");
        } else {
            System.out.println("Тест на выбор нескольких чекбоксов провален");
        }
    }

    // Тест 8: Выбор нескольких радиокнопок формы
    public static void testFormMultipleRadioButtonSelection(WebDriver driver) {
        driver.navigate().refresh();

        // Авторизация
        doFormAuth(driver);

        // Попытка выбрать несколько радиокнопок
        driver.findElement(By.id("dataSelect21")).click();
        driver.findElement(By.id("dataSelect22")).click();
        driver.findElement(By.id("dataSelect23")).click();

        // Проверка на выбор несколких радиокнопок
        boolean isRadio21Selected = driver.findElement(By.id("dataSelect21")).isSelected();
        boolean isRadio22Selected = driver.findElement(By.id("dataSelect22")).isSelected();
        boolean isRadio23Selected = driver.findElement(By.id("dataSelect23")).isSelected();

        if (!isRadio21Selected && !isRadio22Selected && isRadio23Selected) {
            System.out.println("Тест на выбор нескольких радиокнопок в группе прошел успешно");
        } else {
            System.out.println("Тест на выбор нескольких радиокнопок в группе провален");
        }
    }

    // Тест 9: Очистка полей после отправки формы
    public static void testFormFieldsAndOptionsClearedAfterSubmission(WebDriver driver) {
        driver.navigate().refresh();

        // Авторизация
        doFormAuth(driver);

        // Заполнение анкеты
        driver.findElement(By.id("dataEmail")).sendKeys("teste@mail.com");
        driver.findElement(By.id("dataName")).sendKeys("test name");
        driver.findElement(By.id("dataCheck11")).click();
        driver.findElement(By.id("dataCheck12")).click();
        driver.findElement(By.id("dataSelect21")).click();
        driver.findElement(By.id("dataSend")).click();

        // Проверка, что поля ввода очищены
        String emailFieldValue = driver.findElement(By.id("dataEmail")).getAttribute("value");
        String nameFieldValue = driver.findElement(By.id("dataName")).getAttribute("value");
        boolean isFieldsCleared = emailFieldValue.isEmpty() && nameFieldValue.isEmpty();

        // Проверка сброса чекбоксов
        boolean isCheck11Cleared = !driver.findElement(By.id("dataCheck11")).isSelected();
        boolean isCheck12Cleared = !driver.findElement(By.id("dataCheck12")).isSelected();
        boolean isCheckCleared = (isCheck11Cleared && isCheck12Cleared);

        // Проверка сброса радиокнопок
        boolean isRadio21Cleared = !driver.findElement(By.id("dataSelect21")).isSelected();
        boolean isRadio22Cleared = !driver.findElement(By.id("dataSelect22")).isSelected();
        boolean isRadio23Cleared = !driver.findElement(By.id("dataSelect23")).isSelected();
        boolean isRadioCleared = (isRadio21Cleared && isRadio22Cleared && isRadio23Cleared);

        //Общая проверка
        if (isFieldsCleared && isCheckCleared && isRadioCleared) {
            System.out.println("Тест очистки заполняемых данных после отправки формы прошёл успшено");
        } else {
            System.out.println("Тест очистки заполняемых данных после отправки формы провален");
        }
    }

    // Тест 10: Переполнение полей формы
    public static void testFormValidationFieldsOverflow(WebDriver driver) {
        driver.navigate().refresh();

        // Авторизация
        doFormAuth(driver);

        // Создание длинных строк
        String longEmail = "long".repeat(25) + "@test.com";
        String longName = "long".repeat(25);

        // Ввод длинного текста в поля
        WebElement emailField = driver.findElement(By.id("dataEmail"));
        emailField.sendKeys(longEmail);
        WebElement nameField = driver.findElement(By.id("dataName"));
        nameField.sendKeys(longName);
        driver.findElement(By.id("dataSend")).click();

        // Проверка таблицы
        int rowCount = driver.findElements(By.xpath("//table[@id='dataTable']//tbody/tr")).size();

        // Провека содержимого записи
        if (rowCount == 1) {
            WebElement lastRow = driver.findElement(By.xpath("//table[@id='dataTable']//tbody/tr[last()]"));
            String emailInTable = lastRow.findElement(By.xpath("td[1]")).getText();
            String nameInTable = lastRow.findElement(By.xpath("td[2]")).getText();

            int maxAllowedLength = 64; // Максимальная длина, взял из здравого смысла 64, но в реальных условиях отталкивался бы от ТЗ

            boolean isEmailValid = emailInTable.length() <= maxAllowedLength;
            boolean isNameValid = nameInTable.length() <= maxAllowedLength;

            if (!isEmailValid || !isNameValid) {
                System.out.println("Тест провален: запись с недопустимо длинными значениями добавлена в таблицу");
            } else {
                System.out.println("Тест прошел успешно: длинные значения были обрезаны перед добавлением в таблицу");
            }
        } else {
            System.out.println("Тест прошел успешно: запись с длинными значениями не была добавлена");
        }
    }
}



