package com.caerdydd.taf.selenium.VueTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.caerdydd.taf.selenium.TestTools.TafSeleniumTools;

public class PlanningRoleVueTests {
    
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
	public void testInitPagePlanification() throws Exception {
		tafSeleniumTools.openConnexionPage();
		tafSeleniumTools.connexion("bouvieal", "bouvieal");
		tafSeleniumTools.goToPage("Planification");
        tafSeleniumTools.checkPlanificationPage();
		tafSeleniumTools.deconnexion();
	}

	@Test
	public void testInitPageCalendrier() throws Exception {
		tafSeleniumTools.openConnexionPage();
		tafSeleniumTools.connexion("bouvieal", "bouvieal");
		tafSeleniumTools.goToPage("Calendrier");
        tafSeleniumTools.checkCalendrierPage();
		tafSeleniumTools.deconnexion();
	}
}
