package com.consensus.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.consensus.qa.framework.ControlLocators;
import com.consensus.qa.framework.PageBase;

public class VerizonSelectPlanFeaturesPage {

	/* region Page Initialization */
	public VerizonSelectPlanFeaturesPage(WebDriver d)
	{
		PageFactory.initElements(d, this);
	}
	/* end region Page Initialization */

	/* region Select Plan Features Page Elements */

	@FindBy(xpath = ControlLocators.CANCEL_SPF_BUTTON)
	public WebElement cancelSPFButton;

	@FindBy(id = ControlLocators.CONTINUE_SPF_BUTTON)
	public WebElement continueSPFButton;

	@FindBy(id = ControlLocators.BASIC_PHONE_DISCOUNT_0_CHECKBOX)
	public WebElement basicPhoneDiscount0Checkbox;
	
	@FindBy(xpath = ControlLocators.GB_25_CLOUD_FREE_ON_MORE_EVERYTHING_PLANS_$2_99_PER_MONTH_CHECKBOX)
	public WebElement gB25CloudFreeOnMoreEverythingPlans$2_99PerMonthCheckbox;

	@FindBy(xpath = ControlLocators.SELECT_FEATURE_CHECKBOX)
	public WebElement selectPlanFeature;  
	
	@FindBy(xpath = ControlLocators.FIRST_TEXT_PICTURE_AND_MESSAGING_TAB)
	public WebElement firstTextPictureAndMessagingTab;
	
	@FindBy(xpath = ControlLocators.FIRST_1000_MESSAGES_10_PER_MONTH_CHECKBOX)
	public WebElement first1000Messages10PerMonthCheckbox;


	/* region end Select Plan Features Page Elements */

	public void clickContinue()
	{
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();
	}
}