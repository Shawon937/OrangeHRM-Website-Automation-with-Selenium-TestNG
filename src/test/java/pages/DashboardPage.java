package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DashboardPage {
    @FindBy(className = "oxd-userdropdown-img")
    public WebElement imgProfile;
    @FindBy(className = "oxd-select-text-input")
    public List<WebElement> dropdowns;
    @FindBy(css = "[type=submit]")
    public WebElement btnSubmit;

    @FindBy(className = "oxd-userdropdown-tab")
    public WebElement btnProfileIcon;

    @FindBy(className = "oxd-main-menu-item")
    public List<WebElement> menuItem;

    @FindBy(className = "oxd-main-menu-item--name")
    public List<WebElement> myInfo;

    @FindBy(className = "oxd-text--h6")
    public WebElement headerDashboard;

    public DashboardPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void clickOnPIM(){
        menuItem.get(1).click();
    }
}
