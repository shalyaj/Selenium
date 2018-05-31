package com.consensus.qa.tests;

import com.consensus.qa.framework.BrowserSettings;

import com.consensus.qa.framework.PageBase;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class RunTest {

	public static void main(String[] args) {
		

		List<String> file = new ArrayList<String>();
		file.add(BrowserSettings.readConfig("ATT"));
		TestNG objTestSuiteATTNG = new TestNG();
		objTestSuiteATTNG.setTestSuites(file);	
		objTestSuiteATTNG.setDefaultSuiteName("ATT Suite");
		objTestSuiteATTNG.setDefaultTestName("Test Cases");
		objTestSuiteATTNG.run();

		/*file.add(BrowserSettings.readConfig("Verizon"));
		TestNG objTestSuiteSprint = new TestNG();
		objTestSuiteSprint.setTestSuites(file);	
		objTestSuiteSprint.setDefaultSuiteName("Verizon Suite");
		objTestSuiteSprint.setDefaultTestName("Test Cases");
		objTestSuiteSprint.run();	   

		file.add(BrowserSettings.readConfig("Sprint"));
		TestNG objTestSuiteVerizon = new TestNG();
		objTestSuiteVerizon.setTestSuites(file);	
		objTestSuiteVerizon.setDefaultSuiteName("Verizon Suite");
		objTestSuiteVerizon.setDefaultTestName("Test Cases");
		objTestSuiteVerizon.run();*/
	
		
	}
}