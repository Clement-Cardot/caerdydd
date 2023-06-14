package com.caerdydd.taf.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.caerdydd.taf.selenium.TestTools.TafSeleniumTools;

public class TeamsTest {

    TafSeleniumTools tafSeleniumTools = new TafSeleniumTools();

	@Before
	public void setup() throws Exception {
		tafSeleniumTools.setupWebDriver();
		tafSeleniumTools.runSqlScript("../sql/tablesBdd.sql");
		tafSeleniumTools.runSqlScript("../sql/populateTables.sql");
	}

	@After
	public void cleanup() throws Exception {
		tafSeleniumTools.cleanupWebDriver();
		tafSeleniumTools.runSqlScript("../sql/tablesBdd.sql");
		tafSeleniumTools.runSqlScript("../sql/populateTables.sql");
	}

	@Test
	public void testImportEtudiants() throws Exception {
		tafSeleniumTools.openConnexionPage();
		tafSeleniumTools.connexion("bouvieal", "bouvieal");
		tafSeleniumTools.goToPage("Planification");
		tafSeleniumTools.importStudent();
		tafSeleniumTools.deconnexion();
	}

    @Test
	public void testEquipes() throws Exception {
		testImportEtudiants();
        tafSeleniumTools.openConnexionPage();
		tafSeleniumTools.connexion("rousseso", "rousseso");
		tafSeleniumTools.goToPage("Equipes");
		tafSeleniumTools.generateTeams(2);
		tafSeleniumTools.deconnexion();
		tafSeleniumTools.connexion("cardotcl", "cardotcl");
		tafSeleniumTools.goToPage("Equipes");
		tafSeleniumTools.applyInATeam(1);
		tafSeleniumTools.deconnexion();
	}

    
}
