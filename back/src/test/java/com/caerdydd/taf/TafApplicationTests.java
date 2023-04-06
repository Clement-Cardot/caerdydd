package com.caerdydd.taf;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.caerdydd.taf.controllers.TeamController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TafApplicationTests {

	@Autowired
	TeamController teamController;
	
	@Test
	public void contextLoads() {
		Assertions.assertThat(teamController).isNot(null);
	}

}
