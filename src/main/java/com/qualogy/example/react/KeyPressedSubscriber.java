package com.qualogy.example.react;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyPressedSubscriber implements Flow.Subscriber {

  private static final Logger LOGGER = Logger.getLogger(KeyPressedSubscriber.class.getName());

  private Flow.Subscription subscription;

  private Path filePath;

  public KeyPressedSubscriber() throws IOException {
    String fileName = System.currentTimeMillis() + ".txt";
    filePath = Files.write(Paths.get(fileName), "".getBytes(), StandardOpenOption.CREATE);
  }

  @Override
  public void onSubscribe(Flow.Subscription subscription) {
    this.subscription = subscription;
    this.subscription.request(3);
  }

  @Override
  public void onNext(Object item) {
    try {
      FileWriter fr = new FileWriter(filePath.toFile(), true);
      BufferedWriter br = new BufferedWriter(fr);
      PrintWriter pr = new PrintWriter(br);
      pr.println(item.toString());
      pr.close();
      br.close();
      fr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void onError(Throwable throwable) {
    LOGGER.log(Level.SEVERE, "onError = [" + throwable.getMessage() + "]");
  }

  @Override
  public void onComplete() {
    LOGGER.info(() -> "That is all, folks!");
    try {
      filePath.getFileSystem().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
