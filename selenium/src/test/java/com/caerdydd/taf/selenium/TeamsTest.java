package com.caerdydd.taf.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamsTest {

    TafTest tafTest = new TafTest();

	@Before
	public void setup() throws Exception {
		tafTest.setupWebDriver();
		tafTest.runSqlScript("../sql/tablesBdd.sql");
		tafTest.runSqlScript("../sql/populateTables.sql");
	}

	@After
	public void cleanup() throws Exception {
		tafTest.cleanupWebDriver();
		tafTest.runSqlScript("../sql/tablesBdd.sql");
		tafTest.runSqlScript("../sql/populateTables.sql");
	}

	@Test
	public void testImportEtudiants() throws Exception {
		tafTest.openConnexionPage();
		tafTest.connexion("bouvieal", "bouvieal");
		tafTest.goToPage("Planification");
		tafTest.importStudent();
	}

    @Test
	public void testEquipes() throws Exception {
        tafTest.openConnexionPage();
		tafTest.connexion("rousseso", "rousseso");
		tafTest.goToPage("Equipes");
		tafTest.generateTeams(2);
		tafTest.deconnexion();
		tafTest.connexion("pmartin", "pmartin");
		tafTest.goToPage("Equipes");
		tafTest.applyInATeam(1);
		tafTest.deconnexion();
	}

    
}
