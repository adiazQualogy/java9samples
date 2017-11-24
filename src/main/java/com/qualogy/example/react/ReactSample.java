package com.qualogy.example.react;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class ReactSample {

  private static final Logger LOGGER = Logger.getLogger(ReactSample.class.getName());

  private static KeyPressedPublisher publisher = new KeyPressedPublisher();
  private static List<KeyPressedSubscriber> subscribers = new ArrayList<>();

  public static void main(String[] args) {
    addSubscriber();
    addSubscriber();
    publishMessages();
  }

  private static void addSubscriber() {
    KeyPressedSubscriber keyPressedSubscriber = getKeyPressedSubscriber();
    publisher.subscribe(keyPressedSubscriber);
    subscribers.add(keyPressedSubscriber);
  }

  private static void publishMessages() {
    LOGGER.info("\n#######################"
        + "\n# Type any text to add send it to the different subscribers. "
        + "\n# Type ADD to add a new subscriber. "
        + "\n# Type EXIT to quit.\n"
        + "#############################################");
    Scanner sc = new Scanner(System.in);
    String input = "";

    while (!"EXIT".equals(input)) {
      input = sc.nextLine();

      if ("ADD".equals(input)) {
        addSubscriber();
      } else {
        for (KeyPressedSubscriber subscriber : subscribers) {
          subscriber.onNext(input);
        }
      }
    }
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
}
