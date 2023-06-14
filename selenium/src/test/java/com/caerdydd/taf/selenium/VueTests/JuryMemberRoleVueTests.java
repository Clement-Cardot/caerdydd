package com.caerdydd.taf.selenium.VueTests;

import org.junit.After;
import org.junit.Before;

import com.caerdydd.taf.selenium.TestTools.TafSeleniumTools;

public class JuryMemberRoleVueTests {

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

    // TODO
    
}
