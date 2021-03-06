package com.qualogy.example.react;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.logging.Logger;

public class KeyPressedSubscription implements Flow.Subscription {

  private static final Logger LOGGER = Logger.getLogger(KeyPressedSubscription.class.getName());
  private List<String> publishedMessages = new LinkedList<>();
  private final Flow.Subscriber subscriber;

  KeyPressedSubscription(Flow.Subscriber subscriber) {
    this.subscriber = subscriber;
  }

  @Override
  public void request(long numberOfMessages) {
    //TODO Use the numberOfMessages parameter to publish the requested ones.

    for (String publishedMessage : publishedMessages) {
      subscriber.onNext(publishedMessage);
    }
  }

  @Override
  public void cancel() {
    LOGGER.info(() -> "cancel");
  }
}
