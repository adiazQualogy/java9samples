package com.qualogy.example.react;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ReactSample {

  private static final Logger LOGGER = Logger.getLogger(ReactSample.class.getName());

  private static KeyPressedPublisher publisher = new KeyPressedPublisher();
  private static List<KeyPressedSubscriber> subscribers = new ArrayList<>();

  public static void main(String[] args) {
    logWelcomeMessage();

    addSubscriber();
    addSubscriber();

    publishMessages();
    cancelSubscriptions();
  }

  private static void addSubscriber() {
    KeyPressedSubscriber keyPressedSubscriber = getKeyPressedSubscriber();
    publisher.subscribe(keyPressedSubscriber);
    subscribers.add(keyPressedSubscriber);
  }

  private static KeyPressedSubscriber getKeyPressedSubscriber() {
    KeyPressedSubscriber subscriber = null;
    try {
      subscriber = new KeyPressedSubscriber();
      KeyPressedSubscription subscription = new KeyPressedSubscription(subscriber);
      subscriber.onSubscribe(subscription);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return subscriber;
  }

  private static void publishMessages() {
    Scanner sc = new Scanner(System.in);
    String input = null;

    while (!"EXIT".equals(input)) {

      if ("ADD".equals(input)) {
        addSubscriber();
      } else if (input != null) {
        for (KeyPressedSubscriber subscriber : subscribers) {
          subscriber.onNext(input);
        }
      }

      input = sc.nextLine();
    }
  }

  private static void cancelSubscriptions() {
    publisher.cancelAllSubscriptions();
  }

  private static void logWelcomeMessage() {
    LOGGER.info("\n#######################"
        + "\n# Type any text to add send it to the different subscribers. "
        + "\n# Type ADD to add a new subscriber. "
        + "\n# Type EXIT to quit.\n"
        + "#############################################");
  }
}
