package com.consensus.qa.sprint.tests;

import com.consensus.qa.framework.*;
import com.consensus.qa.framework.CSVOperations.FileName;
import com.consensus.qa.pages.PaymentRequiredPage;
import com.consensus.qa.pages.ServiceProviderVerificationPage;
import com.consensus.qa.tests.CarrierCreditCheckDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class AddNewLineTests extends RetailBaseClass {

	//region Variable Declaration
	public String iMEINumber1 = ""; //ToDo: Need to read from data sheet.
	public String imei1 = "";
	public String imei2 = ""; //ToDo: Need to read from data sheet.
	public String imei3 = ""; //ToDo: Need to read from data sheet.
	public String carrierType = "Verizon";  //ToDo: Need to read from data sheet.
	public String cartDevice1price = "";
	public String cartDevice2price = "";
	public String cartDevice3price = "";
	public String cartPlanprice = "";
	public String simType1 = "";
	String receiptId = "";
	//endregion

	//region QA 1705
	@Test(groups = {"sprint"})
	@Parameters("test-type")
	public void QA_1705_Sprint2YrContractWithNewActivation(@Optional String testType) {
		String orderId = "";
		try {
			Log.startTestCase("QA_1705_Sprint2YrContractWithNewActivation");
			testType = BrowserSettings.readConfig("test-type");

			// Verify whether which enviorement to use internal or external.
			selectingCarrierEnviornment(testType);

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			//Calling DBError utility to  find initial count or error in log files.
			//DBError.navigateDBErrorPage();
			//int initialCount = PageBase.AdminPage().totalErrorCount();

			// Switching to previous tab.
			//Utilities.switchPreviousTab();

			orderId = poaCompleteFlow(testType);

			//Inventory Management Page verification.
			if (readConfig("Activation").contains("true")) {
				inventoryManagementVerification();

				//Ship Admin Verification -orderId= ""
				shipAdminVerification(orderId);
			}

			//DBError Verification.
			//DBError.navigateDBErrorPage();
			//Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Reporter.log("<h3>QA_1705_Sprint2YrContractWithNewActivation - Test Case Completes<h3>");
			Log.endTestCase("QA_1705_Sprint2YrContractWithNewActivation");
		} catch (Exception ex) {
			Log.error(ex.getMessage());
			Utilities.driverTakesScreenshot("QA_1705_Sprint2YrContractWithNewActivation");
			Assert.fail();
		}
	}
	//endregion QA 1705

	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA_4255_SprintAALToExistingOptEasyPayAlterDownPay(@Optional String testType) throws IOException, InterruptedException{
		String orderId = "";
		testType = BrowserSettings.readConfig("test-type");
		Log.startTestCase("QA_4255_SprintAALToExistingOptEasyPayAlterDownPay");
		testType = BrowserSettings.readConfig("test-type");

		//			// Verify whether which enviorement to use internal or external.
		//			selectingCarrierEnviornment_QA_4255(testType);
		//
		//			// Switching to previous tab.
		//			Utilities.switchPreviousTab();
		//
		//			// //Calling DBError utility to  find initial count or error in log files.
		//			DBError.navigateDBErrorPage();
		//			int initialCount = PageBase.AdminPage().totalErrorCount();
		//
		//			// // Switching to previous tab.
		//			Utilities.switchPreviousTab();

		orderId = poaCompleteFlow_QA_4255(testType);

		//			//Inventory Management Page verification.
		//			if (readConfig("Activation").contains("true")) {
		//				inventoryManagementVerification();
		//
		//				//Ship Admin Verification -orderId= ""
		//				shipAdminVerification(orderId);
		//			}

		//DBError Verification.
		//DBError.navigateDBErrorPage();
		//Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));
	

		Reporter.log("<h3>QA_1705_Sprint2YrContractWithNewActivation - Test Case Completes<h3>");
		Log.endTestCase("QA_1705_Sprint2YrContractWithNewActivation");

	}

	//region private methods
	private void selectingCarrierEnviornment(@Optional String testType) throws InterruptedException, AWTException, java.io.IOException {
		if (testType.equals("internal")) {
			// Need to set "Backend Simulator or Carrier Responder depend on test case  requirement.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();

			//Selecting Backed Simulator.
			selectingBackendSimulatorForQA1705();

			//Selecting Carrier Responder
			//selectCarrierResponderQA1705();
		} else   //External
		{
			// Need to set External server from Admin page.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse("Verizon", "External");
		}
	}

	private String poaCompleteFlow(@Optional String testType) throws IOException {
		String orderId = "";//Login to retail page.

		lStartTime = new Date().getTime();
		pageName = readPageName("PoaLogin");
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"),

				Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));
		Utilities.webPageLoadTime(lStartTime, pageName);

		// Click on Sales & Activations page.
		lStartTime = new Date().getTime();
		pageName = readPageName("SaleAndActivation");
		PageBase.HomePageRetail().salesAndActivationsLink.click();
		Utilities.webPageLoadTime(lStartTime, pageName);

		// Click on New Activation link.
		lStartTime = new Date().getTime();
		pageName = readPageName("DeviceScan");
		PageBase.ChoosePathPage().newActivation.click();
		Utilities.webPageLoadTime(lStartTime, pageName);

		// Scanning smart phone,basic phone and MIFI phone.
		imei1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.SamsungGalaxyS4_16GBWhite);
		imei2 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.SamsungGalaxyS4_16GBWhite);
		PageBase.DeviceScanPage().enterDeviceScanDetails(imei1);
		PageBase.VerizonEdgePage().declineSprintEasyPay();
		PageBase.DeviceScanPage().enterDeviceScanDetails(imei2);
		Utilities.waitForElementVisible(PageBase.CommonControls().continueButtonDVA);
		PageBase.CommonControls().continueButtonDVA.click();

		//Filling information in Carrier Credit Check Page.
		Utilities.waitForElementVisible(PageBase.CarrierCreditCheckPage().skip);
		String ssn = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.SSNWithDeposit);
		CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails(ssn);
		PageBase.CarrierCreditCheckPage().populatingCarrierCreditCheckPage(cccDetails);

		PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();

		lStartTime = new Date().getTime();
		pageName = readPageName("CarrierCreditCheck");
		PageBase.CommonControls().continueButton.click();
		Utilities.implicitWaitSleep(1000);
		try {
			if (PageBase.CommonControls().continueButton.isEnabled())
				PageBase.CommonControls().continueButton.click();
		} catch (Exception e) {

		}
		//if(testType.equals("internal")) PageBase.CommonControls().continueButton.click();
		Utilities.webPageLoadTime(lStartTime, pageName);

		// Credit Check Verification Results with deposits.
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		boolean exists = driver.findElements(By.id("checkbox-deposit-1")).size() != 0;
		if (exists) {
			Reporter.log("<br> Credit Check Completes.");
			PageBase.CreditCheckVerificationResultsPage().depositCheckBox.click();
			Reporter.log("<br> Selected Deposit Check Box ");
		} else {
			PageBase.CreditCheckVerificationResultsPage().creditCheckPassChkBox.click();
		}
		PageBase.CommonControls().continueCommonButton.click();

		// Selecting Plan.
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().sprintFamilySharePlan);
		PageBase.VerizonShopPlansPage().sprintFamilySharePlan.click();
		PageBase.VerizonShopPlansPage().addPlan();

		//Verifying device with plan and continue.
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		cartDevice1price = PageBase.CartPage().device1Price.getText();
		cartDevice2price = PageBase.CartPage().device2Price.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Selecting plan feature.
		Utilities.waitForElementVisible(PageBase.SelectPlanFeaturesPage().continueSPFButton);
		PageBase.SelectPlanFeaturesPage().continueSPFButton.click();

		// Selecting No Insurance .
		Utilities.waitForElementVisible(PageBase.CommonControls().continueButton);
		PageBase.SelectProtectionPlanInsurancePage().selectNoInsuranceForTwoDevices();

		// Selecting No Number Porting.
		Utilities.waitForElementVisible(PageBase.NumberPortPage().noNumberPortRadiobutton);
		PageBase.NumberPortPage().noNumberPortRadiobutton.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		PageBase.ServiceProviderVerificationPage().populatingSprintSPV("1234567","3","An");

		// Order Review and Confirm Page.
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);

		//TODO: Need to read from data sheet.
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().device1PriceActual.getText(),
				cartDevice1price);


		PageBase.CommonControls().continueCommonButton.click();

		if (readConfig("Activation").contains("true")) {
			//Terms and Condition Page.
			Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().continueTCButton);
			PageBase.TermsandConditionsPage().emailTCChkBox.click();
			PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
			PageBase.TermsandConditionsPage().continueTCButton.click();

			// Credit Check Verification Results with deposits.
			Utilities.waitForElementVisible(PageBase.CommonControls().continueButtonDVA);
			boolean exist = driver.findElements(By.id("checkbox-mini-1")).size() != 0;
			if (exist) {
				PageBase.CreditCheckVerificationResultsPage().creditCheckPassChkBox.click();
				PageBase.CommonControls().continueButtonDeposit.click();
			}

			// Credit card payment  page is coming.
			boolean visaexists = driver.findElements(By.id("radio-1a")).size() != 0;
			if (visaexists) {
				Utilities.waitForElementVisible(PageBase.PaymentRequiredPage().visaTab);
				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(PaymentRequiredPage.CardType.VISA);
				PageBase.PaymentRequiredPage().continuePRButton.click();
			}

			//Print Mobile Scan Sheet.
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().verifyAllTwoDeviceBarCode();

			//TODO:Need to add assertion for store location.
			//		WebElement web = driver.findElement(By.xpath("//span[contains(text(),'2766 - TARGET - SAN FRANCISCO CENTRAL')]"));
			//		String storeLocation = web.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			// Payment Verification page. Scan Reciept id.
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.ReceiptId));
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation page. Scan Device IEMI and enter SIM number.
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceVerificationActiavtionFor3Devices(imei1, imei2, "");

			// WCA Signature page activity and verifications.
			Utilities.ClickElement(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox);
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);

			java.util.List<WebElement> wcaLists = driver.findElements(By.xpath("//div[@class ='termsandcondition'][1]/label"));
			Assert.assertEquals("Application ID No.:", wcaLists.get(0).getText());
			Assert.assertEquals("Order Date:", wcaLists.get(1).getText());
			Assert.assertEquals("Bill Acct. No.:", wcaLists.get(2).getText());
			Assert.assertEquals("Agent Name:", wcaLists.get(3).getText());
			Assert.assertEquals("Activation Type:", wcaLists.get(4).getText());

			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			// Order Activation Complete page.
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().ActivationComplete);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().ActivationComplete.isDisplayed());
			String orderIdfromActPage = PageBase.OrderActivationCompletePage().orderNumberValueText.getText();
			Assert.assertTrue(PageBase.OrderActivationCompletePage().phoneNumberValueText.isDisplayed());
			//Assert.assertTrue(PageBase.OrderActivationCompletePage().device2PhoneNo.isDisplayed());
			Assert.assertTrue(PageBase.OrderActivationCompletePage().iMEINumberValueText.isDisplayed());
			//Assert.assertTrue(PageBase.OrderActivationCompletePage().device2IMEINo.isDisplayed());
		} else {
			Reporter.log("<h3><font color='red'> Activation is stopped purposefully. Change the key in Test Settings to Activate </h3></font>");
		}
		return orderId;
	}

	private void inventoryManagementVerification() throws InterruptedException, AWTException, java.io.IOException {
		PageBase.InventoryManagementPage().launchInventoryInNewTab();
		Utilities.implicitWaitSleep(5000);
		PageBase.InventoryManagementPage().verifyDeviceStatus(imei1, imei2, InventoryManagementBaseClass.IMEIStatus.Sold.toString());
	}

	private void shipAdminVerification(String orderId) {
		ShipAdminBaseClass.launchShipAdminInNewTab();
		Utilities.implicitWaitSleep(5000);
		PageBase.OrderSummaryPage().goToOrderSummaryPage(orderId);

		String eventLogTableContent = PageBase.OrderSummaryPage().checkForErrorAndLog(orderId);
		String status = PageBase.OrderSummaryPage().getOrderStatus();
		Assert.assertEquals(status, Constants.SHIPPED);
		Assert.assertTrue(eventLogTableContent.contains(Constants.RECEIPT_SUBMISSION_SUCCEEDED_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.SHIPPED_BUT_NOT_LITERALLY_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.ACTIVATION_SUCCEEDED_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.PARKING_SUCCEEDED_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.MAP_SUCCEEDED_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.ACTIVATION_ORDER_VALIDATION_PASSED));
		Assert.assertTrue(PageBase.OrderSummaryPage().orderNumberValueSALink.getText().contains(orderId));
		Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.PHONE_AND_PLAN));
		Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));
	}

	private void selectingBackendSimulatorForQA1705() {
		PageBase.AdminPage().selectWebAPIResponse("Sprint", "BackendSimulator");

		//Selecting Use Case from dropdown list.
		PageBase.AdminPage().selectAPIConfig("Sprint");

		//PageBase.AdminPage().selectCreaditReadUseCase("APPROVE_WITH_DEPOSIT");
		//PageBase.AdminPage().selectCreaditWriteUseCase("APPROVE_WITH_DEPOSIT");
		//PageBase.AdminPage().retrieveCustomerDetails("ELIGIBLE");

		PageBase.AdminPage().save();
	}

	private void selectCarrierResponderQA1705() {
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");

		//Selecting Carrier config file.
		PageBase.AdminPage().selectAPIConfig("Verizon");

		// Selecting Verizon and response xml.
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().verizonCarrierTab);
		PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().versionsDropdown);
		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitCreditApplication", "approved_with_deposit.xml");
		Utilities.implicitWaitSleep(3000);
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().saveResponseButton);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		//PageBase.CarrierResponseXMLPage().loadResponseButton.click();
	}

	private CarrierCreditCheckDetails getCarrierCreditCheckDetails(String ssn) throws IOException {
		CarrierCreditCheckDetails cccDetails = new CarrierCreditCheckDetails();
		PageBase.CSVOperations();
		CustomerDetails customerDetails = CSVOperations.ReadCustomerDetailsFromCSV(ServiceProviderVerificationPage.IdType.DRIVERLICENCE);
		cccDetails.setFirstName(customerDetails.FirstName);
		cccDetails.setLastName(customerDetails.LastName);
		cccDetails.setAddress1(customerDetails.Address1);
		cccDetails.setCity(customerDetails.City);
		cccDetails.setState(customerDetails.State);
		cccDetails.setZip(customerDetails.Zip);
		cccDetails.setHomePhone(customerDetails.PhNum);
		cccDetails.setEmail(customerDetails.EMail);
		cccDetails.setBirthMonth(customerDetails.BirthdayMonth);
		cccDetails.setBirthDate(customerDetails.BirthdayDay);
		cccDetails.setBirthYear(customerDetails.BirthdayYear);
		cccDetails.setSSN(ssn);
		cccDetails.setIDType(ServiceProviderVerificationPage.IdType.DRIVERLICENCE);
		cccDetails.setIdTypeState(customerDetails.IDState);
		cccDetails.setIdNumber(customerDetails.IDNumber);
		cccDetails.setMonth(customerDetails.IDExpirationMonth);
		cccDetails.setYear(customerDetails.IDExpirationYear);
		return cccDetails;
	}
	//endregion private methods

	private void selectingCarrierEnviornment_QA_4255(@Optional String testType) throws InterruptedException, AWTException, java.io.IOException {
		if (testType.equals("internal")) {
			// Need to set "Backend Simulator or Carrier Responder depend on test case  requirement.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			if(readConfig("internalTestType").toLowerCase().contains("simulator")){
				PageBase.AdminPage().navigateToSimulator();

				PageBase.AdminPage().selectWebAPIResponse("Sprint", "BackendSimulator");
			}
			else
			{
				PageBase.AdminPage().selectWebAPIResponse("Sprint", "CarrierResponder");

				//Selecting Carrier config file.
				PageBase.AdminPage().selectAPIConfig("Sprint");

				// Selecting Verizon and response xml.
				//				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().verizonCarrierTab);
				//				PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
				//				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().versionsDropdown);
				//				PageBase.CarrierResponseXMLPage().selectOptions("current", "accountValidation", "approved_with_deposit.xml");
				Utilities.implicitWaitSleep(3000);
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().saveResponseButton);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			}

			//Selecting Carrier Responder
		} else   //External
		{
			// Need to set External server from Admin page.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse("Verizon", "External");
		}
	}

	private String poaCompleteFlow_QA_4255(@Optional String testType) throws IOException, InterruptedException {
		String orderId = "";//Login to retail page.
		String imei = "76698676879779"; //PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sprint_SamsungGalaxyS4_16GBWhite);

		lStartTime = new Date().getTime();
		pageName = readPageName("PoaLogin");
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"),

				Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));
		Utilities.webPageLoadTime(lStartTime, pageName);

		// Click on Sales & Activations page.
		lStartTime = new Date().getTime();
		pageName = readPageName("SaleAndActivation");
		PageBase.HomePageRetail().salesAndActivationsLink.click();
		Utilities.webPageLoadTime(lStartTime, pageName);

		// Click on New Activation link.
		lStartTime = new Date().getTime();
		pageName = readPageName("Choose Path");
		Utilities.waitForElementVisible(PageBase.ChoosePathPage().existingCarrier);
		PageBase.ChoosePathPage().existingCarrier.click();
		Utilities.webPageLoadTime(lStartTime, pageName);

		// Pick Your path and continue - Selecting Add a line to existing Account
		Utilities.waitForElementVisible(PageBase.PickYourPathPage().AALExistingAccount);
		Utilities.ClickElement(PageBase.PickYourPathPage().AALExistingAccount);
		PageBase.CommonControls().continueButtonDVA.click();

		// Upgrade-Eligibility-Checker Page - Populating the User Details
		String phoneNumber = "8967936068";
		String ssn= "5647";
		String pwd = "";
		PageBase.UECVerificationPage().FillSprintDetails(phoneNumber,ssn,pwd);
		PageBase.UECVerificationPage().continueSprintButton.click();

		// Add-Line-type-Selection Page - Selecting Add a line to Existing Family Plan
		PageBase.SelectAnOptionPage().AALExistingFamilyPlan.click();
		PageBase.CommonControls().continueButtonDVA.click();

		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().enterDeviceScanDetails(imei);

		Utilities.waitForElementVisible(PageBase.SprintEasyPayPage().yesButton);
		PageBase.SprintEasyPayPage().yesButton.click();

		//Filling information in Carrier Credit Check Page.
		String ccDataSSN = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.SSNWithoutDeposit);
		CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails(ccDataSSN);
		PageBase.CarrierCreditCheckPage().populatingCarrierCreditCheckPage(cccDetails);

		PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();

		lStartTime = new Date().getTime();
		pageName = readPageName("CarrierCreditCheck");
		PageBase.CommonControls().continueButton.click();
		
		//To Do
		// https://poa-d6.consensuscorpdev.com/retail/creditcheck/creditprocess.htm wait till this page is gone
		//or till this page arrives https://poa-d6.consensuscorpdev.com/retail/creditcheck/result.htm
		Utilities.implicitWaitSleep(1000);
		try {
			if (driver.findElement(By.id("checkbox-deposit-1")).isDisplayed())
				driver.findElement(By.id("checkbox-deposit-1")).click();
		} catch (Exception e) {

		}
		
		driver.findElement(By.id("continue")).click();
		
		String defaultDownPayment = driver.findElement(By.xpath(".//span[contains(text(), 'Minimum down payment is ')]")).getText().substring(43,47 );
		driver.findElement(By.id("custDownpayment")).click();
		driver.findElement(By.id("custDownpayment")).sendKeys(String.valueOf((Integer.valueOf(defaultDownPayment)+20)));
		
		driver.findElement(By.id("checkInstallment")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.xpath(".//span[contains(text(), 'Alternate down payment is ')]")).click();
		Utilities.implicitWaitSleep(1000);
		driver.findElement(By.id("continue")).click();
		return orderId;
	}

}