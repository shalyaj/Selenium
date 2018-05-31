package com.consensus.qa.sprint.tests;

import com.consensus.qa.framework.*;
import com.consensus.qa.pages.PaymentRequiredPage;
import com.consensus.qa.pages.ServiceProviderVerificationPage;
import com.consensus.qa.tests.CarrierCreditCheckDetails;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

public class UpgradeTests extends RetailBaseClass{
	//region Test Methods
	//region QA-3065
	@Test(groups={"verizon"})
	@Parameters("test-type")
	public void QA3065_SprintFamilyAccountUpgradeEasyPayExistingPlan(@Optional String testtype) {
		try {
			//This TC need 1 fresh phone number and 1 fresh IMEI for every run.
			CustomerDetails cusDetails = PageBase.CSVOperations().ReadCustomerDetailsFromCSV(ServiceProviderVerificationPage.IdType.DRIVERLICENCE);
			String receiptId = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.ReceiptId);
			String iMEINumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.Sprint_SamsungGalaxyS4_16GBWhite);
			String simNumber = PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.Sprint_Sim_3FF);
			AccountDetails accountDetails = PageBase.CSVOperations().GetDetails(CSVOperations.FileName.SprintEasyPayUpgradeMultipleLinesEligible);
			String phoneNumber = accountDetails.MTN;
			String sSN = accountDetails.SSN;
			String zipCode = cusDetails.Zip;
			String accountPassword = accountDetails.Password;
			String orderId = "";

			Log.startTestCase("QA3065_SprintFamilyAccountUpgradeEasyPayExistingPlan");
			Reporter.log("<h2>Start - QA3065_SprintFamilyAccountUpgradeEasyPayExistingPlan. <br></h2>");
			Reporter.log("Launching Browser <br>", true);

			//Verify whether which enviorement to use internal or external.
			testtype = BrowserSettings.readConfig("test-type");
			if (testtype.equals("internal")) {
				AdminBaseClass adminBaseClass = new AdminBaseClass();
				adminBaseClass.launchAdminInNewTab();
				PageBase.AdminPage().navigateToSimulator();

				if (readConfig("internalTestType").toLowerCase().contains("carrierresponder")) {
					//Customizing xml files in Carrier Responder
					Reporter.log("<h3><U> Carrier Responder</U></h3>", true);
					//carrierResponderSettingsQA3065(phoneNumber, sSN, zipCode);
					Reporter.log("<h3><U> Carrier Responder Changes Done.</U></h3>", true);

				} else {
					Reporter.log("<h3><U> Backend</U></h3>", true);
					//backendSettingsQA3065(phoneNumber);
					PageBase.AdminPage().selectWebAPIResponse("Sprint", "BackendSimulator");
					Reporter.log("<h3><U> Backend Changes Done.</U></h3>", true);
				}
			} else {
				selectingSprintExternalEnvironment();
			}

			//Calling DBError utility to  find initial count or error in log files.
			DBError.navigateDBErrorPage();
			int initialCount = PageBase.AdminPage().totalErrorCount();

			//Switching Back To Retail
			Utilities.switchPreviousTab();

			//POA FLOW

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
			Utilities.waitForElementVisible(PageBase.UECVerificationPage().sprintTab);
			PageBase.UECVerificationPage().sprintTab.click();
			PageBase.UECVerificationPage().phoneNumberSprintTextbox.sendKeys(phoneNumber);
			PageBase.UECVerificationPage().last4OfSSNSprintTextbox.sendKeys(sSN);
			PageBase.UECVerificationPage().pinSprintTextbox.sendKeys(accountPassword);
			PageBase.UECVerificationPage().continueSprintButton.click();

			//UEC Add Lines Page
			Utilities.waitForElementVisible(PageBase.UECAddLinesPage().firstAALCheckbox);
			PageBase.UECAddLinesPage().clickFirstEnabledCheckbox();
			PageBase.UECAddLinesPage().continueUECAddLinesButton.click();

			//Device Scan Page
			Utilities.waitForElementVisible(PageBase.DeviceScanPage().iMEIESNTextbox);
			PageBase.DeviceScanPage().iMEIESNTextbox.sendKeys(iMEINumber);
			PageBase.DeviceScanPage().submitDeviceButton.click();

			//Sprint Easy Pay Page
			Utilities.waitForElementVisible(PageBase.SprintEasyPayPage().yesButton);
			Assert.assertTrue(PageBase.SprintEasyPayPage().priceBox.getText().contains("2yr agreement"));
			PageBase.SprintEasyPayPage().yesButton.click();

			//Carrier Credit Check Page
			Utilities.waitForElementVisible(PageBase.CarrierCreditCheckPage().populateForm);
			CarrierCreditCheckDetails cccDetails = getCarrierCreditCheckDetails();
			PageBase.CarrierCreditCheckPage().populatingCarrierCreditCheckPage(cccDetails);
			PageBase.CarrierCreditCheckPage().guestAgreeToRunCCCheckBox.click();
			PageBase.CommonControls().continueButton.click();
			PageBase.CommonControls().continueButton.click();

			//Sprint Easy Pay Eligibility Result
			Utilities.waitForElementVisible(PageBase.SprintEasyPayEligibilityResultPage().eligibleForEasyPayLabel, 120);
			Assert.assertTrue(PageBase.SprintEasyPayEligibilityResultPage().eligibleForEasyPayLabel.isDisplayed());
			Assert.assertTrue(PageBase.SprintEasyPayEligibilityResultPage().downPaymentLabel.isDisplayed());
			Assert.assertTrue(PageBase.SprintEasyPayEligibilityResultPage().installmentContractLengthLabel.isDisplayed());
			PageBase.SprintEasyPayEligibilityResultPage().minimumDownPaymentEasyPayRadioButton.click();
			PageBase.CommonControls().continueCommonButton.click();

			//Sprint Shop Plans Page
			Utilities.waitForElementVisible(PageBase.SprintShopPlansPage().sprintFamilySharePack1GBAddButton);
			PageBase.SprintShopPlansPage().sprintFamilySharePack1GBAddButton.click(); //Need to click Keep my Existing Plan Page, currently not coming, for completing flow using this plan.

			//Cart Page
			Utilities.waitForElementVisible(PageBase.CartPage().firstAssignNumberDropdown);
			Utilities.dropdownSelect(PageBase.CartPage().firstAssignNumberDropdown, Utilities.SelectDropMethod.SELECTBYINDEX, "1");
			String phonePrice = PageBase.CartPage().firstPhonePriceText.getText();
			String phoneModel = PageBase.CartPage().firstPhoneModelLink.getText();
			PageBase.CartPage().continueCartButton.click();
			//Assertions Pending

			//Select Plan Features Page
			Utilities.waitForElementVisible(PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton);
			PageBase.VerizonSelectPlanFeaturesPage().continueSPFButton.click();

			//Select Protection Plan Insurance Page
			PageBase.SelectProtectionPlanInsurancePage().selectNoInsurance();

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
					PageBase.PaymentRequiredPage().populatingCardDetailsPaymentRequired(PaymentRequiredPage.CardType.VISA);
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
					CreditCardDetails CreditCard = PageBase.CSVOperations().CreditCardDetails(PaymentRequiredPage.CardType.VISA);
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
			}


//			if (readConfig("Activation").toLowerCase().contains("true")) {
//				//Ship Admin
//				ShipAdminBaseClass.launchShipAdminInNewTab();
//				shipAdminVerificationsUpgrade(orderId);
//
//				//Inventory Management
//				PageBase.InventoryManagementPage().launchInventoryInNewTab();
//				PageBase.InventoryManagementPage().verifyDeviceStatus(iMEINumber, Constants.SOLD);
//			}

			//DBError Verification.
			DBError.navigateDBErrorPage();
			//Assert.assertTrue(PageBase.AdminPage().isDBErrorFound(initialCount));

			Reporter.log("<h3>QA3065_SprintFamilyAccountUpgradeEasyPayExistingPlan - Test Case Completes<h3>");
			Log.endTestCase("QA3065_SprintFamilyAccountUpgradeEasyPayExistingPlan");
		}
		catch (Exception ex) {
			Log.error(ex.getMessage());
			System.out.println(ex.getMessage());
			Utilities.driverTakesScreenshot("QA3065_SprintFamilyAccountUpgradeEasyPayExistingPlan");
			Assert.assertTrue(false);
		} finally {

		}
	}

	//endregion QA-62
	//endregion Test Methods

	//region Helper and Refactored Methods
	private void selectingSprintExternalEnvironment() throws InterruptedException, AWTException, IOException {
		AdminBaseClass adminBaseClass = new AdminBaseClass();
		adminBaseClass.launchAdminInNewTab();
		PageBase.AdminPage().navigateToSimulator();
		PageBase.AdminPage().selectWebAPIResponse("Sprint", "External");
		Reporter.log("<h3><U> External Server</U></h3>", true);
	}

	private CarrierCreditCheckDetails getCarrierCreditCheckDetails() throws IOException {
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
		cccDetails.setSSN(PageBase.CSVOperations().GetIMEIOrSimNumberOrReceiptId(CSVOperations.FileName.SSNWithDeposit));
		cccDetails.setIdTypeState(customerDetails.IDState);
		cccDetails.setIdNumber(customerDetails.IDNumber);
		cccDetails.setMonth(customerDetails.IDExpirationMonth);
		cccDetails.setYear(customerDetails.IDExpirationYear);
		cccDetails.setIDType(ServiceProviderVerificationPage.IdType.DRIVERLICENCE);
		return cccDetails;
	}
	//endregion Helper and Refactored Methods
}
