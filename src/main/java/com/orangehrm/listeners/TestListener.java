package com.orangehrm.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class TestListener implements ITestListener {

	// Triggered When a Test Starts
	@Override
	public void onTestStart(ITestResult result) {

		String testName = result.getMethod().getMethodName();// This will return the current test name
		// Start logging in Extent Report
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: " + testName);

	}

	// Triggered when a test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logStep("Test Passed: " + testName);
	}

	// Triggers When a Test Fails
	@Override
	public void onTestFailure(ITestResult result) {
		/**
		 * String testName = result.getMethod().getMethodName();
		 * 
		 * if (result.getThrowable() != null) {
		 * ExtentManager.logStep(result.getThrowable().toString()); }
		 * 
		 * ExtentManager.logFailure( BaseClass.getDriver(), "Test Failed: " + testName,
		 * "Failure Screenshot");
		 **/

		String testName = result.getMethod().getMethodName();

		if (result.getThrowable() != null) {
			ExtentManager.logStep(result.getThrowable().toString());
		}

		WebDriver driver = BaseClass.getDriver();

		if (driver != null) {
			ExtentManager.logFailure(driver, "Test Failed: " + testName, "Failure Screenshot");
		} else {
			ExtentManager.logStep("Test failed before WebDriver initialization");
		}
	}

	// Triggered when a test is skipped
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped: " + testName);
	}

	// Triggers when a suites starts
	@Override
	public void onStart(ITestContext context) {
		// Initialize the Extent Report
		ExtentManager.getReporter();

	}

	// Triggers when the suites Ends
	@Override
	public void onFinish(ITestContext context) {
		// Flush the Extent reports
		ExtentManager.endTest();
	}

}
