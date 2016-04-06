package main

import org.avaje.jettyrunner.JettyRun
import java.io.IOException

object RunApp {

  @Throws(IOException::class)
  @JvmStatic fun main(args: Array<String>) {

    System.setProperty("disableTestProperties", "true");

    val jetty = JettyRun()
    jetty.httpPort = 9090
    jetty.runServer()
  }

}
