package com.consensus.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.consensus.qa.framework.BrowserSettings;
import com.consensus.qa.framework.ControlLocators;
import com.consensus.qa.framework.Utilities;
import com.consensus.qa.framework.Utilities.SelectDropMethod;

/**
 * Created by Rashmimk on 5/13/2015.
 */
public class InstallmentPage {

    public InstallmentPage(WebDriver d)
    {
        PageFactory.initElements(d, this);
    }

    @FindBy(xpath = ControlLocators.VERIZONEDGE_MONTHLYINSTALLMENT_RADIOBUTTON)
    public WebElement edgeMonthlyInstallmentRadiobutton;
}
