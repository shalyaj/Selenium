package com.consensus.qa.sprint.tests;

import com.consensus.qa.framework.PageBase;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExchangeTests {
	//@Parameters({"test-type"})
	@Test	 
	   public void test_Exchange_Sprint1() {

		int count= PageBase.AdminPage().totalErrorCount();
		PageBase.AdminPage().isDBErrorFound(count);


	}
}