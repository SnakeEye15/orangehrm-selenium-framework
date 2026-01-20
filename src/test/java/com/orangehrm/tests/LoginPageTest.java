package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private HomePage homepage;
	private LoginPage loginpage;

	@BeforeMethod
	public void setupPages() {
		loginpage = new LoginPage(getDriver());
		homepage = new HomePage(getDriver());
	}

	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyValidLoginTest(String username, String password) {
		// ExtentManager.startTest("Valid Login Test"); -- This is implemented in
		// TestListener
		ExtentManager.logStep("Navigating to Login page Entering username and password");
		loginpage.login(username, password);
		ExtentManager.logStep("Verifying Admin Tab is visible or not");
		Assert.assertTrue(homepage.isAdminTabVisible(), "Admin Tab should be visible after successful login.");
		ExtentManager.logStep("Validation Successfully!");
		homepage.logout();
		ExtentManager.logStep("Logged Out successfully!");
		staticWait(2);
	}

	@Test(dataProvider = "inValidLoginData", dataProviderClass = DataProviders.class)
	public void invalidLoginTest(String username, String password) {
		// ExtentManager.startTest("InValid Login Test");-- This is implemented in
		// TestListener
		ExtentManager.logStep("Navigating to Login page Entering username and password");
		loginpage.login(username, password);
		String expectedError = "Invalid credentials1";
		Assert.assertTrue(loginpage.verifyErrorMessage(expectedError), "Test Failed: Invalid Error Message");
		ExtentManager.logStep("Validation Successfully!");
		ExtentManager.logStep("Logged Out successfully!");
	}
}
