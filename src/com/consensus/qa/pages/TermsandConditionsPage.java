package com.consensus.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.consensus.qa.framework.ControlLocators;
import com.consensus.qa.framework.Utilities;

public class TermsandConditionsPage {

	/* region Page Initialization */
	public TermsandConditionsPage(WebDriver d)
	{
		PageFactory.initElements(d, this);
	}
	/* end region Page Initialization */

	/* region Select Insurance Page Elements */
	@FindBy(name = ControlLocators.TERMS_CONDITIONS_CHECKBOX)
	public WebElement checkboxTermsConditions;

	@FindBy(id = ControlLocators.CONTINUE_TERMS_AND_CONDITION_BUTTON)
	public WebElement continueTCButton;

	@FindBy(xpath = ControlLocators.TARGET_TERMS)
	public WebElement targteTerms;

	@FindBy(xpath = ControlLocators.TC_CHK_BOX)
	public WebElement tcChkBox;
	
	@FindBy(id = ControlLocators.EMAIL_TC_CHK_BOX)
	public WebElement emailTCChkBox;

	@FindBy(id = ControlLocators.ACCEPTS_TARGET_TC_CHECKBOX)
	public WebElement acceptsTargetTCCheckbox;
	
	@FindBy(id = ControlLocators.SAVE_SIGNATURE_BUTTON)
	public WebElement saveSignatureButton;
	//EndRegion
	
	public void acceptTermsAndConditions()
	{
		 Utilities.waitForElementVisible(checkboxTermsConditions);
		 checkboxTermsConditions.click();
		 acceptsTargetTCCheckbox.click();
		 continueTCButton.click();
	}
}