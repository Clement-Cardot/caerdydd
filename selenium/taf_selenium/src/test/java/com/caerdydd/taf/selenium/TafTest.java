package com.caerdydd.taf.selenium;


import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.time.Duration;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Dimension;

public class TafTest {
    
    static WebDriver driver;
    String websiteUrl = "http://localhost:4200/#/";

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
	
	@Test
	@Order(1)
	public void openConnexionPage() {
		//Interactions avec Selenium
		TafTest.driver.get(websiteUrl);
		driver.manage().window().setSize(new Dimension(1098, 875));
		
		//Assertions
		Assertions.assertEquals("Login - Taf", driver.getTitle());
	}
	
  	public void connexion(String username, String password) {
		// Enter the username
		driver.findElement(By.id("inputUsername")).sendKeys(username);
		// Enter the password
		driver.findElement(By.id("inputPassword")).sendKeys(password);
		// Click on the submit button
		driver.findElement(By.cssSelector(".mdc-button__label")).submit();
		
		new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".mdc-icon-button > .mat-mdc-button-touch-target")));

		//Assertions
		Assertions.assertEquals("Dashboard - Taf", driver.getTitle());
  }

	public void goToPage(String pageName) {
		// Click on the page button
		driver.findElement(By.cssSelector(".mdc-icon-button > .mat-mdc-button-touch-target")).click();
		// Click on the page button
		driver.findElement(By.linkText(pageName)).click();
		//Assertions
		Assertions.assertEquals(pageName+" - Taf", driver.getTitle());
	}

	public void generateTeams(int teamPairsNumber) {
		// Click on the "Créer des équipes" button
		driver.findElement(By.cssSelector("#generateTeamsButton > .mdc-button__label")).click();
		// Enter the number of team pairs
		driver.findElement(By.id("inputNumberTeamPairs")).sendKeys("2");
		// Click on the "Générer des équipes" button
		driver.findElement(By.cssSelector("#buttonCreateTeam > .mdc-button__label")).click();
		//Assertions
		Assertions.assertTrue(driver.findElement(By.cssSelector(".mat-mdc-snack-bar-action > .mdc-button__label")).isDisplayed());
	}

	public void deconnexion() {
		if(!driver.findElement(By.cssSelector(".mdc-icon-button > .mat-mdc-button-touch-target")).isDisplayed()) {
			driver.findElement(By.cssSelector(".mdc-icon-button > .mat-mdc-button-touch-target")).click();
		}
		// Click on the "Déconnexion" button
		driver.findElement(By.cssSelector(".mdc-button__label")).click();
		//Assertions
		Assertions.assertEquals("Login - Taf", driver.getTitle());
	}

	public void applyInATeam(int numberTeam) {
		driver.findElement(By.id("buttonJoinTeam")).click();
		Assertions.assertTrue(driver.findElement(By.cssSelector(".mat-mdc-cell:nth-child(2)")).isDisplayed());
		
	}

	@Test
	@Order(2)
	public void testEquipes() {
		this.connexion("srousseau", "srousseau");
		this.goToPage("Equipes");
		this.generateTeams(2);
		this.deconnexion();
		this.connexion("pmartin", "pmartin");
		this.goToPage("Equipes");
		this.applyInATeam(2);
		this.deconnexion();
	}


}