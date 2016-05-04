package org.example.web.api

import com.avaje.ebean.EbeanServer
import org.example.domain.Country
import org.example.domain.Customer
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Customer web resource.
 */
@Singleton
@Path("/country")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CountryResource : BaseResource<Country> {

  @Inject
  constructor(server: EbeanServer) : super(server, Country::class.java){}

  @GET
  @Path("/{id}")
  override fun getById(@PathParam("id") id: String): Country? {
    return super.getById(id)
  }

  @GET
  @Path("/all")
  fun asBean(): String {

    log.debug("all ...")
    val all = Country.all()

    return Country.db().json().toJson(all);
  }

  @GET
  @Path("/queryCache")
  fun queryCache(): String {

    log.debug("queryCache ...")
    val all = Country
        .where()
        .setUseQueryCache(true)
          .name.asc()
        .findList()

    return Country.db().json().toJson(all);
  }

  @GET
  @Path("index/{id}")
  fun indexGetById(@PathParam("id") id: String): Country? {

    return Country
        .where().setId(id)
        .setUseDocStore(true)
        .findUnique();
  }

  @GET
  @Path("/indexAll")
  fun indexAll(): String {

    Country.db().docStore().indexAll(Country::class.java);

    Customer.db().docStore().indexAll(Customer::class.java);

    return "indexed";
  }
}
