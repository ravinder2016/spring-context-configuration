package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.HearingInterpretor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static junit.framework.Assert.assertEquals;

@ActiveProfiles("externalized")
@SpringJUnitConfig(classes = PropertiesTest.TestConfig.class)
@TestPropertySource("classpath:yanny.properties")
public class PropertiesTest {

  @Configuration
  @ComponentScan("org.springframework.samples.petclinic.sfg")
  static class TestConfig {

  }

  @Autowired
  HearingInterpretor interpretor;

  @Test
  public void whatIHeard() {
    String word = interpretor.whatIHeard();
    assertEquals("Yanny", word);
  }
}
