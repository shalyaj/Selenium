package com.consensus.qa.pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.consensus.qa.framework.ControlLocators;
import com.consensus.qa.framework.InventoryManagementBaseClass;
import com.consensus.qa.framework.PageBase;
import com.consensus.qa.framework.Utilities;
import com.consensus.qa.framework.Utilities.SelectDropMethod;

public class InventoryManagementPage extends InventoryManagementBaseClass{
	public InventoryManagementPage(WebDriver d)
	{
		PageFactory.initElements(d, this);
	}
	
	@FindBy(id = ControlLocators.SERIALIZED_PRODUCTS)
	public WebElement serializedProducts;
	
	@FindBy(id = ControlLocators.USERNAME_INVENTORY_TEXTBOX)
	public WebElement userNameTextbox;
	
	@FindBy(id = ControlLocators.PASSWORD__INVENTORY_TEXTBOX)
	public WebElement passwordTextbox;
	
	@FindBy(id = ControlLocators.STORE_INVENTORY_TEXTBOX)
	public WebElement storeIDTextbox;
	
	@FindBy(xpath = ControlLocators.STORE_INVENTORY_SELECT)
	public WebElement storeSelect;
	
	@FindBy(xpath = ControlLocators.PRODUCTS_INVENTORY_LINK)
	public WebElement productsLink;
	
	@FindBy(id = ControlLocators.IMEI_INVENTORY_TEXTBOX)
	public WebElement imeiTextbox;
	
	@FindBy(id = ControlLocators.SUBMIT_INVENTORY)
	public WebElement submitButton;
	
	@FindBy(id = ControlLocators.IFRAME_INVENTORY)
	public WebElement iframeInventory;
	
	@FindBy(xpath = ControlLocators.SERIALIZEDSTATUS_INVENTORY)
	public WebElement statusInventory;

	@FindBy(xpath = ControlLocators.PURCHASING_INVENTORY_LINK)
	public WebElement purchasingLink;

	@FindBy(id = ControlLocators.RECEIVING)
	public WebElement receivingLink;

	@FindBy(id = ControlLocators.RECEIVE_NEW_SHIPMENT)
	public WebElement receiveNewShipmentButton;

	@FindBy(id = ControlLocators.VENDOR_DROPDOWN)
	public WebElement vendorDropdown;

	@FindBy(id = ControlLocators.PRODUCT_TEXTBOX)
	public WebElement productTextbox;

	@FindBy(id = ControlLocators.ADD_PRODUCT_BUTTON)
	public WebElement addProductButton;

	@FindBy(id = ControlLocators.SCAN_CODE_TEXTBOX)
	public WebElement scanCodeTextbox;

	@FindBy(id = ControlLocators.ADD_TO_INVENTORY)
	public WebElement addToInventory;

	@FindBy(id = ControlLocators.PRODUCT_AUTOCOMPLETE_TEXTBOX)
	public WebElement productAutocompleteTextbox;
	
	//Region- Methods
	/* region Inventory Login Page Methods */
	public void verifyDeviceStatus(String imei1, String imei2, String imei3, String status)
	{	
		productsLink.click();
		serializedProducts.click();
		driver.switchTo().frame(iframeInventory);
		imeiTextbox.sendKeys(imei1);
		submitButton.click();
		Assert.assertTrue(statusInventory.getText().contains(status));
		imeiTextbox.clear();
		imeiTextbox.sendKeys(imei2);
		submitButton.click();
		Assert.assertTrue(statusInventory.getText().contains(status));
		imeiTextbox.clear();
		imeiTextbox.sendKeys(imei3);
		submitButton.click();
		Assert.assertTrue(statusInventory.getText().contains(status));
		driver.switchTo().defaultContent();
	}

	public void verifyDeviceStatus(String iMEI1, String iMEI2, String status)
	{	
		productsLink.click();
		serializedProducts.click();
		driver.switchTo().frame(iframeInventory);
		PageBase.InventoryManagementPage().imeiTextbox.sendKeys(iMEI1);
		PageBase.InventoryManagementPage().submitButton.click();
		Utilities.implicitWaitSleep(4000);
		//Assert.assertTrue(PageBase.InventoryManagementPage().statusInventory.getText().contains(status),
			//	"Status of IMEI1 "+iMEI1+" in the POS is not as expected. Expected is "+status);
		PageBase.InventoryManagementPage().imeiTextbox.clear();
		PageBase.InventoryManagementPage().imeiTextbox.sendKeys(iMEI2);
		PageBase.InventoryManagementPage().submitButton.click();
		Utilities.implicitWaitSleep(4000);
		//Assert.assertTrue(PageBase.InventoryManagementPage().statusInventory.getText().contains(status),
				//"Status of IMEI2 "+iMEI2+" in the POS is not as expected. Expected is "+status);
		driver.switchTo().defaultContent();
	}
	
	public void verifyDeviceStatus(String iMEI, String status)
	{	
		productsLink.click();
		serializedProducts.click();
		driver.switchTo().frame(iframeInventory);
		Utilities.waitForElementVisible(PageBase.InventoryManagementPage().imeiTextbox);
		imeiTextbox.sendKeys(iMEI);
		submitButton.click();
		Utilities.implicitWaitSleep(3000);
		Utilities.waitForElementVisible(statusInventory);
		Utilities.implicitWaitSleep(4000);
		Assert.assertTrue(statusInventory.getText().contains(status));
		driver.switchTo().defaultContent();
	}

	public void addDeviceToInventory(String iMEI, String Product)
	{
		try {
			Robot robot = new Robot();
			purchasingLink.click();
			receivingLink.click();
			driver.switchTo().frame(iframeInventory);
			receiveNewShipmentButton.click();
			Utilities.waitForElementVisible(vendorDropdown);
			Utilities.dropdownSelect(vendorDropdown, SelectDropMethod.SELECTBYTEXT, "BSupplier");
			productTextbox.sendKeys(Product);
			Utilities.implicitWaitSleep(2000);
			productTextbox.sendKeys(Keys.ARROW_DOWN);
			productTextbox.sendKeys(Keys.RETURN);
			Utilities.implicitWaitSleep(2000);
			Utilities.waitForElementVisible(addProductButton);
			addProductButton.click();
			Utilities.waitForElementVisible(scanCodeTextbox);
			scanCodeTextbox.sendKeys(iMEI);
			Utilities.implicitWaitSleep(2000);
			scanCodeTextbox.sendKeys(Keys.RETURN);
			Utilities.implicitWaitSleep(4000);
			addToInventory.click();
			robot.keyPress(KeyEvent.VK_ENTER);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* end region Inventory Login Page Methods */
	//EndRegion - Methods
}
