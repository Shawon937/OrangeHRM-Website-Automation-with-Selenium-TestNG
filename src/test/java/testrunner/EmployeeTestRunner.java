package testrunner;

import io.qameta.allure.Allure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PersonalDetailsPage;
import pages.PIMPage;
import setup.Setup;
import utils.Utils;

import java.io.IOException;
import java.util.List;

public class EmployeeTestRunner extends Setup {
    PersonalDetailsPage personalDetailsPage;
    DashboardPage dashboardPage;
    LoginPage loginPage;
    PIMPage pimPage;

    Utils utils;
    Actions actions;

    @BeforeTest
    public void doLogin() throws InterruptedException {
        driver.get("https://opensource-demo.orangehrmlive.com");
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        pimPage = new PIMPage(driver);

        String adminUser = "admin";
        String adminPass = "admin123";
        loginPage.doLogin(adminUser, adminPass);
        dashboardPage.clickOnPIM();
        Thread.sleep(1500);
        pimPage.btnAddEmployee.get(2).click();
        Thread.sleep(1000);


    }

    @Test(priority = 1, description = "Creating employee without LastName")
    public void createEmployeeWithoutLastname() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        utils = new Utils();
        utils.geneateRandomData();

        String firstName = utils.getFirstname();


        pimPage.createEmployeeWithoutLastname(firstName);
        Thread.sleep(3000);
        String txtActual = pimPage.requiredMsg.getText();
        String txtExpected = "Required";
        Assert.assertTrue(txtActual.contains(txtExpected));
        Allure.description("Create Employee Without Lastname");

    }

    @Test(priority = 2, description = "Creating first employee")
    public void createEmployee1() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        utils = new Utils();
        utils.geneateRandomData();
        actions = new Actions(driver);
        personalDetailsPage = new PersonalDetailsPage(driver);

        String firstName = utils.getFirstname();
        String lastName = utils.getLastname();
        int empIdInt = Utils.generateRandomNumber(10000, 99999);
        String empIdStr = String.valueOf(empIdInt);


        WebElement empID = pimPage.txtInput.get(4);

        empID.clear();
        empID.sendKeys(Keys.CONTROL + "a");
        empID.sendKeys(empIdStr);

        String userName = utils.getUsername();
        String password = "P@ssword123";
        String confirmPassword = password;

//        pimPage.txtInput.get(1).sendKeys(Keys.CONTROL+"a");
//        pimPage.txtInput.get(1).sendKeys(Keys.BACK_SPACE);
        driver.navigate().refresh();

        Thread.sleep(1500);
        pimPage.createEmployee(firstName, lastName, userName, empIdStr, password, confirmPassword);
        Thread.sleep(1500);

        List<WebElement> headerTitle = driver.findElements(By.className("orangehrm-main-title"));
        Assert.assertTrue(headerTitle.get(0).isDisplayed());
        Allure.description("Creating First Employee");

        Utils.waitForElement(driver, headerTitle.get(0), 50);
        if (headerTitle.get(0).isDisplayed()) {
            utils.saveJsonList(userName, password, empIdStr);
        }

        personalDetailsPage.topBarItem.get(2).click();
        Thread.sleep(3000);
    }

    @Test(priority = 3, description = "Creating 2nd employee")
    public void createEmployee2() throws InterruptedException, IOException, ParseException {
        pimPage = new PIMPage(driver);
        utils = new Utils();
        utils.geneateRandomData();
        actions = new Actions(driver);
        personalDetailsPage = new PersonalDetailsPage(driver);

        String firstName = utils.getFirstname();
        String lastName = utils.getLastname();
        int empIdInt = Utils.generateRandomNumber(10000, 99999);
        String empIdStr = String.valueOf(empIdInt);

        WebElement empID = pimPage.txtInput.get(4);

        actions.moveToElement(empID).
                doubleClick(empID).
                sendKeys(Keys.BACK_SPACE).
                sendKeys(empIdStr).
                perform();


        String userName = utils.getUsername();
        String password = "P@ssword123";
        String confirmPassword = password;
        pimPage.createEmployee(firstName, lastName, userName, empIdStr, password, confirmPassword);

        Thread.sleep(3000);

        List<WebElement> headerTitle = driver.findElements(By.className("orangehrm-main-title"));
        Assert.assertTrue(headerTitle.get(0).isDisplayed());
        Allure.description("Create 2nd Employee");

        Utils.waitForElement(driver, headerTitle.get(0), 50);
        if (headerTitle.get(0).isDisplayed()) {
            utils.saveJsonList(userName, password, empIdStr);
        }
        Thread.sleep(3500);

    }


    @Test(priority = 4, description = "Search employee")
    public void searchEmployee() throws InterruptedException, IOException, ParseException {

        actions = new Actions(driver);

        pimPage = new PIMPage(driver);
        personalDetailsPage = new PersonalDetailsPage(driver);
        pimPage.clickOnPIM();

        String fileName = "./src/test/resources/Users.json";
        JSONArray jsonArray = (JSONArray) Utils.readJSONArray(fileName);
        int indexOfFirstEmp = jsonArray.size() - 2;
        int indexOfSecondEmp = jsonArray.size() - 1;

        JSONObject firstEmp = new JSONObject();
        firstEmp = (JSONObject) jsonArray.get(indexOfFirstEmp);
        String firstEmpId = (String) firstEmp.get("employeeId");

        Thread.sleep(2500);
        pimPage.txtInputEmpId.get(1).sendKeys(firstEmpId);
        pimPage.btnSubmit.click();

        Utils.doScroll(driver);
        Thread.sleep(500);
        pimPage.empList.click();
        Thread.sleep(3500);

        String actualRecordEmp1 = pimPage.txtInput.get(5).getAttribute("value");
        System.out.println(actualRecordEmp1);


        String expectedRecordEmp1 = firstEmpId;
        Assert.assertTrue(actualRecordEmp1.equals(expectedRecordEmp1));
        personalDetailsPage.topBarItem.get(1).click();
        Thread.sleep(1000);

        //Getting 2nd Employee from JSON

        JSONObject secondEmp = new JSONObject();
        secondEmp = (JSONObject) jsonArray.get(indexOfSecondEmp);
        String secondEmpId = (String) secondEmp.get("employeeId");

        Thread.sleep(2000);
        pimPage.txtInputEmpId.get(1).sendKeys(secondEmpId);
        pimPage.btnSubmit.click();

        Utils.doScroll(driver);
        Thread.sleep(500);
        pimPage.empList.click();
        Thread.sleep(3000);

        String actualRecordEmp2 = pimPage.txtInput.get(5).getAttribute("value");
        //   System.out.println(actualRecordEmp2);

        String expectedRecordEmp2 = secondEmpId;
        Assert.assertTrue(actualRecordEmp2.equals(expectedRecordEmp2));
        Allure.description("Search Employee");
    }

    @AfterTest
    public void logout() {
        dashboardPage.btnProfileIcon.click();
        driver.findElement(By.partialLinkText("Logout")).click();
    }
}
