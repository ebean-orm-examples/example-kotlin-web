package org.example.web.api

import com.avaje.ebean.EbeanServer
import org.example.domain.Customer
import org.example.extension.loggerFor
import org.example.service.LoadExampleData
import org.example.web.api.BaseResource
import javax.inject.Inject
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
@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CustomerResource : BaseResource<Customer> {

  @Inject
  constructor(server: EbeanServer) : super(server, Customer::class.java){}

  @GET
  @Path("/all")
  fun asBean(): String {

    log.debug("all ...")
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
