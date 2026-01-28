package com.orangehrm.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class DBVerificationTest extends BaseClass {

	private HomePage homepage;
	private LoginPage loginpage;

	@BeforeMethod
	public void setupPages() {
		loginpage = new LoginPage(getDriver());
		homepage = new HomePage(getDriver());
	}

	@Test(dataProvider = "employeeDeails", dataProviderClass = DataProviders.class)
	public void EmployeeNameVerificationTest(String empID, String empName) {
		ExtentManager.logStep("Logging with Admin ");
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));

		ExtentManager.logStep("Click on PIM tab");
		homepage.clickOnPIM();

		ExtentManager.logStep("Search for employee");
		homepage.employeeSearch(empName);

		ExtentManager.logStep("Get the employee Name from DB");
		String employee_id = empID;

		// Fetch the data into the map
		Map<String, String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);

		String firstName = employeeDetails.get("First_name");
		String middleName = employeeDetails.get("Middle_name");
		String lastName = employeeDetails.get("Last_name");

		String empFirstAndMiddleName = (firstName + " " + middleName).trim();

		ExtentManager.logStep("Verify the employee first and Middle name");
		Assert.assertTrue(homepage.verifyEmployeeFirstAndMiddleName(empFirstAndMiddleName),
				"Employee first and last name are not matching ");

		ExtentManager.logStep("Verify the employee last Name");
		Assert.assertTrue(homepage.verifyEmployeeLastName(lastName), "Employee last name not matching ");

		ExtentManager.logStep("DB validation completed");

	}

}
