package com.consensus.qa.pages;

import com.consensus.qa.framework.ControlLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.consensus.qa.framework.ControlLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.consensus.qa.framework.ControlLocators;
import com.consensus.qa.framework.Utilities;
import com.consensus.qa.framework.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Created by mritunjaikr on 5/19/2015.
 */
public class DeviceFinancingInstallmentContractPage {
    public DeviceFinancingInstallmentContractPage( WebDriver d)
    {
        PageFactory.initElements(d, this);
    }

    //region- Web Elements
    @FindBy(xpath = ControlLocators.HEADER_DEVICE_FINANCE)
    public WebElement headerDeviceFinance;

    @FindBy(id = ControlLocators.DEPOSIT_CHKBOX)
    public WebElement depositCheckBox;

    @FindBy(id = ControlLocators.PRINT)
    public WebElement print;

    @FindBy(id = ControlLocators.GUEST_CHKBOX)
    public WebElement guestAcceptChkBox;
    //endregion

    public void PrintDeviceFinancingDetails(WebDriver driver) throws InterruptedException, AWTException, IOException {
        Utilities.waitForElementVisible(PageBase.DeviceFinancingInstallmentContractPage().print);
        PageBase.DeviceFinancingInstallmentContractPage().print.click();
        Utilities.implicitWaitSleep(3000);

        Robot robot = new Robot();
        Utilities.sendKeys(KeyEvent.VK_ENTER, robot);
        Utilities.sendKeys(KeyEvent.VK_ESCAPE, robot);
        Utilities.sendKeys(KeyEvent.VK_ESCAPE, robot);
        Utilities.implicitWaitSleep(6000);

        Utilities.waitForElementVisible(PageBase.DeviceFinancingInstallmentContractPage().guestAcceptChkBox);
        PageBase.DeviceFinancingInstallmentContractPage().guestAcceptChkBox.click();
        PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
        PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();
        Utilities.implicitWaitSleep(2000);
    }

}
