package com.consensus.qa.verizon.tests;

import com.consensus.qa.framework.*;
import com.consensus.qa.framework.CSVOperations.FileName;
import com.consensus.qa.framework.ExcelOperations.SheetName;
import com.consensus.qa.framework.Utilities.SelectDropMethod;
import com.consensus.qa.pages.GuestVerificationPage;
import com.consensus.qa.pages.PaymentRequiredPage.CardType;
import com.consensus.qa.pages.PaymentRequiredPage.MonthIdExp;
import com.consensus.qa.pages.ServiceProviderVerificationPage;
import com.consensus.qa.pages.ServiceProviderVerificationPage.IdType;
import com.consensus.qa.pages.ServiceProviderVerificationPage.Month;
import com.consensus.qa.tests.CarrierCreditCheckDetails;
import com.consensus.qa.pages.ReturnProcessPage;

import org.w3c.dom.Document;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import jdk.internal.org.xml.sax.SAXException;


public class UpgradeTests extends RetailBaseClass{

	//region public variable
	public String carrierType = "Verizon";
	String phoneNumber = "";
	String iMEINumber = "";
	String simNumber = "";
	String imei1 = "";
	String orderId = "";
	String receiptId = "";
	//endregion public variable

	//region Test Methods
	//region QA 79
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA_79_VerizonEdgeUpgradeWithExistingPlan(@Optional String testType) throws InterruptedException, AWTException, IOException{

		try {
			Log.startTestCase("QA_79VerizonEdgeUpgradeWithExistingPlan");

			testType = BrowserSettings.readConfig("test-type");

			// Verify whether which enviorement to use internal or external.
			selectingCarrierEnviornment(testType);

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			//Calling DBError utility to  find initial count or error in log files.
			/*DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			// Switching to previous tab.
			Utilities.switchPreviousTab();*/

			CustomerDetails customerDetails = CSVOperations.ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);

			String orderId = QA_79VerizonUpgradeEdge(customerDetails);

			//-----------------------Return Exchange---------------------
			QA_79VerizonExchangeDevice(orderId, customerDetails);

			//Todo: Will be uncomented when the exchange API is functional as per discussions
			//----------------Upgrade with non edge
		/*	Utilities.switchPreviousTab();
			Utilities.waitForElementVisible(PageBase.HomePageRetail().newGuestButton);
			PageBase.HomePageRetail().newGuestButton.click();

			PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

			Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);

			PageBase.UECVerificationPage().fillVerizonDetails("9890901312", "4850", "HELLO", "94109");
			PageBase.UECVerificationPage().continueVerizonButton.click();

			Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
			PageBase.UECAddLinesPage().firstAALCheckbox.click();
			PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

			//Device Scan Page
			Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
			PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys("908070605555");
			PageBase.DeviceScanPage().submitDeviceButton.click();

			Utilities.waitForElementVisible(PageBase.CommonControls().cancelButton);
			PageBase.CommonControls().cancelButton.click();

			//Verizon Shop Plans Page
			Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
			PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton.click();

			//Cart Page
			Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
			Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, Utilities.SelectDropMethod.SELECTBYINDEX, "1");
			String phonePrice_1 = PageBase.CartPage().firstPhonePriceText.getText();
			String phoneModel_1 = PageBase.CartPage().firstPhoneModelLink.getText();
			PageBase.CartPage().continueCartButton.click();

			Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
			//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
			PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

			//Select Protection Plan Insurance Page
			Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
			PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
			PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
			Utilities.implicitWaitSleep(6000);
			PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
			PageBase.CommonControls().continueButton.click();

			Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
			PageBase.ServiceProviderVerificationPage().populateFormByClassButton.click();
			//PageBase.ServiceProviderVerificationPage().populatingSPVPage();

			Utilities.ClickElement(PageBase.ServiceProviderVerificationPage().guestAgreesCreditCheck);
			Utilities.ClickElement(PageBase.CommonControls().continueButton);
			//Utilities.ClickElement(PageBase.CommonControls().continueButton);

			//	Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
			PageBase.CommonControls().continueCommonButton.click();

			//Accept the Terms and Conditions
			PageBase.TermsandConditionsPage().acceptTermsAndConditions();

			Utilities.implicitWaitSleep(10000);
			if (driver.getCurrentUrl().contains("payment")) {

				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA); //ToDo: Need to read from data sheet.
				Utilities.ClickElement(PageBase.PaymentRequiredPage().sameAddressTab);
				Utilities.ClickElement(PageBase.PaymentRequiredPage().continuePRButton);
			}

			// MSS page.
			//ToDo: BAR Code verification after clarifying on the expected Bar code.
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys("132710003003669460");
			PageBase.PaymentVerificationPage().submitButton.click();

			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys("908070605555");
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();

			PageBase.DeviceVerificationaandActivation().simType.sendKeys("21212121212121212121");

			try {
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");  // ToDo: Read from data sheet.
			} catch (Exception e) {
			}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();
			Utilities.ClickElement(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox);
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			Utilities.waitForElementVisible(PageBase.OrderReceiptPage().orderCompletionText);
			PageBase.OrderReceiptPage().verifyOrderCompletionPage();
			Assert.assertTrue(PageBase.OrderActivationCompletePage().ActivationComplete.isDisplayed());
			QA_79ShipAdminVerification(orderId);*/


			DBError.navigateDBErrorPage();
			Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(3));
			Reporter.log("<h3>QA_79VerizonEdgeUpgradeWithExistingPlan - Test Case Completes<h3>");
			Log.endTestCase("QA_79VerizonEdgeUpgradeWithExistingPlan");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			Utilities.driverTakesScreenshot("QA79_VerizonNonEdgeUpgrade");
			Assert.assertTrue(false);
		}
	}

	private void QA_79VerizonExchangeDevice(String orderId,CustomerDetails customerDetails) throws IOException, AWTException, InterruptedException {
		//Click on New Guest Button
		PageBase.HomePageRetail().newGuestButton.click();
		Utilities.waitForElementVisible(PageBase.HomePageRetail().guestLookupTab);
		PageBase.HomePageRetail().guestLookupTab.click();

		String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
		String esnNo = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBBlack);

		Utilities.waitForElementVisible(PageBase.CustomerLookupPage().receiptIdTextbox);
		PageBase.CustomerLookupPage().receiptIdTextbox.sendKeys(receiptId); //"132710003003669460"

		List<WebElement> SubmitButtons = driver.findElements(By.xpath(ControlLocators.SUBMIT_RECEIPTID));
		for (WebElement visibleSubmitButton : SubmitButtons) {
			if (visibleSubmitButton.isDisplayed()) {
				visibleSubmitButton.click();
				break;
			}
		}

		//Select a particular Order
		driver.navigate().to(readConfig("CustomerVerification") + orderId);

		Utilities.waitForElementVisible(PageBase.ReturnOrExchangeVerificationPage().cvFirstNameTextbox);
		//PageBase.ReturnOrExchangeVerificationPage().populatingPage(IdType.DRIVERLICENCE, "CA", "123456789", "Test", "", "Tester");
		PageBase.ReturnOrExchangeVerificationPage().populatingPage(IdType.DRIVERLICENCE, customerDetails.IDState, customerDetails.IDNumber, customerDetails.FirstName, "", customerDetails.LastName);
		PageBase.CommonControls().continueCommonButton.click();

		//Enter the ESN/EMEI number for the device to be exchanged
		Utilities.waitForElementVisible(PageBase.ReturnScanDevicePage().esnIemeidTextbox);
		PageBase.ReturnScanDevicePage().esnIemeidTextbox.sendKeys(esnNo);//7006007666666
		PageBase.CommonControls().continueButtonDVA.click();

		Utilities.waitForElementVisible(PageBase.ReturnOrExhangePreConditions().deviceAccessoryRadioButton);
		PageBase.ReturnOrExhangePreConditions().SelectPreconditions();
		PageBase.ReturnOrExhangePreConditions().continueREVButton.click();

		//Select more Details
		Utilities.waitForElementVisible(PageBase.ReturningProcessPage().acceptReturnExchangeRadioButton);
		PageBase.ReturningProcessPage().acceptReturnExchangeRadioButton.click();
		PageBase.ReturningProcessPage().exchangeDeviceRadioButton.click();
		PageBase.ReturningProcessPage().SelectFinancingOption(ReturnProcessPage.FinancingOption.CHECKFINANCING);
		Assert.assertEquals(PageBase.ReturningProcessPage().exchangeMessage.getText(), Constants.EXCHANGE_MESSAGE);
		Assert.assertTrue(PageBase.ReturningProcessPage().continueExchangeButton.isDisplayed());
		PageBase.ReturningProcessPage().SelectExchangeReason(ReturnProcessPage.ExchangeReason.INPOLICYGUESTRETURN);
		PageBase.CommonControls().continueButtonDVA.click();

		Utilities.waitForElementVisible(PageBase.AccountPasswordPage().continueButton);
		PageBase.AccountPasswordPage().accountPassword.sendKeys("HELLO");
		PageBase.AccountPasswordPage().continueButton.click();

		//Check for ShipAdmin Details
		ShipAdminBaseClass.launchShipAdminInNewTab();
		Utilities.implicitWaitSleep(5000);
		PageBase.OrderSummaryPage().SelectActivationInfo(orderId);

	}

	private String QA_79VerizonUpgradeEdge(CustomerDetails customerDetails) throws InterruptedException, AWTException, IOException {
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId2766"));
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
		String esnNo = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBBlack);
		String simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);

		AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonEdgeUpgrade);
		String MTNNumber = accountDetails.MTN;
		String accountPassword = accountDetails.Password;
		String SSN = accountDetails.SSN;
		String zipCode = customerDetails.Zip;


		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().fillVerizonDetails(MTNNumber,SSN,accountPassword,zipCode);
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		PageBase.UECAddLinesPage().firstAALCheckbox.click();
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(esnNo);//"7006007666666"
		PageBase.DeviceScanPage().submitDeviceButton.click();

		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().YesCheckEligibilityButton);
		PageBase.VerizonEdgePage().YesCheckEligibilityButton.click();

		PageBase.ServiceProviderVerificationPage().populateFormByClassButton.click();
		/*PageBase.ServiceProviderVerificationPage().populatingSPVPage
				("Fred", "", "Consumer Two", "nobody@letstalk.com", IdType.DRIVERLICENCE,
						"CA", "123456789", ServiceProviderVerificationPage.Month.DECEMBER,
						2020, "9880203387", "April", 11, 1970);*/
		PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();
		PageBase.CommonControls().continueButton.click();
		PageBase.CommonControls().continueButton.click();

		Utilities.waitForElementVisible(PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton);
		PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton.click();
		PageBase.CommonControls().continueCommonButton.click();

		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
		PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton.click();

		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		PageBase.CommonControls().continueCommonButton.click();

		//Accept the Terms and Conditions
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		try {
			PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA); //ToDo: Need to read from data sheet.
			Utilities.ClickElement(PageBase.PaymentRequiredPage().sameAddressTab);
			Utilities.ClickElement(PageBase.PaymentRequiredPage().continuePRButton);
		} catch (Exception e) {
		}


		// MSS page.
		//ToDo: BAR Code verification after clarifying on the expected Bar code.
		Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
		String orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();

		PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();
		PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(receiptId);//"132710003003669460"
		PageBase.PaymentVerificationPage().submitButton.click();

		Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
		PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(esnNo);//7006007666666
		PageBase.DeviceVerificationaandActivation().submitDVAButton.click();

		PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber);//"21212121212121212121"

		try {
			PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");  // ToDo: Read from data sheet.
		} catch (Exception e) {
		}

		PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();
		Utilities.ClickElement(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox);
		PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
		PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

		PageBase.DeviceFinancingInstallmentContractPage().PrintDeviceFinancingDetails(driver);

		Utilities.waitForElementVisible(PageBase.OrderReceiptPage().orderCompletionText);
		PageBase.OrderReceiptPage().verifyOrderCompletionPage();
		Assert.assertTrue(PageBase.OrderActivationCompletePage().ActivationComplete.isDisplayed());


		if(readConfig("Activation")=="true") {
			//Inventory Management
			PageBase.InventoryManagementPage().launchInventoryInNewTab();
			PageBase.InventoryManagementPage().verifyDeviceStatus(simNumber, Constants.SOLD); //908070605555

		}
		return orderId;
	}

	private void QA_79ShipAdminVerification(String orderId) {
		//Ship Admin
		ShipAdminBaseClass.launchShipAdminInNewTab();
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
		Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
		Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));
	}


	//region QA-62
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA62_VerizonNonEdgeUpgradeKeepExistingPlan(@Optional String testtype)
	{
		try
		{
			//This TC need 1 fresh phone number and 1 fresh IMEI for every run.
			CustomerDetails cusDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
			String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
			String iMEINumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
			String simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
			AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeUpgradeMultipleLinesEligible);
			String phoneNumber = accountDetails.MTN;
			String sSN = accountDetails.SSN;
			String zipCode = cusDetails.Zip;
			String accountPassword = accountDetails.Password;
			String orderId = "";

			Log.startTestCase("QA62_VerizonNonEdgeUpgradeKeepExistingPlan");
			Reporter.log("<h2>Start - QA62_VerizonNonEdgeUpgradeKeepExistingPlan. <br></h2>");
			Reporter.log("Launching Browser <br>", true);

			//Verify whether which enviorement to use internal or external.
			testtype = BrowserSettings.readConfig("test-type");
			if(testtype.equals("internal"))
			{
				//Customizing xml files in Carrier Responder
				Reporter.log("<h3><U> Carrier Responder</U></h3>", true);
				carrierResponderSettingsQA62(phoneNumber);
				Reporter.log("<h3><U> Carrier Responder Changes Done.</U></h3>", true);
			}
			else
			{
				selectingVerizonExternalEnvironment();
			}

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			//Switching Back To Retail
			Utilities.switchPreviousTab();

			//POA FLOW
			orderId = poaFlowQA62(phoneNumber, iMEINumber, simNumber, orderId, sSN, accountPassword, zipCode, receiptId);

			if (readConfig("Activation").toLowerCase().contains("true")) {
				//Ship Admin
				ShipAdminBaseClass.launchShipAdminInNewTab();
				shipAdminVerificationsUpgrade(orderId);

				//Inventory Management
				PageBase.InventoryManagementPage().launchInventoryInNewTab();
				PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber, Constants.SOLD);
			}

			//DBError Verification.
			DBError.navigateDBErrorPage();
			//Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Reporter.log("<h3>QA62_VerizonNonEdgeUpgradeKeepExistingPlan - Test Case Completes<h3>");
			Log.endTestCase("QA62_VerizonNonEdgeUpgradeKeepExistingPlan");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			System.out.println(ex.getMessage());
			Utilities.driverTakesScreenshot("QA62_VerizonNonEdgeUpgradeKeepExistingPlan");
			Assert.assertTrue(false);
		}
		finally
		{

		}
	}
	//endregion QA-62

	//region QA-60
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA60_VerizonNonEdgeUpgradeNewPlan(@Optional String testtype)
	{
		//This TC need 1 fresh phone number and 1 fresh IMEI for every run.
		try
		{
			CustomerDetails cusDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
			String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
			String iMEINumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
			String simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
			AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeUpgradeMultipleLinesEligible);
			String phoneNumber = accountDetails.MTN;
			String sSN = accountDetails.SSN;
			String zipCode = cusDetails.Zip;
			String accountPassword = accountDetails.Password;
			String orderId = "";

			Log.startTestCase("QA60_VerizonNonEdgeUpgradeNewPlan");
			Reporter.log("<h2>Start - QA60_VerizonNonEdgeUpgradeNewPlan. <br></h2>" );
			Reporter.log("Launching Browser <br>", true);

			//Verify whether which enviorement to use internal or external.
			testtype = BrowserSettings.readConfig("test-type");
			if(testtype.equals("internal"))
			{
				//Customizing xml files in Carrier Responder
				Reporter.log("<h3><U> Carrier Responder</U></h3>", true);
				carrierResponderSettingsQA60(phoneNumber);
				Reporter.log("<h3><U> Carrier Responder Changes Done.</U></h3>", true);
			}
			else
			{
				selectingVerizonExternalEnvironment();
				Reporter.log("<h3><U> External Server</U></h3>", true);
			}

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			//Switching Back To Retail
			Utilities.switchPreviousTab();

			//POA FLOW
			orderId = poaFlowQA60(phoneNumber, iMEINumber, simNumber, orderId, sSN, accountPassword, zipCode, receiptId);


			if (readConfig("Activation").toLowerCase().contains("true")) {
				//Ship Admin
				ShipAdminBaseClass.launchShipAdminInNewTab();
				shipAdminVerificationsUpgrade(orderId);

				//Inventory Management
				PageBase.InventoryManagementPage().launchInventoryInNewTab();
				PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber, Constants.SOLD);
			}

			//DBError Verification.
			DBError.navigateDBErrorPage();
			//Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Reporter.log("<h3>QA60_VerizonNonEdgeUpgradeNewPlan - Test Case Completes<h3>");
			Log.endTestCase("QA60_VerizonNonEdgeUpgradeNewPlan");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			System.out.println(ex.getMessage());
			Utilities.driverTakesScreenshot("QA60_VerizonNonEdgeUpgradeNewPlan");
			Assert.assertTrue(false);
		}
		finally
		{

		}
	}


	//endregion QA-60

	//region QA-5572
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA5572_VerizonNonEdgeUpgradeWithTwoSmartPhones(@Optional String testtype)
	{
		try
		{
			//This TC requires 2 fresh IMEI numbers and 2 fresh phone numbers for internal run.

			//Verify whether which enviorement to use internal or external.
			testtype = BrowserSettings.readConfig("test-type");
			CustomerDetails custDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
			String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
			String iMEINumber1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
			String iMEINumber2 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
			String simNumber1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
			String simNumber2 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
			AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeUpgradeMultipleLinesEligible);
			String phoneNumberLine1 = accountDetails.MTN;
			String phoneNumberLine2;
			String sSN = accountDetails.SSN;
			String zipCode = custDetails.Zip;
			String accountPassword = accountDetails.Password;;
			String orderId = "";
			if(testtype.equals("internal"))
			{
				phoneNumberLine2 = CommonFunction.getUniqueNumber(phoneNumberLine1); //For getting unique number for internal testing
			}
			else
			{
				phoneNumberLine2 = "";
			}

			Log.startTestCase("QA5572_VerizonNonEdgeUpgradeWithTwoSmartPhones");
			Reporter.log("<h2>Start - QA5572_VerizonNonEdgeUpgradeWithTwoSmartPhones. <br></h2>");
			Reporter.log("Launching Browser <br>", true);

			if(testtype.equals("internal"))
			{
				//Customizing xml files in Carrier Responder
				Reporter.log("<h3><U> Carrier Responder</U></h3>", true);
				carrierResponderSettingsQA5572(phoneNumberLine1, phoneNumberLine2);
				Reporter.log("<h3><U> Carrier Responder Changes Done.</U></h3>", true);
			}
			else
			{
				selectingVerizonExternalEnvironment();
				Reporter.log("<h3><U> External Server</U></h3>", true);
			}

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			//Switching Back To Retail
			Utilities.switchPreviousTab();

			//POA FLOW
			orderId = poaFlowQA5572(phoneNumberLine1, phoneNumberLine2, iMEINumber1, iMEINumber2, simNumber1, simNumber2, orderId, testtype, sSN, accountPassword, zipCode, receiptId);


			if (readConfig("Activation").toLowerCase().contains("true")) {
				//Ship Admin
				Utilities.switchPreviousTab(); Utilities.switchPreviousTab(); Utilities.switchPreviousTab();
				shipAdminVerificationsQA5572(orderId);

				//Verify in Server DB
				PageBase.SQLUtilAdminPage().launchSQLUtilInNewTab();
				serverDBVerificationsQA5572(orderId);

				//Inventory Management
				PageBase.InventoryManagementPage().launchInventoryInNewTab();
				PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber1, iMEINumber2, Constants.SOLD);

			}
			//DBError Verification.
			DBError.navigateDBErrorPage();
			//Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Reporter.log("<h3>QA5572_VerizonNonEdgeUpgradeWithTwoSmartPhones - Test Case Completes<h3>");
			Log.endTestCase("QA5572_VerizonNonEdgeUpgradeWithTwoSmartPhones");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			System.out.println(ex.getMessage());
			Utilities.driverTakesScreenshot("QA5572_VerizonNonEdgeUpgradeWithTwoSmartPhones");
			Assert.assertTrue(false);
		}
		finally
		{

		}
	}

	//endregion QA-5572

	//region QA-64
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA64_VerizonNonEdgeOneBuddyUpgrade(@Optional String testtype)
	{
		boolean  stopActivation = true;
		try
		{
			//This TC requires 2 fresh phone numbers and 1 IMEI for every run for internal run.
			CustomerDetails custDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
			String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
			String iMEINumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
			String simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
			AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeBuddyUpgrade);
			String phoneNumber = accountDetails.MTN;
			String sSN = accountDetails.SSN;
			String zipCode = custDetails.Zip;
			String accountPassword = accountDetails.Password;
			String donorPhoneNumber = CommonFunction.getUniqueNumber(phoneNumber); //This will use in only internal testing.
			String orderId = "";

			Log.startTestCase("QA64_VerizonNonEdgeOneBuddyUpgrade");
			Reporter.log("<h2>Start - QA5572_VerizonNonEdgeUpgradeWithTwoSmartPhones. <br></h2>" );
			Reporter.log("Launching Browser <br>", true);

			//Verify whether which enviorement to use internal or external.
			testtype = BrowserSettings.readConfig("test-type");
			if(testtype.equals("internal"))
			{
				//Customizing xml files in Carrier Responder
				Reporter.log("<h3><U> Carrier Responder</U></h3>", true);
				carrierResponderSettingsQA64(phoneNumber, donorPhoneNumber);
				Reporter.log("<h3><U> Carrier Responder Changes Done.</U></h3>", true);

			}
			else
			{
				selectingVerizonExternalEnvironment();
				Reporter.log("<h3><U> External Server</U></h3>", true);
			}

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			//Switching Back To Retail
			Utilities.switchPreviousTab();

			//POA FLOW
			orderId = poaFlowQA64(phoneNumber, iMEINumber, simNumber, orderId, sSN, accountPassword, zipCode, receiptId);

			if (readConfig("Activation").toLowerCase().contains("true")) {

				//Ship Admin
				ShipAdminBaseClass.launchShipAdminInNewTab();
				shipAdminVerificationsUpgrade(orderId);

				//Inventory Management
				PageBase.InventoryManagementPage().launchInventoryInNewTab();
				PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber, Constants.SOLD);
			}

			//DBError Verification.
			DBError.navigateDBErrorPage();
			Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Reporter.log("<h3>QA64_VerizonNonEdgeOneBuddyUpgrade - Test Case Completes<h3>");
			Log.endTestCase("QA64_VerizonNonEdgeOneBuddyUpgrade");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			System.out.println(ex.getMessage());
			Utilities.driverTakesScreenshot("QA64_VerizonNonEdgeOneBuddyUpgrade");
			Assert.assertTrue(false);
		}
		finally
		{

		}
	}
	//endregion QA-64

	//region QA-5356
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA5356_VerizonEdgeTwoYearContractUpgradePaymentNotRequired(@Optional String testtype)
	{
		try
		{
			//This TC need 1 fresh phone number and 1 fresh IMEI for every run.
			CustomerDetails custDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
			String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
			String iMEINumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite0003);
			String simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
			AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonEdgeUPPaymentNotRequired);
			String phoneNumber = accountDetails.MTN;
			String sSN = accountDetails.SSN;
			String zipCode = custDetails.Zip;
			String accountPassword = accountDetails.Password;
			String orderId = "";

			Log.startTestCase("QA5356_VerizonEdgeTwoYearContractUpgradePaymentNotRequired");
			Reporter.log("<h2>Start - QA5356_VerizonEdgeTwoYearContractUpgradePaymentNotRequired. <br></h2>");
			Reporter.log("Launching Browser <br>", true);

			//Verify whether which enviorement to use internal or external.
			testtype = BrowserSettings.readConfig("test-type");
			if(testtype.equals("internal"))
			{
				AdminBaseClass adminBaseClass = new AdminBaseClass();
				adminBaseClass.launchAdminInNewTab();
				PageBase.AdminPage().navigateToSimulator();

				if(readConfig("internalTestType").toLowerCase().contains("carrierresponder"))
				{
					//Customizing xml files in Carrier Responder
					Reporter.log("<h3><U> Carrier Responder</U></h3>", true);
					carrierResponderSettingsQA5356(phoneNumber, sSN, zipCode);
					Reporter.log("<h3><U> Carrier Responder Changes Done.</U></h3>", true);

				}
				else
				{
					Reporter.log("<h3><U> Backend</U></h3>", true);
					backendSettingsQA5356(phoneNumber);
					Reporter.log("<h3><U> Backend Changes Done.</U></h3>", true);
				}
			}
			else
			{
				selectingVerizonExternalEnvironment();
				Reporter.log("<h3><U> External Server</U></h3>", true);
			}

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			//Switch Back To Retail
			Utilities.switchPreviousTab();

			//POA Flow
			orderId = poaFlowQA5356(phoneNumber, iMEINumber, simNumber, sSN, accountPassword, zipCode, receiptId);
			if(readConfig("Activation").toLowerCase().contains("true"))
			{
				//Ship Admin
				ShipAdminBaseClass.launchShipAdminInNewTab();
				shipAdminVerificationsQA5356(orderId);

				//Verify in Server DB
				PageBase.SQLUtilAdminPage().launchSQLUtilInNewTab();
				serverDBVerificationsQA5356(orderId);

				//Inventory Management
				PageBase.InventoryManagementPage().launchInventoryInNewTab();
				PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber, Constants.SOLD);
			}

			//DBError Verification.
			DBError.navigateDBErrorPage();
			//Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Reporter.log("<h3>QA5356_VerizonEdgeTwoYearContractUpgradePaymentNotRequired - Test Case Completes<h3>");
			Log.endTestCase("QA5356_VerizonEdgeTwoYearContractUpgradePaymentNotRequired");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			System.out.println(ex.getMessage());
			Utilities.driverTakesScreenshot("QA5356_VerizonEdgeTwoYearContractUpgradePaymentNotRequired");
			Assert.assertTrue(false);
		}
		finally
		{

		}
	}
	//endregion QA-5356

	//region QA 58
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA_58_VerizonNonEdgeExchange(@Optional String testType)
	{
		try
		{
			Log.startTestCase("QA_58_VerizonNonEdgeExchange");
			testType = BrowserSettings.readConfig("test-type");

			// Verify whether which enviorement to use internal or external.
			selectingCarrierEnviornment(testType);

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			//Calling DBError utility to  find initial count or error in log files.
			/*DBError.navigateDBErrorPage();
         int initialCount= PageBase.AdminPage().totalErrorCount();

         // Switching to previous tab.
         Utilities.switchPreviousTab();*/

			//POA complete flow.
			String orderId = poaFlowQA58();

			//DBError Verification.
			DBError.navigateDBErrorPage();
			Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(3));
			Reporter.log("<h3>QA_58_VerizonNonEdgeExchange - Test Case Completes<h3>");
			Log.endTestCase("QA_58_VerizonNonEdgeExchange");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			//Utilities.driverTakesScreenshot("QA_58_VerizonNonEdgeExchange");
			Assert.fail();
		}
	}
	//endregion

	//region QA 5573
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA5573_VerizonNonEdgeUpgradeWithAppleCare(@Optional String testtype) throws InterruptedException, AWTException,
	IOException{

		boolean stopActivation = true;
		try {
			Log.startTestCase("QA5573_VerizonNonEdgeUpgrade");

			testtype = BrowserSettings.readConfig("test-type");

			selectCarrierResponder_QA5573(testtype);

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			String orderId = poaCompleteFlow_QA5573(stopActivation);

			//Inventory Management Page verification.
			if(readConfig("Activation")=="true"){
				inventoryManagementVerification_QA5573();

				//Ship Admin Verification -orderId= ""
				shipAdminVerification(orderId);
			}

			//DBError Verification.
			DBError.navigateDBErrorPage();
			Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));
			DBError.navigateDBErrorPage();
			Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));
			Reporter.log("<h3>QA5573_VerizonNonEdgeUpgrade - Test Case Completes<h3>");
			Log.endTestCase("QA5573_VerizonNonEdgeUpgrade");

		} catch (Exception ex) {
			Log.error(ex.getMessage());
			//Utilities.driverTakesScreenshot();
			Assert.assertTrue(false);
		} finally {

		}
	}
	//endregion

	//region QA 5358
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA5358_VerizonEdgeUpgrade(@Optional String testtype) throws InterruptedException, AWTException,
	IOException, ParserConfigurationException, SAXException, TransformerException
	{
		String testType = BrowserSettings.readConfig("test-type");
		CreditCardDetails creditCard = new CreditCardDetails();
		creditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
		String phoneNumberLine1 = "";
		String receiptId = "";
		String iMEINumber1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.iPhone5C);
		String simType1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
		AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeUpgradeMultipleLinesEligible);
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.STATEID);
		String phoneNumber = accountDetails.MTN;
		String sSN = accountDetails.SSN;
		String zipCode = customerDetails.Zip;
		String accountPassword = accountDetails.Password;

		boolean  stopActivation = true;
		try
		{
			Log.startTestCase("QA5358_VerizonNonEdgeUpgrade");

			carrierResponderSetupQA5358(phoneNumber, sSN, zipCode, testType);

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			poaCompleteFlowQA5358(phoneNumber, sSN, zipCode, iMEINumber1, stopActivation, testType, simType1);

			Log.endTestCase("QA5358_VerizonNonEdgeUpgrade");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			//Utilities.driverTakesScreenshot();
			Assert.assertTrue(false);
		}
		finally
		{

		}
	}
	//endregion

	//region QA 72
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA72_VerizonEdgeUpgradeWithAppleCare(@Optional String testtype) {

		boolean stopActivation = true;
		try {
			Log.startTestCase("QA72_VerizonEdgeUpgradeWithAppleCare");

			AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeUpgradeWithAppleCare);
			CustomerDetails custDetails = CSVOperations.ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
			String MTNNumber = accountDetails.MTN;
			String accountPassword = accountDetails.Password;
			String SSN = accountDetails.SSN;
			String zipCode = custDetails.Zip;
			CreditCardDetails creditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
			iMEINumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.iPhone5C);
			simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);

			selectCarrierResponder_QA72(testtype, MTNNumber);

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			poaCompleteFlow_QA72(stopActivation, MTNNumber, SSN, zipCode);

			Log.endTestCase("QA72_VerizonEdgeUpgradeWithAppleCare");
		} catch (Exception ex) {
			Log.error(ex.getMessage());
			//Utilities.driverTakesScreenshot();
			Assert.assertTrue(false);
		} finally {

		}
	}
	//endregion QA 72

	//region QA 5355
	@Test(groups={"verizon"})
	public void QA_5355_VerizonEdgeUp2YearContractUpgradeFlow() throws InterruptedException, AWTException,
	IOException, ParserConfigurationException, SAXException, TransformerException{
		String testType = BrowserSettings.readConfig("test-type");
		PageBase.CSVOperations();
		CreditCardDetails creditCard = new CreditCardDetails();
		creditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
		String orderId = "";String phoneNumberLine1 = "";String receiptId = "";
		String iMEINumber1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.iPhone5C);
		String simType1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
		AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonEdgeUpgrade);
		CustomerDetails customerDetails = CSVOperations.ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		String MTNNumber = accountDetails.MTN;
		String SSN = accountDetails.SSN;
		String zipCode = customerDetails.Zip;

		Reporter.log("<h2>Start - QA_5355_VerizonEdgeUp2YearContractUpgradeFlow. <br></h2>" );
		Reporter.log("<h3>Description: Verizon-NonEdge-Buddy Early Upgrade full activation</h3>");
		Reporter.log("Launching Browser <br>", true);
		Log.startTestCase("QA_5355_VerizonEdgeUp2YearContractUpgradeFlow");

		//Test Type Settings
		boolean stopActivation = Boolean.parseBoolean(BrowserSettings.readConfig("Activation"));
		if(testType.equals("internal"))
		{
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();
			PageBase.AdminPage().navigateToSimulator();
			phoneNumberLine1 =  String.valueOf((long)(Math.floor(Math.random() * 9000000000L) + 6000000000L));
			phoneNumberLine1 = phoneNumberLine1.substring(0,10);
			if(readConfig("internalTestType").toLowerCase().equals("carrierresponder")){
				Reporter.log("<h3><U> Carrier Responder</U></h3>", true);

				PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
				PageBase.AdminPage().selectAPIConfig("Verizon");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().verizonCarrierTab);
				PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();

				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().versionsDropdown);

				PageBase.CarrierResponseXMLPage().selectOptions("current",
						"retrieveCustomerDetails", "vwz_edge_up_eligible_payment_needed.xml");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);
				String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_SSN, SSN);
				xmlContent1 = xmlContent1.replace("07920", zipCode);
				xmlContent1 = xmlContent1.replace("6567778888", MTNNumber);
				xmlContent1 = xmlContent1.replace("281", Utilities.getCredentials("storeId0003"));
				xmlContent1 = xmlContent1.replace("SEPTEMBER TEST", customerDetails.FirstName +" "+customerDetails.LastName);
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				Robot robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				Utilities.implicitWaitSleep(5000);


				PageBase.CarrierResponseXMLPage().selectOptions("current",
						"retrieveExistingCustomerInstallmentDetails", "eligible_payment_needed.xml");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);
				xmlContent1 = "";
				xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				xmlContent1 = xmlContent1.replace("6567778888", MTNNumber);
				xmlContent1 = xmlContent1.replace("281", Utilities.getCredentials("storeId0003"));
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				Utilities.implicitWaitSleep(5000);

				PageBase.CarrierResponseXMLPage().selectOptions("current",
						"retrieveCreditApplication", "upgrade_approved_noedge.xml");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);
				xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, MTNNumber);
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_OF_LINES, "001");
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887, Utilities.getCredentials("storeId0003"));
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_ZipCode, zipCode);
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				Utilities.implicitWaitSleep(5000);


				PageBase.CarrierResponseXMLPage().selectOptions("current",
						"retrievePricePlans", "plaid_v_1_line.xml");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);
				xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, MTNNumber   );
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
						Utilities.getCredentials("storeId0003"));
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				Utilities.implicitWaitSleep(5000);


				PageBase.CarrierResponseXMLPage().selectOptions("current",
						"submitCreditApplication", "approved.xml");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);
				xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887,
						Utilities.getCredentials("storeId0003"));
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				Utilities.implicitWaitSleep(5000);


				PageBase.CarrierResponseXMLPage().selectOptions("current",
						"submitReceipt", "default.xml");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);
				xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887,
						Utilities.getCredentials("storeId0003"));
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				Utilities.implicitWaitSleep(5000);


				PageBase.CarrierResponseXMLPage().selectOptions("current",
						"submitServiceDetails", "default.xml");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);
				xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
						Utilities.getCredentials("storeId0003"));
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, MTNNumber);
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				Utilities.implicitWaitSleep(5000);


				PageBase.CarrierResponseXMLPage().selectOptions("current","submitactivation", "success_1_line.xml");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);
				xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, MTNNumber);
				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
						Utilities.getCredentials("storeId0003"));
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().saveResponseButton);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();
				Utilities.implicitWaitSleep(5000);

				Reporter.log("<br>Carrier Responder Changes Done!!!", true);
			}
			else{
				PageBase.AdminPage().selectWebAPIResponse("Verizon", "BackendSimulator");
				PageBase.AdminPage().selectAPIConfig("Verizon");
				//PageBase.AdminPage().retrieveCreditApplication("APPROVED_EDGEUP");
				PageBase.AdminPage().retrieveCustomerDetails("VZW_EDGE_UP_ELIGIBLE");

				try{
					if(!PageBase.AdminPage().firstPhoneTextBox.isEnabled());
				}
				catch(Exception e)
				{
					PageBase.AdminPage().addPhoneNumbers.click();
				}

				try{
					if(PageBase.AdminPage().secondPhoneTextBox.isEnabled()){
						PageBase.AdminPage().secondPhoneRemoveTextBox.click();
					}
				}
				catch(Exception e)
				{
					PageBase.AdminPage().addPhoneNumbers.click();
				}

				PageBase.AdminPage().firstPhoneTextBox.clear();
				PageBase.AdminPage().firstPhoneTextBox.sendKeys(MTNNumber);

				PageBase.AdminPage().accountPlanType("Individual");
				PageBase.AdminPage().retrieveExistingCustomerInstallmentsDetails("SUCCESS_WITH_PAYMENT");
				PageBase.AdminPage().retrievePricePlan(Constants.SUCCESS);
				PageBase.AdminPage().submitCreditApplication(Constants.APPROVED);
				PageBase.AdminPage().submitReciept(Constants.SUCCESS);
				PageBase.AdminPage().submitServiceDetails(Constants.SUCCESS);
				PageBase.AdminPage().submitEdgeUpPayment(Constants.SUCCESS);
				PageBase.AdminPage().save();
				Utilities.implicitWaitSleep(1000);
			}
		}
		else   //External
		{
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse("Verizon", "External");
			Reporter.log("<h3><U> External Server</U></h3>", true);
		}

		//POA Flow
		Utilities.switchPreviousTab();
		//Calling DBError utility to  find initial count or error in log files.
		DBError.navigateDBErrorPage();
		int initialCount= PageBase.AdminPage().totalErrorCount();
		Utilities.switchPreviousTab();

		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"),
				Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(MTNNumber);
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(String.valueOf(SSN));
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(String.valueOf(zipCode));
		PageBase.UECVerificationPage().continueVerizonButton.click();

		Reporter.log("<br> UEC - Add Lines Page");

		String makePaymentButton = "//a[@name='paymentButton-"+MTNNumber+"']";
		Utilities.waitForElementVisible(driver.findElement(By.xpath(makePaymentButton)));
		driver.findElement(By.xpath(makePaymentButton)).click();

		String cardNumber = "cardNumber-"+MTNNumber;
		driver.findElement(By.id(cardNumber)).sendKeys(creditCard.Number);
		String cardcvvNumber = "ccv2-"+MTNNumber;
		driver.findElement(By.id(cardcvvNumber)).sendKeys(creditCard.CVV);

		String month = "card-month-"+MTNNumber;
		PageBase.ServiceProviderVerificationPage().SetMonthWithIndex(driver.findElement(By.id(month)),
				creditCard.ExpiryMonth.toUpperCase());

		String year = "card-year-"+MTNNumber;
		Utilities.dropdownSelect(driver.findElement(By.id(year)), SelectDropMethod.SELECTBYTEXT, creditCard.ExpiryYear);

		driver.findElement(By.xpath(".//span[contains(text(),'Use a different address')]")).click();;
		String firstName = "form-firstName-"+MTNNumber;
		driver.findElement(By.id(firstName)).sendKeys(customerDetails.FirstName);;

		String lastName = "form-lastName-"+MTNNumber;
		driver.findElement(By.id(lastName)).sendKeys(customerDetails.LastName);;

		String address1 = "form-address1-"+MTNNumber;
		driver.findElement(By.id(address1)).sendKeys(customerDetails.Address1);;

		String city = "form-city-"+MTNNumber;
		driver.findElement(By.id(city)).sendKeys(customerDetails.City);;

		String state = "form-state-"+MTNNumber;
		Utilities.dropdownSelect(driver.findElement(By.id(state)),SelectDropMethod.SELECTBYTEXT,customerDetails.State);

		String cardAddressZip = "form-cardAddressZip-"+MTNNumber;
		driver.findElement(By.id(cardAddressZip)).sendKeys(customerDetails.Zip);;

		driver.findElement(By.xpath(".//button[contains(text(),'submit & continue')]")).click();
		Reporter.log("<br> Entered Credit Card Details");
		Thread.sleep(5000);

		Utilities.waitForElementVisible(driver.findElement(By.id("checkbox-mini-0")));
		driver.findElement(By.id("checkbox-mini-0")).click();


		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		Assert.assertTrue(PageBase.UECAddLinesPage().eligibleForDF.getText().contains("Eligible for Device Finance\n"));
		PageBase.UECAddLinesPage().clickCheckboxForParticularPhoneNumber(MTNNumber);
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber1);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().YesCheckEligibilityButton);
		PageBase.VerizonEdgePage().YesCheckEligibilityButton.click();

		//Filling information in Carrier Credit Check Page.
		Utilities.waitForElementVisible(PageBase.CarrierCreditCheckPage().populateForm);
		CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails();
		PageBase.CarrierCreditCheckPage().populatingCarrierCreditCheckPage(cccDetails);
		PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();
		PageBase.CommonControls().continueButton.click();
		PageBase.CommonControls().continueButton.click();

		// Installment Page
		Utilities.waitForElementVisible(PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton);
		PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton.click();
		PageBase.CommonControls().continueCommonButton.click();


		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().VerizonMoreEverything);
		PageBase.VerizonShopPlansPage().VerizonMoreEverything.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().appleCareInsurance);
		PageBase.SelectProtectionPlanInsurancePage().appleCareInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		Reporter.log("<br> Service Provider Verification Page");
		//PageBase.ServiceProviderVerificationPage().populatingSPVPage

		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth,Integer.parseInt(customerDetails.IDExpirationYear));
		Reporter.log("<br> Entered Details for "+customerDetails.FirstName +" "+ customerDetails.LastName);
		PageBase.ServiceProviderVerificationPage().guestCreditCheckCheckbox.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();


		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phoneMonthlyFee.getText(), phonePrice.replace('*', ' ').trim());
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		//Payment Required Page
		Utilities.implicitWaitSleep(8000);
		String url = driver.getCurrentUrl();
		if (url.contains("payment")) {
			PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
			PageBase.PaymentRequiredPage().sameAddressTab.click();
			PageBase.PaymentRequiredPage().continuePRButton.click();
			Reporter.log("<br> Entered Credit Card Details");
		}
		if (stopActivation == false)
		{
			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
			//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(PageBase.CSVOperations().GetIMEIAndSimNumber(FileName.ReceiptId));
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation Page
			//PageBase.DeviceVerificationaandActivation().deviceVerificationActiavtionFor1Device(iMEINumber1);
			try
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(String.valueOf(creditCardDetails.CVV));
			}
			catch(Exception e){}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);

			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			PageBase.CSVOperations().UpdateIMEIStatusToUsed(iMEINumber1, FileName.iPhone5C);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(MTNNumber));
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber1);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simType1);

			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);

			//Ship Admin
			ShipAdminBaseClass.launchShipAdminInNewTab();
			PageBase.OrderSummaryPage().goToOrderSummaryPage(orderId);
			PageBase.OrderSummaryPage().verifyAppleCareInsuranceStatus();
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
			Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
			//Assert.assertTrue(PageBase.OrderSummaryPage().additionalInfoValueText.getText().contains(Constants.EXISTING_ACCOUNT_HOLDER));
			Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));

			//Inventory Management
			PageBase.InventoryManagementPage().launchInventoryInNewTab();
			PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber1, Constants.SOLD);

			//DB Errors
			DBError.navigateDBErrorPage();
			Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			PageBase.CSVOperations();
			CSVOperations.WriteToCSV("QA_5355",orderId,iMEINumber1,"","",customerDetails.FirstName,customerDetails.LastName,
					customerDetails.EMail,receiptId,customerDetails.IDType,customerDetails.State,
					customerDetails.IDNumber, customerDetails.PhNum,customerDetails.Zip,SSN,
					customerDetails.IDExpirationMonth,customerDetails.IDExpirationYear);
		}

		Log.endTestCase("QA_5355_VerizonEdgeUp2YearContractUpgradeFlow");
		Reporter.log("<h2><font color='green'> Test Case Completes </font></h2>");

	}
	//endregion QA 5355

	//region QA 76
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA_76_VerizonEdgeUpgradeAddNewPlan(@Optional String testType)
	{
		//This TC need 1 fresh phone number and 1 fresh IMEI for every run.
		testType = BrowserSettings.readConfig("test-type");
		try
		{
			Log.startTestCase("QA_76_VerizonEdgeUpgradeAddNewPlan");
			String phoneNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.NewPhoneNumber);
			String imeiNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
			System.out.println(imeiNumber);
			String simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);

			//Verify whether which enviorement to use internal or external.
			if(testType.equals("internal")) {
				//Customizing xml files in Carrier Responder
				selectCarrierResponderAPISetUp(phoneNumber);
			}
			else
			{
				// Need to set External server from Admin page.
				AdminBaseClass adminBaseClass = new AdminBaseClass();
				adminBaseClass.launchAdminInNewTab();

				PageBase.AdminPage().navigateToSimulator();
				PageBase.AdminPage().selectWebAPIResponse("Verizon","External");
			}
			//Switching Back to Retail
			Utilities.switchPreviousTab();

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount= PageBase.AdminPage().totalErrorCount();

			// Switching to previous tab.
			Utilities.switchPreviousTab();

			// POA flow.
			poaCompleteFlow_QA_76(phoneNumber, imeiNumber, simNumber);

			//DBError Verification.
			DBError.navigateDBErrorPage();
			Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Reporter.log("<h3>QA_76_VerizonEdgeUpgradeAddNewPlan - Test Case Completes<h3>");
			Log.endTestCase("QA_76_VerizonEdgeUpgradeAddNewPlan");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			Utilities.driverTakesScreenshot("QA_76_VerizonEdgeUpgradeAddNewPlan");
			Assert.assertTrue(false);
		}
	}
	//endregion QA-76

	//region QA 5354
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA5354_VerizonEdgeUpUpgradeWithDFAndPaymentRequired (@Optional String testType) throws InterruptedException, AWTException,
	IOException, ParserConfigurationException, SAXException, TransformerException
	{
		testType = BrowserSettings.readConfig("test-type");
		CreditCardDetails creditCard = new CreditCardDetails();
		creditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
		String orderId = "";String phoneNumberLine1 = "";String receiptId = "";
		String iMEINumber1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
		String simType1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
		AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonEdgeUpgrade);
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		String MTNNumber = accountDetails.MTN;
		String ssn = accountDetails.SSN;
		String zipCode = customerDetails.Zip;
		String recieptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);

		Reporter.log("<h2>Start - QA5354_VerizonEdgeUpUpgradeWithDFAndPaymentRequired. <br></h2>" );
		Reporter.log("<h3>Description: QA5354_VerizonEdgeUpUpgradeWithDFAndPaymentRequired</h3>");
		Reporter.log("Launching Browser <br>", true);
		Log.startTestCase("QA5354_VerizonEdgeUpUpgradeWithDFAndPaymentRequired");

		//Test Type Settings
		boolean stopActivation = Boolean.parseBoolean(BrowserSettings.readConfig("Activation"));
		try
		{
			//Verify whether which enviorement to use internal or external.
			if(testType.equals("internal"))
			{
				setupInternalEnviornment_QA_5354(ssn, zipCode, MTNNumber, customerDetails);
			}
			else
			{
				AdminBaseClass adminBaseClass = new AdminBaseClass();
				adminBaseClass.launchAdminInNewTab();

				PageBase.AdminPage().navigateToSimulator();
				PageBase.AdminPage().selectWebAPIResponse(carrierType,"External");
			}

			//POA Flow
			//Utilities.switchPreviousTab();
			//Calling DBError utility to  find initial count or error in log files.
			//DBError.navigateDBErrorPage();
			//int initialCount= PageBase.AdminPage().totalErrorCount();

			Utilities.switchPreviousTab();

			//POA flow.
			poaCompleteFlow_QA_5354(MTNNumber,ssn,zipCode,iMEINumber1,recieptId,customerDetails,creditCard,simType1,stopActivation);

			//DB Errors
			//DBError.navigateDBErrorPage();
			//Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Log.endTestCase("QA5354_VerizonEdgeUpUpgradeWithDFAndPaymentRequired");
			Reporter.log("<h2><font color='green'> Test Case Completes </font></h2>");
		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			Utilities.driverTakesScreenshot("QA5354_VerizonEdgeUpUpgradeWithDFAndPaymentRequired");
			Assert.assertTrue(false);
		}
	}

	// region QA-5266
	@Test(groups={"verizon"})
	public void QA_5266_VerizonNonEdgeBuddyEarlyUpgrade() throws
	InterruptedException, AWTException, IOException,
	ParserConfigurationException, SAXException, org.xml.sax.SAXException{

		String testType = BrowserSettings.readConfig("test-type");
		String orderId = "";
		String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
		String phoneNumberLine1 = "";String phoneNumberLine2 = "";
		int line1 = 1;
		AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeBuddyEarlyUpgrade);
		String iMEINumber1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
		String simType1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
		CustomerDetails customerDetails = CSVOperations.ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		CreditCardDetails creditCard = new CreditCardDetails();

		Reporter.log("<h2>Start - QA_5266_VerizonNonEdgeLikeForUnlike. <br></h2>" );
		Reporter.log("<h3>Description: Verizon-NonEdge-Buddy Early Upgrade full activation</h3>");
		Reporter.log("Launching Browser <br>", true);
		Log.startTestCase("QA5266_VerizonNonEdgeBuddyEarlyUpgrade");

		boolean stopActivation = Boolean.parseBoolean(BrowserSettings.readConfig("Activation"));
		if(testType.equals("internal"))
		{
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();
			PageBase.AdminPage().navigateToSimulator();

			if(readConfig("internalTestType").toLowerCase().equals("carrierresponder")){
				Reporter.log("<h3><U> Carrier Responder</U></h3>", true);

				PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
				PageBase.AdminPage().selectAPIConfig("Verizon");
				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().verizonCarrierTab);
				PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();

				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().versionsDropdown);
				PageBase.CarrierResponseXMLPage().selectOptions("current",
						"retrieveCustomerDetails", "custLookup_buddy_upgrade.xml");

				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
				Utilities.implicitWaitSleep(5000);

				String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				String filePath = "TestData//custLookup_buddy_upgrade.xml";
				Document doc = docBuilder.parse(new File(filePath));

				doc.setTextContent(xmlContent1);


				xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8155491829, "");
				PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
				Robot robot = new Robot();
				Utilities.copyPaste(xmlContent1, robot);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();


				Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().saveResponseButton);
				PageBase.CarrierResponseXMLPage().saveResponseButton.click();

				Reporter.log("<br>Carrier Responder Changes Done!!!", true);
			}
			else{
				PageBase.AdminPage().selectWebAPIResponse("Verizon", "BackendSimulator");
				PageBase.AdminPage().selectAPIConfig("Verizon");
				PageBase.AdminPage().retrieveCustomerDetails("MIXED_ELIGIBILITY_SIMPLE");
				try{
					if(!PageBase.AdminPage().firstPhoneTextBox.isEnabled());
				}
				catch(Exception e)
				{
					PageBase.AdminPage().addPhoneNumbers.click();
				}
				try{
					if(!PageBase.AdminPage().secondPhoneTextBox.isEnabled());
				}
				catch(Exception e)
				{
					PageBase.AdminPage().addPhoneNumbers.click();
				}
				PageBase.AdminPage().firstPhoneTextBox.clear();
				PageBase.AdminPage().firstPhoneTextBox.sendKeys(phoneNumberLine1);
				PageBase.AdminPage().secondPhoneTextBox.clear();
				PageBase.AdminPage().secondPhoneTextBox.sendKeys(phoneNumberLine2);

				PageBase.AdminPage().accountPlanType("Family Share");
				PageBase.AdminPage().save();
			}
		}
		else   //External
		{
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse("Verizon", "External");
			Reporter.log("<h3><U> External Server</U></h3>", true);
		}

		Utilities.switchPreviousTab();

		//Calling DBError utility to  find initial count or error in log files.
		DBError.navigateDBErrorPage();
		int initialCount = PageBase.AdminPage().totalErrorCount();

		// Switching to previous tab.
		Utilities.switchPreviousTab();

		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"),
				Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();
		Reporter.log("<br>Upgrade Eligibility Checker Selected");
		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		Reporter.log("<br> Verizon Tab Selected");
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(accountDetails.MTN);
		Reporter.log("<br> MTN Number Used: " +accountDetails.MTN);
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(accountDetails.SSN);
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys(accountDetails.Password);
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(customerDetails.Zip);
		PageBase.UECVerificationPage().continueVerizonButton.click();

		List<WebElement> element = PageBase.UECAddLinesPage().numbersUnderAccount;
		System.out.println(element.size());
		for(int i = 0;i<element.size();i++){
			WebElement e = element.get(i);
			if((driver.findElement(By.xpath(".//li[(@class='upgradeelig-block2')]/div[1]/div/label/span/span[2]")).
					getAttribute("class").contains("icon-checkbox-on") && e.getAttribute("data-eligible").equals("Y"))){
				driver.findElement(By.xpath("ul/li[2]/div[1]/div/label/span/span[2]")).click();
			}
			if(e.getAttribute("data-eligible").equals("Y")){
				if(phoneNumberLine1==""){
					line1=1;
					phoneNumberLine1 = e.getAttribute("data-phonenumber");
					Reporter.log("<br> Eligible To Transfer: " +phoneNumberLine1);
				}
			}
			if(e.getAttribute("data-eligible").equals("N")){
				phoneNumberLine2 = e.getAttribute("data-phonenumber");
				Reporter.log("<br> Buddy Number: " +phoneNumberLine2);
			}
			System.out.println(e.getAttribute("data-phonenumber"));
			System.out.println("\n");
		}


		String phoneFormat = "Line "+line1+" - "+CommonFunction.getFormattedPhoneNumber(phoneNumberLine1);
		System.out.println(phoneFormat);
		Utilities.dropdownSelect(driver.findElement(By.id("select-reason-2")),SelectDropMethod.SELECTBYTEXT,phoneFormat);

		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber1);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
		PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		Utilities.implicitWaitSleep(6000);
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);

		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth,Integer.parseInt(customerDetails.IDExpirationYear));

		PageBase.ServiceProviderVerificationPage().guestCreditCheckCheckbox.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
//		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
//		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.getText(), phonePrice);
//		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
//		String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
//		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().twoYearsUpgradeLabel.isDisplayed());
//		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
//		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
//		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
//		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		if (readConfig("Activation").equals("true")) {
			PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
			PageBase.TermsandConditionsPage().continueTCButton.click();

			//Payment Required Page //ToDo: Remove this when no insurance bug will fix premanently.
			Utilities.implicitWaitSleep(4000);
			String url = driver.getCurrentUrl();
			if (url.contains("payment")) {
				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.valueOf(creditCard.CardType.toUpperCase()));
				PageBase.PaymentRequiredPage().sameAddressTab.click();
				PageBase.PaymentRequiredPage().continuePRButton.click();
			}

			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(receiptId);
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation Page
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber1);
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
			PageBase.DeviceVerificationaandActivation().simType.clear();
			PageBase.DeviceVerificationaandActivation().simType.sendKeys(simType1);
			try  //ToDo:Remove this when no insurance bug will fix permanently.
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");
			}
			catch (Exception e) {
				Reporter.log("CVN Number entered");
			}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);

			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(phoneNumberLine1));
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber1);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simType1);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);

			//Ship Admin
			ShipAdminBaseClass.launchShipAdminInNewTab();
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
			Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
			Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));

			//Inventory Management
			PageBase.InventoryManagementPage().launchInventoryInNewTab();
			PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber1, Constants.SOLD);

			//DBError Verification.
			DBError.navigateDBErrorPage();
			Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			PageBase.CSVOperations();
			CSVOperations.WriteToCSV("QA_5355",orderId,iMEINumber1,"","",customerDetails.FirstName,customerDetails.LastName,
					customerDetails.EMail,receiptId,customerDetails.IDType,customerDetails.State,
					customerDetails.IDNumber, customerDetails.PhNum,customerDetails.Zip,accountDetails.SSN,
					customerDetails.IDExpirationMonth,customerDetails.IDExpirationYear);
		}
		else
			Reporter.log("STOPPING ACTIVATION PURPOSEFULLY. Change test settings if Activation is needed");

		Reporter.log("<h2><font color='green'> Test Case Completes </font></h2>");
	}
	//endregion QA-5266

	//region QA 5357
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA5357_VerizonEdgeUpUpgradeWithDFAndPaymentNotRequired (@Optional String testType) throws IOException, AWTException, InterruptedException {
		testType = BrowserSettings.readConfig("test-type");
		CreditCardDetails creditCard = new CreditCardDetails();
		creditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
		String orderId = "";
		String phoneNumberLine1 = "";
		String receiptId = "";
		String iMEINumber1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SamsungGalaxyS4_16GBWhite);
		String simType1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);
		AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonEdgeUpgrade);
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		String MTNNumber = accountDetails.MTN;
		String ssn = accountDetails.SSN;
		String zipCode = customerDetails.Zip;
		String recieptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);

		Reporter.log("<h2>Start - QA5357_VerizonEdgeUpUpgradeWithDFAndPaymentNotRequired. <br></h2>");
		Reporter.log("<h3>Description: QA5357_VerizonEdgeUpUpgradeWithDFAndPaymentNotRequired</h3>");
		Reporter.log("Launching Browser <br>", true);
		Log.startTestCase("QA5357_VerizonEdgeUpUpgradeWithDFAndPaymentNotRequired");

		//Test Type Settings
		boolean stopActivation = Boolean.parseBoolean(BrowserSettings.readConfig("Activation"));

		try {
			//Verify whether which enviorement to use internal or external.
			if (testType.equals("internal")) {
				//setupInternalEnviornment_QA_5354(ssn, zipCode, MTNNumber, customerDetails);
			} else {
				AdminBaseClass adminBaseClass = new AdminBaseClass();
				adminBaseClass.launchAdminInNewTab();

				PageBase.AdminPage().navigateToSimulator();
				PageBase.AdminPage().selectWebAPIResponse(carrierType, "External");

			}

			poaCompleteFlow_QA_5357();
			poaExchangeDevice_QA_5357();

		}
		catch(Exception ex)
		{
			Log.error(ex.getMessage());
			Utilities.driverTakesScreenshot("QA5357_VerizonEdgeUpUpgradeWithDFAndPaymentNotRequired");
			Assert.assertTrue(false);
		}
	}
	//endregion QA 5357

	//region Helper and Refactored Methods
	private void selectingVerizonExternalEnvironment() throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();
		PageBase.AdminPage().navigateToSimulator();
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "External");
		Reporter.log("<h3><U> External Server</U></h3>", true);
	}

	private void poaCompleteFlow_QA_76(String phoneNumber, String iMEINumber, String simNumber) throws IOException, InterruptedException, AWTException {
		try {
			PageBase.CSVOperations();
			AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonEdgeUpgrade);
			CustomerDetails customerDetails = CSVOperations.ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
			CreditCardDetails creditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
			String MTNNumber = accountDetails.MTN;
			String SSN = accountDetails.SSN;
			String zipCode = customerDetails.Zip;

			//Login
			PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId2766"));

			//Home Page
			PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

			//UEC Verification Page
			Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
			PageBase.UECVerificationPage().verizonTab.click();
			PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(MTNNumber);
			PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(SSN);
			PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
			PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(zipCode);
			PageBase.UECVerificationPage().continueVerizonButton.click();

			//UEC Add Lines Page
			Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
			PageBase.UECAddLinesPage().clickCheckboxForParticularPhoneNumber(phoneNumber); //In external, need to select first line.
			PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

			//Device Scan Page
			Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
			PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber);
			PageBase.DeviceScanPage().submitDeviceButton.click();

			//Verizon Edge Page
			Utilities.waitForElementVisible(PageBase.VerizonEdgePage().YesCheckEligibilityButton);
			PageBase.VerizonEdgePage().YesCheckEligibilityButton.click();

			// Credit Check Page.
			Utilities.waitForElementVisible(PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox);
			CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails();

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

			//Verizon Edge
			Utilities.waitForElementVisible(PageBase.VerizonEdgePage().customerDownPayment);
			PageBase.VerizonEdgePage().customerDownPayment.sendKeys("100");
			PageBase.VerizonEdgePage().submit.click();
			PageBase.VerizonEdgePage().monthlyDownPayment.click();
			PageBase.CartPage().continueCartButton.click();

			//Verizon Shop Plans Page
			Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().verizonMoreEverythingUnlimitedMinutesAndMessaging500MBDataAddButton);
			PageBase.VerizonShopPlansPage().verizonMoreEverythingUnlimitedMinutesAndMessaging500MBDataAddButton.click();

			//Cart Page
			Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
			Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
			String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
			String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
			PageBase.CartPage().continueCartButton.click();

			//Verizon Select Plan Features Page
			Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
			//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
			PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

			//Select Protection Plan Insurance Page
			Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
			PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
			PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
			Utilities.implicitWaitSleep(5000);
			PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
			PageBase.CommonControls().continueButton.click();

			//Order Review and Confirm Page
			Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
			//Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
			//Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.getText(), phonePrice);
			//Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
			//String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
			//Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().twoYearsUpgradeLabel.isDisplayed());
			//Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
			//String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
			//Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
			//String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
			PageBase.CommonControls().continueCommonButton.click();

			//Terms & Conditions Page
			Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
			PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
			PageBase.TermsandConditionsPage().continueTCButton.click();

			//Payment Required Page //ToDo: Remove this when no insurance bug will fix premanently.
			Utilities.implicitWaitSleep(8000);
			String url = driver.getCurrentUrl();
			if (url.contains("payment")) {
				Utilities.waitForElementVisible(PageBase.PaymentRequiredPage().sameAddressTab);
				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.valueOf(creditCard.CardType.toUpperCase()));
				PageBase.PaymentRequiredPage().sameAddressTab.click();
				PageBase.PaymentRequiredPage().continuePRButton.click();
			}

			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().storeLocationValuePMSSText.getText(), Constants.SAN_FRANCISCO_CENTRAL_2766);
			//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
			//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
			//Assert.assertTrue(PageBase.PrintMobileScanSheetPage().barcodePMSSImage.isDisplayed());
			String orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(Utilities.receiptCalculator());
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation Page
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber);
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
			PageBase.DeviceVerificationaandActivation().simType.clear();
			PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber);
			try  //ToDo: Remove this when no insurance bug will fix premanently.
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(creditCard.CVV);
			} catch (Exception e) {
			}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			String termasAndConditionsContent = PageBase.WirelessCustomerAgreementPage().termsAndConditionsDiv.getText();
			//Assert.assertTrue(termasAndConditionsContent.contains(customerDetails.FirstName));
			//Assert.assertTrue(termasAndConditionsContent.contains(customerDetails.LastName));
			//Assert.assertTrue(termasAndConditionsContent.contains("BRIAN"));
			//Assert.assertTrue(termasAndConditionsContent.contains("JETER"));
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
			if (readConfig("Activation").contains("true")) {
				Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().continueWCAButton);
				PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

				//Device Finance and Installement Contarct page.
				Utilities.waitForElementVisible(PageBase.DeviceFinancingInstallmentContractPage().print);
				PageBase.DeviceFinancingInstallmentContractPage().print.click();
				Utilities.implicitWaitSleep(3000);
				Robot robot = new Robot();
				Utilities.sendKeys(KeyEvent.VK_ENTER, robot);
				Utilities.implicitWaitSleep(6000);
				Utilities.waitForElementVisible(PageBase.DeviceFinancingInstallmentContractPage().guestAcceptChkBox);
				PageBase.DeviceFinancingInstallmentContractPage().guestAcceptChkBox.click();
				PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
				PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();
				Utilities.implicitWaitSleep(2000);

				//Ship Admin
				ShipAdminBaseClass.launchShipAdminInNewTab();
				PageBase.OrderSummaryPage().goToOrderSummaryPage(orderId);
				String eventLogTableContent = PageBase.OrderSummaryPage().checkForErrorAndLog(orderId);
				String status = PageBase.OrderSummaryPage().getOrderStatus();
				//Assert.assertEquals(status, Constants.DEVICE_FINANCE_SIGNATURE_REQUIRED);
				Assert.assertTrue(eventLogTableContent.contains(Constants.FINANCE_CONTRACT));

				//Inventory Management
				PageBase.InventoryManagementPage().launchInventoryInNewTab();
				PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber, Constants.SOLD);
			}
			else {
				Reporter.log("<h3><font color='red'> Activation is stopped purposefully. Change the key in Test Settings to Activate </h3></font>");
			}

			Log.endTestCase("QA76_VerizonNonEdgeUpgradeNewPlan");
		}catch (Exception ex)
		{
			throw ex;
		}
	}

	private void selectCarrierResponderAPISetUp(String phoneNumber) throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();
		PageBase.AdminPage().navigateToSimulator();
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
		//Selecting Use Case from dropdown list.
		PageBase.AdminPage().selectAPIConfig("Verizon");
		PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();

		PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails", "vwz_accountLookup_LLP_3lines_eligible.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8155491829, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Robot robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();

		PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCreditApplication", "upgrade_approved_edge.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent2 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent2 = xmlContent2.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent2, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();

		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitServiceDetails", "success_edge.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent3 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent3 = xmlContent3.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent3, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();

		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitactivation", "success_1_line.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent4 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent4 = xmlContent4.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent4, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();

		PageBase.CarrierResponseXMLPage().selectOptions("current", "retrievePricePlans", "plaid_vd_300mb_2_line.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent5 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent5 = xmlContent5.replace(Constants.DEFAULT_XML_NUMBER_4152648022, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent5, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();

		PageBase.CarrierResponseXMLPage().selectOptions("current", "changePricePlan", "default.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent6 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent6 = xmlContent6.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent6, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();

		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitCreditApplication", "approved.xml");
		Utilities.implicitWaitSleep(5000);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
	}

	private String poaFlowQA58() throws IOException, AWTException, InterruptedException {
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId2766"));

		CustomerDetails customerDetails = CSVOperations.ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId);
		String esnNo = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.FamilyPlanPlaidUpgrade);
		String simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);

		AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeBuddyEarlyUpgrade);
		String MTNNumber = accountDetails.MTN;
		String accountPassword = accountDetails.Password;
		String SSN = accountDetails.SSN;
		String zipCode = customerDetails.Zip;

		CreditCardDetails creditCard = new CreditCardDetails();
		creditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);

		//Click on Upgrade Eligibility Check link
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//CustomerDetails customerDetails = PageBase.ExcelOperations().GetCustomerDetails(IdType.DRIVERLICENCE);

		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		//Fill Phone Number,SSN,Zipcode and Password values
		//PageBase.UECVerificationPage().fillVerizonDetails("7766554433","4850","HELLO","94109");
		//		PageBase.UECVerificationPage().fillVerizonDetails();
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//Choose the Second line To Upgrade
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().secondAALCheckbox);
		PageBase.UECAddLinesPage().addALine();
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);

		//Enter the IMEI/SSN Details
		PageBase.DeviceScanPage().enterDeviceScanDetails(esnNo);

		//Choose Verizon Non Edge Device
		//  Utilities.waitForElementVisible(PageBase.VerizonEdgePage().declineButton);
		//  PageBase.VerizonEdgePage().declineButton.click();
		Utilities.waitForElementVisible(PageBase.CommonControls().cancelButton);
		PageBase.CommonControls().cancelButton.click();
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().verizonMorePlanOnly);
		System.out.println("Plan Name"+PageBase.VerizonShopPlansPage().verizonMorePlanOnly.getText());

		//Select a Plan which has 'More'
		String orderText =  PageBase.VerizonShopPlansPage().selectPlanWithMore();
		PageBase.VerizonShopPlansPage().addPlan();

		//Select Any one Number from the Dropdown to Assign plan
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		PageBase.CartPage().selectPhoneNumber();
		PageBase.CommonControls().continueCommonButton.click();
		//Select a Feature
		PageBase.VerizonSelectPlanFeaturesPage().clickContinue();

		//Select the First Insurance
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsuranceFirst);
		PageBase.SelectProtectionPlanInsurancePage().selectAnInsurance();

		//Fill in Credit Check Details
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);

		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth,Integer.parseInt(customerDetails.IDExpirationYear));
		PageBase.ServiceProviderVerificationPage().guestCreditCheckCheckbox.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();

		//Verify Plan and Feature Details Information on the Order Confirmation Page

		String TwoYearText = PageBase.OrderReviewAndConfirmPage().verify2yrUpgradeText.getText();
		String getOrderDetails = PageBase.OrderReviewAndConfirmPage().GetOrderDetails();
		//Assert.assertEquals(TwoYearText, "2 Year Upgrade:","Feature plan Matches");
		Assert.assertTrue(getOrderDetails.contains(orderText), "Both Plans are not Equal");
		Assert.assertTrue(getOrderDetails.contains(TwoYearText),"Both Feature Plans are not Equal");
		PageBase.CommonControls().continueCommonButton.click();

		//Accept terms and conditions
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		/*PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(Utilities.receiptCalculator());
        PageBase.CommonControls().submitButton.click(); */

		//Enter Credit Card Details
		/* Utilities.waitForElementVisible(PageBase.PaymentRequiredPage().creditCardNumberTextbox);
       CreditCardDetails creditCard = PageBase.ExcelOperations().GetCreditCardDetails(CardType.VISA);
      // PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA, String.valueOf(creditCard.Number), MonthIdExp.JULY, creditCard.ExpiryYear, creditCard.CVV);
       PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA, "4111111111111111", MonthIdExp.JULY.toString(), 2020, 123); //ToDo: Need to read from data sheet.
      PageBase.PaymentRequiredPage().sameAddressTab.click();
      PageBase.PaymentRequiredPage().continuePRButton.click();   */

		Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);

		String orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
		System.out.println("OrderID"+orderId);
		PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();
		//Click continue on Print Mobile Scan Page
		//  PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

		//Enter Receipt ID ,Sim ,CVN details
		// Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);

		PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(receiptId);//132710003003669460
		PageBase.PaymentVerificationPage().submitButton.click();
		Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);

		PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(esnNo); //"2113114115116110"
		PageBase.DeviceVerificationaandActivation().submitDVAButton.click();

		PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber);//21212121212121212121
		// PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");  // ToDo: Read from data sheet.
		PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

		// Click on Accept Terms and Conditions
		Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
		PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();

		//Save Signature
		PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
		PageBase.TermsandConditionsPage().saveSignatureButton.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		//Verify Order generated on Receipt Page
		Utilities.waitForElementVisible(PageBase.OrderReceiptPage().orderCompletionText);
		PageBase.OrderReceiptPage().verifyOrderCompletionPage();
		PageBase.CSVOperations().UpdateIMEIStatusToUsed(esnNo, FileName.SamsungGalaxyS4_16GBWhite);

		if(readConfig("Activation")=="true") {
			//Shipadmin verification.
			shipAdminVerification(orderId);
			//Verifying Inventory mangement.
			PageBase.InventoryManagementPage().launchInventoryInNewTab();
			PageBase.InventoryManagementPage().verifyDeviceStatus(simNumber, Constants.SOLD);//2113114115116110
		}
		return orderId;
	}

	private void shipAdminVerification(String orderId) {
		ShipAdminBaseClass.launchShipAdminInNewTab();
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
		Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
		//Assert.assertTrue(PageBase.OrderSummaryPage().additionalInfoValueText.getText().contains(Constants.EXISTING_ACCOUNT_HOLDER));
		Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));
	}

	private void selectingCarrierEnviornment(@Optional String testType) throws InterruptedException, AWTException, IOException {
		if(testType.equals("internal"))
		{
			// Need to set "Backend Simulator or Carrier Responder depend on test case  requirement.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();

			//selectCarrierResponder();

			selectbackenSimulator();
		}
		else   //External
		{
			// Need to set External server from Admin page.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse("Verizon","External");
		}
	}

	private void selectCarrierResponder() throws IOException, InterruptedException {
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");

		//Selecting Use Case from dropdown list.
		PageBase.AdminPage().selectAPIConfig("Verizon");
		PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails", "LLP_5_line.xml");
		PageBase.CarrierResponseXMLPage().SelectCarrierResponserXML();
	}

	private void selectbackenSimulator()throws IOException, InterruptedException {
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "BackendSimulator");
	}

	private CarrierCreditCheckDetails getCarrierCreditCheckDetails() throws IOException {
		CarrierCreditCheckDetails cccDetails = new CarrierCreditCheckDetails();
		PageBase.CSVOperations();
		CustomerDetails customerDetails = CSVOperations.ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
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
		cccDetails.setSSN(PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.SSNWithDeposit));

		cccDetails.setIdTypeState(customerDetails.IDState);
		cccDetails.setIdNumber(customerDetails.IDNumber);
		cccDetails.setMonth(customerDetails.IDExpirationMonth);
		cccDetails.setYear(customerDetails.IDExpirationYear);
		cccDetails.setIDType(IdType.DRIVERLICENCE);
		return cccDetails;
	}

	private void poaCompleteFlow_QA_5354(String MTNNumber,String ssn,String zipCode,String iMEINumber1,String recieptId,CustomerDetails customerDetails,CreditCardDetails  creditCard,String simType1, boolean stopActivation) throws IOException, InterruptedException, AWTException {
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"),
				Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(MTNNumber);
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(String.valueOf(ssn));
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(String.valueOf(zipCode));
		PageBase.UECVerificationPage().continueVerizonButton.click();

		Reporter.log("<br> UEC - Add Lines Page");

		String makePaymentButton = "//a[@name='paymentButton-"+MTNNumber+"']";
		Utilities.waitForElementVisible(driver.findElement(By.xpath(makePaymentButton)));
		driver.findElement(By.xpath(makePaymentButton)).click();

		String cardNumber = "cardNumber-"+MTNNumber;
		driver.findElement(By.id(cardNumber)).sendKeys(creditCard.Number);
		String cardcvvNumber = "ccv2-"+MTNNumber;
		driver.findElement(By.id(cardcvvNumber)).sendKeys(creditCard.CVV);

		String month = "card-month-"+MTNNumber;
		PageBase.ServiceProviderVerificationPage().SetMonthWithIndex(driver.findElement(By.id(month)),
				creditCard.ExpiryMonth.toUpperCase());

		String year = "card-year-"+MTNNumber;
		Utilities.dropdownSelect(driver.findElement(By.id(year)), SelectDropMethod.SELECTBYTEXT, creditCard.ExpiryYear);

		driver.findElement(By.xpath(".//span[contains(text(),'Use a different address')]")).click();;
		String firstName = "form-firstName-"+MTNNumber;
		driver.findElement(By.id(firstName)).sendKeys(customerDetails.FirstName);;

		String lastName = "form-lastName-"+MTNNumber;
		driver.findElement(By.id(lastName)).sendKeys(customerDetails.LastName);;

		String address1 = "form-address1-"+MTNNumber;
		driver.findElement(By.id(address1)).sendKeys(customerDetails.Address1);;

		String city = "form-city-"+MTNNumber;
		driver.findElement(By.id(city)).sendKeys(customerDetails.City);;

		String state = "form-state-"+MTNNumber;
		Utilities.dropdownSelect(driver.findElement(By.id(state)),SelectDropMethod.SELECTBYTEXT,customerDetails.State);

		String cardAddressZip = "form-cardAddressZip-"+MTNNumber;
		driver.findElement(By.id(cardAddressZip)).sendKeys(customerDetails.Zip);;

		Thread.sleep(2000);
		driver.findElement(By.xpath(".//button[contains(text(),'submit & continue')]")).click();
		Reporter.log("<br> Entered Credit Card Details");
		Thread.sleep(5000);

		Utilities.waitForElementVisible(driver.findElement(By.id("checkbox-mini-0")));
		driver.findElement(By.id("checkbox-mini-0")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//button[contains(text(),'continue')]")).click();

		//UEC Add Lines Page
		//Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		//Assert.assertTrue(PageBase.UECAddLinesPage().eligibleForDF.getText().contains("Eligible for Device Finance\n"));
		//PageBase.UECAddLinesPage().clickCheckboxForParticularPhoneNumber(MTNNumber);
		//PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber1);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().YesCheckEligibilityButton);
		PageBase.VerizonEdgePage().YesCheckEligibilityButton.click();

		//Filling information in Carrier Credit Check Page.
		Utilities.waitForElementVisible(PageBase.CarrierCreditCheckPage().populateForm);
		CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails();
		PageBase.CarrierCreditCheckPage().populatingCarrierCreditCheckPage(cccDetails);
		PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();
		PageBase.CommonControls().continueButton.click();

		try
		{
			PageBase.CommonControls().continueButton.click();
		}
		catch (Exception ex)
		{}

		// Installment Page
		Utilities.waitForElementVisible(PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton);
		PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton.click();
		PageBase.CommonControls().continueCommonButton.click();


		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
		PageBase.VerizonShopPlansPage().VerizonMoreEverything.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().appleCareInsurance);
		PageBase.SelectProtectionPlanInsurancePage().appleCareInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		Reporter.log("<br> Service Provider Verification Page");
		//PageBase.ServiceProviderVerificationPage().populatingSPVPage

		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth,Integer.parseInt(customerDetails.IDExpirationYear));
		Reporter.log("<br> Entered Details for "+customerDetails.FirstName +" "+ customerDetails.LastName);
		PageBase.ServiceProviderVerificationPage().guestCreditCheckCheckbox.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();


		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phoneMonthlyFee.getText(), phonePrice.replace('*', ' ').trim());
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		//Payment Required Page
		Utilities.implicitWaitSleep(8000);
		String url = driver.getCurrentUrl();
		if (url.contains("payment")) {
			PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
			PageBase.PaymentRequiredPage().sameAddressTab.click();
			PageBase.PaymentRequiredPage().continuePRButton.click();
			Reporter.log("<br> Entered Credit Card Details");
		}
		if (stopActivation == false)
		{
			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
			//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(PageBase.CSVOperations().GetIMEIAndSimNumber(FileName.ReceiptId));
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation Page
			PageBase.DeviceVerificationaandActivation().deviceVerificationActiavtionFor1Device(iMEINumber1,simType1);
			try
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(String.valueOf(creditCardDetails.CVV));
			}
			catch(Exception e){}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);

			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			PageBase.CSVOperations().UpdateIMEIStatusToUsed(iMEINumber1, FileName.iPhone5C);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(MTNNumber));
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber1);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simType1);

			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);

			//Ship Admin
			ShipAdminBaseClass.launchShipAdminInNewTab();
			PageBase.OrderSummaryPage().goToOrderSummaryPage(orderId);
			PageBase.OrderSummaryPage().verifyAppleCareInsuranceStatus();
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
			Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
			//Assert.assertTrue(PageBase.OrderSummaryPage().additionalInfoValueText.getText().contains(Constants.EXISTING_ACCOUNT_HOLDER));
			Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));

			//Inventory Management
			PageBase.InventoryManagementPage().launchInventoryInNewTab();
			PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber1, Constants.SOLD);
		}
	}

	private void setupInternalEnviornment_QA_5354(String SSN,String zipCode,String MTNNumber,CustomerDetails customerDetails ) throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();
		PageBase.AdminPage().navigateToSimulator();
		String phoneNumberLine1 =  String.valueOf((long)(Math.floor(Math.random() * 9000000000L) + 6000000000L));
		phoneNumberLine1 = phoneNumberLine1.substring(0,10);
		if(readConfig("internalTestType").toLowerCase().equals("carrierresponder")) {
			Reporter.log("<h3><U> Carrier Responder</U></h3>", true);

			PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
			PageBase.AdminPage().selectAPIConfig("Verizon");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().verizonCarrierTab);
			PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();

			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().versionsDropdown);

			PageBase.CarrierResponseXMLPage().selectOptions("current",
					"retrieveCustomerDetails", "vwz_edge_up_eligible_payment_needed.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_SSN, SSN);
			xmlContent1 = xmlContent1.replace("07920", zipCode);
			xmlContent1 = xmlContent1.replace("6567778888", MTNNumber);
			xmlContent1 = xmlContent1.replace("281", Utilities.getCredentials("storeId0003"));
			xmlContent1 = xmlContent1.replace("SEPTEMBER TEST", customerDetails.FirstName + " " + customerDetails.LastName);
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			Robot robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);


			PageBase.CarrierResponseXMLPage().selectOptions("current",
					"retrieveExistingCustomerInstallmentDetails", "eligible_payment_needed.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			xmlContent1 = "";
			xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace("6567778888", MTNNumber);
			xmlContent1 = xmlContent1.replace("281", Utilities.getCredentials("storeId0003"));
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);

			PageBase.CarrierResponseXMLPage().selectOptions("current",
					"retrieveCreditApplication", "upgrade_approved_edge.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, MTNNumber);
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_OF_LINES, "001");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887, Utilities.getCredentials("storeId0003"));
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_ZipCode, zipCode);
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);


			PageBase.CarrierResponseXMLPage().selectOptions("current",
					"retrievePricePlans", "plaid_v_1_line.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, MTNNumber);
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
					Utilities.getCredentials("storeId0003"));
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);


			PageBase.CarrierResponseXMLPage().selectOptions("current",
					"submitCreditApplication", "approved.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887,
					Utilities.getCredentials("storeId0003"));
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);


			PageBase.CarrierResponseXMLPage().selectOptions("current",
					"submitReceipt", "default.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887,
					Utilities.getCredentials("storeId0003"));
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);


			PageBase.CarrierResponseXMLPage().selectOptions("current",
					"submitServiceDetails", "default.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
					Utilities.getCredentials("storeId0003"));
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, MTNNumber);
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);


			PageBase.CarrierResponseXMLPage().selectOptions("current", "submitactivation", "success_1_line.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, MTNNumber);
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
					Utilities.getCredentials("storeId0003"));
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().saveResponseButton);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);

			Reporter.log("<br>Carrier Responder Changes Done!!!", true);
		}

		else{
			PageBase.AdminPage().selectWebAPIResponse("Verizon", "BackendSimulator");
			PageBase.AdminPage().selectAPIConfig("Verizon");
			//PageBase.AdminPage().retrieveCreditApplication("APPROVED_EDGEUP");
			PageBase.AdminPage().retrieveCustomerDetails("VZW_EDGE_UP_ELIGIBLE");

			try{
				if(!PageBase.AdminPage().firstPhoneTextBox.isEnabled());
			}
			catch(Exception e)
			{
				PageBase.AdminPage().addPhoneNumbers.click();
			}

			try{
				if(PageBase.AdminPage().secondPhoneTextBox.isEnabled()){
					PageBase.AdminPage().secondPhoneRemoveTextBox.click();
				}
			}
			catch(Exception e)
			{
				PageBase.AdminPage().addPhoneNumbers.click();
			}

			PageBase.AdminPage().firstPhoneTextBox.clear();
			PageBase.AdminPage().firstPhoneTextBox.sendKeys(MTNNumber);

			PageBase.AdminPage().accountPlanType("Individual");
			PageBase.AdminPage().selectCreaditReadUseCase("APPROVED_EDGEUP");
			PageBase.AdminPage().retrieveExistingCustomerInstallmentsDetails("SUCCESS_WITH_PAYMENT");
			PageBase.AdminPage().retrievePricePlan(Constants.SUCCESS);
			PageBase.AdminPage().submitCreditApplication(Constants.APPROVED);
			PageBase.AdminPage().submitReciept(Constants.SUCCESS);
			PageBase.AdminPage().submitServiceDetails(Constants.SUCCESS);
			PageBase.AdminPage().submitEdgeUpPayment(Constants.SUCCESS);
			PageBase.AdminPage().save();
			Utilities.implicitWaitSleep(1000);
		}
	}

	private void setupBackendSimulator_QA_5354() throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();

		PageBase.AdminPage().navigateToSimulator();
		PageBase.AdminPage().selectWebAPIResponse(carrierType, BrowserSettings.readConfig("backendSimulator"));
		PageBase.AdminPage().selectAPIConfig(carrierType);
		PageBase.AdminPage().selectCreaditReadUseCase("APPROVED_EDGEUP");
		PageBase.AdminPage().retrieveCustomerDetails("VZW_EDGE_UP_ELIGIBLE");
		PageBase.AdminPage().accountPlanType("Family Share");
		PageBase.AdminPage().retrievePricePlan("SUCCESS");
		PageBase.AdminPage().submitActivation("SUCCESS");
		PageBase.AdminPage().submitCreditApplication("APPROVED");
		PageBase.AdminPage().submitReciept("SUCCESS");
		PageBase.AdminPage().submitServiceDetails("SUCCESS");
		PageBase.AdminPage().submitEdgeUpPayment("SUCCESS");
		PageBase.AdminPage().returnOrExchangeDevice("SUCCESS");
		List<WebElement> phoneList =PageBase.AdminPage().phoneList.findElements(By.className("phoneNumberRow"));
		if(phoneList.size()>0)
		{
			if (phoneList.size()>1)
			{
				List<WebElement> removePhone =PageBase.AdminPage().phoneList.findElements(By.id("retrieveCustomerDetails_removePhoneNumberRowButton"));
				for (int i=0;i<removePhone.size()-1;i++)
				{
					removePhone.get(i).click();
				}
				PageBase.AdminPage().firstPhoneTextBox.clear();
				PageBase.AdminPage().firstPhoneTextBox.sendKeys(phoneNumber);
			}
			else
			{
				PageBase.AdminPage().firstPhoneTextBox.clear();
				PageBase.AdminPage().firstPhoneTextBox.sendKeys(phoneNumber);
			}
		}
		else {
			PageBase.AdminPage().addPhoneNumbers.click();
			PageBase.AdminPage().firstPhoneTextBox.clear();
			PageBase.AdminPage().firstPhoneTextBox.sendKeys(phoneNumber);
		}
		Utilities.implicitWaitSleep(2000);
		PageBase.AdminPage().save();
	}

	private void selectCarrierResponder_QA5573(@Optional String testType) throws InterruptedException, AWTException, IOException
	{
		//Verify whether which enviorement to use internal or external.
		if (testType.equals("internal")) {
			// Need to set "Backend Simulator or Carrier Responder depend on test case requirement.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();
			Utilities.implicitWaitSleep(10000);
			PageBase.AdminPage().widgetManagementOperations();
			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType, BrowserSettings.readConfig("internalTestType"));

			//Selecting Use Case from dropdown list.
			PageBase.AdminPage().selectAPIConfig(carrierType);

			//Customizing xml files in Carrier Responder
			PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails",
					"vwz_accountLookup_LLP_3lines_eligible.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8155491829, "9435831267");
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			Robot robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);
			Reporter.log("<br>Carrier Responder Changes Done!!!", true);
		} else {   //External// Need to set External server from Admin page.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType, "External");
		}
	}

	private String poaCompleteFlow_QA5573(boolean stopActivation) throws IOException, InterruptedException, AWTException {

		AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(FileName.VerizonNonEdgeUpgradeWithAppleCare);
		CustomerDetails cusDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		String MTNNumber = accountDetails.MTN;
		String accountPassword = accountDetails.Password;
		String SSN = accountDetails.SSN;
		String zipCode = cusDetails.Zip;
		CreditCardDetails creditCard = new CreditCardDetails();
		creditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
		imei1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.iPhone5C);
		String simType1 = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.Sim_3FF);

		//Login
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		//Home Page
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.STATEID);
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(MTNNumber);
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(SSN);
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(zipCode);
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		Assert.assertTrue(PageBase.UECAddLinesPage().eligibleForDF.getText().contains("Eligible for Device Finance\n"));
		PageBase.UECAddLinesPage().clickCheckboxForParticularPhoneNumber(MTNNumber);
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(imei1);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Edge Page
		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().noContinueWith2YearButton);
		PageBase.VerizonEdgePage().noContinueWith2YearButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().verizonMoreEverythingUnlimitedMinutesAndMessaging500MBDataAddButton);
		PageBase.VerizonShopPlansPage().verizonMoreEverythingUnlimitedMinutesAndMessaging500MBDataAddButton.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().appleCareInsurance);
		PageBase.SelectProtectionPlanInsurancePage().appleCareInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		//CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		//PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, "", customerDetails.LastName, customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.DLNumber, Month.FEBRUARY, customerDetails.DLExpirationYear);
		PageBase.ServiceProviderVerificationPage().populatingSPVPage
		(customerDetails.FirstName, customerDetails.LastName, customerDetails.EMail,
				IdType.DRIVERLICENCE, customerDetails.IDState, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth, Integer.parseInt(customerDetails.IDExpirationYear));
		PageBase.ServiceProviderVerificationPage().guestCreditCheckCheckbox.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.getText(), phonePrice);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().twoYearsUpgradeLabel.isDisplayed());
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		//Payment Required Page
		Utilities.implicitWaitSleep(8000);
		if (driver.getCurrentUrl().contains("payment")) {

			PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
			Utilities.ClickElement(PageBase.PaymentRequiredPage().sameAddressTab);
			Utilities.ClickElement(PageBase.PaymentRequiredPage().continuePRButton);
		}

		//Print Mobile Scan Sheet Page
		Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
		//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
		//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
		String orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
		PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

		//Payment Verification Page
		Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
		PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId));
		PageBase.PaymentVerificationPage().submitButton.click();

		//Device Verification and Activation Page
		Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
		PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(imei1);
		PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
		PageBase.DeviceVerificationaandActivation().simType.clear();
		PageBase.DeviceVerificationaandActivation().simType.sendKeys(simType1);
		try {
			PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
			//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(String.valueOf(creditCardDetails.CVV));
		} catch (Exception e) {
		}
		PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

		//Wireless Customer Agreement Page
		Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
		PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
		PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
		if (stopActivation == false) {
			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(""));
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), imei1);
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simNumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);
		}
		return orderId;
	}

	private void inventoryManagementVerification_QA5573() throws InterruptedException, AWTException, IOException {
		PageBase.InventoryManagementPage().launchInventoryInNewTab();
		Utilities.implicitWaitSleep(5000);
		PageBase.InventoryManagementPage().verifyDeviceStatus(imei1, InventoryManagementBaseClass.IMEIStatus.Sold.toString());
	}

	private void shipAdminVerification_QA5573(String orderId) throws InterruptedException, AWTException, IOException {
		//Ship Admin
		ShipAdminBaseClass.launchShipAdminInNewTab();
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
		Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
		//Assert.assertTrue(PageBase.OrderSummaryPage().additionalInfoValueText.getText().contains(Constants.EXISTING_ACCOUNT_HOLDER));
		Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));
	}

	private void selectCarrierResponder_QA72(@Optional String testtype, String MTNNumber) throws InterruptedException, AWTException, IOException
	{
		testtype = BrowserSettings.readConfig("test-type");
		//Verify whether which enviorement to use internal or external.
		if (testtype.equals("internal")) {
			// Need to set "Backend Simulator or Carrier Responder depend on test case requirement.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();
			Utilities.implicitWaitSleep(10000);
			PageBase.AdminPage().widgetManagementOperations();
			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType, BrowserSettings.readConfig("internalTestType"));

			//Selecting Use Case from dropdown list.
			PageBase.AdminPage().selectAPIConfig(carrierType);

			//Customizing xml files in Carrier Responder
			PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails",
					"vwz_accountLookup_LLP_3lines_eligible.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8155491829, MTNNumber);
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			Robot robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);
		} else {   //External// Need to set External server from Admin page.
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType, "External");
		}
	}

	private String poaCompleteFlow_QA72(boolean stopActivation, String MTNNumber, String sSN, String zip) throws IOException, InterruptedException, AWTException {

		//Login
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		//Home Page
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(MTNNumber);
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(String.valueOf(sSN));
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(String.valueOf(zip));
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		Assert.assertTrue(PageBase.UECAddLinesPage().eligibleForDF.getText().contains("Eligible for Device Finance\n"));
		PageBase.UECAddLinesPage().clickCheckboxForParticularPhoneNumber(MTNNumber);
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Edge Page
		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().YesCheckEligibilityButton);
		PageBase.VerizonEdgePage().YesCheckEligibilityButton.click();

		//Filling information in Carrier Credit Check Page.
		Utilities.waitForElementVisible(PageBase.CarrierCreditCheckPage().populateForm);
		CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails();
		PageBase.CarrierCreditCheckPage().populatingCarrierCreditCheckPage(cccDetails);
		PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();
		PageBase.CommonControls().continueButton.click();
		PageBase.CommonControls().continueButton.click();

		// Installment Page
		Utilities.waitForElementVisible(PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton);
		PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton.click();
		PageBase.CommonControls().continueCommonButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().VerizonMoreEverything);
		PageBase.VerizonShopPlansPage().VerizonMoreEverything.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().appleCareInsurance);
		PageBase.SelectProtectionPlanInsurancePage().appleCareInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phoneMonthlyFee.getText(), phonePrice);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		//Payment Required Page
		Utilities.implicitWaitSleep(8000);
		String url = driver.getCurrentUrl();
		if(url.contains("payment"))
		{
			PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA); //ToDo: Need to read from data sheet.
			Utilities.ClickElement(PageBase.PaymentRequiredPage().sameAddressTab);
			Utilities.ClickElement(PageBase.PaymentRequiredPage().continuePRButton);
		}

		//Print Mobile Scan Sheet Page
		Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
		Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
		Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
		String orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
		PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

		//Payment Verification Page
		Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
		PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys("230440567552718374");
		PageBase.PaymentVerificationPage().submitButton.click();

		//Device Verification and Activation Page
		PageBase.DeviceVerificationaandActivation().deviceVerificationActiavtionFor1Device(iMEINumber, simNumber);
		try
		{
			PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
			//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(String.valueOf(creditCardDetails.CVV));
		}
		catch(Exception e){}
		PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

		//Wireless Customer Agreement Page
		Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
		PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
		PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
		if (stopActivation == false)
		{
			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(),
					CommonFunction.getFormattedPhoneNumber("9475831856"));
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber);
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simNumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);
		}
		return orderId;
	}

	private void inventoryManagementVerification_QA72() throws InterruptedException, AWTException, IOException {
		PageBase.InventoryManagementPage().launchInventoryInNewTab();
		Utilities.implicitWaitSleep(5000);
		PageBase.InventoryManagementPage().verifyDeviceStatus(imei1, InventoryManagementBaseClass.IMEIStatus.Sold.toString());
	}

	private void shipAdminVerification_QA72(String orderId) throws InterruptedException, AWTException, IOException {
		//Ship Admin
		ShipAdminBaseClass.launchShipAdminInNewTab();
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
		Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
		//Assert.assertTrue(PageBase.OrderSummaryPage().additionalInfoValueText.getText().contains(Constants.EXISTING_ACCOUNT_HOLDER));
		Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));
	}

	private void shipAdminVerificationsUpgrade(String orderId) {
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
		Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
		Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));
		Assert.assertTrue(PageBase.OrderSummaryPage().additionalInfoValueText.getText().contains(Constants.EXISTING_ACCOUNT_ORDER));
	}

	//region QA-62 Refactored Methods Region
	private String poaFlowQA62(String phoneNumber, String iMEINumber, String simNumber, String orderId, String sSN, String accountPassword, String zipCode, String receiptId) throws IOException {
		//Login
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		//Home Page
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(phoneNumber);
		//PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys("1234");
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(sSN);
		//PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys(accountPassword);
		//PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys("98104");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(zipCode);
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		PageBase.UECAddLinesPage().firstAALCheckbox.click();
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Edge Page
		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().noContinueWith2YearButton);
		PageBase.VerizonEdgePage().noContinueWith2YearButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
		PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		Utilities.implicitWaitSleep(6000);
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth, Integer.parseInt(customerDetails.IDExpirationYear));
		PageBase.ServiceProviderVerificationPage().guestAgreesCreditCheck.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		//Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.getText(), phonePrice); //Not matching right now
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().twoYearsUpgradeLabel.isDisplayed());
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		if (readConfig("Activation").toLowerCase().contains("true")) {
			PageBase.TermsandConditionsPage().continueTCButton.click();

			//Payment Required Page //ToDo: Remove this when no insurance bug will fix premanently.
			Utilities.implicitWaitSleep(4000);
			String url = driver.getCurrentUrl();
			if (url.contains("payment")) {
				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
				PageBase.PaymentRequiredPage().sameAddressTab.click();
				PageBase.PaymentRequiredPage().continuePRButton.click();
			}

			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
			//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(receiptId);
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation Page
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber);
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
			PageBase.DeviceVerificationaandActivation().simType.clear();
			PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber);
			try  //ToDo:Remove this when no insurance bug will fix permanently.
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");
				CreditCardDetails CreditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
				String cVNNumberS = "" + CreditCard.CVV;
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(cVNNumberS);
			} catch (Exception e) {
			}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);

			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simNumber);
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(phoneNumber));

			//Sending Details of this order to csv file which will be used in other TCs
			PageBase.CSVOperations();
			CSVOperations.WriteToCSV("QA_62", orderId, iMEINumber, "", "", customerDetails.FirstName, customerDetails.LastName,
					customerDetails.EMail, receiptId, customerDetails.IDType, customerDetails.State,
					customerDetails.IDNumber, customerDetails.PhNum, customerDetails.Zip, sSN, customerDetails.IDExpirationMonth, customerDetails.IDExpirationYear);
		}
		return orderId;
	}

	private void carrierResponderSettingsQA62(String phoneNumber) throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();
		PageBase.AdminPage().navigateToSimulator();
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
		//Selecting Use Case from dropdown list.
		PageBase.AdminPage().selectAPIConfig("Verizon");
		PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails", "vwz_accountLookup_LLP_3lines_eligible.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8155491829, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Robot robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitactivation", "success_1_line.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent2 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent2 = xmlContent2.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent2, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
	}
	//endregion QA-62 Refactored Methods Region

	//region QA-60 Refactored Methods Region
	private String poaFlowQA60(String phoneNumber, String iMEINumber, String simNumber, String orderId, String sSN, String accountPassword, String zipCode, String receiptId) throws IOException {
		//Login
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		//Home Page
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(phoneNumber);
		//PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys("1234");
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(sSN);
		//PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys(accountPassword);
		//PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys("98104");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(zipCode);
		PageBase.UECVerificationPage().continueVerizonButton.click();
		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		PageBase.UECAddLinesPage().clickCheckboxForParticularPhoneNumber(phoneNumber); //In external, need to select first line.
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Edge Page
		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().noContinueWith2YearButton);
		PageBase.VerizonEdgePage().noContinueWith2YearButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().verizonMoreEverythingUnlimitedMinutesAndMessaging500MBDataAddButton);
		PageBase.VerizonShopPlansPage().verizonMoreEverythingUnlimitedMinutesAndMessaging500MBDataAddButton.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		Utilities.implicitWaitSleep(5000);
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth, Integer.parseInt(customerDetails.IDExpirationYear));
		PageBase.ServiceProviderVerificationPage().guestAgreesCreditCheck.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();


		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		//Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.getText(), phonePrice); //Not Matching right now.
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().twoYearsUpgradeLabel.isDisplayed());
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		if (readConfig("Activation").toLowerCase().contains("true")) {
			PageBase.TermsandConditionsPage().continueTCButton.click();

			//Payment Required Page //ToDo: Remove this when no insurance bug will fix premanently.
			Utilities.implicitWaitSleep(8000);
			String url = driver.getCurrentUrl();
			if (url.contains("payment")) {
				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
				PageBase.PaymentRequiredPage().sameAddressTab.click();
				PageBase.PaymentRequiredPage().continuePRButton.click();
			}

			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().storeLocationValuePMSSText.getText(), Constants.SAN_FRANCISCO_CENTRAL_2766);
			//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
			//Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
			Assert.assertTrue(PageBase.PrintMobileScanSheetPage().barcodePMSSImage.isDisplayed());
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(receiptId);
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation Page
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber);
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
			PageBase.DeviceVerificationaandActivation().simType.clear();
			PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber);
			try  //ToDo: Remove this when no insurance bug will fix premanently.
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");
				CreditCardDetails CreditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
				String cVNNumberS = "" + CreditCard.CVV;
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(cVNNumberS);
			} catch (Exception e) {
			}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			String termasAndConditionsContent = PageBase.WirelessCustomerAgreementPage().termsAndConditionsDiv.getText();
			//Assert.assertTrue(termasAndConditionsContent.contains("BRIAN")); //Different for External Environment
			//Assert.assertTrue(termasAndConditionsContent.contains("JETER")); //Different for External Environment
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simNumber);
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(phoneNumber));
		}
		return orderId;
	}

	private void carrierResponderSettingsQA60(String phoneNumber) throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();
		PageBase.AdminPage().navigateToSimulator();
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
		//Selecting Use Case from dropdown list.
		PageBase.AdminPage().selectAPIConfig("Verizon");
		PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails", "vwz_accountLookup_LLP_3lines_eligible.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8155491829, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Robot robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitactivation", "success_1_line.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent2 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent2 = xmlContent2.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent2, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
	}
	//endregion QA-60 Refactored Methods Region

	//region QA-5572 Refactored Methods Region
	private void serverDBVerificationsQA5572(String orderId) {
		Utilities.dropdownSelect(PageBase.SQLUtilAdminPage().chooseQueryWrapperDropdown, SelectDropMethod.SELECTBYINDEX, "2");
		PageBase.SQLUtilAdminPage().queryTextbox.sendKeys("select * from ordersignatures where ordid=" + orderId);
		PageBase.SQLUtilAdminPage().submitButton.click();
		Utilities.waitForElementVisible(PageBase.SQLUtilAdminPage().orderSignaturesTable);
		String orderSignaturesTableContent = PageBase.SQLUtilAdminPage().orderSignaturesTable.getText();
		Assert.assertTrue(orderSignaturesTableContent.contains(orderId));
		String encryptedSignature = PageBase.SQLUtilAdminPage().encryptedSignatureRow.getText(); //If it is coming in string i.e. signature is encrypted.
		Assert.assertTrue(encryptedSignature.length() > 0);
	}

	private void shipAdminVerificationsQA5572(String orderId) {
		PageBase.OrderSummaryPage().statusValueLink.sendKeys(Keys.F5);
		Utilities.waitForElementVisible(PageBase.OrderSummaryPage().shippedStatusLink);
		Assert.assertEquals(PageBase.OrderSummaryPage().getOrderStatus(), Constants.SHIPPED);
		String eventLogTableContent = PageBase.OrderSummaryPage().checkForErrorAndLog(orderId);
		Assert.assertTrue(eventLogTableContent.contains(Constants.RECEIPT_SUBMISSION_SUCCEEDED_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.SHIPPED_BUT_NOT_LITERALLY_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.ACTIVATION_SUCCEEDED_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.PARKING_SUCCEEDED_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.MAP_SUCCEEDED_COMMENT));
		Assert.assertTrue(eventLogTableContent.contains(Constants.ACTIVATION_ORDER_VALIDATION_PASSED));
		Assert.assertTrue(PageBase.OrderSummaryPage().orderNumberValueSALink.getText().contains(orderId));
		Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
		Assert.assertTrue(PageBase.OrderSummaryPage().additionalInfoValueText.getText().contains(Constants.EXISTING_ACCOUNT_ORDER));
		Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));
	}

	private String poaFlowQA5572(String phoneNumberLine1, String phoneNumberLine2, String iMEINumber1, String iMEINumber2, String simNumber1, String simNumber2, String orderId, String testtype, String sSN, String accountPassword, String zipCode, String receiptId) throws IOException {
		//Login
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		//Home Page
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(phoneNumberLine1);
		//PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys("1234");
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(sSN);
		//PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys(accountPassword);
		//PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys("98104");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(zipCode);
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		PageBase.UECAddLinesPage().firstAALCheckbox.click();
		PageBase.UECAddLinesPage().secondAALCheckbox.click();
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();
		if(!testtype.equals("internal")) {
			phoneNumberLine2 = ""; //Need to update from external environment.
		}
		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber1);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Edge Page
		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().noContinueWith2YearButton);
		PageBase.VerizonEdgePage().noContinueWith2YearButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber2);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Edge Page
		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().noContinueWith2YearButton);
		PageBase.VerizonEdgePage().noContinueWith2YearButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
		PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		Utilities.dropdownSelect(PageBase.CartPage().secondAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice1 = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel1 = PageBase.CartPage().firstPhoneModelLink.getText();
		String phonePrice2 = PageBase.CartPage().secondPhonePriceText.getText();
		String phoneModel2 = PageBase.CartPage().secondPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		try
		{
			PageBase.VerizonSelectPlanFeaturesPage().firstTextPictureAndMessagingTab.click();
			PageBase.VerizonSelectPlanFeaturesPage().first1000Messages10PerMonthCheckbox.click();
		}
		catch(Exception e){}
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		Utilities.implicitWaitSleep(6000);
		PageBase.SelectProtectionPlanInsurancePage().noInsurancesecondMob.click();
		PageBase.SelectProtectionPlanInsurancePage().noInsurancesecondMob.click();
		Utilities.implicitWaitSleep(6000);
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth, Integer.parseInt(customerDetails.IDExpirationYear));
		PageBase.ServiceProviderVerificationPage().guestAgreesCreditCheck.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		//Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.getText(), phonePrice1);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine2.isDisplayed());
		//Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine2.getText(), phonePrice2);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan1 = CommonFunction.getPlan(PageBase.OrderReviewAndConfirmPage().existingPlanDiv, 17);
		String existingPlan2 = CommonFunction.getPlan(PageBase.OrderReviewAndConfirmPage().existingPlan2Div, 17);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().twoYearsUpgradeLabel.isDisplayed());
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().twoYearsUpgrade2Label.isDisplayed());
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText(), Constants.upgradeFees40);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValue2Text.isDisplayed());
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().upgradeFeeValue2Text.getText(), Constants.upgradeFees40);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().emailTCChkBox.click();
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		if (readConfig("Activation").toLowerCase().contains("true")) {
			PageBase.TermsandConditionsPage().continueTCButton.click();

			//Payment Required Page  //ToDo:Remove this when no insurance bug will fix.
			Utilities.implicitWaitSleep(4000);
			String url = driver.getCurrentUrl();
			if (url.contains("payment")) {
				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
				PageBase.PaymentRequiredPage().sameAddressTab.click();
				PageBase.PaymentRequiredPage().continuePRButton.click();
			}

			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();

			//Ship Admin Order Status Verification
			ShipAdminBaseClass.launchShipAdminInNewTab();
			PageBase.OrderSummaryPage().goToOrderSummaryPage(orderId);
			Assert.assertEquals(PageBase.OrderSummaryPage().getOrderStatus(), Constants.IN_STORE_BILLING);

			//Switching Back to Retail
			Utilities.switchPreviousTab();

			//Print Mobile Scan Sheet Page
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice1);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phone2PriceValuePMSSText.getText(), phonePrice2);
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(receiptId);
			PageBase.PaymentVerificationPage().submitButton.click();

			//Ship Admin Order Status Verification
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			Utilities.switchPreviousTab(); Utilities.switchPreviousTab(); Utilities.switchPreviousTab();
			PageBase.OrderSummaryPage().statusValueLink.sendKeys(Keys.F5);
			Utilities.waitForElementVisible(PageBase.OrderSummaryPage().activationScanRequiredStatusLink);
			Assert.assertEquals(PageBase.OrderSummaryPage().getOrderStatus(), Constants.ACTIVATION_SCAN_REQUIRED);

			//Switching Back to Retail
			Utilities.switchPreviousTab();

			//Device Verification and Activation Page
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber1);
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber2);
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
			PageBase.DeviceVerificationaandActivation().simType.clear();
			PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber1);
			PageBase.DeviceVerificationaandActivation().simType2Textbox.clear();
			PageBase.DeviceVerificationaandActivation().simType2Textbox.sendKeys(simNumber2);
			try  //ToDo:Remove this when no insurance bug will fix.
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");
				CreditCardDetails CreditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
				String cVNNumberS = "" + CreditCard.CVV;
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(cVNNumberS);
			} catch (Exception e) {
			}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Ship Admin Order Status Verification
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			Utilities.switchPreviousTab();  Utilities.switchPreviousTab();  Utilities.switchPreviousTab();
			PageBase.OrderSummaryPage().statusValueLink.sendKeys(Keys.F5);
			Utilities.waitForElementVisible(PageBase.OrderSummaryPage().wCASignatureRequiredStatusLink);
			Assert.assertEquals(PageBase.OrderSummaryPage().getOrderStatus(), Constants.WCA_SIGNATURE_REQUIRED);

			//Getting BAN Value From Ship Admin
			Utilities.dropdownSelect(PageBase.OrderSummaryPage().viewDetailsDropdown, SelectDropMethod.SELECTBYINDEX, "2");
			Utilities.waitForElementVisible(PageBase.OrderSummaryPage().bANValueText);
			String banValueInShipAdmin = PageBase.OrderSummaryPage().bANValueText.getText();

			//Switching Back to Retail
			Utilities.switchPreviousTab();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().emailCheckbox.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().signatureTextbox.isDisplayed());
			String termasAndConditionsContent = PageBase.WirelessCustomerAgreementPage().termsAndConditionsDiv.getText();
			Assert.assertTrue(termasAndConditionsContent.contains("Application ID No.:"));
			Assert.assertTrue(termasAndConditionsContent.contains("Order Date:"));
			Assert.assertTrue(termasAndConditionsContent.contains(CommonFunction.getCurrentDate()));
			Assert.assertTrue(termasAndConditionsContent.contains("Bill Acct. No.:"));
			Assert.assertTrue(termasAndConditionsContent.contains(banValueInShipAdmin));
			Assert.assertTrue(termasAndConditionsContent.contains("Agent Name:"));
			Assert.assertTrue(termasAndConditionsContent.contains(" Target 2766: 789 Mission St, San Francisco, CA 94103, U.S.A"));
			Assert.assertTrue(termasAndConditionsContent.contains("Activation Type:"));
			Assert.assertTrue(termasAndConditionsContent.contains("Upgrade"));
			Assert.assertTrue(termasAndConditionsContent.contains("Customer Information:"));
			Assert.assertTrue(termasAndConditionsContent.contains("BRIAN"));  //First Name
			Assert.assertTrue(termasAndConditionsContent.contains("JETER"));  //Last Name
			Assert.assertTrue(termasAndConditionsContent.contains("9999999999")); //Home Phone Value
			Assert.assertTrue(termasAndConditionsContent.contains("Home Phone:"));
			Assert.assertTrue(termasAndConditionsContent.contains("Line 1 Details"));
			Assert.assertTrue(termasAndConditionsContent.contains("Line 2 Details"));
			//Assert.assertTrue(termasAndConditionsContent.contains(phoneNumberLine1)); //Not matching  //Different way for External Environment
			//Assert.assertTrue(termasAndConditionsContent.contains(phoneNumberLine2)); //Not matching //Different way for External Environment
			Assert.assertTrue(termasAndConditionsContent.contains(existingPlan1));
			//Assert.assertTrue(termasAndConditionsContent.contains(existingPlan2)); //Plan 2 not matching whatever coming in order review and confirm page.
			Assert.assertTrue(termasAndConditionsContent.contains("Plan Access Fee:"));
			//Assert.assertTrue(termasAndConditionsContent.contains("Plan Access Fee: VAlue")); //Need to check from where the value is coming
			Assert.assertTrue(termasAndConditionsContent.contains("Line Access Fee:"));
			//Assert.assertTrue(termasAndConditionsContent.contains("Line Access Fee: Value"));  //Need to check from where it is coming
			Assert.assertTrue(termasAndConditionsContent.contains("Upgrade Fee:"));
			Assert.assertTrue(termasAndConditionsContent.contains("40"));
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().term1Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().term2Label.isDisplayed());
			Assert.assertTrue(termasAndConditionsContent.contains(" 24 months"));
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().eTF1Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().eTF2Label.isDisplayed());
			Assert.assertTrue(termasAndConditionsContent.contains(" $350"));
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().activationDate1Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().activationDate2Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().activationFee1Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().activationFee2Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().upgradeFee1Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().upgradeFee2Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().features1Label.isDisplayed());
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().features2Label.isDisplayed());
			//ToDo:Features Content Validation Pending, Need to explore from where to validate
			Assert.assertTrue(termasAndConditionsContent.contains("Security Deposit (per line):"));
			Assert.assertTrue(termasAndConditionsContent.contains("USD 0"));
			Assert.assertTrue(termasAndConditionsContent.contains("Customer Accepted:"));
			Assert.assertTrue(termasAndConditionsContent.contains("Customer Agreement version:"));
			Assert.assertTrue(termasAndConditionsContent.contains(" 08.13.2014"));
			Assert.assertTrue(termasAndConditionsContent.contains("Taxes & surcharges apply may vary. Federal Universal Service Charge of 16.10% of"));
			Assert.assertTrue(PageBase.WirelessCustomerAgreementPage().signatureTextbox.isDisplayed());
			PageBase.WirelessCustomerAgreementPage().emailCheckbox.click();
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(phoneNumberLine1));
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValue2Text.getText(), CommonFunction.getFormattedPhoneNumber(phoneNumberLine2)); //Different way for External Environment
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber1);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValue2Text.getText(), iMEINumber2);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simNumber1);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValue2Text.getText(), simNumber2);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice1);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValue2Text.getText(), phonePrice2);
		}
		return orderId;
	}

	private void carrierResponderSettingsQA5572(String phoneNumberLine1, String phoneNumberLine2) throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();
		PageBase.AdminPage().navigateToSimulator();
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
		//Selecting Use Case from dropdown list.
		PageBase.AdminPage().selectAPIConfig("Verizon");
		PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails", "vwz_accountLookup_LLP_3lines_eligible.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8155491829, phoneNumberLine1);
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8159547507, phoneNumberLine2);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Robot robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitactivation", "success_2_line.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent2 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent2 = xmlContent2.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumberLine1);
		xmlContent2 = xmlContent2.replace(Constants.DEFAULT_XML_NUMBER_4152648022, phoneNumberLine2);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent2, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
	}
	//endregion QA-5572 Refactored Methods Region

	//region QA-64 Refactored Methods Region
	private String poaFlowQA64(String phoneNumber, String iMEINumber, String simNumber, String orderId, String sSN, String accountPassword, String zipCode, String receiptId) throws IOException {
		//Login
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		//Home Page
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(phoneNumber);
		//PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys("1234");
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(sSN);
		//PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys(accountPassword);
		//PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys("98104");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(zipCode);
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().pleaseSelectAnEligibleDonor1Dropdown);
		Utilities.dropdownSelect(PageBase.UECAddLinesPage().pleaseSelectAnEligibleDonor1Dropdown, SelectDropMethod.SELECTBYINDEX, "1" );
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
		PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		Utilities.implicitWaitSleep(6000);
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth, Integer.parseInt(customerDetails.IDExpirationYear));
		PageBase.ServiceProviderVerificationPage().guestAgreesCreditCheck.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		//Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.getText(), phonePrice);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan1 = CommonFunction.getPlan(PageBase.OrderReviewAndConfirmPage().existingPlanDiv, 17);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().twoYearsUpgradeLabel.isDisplayed());
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText(), Constants.upgradeFees40);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();


		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		if (readConfig("Activation").toLowerCase().contains("true")) {
			PageBase.TermsandConditionsPage().continueTCButton.click();

			//Payment Required Page  //ToDo:Remove this when no insurance bug will fix.
			Utilities.implicitWaitSleep(4000);
			String url = driver.getCurrentUrl();
			if (url.contains("payment")) {
				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
				PageBase.PaymentRequiredPage().sameAddressTab.click();
				PageBase.PaymentRequiredPage().continuePRButton.click();
			}

			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(receiptId);
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation Page
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber);
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
			PageBase.DeviceVerificationaandActivation().simType.clear();
			PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber);
			try  //ToDo:Remove this when no insurance bug will fix.
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");
				CreditCardDetails CreditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
				String cVNNumberS = "" + CreditCard.CVV;
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(cVNNumberS);
			} catch (Exception e) {
			}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);

			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(phoneNumber));
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simNumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
		}
		return orderId;
	}

	private void carrierResponderSettingsQA64(String phoneNumber, String donorPhoneNumber) throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();
		PageBase.AdminPage().navigateToSimulator();
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
		//Selecting Use Case from dropdown list.
		PageBase.AdminPage().selectAPIConfig("Verizon");
		PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails", "custLookup_buddy_upgrade.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_6092340476, phoneNumber);
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_6097315076, donorPhoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Robot robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitactivation", "success_1_line.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent2 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent2 = xmlContent2.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Utilities.copyPaste(xmlContent2, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		PageBase.CarrierResponseXMLPage().selectOptions("current", "submitServiceDetails", "vwz_serviceWrite_line1buddyupgrade_success.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(4000);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
	}
	//endregion QA-64 Refactored Methods Region

	//region QA-5356 Refactored Methods Region
	private String poaFlowQA5356(String phoneNumber, String iMEINumber, String simNumber, String sSN, String accountPassword, String zipCode, String receiptId) throws IOException {
		String orderId = "";
		//Login
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		//Home Page
		PageBase.HomePageRetail().salesAndActivationsLink.click();

		//Choose A Path Page
		Utilities.waitForElementVisible(PageBase.ChoosePathPage().existingCarrier);
		PageBase.ChoosePathPage().existingCarrier.click();

		//Pick Your Path Page
		Utilities.waitForElementVisible(PageBase.PickYourPathPage().upgradeDeviceForOneOrMoreLinesOnExistingAccountRadioButton);
		PageBase.PickYourPathPage().upgradeDeviceForOneOrMoreLinesOnExistingAccountRadioButton.click();
		PageBase.CommonControls().continueButtonDVA.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(phoneNumber);
		//PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys("1234");
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(sSN);
		//PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys(accountPassword);
		//PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys("98104");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(zipCode);
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Assert.assertTrue(PageBase.UECAddLinesPage().eligibleForDF.getText().contains("Eligible for Device Finance\nYes"));
		Assert.assertTrue(PageBase.UECAddLinesPage().eligibleFor2Year.getText().contains("Eligible for 2-Year\nNo"));
		Assert.assertTrue(PageBase.UECAddLinesPage().transferEligible.getText().contains("Transfer Eligible\nNo"));
		PageBase.UECAddLinesPage().clickCheckboxForParticularPhoneNumber(phoneNumber);
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Edge Page
		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().noContinueWith2YearButton);
		PageBase.VerizonEdgePage().noContinueWith2YearButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
		PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		Utilities.implicitWaitSleep(6000);
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Service Provider Verification Page
		Utilities.waitForElementVisible(PageBase.ServiceProviderVerificationPage().firstNameTextbox);
		CustomerDetails customerDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(IdType.DRIVERLICENCE);
		PageBase.ServiceProviderVerificationPage().populatingSPVPage(customerDetails.FirstName, customerDetails.LastName,
				customerDetails.EMail, IdType.DRIVERLICENCE, customerDetails.State, customerDetails.IDNumber,
				customerDetails.IDExpirationMonth, Integer.parseInt(customerDetails.IDExpirationYear));
		//PageBase.ServiceProviderVerificationPage().populatingSPVPage("Faizal", "Khan", "faizal.khan@test.com", IdType.DRIVERLICENCE, "CA", "123456789", "January", 2020);
		PageBase.ServiceProviderVerificationPage().guestAgreesCreditCheck.click();
		PageBase.ServiceProviderVerificationPage().continueSPVButton.click();

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		//Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.getText(), phonePrice);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan = CommonFunction.getPlan(PageBase.OrderReviewAndConfirmPage().existingPlanDiv, 17);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertEquals(upgradeFee, "$0.00");
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		if(readConfig("Activation").toLowerCase().contains("true"))
		{
			PageBase.TermsandConditionsPage().continueTCButton.click();

			//Payment Required Page //ToDo: Remove this when no insurance bug will fix premanently.
			Utilities.implicitWaitSleep(4000);
			String url = driver.getCurrentUrl();
			if (url.contains("payment")) {
				PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
				PageBase.PaymentRequiredPage().sameAddressTab.click();
				PageBase.PaymentRequiredPage().continuePRButton.click();
			}

			//Print Mobile Scan Sheet Page
			Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
			Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
			orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
			PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

			//Payment Verification Page
			Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
			PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(receiptId);
			PageBase.PaymentVerificationPage().submitButton.click();

			//Device Verification and Activation Page
			Utilities.waitForElementVisible(PageBase.DeviceVerificationaandActivation().deviceIMEITextbox);
			PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber);
			PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
			PageBase.DeviceVerificationaandActivation().simType.clear();
			PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber);
			try  //ToDo:Remove this when no insurance bug will fix permanently.
			{
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
				//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");
				CreditCardDetails CreditCard = PageBase.CSVOperations().CreditCardDetails(CardType.VISA);
				String cVNNumberS = "" + CreditCard.CVV;
				PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(cVNNumberS);
			} catch (Exception e) {
			}
			PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

			//Wireless Customer Agreement Page
			Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
			PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
			PageBase.WirelessCustomerAgreementPage().signingWCA(driver);

			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(phoneNumber));
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simNumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(), CommonFunction.getFormattedPhoneNumber(phoneNumber));

			//Sending Details of this order to csv file which will be used in other TCs
			PageBase.CSVOperations();
			CSVOperations.WriteToCSV("QA_5356", orderId, iMEINumber, "", "", customerDetails.FirstName, customerDetails.LastName,
					customerDetails.EMail, receiptId, customerDetails.IDType, customerDetails.State,
					customerDetails.IDNumber, customerDetails.PhNum, customerDetails.Zip, sSN, customerDetails.IDExpirationMonth, customerDetails.IDExpirationYear);
		}
		return orderId;
	}

	private void carrierResponderSettingsQA5356(String phoneNumber, String sSN, String zipCode) throws AWTException, InterruptedException {
		Reporter.log("<h3><U> Carrier Responder</U></h3>", true);

		PageBase.AdminPage().selectWebAPIResponse("Verizon", "CarrierResponder");
		PageBase.AdminPage().selectAPIConfig("Verizon");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().verizonCarrierTab);
		PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();

		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().versionsDropdown);

		PageBase.CarrierResponseXMLPage().selectOptions("current",
				"retrieveCustomerDetails", "vwz_edge_up_eligible_not_payment_needed.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_SSN, sSN);
		xmlContent1 = xmlContent1.replace("07920", zipCode);
		xmlContent1 = xmlContent1.replace("6567778895", phoneNumber);
		xmlContent1 = xmlContent1.replace("281", Utilities.getCredentials("storeId0003"));
		//xmlContent1 = xmlContent1.replace("SEPTEMBER TEST", customerDetails.FirstName +" "+customerDetails.LastName);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		Robot robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		Utilities.implicitWaitSleep(5000);


		PageBase.CarrierResponseXMLPage().selectOptions("current",
				"retrieveExistingCustomerInstallmentDetails", "eligible_no_payment_needed.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		xmlContent1 = "";
		xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace("6567778895", phoneNumber);
		xmlContent1 = xmlContent1.replace("281", Utilities.getCredentials("storeId0003"));
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		Utilities.implicitWaitSleep(5000);

		PageBase.CarrierResponseXMLPage().selectOptions("current",
				"retrieveCreditApplication", "upgrade_approved_noedge.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_OF_LINES, "001");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887, Utilities.getCredentials("storeId0003"));
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_ZipCode, zipCode);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		Utilities.implicitWaitSleep(5000);


		PageBase.CarrierResponseXMLPage().selectOptions("current",
				"retrievePricePlans", "plaid_v_1_line.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
				Utilities.getCredentials("storeId0003"));
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		Utilities.implicitWaitSleep(5000);


		PageBase.CarrierResponseXMLPage().selectOptions("current",
				"submitCreditApplication", "approved.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887,
				Utilities.getCredentials("storeId0003"));
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		Utilities.implicitWaitSleep(5000);


		PageBase.CarrierResponseXMLPage().selectOptions("current",
				"submitReceipt", "default.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_88887,
				Utilities.getCredentials("storeId0003"));
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		Utilities.implicitWaitSleep(5000);


		PageBase.CarrierResponseXMLPage().selectOptions("current",
				"submitServiceDetails", "default.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
				Utilities.getCredentials("storeId0003"));
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		Utilities.implicitWaitSleep(5000);


		PageBase.CarrierResponseXMLPage().selectOptions("current",
				"submitactivation", "success_1_line.xml");
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
		Utilities.implicitWaitSleep(5000);
		xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_4152647954, phoneNumber);
		xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_STOREID_9999,
				Utilities.getCredentials("storeId0003"));
		PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
		robot = new Robot();
		Utilities.copyPaste(xmlContent1, robot);
		Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().saveResponseButton);
		PageBase.CarrierResponseXMLPage().saveResponseButton.click();
		Utilities.implicitWaitSleep(5000);

		Reporter.log("<br>Carrier Responder Changes Done!!!", true);
	}

	private void serverDBVerificationsQA5356(String orderId) {
		Utilities.dropdownSelect(PageBase.SQLUtilAdminPage().chooseQueryWrapperDropdown, SelectDropMethod.SELECTBYINDEX, "2");
		PageBase.SQLUtilAdminPage().queryTextbox.sendKeys("SELECT oei.ordId, oeit.oeitId, oeit.oeitTypeName, oei.oeiValue  FROM serverdb.orderextrainfos oei, productdb.orderextrainfotypes oeit WHERE oei.oeitId = oeit.oeitId AND oeit.oeitId IN (54,55) AND ordId =" + orderId);
		PageBase.SQLUtilAdminPage().submitButton.click();
		Utilities.waitForElementVisible(PageBase.SQLUtilAdminPage().generalTable);
		String generalTableContent = PageBase.SQLUtilAdminPage().generalTable.getText();
		Assert.assertTrue(generalTableContent.contains(orderId));
	}

	private void shipAdminVerificationsQA5356(String orderId) {
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
		Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
		Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));
		Assert.assertTrue(PageBase.OrderSummaryPage().financingProgramValueText.getText().contains("Verizon Edge Up"));
		Assert.assertTrue(PageBase.OrderSummaryPage().tradeInRequiredValueText.getText().contains("Yes"));
		Assert.assertTrue(PageBase.OrderSummaryPage().paymentRequiredValueText.getText().contains("No"));
		Assert.assertTrue(PageBase.OrderSummaryPage().amountPaidVauleText.getText().contains("$0.00"));
	}

	private void backendSettingsQA5356(String phoneNumber) {
		PageBase.AdminPage().selectWebAPIResponse("Verizon", "BackendSimulator");
		PageBase.AdminPage().selectAPIConfig("Verizon");
		PageBase.AdminPage().selectCreaditReadUseCase("APPROVED_EDGEUP"); //For retrieveCreditApplication
		PageBase.AdminPage().retrieveCustomerDetails("VZW_EDGE_UP_ELIGIBLE");

		try{
			if(!PageBase.AdminPage().firstPhoneTextBox.isEnabled());
		}
		catch(Exception e)
		{
			PageBase.AdminPage().addPhoneNumbers.click();
		}

		try{
			if(PageBase.AdminPage().secondPhoneTextBox.isEnabled()){
				PageBase.AdminPage().secondPhoneRemoveTextBox.click();
			}
		}
		catch(Exception e)
		{
			PageBase.AdminPage().addPhoneNumbers.click();
		}

		PageBase.AdminPage().firstPhoneTextBox.clear();
		PageBase.AdminPage().firstPhoneTextBox.sendKeys(phoneNumber);

		PageBase.AdminPage().accountPlanType("Individual");
		PageBase.AdminPage().retrieveExistingCustomerInstallmentsDetails("SUCCESS_NO_PAYMENT");
		PageBase.AdminPage().retrievePricePlan("SUCCESS");
		PageBase.AdminPage().submitCreditApplication("APPROVED");
		PageBase.AdminPage().submitReciept("SUCCESS");
		PageBase.AdminPage().submitServiceDetails("SUCCESS");
		PageBase.AdminPage().submitEdgeUpPayment("SUCCESS");
		PageBase.AdminPage().save();
		Utilities.implicitWaitSleep(1000);
	}
	//endregion QA-5356 Refactored Methods Region

	//region QA-5358 Refactored Methods Region
	private void carrierResponderSetupQA5358(String phoneNumber, String sSN, String zipCode, String testtype) throws AWTException,
	IOException, InterruptedException
	{
		//Verify whether which enviorement to use internal or external.
		testtype = BrowserSettings.readConfig("test-type");
		if(testtype.equals("internal"))
		{
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType, BrowserSettings.readConfig("internalTestType"));

			//Selecting Use Case from dropdown list.
			PageBase.AdminPage().selectAPIConfig(carrierType);

			//Customizing xml files in Carrier Responder
			PageBase.CarrierResponseXMLPage().verizonCarrierTab.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCustomerDetails", "vwz_edge_up_paidoff.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(5000);
			String xmlContent1 = PageBase.CarrierResponseXMLPage().xmlTextArea.getAttribute("value");
			xmlContent1 = xmlContent1.replace(Constants.DEFAULT_XML_NUMBER_8155491829, "9475831856");
			PageBase.CarrierResponseXMLPage().xmlTextArea.clear();
			Robot robot = new Robot();
			Utilities.copyPaste(xmlContent1, robot);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveExistingCustomerInstallmentDetails",
					"eligible_no_payment_needed.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveCreditApplication",
					"upgrade_approved_edge.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "retrievePricePlans", "plaid_v_1_line.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "submitCreditApplication", "approved.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "submitReceipt", "default.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "submitServiceDetails", "default.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "submitactivation", "success_1_line.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "generateInstallmentContract", "success.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveInstallmentDetailsForDevice", "eligible.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "retrieveInstallmentContractApprovalStatus", "success.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			PageBase.CarrierResponseXMLPage().selectOptions("current", "submitSigpadCaptureForInstallmentAgreement", "success.xml");
			Utilities.waitForElementVisible(PageBase.CarrierResponseXMLPage().xmlTextArea);
			Utilities.implicitWaitSleep(4000);
			PageBase.CarrierResponseXMLPage().saveResponseButton.click();
			Utilities.implicitWaitSleep(5000);
		}
		else
		{
			AdminBaseClass adminBaseClass = new AdminBaseClass();
			adminBaseClass.launchAdminInNewTab();

			PageBase.AdminPage().navigateToSimulator();
			PageBase.AdminPage().selectWebAPIResponse(carrierType,"External");
		}

	}

	private void poaCompleteFlowQA5358(String MTNNumber, String SSN, String zipCode, String iMEINumber1, boolean stopActivation,
			String testtype, String simType1) throws AWTException,
			IOException, InterruptedException
	{
		//Login
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"), Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));

		//Home Page
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(MTNNumber);
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(String.valueOf(SSN));
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(String.valueOf(zipCode));
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		Assert.assertTrue(PageBase.UECAddLinesPage().eligibleForDF.getText().contains("Eligible for Device Finance\nYes"));
		Assert.assertTrue(PageBase.UECAddLinesPage().eligibleFor2Year.getText().contains("Eligible for 2-Year\nYes"));
		Assert.assertTrue(PageBase.UECAddLinesPage().transferEligible.getText().contains("Transfer Eligible\nYes"));
		PageBase.UECAddLinesPage().clickCheckboxForParticularPhoneNumber(MTNNumber);
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber1);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		//Verizon Edge Page
		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().YesCheckEligibilityButton);
		PageBase.VerizonEdgePage().YesCheckEligibilityButton.click();

		//Filling information in Carrier Credit Check Page.
		Utilities.waitForElementVisible(PageBase.CarrierCreditCheckPage().populateForm);
		CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails();
		PageBase.CarrierCreditCheckPage().populatingCarrierCreditCheckPage(cccDetails);
		PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();
		PageBase.CommonControls().continueButton.click();
		PageBase.CommonControls().continueButton.click();

		// Installment Page
		Utilities.waitForElementVisible(PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton);
		PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton.click();
		PageBase.CommonControls().continueCommonButton.click();

		//Verizon Shop Plans Page
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().VerizonMoreEverything);
		PageBase.VerizonShopPlansPage().VerizonMoreEverything.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().phonePriceLine1.isDisplayed());
		Assert.assertEquals(PageBase.OrderReviewAndConfirmPage().phoneMonthlyFee.getText(), phonePrice);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().existingPlanDiv.isDisplayed());
		String existingPlan = PageBase.OrderReviewAndConfirmPage().existingPlanDiv.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		//Payment Required Page
		Utilities.implicitWaitSleep(8000);
		String url = driver.getCurrentUrl();
		if(url.contains("payment"))
		{
			PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA); //ToDo: Need to read from data sheet.
			Utilities.ClickElement(PageBase.PaymentRequiredPage().sameAddressTab);
			Utilities.ClickElement(PageBase.PaymentRequiredPage().continuePRButton);
		}

		//Print Mobile Scan Sheet Page
		Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
		Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phoneModelText.getText(), phoneModel);
		Assert.assertEquals(PageBase.PrintMobileScanSheetPage().phonePriceValuePMSSText.getText(), phonePrice);
		orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
		PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

		//Payment Verification Page
		Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
		PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys(PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(FileName.ReceiptId));
		PageBase.PaymentVerificationPage().submitButton.click();

		//Device Verification and Activation Page
		PageBase.DeviceVerificationaandActivation().deviceVerificationActiavtionFor1Device(iMEINumber1, simType1);
		try
		{
			PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.clear();
			//PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys(String.valueOf(creditCardDetails.CVV));
		}
		catch(Exception e){}
		PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

		//Wireless Customer Agreement Page
		Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
		PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();
		PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
		if (stopActivation == false)
		{
			PageBase.WirelessCustomerAgreementPage().continueWCAButton.click();

			//Order Activation and Complete Page
			Utilities.waitForElementVisible(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText, 120);
			Assert.assertTrue(PageBase.OrderActivationCompletePage().orderAndActivationCompleteText.isDisplayed());
			Assert.assertEquals(PageBase.OrderActivationCompletePage().phoneNumberValueText.getText(),
					CommonFunction.getFormattedPhoneNumber("9475831856"));
			Assert.assertEquals(PageBase.OrderActivationCompletePage().iMEINumberValueText.getText(), iMEINumber1);
			//Assert.assertEquals(PageBase.OrderActivationCompletePage().simNumberValueText.getText(), simNumber);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().priceValueText.getText(), phonePrice);
			Assert.assertEquals(PageBase.OrderActivationCompletePage().subtotalValueText.getText(), totalDue);

			//Ship Admin
			ShipAdminBaseClass.launchShipAdminInNewTab();
			PageBase.OrderSummaryPage().goToOrderSummaryPage(orderId);
			PageBase.OrderSummaryPage().verifyAppleCareInsuranceStatus();
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
			Assert.assertTrue(PageBase.OrderSummaryPage().orderBuyTypeValueText.getText().contains(Constants.HANDSET_UPGRADE));
			//Assert.assertTrue(PageBase.OrderSummaryPage().additionalInfoValueText.getText().contains(Constants.EXISTING_ACCOUNT_HOLDER));
			Assert.assertTrue(PageBase.OrderSummaryPage().partnerValueText.getText().contains(Constants.VERIZON_WIRELESS_XML));

			//Inventory Management
			PageBase.InventoryManagementPage().launchInventoryInNewTab();
			PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber1, Constants.SOLD);
		}
	}
	//endregion

	private void poaCompleteFlow_QA_5357() throws IOException, InterruptedException, AWTException {
		PageBase.LoginPageRetail().poaLogin(Utilities.getCredentials("tuserUN"),
				Utilities.getCredentials("tuserPwd"), Utilities.getCredentials("storeId0003"));
		String MTNNumber = "9889080097";
		String ssn = "1234";
		String zipCode = "94109";
		String iMEINumber1 = "99090181788776";
        simNumber = "20202020202020202020";
		// Click the upgradeEligibility
		PageBase.HomePageRetail().upgradeEligibilityCheckerLink.click();

		//UEC Verification Page
		Utilities.waitForElementVisible(PageBase.UECVerificationPage().verizonTab);
		PageBase.UECVerificationPage().verizonTab.click();
		PageBase.UECVerificationPage().phoneNumberVerizonTextbox.sendKeys(MTNNumber);
		PageBase.UECVerificationPage().last4OfSSNVerizonTextbox.sendKeys(String.valueOf(ssn));
		PageBase.UECVerificationPage().accountPasswordVerizonTextbox.sendKeys("");
		PageBase.UECVerificationPage().accountZipcodeVerizonTextbox.sendKeys(String.valueOf(zipCode));
		PageBase.UECVerificationPage().continueVerizonButton.click();

		//UEC Add Lines Page
		Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
		PageBase.UECAddLinesPage().firstAALCheckbox.click();
		PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

		//Device Scan Page
		Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
		PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber1);
		PageBase.DeviceScanPage().submitDeviceButton.click();

		Utilities.waitForElementVisible(PageBase.VerizonEdgePage().YesCheckEligibilityButton);
		PageBase.VerizonEdgePage().YesCheckEligibilityButton.click();

		//Filling information in Carrier Credit Check Page.
		Utilities.waitForElementVisible(PageBase.CarrierCreditCheckPage().populateForm);
		CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails();
		PageBase.CarrierCreditCheckPage().populatingCarrierCreditCheckPage(cccDetails);
		PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();
		PageBase.CommonControls().continueButton.click();

		try
		{
			PageBase.CommonControls().continueButton.click();
		}
		catch (Exception ex)
		{}

		// Installment Page
		Utilities.waitForElementVisible(PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton);
		PageBase.InstallmentPage().edgeMonthlyInstallmentRadiobutton.click();
		PageBase.CommonControls().continueCommonButton.click();

		//Keep existing Plan
		Utilities.waitForElementVisible(PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton);
		PageBase.VerizonShopPlansPage().KeepMyExistingVerizonWirelessLegacyAddButton.click();

		//Cart Page
		Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
		Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, SelectDropMethod.SELECTBYINDEX, "1");
		String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
		String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
		PageBase.CartPage().continueCartButton.click();

		//Verizon Select Plan Features Page
		Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
		//PageBase.VerizonSelectPlanFeaturesPage().basicPhoneDiscount0Checkbox.click();
		PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

		//Select Protection Plan Insurance Page
		Utilities.waitForElementVisible(PageBase.SelectProtectionPlanInsurancePage().eSecuritelInsurance);
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		PageBase.SelectProtectionPlanInsurancePage().NoInsurance.click();
		Utilities.implicitWaitSleep(6000);
		PageBase.SelectProtectionPlanInsurancePage().guestReview.click();
		PageBase.CommonControls().continueButton.click();

		//Order Review and Confirm Page
		Utilities.waitForElementVisible(PageBase.CommonControls().continueCommonButton);
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.isDisplayed());
		String upgradeFee = PageBase.OrderReviewAndConfirmPage().upgradeFeeValueText.getText();
		Assert.assertTrue(PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.isDisplayed());
		String totalDue = PageBase.OrderReviewAndConfirmPage().totalDueTodayValue.getText();
		PageBase.CommonControls().continueCommonButton.click();

		//Terms & Conditions Page
		Utilities.waitForElementVisible(PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox);
		PageBase.TermsandConditionsPage().acceptsTargetTCCheckbox.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		//Payment Required Page
		Utilities.implicitWaitSleep(8000);
		String url = driver.getCurrentUrl();
		if (url.contains("payment")) {
			PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(CardType.VISA);
			PageBase.PaymentRequiredPage().sameAddressTab.click();
			PageBase.PaymentRequiredPage().continuePRButton.click();
			Reporter.log("<br> Entered Credit Card Details");
		}

		//Print Mobile Scan Sheet Page
		Utilities.waitForElementVisible(PageBase.PrintMobileScanSheetPage().continueFirstMSSButton);
		orderId = PageBase.PrintMobileScanSheetPage().orderNumberValuePMSSText.getText();
		PageBase.PrintMobileScanSheetPage().continueFirstMSSButton.click();

		//Payment Verification Page
		Utilities.waitForElementVisible(PageBase.PaymentVerificationPage().textboxTargetReceiptID);
		PageBase.PaymentVerificationPage().textboxTargetReceiptID.sendKeys("132710003003669460");
		PageBase.PaymentVerificationPage().submitButton.click();

		PageBase.DeviceVerificationaandActivation().deviceIMEITextbox.sendKeys(iMEINumber1); //"2113114115116110"
		PageBase.DeviceVerificationaandActivation().submitDVAButton.click();
		PageBase.DeviceVerificationaandActivation().simType.sendKeys(simNumber);//21212121212121212121
		try {
			PageBase.DeviceVerificationaandActivation().cvnNumberDVATextbox.sendKeys("123");  // ToDo: Read from data sheet.
		} catch (Exception e) {
		}
		PageBase.DeviceVerificationaandActivation().continueButtonDVA.click();

		// Click on Accept Terms and Conditions
		Utilities.waitForElementVisible(PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox, 120);
		PageBase.WirelessCustomerAgreementPage().acceptsWCACheckbox.click();

		//Save Signature
		PageBase.WirelessCustomerAgreementPage().signingWCA(driver);
		PageBase.TermsandConditionsPage().saveSignatureButton.click();
		PageBase.TermsandConditionsPage().continueTCButton.click();

		//Verify Order generated on Receipt Page
		Utilities.waitForElementVisible(PageBase.OrderReceiptPage().orderCompletionText);
		PageBase.OrderReceiptPage().verifyOrderCompletionPage();
		PageBase.CSVOperations().UpdateIMEIStatusToUsed(iMEINumber1, FileName.SamsungGalaxyS4_16GBWhite);

		//Print the Details
		PageBase.DeviceFinancingInstallmentContractPage().PrintDeviceFinancingDetails(driver);
		//Verify that the Order is Successfully Created
		Utilities.waitForElementVisible(PageBase.OrderReceiptPage().orderCompletionText);
		PageBase.OrderReceiptPage().verifyOrderCompletionPage();
		Assert.assertTrue(PageBase.OrderActivationCompletePage().ActivationComplete.isDisplayed());

		PageBase.InventoryManagementPage().launchInventoryInNewTab();
		PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber1, "Sold");
	}

	private void poaExchangeDevice_QA_5357()
	{
		PageBase.HomePageRetail().guestLookupTab.click();
		PageBase.CustomerLookupPage().viewGuestOrders.click();
		PageBase.CustomerLookupPage().continueButton.click();

		Utilities.waitForElementVisible(PageBase.GuestVerificationPage().idTypeDropdown);
		PageBase.GuestVerificationPage().populateGuestVerificationDetails(GuestVerificationPage.IdType.DRIVERLICENCE, "",
				"", "", "");
		driver.navigate().to(readConfig("CustomerVerification") + "100050119");

		Utilities.waitForElementVisible(PageBase.ReturnScanDevicePage().orderID);
		orderId = PageBase.ReturnScanDevicePage().orderID.getText();
		PageBase.ReturnScanDevicePage().esnIemeidTextbox.sendKeys(imei1);
		PageBase.CustomerLookupPage().continueButton.click();

		//Select the Precondictions
		Utilities.waitForElementVisible(PageBase.ReturnOrExhangePreConditions().deviceAccessoryRadioButton);
		PageBase.ReturnOrExhangePreConditions().SelectPreconditions();
		PageBase.ReturnOrExhangePreConditions().continueREVButton.click();

		//Select more Details
		Utilities.waitForElementVisible(PageBase.ReturningProcessPage().acceptReturnExchangeRadioButton);
		PageBase.ReturningProcessPage().acceptReturnExchangeRadioButton.click();

		Utilities.dropdownSelect(PageBase.ReturnOrExchangeVerificationPage().returnReasons,
				SelectDropMethod.SELECTBYINDEX, "1");
		PageBase.CustomerLookupPage().continueButton.click();

		if(driver.getCurrentUrl().contains("passwordcapture"))
		{
			PageBase.VerizonAccountPassword().password.sendKeys("HELLO");
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
	}

	//endregion Helper and Refactored Methods
}
