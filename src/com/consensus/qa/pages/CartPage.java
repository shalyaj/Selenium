package com.consensus.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.consensus.qa.framework.ControlLocators;
import com.consensus.qa.framework.PageBase;

public class CartPage {

	/* region Page Initialization */
	public CartPage(WebDriver d)
	{
		PageFactory.initElements(d, this);
	}
	/* end region Page Initialization */

	/* region Cart Page Elements */
	@FindBy(id = ControlLocators.CLEAR_CART_BUTTON)
	public WebElement clearCartButton;

	@FindBy(id = ControlLocators.CONTINUE_CART_BUTTON)
	public WebElement continueCartButton;

	@FindBy(id = ControlLocators.FIRST_ASSIGN_NUMBER_DROPDOWN)
	public WebElement firstAssignNumberDropdown;
	
	@FindBy(id = ControlLocators.SECOND_ASSIGN_NUMBER_DROPDOWN)
	public WebElement secondAssignNumberDropdown;
	
	@FindBy(xpath = ControlLocators.FIRST_PHONE_PRICE_TEXT)
	public WebElement firstPhonePriceText;
	
	@FindBy(xpath = ControlLocators.SECOND_PHONE_PRICE_TEXT)
	public WebElement secondPhonePriceText;
	
	@FindBy(xpath = ControlLocators.FIRST_PHONE_MODEL_LINK)
	public WebElement firstPhoneModelLink;	
	
	@FindBy(xpath = ControlLocators.SECOND_PHONE_MODEL_LINK)
	public WebElement secondPhoneModelLink;

	@FindBy(xpath = ControlLocators.PHONE_PRICE_AAL_TEXT)
	public WebElement phonePriceAALText;

	@FindBy(xpath = ControlLocators.PHONE_MODEL_AAL_LINK)
	public WebElement phoneModelAALLink;

	@FindBy(xpath = ControlLocators.DEVICE1_PRICE)
	public WebElement device1Price;
	
	@FindBy(xpath = ControlLocators.DEVICE2_PRICE)
	public WebElement device2Price;
	
	@FindBy(xpath = ControlLocators.DEVICE3_PRICE)
	public WebElement device3Price;
	
	@FindBy(xpath = ControlLocators.PLAN_PRICE)
	public WebElement planPriceActual;
	/* end region Cart Page Elements */

	//Region - Methods
	public void selectPhoneNumber()
	{
		Select assignNumber = new Select(PageBase.CartPage().firstAssignNumberDropdown);
		assignNumber.selectByIndex(1);	
	}
	//EndRegion
}
