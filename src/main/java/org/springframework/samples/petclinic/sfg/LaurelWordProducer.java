package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class LaurelWordProducer implements WordProducer {
  @Override
  public String getWord() {
    return "Laurel";
  }
}
