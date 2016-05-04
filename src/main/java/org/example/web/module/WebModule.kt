package org.example.myapp.web.module

import com.google.inject.AbstractModule
import org.example.extension.loggerFor
import org.example.module.DbModule
import org.example.web.api.CountryResource
import org.example.web.api.CustomerResource
import org.example.web.api.HelloResource

/**
 * Main module binding the web and lower layers
 */
class WebModule : AbstractModule() {

  private val logger = loggerFor(javaClass)

  override fun configure() {

    logger.debug("configuring module ...")

    install(DbModule())
    bind(CountryResource::class.java).asEagerSingleton()
    bind(CustomerResource::class.java).asEagerSingleton()
    bind(HelloResource::class.java).asEagerSingleton()
  }

}