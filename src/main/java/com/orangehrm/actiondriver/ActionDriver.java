package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class ActionDriver {
	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
	}

	// Method to Click on Element
	public void click(By by) {
		String elementDescritpion = getElementDescription(by);
		try {
			waitForElementToBeClickable(by);
			applyBorder(by, "green");
			driver.findElement(by).click();
			ExtentManager.logStep("Clicked an Element: " + elementDescritpion);
			logger.info("Element Clicked --> " + elementDescritpion);
		} catch (Exception e) {
			applyBorder(by, "red");
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to Click Element",
					elementDescritpion + "_Unable To Click");
			logger.error("Unable to Click element :" + e.getMessage());
		}
	}

	// Method to Enter Text -Updated this function to minimize code duplication
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			// driver.findElement(by).clear();
			// driver.findElement(by).sendKeys(value);
			WebElement element = driver.findElement(by);
			element.clear();
			element.sendKeys(value);
			logger.info("Entered Text into " + getElementDescription(by) + "---> " + value);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to Enter the value: " + e.getMessage());
		}
	}

	// Method to get Text
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			return driver.findElement(by).getText();
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to get Text: " + e.getMessage());
			return "";
		}
	}

	// Method to Compare two Text --Change return type to boolean
	public boolean compareText(By by, String expectedValue) {
		try {
			waitForElementToBeVisible(by);
			String actualValue = driver.findElement(by).getText();
			if (expectedValue.equals(actualValue)) {
				applyBorder(by, "green");
				logger.info("Texts are Matching: " + actualValue + " equals " + expectedValue);
				ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Compare Text",
						"Text Verified Successfully! " + actualValue + " equals " + expectedValue);
				return true;
			} else {
				applyBorder(by, "red");
				ExtentManager.logFailure(BaseClass.getDriver(), "Compare Text",
						"Text Verified Failed! " + actualValue + " not equals " + expectedValue);
				logger.info("Texts are not Matching: " + actualValue + " not equals " + expectedValue);
				return false;
			}
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to Compare Text: " + e.getMessage());
			return false;
		}

	}

	// Method to verify that weather a element is displayed or not
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by, "green");
			logger.info("Element is displayed " + getElementDescription(by));
			ExtentManager.logStep("Element is Displayed: " + getElementDescription(by));
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element is Displayed.",
					"Element is Displayed: " + getElementDescription(by));
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Element is not Displayed: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Element is not Displayed",
					"Element is not Displayed: " + getElementDescription(by));
			return false;
		}
	}

	// Scroll to an Element
	public void scrollToElement(By by) {
		try {
			applyBorder(by, "green");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguements[0],scrollIntoView(true);", element);
		} catch (Exception e) {
			applyBorder(by, "red");
			logger.error("Unable to locate Element: " + e.getMessage());
		}
	}

	// Wait for the page to load
	public void waitForPageLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec)).until(WebDriver -> ((JavascriptExecutor) WebDriver))
					.executeScript("return document.readyState").equals("Complete");
			logger.info("Page Loaded Successfully.");
		} catch (Exception e) {
			logger.error("Page didn't Load within: " + timeOutInSec + " seconds. Exception: " + e.getMessage());
		}
	}

	// Wait for Element to be Clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element is not Clickable: " + e.getMessage());
		}
	}

	// Wait for Element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			logger.error("Element is not Visible: " + e.getMessage());
		}
	}

	// Method to get the Description of Web Element
	public String getElementDescription(By locator) {
		// Check driver or locator null to avoid nullPointerException
		if (driver == null) {
			return "Driver is null";
		}
		if (locator == null) {
			return "Locator is null";
		}

		try {
			// find the element using the locator
			WebElement element = driver.findElement(locator);

			// Get Element attributes
			String id = element.getDomAttribute("id");
			String name = element.getDomAttribute("name");
			String text = element.getText();
			String ClassName = element.getDomAttribute("class");
			String placeholder = element.getDomAttribute("placeholer");

			// Return the description based on element
			if (isNotEmpty(name)) {
				return "Element with name: " + name;
			} else if (isNotEmpty(id)) {
				return "Element with Id: " + id;
			} else if (isNotEmpty(text)) {
				return "Element having text: " + truncate(text, 50);
			} else if (isNotEmpty(ClassName)) {
				return "Element with class Name: " + ClassName;
			} else if (isNotEmpty(placeholder)) {
				return "Element with placeholder: " + placeholder;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to Describe the Element");
		}
		return "Unable to Describe the Element";

	}

	// Utility Method to check a String is not empty and not null
	private boolean isNotEmpty(String Value) {
		return Value != null && !Value.isEmpty();
	}

	// Utility Method to truncate the long string
	private String truncate(String value, int maxlenght) {
		if (value == null || value.length() <= maxlenght) {
			return value;
		}
		return value.substring(0, maxlenght) + "...";

	}

	// Utility Method to Border an Element
	public void applyBorder(By by, String color) {
		try {
			// Locate the element
			WebElement element = driver.findElement(by);
			// Apply the Border
			String script = "arguments[0].style.border='3px solid " + color + "'";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(script, element);
			logger.info("Applied the Border with color " + color + " to element: " + getElementDescription(by));
		} catch (Exception e) {
			logger.warn("Failed to apply the Border to an element: " + getElementDescription(by), e.getMessage());

		}

	}

}
