package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Component;

@Component
public class HearingInterpretor {

  private WordProducer wordProducer;

  public WordProducer getWordProducer() {
    return wordProducer;
  }

  public HearingInterpretor(final WordProducer wordProducer) {
    this.wordProducer = wordProducer;
  }

  public String whatIHeard() {
    return wordProducer.getWord();
  }

}
