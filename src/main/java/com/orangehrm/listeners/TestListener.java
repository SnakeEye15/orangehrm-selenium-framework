package com.orangehrm.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class TestListener implements ITestListener{

	//Triggered When a Test Starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName=result.getMethod().getMethodName(); //This will return the current test name
		//Start logging in Extent Report
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: "+testName);
		
	}

	//Triggered when a test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!", "Test End: "+testName+" -Test Passed");
		
	}

	//Triggers When a Test Fails
	@Override
	public void onTestFailure(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		String failureMessage=result.getThrowable().getMessage();
		ExtentManager.logStep(failureMessage);
		ExtentManager.logFailure(BaseClass.getDriver(),"Test Failed!" ,"Test End "+testName +" - Test Failed.. ");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped "+testName);
	}

	//Triggers when a suites starts 
	@Override
	public void onStart(ITestContext context) {
		//Initialize the Extent Report
		ExtentManager.getReporter();
		
	}

	//Triggers when the suites Ends
	@Override
	public void onFinish(ITestContext context) {
		 //Flush the Extent reports
		ExtentManager.endTest();
	}

	
}
