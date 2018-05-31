package com.consensus.qa.pages;

import java.util.List;

import com.consensus.qa.framework.*;
import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.thoughtworks.selenium.webdriven.commands.Click;

public class UECAddLinesPage extends RetailBaseClass{

	/* region Page Initialization */
	public UECAddLinesPage(WebDriver d)
	{
		PageFactory.initElements(d, this);
	}
	/* end region Page Initialization */

	/* region UEC Add Lines Page Elements */
	@FindBy(xpath = ControlLocators.FIRST_AAL_CHECKBOX)
	public WebElement firstAALCheckbox;

	@FindBy(xpath = ControlLocators.SECOND_AAL_CHECKBOX)
	public WebElement secondAALCheckbox;

	@FindBy(xpath = ControlLocators.THIRD_AAL_CHECKBOX)
	public WebElement thirdAALCheckbox;

	@FindBy(xpath = ControlLocators.FOURTH_AAL_CHECKBOX)
	public WebElement fourthAALCheckbox;

	@FindBy(xpath = ControlLocators.FIFTH_AAL_CHECKBOX)
	public WebElement fifthAALCheckbox;

	@FindBy(xpath = ControlLocators.CONTINUE_UEC_ADD_LINES_BUTTON)
	public WebElement continueUECAddLinesButton;

	@FindBy(xpath = ControlLocators.ELIGIBLE_FOR_DF)
	public WebElement eligibleForDF;

	@FindBy(xpath = ControlLocators.ELIGIBLE_FOR_2YEAR)
	public WebElement eligibleFor2Year;

	@FindBy(xpath = ControlLocators.TRANSFER_ELIGIBLE)
	public WebElement transferEligible;

	@FindBy(id = ControlLocators.PLEASE_SELECT_AN_ELIGIBLE_DONOR_DROPDOWN)
	public WebElement pleaseSelectAnEligibleDonor1Dropdown;
	
	@FindBy(xpath = ControlLocators.ALL_NUMBERS_ELEMENTS)
	public List<WebElement> numbersUnderAccount;

	/* end region UEC Add Line Page Elements */

    //region Methods
	public void addALine()
	{
		PageBase.UECAddLinesPage().secondAALCheckbox.click();
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();
	}

	//This method will be used for checking checkbox for a particular number.
	//Author : Tarun
	public void clickCheckboxForParticularPhoneNumber(String phoneNumber)
	{
		String formattedPhoneNumber = CommonFunction.getFormattedPhoneNumber(phoneNumber);
		driver.findElement(By.xpath("//span[contains(text(),'Mobile Number:  "+formattedPhoneNumber+"')]/parent::span/parent::label/parent::div/child::input")).click();
	}

	//This method will be used for checking first enabled checkbox.
	//Author : Tarun
	public void clickFirstEnabledCheckbox()
	{
		try {
			Utilities.waitForElementVisible(firstAALCheckbox);
			if (firstAALCheckbox.isEnabled()) {
				firstAALCheckbox.click();
			} else if (secondAALCheckbox.isEnabled()) {
				secondAALCheckbox.click();
			} else if (thirdAALCheckbox.isEnabled()) {
				thirdAALCheckbox.click();
			} else if (fourthAALCheckbox.isEnabled()) {
				fourthAALCheckbox.click();
			}
			else if (fifthAALCheckbox.isEnabled()) {
				fifthAALCheckbox.click();
			}
			else
			{
				Log.error("Not even single checkbox is enabled");
				System.out.println("Not even single checkbox is enabled");
				Assert.assertTrue(false);
			}
		}
		catch (Exception e)
		{
			Log.error("Not even single checkbox is enabled");
			System.out.println("Not even single checkbox is enabled");
			Assert.assertTrue(false);
		}
	}

	//This method will be used for checking first two enabled checkbox.
	//Author : Tarun
	public void clickFirstTwoEnabledCheckbox()
	{
		try
		{
			clickFirstEnabledCheckbox();
			if (secondAALCheckbox.isEnabled() && !secondAALCheckbox.isSelected()) {
				secondAALCheckbox.click();
			}
			else if (thirdAALCheckbox.isEnabled() && !thirdAALCheckbox.isSelected())
			{
				thirdAALCheckbox.click();
			}
			else if (fourthAALCheckbox.isEnabled() && !fourthAALCheckbox.isSelected())
			{
				fourthAALCheckbox.click();
			}
			else if (fifthAALCheckbox.isEnabled() && !fifthAALCheckbox.isSelected())
			{
				fifthAALCheckbox.click();
			}
			else
			{
				Log.error("No two checkboxes are enabled");
				System.out.println("No two checkboxes are enabled");
				Assert.assertTrue(false);
			}
		}
		catch (Exception e)
		{
			Log.error("No two checkboxes are enabled");
			System.out.println("No two checkboxes are enabled");
			Assert.assertTrue(false);
		}
	}
	//endregion Methods

}