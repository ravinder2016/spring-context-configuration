package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("yanny")
@Service
@Primary
public class YannyWordProducer implements WordProducer{
  @Override
  public String getWord() {
    return "Yanny";
  }
}
