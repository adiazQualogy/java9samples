package com.qualogy.example.react;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.logging.Logger;

public class KeyPressedPublisher implements Flow.Publisher {

  private static final Logger LOGGER = Logger.getLogger(KeyPressedPublisher.class.getName());

  private List<KeyPressedSubscription> subscriptionList = new ArrayList<>();

  @Override
  public void subscribe(Flow.Subscriber subscriber) {
    KeyPressedSubscription subscription = new KeyPressedSubscription(subscriber);
    subscriptionList.add(subscription);
    subscriber.onSubscribe(subscription);

    LOGGER.info(() -> "New subscription (nr " + subscriptionList.size() + ")");
  }
}
