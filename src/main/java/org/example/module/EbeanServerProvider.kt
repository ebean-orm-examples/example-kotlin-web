package org.example.module

import com.avaje.ebean.EbeanServer
import com.avaje.ebean.EbeanServerFactory
import com.avaje.ebean.config.ServerConfig
import com.google.inject.Provider
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Creates the default EbeanServer instance.
 *
 * This should be a singleton.
 */
class EbeanServerProvider : Provider<EbeanServer> {

  override fun get(): EbeanServer {

    val config = ServerConfig()
    config.name = "db"
    config.isDefaultServer = true
    config.loadFromProperties()

    return EbeanServerFactory.create(config);
  }
}