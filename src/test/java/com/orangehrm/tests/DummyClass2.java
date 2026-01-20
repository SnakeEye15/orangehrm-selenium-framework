package com.orangehrm.tests;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass2 extends BaseClass {
	
	@Test
	public void dummyTest2() {
		//ExtentManager.startTest("DummyTest2 Test"); -- This is implemented in TestListener
		String Title= getDriver().getTitle();
		ExtentManager.logStep("verify the title");
		assert Title.equals("OrangeHRM") : "Test Failed - Title is not Matching";
		
		System.out.println("TestCase Passed - Title is matching ");
		ExtentManager.logStep("Validation Successful!");
	}

}
