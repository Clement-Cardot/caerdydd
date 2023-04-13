package com.caerdydd.taf;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.models.entities.RoleEntity;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.models.entities.TeamMemberEntity;
import com.caerdydd.taf.models.entities.UserEntity;

@SpringBootTest
public class TestConfig {
    private static SessionFactory sessionFactory = null;
    private Session session = null;

  @BeforeAll
  static void setup(){
    try {
      StandardServiceRegistry standardRegistry
          = new StandardServiceRegistryBuilder()
          .configure("hibernate-test.cfg.xml")
          .build();

      Metadata metadata = new MetadataSources(standardRegistry)
          .addAnnotatedClass(TeamEntity.class)
          .addAnnotatedClass(TeamMemberEntity.class)
          .addAnnotatedClass(UserEntity.class)
          .addAnnotatedClass(ProjectEntity.class)
          .addAnnotatedClass(RoleEntity.class)
          .getMetadataBuilder()
          .build();

      sessionFactory = metadata
          .getSessionFactoryBuilder().build();

    } catch (Throwable ex) {
      throw new ExceptionInInitializerError(ex);
    }
  }

  @BeforeEach
  void setupThis(){
    session = sessionFactory.openSession();
    session.beginTransaction();
  }

  @AfterEach
  void tearThis(){
    session.getTransaction().commit();
  }

  @AfterAll
  static void tear(){
    sessionFactory.close();
  }

  // @Test
  // void createSessionFactoryWithXML() {
  //   TeamEntity team = new TeamEntity();
  //   // team.setIdTeam(1);
  //   team.setName("Test Team");
  //   team.setProjectDev(new ProjectEntity("Test Project Dev", "Test Project Dev Description"));
  //   team.setProjectValidation(new ProjectEntity("Test Project Validation", "Test Project Validation Description"));

  //   Assertions.assertNull(team.getIdTeam());

  //   session.save(team);

  //   Assertions.assertNotNull(team.getIdTeam());
  // }
}
