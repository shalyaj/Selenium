package com.consensus.qa.verizon.tests;

import com.consensus.qa.framework.*;
import com.consensus.qa.pages.PaymentRequiredPage;
import com.consensus.qa.pages.ServiceProviderVerificationPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.consensus.qa.framework.Utilities.SelectDropMethod;
import com.consensus.qa.pages.GuestVerificationPage.IdType;

import javax.rmi.CORBA.Util;

import java.awt.*;
import java.io.IOException;

public class ReturnTests extends RetailBaseClass{

	String imei1 = "35799605310008";
	String orderId = "";
	public String carrierType = "Verizon"; 

	//region QA 53
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA_53_VerizonNonEdgeReturn(@Optional String testtype) throws IOException, InterruptedException
	{
		DependantTestCaseInputs dependantValues = Utilities.ReadFromCSV("QA_50");
		try {
			String iMEINumber1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.iPhone5C);
			Log.startTestCase("QA_53_VerizonNonEdgeReturn");

			// Selecting Carrier Responder
			selectCarrierResponderQA53(testtype);

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			// Switching to previous tab.A5661
			Utilities.switchPreviousTab();

			// POA Complete flow
			/*returnFlowQA53(testtype, dependantValues.ORDER_ID, dependantValues.STATE,IdType.valueOf(dependantValues.ID_TYPE),
					dependantValues.ID_NUMBER, dependantValues.FIRST_NAME, dependantValues.LAST_NAME, iMEINumber1);*/
			returnFlowQA53(testtype, "100050032", "CA",IdType.DRIVERLICENCE,
					"579468249", "Test", "Ratna", "99000342708022");

			// POS Verification
			inventoryManagementVerification(imei1);

			// Shipadmin Verification
			shipAdminVerification(orderId);

			// Not verifiable for Carrier Responder, since it goes to Support Center
			//Assert.assertTrue(eventLogTableContent.contains(Constants.ASSIGNED_RETURN_ORDER_NUMBER));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	//endregion QA 53

	//region QA-73
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA73_VerizonEdgeUpgradeAndReturn(@Optional String testtype)
	{
		boolean  stopActivation = false;
		try
		{
			//This TC requires

			Log.startTestCase("QA73_VerizonEdgeUpgradeAndReturn");

			String phoneNumber = "8553835666";
			String iMEINumber = "9886886321";
			System.out.println(iMEINumber);
			String simNumber = "12345678901234567890";
			String receiptID = "132710003003680723";


			//Login
			PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId2766"));
			//Verify whether which enviorement to use internal or external.
			testtype ="internal";
			if(testtype.equals("internal"))
			{


			}
			else
			{
				//Script for external testing.
			}
			PageBase.HomePageRetail().newGuestButton.click();

			//Home Page
			Utilities.waitForElementVisible(PageBase.HomePageRetail().upgradeEligibilityCheckerLink);
			PageBase.HomePageRetail().guestLookupTab.click();

			//Customer Lookup Page
			Utilities.waitForElementVisible(PageBase.CustomerLookupPage().receiptIdTextbox);
			PageBase.CustomerLookupPage().receiptIdTextbox.sendKeys(receiptID);
			PageBase.CustomerLookupPage().submitButton.click();
			//Order History Page
			try
			{
				Utilities.waitForElementVisible(PageBase.OrderHistory().firstCompletedLink, 12);
				PageBase.OrderHistory().firstCompletedLink.click();
			}
			catch(Exception e){}

			//Guest Verification Page
			Utilities.waitForElementVisible(PageBase.GuestVerificationPage().idTypeDropdown);
			PageBase.GuestVerificationPage().populateGuestVerificationDetails(IdType.DRIVERLICENCE, "CA",
					"123456789", "TEST", "TESTER");
			PageBase.CommonControls().continueCommonButton.click();

			//Return or Exchange Scan Device Page
			Utilities.waitForElementVisible(PageBase.ReturnScanDevicePage().esnIemeidTextbox);
			PageBase.ReturnScanDevicePage().esnIemeidTextbox.sendKeys(iMEINumber);
			PageBase.CustomerLookupPage().continueButton.click();

			//Return or Exchange Verification Page
			Utilities.waitForElementVisible(PageBase.ReturnOrExhangePreConditions().continueREVButton);
			PageBase.ReturnOrExhangePreConditions().SelectPreconditions();
			PageBase.ReturnOrExhangePreConditions().continueREVButton.click();

			//Return or Exchange Verification
			PageBase.ReturnOrExchangeVerificationPage().proceedEXCHANGE.click();
			PageBase.ReturnOrExchangeVerificationPage().returnDEVICE.click();
			Utilities.dropdownSelect(PageBase.ReturnOrExchangeVerificationPage().returnReasons,
					SelectDropMethod.SELECTBYINDEX, "1");
			PageBase.CustomerLookupPage().continueButton.click();

			//Verizon Account Password
			Utilities.waitForElementVisible(PageBase.VerizonAccountPassword().continueButton);
			PageBase.VerizonAccountPassword().continueButton.click();

			//Support Center Page, currently this is the expected behaviour
			Utilities.waitForElementVisible(PageBase.CommonControls().supportCenterText);
			PageBase.CommonControls().supportCenterText.isDisplayed();

			Log.endTestCase("QA73_VerizonEdgeUpgradeAndReturn");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			System.out.println(ex.getMessage());
			Utilities.driverTakesScreenshot("QA73_VerizonEdgeUpgradeAndReturn");
			Assert.assertTrue(false);
		}
		finally
		{

		}
	}
	//endregion QA-73

	//region QA-70
	@Test(groups={"verizon"})
	public void QA_70_VZNEdge_ReturnAALToFamilyPlan_UpgradeKeepingExistingPlan() 
			throws IOException, InterruptedException, AWTException
	{
		Reporter.log("<h4><font color=blue> RETURN/EXCHANGE ORDERS ARE STOPPED TILL SUPPORT PAGE. "
				+ "<br>SCRIPTS ARE TO BE UPDATED ONCE THE API IS AVAILABLE"
				+ "Hence Upgrade is not expected right now!!!</font>");
		DependantTestCaseInputs dependantValues = Utilities.ReadFromCSV("QA_69");
		String testtype = readConfig("test-type");
		Log.startTestCase("QA_70_VerizonNonEdgeReturn");

		// Selecting Carrier Responder
		selectTestTypeSettings_QA70(testtype);

		// Switching to previous tab.
		Utilities.switchPreviousTab();

		//Calling DBError utility to  find initial count or error in log files.
		DBError.navigateDBErrorPage();
		int initialCount= PageBase.AdminPage().totalErrorCount();

		// Switching to previous tab.A5661
		Utilities.switchPreviousTab();

		// POA Complete flow
		poaCompleteFlow_QA70(dependantValues);

		// POS Verification
		inventoryManagementVerification(imei1);

		// Shipadmin Verification
		shipAdminVerification(orderId);

		//DBError Verification
		DBError.navigateDBErrorPage();
		Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));
		//Assert.assertTrue(eventLogTableContent.contains(Constants.ASSIGNED_RETURN_ORDER_NUMBER));


	}
	//endregion QA-70

	//region Private Methods and Refacotored Codes

	private void selectCarrierResponderQA53(@Optional String testtype) throws IOException, InterruptedException, AWTException
	{
		// Verify whether which environment to use internal or external.
		testtype = BrowserSettings.readConfig("test-type");
		if(testtype.equals("internal")){
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType,BrowserSettings.readConfig("internalTestType"));

			//Selecting Use Case from dropdown list.
			PageBase.AdminPage().selectAPIConfig(carrierType);
		}
		else{
			//External// Need to set External server from Admin page.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType,"External");
		}
	}

	private String returnFlowQA53(@Optional String testType, String parentOrderId, String state,
			IdType idType, String idNumber, String firstName, String lastName, String iMEI) throws IOException
	{
		//Login to retail page.
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"),
				Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId2766"));

		PageBase.HomePageRetail().guestLookupTab.click();

		PageBase.CustomerLookupPage().viewGuestOrders.click();
		PageBase.CustomerLookupPage().continueButton.click();

		//Select a Particular Order
		driver.navigate().to(readConfig("CustomerVerification") + parentOrderId);

		Utilities.waitForElementVisible(PageBase.GuestVerificationPage().idTypeDropdown);
		PageBase.GuestVerificationPage().populateGuestVerificationDetails(idType, state,
				idNumber, firstName, lastName);

		Utilities.waitForElementVisible(PageBase.ReturnScanDevicePage().orderID);
		orderId = PageBase.ReturnScanDevicePage().orderID.getText();
		PageBase.ReturnScanDevicePage().esnIemeidTextbox.sendKeys(iMEI);
		PageBase.CustomerLookupPage().continueButton.click();

		PageBase.ReturnOrExhangePreConditions().SelectPreconditions();
		PageBase.ReturnOrExhangePreConditions().continueREVButton.click();
		PageBase.ReturnOrExchangeVerificationPage().proceedEXCHANGE.click();
		PageBase.ReturnOrExchangeVerificationPage().returnDEVICE.click();
		Utilities.dropdownSelect(PageBase.ReturnOrExchangeVerificationPage().returnReasons,
				SelectDropMethod.SELECTBYINDEX, "1");
		PageBase.CustomerLookupPage().continueButton.click();

		if(driver.getCurrentUrl().contains("passwordcapture"))
		{
			PageBase.VerizonAccountPassword().password.sendKeys("Hello");
			PageBase.VerizonAccountPassword().continueButton.click();
		}
		else if(driver.getCurrentUrl().contains("printticket"))
		{
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();
		}
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Support Center')]")).isDisplayed());

		// Commenting below Assertions as this doesn't show up in Carrier Responder

		if(driver.getCurrentUrl().contains("passwordcapture"))
		{
			PageBase.VerizonAccountPassword().password.sendKeys("Hello");
			PageBase.VerizonAccountPassword().continueButton.click();
		}
		//*PageBase.CommonControls().continueButton.click();

		// RMSS Assertions - Todo from Parent Order
		/*Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(),
					"");
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneTaxValuePMSSText.getText(),
					"");
			PageBase.PrintMobileScanSheetPage().barcodePMSSImage.isDisplayed();

			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();


			Assert.assertTrue(PageBase.ReturnConfirmation().returnConfirmation.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().successfullyReturnedString.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().linePhonenoString.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().returnStep1Text.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().returnStep2Text.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().printInstruction.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().returnHome.isDisplayed());*/
		return orderId;
	}

	private void selectTestTypeSettings_QA70(String testtype) throws InterruptedException, AWTException, IOException{
		// Verify whether which environment to use internal or external.
		testtype = BrowserSettings.readConfig("test-type");
		if(testtype.equals("internal")){
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			if(readConfig("internalTestType").toLowerCase().contains("simulator")){
				PageBase.AdminPage().navigateToSimulator();
			}
			else{
				Reporter.log("Using Carrier Responder  <br> <p>", true);
				PageBase.AdminPage().selectWebAPIResponse(carrierType, "CarrierResponder");
				PageBase.AdminPage().selectAPIConfig("Verizon");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().verizonCarrierTab);
				PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
				
				//Select Return xml
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().versionsDropdown);
				PageBase.CarrierResponseXMLPage().selectOptions("current", 
						"returnOrExchangeDevice", "default_return.xml");
				Reporter.log("<br>XML Used: returnOrExchangeDevice - default_return.xml", true);
				
				//Save XML
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().saveResponseButton);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				
				Reporter.log("<br>Carrier Responder Changes Done!!!", true);
			}
		}
		else{
			//External// Need to set External server from Admin page.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType,"External");
		}
	}

	private String poaCompleteFlow_QA70(DependantTestCaseInputs dependantValues) throws IOException{
		//Login to retail page.
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"),
				Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		PageBase.HomePageRetail().guestLookupTab.click();

		PageBase.CustomerLookupPage().receiptIdTextbox.sendKeys(dependantValues.RECEIPT_ID);
		Reporter.log(dependantValues.RECEIPT_ID);

		java.util.List<WebElement> SubmitButtons = driver.findElements(By.xpath(ControlLocators.SUBMIT_RECEIPTID));
		for(WebElement visibleSubmitButton: SubmitButtons){
			if(visibleSubmitButton.isDisplayed())
			{visibleSubmitButton.click(); break; }
		}

		PageBase.CarrierCreditCheckPage().firstNameTextBox.sendKeys(dependantValues.FIRST_NAME);
		PageBase.CarrierCreditCheckPage().lastNameTextBox.sendKeys(dependantValues.LAST_NAME);
		Reporter.log("<br>First Name: "+ dependantValues.FIRST_NAME);
		Reporter.log("  Last Name: "+ dependantValues.LAST_NAME +"; ");
		ExchangeTests.SelectLicense(dependantValues.ID_TYPE.toString(), 
				PageBase.ReturnOrExchangeVerificationPage().idTypeDropdown);
		Reporter.log("State: "+ dependantValues.STATE +"; ");
		Utilities.dropdownSelect(PageBase.ReturnOrExchangeVerificationPage().stateDropdown,
				Utilities.SelectDropMethod.SELECTBYTEXT, dependantValues.STATE);
		Reporter.log("ID Type: "+ dependantValues.ID_TYPE +"; ");
		PageBase.ReturnOrExchangeVerificationPage().idNumberTextbox.sendKeys(dependantValues.ID_NUMBER);
		Reporter.log("ID Number: "+ dependantValues.ID_NUMBER +".");
		PageBase.CommonControls().continueCommonButton.click();
		
		Utilities.waitForElementVisible(PageBase.ReturnScanDevicePage().esnIemeidTextbox);
		PageBase.ReturnScanDevicePage().esnIemeidTextbox.click();
		PageBase.ReturnScanDevicePage().esnIemeidTextbox.clear();
		PageBase.ReturnScanDevicePage().esnIemeidTextbox.sendKeys(dependantValues.ESN_IMEI1);
		PageBase.ReturnOrExchangeVerificationPage().continueEXCHANGE.click();

		PageBase.ReturnOrExhangePreConditions().SelectPreconditions();
		PageBase.ReturnOrExhangePreConditions().continueREVButton.click();
		PageBase.ReturnOrExchangeVerificationPage().proceedEXCHANGE.click();
		PageBase.ReturnOrExchangeVerificationPage().returnDEVICE.click();
		Utilities.dropdownSelect(PageBase.ReturnOrExchangeVerificationPage().returnReasons,
				SelectDropMethod.SELECTBYINDEX, "1");
		PageBase.CustomerLookupPage().continueButton.click();

		if(driver.getCurrentUrl().contains("passwordcapture"))
		{
			PageBase.VerizonAccountPassword().password.sendKeys("Hello");
			PageBase.VerizonAccountPassword().continueButton.click();
		}
		else if(driver.getCurrentUrl().contains("printticket"))
		{
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();
		}
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(), 'Support Center')]")).isDisplayed());

		// Commenting below Assertions as this doesn't show up in Carrier Responder

		if(driver.getCurrentUrl().contains("passwordcapture"))
		{
			PageBase.VerizonAccountPassword().password.sendKeys("Hello");
			PageBase.VerizonAccountPassword().continueButton.click();
		}
		//*PageBase.CommonControls().continueButton.click();

		// RMSS Assertions - Todo from Parent Order
		/*Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(),
					"");
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneTaxValuePMSSText.getText(),
					"");
			PageBase.PrintMobileScanSheetPage().barcodePMSSImage.isDisplayed();

			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();


			Assert.assertTrue(PageBase.ReturnConfirmation().returnConfirmation.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().successfullyReturnedString.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().linePhonenoString.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().returnStep1Text.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().returnStep2Text.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().printInstruction.isDisplayed());
			Assert.assertTrue(PageBase.ReturnConfirmation().returnHome.isDisplayed());*/
		return orderId;
	}

	private void inventoryManagementVerification(String imei1) throws InterruptedException, AWTException, java.io.IOException {
		PageBase.InventoryManagementPage().launchInventoryInNewTab();
		Utilities.implicitWaitSleep(5000);
		PageBase.InventoryManagementPage().verifyDeviceStatus(imei1, InventoryManagementBaseClass.IMEIStatus.Sold.toString());
	}

	private void shipAdminVerification(String orderId) {
		ShipAdminBaseClass.launchShipAdminInNewTab();
		PageBase.OrderSummaryPage().goToOrderSummaryPage(orderId);
		String eventLogTableContent = PageBase.OrderSummaryPage().checkForErrorAndLog(orderId);
		String status = PageBase.OrderSummaryPage().getOrderStatus();
		Assert.assertEquals(status, Constants.SHIPPED);
	}
	//endregion Private Methods and Refacotored Codes
}