package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpretor;
import org.springframework.samples.petclinic.sfg.LaurelWordProducer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("inner-class")
@SpringJUnitConfig(classes = {HearingInterpretorTestInnerClass.TestConfig.class})
class HearingInterpretorTestInnerClass {

  @Configuration
  @Profile("inner-class")
  static class TestConfig {

    @Bean
    public HearingInterpretor hearingInterpretor() {
      return new HearingInterpretor(new LaurelWordProducer());
    }

  }

  @Autowired
  HearingInterpretor interpretor;

  @Test
  public void whatIHeard() {
    String word = interpretor.whatIHeard();
    assertEquals("Laurel", word);
  }
}