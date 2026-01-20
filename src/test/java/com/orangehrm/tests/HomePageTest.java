package com.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass {

	private HomePage homepage;
	private LoginPage loginpage;

	@BeforeMethod
	public void setupPages() {
		loginpage = new LoginPage(getDriver());
		homepage = new HomePage(getDriver());
	}

	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyOrangeHRMLogo(String username, String password) {
		// ExtentManager.startTest("Home page Verify Logo Test"); -- This is implemented
		// in TestListener
		ExtentManager.logStep("Navigating to Login page Entering username and password");
		loginpage.login(username, password);
		ExtentManager.logStep("Verify Logo is visible or not.");
		Assert.assertTrue(homepage.verifyOrangeHRMlog(), "OrangeHRM logo isn't displayed");
		ExtentManager.logStep("Validation Successfully!");
		ExtentManager.logStep("Logged Out successfully!");
	}

}
