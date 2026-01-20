package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {
	private ActionDriver actionDriver;
	
	//Define Locator using By class
	
	private By usernameFiled=By.name("username");
	private By passwordFiled=By.name("password"); 
	private By loginButton=By.xpath("//button[text()=' Login ']");
	private By errorMessage=By.xpath("//p[text()='Invalid credentials']");
	
	//Initialize the ActionDriver object by passing WebDriver Instance
	/*public LoginPage(WebDriver driver) {
		this.actionDriver=new ActionDriver(driver);
	}*/
	
	//Initialize the Action Driver Object by passing WebDriver instance
	public LoginPage(WebDriver driver) {
		this.actionDriver=BaseClass.getActionDriver();
	}
	
	
	//Method to perform Login
	public void login(String username, String password) {
		actionDriver.enterText(usernameFiled, username);
		actionDriver.enterText(passwordFiled, password);
		actionDriver.click(loginButton);
		
	}
	
	//Method to Check if Error message is displayed
	public boolean isErrorMessageDisplayed() {
		return actionDriver.isDisplayed(errorMessage);
	}
	
	//Method to get Error Message
	public String getErrorMessageText() {
		return actionDriver.getText(errorMessage);
	}
	
	//Verify Error message is correct or not -change return type to boolean 
	public boolean verifyErrorMessage(String expectedError) {
		return actionDriver.compareText(errorMessage, expectedError);
		
	}

}
