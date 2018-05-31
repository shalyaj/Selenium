package com.consensus.qa.framework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.consensus.qa.pages.PaymentRequiredPage.CardType;
import com.consensus.qa.pages.ServiceProviderVerificationPage.IdType;

public class CSVOperations {

	public enum FileName
	{
		Sim_4FF,
		Sim_2FF,
		DependentTestCaseDetails,
		iPhone6,
		iPhone5C,
		iPhone5S,
		iPhone4S,
		SamsungGalaxyS4_16GBWhite,
		SamsungGalaxyS4_16GBWhite0003,
		SamsungGalaxyS4_16GBBlack,
		Sim_3FF,
		sim_2FF,
		AccountDetails,
		CreditCardDetails,
		SSNWithDeposit,
		SSNWithoutDeposit,
		NewPhoneNumber,
		ReceiptId,
		VerizonEdgeUpgrade,
		CustomerDetails,
		FamilyPlanPlaidUpgrade,
		VerizonNonEdgeUpgradeWithAppleCare,
		NumberPortingSingle,
		VerizonNonEdgeUpgradeMultipleLinesEligible,
		VerizonNonEdgeBuddyUpgrade,
		VerizonNonEdgeBuddyEarlyUpgrade,
		VerizonEdgeUPPaymentNotRequired,
		VerizonEdgeIndividualAccountReUseable,
		DownPaymentSSN,
		NPA_NXX,
		AppleCareiPhone5C,
		Sprint_Sim_3FF,
		SprintEasyPayUpgradeMultipleLinesEligible,
		Sprint_SamsungGalaxyS4_16GBWhite
	}

	String line = null;

	private static void fileUpdate(File file, List<String> lines) throws IOException {
		FileWriter fwriter = new FileWriter(file, false);
		BufferedWriter bwriter = new BufferedWriter(fwriter);
		try {
			for (String s : lines) {
				bwriter.write(s);
				bwriter.newLine();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			bwriter.flush();
			bwriter.close();
		}
	}

	public String GetIMEIOrSimNumberOrReceiptId(FileName FileName) throws IOException {
		List<String> lines = new ArrayList<String>();
		File file = new File(ResolveFileName(FileName));
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String number = null;
		try {
			int i = 0;

			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				if (split.length == 1 && i == 0) {
					number = split[0];
					line = line + "," + "InUse";
					i++;
				}
				lines.add(line);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			fr.close();
			br.close();
			CSVOperations.fileUpdate(file, lines);
		}
		return number;
	}

	@SuppressWarnings("null")
	public String[] GetIMEIAndSimNumber(FileName fileName) throws IOException{
		String[] IMEIAndSimNumbers = null;
		List<String> lines = new ArrayList<String>();
		File file = new File(ResolveFileName(fileName));
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		try {
			int i = 0;

			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				if (split.length == 1 && i == 0) {
					IMEIAndSimNumbers[0] = split[0];
					IMEIAndSimNumbers[1] = split[1];
					line = line + "," + "InUse";
					i++;
				}
				lines.add(line);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			fr.close();
			br.close();
			CSVOperations.fileUpdate(file, lines);
		}		
		return IMEIAndSimNumbers;
	}

	public void UpdateIMEIStatusToUsed(String IMEI, FileName FileName) throws IOException {
		List<String> lines = new ArrayList<String>();
		File file = new File(ResolveFileName(FileName));
		FileReader freader = new FileReader(file);
		BufferedReader breader = new BufferedReader(freader);
		try {
			int i = 0;

			while ((line = breader.readLine()) != null) {
				if (IMEI != null) {
					if (line.contains(IMEI) && i == 0) {
						line = line.replace("InUse", "Used");
						i++;
					}
				}
				lines.add(line);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			freader.close();
			breader.close();
			CSVOperations.fileUpdate(file, lines);
		}

	}

	public AccountDetails GetDetails(FileName FileName) throws IOException {
		List<String> lines = new ArrayList<String>();
		AccountDetails accountDetails = new AccountDetails();
		File file = new File(ResolveFileName(FileName));
		FileReader freader = new FileReader(file);
		BufferedReader breader = new BufferedReader(freader);
		try {
			int i = 0;

			while ((line = breader.readLine()) != null) {
				String[] split = line.split(",");
				if (split.length == 2 && i == 0) {
					accountDetails.MTN = split[0];
					accountDetails.Password = "";
					accountDetails.SSN = split[1];
					line = line + "," + "," + "InUse";
					i++;
				} else if (split.length == 3 && i == 0) {
					accountDetails.MTN = split[0];
					accountDetails.Password = split[1];
					accountDetails.SSN = split[2];
					line = line + "," + "InUse";
					i++;
				}
				lines.add(line);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			freader.close();
			breader.close();

			CSVOperations.fileUpdate(file, lines);
		}

		return accountDetails;
	}

	public void UpdateStatus(String Details, FileName fileName) throws IOException {
		List<String> lines = new ArrayList<String>();
		File file = new File(ResolveFileName(fileName));
		FileReader freader = new FileReader(file);
		BufferedReader breader = new BufferedReader(freader);
		try {
			int i = 0;

			while ((line = breader.readLine()) != null) {
				if (Details != null) {
					if (line.contains(Details) && i == 0) {
						line = line.replace("InUse", "Used");
						i++;
					}
				}
				lines.add(line);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			freader.close();
			breader.close();
		}
		CSVOperations.fileUpdate(file, lines);
	}

	public static String ResolveFileName(FileName fileName){
		String filePath = null;
		if(BrowserSettings.readConfig("test-type").contains("internal"))
			filePath = "TestData-Internal\\"+fileName.toString()+".csv";
		if(BrowserSettings.readConfig("test-type").contains("external"))
			filePath = "TestData-External\\"+fileName.toString()+".csv";
		return filePath;
	}

	public CreditCardDetails CreditCardDetails(CardType CardType) throws IOException
	{
		CreditCardDetails CardDetails = new CreditCardDetails();
		List<String> lines = new ArrayList<String>();
		File file = new File(ResolveFileName(FileName.CreditCardDetails));
		FileReader freader = new FileReader(file);
		BufferedReader breader = new BufferedReader(freader);
		try {
			int i = 0;

			while ((line = breader.readLine()) != null) {
				String[] split = line.split(",");
				if (line.contains(CardType.toString()) && i==0) {
					CardDetails.Number = split[0];
					CardDetails.ExpiryMonth = split[1];
					CardDetails.ExpiryYear = split[2];
					CardDetails.CVV = split[3];
					CardDetails.CardType = split[4];
					i++;					
				} 
				lines.add(line);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			freader.close();
			breader.close();
		}
		return CardDetails;
	}	

	public static CustomerDetails ReadCustomerDetailsFromCSV(IdType IdType) throws IOException
	{
		File file = new File(ResolveFileName(FileName.CustomerDetails));
		BufferedReader br = new BufferedReader(new FileReader(file));
		CustomerDetails customerDetails = new CustomerDetails();
		String line = null;
		String[] values=null;
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			if(values[11].equals(IdType.toString())){
				customerDetails.FirstName = values[0];
				customerDetails.LastName = values[1];
				customerDetails.Address1 = values[2];
				customerDetails.City = values[3];
				customerDetails.State = values[4];
				customerDetails.Zip = values[5];
				customerDetails.PhNum = values[6];
				customerDetails.EMail = values[7];
				customerDetails.BirthdayMonth = values[8];
				customerDetails.BirthdayDay = values[9];
				customerDetails.BirthdayYear = values[10];
				customerDetails.IDType = values[11];
				customerDetails.IDState = values[12];
				customerDetails.IDNumber = values[13];
				customerDetails.IDExpirationMonth = values[14];
				customerDetails.IDExpirationYear = values[15];
				break;
			}
		}
		br.close();
		return customerDetails;
	}

	public static NumPortingDetails ReadPortingDetails() throws IOException
	{
		File file = new File(ResolveFileName(FileName.NumberPortingSingle));
		BufferedReader br = new BufferedReader(new FileReader(file));
		NumPortingDetails portingDetails = new NumPortingDetails();
		String line = null;
		String[] values=null;
		int i=0;
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			if(i==1){
				portingDetails.CurrentPhoneNumber = values[0];
				portingDetails.CurrentAccNumber = values[1];
				portingDetails.SSN = values[2];
				portingDetails.Carrier = values[3];
				break;
			}
			i++;
		}
		br.close();
		return portingDetails;
	}

	public String GetNPANXX(String market) throws IOException {
		File file = new File(ResolveFileName(FileName.NPA_NXX));
		BufferedReader br = new BufferedReader(new FileReader(file));
		String NPANum = null;
		String line = null;
		String[] values = null;
		while ((line = br.readLine()) != null) {
			values = line.split(",");
			if (values[0].contains(market)) 
				NPANum = values[2];
		}
		br.close();
		return NPANum;
	}

	public static void WriteToCSV(String TC_ID, String ORDER_ID, String IMEI1,String IMEI2,String IMEI3,
			String FNAME,String LNAME,String EMAIL,String RECEIPT_ID,
			String ID_TYPE,String STATE,String ID_Number, String PhNum,String zip,String SSN,String Month,String Year) throws IOException {
		String fileName = ResolveFileName(FileName.DependentTestCaseDetails);

		FileWriter fw = new FileWriter(fileName,true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.newLine();
		bw.write( TC_ID + "," + ORDER_ID + "," + IMEI1 + "," +IMEI2 + "," +IMEI3+","+
				FNAME + "," + LNAME + "," + EMAIL + "," + RECEIPT_ID + ","
				+ ID_TYPE + "," + STATE + "," + ID_Number +"," + PhNum +","+zip +","+SSN +","+Month +","+Year
				);
		bw.flush();
		fw.close();
	}
}