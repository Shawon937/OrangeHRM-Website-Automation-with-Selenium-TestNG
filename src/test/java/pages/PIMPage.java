package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PIMPage {
    public PIMPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    @FindBy(className = "oxd-button")
    public List<WebElement> btnAddEmployee;

    @FindBy(className = "oxd-switch-input")
    public WebElement btnToggle;

    @FindBy(className = "oxd-input")
    public List<WebElement> txtInput; // get(1)= FirstName, 2=MidName, 3= LastName 4=empId 5=username 6= password 7=confirmPassword

    @FindBy(css = "[type=submit]")
    public WebElement btnSubmit;

    @FindBy(className = "oxd-main-menu-item")
    public List<WebElement> menuItem;

    @FindBy(className = "oxd-input--active")
    public List<WebElement> txtInputEmpId;

    @FindBy(className = "orangehrm-left-space")
    public WebElement btnSearch;


    @FindBy(className = "oxd-text")
    public List<WebElement> recordsTxt;

    @FindBy(className = "data")
    public List<WebElement> dataEmp;

    @FindBy(className = "orangehrm-employee-list")
    public WebElement empList;

    @FindBy(className = "oxd-input-field-error-message")
    public WebElement requiredMsg;



    public void createEmployee(String firstName, String lastName, String userName,String empIdStr, String password, String confirmPassword) throws InterruptedException {
        txtInput.get(1).sendKeys(firstName);
        txtInput.get(3).sendKeys(lastName);

        WebElement empID = txtInput.get(4);
        Thread.sleep(2000);
        empID.clear();
        empID.sendKeys(Keys.CONTROL+"a");
        empID.sendKeys(empIdStr);

        btnToggle.click();

        txtInput.get(5).sendKeys(userName); //username
        txtInput.get(6).sendKeys(password); //password
        txtInput.get(7).sendKeys(confirmPassword); //confirm password
        btnSubmit.click();
    }
    public void createEmployeeWithoutLastname(String firstName) throws InterruptedException {
        txtInput.get(1).sendKeys(firstName);
        btnSubmit.click();
        Thread.sleep(1500);
    }

    public void clickOnPIM(){
        menuItem.get(1).click();
    }



}
