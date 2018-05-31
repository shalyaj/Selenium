package com.consensus.qa.framework;

public class ControlLocators {

	/* region Login Page Retail Elements */
	public static final String USERNAME_TEXTBOX = "Login";
	public static final String PASSWORD_TEXTBOX = "password";
	public static final String STORE_ID_TEXTBOX = "storeId";
	public static final String STORE_ID_BUTTON= "//fieldset[@id='storeRadios']/div/label/span/span[2]";
	public static final String LOGIN_BUTTON = "//span[contains(text(),'login')]";
	/* end region Login Page Retail Elements */

	/* region Home Page Retail Elements */
	public static final String NEW_GUEST_BUTTON = "//span[contains(text(),'New Guest')]";
	public static final String CART_ICON = "";
	public static final String SETTINGS_ICON = "";
	public static final String SALES_AND_ACTIVATIONS_LINK = "//p[contains(text(),'Sales & Activations')]";
	public static final String UPGRADE_ELIGIBILITY_CHECKER_LINK = "//p[contains(text(),'Upgrade Eligibility Checker')]";
	public static final String CARRIER_CREDIT_CHECK_LINK = "";
	public static final String ELECTRONIC_TRADEIN_LINK = "";	
	public static final String APPLE_CARE_LINK = "";
	public static final String ACCOUNT_LOOK_UP_LINK = "";
	public static final String PREPAID_ACTIVATION_LINK = "";
	public static final String HOME_TAB = "";
	public static final String INVENTORY_MANAGEMENT_TAB = "";
	public static final String GUEST_LOOKUP_TAB = "customerlookupico";
	public static final String SAVE_TAB = "";
	public static final String LOCK_TAB = "";
	/* end region Home Page Retail Elements */

	/* region Choose a Path */
	public static final String NEW_ACTIVATION = "//h3[contains(text(),'New Activation')]";
	public static final String EXISTING_CAREER = "//h3[contains(text(),'Existing Carrier')]";
	public static final String BROWSE_PHONES = "//h3[contains(text(),'Browse Phones')]";
	public static final String BROWSE_PLANS = "//h3[contains(text(),'Browse Plans')]";
	public static final String PLAN_DESCRIPTION_TEXT = "(//h1[contains(text(),'More')])[1]";
	/* end region Choose a Path */

	/* region Upgrade Eligibility Checker Page Elements */
	public static final String VERIZON_TAB = "//img[@src='/img/retail/corps/plLgs660.png ']";
	public static final String PHONE_NUMBER_VERIZON_TEXTBOX = "(//input[@id='number'])[3]";
	public static final String LAST_4_OF_SSN_VERIZON_TEXTBOX = "(//input[@id='ssn'])[3]";
	public static final String ACCOUNT_PASSWORD_VERIZON_TEXTBOX = "(//input[@id='apssword'])[2]";
	public static final String ACCOUNT_ZIPCODE_VERIZON_TEXTBOX = "(//input[@id='azippcode'])[2]";
	public static final String CONTINUE_VERIZON_BUTTON = "(//button[@name='eligibility'])[3]";
	public  static  final String SPRINT_TAB = "//img[@src='/img/retail/corps/plLgs545.png ']";
	public  static  final String PHONE_NUMBER_SPRINT_TEXTBOX = "(//input[@id='number'])[2]";
	public  static  final String LAST_4_OF_SSN_SPRINT_TEXTBOX = "(//input[@id='ssn'])[2]";
	public  static  final String PIN_SPRINT_TEXTBOX = "//input[@id='pin']";
	public  static  final String CONTINUE_SPRINT_BUTTON = "(//button[@name='eligibility'])[2]";
	/* end region Upgrade Eligibility Checker Page Elements */

	/* region UEC Add Lines Page Elements */
	public static final String FIRST_AAL_CHECKBOX = "(//span[@class='ui-icon ui-icon-checkbox-off ui-icon-shadow'])[1]";
	public static final String SECOND_AAL_CHECKBOX = "(//li[@class='eligibilityLineItem']/ul[@class='upgradeelig-select clearfix']/li[@class='upgradeelig-block2']/div[@class='upgradeelig-check']/div[@class='ui-checkbox']/input)[2]";
	public static final String THIRD_AAL_CHECKBOX = "";
	public static final String FOURTH_AAL_CHECKBOX = "(//span[@class='ui-icon ui-icon-checkbox-off ui-icon-shadow'])[4]";
	public static final String FIFTH_AAL_CHECKBOX = "";
	public static final String CONTINUE_UEC_ADD_LINES_BUTTON = "//button[@value='proceedWithUpgrade']";
	public static final String ELIGIBLE_FOR_DF="(//section[contains(@class, 'upgradeContent-wrapper clearfix')])[1]";
	public static final String ELIGIBLE_FOR_2YEAR ="(//section[contains(@class, 'upgradeContent-wrapper sublineTop clearfix')])[1]";
	public static final String TRANSFER_ELIGIBLE = "(//section[contains(@class, 'upgradeContent-wrapper last clearfix')])[2]";
	public static final String PLEASE_SELECT_AN_ELIGIBLE_DONOR_DROPDOWN = "select-reason-1";
	public static final String ALL_NUMBERS_ELEMENTS = ".//ul[(@class='upgradeelig-list')]/li";
	public static final String FIRST_LINE_STATUS = ".//*[@id='retailPage']/section/form/section/ul/li[1]/ul/li[2]/div[1]/div/label/span/span[2]";
	//div[contains(text(), 'Eligible for Device Finance')]
	/* end region UEC Add Lines Page Elements */

	/* region Device Scan Page Elements */
	public static final String IMEIESN_TEXTBOX = "//input[@name='prodIdents[addDeviceId]']";
	public static final String SUBMIT_DEVICE_BUTTON = "deviceSubmit";
	public static final String ATT_GO_LINK = "//span[contains(text(),'AT&T GO')]/parent::a";
	public static final String SPRINT_GO_LINK = "//span[contains(text(),'Sprint GO')]/parent::a";
	public static final String TMOBILE_GO_LINK = "//span[contains(text(),'T-Mobile GO')]/parent::a";
	public static final String VERIZON_GO_LINK = "//span[contains(text(),'Verizon GO')]/parent::a";
	public static final String VOICE_ONLY_PHONE_GO = "//span[contains(text(),'Voice Only Phone GO')]/parent::a";
	public static final String IPHONE_GO_LINK = "//span[contains(text(),'iPhone GO')]/parent::a";
	public static final String VERIZON_MIFI_GO_LINK = "//span[contains(text(),'Verizon MIFI GO')]/parent::a";
	public static final String DEVICE_NOT_FOUND = "//div[contains(text(),'Device Not Found')]";
	public static final String CONTINUE_DS_BUTTON = "//span[contains(text(),'continue')]";
	/* end region Device Scan Page Elements */

	//Region -Carrier Credit Check
	public static final String CCC_POPULATE_FORM = "//span[contains(text(),'Populate Form')]";
	public static final String POPULATE_SUBMIT_FORM = "//span[contains(text(),'Populate & Submit')]";
	public static final String SKIP = "//span[contains(text(),'skip')]";
	public static final String FIRST_NAME = "Ecom_BillTo_Postal_Name_First";
	public static final String MIDDLE_NAME = "Ecom_BillTo_Postal_Name_Middle";
	public static final String LAST_NAME = "Ecom_BillTo_Postal_Name_Last";
	public static final String ADDRESS1 = "Ecom_BillTo_Postal_Street_Line1";
	public static final String ADDRESS2 = "Ecom_BillTo_Postal_Street_Line2";
	public static final String CITY = "Ecom_BillTo_Postal_City";
	public static final String STATE_DD = "Ecom_BillTo_Postal_StateProv";
	public static final String ZIP = "Ecom_BillTo_Postal_PostalCode";
	public static final String HOME_PHONE = "Ecom_BillTo_Telecom_Phone_Number";
	public static final String EMAIL = "Ecom_BillTo_Online_Email";
	public static final String MONTH_DD = "birthMonth";
	public static final String DAY_DD = "birthDay";
	public static final String YEAR_DD = "birthYear";
	public static final String SSN = "Social_Security";
	public static final String ID_TYPE_DD = "Identification_Type";
	public static final String ID_STATE_DD = "Identification_State";
	public static final String ID_NUMBER = "Identification_Number";
	public static final String ID_EXPIRATION_MONTH_DD = "Identification_Expiration_Month";
	public static final String ID_EXPIRATION_YEAR_DD = "Identification_Expiration_Year";
	public static final String GUEST_AGREE_TO_RUN_CCC_CHECKBOX = "radio-choice-1";
	//EndRegion

	//Region-Credit Check Verification Result
	public static final String CREDIT_CHECK_PASS_CHKBOX = "checkbox-mini-1";
	public static final String DEPOSIT_CHKBOX = "checkbox-deposit-1";
	public static final String AGREE_DESPOIT_$100 = "//span[contains(text(),'I understand that a $100.00 deposit is required if I activate (1) line with Verizon Wireless')]";
	public static final String CONTINUE_DEPOSIT_PAGE = "//button[contains(text(),'continue')]";
	//EndRegion

	/* region Verizon Edge Page Elements */
	public static final String CHECKBOX_Decline_VERIZON_ORDER = "//*[@id='retailPage']/section/div[2]/div/label/span/span[2]";
	public static final String DECLINE_BUTTON = "(//span[contains(text(),'decline')])[2]"; 
	public static final String NO_CONTINUE_WITH_2_YEAR_BUTTON = "cancelButton";
	public static final String YES_CHECK_ELIGIBILITY = "eligibilityCheckButton";
	public static final String ESNVALUE_TEXT = "";
	public static final String DECLINE_VERIZON_EDGE = "//span[contains(text(),'Check to decline Verizon Edge for entire order')]";
	public static final String DOWNPAYMENT_TEXTBOX = "custDownpayment";
	public static final String MIN_DOWNPAYMENT = "//label[@for='radio-choice-8']";
	public static final String INSTALMENT_SUBMIT_BUTTON = "checkInstallment";
	public static final String DOWN_PAYMENT_AMOUNT = "//*[@id='dipslayDownpayment']";
	public static final String DISCLAIMER_MESSAGE = "//*[@class='cartFP']";
	public static final String MONTHLY_INSTALLMENT_AMT = "(//span[@class='green installment'])[1]";
	public static final String DEVICE_PRICE_WITH_FINANCE = "(//span[@class='bold green'])[2]";
	public static  final String DO_NOT_PROMPT_FOR_ENTIRE_ORDER = "//label[@for='checkbox-1']";
	/* end region Verizon Edge Page Elements */

	/* region Verizon Shop Plans Elements */
	public static final String VERIZON_MORE_EVERYTHING_UNLIMITED_MINUTES_AND_MESSAGING = "//h1[contains(text(),'Verizon More Everything Unlimited Minutes & Messaging - 500MB Data')]/parent::div/parent::div/following-sibling::div/child::a/child::img";
	public static final String VERIZON_MORE_EVERYTHING_UNLIMITED_MINUTES_AND_MESSAGING_500MB_DATA_ADD_BUTTON = "//h1[contains(text(),'Verizon More Everything Unlimited Minutes & Messaging - 500MB Data')]/parent::a/parent::div/parent::div/following-sibling::div/child::a";
	public static final String KEEP_MY_EXISTING_VERIZON_WIRELESS_LEGACY_ADD_BUTTON = "//h1[contains(text(),'Keep My Existing Verizon')]/parent::div/parent::div/following-sibling::div/child::a/child::img";
	public static final String VERIZON_MORE_EVERYTHING = "//h1[contains(text(),'Verizon More Everything Unlimited Minutes & Messaging - 500MB Data')]/parent::a/parent::div/parent::div/following-sibling::div/child::a/child::img";
	public static final String VERIZON_MORE_PLAN_ONLY="(//h1[contains(text(),'More')])[1]";
	public static final String SPRINT_FAMILY_SHARE_PLAN = "(//h1[contains(text(),'Sprint Family Share')])[1]";
	public static final String ADD_PLAN =	"//span[contains(text(),'add')]";  
	public static final String SELECT_FEATURE_CHECKBOX = "//span[@class='ui-icon ui-icon-shadow ui-icon-checkbox-off']";
	public static final String FIRST_TEXT_PICTURE_AND_MESSAGING_TAB = "(//span[contains(text(),'Text, Picture, and Video Messaging')])[1]";
	public static final String FIRST_1000_MESSAGES_10_PER_MONTH_CHECKBOX = "(//span[contains(text(),'1000 Messages				        $10.00/month')])[1]/parent::span/parent::label/parent::div/child::input";
	/* end region Verizon Shop Plans Elements */

	/* region Cart Page Elements */  
	public static final String CLEAR_CART_BUTTON = "emptyCart";
	public static final String CONTINUE_COMMON_BUTTON = "continue";
	public static final String FIRST_ASSIGN_NUMBER_DROPDOWN = "phonenumber-1";
	public static final String SECOND_ASSIGN_NUMBER_DROPDOWN = "phonenumber-2";  
	public static final String CONTINUE_CART_BUTTON = "continue";

	public static final String FIRST_PHONE_PRICE_TEXT = "((//select[@id='phonenumber-1']/parent::div/parent::div/parent::div/following-sibling::div/child::a/child::div)[3]/child::span)[2]";
	public static final String SECOND_PHONE_PRICE_TEXT = "((//select[@id='phonenumber-2']/parent::div/parent::div/parent::div/following-sibling::div/child::a/child::div)[3]/child::span)[2]";
	public static final String FIRST_PHONE_MODEL_LINK = "//select[@name='phonenumber-1']/parent::div/parent::div/preceding-sibling::h1/child::a";
	public static final String SECOND_PHONE_MODEL_LINK = "//select[@name='phonenumber-2']/parent::div/parent::div/preceding-sibling::h1/child::a";
	public static final String PHONE_PRICE_AAL_TEXT = "(((//div[@class='b2']/child::div)[2]/child::a/child::div)[3]/child::span)[2]";
	public static final String PHONE_MODEL_AAL_LINK = "(//div[@class='b2']/child::div)[1]/child::h1/child::a";
	public static final String PLAN_PRICE = "//span[contains(text(),'$140.00')]";
	public static final String DEVICE1_PRICE = "(//div[@class='f1 clearfix']/a/div/span)[3]";
	public static final String DEVICE2_PRICE = "(//div[@class='f1 clearfix']/a/div/span)[6]";
	public static final String DEVICE3_PRICE = "(//div[@class='f1 clearfix']/a/div/span)[9]";

	/* end region Cart Page Elements */

	/* region Select Plan Features Page Elements */
	public static final String CANCEL_SPF_BUTTON = "//button[contains(text(),'cancel')]";
	public static final String CONTINUE_SPF_BUTTON = "featureSubmit";
	public static final String BASIC_PHONE_DISCOUNT_0_CHECKBOX = "addPoptInput_1_99366";
	public static final String GB_25_CLOUD_FREE_ON_MORE_EVERYTHING_PLANS_$2_99_PER_MONTH_CHECKBOX = "(//span[contains(text(),'25GB Cloud - Free on MORE Everything Plans!				        $2.99/month')]/parent::span/parent::label/parent::div/child::input)[1]";
	/* end region Select Plan Features Page Elements */

	/* region Admin Page Elements */// Need to refactor this.
	public static final String GRANT_ALL_PERMISSIONS_EXPR = "//form[@name='PermissionsOverrideCookie']/table/tbody/tr/td/button[2]";
	public static final String MODIFY_RESULT = "modtype";
	public static final String EDIT_SCORE_FACTOR_POINTS = "sfPoints";
	public static final String NO_731_BRAND_SCORE_FACTOR = "731";
	public static final String BRAND_SCORE_FACTOR_POINTS = "brandScoreFactorPoints";
	public static final String BRAND_MANAGEMENT = "Brand Management";
	public static final String GRANT_PERMISSIONS_URL = "/admin/qa/setprofilingcookies.php";
	public static final String LIST_SCORE_FACTORS_URL = "/admin/scorefactor/listScoreFactor.php";
	public static final String SCORE_FACTOR_XPATH_EXPR = "//*[contains(@href,'editScoreFactor.php?sfId=&storeId=')]/parent::td[1]/following-sibling::td[3]";
	public static final String BRAND_SCORE_FACTOR_EXPR = "/admin/brands/editBrands.php?brandId=";
	public static final String UP_LEVEL = "(//a[contains(text(), 'Up Level')])[1]";
	/* end region Admin Page Elements */

	/* region Select a Protection Plan Insurance Page Elements */

	public static final String E_SECURITEL_INSURANCE = "(//div[@class='ui-radio']/child::input)[1]";
	public static final String APPLECARE_INSURANCE = "(//div[@class='ui-radio']/child::input)[1]";
	public static final String NO_INSURANCE = "(//span[contains(text(),'No, guest does not want insurance')]/parent::span/parent::label/parent::div/child::input)[1]";
	public static final String NOINSURANCE_FIRSTMOBILE = "(//div[@class='ui-radio']/child::input)[3]";
	public static final String NOINSURANCE_SECONDMOBILE = "(//div[@class='ui-radio']/child::input)[6]";
	public static final String NOINSURANCE_THIRDMOBILE = "(//div[@class='ui-radio']/child::input)[9]";
	public static final String NOINSURANCE_SPRINT_FIRSTMOBILE = "(//div[@class='ui-radio']/child::input)[2]";
	public static final String NOINSURANCE_SPRINT_SECONDMOBILE = "(//div[@class='ui-radio']/child::input)[4]";
	public static final String GUEST_REVIEW = "guestReview";
	public static final String CONTINUE_BUTTON = "continueButton";
	public static final String CANCEL_BUTTON = "cancelButton";
	public static final String E_SECURITEL_INSURANCE_FIRST ="(//div[@class='ui-radio'])[1]/child::input";
	/* end region Select Plan Features Page Elements */

	/* region Service Provider Verification Page Elements */
	public static final String POPULATE_FORM = "input.ui-btn-hidden";
	public static final String POPULATE_FORM_BYCLASS_BUTTON="ui-btn-hidden";
	public static final String GUEST_CREDIT_CHECK_CHECKBOX = "//form[@id='checkout']/div[3]/div[4]/div/div/label/span/span[2]";
	public static final String FIRST_NAME_TEXTBOX = "Srv_Bill_First_Name";
	public static final String MIDDLE_INITIAL_TEXTBOX = "Srv_Bill_Middle_Initial";
	public static final String LAST_NAME_TEXTBOX = "Srv_Bill_Last_Name";
	public static final String EMAIL_TEXTBOX = "Ecom_BillTo_Online_Email";
	public static final String ID_TYPE_DROPDOWN = "Identification_Type";
	public static final String STATE_DROPDOWN = "Identification_State";
	public static final String ID_NUMBER_TEXTBOX = "Identification_Number";
	public static final String MONTH_ID_EXPIRATION_DROPDOWN = "Identification_Expiration_Month";
	public static final String YEAR_ID_EXPIRATION_DROPDOWN = "Identification_Expiration_Year";
	public static final String CONTINUE_SPV_BUTTON = "continueButton";
	public static final String SPV_PHONE_NUMBER = "Srv_Bill_Daytime_Phone";
	public static final String GUEST_AGREES_CREDIT_CHECK = "Agree_To_Credit_Check";
	public static final String SERVICE_BILLING_ADDRESS1 = "Srv_Bill_Address1";
	public static final String SERVICE_BILLING_CITY = "Srv_Bill_City";
	public static final String SERVICE_BILLING_STATE = "Srv_Bill_State";
	public static final String SERVICE_BILLING_ZIPCODE = "Srv_Bill_Zip";
	public static final String ADDRESS_1_TEXTBOX = "Srv_Bill_Address1";
	public static final String ADDRESS_2_TEXTBOX = "Srv_Bill_Address2";
	public static final String CITY_TEXTBOX = "Srv_Bill_City";
	public static final String ZIP_TEXTBOX = "Srv_Bill_Zip";
	public static final String BIRTH_MONTH_DROPDOWN = "birthMonth";
	public static final String BIRTH_DAY_DROPDOWN = "birthDay";
	public static final String BIRTH_YEAR_DROPDOWN = "birthYear";
	public static final String SSN_TEXTBOX = "Social_Security";
	public static final String STATE_PI_DROPDOWN = "Srv_Bill_State";
	public static final String PIN_NUMBER = "ccfparam_37_0_0_0";
	public static final String SPRINT_SECURITY_QUESTIONS = "ccfparam_38_0_0_0";
	public static final String SPRINT_SECURITY_ANSWERS = "ccfparam_39_0_0_0";
	public static final String YES_RECIEVE_SPRINT_EBILL = "//label[@for='ccfparam_79_0_0_0']";
	/* end region Service Provider Verification Page Elements */


	/* region Order Review and Confirm Page Elements */ 
	public static final String CANCEL_ORDER_BUTTON = "//button[contains(text(),'cancel order')]";
	public static final String VERIFY_2YR_UPGRADE_TEXT = "(//div[@class='orSplitWrapper']/div[@class='orSplitData orW-50'])[5]";

	public static final String VERIFY_PLAN_DESCRIPTION_TEXT = "(//div[@class='orSplitWrapper']/div[@class='orSplitData orW-50'])[4]";
	public static final String phonePrice$649_99 = "(//div[contains(text(),'$649.99')])[1]";
	public static final String PHONE_PRICE_LINE1 = "((//b[contains(text(),'Line 1')])[1]/parent::div/following-sibling::div)[1]";
	public static final String PHONE_PRICE_LINE2 = "((//b[contains(text(),'Line 2')])[1]/parent::div/following-sibling::div)[1]";
	public static final String PHONE_MONTHLY_FEE = "((//div[contains(text(),'First Month Device Installment')])[1]/following-sibling::div)[1]";
	public static final String UPGRADE_FEE_VALUE_TEXT = "((//div[contains(text(),'Upgrade Fee:')])[1]/following-sibling::div)[1]";
	public static final String UPGRADE_FEE_VALUE_2_TEXT = "((//div[contains(text(),'Upgrade Fee:')])[2]/following-sibling::div)[1]";
	public static final String TOTAL_DUE_TODAY_VALUE = "((//div[contains(text(),'Total Due Today:')])[1]/following-sibling::div)[2]";
	public static final String EXISTING_PLAN_DIV = "(//div[@class='orLineOrderContainer']/child::li)[1]";  
	public static final String EXISTING_PLAN_2_DIV = "((//div[@class='orLineOrderContainer']/child::li)[1]/parent::div/parent::div/parent::div/following-sibling::div/child::div/child::div/child::li)[1]";
	public static final String TWO_YEARS_UPGRADE_LABEL = "(//div[contains(text(),'2 Year Upgrade:')])[1]";	
	public static final String TWO_YEARS_UPGRADE_2_LABEL = "(//div[contains(text(),'2 Year Upgrade:')])[2]";
	public static final String DEVICE1_PRICE_ORDERREVIEW = "(//div[@class='orSplitWrapper']/div)[5]";
	public static final String DEVICE1_ACTIVATIONFEE_ORDERREVIEW = "id('retailPage')/x:section/x:div[2]/x:div[2]/x:div[1]/x:div[2]/x:li[2]/x:div/x:div[2]";
	public static final String DEVICE2_PRICE_ORDERREVIEW = "(//div[@class='orSplitWrapper']/div)[11]";
	public static final String DEVICE2_ACTIVATIONFEE_ORDERREVIEW = "id('retailPage')/x:section/x:div[2]/x:div[2]/x:div[2]/x:div[2]/x:li[2]/x:div/x:div[2]";
	public static final String DEVICE3_PRICE_ORDERREVIEW = "(//div[@class='orSplitWrapper']/div)[17]";
	public static final String DEVICE3_ACTIVATIONFEE_ORDERREVIEW = "id('retailPage')/x:section/x:div[2]/x:div[2]/x:div[3]/x:div[2]/x:li[2]/x:div/x:div[2]";
	public static final String TOTAL_FEE = "//div[@class='orSplitData orW-10 orAlignRight bold green']";
	public static final String PLAN_PRICE_ORDERREVIEW = "id('retailPage')/x:section/x:div[2]/x:div[3]/x:div[1]/x:div[2]/x:div/x:li[1]/x:div/x:div[2]";
    


	/* end region Order Review and Confirm Page Elements */

	/* region Terms and Conditions Page Elements */
	public static final String CONTINUE_TERMS_AND_CONDITION_BUTTON = "contBtn";
	public static final String TARGET_TERMS = "//h1[contains(text(),'Target Terms')]";
	public static final String TC_CHK_BOX = "//for[contains(text(),'Target Terms')]";
	public static final String EMAIL_TC_CHK_BOX ="checkbox-1";
	public static final String ACCEPTS_TARGET_TC_CHECKBOX = "term-lt";
	public static final String TERMS_CONDITIONS_CHECKBOX = "emailTerms";
	public static final String CARRIER_TERMS_CHECKBOX = "term-carrier";
	public static final String TARGET_TERMS_CHECKBOX = "term-lt";
	public static final String SAVE_SIGNATURE_BUTTON = "saveSig";
	public static final String CLEAR_SIGNATURE_BUTTON = "deleteSig";
	public static final String CONTINUE_TERMS_CONDITIONS_BUTTON = "contBtn";
	/* end region Terms and Conditions Page Elements */

	/* region Print Mobile Scan Sheet Page Elements */
	public static final String PRINT_MOBILE_SCAN_SHEET = "//button[contains(text(),'print mobile scan sheet')]";

	public static final String CONTINUE_FIRST_MSS_BUTTON = "(//button[contains(text(),'continue')])[1]";
	public static final String PHONE_MODEL_TEXT = "((//th[contains(text(),'Product Description - Line 1')]/parent::tr/following-sibling::tr)[1]/child::td)[1]";
	public static final String ORDER_NUMBER_VALUE_PMSS_TEXT = "//span[contains(text(),'Order Number:')]/following-sibling::span";
	public static final String STORE_LOCATION_VALUE_PMSS_TEXT = "//span[contains(text(),'Store Location:')]/following-sibling::span";
	public static final String PHONE_PRICE_VALUE_PMSS_TEXT = "((//th[contains(text(),'Product Description - Line 1')]/parent::tr/following-sibling::tr)[1]/child::td)[2]";
	public static final String PHONE_TAX_VALUE_PMSS_TEXT = "((//th[contains(text(),'Product Description - Line 1')]/parent::tr/following-sibling::tr)[2]/child::td)[2]";
	public static final String PHONE_2_PRICE_VALUE_PMSS_TEXT = "((//th[contains(text(),'Product Description - Line 2')]/parent::tr/following-sibling::tr)[1]/child::td)[2]";
	public static final String BARCODE_PMSS_IMAGE = "((//th[contains(text(),'Product Description - Line 1')]/parent::tr/following-sibling::tr)[1]/child::td)[3]/child::img";
	public static final String BARCODE_PMSS_IMAGE2 = "((//th[contains(text(),'Product Description - Line 2')]/parent::tr/following-sibling::tr)[1]/child::td)[3]/child::img";
	public static final String BARCODE_PMSS_IMAGE3 = "((//th[contains(text(),'Product Description - Line 3')]/parent::tr/following-sibling::tr)[1]/child::td)[3]/child::img";
	public static final String ORDER_DETAILS = " orConfirmWrapper";
	public static final String FIRST_DEVICE_BARCODE_IMAGE = "//div[@class = 'mss']/table/tbody/tr[2]/td/img";
	public static final String SECOND_DEVICE_BARCODE_IMAGE = "//div[@class = 'mss']/table/tbody/tr[4]/td/img";
	public static final String THIRD_DEVICE_BARCODE_IMAGE = "//div[@class = 'mss']/table/tbody/tr[6]/td/img";
	public static final String STORE_LOCATION = "//ul[@id = 'table']/li[3]";
	public static final String MOBILE_DETAILS = "mss";

	/* end region Print Mobile Scan Sheet Page Elements */

	/* region Payment Verification Page Elements */
	public static final String TARGET_RECEIPT_ID_TEXTBOX = "receipt";
	public static final String SUBMIT_BUTTON = "//button[contains(text(),'submit')]";
	/* end region Payment Verification Page Elements */

	/* region Device Verification and Activation Page Elements */
	public static final String DEVICE_IMEI_TEXTBOX = "serial";
	public static final String SUBMIT_DVA_BUTTON = "submit";
	public static final String SIM_TYPE = "(//input[@placeholder='SIM '])[1]";
	public static final String SIM_TYPE_2_TEXTBOX = "(//input[@placeholder='SIM '])[2]";
	public static final String CONTINUE_BUTTON_DVA = "//span[contains(text(),'continue')]"; 
	public static final String CVN_NUMBER_DVA_TEXTBOX = "cvn";
	public static final String CONTINUE_BUTTON_DEPOSIT = "//button[contains(text(),'continue')]";
	/* end region Device Verification and Activation Page Elements */


	/* region Activation Complete Page Elements */
	public static final String ACTIVATION_COMPLETE = "repwrapperactivationcomplete"; 
	public static final String ORDER_AND_ACTIVATION_COMPLETE_TEXT = "//h2[contains(text(),'Order and Activation Complete')]";
	public static final String ORDER_NUMBER_VALUE_TEXT = "//span[contains(text(),'Order Number:')]/following-sibling::span";
	public static final String PHONE_NUMBER_VALUE_TEXT = "(//th[contains(text(),'Phone #')]/parent::tr/following-sibling::tr/child::td)[2]";
	public static final String PHONE_NUMBER_VALUE_2_TEXT = "(//th[contains(text(),'Phone #')]/parent::tr/following-sibling::tr/child::td)[7]";
	public static final String IMEI_NUMBER_VALUE_TEXT = "(//th[contains(text(),'Phone #')]/parent::tr/following-sibling::tr/child::td)[4]";
	public static final String IMEI_NUMBER_VALUE_2_TEXT = "(//th[contains(text(),'Phone #')]/parent::tr/following-sibling::tr/child::td)[9]";
	public static final String SIM_NUMBER_VALUE_TEXT = "(//th[contains(text(),'Phone #')]/parent::tr/following-sibling::tr/child::td)[5]";
	public static final String SIM_NUMBER_VALUE_2_TEXT = "(//th[contains(text(),'Phone #')]/parent::tr/following-sibling::tr/child::td)[10]";
	public static final String PRICE_VALUE_TEXT = "(//th[contains(text(),'PRICE')]/parent::tr/following-sibling::tr/child::td)[2]";
	public static final String PRICE_VALUE_2_TEXT = "(//th[contains(text(),'PRICE')]/parent::tr/following-sibling::tr/child::td)[6]";
	public static final String DISCOUNT_VALUE_TEXT = "(//th[contains(text(),'PRICE')]/parent::tr/following-sibling::tr/child::td)[3]";
	public static final String SUBTOTAOL_VALUE_TEXT = "(//th[contains(text(),'PRICE')]/parent::tr/following-sibling::tr/child::td)[4]";
	public static final String DEVICE1_PHONENO = "id('ticketwrapper')/x:div[2]/x:table/x:tbody/x:tr[2]/x:td[2]";
	public static final String DEVICE2_PHONENO = "id('ticketwrapper')/x:div[2]/x:table/x:tbody/x:tr[3]/x:td[2]";
	public static final String DEVICE3_PHONENO = "id('ticketwrapper')/x:div[2]/x:table/x:tbody/x:tr[4]/x:td[2]";
	public static final String DEVICE1_IMEINO = "id('ticketwrapper')/x:div[2]/x:table/x:tbody/x:tr[2]/x:td[4]";
	public static final String DEVICE2_IMEINO = "id('ticketwrapper')/x:div[2]/x:table/x:tbody/x:tr[3]/x:td[4]";
	public static final String DEVICE3_IMEINO = "id('ticketwrapper')/x:div[2]/x:table/x:tbody/x:tr[4]/x:td[4]";

	/* end region Activation Complete Page Elements */

	/* region Wireless Customer Agreement Page Elements */  
	public static final String ACCEPTS_WCA_CHECKBOX = "term-wca";
	public static final String EMAIL_CHECKBOX = "emailTerms";
	public static final String SIGNATURE_TEXTBOX = "signature";
	public static final String SAVE_SIGNATURE_WCA_BUTTON = "saveSig";
	public static final String CONTINUE_WCA_BUTTON = "contBtn";
	/* end region Wireless Customer Agreement Page Elements */

	/* region Number port Page Elements */
	public static final String NUMBER_PORT_RADIOBUTTON = "radio-choice-2";
	public static final String NO_NUMBER_PORT_RADIOBUTTON = "radio-choice-1";
	/* end region Number port Page Elements */


	public static final String VISA_TAB = "radio-1a";
	public static final String MASTERCARD_TAB = "radio-2a";
	public static final String DISCOVER_TAB = "radio-3a";
	public static final String AMERICAN_EXPRESS_TAB = "radio-4a";

    public static final String USE_A_NEW_ADDRESS_BUTTON = "//label[@for='radio-choice-2']";
	public static final String CREDIT_CARD_NUMBER_TEXTBOX = "cc";  
	public static final String MONTH_ID_EXP_DROPDOWN = "select-choice-month";
	public static final String YEAR_ID_EXPDROPDOWN = "select-choice-year";
	public static final String CVN_NUMBER_TEXTBOX = "serial";   
	public static final String CONTINUE_PR_BUTTON = "paysubmit"; 
	public static final String SAME_ADDRESS_TAB = "//span[contains(text(),'Same as service billing address')]";

	public static final String SKIP_BUTTON = "//span[contains(text(),'skip')]";

	//Region - Admin page
	public static final String CREDIT_READ = "//td[contains(text(),'creditRead')]";
	public static final String CREDIT_READ_DD = "config[retrieveCreditApplication][metacode]";
	public static final String CREDIT_WRITE_DD = "config[submitCreditApplication][metacode]";
	public static final String RETRIEVE_CUSTOMER_DETAILS= "config[retrieveCustomerDetails][metacode]";
	public static final String RETRIEVE_CREDIT_APPLICATION= "config[retrieveCreditApplication][metacode]";
	public static final String ACCOUNT_PLAN_TYPE= "config[retrieveCustomerDetails][options][accountPlanType]";
	public static final String ADD_PHONE_NUMBERS = "//button[@id='retrieveCustomerDetails_addPhoneNumber']";
	public static final String PHONE_LIST = "//table[@id='phoneNumbersList']";
	public static final String FIRST_PHONE_TEXT_BOX = "//input[@name = 'config[retrieveCustomerDetails][options][phoneNumbers][1]']";
	public static final String SECOND_PHONE_TEXT_BOX = "//input[@name = 'config[retrieveCustomerDetails][options][phoneNumbers][2]']";
	public static final String SECOND_PHONE_REMOVE = "//input[@name = 'config[retrieveCustomerDetails][options][phoneNumbers][2]']/parent::td/following-sibling::td/child::button";
	public static final String RETRIEVE_EXISTING_CUSTOMER_INSTALLEMENTS_DETAILS = "config[retrieveExistingCustomerInstallmentDetails][metacode]";
	public static final String RETRIEVE_PRICE_PLANS = "config[retrievePricePlans][metacode]";
	public static final String SUBMIT_ACTIVATION = "config[submitActivation][metacode]";
	public static final String SUBMIT_CREDIT_APPLICATION = "config[submitCreditApplication][metacode]";
	public static final String SUBMIT_RECIEPT = "config[submitReceipt][metacode]";
	public static final String SUBMIT_SERVICE_DETAILS = "config[submitServiceDetails][metacode]";
	public static final String SUBMIT_EDGE_UP_PAYMENT = "config[submitEdgeUpPayment][metacode]";
	public static final String RETURN_OR_EXCHANGE_DEVICE = "config[returnOrExchangeDevice][metacode]";

	public static final String SAVE = "submitButton";
	public static final String VERIZON_BS = "config[1008][postLocation][user]";
	public static final String VERIZON_CR = "//button[@name='config[1008][postLocation][user]' and contains(text(), 'Carrier Responder')]";
	public static final String VERIZON_EXTERNAL = "//button[@name='config[1008][postLocation][user]' and contains(text(), 'External Server')]";
	public static final String ATT_BS = "config[1003][postLocation][user]";
	public static final String ATT_CR = "//button[@name='config[1003][postLocation][user]' and contains(text(), 'Carrier Responder')]";
	public static final String ATT_EXTERNAL = "//button[@name='config[1003][postLocation][user]' and contains(text(), 'External Server')]";
	public static final String SPRINT_BS = "config[1034][postLocation][user]";
	public static final String SPRINT_CR = "//button[@name='config[1034][postLocation][user]' and contains(text(), 'Carrier Responder')]";
	public static final String SPRINT_EXTERNAL = "//button[@name='config[1034][postLocation][user]' and contains(text(), 'External Server')]";
	public static final String ATT_APICONFIG_LINK = "//a[@href='config.htm?prtnrId=1003']";
	public static final String VERIZON_APICONFIG_LINK = "//a[@href='config.htm?prtnrId=1008']";
	public static final String SPRINT_APICONFIG_LINK = "//a[@href='config.htm?prtnrId=1034']";
	public static final String WIDGET_MANAGEMENT = "/admin/widget/listwidget.php";
	public static final String SEARCH_WIDGET_TEXTBOX = "searchWidgetName";
	public static final String SEARCH_WIDGET_BUTTON = "modtype";
	public static final String SERVICE_CHARGE_BREAKDOWN_LINK = "//td[contains(text(), 'Hide Service Charge ')]/preceding-sibling::td/child::a";
	public static final String WIDGET_CONTENT = "//textarea[@name='widgContent']";
	//EndRegion

	//Region Ship Admin Page
	public static final String ORDER_TEXTBOX = "//input[@name='ordId']";
	public static final String DEACTIVATE_LINE_1 = "(//input[@type='checkbox'])[1]";
	public static final String UPDATE_BUTTON = "//input[@type='submit' and @value='Update']";
	//EndRegion Ship Admin Page

	//Region - DBError
	public static final String DBERROR_FILTER = "filter";
	public static final String DBERROR_SECTION = "//pre";
	public static final String DBERROR_SECTION_BR = "//pre/br";
	//EndRegion

	//Region Order Summary Page   
	public static final String STATUS_VALUE_LINK = "(//th[contains(text(),'Status')])[1]/following-sibling::td/child::a";
	public static final String ORDER_NUMBER_VALUE_SA_LINK = "//th[contains(text(),'Order #')]/following-sibling::td/child::a";
	public static final String ORDER_BUY_TYPE_VALUE_TEXT = "//th[contains(text(),'Order Buy Type')]/following-sibling::td";
	public static final String ADDITIONAL_INFO_VALUE_TEXT = "//th[contains(text(),'Additional Info')]/following-sibling::td";
	public static final String PARTNER_VALUE_TEXT = "//th[contains(text(),'Partner')]/following-sibling::td";
	public static final String FINANCING_PROGRAM_VALUE_TEXT = "//th[contains(text(),'Financing Program')]/following-sibling::td";
	public static final String TRADE_IN_REQUIRED_VALUE_TEXT = "//th[contains(text(),'Trade-in Required')]/following-sibling::td";
	public static final String PAYMENT_REQUIRED_VALUE_TEXT = "//th[contains(text(),'Payment Required')]/following-sibling::td";
	public static final String AMOUNT_PAID_VALUE_TEXT = "//th[contains(text(),'Amount Paid')]/following-sibling::td";
	public static final String FIRST_SERVICE_ACTIVATION_RESPONSE_RECEIVED_COMMENT_VALUE = "(//font[contains(text(),'Service activation response received')]/parent::td)[1]/following-sibling::td";
	public static final String SECOND_SERVICE_ACTIVATION_RESPONSE_RECEIVED_COMMENT_VALUE = "(//font[contains(text(),'Service activation response received')]/parent::td)[2]/following-sibling::td";
	public static final String THIRD_SERVICE_ACTIVATION_RESPONSE_RECEIVED_COMMENT_VALUE = "(//font[contains(text(),'Service activation response received')]/parent::td)[3]/following-sibling::td";
	public static final String FOURTH_SERVICE_ACTIVATION_RESPONSE_RECEIVED_COMMENT_VALUE = "(//font[contains(text(),'Service activation response received')]/parent::td)[4]/following-sibling::td";
	public static final String FIFTH_SERVICE_ACTIVATION_RESPONSE_RECEIVED_COMMENT_VALUE = "(//font[contains(text(),'Service activation response received')]/parent::td)[5]/following-sibling::td";
	public static final String TRANSACTION_POST_COMMENT_VALUE = "(//font[contains(text(),'Transaction Post')])[1]/parent::td/following-sibling::td";
	public static final String SHIPPING_COMMENT_VALUE = "(//font[contains(text(),'Shipping')])[1]/parent::td/following-sibling::td";
	public static final String BAN_VALUE_TEXT = "(//b[contains(text(),'Billing Account Number (BAN)')]/parent::td/following-sibling::td)[1]";
	public static final String VIEW_DETAILS_DROPDOWN = "gotoPageDetails";
	public static final String ROC_HOME_LINK = "//a[@href='/shipadmin/']";
	public static final String IN_STORE_BILLING_STATUS_LINK = "//a[contains(text(),'In-Store Billing')]";
	public static final String ACTIVATION_SCAN_REQUIRED_STATUS_LINK = "//a[contains(text(),'Activation Scan Required')]";
	public static final String WCA_SIGNATURE_REQUIRED_STATUS_LINK = "//a[contains(text(),'WCA Signature Required')]";
	public static final String SHIPPED_STATUS_LINK = "//a[contains(text(),'Shipped')]";
	public static final String PRINTABLE_FORMS_DROPDOWN = "gotoPagePrint";
	public static final String EVENT_LOG_TABLE = "//th[contains(text(),'oevId')]/parent::tr/parent::tbody/parent::table";
	public static final String ISSUED_RMA = ".//*[@id='bg2']/table/tbody/tr[13]/td/a";
	public static final String CHILD_ORDER = "(//th[contains(text(),'Child Orders')])[1]/following-sibling::td/child::a";
	public static final String MANUAL_REVIEW_STATUS = ".//*[contains(text(),'Manual Review')]";
	public static final String PARTNER_TEXT= "(//th[contains(text(),'Partner')])[1]/following-sibling::td";
	public static final String APPLECARE_INSURANCESTATUS= "(//td[contains(text(), 'Insurance Status')])/following-sibling::td";
	public static final String ORDER_LINE_CREDIT_DROPDOWN = ".//select[@name='orderLineCreditResultArr[1][1]']";
	public static final String MOVE_QUEUES_DROPDOWN = ".//*[@id='actFormDiv']/div[1]/input[1]";
	public static final String MOVE_QUEUES = "html/body/table[2]/tbody/tr[2]/th[4]/b/select";
	public static final String ORDER_SUB_STATUS = ".//select[@name='ordsubstId']";
	public static final String MOVE_QUEUES_BUTTON = ".//select[@value='Move Queues']";
	//EndRegion Order Summary Page

	// EndRegion Wireless Customer Agreement  

	//Region - Receipt Page
	public static final String ORDER_COMPLETION_TEXT= "//h2[contains(text(),'Order and Activation Complete')]";
	//EndRegion - Receipt Page

	public static final String SIGNATURE_WCA_TEXTBOX = "signature"; 
	public static final String TERMS_AND_CONDITIONS_DIV = "//div[@class='termsandcondition']";
	public static final String TERM_1_LABEL = "(//label[contains(text(),'Term')])[1]";
	public static final String TERM_2_LABEL = "(//label[contains(text(),'Term')])[2]";
	public static final String ETF_1_LABEL = "(//label[contains(text(),'ETF:')])[1]";
	public static final String ETF_2_LABEL = "(//label[contains(text(),'ETF:')])[2]";  
	public static final String ACTIVATION_DATE_1_LABEL = "(//label[contains(text(),'Activation Date:')])[1]"; 
	public static final String ACTIVATION_DATE_2_LABEL = "(//label[contains(text(),'Activation Date:')])[2]"; 
	public static final String ACTIVATION_FEE_1_LABEL = "(//label[contains(text(),'Activation Fee:')])[1]";
	public static final String ACTIVATION_FEE_2_LABEL = "(//label[contains(text(),'Activation Fee:')])[2]";
	public static final String UPGRADE_FEE_1_LABEL = "(//label[contains(text(),'Upgrade Fee:')])[1]";
	public static final String UPGRADE_FEE_2_LABEL = "(//label[contains(text(),'Upgrade Fee:')])[2]"; 
	public static final String FEATURES_1_LABEL = "(//label[contains(text(),'Features:')])[1]";
	public static final String FEATURES_2_LABEL = "(//label[contains(text(),'Features:')])[2]";
	public static final String WCA_CANCEL_ORDER_BUTTON = "//span[contains(text(),'guest declines')]";

	
	// EndRegion Wireless Customer Agreement


	//Region - Inventory Management Page
	public static final String USERNAME_INVENTORY_TEXTBOX = "userName";
	public static final String PASSWORD__INVENTORY_TEXTBOX = "password";
	public static final String STORE_INVENTORY_TEXTBOX = "autoCompleteField";
	public static final String STORE_INVENTORY_SELECT = "id('autoCompleteField:menu')/x:ul/x:li";
	public static final String PRODUCTS_INVENTORY_LINK = "//li[contains(text(),'PRODUCTS')]";
	public static final String SERIALIZED_PRODUCTS = "tlink_89";
	public static final String IMEI_INVENTORY_TEXTBOX = "imei";
	public static final String SUBMIT_INVENTORY = "submit_0";
	public static final String IFRAME_INVENTORY = "content";
	public static final String SERIALIZEDSTATUS_INVENTORY = "//td[@class='serializedStatusTranslated']";
	public static final String PURCHASING_INVENTORY_LINK = "//li[contains(text(),'PURCHASING')]";
	public static final String RECEIVING = "tlink_99";
	public static final String RECEIVE_NEW_SHIPMENT = "pagelink";
	public static final String VENDOR_DROPDOWN = "vendors";
	public static final String PRODUCT_TEXTBOX = "shipmentProduct";
	public static final String ADD_PRODUCT_BUTTON = "addButton";
	public static final String SCAN_CODE_TEXTBOX = "scancodesfield";
	public static final String ADD_TO_INVENTORY = "addToInventory";
	public static final String PRODUCT_AUTOCOMPLETE_TEXTBOX = "//a[starts-with(@id, 'ui-id-')]";

	//EndRegion - Inventory Management Page


	//Region - Customer Look Up
	public static final String RECEIPTID_TEXTBOX = "receiptId";
	public static final String SUBMIT_RECEIPTID = ".//*[@id='scanlookupForm']/div/div[2]/div[2]/a/span";
	public static final String VIEW_GUEST_ORDERS = "(//span[@class='ui-btn-text'])[7]";
	public static final String CONTINUE_CUSTOMERLOOKUP = "continue";
	public static final String SUBMIT_CUSTOMER_LOOKUP_BUTTON = "//span[contains(text(),'submit')]";


	public static final String RETURNDEVICEESN_TEXTBOX=	"//input[@id='addDeviceId']";
	public static final String SUBMITFORM_BUTTON = "submitForm";
	public static final String FIRSTNAME_TEXTBOX = "//input[@id='fname']";
	public static final String LASTNAME_TEXTBOX = "//input[@id='lname']";
	public static final String EMAILID_TEXTBOX = "//input[@id='email']";
	public static final String PHONE_TEXTBOX = "//input[@id='phone']";
	public static final String ORDERLOOKUP_TAB = "//a/span/span[contains(text(),'Order lookup with guest info')]";
	public static final String EXCHANGE_MESSAGE = "financingExchangeWarning";
	public static final String CONTINUE_EXCHANGE_BUTTON ="//a[@class='ui-btn ui-btn-up-e ui-shadow ui-btn-corner-all ui-disabled']";
	//EndRegion - CustomerLookup

	//Region - Return Scan Page
	public static final String ESNIEMEID_TEXTBOX = "serialId";
	public static final String ORDER_ID = "(//div[@class='irListData'])[5]";
	//EndRegion

	//Region -Return or Exchange Verification
	public static final String DEVICEPOWERON_RADIOBUTTON ="(//input [@name='devicePowerOn'])[1]";
	public static final String DEVICECONDITION_RADIOBUTTON = "(//input [@name='deviceCondition'])[2]";
	public static final String DEVICEACCESSORY_RADIOBUTTON = "(//input [@name='deviceAccessory'])[1]";
	public static final String DEVICEPACKAGING_RADIOBUTTON = "(//input [@name='devicePackaging'])[1]";
	public static final String CONTINUEREV_BUTTON =  "continueButton";
	public static final String DEVICE_POWERON = ".//*[@id='devicequestions']/div/section[1]/div[2]/fieldset/div/div[1]/label/span";
	public static final String DEVICECONDITION_YES =  ".//*[@id='devicequestions']/div/section[2]/div[2]/fieldset/div/div[1]/label/span";
	public static final String DEVICEACCESSORY_NO =  ".//*[@id='devicequestions']/div/section[3]/div[2]/fieldset/div/div[2]/label/span";
	public static final String DEVICEPACKING_NO =  ".//*[@id='devicequestions']/div/section[4]/div[2]/fieldset/div/div[2]/label/span";
	//EndRegion - Return or Exchange Verification


	//Region -Return or Exchange Verification
	public static final String ACCEPTRETURNEXCHANGE_RADIOBUTTON = "//input[@id='acceptReturnExchange']";
	public static final String REJECTRETURNEXCHANGE_RADIOBUTTON ="//input[@id='rejectReturnExchange']";
	public static final String RETURNDEVICE_RADIOBUTTON = "//input[@id='acceptReturn']";
	public static final String EXCHANGEDEVICE_RADIOBUTTON = "//input[@id='acceptExchange']";
	public static final String FINANCING_DROPDOWN = "financingOptIn";
	public static final String EXCHANGEREASONS_DROPDOWN = "exchangeReasons";
	public static final String RETURNREASONS_DROPDOWN = "returnReasons";
	public static final String CONTINUE_EXCHANGE = ".//*[@id='continue']/span";
	public static final String CONTINUE_PRECONDITION = ".//*[@id='verificationForm']/div/fieldset/div[2]/div/button";
	public static final String PROCEED_EXCHANGE= ".//*[@id='processForm']/div/div/ul/li/div[2]/div[1]/div[2]/fieldset/div/div[1]/label/span";
	public static final String EXCHANGE_DEVICE = ".//*[@id='returnOrExchange']/section/div[2]/fieldset/div/div[2]/label/span";
	public static final String RETURN_DEVICE = ".//*[@id='returnOrExchange']/section/div[2]/fieldset/div/div[1]/label/span";
	public static final String RETURN_ANOTHER = "returnAnother";
	public static final String FIRSTDEVICE_NOINSURANCE=".//*[@id='retailPage']/section/form/div[2]/div/div/ul/li[1]/div[2]/fieldset/div/div[3]/label/span";
	public static final String SECONDDEVICE_NOINSURANCE=".//*[@id='retailPage']/section/form/div[2]/div/div/ul/li[2]/div[2]/fieldset/div/div[3]/label/span";
	public static final String CONTINUE_EXCHANGE_MSS = ".//*[@id='retailPage']/section/fieldset[1]/div[2]/div";
	public static final String PARENT_ORDER_NUMBER = "(//th[contains(text(),'Parent Order')])[1]/following-sibling::td/child::a";
	//EndRegion - Return or Exchange Verification

	//EndRegion - Exchange Device Number


	//Region - Exchange Device Number
	public static final String PORT_NUMBER_TEXT = "//span[contains(text(),'Yes, port an existing number for this line.')]";
	public static final String CURRENT_PHONE_NUMBER = "//input[@id='cpnumber']";
	public static final String SELECT_CARRIER = "//select[@id='select-choice-0']";
	public static final String CURRENT_ACCOUNT_NUMBER = "//input[@id='canumber']";
	public static final String PORT_SSN = "//input[@id='ssn']";
	public static final String PORT_FIRST_NAME = "//input[contains(@id, 'firstName')]";
	public static final String PORT_LAST_NAME = "//input[contains(@id, 'lastName')]";
	public static final String ADDRESS_1 = "//input[contains(@id, 'addr1')]";
	public static final String ADDRESS_2 = "//input[contains(@id, 'addr2')]";
	public static final String PORT_CITY = "//input[contains(@id, 'city')]";
	public static final String PORT_STATE = "//select[contains(@id, 'state')]";
	public static final String PORT_ZIP = "//input[contains(@id, 'zip')]";

	public static final String CV_FIRST_NAME_TEXTBOX ="Ecom_BillTo_Postal_Name_First";
	public static final String CV_MIDDLE_NAME_TEXTBOX ="Ecom_BillTo_Postal_Name_Middle";
	public static final String CV_LAST_NAME_TEXTBOX = "Ecom_BillTo_Postal_Name_Last";

	//EndRegion - Exchange Device Number

	//Region SQL Util Admin Page
	public static final String SUBMIT_QUERY_BUTTON = "sqlSubmit";
	public static final String CHOOSE_QUERY_WRAPPER_DROPDOWN = "queryWrapper";
	public static final String QUERY_TEXTBOX = "sqlStatement";
	public static final String ORDER_SIGNATURES_TABLE = "//b[contains(text(),'ordId')]/parent::span//parent::th/parent::tr/parent::tbody";
	public static final String ENCRYPTED_SIGNATURE_ROW = "(//b[contains(text(),'ordId')]/parent::span/parent::th/parent::tr/following-sibling::tr/child::td)[2]/child::b";
	public static final String GENERAL_TABLE = "//b[contains(text(),'ordId')]/parent::span//parent::th/parent::tr/parent::tbody";
	//EndRegion SQL Util Admin Page


	//Region Wireless Customer Agreement

	//Region - Carrier Responder XML Elements
	public static final String VERIZON_CARRIER_TAB ="//th[contains(text(),'verizon')]";
	public static final String VERSIONS_DROPDOWN = "versions";
	public static final String SERVICES_DROPDOWN = "services";
	public static final String TEMPLATES_DROPDOWN ="templates";
	public static final String SAVE_RESPONSE_BUTTON = "//input[@type='submit' and @value='Save Response']";
	public static final String LOAD_RESPONSE_BUTTON = "//input[@type='submit' and @value='Load Response']";
	public static final String XML_TEXTAREA = "payload";
	//EndRegion - Carrier Responder XML Elements

	//Region - Mail Verification
	public static final String SIGN_IN_LINK = "Email";
	public static final String G_EMAIL = "Email";
	public static final String GMAIL_PASSWORD = "Passwd";
	public static final String SIGIN_IN_BUTTON = "signIn";
	public static final String INBOX = "//a[@href ='https://mail.google.com/mail/#inbox']";
	public static final String DELETE = "id(':5')/div/div[1]/div[1]/div/div/div[2]/div[3]/div/div";
	public static final String SELECT_ALL = "id(':39')/div[1]/span/div";
	public static final String NEXT_BUTTON = "next";
	public static final String SEARCH_TEXTBOX = "gbqfq";
	public static final String WCA_EMAIL = "//span[contains(text(), 'Terms and Conditions')]";
	public static final String VERIZON_WCA = "(//div[contains(text(), 'VERIZON WIRELESS CUSTOMER AGREEMENT')])[1]";
	public static final String SEARCH_BUTTON = "gbqfb";
	//EndRegion

	//Region - Order History
	public static final String COMPLETED_LINK = "(//a[contains(text(),'Completed')])[2]";
	public static final String FIRST_COMPLETED_LINK = "(//a[contains(text(),'Completed')])[1]";
	//EndREgion Order History

	//region - Verizon Account Password
	public static final String VZNPASSWORD_CONTINUE_BUTTON = "submit";
	public static final String VZN_PASSWORD_BUTTON = "//button[contains(text(),'continue')]";
	public static final String VZN_ACCOUNT_PWD = "password";
	//endregion Verizon Account Password

	//Region - Return Device Page
	public static final String RETURNS_CONFIRMATION = "//h2[contains(text(), 'Returns Confirmation')]";
	public static final String SUCCESSFULLY_RETURNED_STRING = "//p[contains(text(), 'device has been successfully returned')]";
	public static final String LINE_AND_PHONE_NUMBER_STRING = "//div[@class='deact-row deact-table-grey']";
	public static final String RETURN_STEP1_TEXT = "//li[contains(text(), 'Verizon Wireless Activation')]";
	public static final String RETURN_STEP2_TEXT = "//li[contains(text(), 'Request deactivation')]";
	public static final String PRINT_INSTRUCTIONS = "//button[contains(text(),'print instructions')]";
	public static final String RETURN_HOME = "//span[contains(text(),'return home')]";
	//EndRegion - Return Device Page

	//Region-Plan Type Selection
	public static final String AAL_EXISTING_ACCOUNT = "radio-choice-1";
	public static final String UPGRADE_DEVICE_FOR_ONE_OR_MORE_LINES_ON_EXISTING_ACCOUNT_RADIO_BUTTON = "radio-choice-2";
	//EndRegion- Return Device Page

	//Region - AAL type selection
	public static final String AAL_EXISTING_FAMILY_PLAN="radio-choice-2";
	public static final String SWITCH_FROM_AN_INDIVIDUAL_TO_FAMILY_PLAN_AND_ADD_A_LINE_RADIO_BUTTON = "radio-choice-1";
	//EndRegion

	//region Installment Details page
	public static final String VERIZONEDGE_MONTHLYINSTALLMENT_RADIOBUTTON="(//input[contains(@id, 'radio')])[1]";
	//endregion

	//region - Support Center Page
	public static final String SUPPORT_CENTER_MESSAGE_POA = "//h2[contains(text(),'Support Center')]";
	public static final String ORDERID_SUPPORT_CENTER_PAGE = "//div[@id='retailPage']/section/p[2]/strong";
	public static final String CREDIT_CHECK_RESULT_SUMMARY_TABLE = ".//th[contains(text(),'Credit Check Results Summary')]";
	public static final String UPDATE_CREDIT_CHECK = ".//*[@id='bg2']/td[6]/input";
	public static final String CREDIT_CHECK_RESULT_UPDATE_DROPDOWN = ".//*[@id='bg2']/td[2]/select[1]";
	public static final String CREDIT_CHECK_RESULT_RESOLUTION_UPDATE_DROPDOWN = ".//*[@id='bg2']/td[2]/select[2]";
	public static final String NUMBER_OF_LINES_TO_UPDATE = ".//*[@name='ocrNumberOfPhoneLines']";
	public static final String ADD_UPDATE_CREDITCHECK_BUTTON = ".//form[@name='creditDetailForm']/table/tbody/tr/th/input[1]";
	public static final String CONTINUE_SUPPORT_CENTER_PAGE = "//button[contains(text(),'continue')]";
	public static final String CONTINUE_ACTIVATION = "activateNowButton";
	public static final String CONTINUE_AFTER_DEVICE_SHIPPED = "//div[@id='retailPage']/section/form/span/div/button";
	//endregion - Support Center Page

	//region Processing Error Page
	public static final String PROCESSING_ERROR_TEXT = "//h2[contains(text(),'Processing Error')]";
	public static final String PROCESSING_ERROR_MESSAGE_TEXT = "//p[contains(text(),'Please call the carrier to')]";
	public static final String ATT_HELP_NUMBET_TEXT = "//li[contains(text(),'AT&T: 800-331-0500')]";
	public static final String VERIZON_HELP_NUMBET_TEXT = "//li[contains(text(),'Verizon Wireless: 800-922-0204')]";
	public static final String SPRINT_HELP_NUMBET_TEXT = "//li[contains(text(),'Sprint: 800-639-6111')]";
	//endregion Processing Error Page

	//region DeviceFinance Installment Contract
	public static final String HEADER_DEVICE_FINANCE = "//h2[contains(text(),'Device Financing Installment Contract']";
	public static final String PRINT = "tcprint";
	//public static final String GUEST_CHKBOX = "//span[@classname='ui-icon ui-icon-shadow ui-icon-checkbox-on']";
	public static final String GUEST_CHKBOX = "term-contractFile";

	//endregion DeviceFinance Installment Contract

	//region Sprint Easy Pay Page
	public static final String PRICE_BOX = "//article[@class='optin-pricingwrap']";
	//endregion Sprint Easy Pay Page

	//region Sprint Easy Pay Eligibility Result
	public static final String ELIGIBLE_FOR_EASY_PAY_LABEL = "//div[contains(text(),'Eligible for Easy Pay:')]";
	public static final String DOWN_PAYMENT_LABEL = "//div[contains(text(),'Down payment:')]";
	public static final String INSTALLMENT_CONTRACT_LENGTH_LABEL = "//div[contains(text(),'Installment contract length:')]";
	public static final String MINIMUM_DOWN_PAYMENT_EASY_PAY_RADIO_BUTTON = "radio-choice-5";
	public static final String ALTERNATE_DOWN_PAYMENT_EASY_PAY_RADIO_BUTTON = "radio-choice-customize-5";
	//endregion Sprint Easy Pay Eligibility Result

    //region Sprint Shop Plans Page
	public static final String SPRINT_FAMILY_SHARE_1_GB_ADD_BUTTON = "//h1[contains(text(),'Sprint Family Share Pack 1GB')]/parent::a/parent::div/parent::div/following-sibling::div/child::a/child::img";
	//endregion Sprint Shop Plans Page
}
