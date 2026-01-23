package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	private ActionDriver actionDriver;

	// Define Locator using By class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text()='Logout']");
	private By OrangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

	private By pimTab = By.xpath("//span[text()='PIM']");
	private By employeeSearch = By
			.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div/div/div/input");
	private By searchButton = By.xpath("//button[text()=' Search ']");
	private By empFirstAndMiddleName = By.xpath("//div[@class='oxd-table-card']/div/div[3]");
	private By empLastName = By.xpath("//div[@class='oxd-table-card']/div/div[4]");

	// Initialize the ActionDriver object by passing WebDriver Instance
	/*
	 * public HomePage(WebDriver driver) { this.actionDriver = new
	 * ActionDriver(driver); }
	 */

	// Initialize the Action Driver Object by passing WebDriver instance
	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}

	// Method to verify if admin tab is visible
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}

	// Method to verify if OrangeHRM logo is visible
	public boolean verifyOrangeHRMlog() {
		return actionDriver.isDisplayed(OrangeHRMLogo);
	}

	// Method to Open PIM tab
	public void clickOnPIM() {
		actionDriver.click(pimTab);
	}

	// employee Search
	public void employeeSearch(String value) {
		actionDriver.enterText(employeeSearch, value);
		actionDriver.click(searchButton);
		actionDriver.scrollToElement(empFirstAndMiddleName);
	}

	// Verify employee first and middle name
	public boolean verifyEmployeeFirstAndMiddleName(String firstAndMiddleNameFromDB) {
		return actionDriver.compareText(empFirstAndMiddleName, firstAndMiddleNameFromDB);

	}
	
	// Verify employee first and middle name
		public boolean verifyEmployeeLastName(String empLastNameFromDB) {
			return actionDriver.compareText(empLastName, empLastNameFromDB);

		}

	// Method to perform Logout Operations
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);
	}

}
