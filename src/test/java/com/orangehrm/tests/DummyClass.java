package com.orangehrm.tests;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass extends BaseClass {
	
	@Test
	public void dummyTest() {
		//ExtentManager.startTest("DummyTest1 Test"); -- This is implemented in TestListener
		String Title= getDriver().getTitle();
		ExtentManager.logStep("verify the title");
		assert Title.equals("OrangeHRM") : "Test Failed - Title is not Matching";
		
		System.out.println("TestCase Passed - Title is matching ");
		//ExtentManager.logSkip("This test case is skipped");
		throw new SkipException("Skipping the Test as per part of testing");
	}

}
