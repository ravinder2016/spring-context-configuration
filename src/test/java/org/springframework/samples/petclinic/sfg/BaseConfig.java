package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("base-test")
public class BaseConfig {

  @Bean
  HearingInterpretor hearingInterpretor(WordProducer producer) {
    System.out.println("BaseConfig - " + producer);
    return new HearingInterpretor(producer);
  }

}
