package main;

import com.avaje.ebean.Ebean;

public class JavaMain {

  public static void main(String[] args) throws Exception {

    System.currentTimeMillis();
    try {
      Ebean.getDefaultServer();
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
}
