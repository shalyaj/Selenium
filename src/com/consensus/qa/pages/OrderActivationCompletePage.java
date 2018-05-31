package com.consensus.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.consensus.qa.framework.ControlLocators;

public class OrderActivationCompletePage {

	/* region Page Initialization */
	public OrderActivationCompletePage(WebDriver d)
    {
        PageFactory.initElements(d, this);
    }	
	/* end region Page Initialization */
	
	@FindBy(className = ControlLocators.ACTIVATION_COMPLETE)
	public WebElement ActivationComplete;
	
	@FindBy(xpath = ControlLocators.ORDER_AND_ACTIVATION_COMPLETE_TEXT)
	public WebElement orderAndActivationCompleteText;
	
	@FindBy(xpath = ControlLocators.ORDER_NUMBER_VALUE_TEXT)
	public WebElement orderNumberValueText;
	
	@FindBy(xpath = ControlLocators.PHONE_NUMBER_VALUE_TEXT)
	public WebElement phoneNumberValueText;
	
	@FindBy(xpath = ControlLocators.PHONE_NUMBER_VALUE_2_TEXT)
	public WebElement phoneNumberValue2Text;
	
	@FindBy(xpath = ControlLocators.IMEI_NUMBER_VALUE_TEXT)
	public WebElement iMEINumberValueText;
	
	@FindBy(xpath = ControlLocators.IMEI_NUMBER_VALUE_2_TEXT)
	public WebElement iMEINumberValue2Text;
	
	@FindBy(xpath = ControlLocators.SIM_NUMBER_VALUE_TEXT)
	public WebElement simNumberValueText;
	
	@FindBy(xpath = ControlLocators.SIM_NUMBER_VALUE_2_TEXT)
	public WebElement simNumberValue2Text;
	
	@FindBy(xpath = ControlLocators.PRICE_VALUE_TEXT)
	public WebElement priceValueText;
	
	@FindBy(xpath = ControlLocators.PRICE_VALUE_2_TEXT)
	public WebElement priceValue2Text;
	
	
	@FindBy(xpath = ControlLocators.DISCOUNT_VALUE_TEXT)
	public WebElement discountValueText;
	
	@FindBy(xpath = ControlLocators.SUBTOTAOL_VALUE_TEXT)
	public WebElement subtotalValueText;
	
	
	
}