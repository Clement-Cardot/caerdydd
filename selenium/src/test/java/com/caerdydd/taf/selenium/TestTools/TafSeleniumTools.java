package com.caerdydd.taf.selenium.TestTools;

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

public class TafSeleniumTools {
    
    WebDriver driver;
	String websiteUrl="http://localhost:4200/taf/#";
	String mariaDBUrl="jdbc:mariadb://localhost:3306/ProjetGL";
	String username="root";
	String password="root";

	String projectDir = System.getProperty("user.dir");

	// Click on element
	private void clickOnElementById(String id){
		new WebDriverWait(driver, Duration.ofSeconds(5))
			.until(ExpectedConditions.elementToBeClickable(By.id(id)))
			.click();
	}

	private void clickOnElementByClassName(String className){
		new WebDriverWait(driver, Duration.ofSeconds(5))
			.until(ExpectedConditions.elementToBeClickable(By.className(className)))
			.click();
	}

	private void clickOnElementByCSSSelector(String cssSelector){
		new WebDriverWait(driver, Duration.ofSeconds(5))
			.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)))
			.click();
	}

	private void clickOnElementByLinkText(String linkText){
		new WebDriverWait(driver, Duration.ofSeconds(5))
			.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)))
			.click();
	}

	// Send keys to element
	private void sendKeysToElementById(String id, String keys){
		new WebDriverWait(driver, Duration.ofSeconds(5))
			.until(ExpectedConditions.elementToBeClickable(By.id(id)))
			.sendKeys(keys);
	}

	private void sendKeysToElementByClassName(String className, String keys){
		new WebDriverWait(driver, Duration.ofSeconds(5))
			.until(ExpectedConditions.elementToBeClickable(By.className(className)))
			.sendKeys(keys);
	}

	// Click on element
	private void isDisplayedElementById(String id){
		Assert.assertTrue(
			new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.elementToBeClickable(By.id(id)))
				.isDisplayed()
		);
	}

	private void isDisplayedElementByClassName(String className){
		Assert.assertTrue(
			new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.elementToBeClickable(By.className(className)))
				.isDisplayed()
		);
	}

	private void isDisplayedElementByCSSSelector(String cssSelector){
		Assert.assertTrue(
			new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)))
				.isDisplayed()
		);
	}

	private void isDisplayedElementByTagName(String tagName){
		Assert.assertTrue(
			new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.elementToBeClickable(By.tagName(tagName)))
				.isDisplayed()
		);
	}

	// Setup & Cleanup
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

	// Init BDD
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

	// Connexion & Deconnection
	public void openConnexionPage() {
		//Interactions avec Selenium
		this.driver.get(this.websiteUrl);
		driver.manage().window().setSize(new Dimension(1098, 875));
		
		//Assertions
		Assert.assertEquals("Login - Taf", driver.getTitle());
	}
	
  	public void connexion(String username, String password) {
		// Enter the username
		sendKeysToElementById("inputUsername", username);

		// Enter the password
		sendKeysToElementById("inputPassword", password);

		// Click on the submit button
		driver.findElement(By.cssSelector(".mdc-button__label")).submit();
		
		new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".mdc-icon-button > .mat-mdc-button-touch-target")));

		//Assertions
		Assert.assertEquals("Tableau de bord - Taf", driver.getTitle());
  }

	public void deconnexion() {
		//Test if the menu bar is open
		if(!driver.findElement(By.tagName("mat-sidenav")).isDisplayed()) {
			// Click on the page button
			clickOnElementByCSSSelector(".mdc-icon-button > .mat-mdc-button-touch-target");
		}

		// Click on the "Déconnexion" button
		clickOnElementByCSSSelector(".mdc-button__label");

		//Assertions
		Assert.assertEquals("Login - Taf", driver.getTitle());
	}

	// Pages
	public void goToPage(String pageName) throws InterruptedException {
		//Test if the menu bar is open
		
		if(!driver.findElement(By.tagName("mat-sidenav")).isDisplayed()) {
			// Click on the page button
			clickOnElementByCSSSelector(".mdc-icon-button > .mat-mdc-button-touch-target");
		}

		// Click on the page button
		clickOnElementByLinkText(pageName);

		//Assertions
		Assert.assertEquals(pageName+" - Taf", driver.getTitle());
	}

	public void checkPlanificationPage() {
		isDisplayedElementById("studentImportCard");
		isDisplayedElementById("consultingImportCard");
		isDisplayedElementById("juryCreationCard");
		isDisplayedElementById("createPresentationCard");
	}

	public void checkCalendrierPage() throws Exception {
		isDisplayedElementByTagName("mwl-calendar-week-view");
	}

	public void checkAdministrationPage() throws Exception {
		// TODO : Page Administration à faire
		Assert.assertTrue(true);
	}

	public void checkNotesPage() throws Exception {
		isDisplayedElementById("markPanel1");
		isDisplayedElementById("markPanel2");
		isDisplayedElementById("markPanel3");
		isDisplayedElementById("markPanel4");
	}

	public void checkEquipesPage() throws Exception {
		isDisplayedElementById("teamCard1");
		isDisplayedElementById("teamCard2");
		isDisplayedElementById("teamCard3");
		isDisplayedElementById("teamCard4");
	}

    public void checkCorpsEnseignantPage() throws Exception {
		isDisplayedElementByCSSSelector("#sidenav > mat-sidenav-content > app-teaching-staff-page > app-define-specialty");
		isDisplayedElementByClassName("listTS");
    }

    public void checkSujetsPage() throws Exception {
		isDisplayedElementById("subjectCard1");
		isDisplayedElementById("subjectCard2");
		isDisplayedElementById("subjectCard3");
		isDisplayedElementById("subjectCard4");
    }

	// Features
	public void generateTeams(int teamPairsNumber) throws InterruptedException {
		try {
			clickOnElementByClassName("generateTeamsButton");
		}
		catch (Exception e) {

		}
		clickOnElementById("nbPaire");
		// Enter the number of team pairs
		sendKeysToElementById("inputNumberTeamPairs", String.valueOf(teamPairsNumber));
		// Click on the "Générer des équipes" button
		clickOnElementByCSSSelector("#buttonCreateTeam > .mdc-button__label");
		//Assertions
		isDisplayedElementByCSSSelector(".mat-mdc-snack-bar-action > .mdc-button__label");
	}

	public void applyInATeam(int teamId) {
		clickOnElementById("buttonJoinTeam"+teamId);
		isDisplayedElementByCSSSelector(".mat-mdc-cell:nth-child(2)");
	}

	public void importStudent() throws InterruptedException {

		// Add path to upload file
		String path = projectDir + "\\..\\demo-resources\\students.csv";
		String normalizedPath = Path.of(path).normalize().toString();
		driver.findElement(By.cssSelector("#ngx-mat-file-input-1 > input")).sendKeys(normalizedPath);
		
		// Click on Upload
		clickOnElementByCSSSelector("#studentsForm > button");

		// Wait for snack bar
		isDisplayedElementByCSSSelector(".mat-mdc-snack-bar-action > .mdc-button__label");
	}

}