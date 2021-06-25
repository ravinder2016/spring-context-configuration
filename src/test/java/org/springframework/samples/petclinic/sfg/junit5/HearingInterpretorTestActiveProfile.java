package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.BaseConfig;
import org.springframework.samples.petclinic.sfg.HearingInterpretor;
import org.springframework.samples.petclinic.sfg.LaurelConfig;
import org.springframework.samples.petclinic.sfg.YannyConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("yanny")
@SpringJUnitConfig(classes = {HearingInterpretorTestActiveProfile.TestConfig.class})
class HearingInterpretorTestActiveProfile {

  @ComponentScan("org.springframework.samples.petclinic.sfg")
  @Configuration
  static class TestConfig {

  }

  @Autowired
  HearingInterpretor interpretor;

  @Test
  void whatIHeard() {
    String word = interpretor.whatIHeard();
    assertEquals("Yanny", word);
  }
}