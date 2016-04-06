package org.example.web.api

import com.avaje.ebean.EbeanServer
import com.avaje.ebean.Query
import com.avaje.ebean.text.json.JsonContext
import com.avaje.ebean.text.json.JsonWriteOptions
import org.example.web.BeanValidationException
import org.slf4j.LoggerFactory

import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
abstract class BaseResource<T> {

  protected val server: EbeanServer

  protected val beanType: Class<T>

  protected var log = LoggerFactory.getLogger(javaClass)

  protected val jsonContext: JsonContext

  protected var updateReturnProperties = "id,whenUpdated,version"

  @Inject
  constructor(server: EbeanServer, beanType: Class<T>) {
    this.server = server;
    this.beanType = beanType;
    this.jsonContext = server.json()
  }

  @GET
  fun all(): List<T> {
    val query = server.find(beanType).order().asc("id")
    return query.findList()
  }

  @GET
  @Path("/{id}")
  fun getById(@PathParam("id") id: String): T? {

    val query = server.find(beanType).setId(id)//.select("*")
    applyGetByIdPredicates(query)
    return query.findUnique()
  }

  /**
   * Apply predicates and order by clauses to the query as necessary.
   */
  protected fun applyGetByIdPredicates(query: Query<T>) {

  }

  protected fun sanitiseBean(bean: T) {

  }

  protected fun validateBean(bean: T) {

  }
  @POST
  fun insert(@Context uriInfo: UriInfo, bean: T): Response {

    try {
      validateBean(bean);
      sanitiseBean(bean)

      server.save(bean)
      val id = server.getBeanId(bean)

      val ub = uriInfo.absolutePathBuilder
      val createdUri = ub.path("" + id).build()

      postInsert(bean)

      return Response.created(createdUri).entity(bean).build()

    } catch (e: BeanValidationException) {
      log.info("validation errors " + e)
      return Response.ok("TODO: BeanValidationException").build()
    }

  }

  /**
   * Override to lookup important related objects.
   */
  protected fun postInsert(bean: T) {

  }

  @PUT
  @Path("/{id}")
  fun update(@PathParam("id") id: String, bean: T): Response {
    try {
      validateBean(bean);
      sanitiseBean(bean)

      server.update(bean)
      postUpdate(bean)

      val beanUpdateOptions = JsonWriteOptions.parsePath("($updateReturnProperties)")
      val json = jsonContext.toJson(bean, beanUpdateOptions)
      return Response.ok(json).build()

    } catch (e: BeanValidationException) {
      log.info("validation errors " + e)
      return Response.ok("TODO: BeanValidationException").build()
    }

  }

  /**
   * Override to lookup important related objects etc.
   */
  protected fun postUpdate(bean: T) {

  }

  /**
   * Delete just using the Id. Note that this doesn't take into account
   * optimistic locking.
   *
   *
   * Also consume Application/XML as AngularJS will send that for some browsers.
   *
   */
  @Consumes(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
  @DELETE
  @Path("/{id}")
  fun delete(@PathParam("id") id: String): Response {
    return performDelete(id)
  }

  /**
   * Delete using a POST to get around issues with AngularJS sending the wrong
   * content type.
   */
  @POST
  @Path("/{id}/delete")
  fun deletePath(@PathParam("id") id: String): Response {

    return performDelete(id)
  }

  protected fun performDelete(id: String): Response {
    server.delete(beanType, id)
    return Response.ok().build()
  }

  //  @Path("/query")
  //  @POST
  //  @Produces({ MediaType.APPLICATION_JSON })
  //  public String query(QueryRequest queryRequest) {
  //
  //    QueryResponse<T> fetchData = fetchData(queryRequest);
  //
  //    JsonWriteOptions writeOptions = null;// JsonWriteOptions.parsePath("(success,pageIndex,pageSize,list(*))");
  //    String val = jsonContext.toJson(fetchData, writeOptions);
  //
  //    // String val = jacksonService.serialise(fetchData);
  //    return val;
  //  }
  //
  //  /**
  //   * Apply predicates and order by clauses to the query as necessary.
  //   */
  //  protected void applyPredicates(QueryRequest request, Query<T> query) {
  //      if(request.getPredicate("order") != null){
  //        query.orderBy(request.getPredicate("order"));
  //      }
  //  }
  //
  //  /**
  //   * Fetch the data based on the paging information.
  //   */
  //  public QueryResponse<T> fetchData(QueryRequest request) {
  //
  //    log.info("process query request for {}", beanType);
  //    Query<T> query = server.find(beanType);
  //    if (request.hasPaging()) {
  //      query.setFirstRow(request.getFirstRow());
  //      query.setMaxRows(request.getPageSize());
  //    }
  //
  //    // Apply predicates and Order by clause to the query
  //    applyPredicates(request, query);
  //
  //    Future<Integer> rowCount = null;
  //    if (request.getPageSize() > 0 && request.getPageIndex() == 0) {
  //      rowCount = query.findFutureRowCount();
  //    }
  //
  //    List<T> list = query.findList();
  //
  //    return buildResponse(request, list, rowCount);
  //  }
  //
  //  protected QueryResponse<T> buildResponse(QueryRequest request, List<T> list, Future<Integer> rowCount) {
  //
  //    @SuppressWarnings("unchecked")
  //    boolean hasNext = ((BeanCollection<User>) list).hasMoreRows();
  //
  //    QueryResponse<T> response = new QueryResponse<T>();
  //    response.setList(list);
  //    response.setPageIndex(request.getPageIndex());
  //    response.setPageSize(request.getPageSize());
  //    response.setMore(hasNext);
  //
  //    if (rowCount != null) {
  //      try {
  //        response.setTotalCount(rowCount.get(60, TimeUnit.SECONDS));
  //      } catch (Exception e) {
  //        log.error("Error trying to fetch total Row count", e);
  //      }
  //    }
  //
  //    return response;
  //  }

}
