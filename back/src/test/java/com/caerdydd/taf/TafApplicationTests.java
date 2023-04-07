package com.caerdydd.taf;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caerdydd.taf.controllers.TeamController;
import com.caerdydd.taf.controllers.TeamMemberController;
import com.caerdydd.taf.controllers.UserController;

@SpringBootTest
class TafApplicationTests {

	@Autowired
	TeamController teamController;

	@Autowired
	TeamMemberController teamMemberController;

	@Autowired
	UserController userController;
	
	@Test
	public void contextLoads() {
		Assertions.assertThat(teamController).isNotNull();
		Assertions.assertThat(teamMemberController).isNotNull();
		Assertions.assertThat(userController).isNotNull();
	}

}
