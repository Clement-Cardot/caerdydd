package com.caerdydd.taf.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTeams {

    TafTest tafTest = new TafTest();

	@BeforeEach
	public void setup() throws Exception {
		tafTest.setupWebDriver();
		tafTest.runSqlScript("../../sql/tablesBdd.sql");
		tafTest.runSqlScript("../../sql/populateTables.sql");
	}

	@AfterEach
	public void cleanup() throws Exception {
		tafTest.cleanupWebDriver();
		tafTest.runSqlScript("../../sql/tablesBdd.sql");
	}

    @Test
	public void testEquipes() throws Exception {
        tafTest.openConnexionPage();
		tafTest.connexion("srousseau", "srousseau");
		tafTest.goToPage("Equipes");
		tafTest.generateTeams(2);
		tafTest.deconnexion();
		tafTest.connexion("pmartin", "pmartin");
		tafTest.goToPage("Equipes");
		tafTest.applyInATeam(1);
		tafTest.deconnexion();
	}
    
}
