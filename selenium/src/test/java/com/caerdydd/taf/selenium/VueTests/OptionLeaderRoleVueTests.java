package com.caerdydd.taf.selenium.VueTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.caerdydd.taf.selenium.TestTools.TafSeleniumTools;

public class OptionLeaderRoleVueTests {

    TafSeleniumTools tafSeleniumTools = new TafSeleniumTools();

    @Before
    public void before() throws Exception {
        tafSeleniumTools.runSqlScript("../sql/tablesBdd.sql");
        tafSeleniumTools.runSqlScript("../sql/populateTables.sql");

        tafSeleniumTools.setupWebDriver();
        tafSeleniumTools.openConnexionPage();
        tafSeleniumTools.connexion("rousseso", "rousseso");

        tafSeleniumTools.goToPage("Equipes");
        tafSeleniumTools.generateTeams(2);
    }

    @After
    public void after() throws Exception {
        tafSeleniumTools.deconnexion();
        tafSeleniumTools.cleanupWebDriver();
        tafSeleniumTools.runSqlScript("../sql/tablesBdd.sql");
		tafSeleniumTools.runSqlScript("../sql/populateTables.sql");
    }

    @Test
	public void testInitPageAdministration() throws Exception {
		tafSeleniumTools.goToPage("Administration");
        tafSeleniumTools.checkAdministrationPage();
	}

    @Test
	public void testInitPageNotes() throws Exception {
		tafSeleniumTools.goToPage("Notes");
        tafSeleniumTools.checkNotesPage();
	}

    @Test
	public void testInitPageSujets() throws Exception {
		tafSeleniumTools.goToPage("Sujets");
        tafSeleniumTools.checkSujetsPage();
	}

    @Test
	public void testInitPageCalendrier() throws Exception {
		tafSeleniumTools.goToPage("Calendrier");
        tafSeleniumTools.checkCalendrierPage();
	}

    @Test
	public void testInitPageEquipes() throws Exception {
        tafSeleniumTools.checkEquipesPage();
	}

    @Test
	public void testInitPageCorpsEnseignant() throws Exception {
		tafSeleniumTools.goToPage("Corps Enseignant");
        tafSeleniumTools.checkCorpsEnseignantPage();
	}
}
