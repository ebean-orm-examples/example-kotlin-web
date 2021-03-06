package org.example.web.api

import com.avaje.ebean.EbeanServer
import org.example.domain.Customer
import org.example.service.LoadExampleData
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.*
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
  @Path("/{id}")
  override fun getById(@PathParam("id") id: String): Customer? {
    return super.getById(id)
  }

  @GET
  @Path("/all")
  fun asBean(): String {

    log.debug("all ...")
    val all = Customer.find.all()

    return Customer.db().json().toJson(all);
  }

  @GET
  @Path("/queryCache")
  fun queryCache(): String {

    log.debug("queryCache ...")
    val all = Customer.find
        .where()
        .setUseQueryCache(true)
        .name.icontains("fiona")
        .order()
          .name.asc()
        .findList()

    return Customer.db().json().toJson(all);
  }

  @GET
  @Path("index/{id}")
  fun indexGetById(@PathParam("id") id: String): Customer? {

    return Customer
        .where().setId(id)
        .setUseDocStore(true)
        .setUseCache(false)
        .findUnique();
  }

  @GET
  @Path("/init")
  fun useData(): String {

    LoadExampleData().load()

    return "loaded"
  }

}
