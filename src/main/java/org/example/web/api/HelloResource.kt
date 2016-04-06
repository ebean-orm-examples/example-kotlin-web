package org.example.web.api

import org.example.domain.Customer
import org.example.extension.loggerFor
import org.example.service.LoadExampleData
import javax.inject.Singleton
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Customer web resource.
 */
@Singleton
@Path("/customerRes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
open class HelloResource {

  private val logger = loggerFor(javaClass)

  //@Inject
  constructor() {
  }


  @Produces(MediaType.TEXT_PLAIN)
  @GET
  fun hello(): String {

    logger.debug("plain boring hello ...")
    return "Hello"
  }

  @GET
  @Path("/all")
  fun asBean(): String {

    logger.debug("all ...")
    val all = Customer.find.all()

    return Customer.db().json().toJson(all);
  }

  @GET
  @Path("/init")
  fun useData(): String {

    LoadExampleData().load()

    return "loaded"
  }

}
