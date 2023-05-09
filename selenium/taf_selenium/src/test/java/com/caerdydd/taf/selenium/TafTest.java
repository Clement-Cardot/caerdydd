package com.caerdydd.taf.selenium;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Dimension;

public class TafTest {
    
    static WebDriver driver;
    String websiteUrl = "http://172.24.1.10:8080/taf/#/";

	@BeforeAll
	public static void setupWebDriver() {
		WebDriverManager.firefoxdriver().setup();
		FirefoxOptions options = new FirefoxOptions();
		TafTest.driver = new FirefoxDriver(options);
	}
	
	@AfterAll
	public static void cleanupWebDriver() {
		TafTest.driver.close();
	}
	
	/**
	 * This method tests the points 1 to 4 in the 
	 * Validation Scenario Connect to OpenProject
	 */
	@Test
	@Order(1)
	public void openConnexionPage() {
		//Interactions avec Selenium
		TafTest.driver.get(websiteUrl);
		driver.manage().window().setSize(new Dimension(1098, 875));
		
		//Assertions
		Assertions.assertTrue(driver.findElement(By.id("loginBox")).isDisplayed());
	}
	
	@Test
	@Order(2)
	public void connect() {
		driver.get("http://172.24.1.10:8080/taf/#/");
		// 2 | setWindowSize | 1098x875 | 
		driver.manage().window().setSize(new Dimension(1098, 875));
		// 3 | click | id=mat-input-0 | 
		driver.findElement(By.id("mat-input-0")).click();
		// 4 | type | id=mat-input-0 | srousseau
		driver.findElement(By.id("mat-input-0")).sendKeys("srousseau");
		// 5 | click | id=mat-input-1 | 
		driver.findElement(By.id("mat-input-1")).click();
		// 6 | mouseOver | css=.mdc-button__label | 
		{
		WebElement element = driver.findElement(By.cssSelector(".mdc-button__label"));
		Actions builder = new Actions(driver);
		builder.moveToElement(element).perform();
		}
		// 7 | type | id=mat-input-1 | srousseau
		driver.findElement(By.id("mat-input-1")).sendKeys("srousseau");
		// 8 | click | css=.mdc-button__label | 
		driver.findElement(By.cssSelector(".mdc-button__label")).click();

	}
}