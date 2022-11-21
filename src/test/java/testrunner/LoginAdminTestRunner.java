package testrunner;

import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import setup.Setup;

public class LoginAdminTestRunner extends Setup {

    DashboardPage dashboardPage;
    LoginPage loginPage;

    @BeforeTest(description = "Visiting the Website")
    public void visitURL(){
        driver.get("https://opensource-demo.orangehrmlive.com");
    }

    @Test(priority = 1, description = "Login Admin with wrong creds")
    public void doLoginWithInvalidCreds() throws InterruptedException {
        loginPage=new LoginPage(driver);
        loginPage.doLogin("Adm","admin12");
        String txtActual=loginPage.invalidCred.getText();
        String txtExpected="Invalid credentials";
        Assert.assertTrue(txtActual.contains(txtExpected));
        Allure.description("Login with Invalid Creds Assertion");
    }

    @Test(priority = 2, description = "Login Admin")
    public void doLogin() throws InterruptedException {
        loginPage=new LoginPage(driver);
        loginPage.doLogin("Admin","admin123");
        String urlActual=driver.getCurrentUrl();
        String urlExpected="dashboard/index";
        Assert.assertTrue(urlActual.contains(urlExpected));
        Allure.description("Dashboard Assertion");
    }


}
