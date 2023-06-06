package com.caerdydd.taf.selenium;

import java.sql.DriverManager;
import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Dimension;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Assert;

public class TafTest {
    
    WebDriver driver;
	String websiteUrl="http://localhost:4200/taf/#";
	String mariaDBUrl="jdbc:mariadb://localhost:3306/ProjetGL";
	String username="root";
	String password="root";

	String projectDir = System.getProperty("user.dir");

	public void setupWebDriver() throws Exception{

		ChromeOptions options = new ChromeOptions();

		// Pour debug sur votre serveur local, mettez "local" à la place de "dev" ci dessous
		String profil = "local";

		System.out.println("PROFIL SELECTIONNE : "+ profil);

		switch(profil) {
			/*
			* PROFIL SERVEUR TOMCAT DEV
			*/
			case "dev":
				websiteUrl="http://172.24.1.10:8080/taf/#";
				mariaDBUrl="jdbc:mariadb://172.24.1.10:3306/ProjetGL";
				username="devuser";
				password="WAl_UPpmE27V4ixh";
				options.addArguments("--headless=new");
				break;

			/*
			* PROFIL SERVEUR TOMCAT LOCAL
			*/
			case "local" :
			default:
				websiteUrl="http://localhost:4200/taf/#";
				mariaDBUrl="jdbc:mariadb://localhost:3306/ProjetGL";
				username="root";
				password="root";
				// options.addArguments("--headless=new"); // Décommanter pour actver le headless en local
				break;
		}
		
		options.addArguments("--remote-allow-origins=*");
		this.driver = new ChromeDriver(options);
	}

	public void cleanupWebDriver() {
		this.driver.close();
	}

	public void runSqlScript(String scriptName) throws SQLException, FileNotFoundException {
		//Registering the Driver
		DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
		//Getting the connection
		Connection con = DriverManager.getConnection(mariaDBUrl, username, password);
		System.out.println("Connection established......");
		//Initialize the script runner
		ScriptRunner sr = new ScriptRunner(con);
		//Creating a reader object
		Reader reader = new BufferedReader(new FileReader(scriptName));
		//Running the script
		sr.runScript(reader);
	}

	public void openConnexionPage() {
		//Interactions avec Selenium
		this.driver.get(this.websiteUrl);
		driver.manage().window().setSize(new Dimension(1098, 875));
		
		//Assertions
		Assert.assertEquals("Login - Taf", driver.getTitle());
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
		Assert.assertEquals("Tableau de bord - Taf", driver.getTitle());
  }

	public void deconnexion() {
		//Test if the menu bar is open
		if(!driver.findElement(By.cssSelector(".mdc-icon-button > .mat-mdc-button-touch-target")).isDisplayed()) {
			driver.findElement(By.cssSelector(".mdc-icon-button > .mat-mdc-button-touch-target")).click();
		}
		// Click on the "Déconnexion" button
		driver.findElement(By.cssSelector(".mdc-button__label")).click();
		//Assertions
		Assert.assertEquals("Login - Taf", driver.getTitle());
	}

	public void goToPage(String pageName) throws InterruptedException {
		//Test if the menu bar is open
		if(!driver.findElement(By.tagName("mat-sidenav")).isDisplayed()) {
			// Click on the page button
			driver.findElement(By.cssSelector(".mdc-icon-button > .mat-mdc-button-touch-target")).click();
		}

		Thread.sleep(250);

		// Click on the page button
		driver.findElement(By.linkText(pageName)).click();
		//Assertions
		Assert.assertEquals(pageName+" - Taf", driver.getTitle());
	}

	public void generateTeams(int teamPairsNumber) throws InterruptedException {
		try { 
			driver.findElement(By.cssSelector("#generateTeamsButton > .mdc-button__label")).click();
		}
		catch (Exception e) {

		}
		driver.findElement(By.cssSelector(".labelForm")).click();
		// Enter the number of team pairs
		driver.findElement(By.id("inputNumberTeamPairs")).sendKeys(String.valueOf(teamPairsNumber));
		// Click on the "Générer des équipes" button
		driver.findElement(By.cssSelector("#buttonCreateTeam > .mdc-button__label")).click();
		//Assertions
		Assert.assertTrue(driver.findElement(By.cssSelector(".mat-mdc-snack-bar-action > .mdc-button__label")).isDisplayed());
	}

	public void applyInATeam(int teamId) {
		driver.findElement(By.id("buttonJoinTeam"+teamId)).click();
		Assert.assertTrue(driver.findElement(By.cssSelector(".mat-mdc-cell:nth-child(2)")).isDisplayed());
		
	}

	public void importStudent() throws InterruptedException {

		// Add path to upload file
		String path = projectDir + "\\..\\demo-resources\\students.csv";
		String normalizedPath = Path.of(path).normalize().toString();
		driver.findElement(By.cssSelector("#ngx-mat-file-input-1 > input")).sendKeys(normalizedPath);
		
		// Click on Upload
		driver.findElement(By.cssSelector("#studentsForm > button")).click();

		// Wait for snack bar
		for (int i = 0; i < 10; i++) {
			try {
				driver.findElement(By.cssSelector(".mat-mdc-snack-bar-action > .mdc-button__label"));
				return;
			} catch (Exception e) {
				// Nothing here
			}
			Thread.sleep(1000);
		}
		fail("Snack bar not found after importing students !");
	}
}