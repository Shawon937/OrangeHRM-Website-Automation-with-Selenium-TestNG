package testrunner;

import io.qameta.allure.Allure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import pages.PersonalDetailsPage;
import setup.Setup;
import utils.Utils;

import java.io.IOException;
import java.util.List;

public class EmployeeUpdateTestRunner extends Setup {
    PersonalDetailsPage personalDetailsPage;
    DashboardPage dashboardPage;
    LoginPage loginPage;
    PIMPage pimPage;
    Utils utils;
    Actions actions;


    @BeforeTest(description = "Visiting the Website")
    public void visitURL(){
        driver.get("https://opensource-demo.orangehrmlive.com");
    }

    @Test(priority = 1, description = "Login with Last Created User with wrong Password")
    public void doLoginWithInvalidCreds() throws InterruptedException, IOException, ParseException {
        loginPage=new LoginPage(driver);

        String fileName="./src/test/resources/Users.json";
        JSONArray jsonArray= (JSONArray) Utils.readJSONArray(fileName);
        int indexOfSecondEmp = jsonArray.size() - 1;

        JSONObject secondEmp = new JSONObject();
        secondEmp = (JSONObject) jsonArray.get(indexOfSecondEmp);
        String secondEmpUsername = (String) secondEmp.get("userName");
        String secondEmpPassword = (String) secondEmp.get("password");

        String customerUser=secondEmpUsername;
        String customerPass=secondEmpPassword+"@3215";

        loginPage.doLogin(customerUser,customerPass);
        String txtActual=loginPage.invalidCred.getText();
        String txtExpected="Invalid credentials";
        Assert.assertTrue(txtActual.contains(txtExpected));
        Allure.description("Login with Invalid Creds Assertion");
    }

    @Test(priority = 2, description = "Login with Last Created User")
    public void doLogin() throws InterruptedException, IOException, ParseException {
        loginPage=new LoginPage(driver);
        dashboardPage= new DashboardPage(driver);
        pimPage = new PIMPage(driver);
        personalDetailsPage = new PersonalDetailsPage(driver);


        String fileName="./src/test/resources/Users.json";
        JSONArray jsonArray= (JSONArray) Utils.readJSONArray(fileName);
        int indexOfSecondEmp = jsonArray.size() - 1;

        JSONObject secondEmp = new JSONObject();
        secondEmp = (JSONObject) jsonArray.get(indexOfSecondEmp);
        String secondEmpUsername = (String) secondEmp.get("userName");
        String secondEmpPassword = (String) secondEmp.get("password");

        String customerUser=secondEmpUsername;
        String customerPass=secondEmpPassword;
        loginPage.doLogin(customerUser,customerPass);


        Thread.sleep(1000);

        Assert.assertTrue( dashboardPage.headerDashboard.isDisplayed());

        dashboardPage.myInfo.get(2).click();
        Thread.sleep(1000);

        Assert.assertTrue(personalDetailsPage.headerTitle.get(0).isDisplayed());


    }

    @Test(priority = 3, description = "Update user info")
    public void updateUserInfo() throws InterruptedException {
        personalDetailsPage = new PersonalDetailsPage(driver);

        Utils.waitForElement(driver, personalDetailsPage.headerTitle.get(0), 50);
        if (personalDetailsPage.headerTitle.get(0).isDisplayed()) {
            personalDetailsPage = new PersonalDetailsPage(driver);


            personalDetailsPage.dropdownBox.get(0).click();
            personalDetailsPage.dropdownBox.get(0).sendKeys("b");
            personalDetailsPage.dropdownBox.get(0).sendKeys(Keys.ARROW_DOWN);
            personalDetailsPage.dropdownBox.get(0).sendKeys(Keys.ARROW_DOWN);
            personalDetailsPage.dropdownBox.get(0).sendKeys(Keys.ENTER);
            Utils.doScrollPixels(driver);

            List<WebElement> buttons = driver.findElements(By.className("orangehrm-left-space"));
            buttons.get(0).click();
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(5000);
            List<WebElement> list = driver.findElements(By.className("oxd-select-text-input"));
            String country = list.get(0).getText();
            System.out.println(country);
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(country, "Bangladeshi");
            Assert.assertEquals(country, "Bangladeshi");
            softAssert.assertAll();
            Allure.description("Assertion of updating Nationality dropdown ");
        }
    }

    @Test(priority = 4, description = "Logout")
    public void logout() throws InterruptedException {
        dashboardPage = new DashboardPage(driver);
        dashboardPage.btnProfileIcon.click();
        driver.findElement(By.partialLinkText("Logout")).click();
        Thread.sleep(1500);
        String loginTxt =  driver.findElement(By.className("orangehrm-login-title")).getText();
        Assert.assertTrue(loginTxt.equals("Login"));
    }




}
