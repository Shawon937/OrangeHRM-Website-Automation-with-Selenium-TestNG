package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PersonalDetailsPage {
    public PersonalDetailsPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(className = "oxd-topbar-body-nav-tab-item")
    public List<WebElement> topBarItem;

    @FindBy(className = "oxd-select-text-input")
    public List<WebElement> dropdownBox;  // 0=Nationality

    @FindBy(className = "orangehrm-main-title")
    public List<WebElement> headerTitle;
}
