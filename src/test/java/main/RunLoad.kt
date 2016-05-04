package main

import com.avaje.ebean.Ebean
import com.avaje.ebean.FetchConfig
import org.example.domain.Order
import org.example.domain.Product
import java.io.IOException
import java.time.LocalDate
import java.util.*

object RunLoad {

  @Throws(Exception::class)
  @JvmStatic fun main(args: Array<String>) {

    // go into Postgres DB rather than H2 which we are using to run unit tests
    System.setProperty("disableTestProperties", "true");

    val start = System.currentTimeMillis()

    runSomeQueries(2,2)
    runSomeQueries(8,10)
    System.out.println("---")
    System.out.println("---")
    runSomeQueries(30,10)

    //runSomeQueries(20,100)
    //runLoad()

    val exe = System.currentTimeMillis() - start
    System.out.println("done "+exe)

    Ebean.getDefaultServer().shutdown(true, false)
  }

  private fun runLoad() {
    //val load = BigLoad()
    //load.loadProducts()
    //load.loadCustomers();
    //load.loadOrders()

    //Product.db().docStore().indexAll(Product::class.java)
    //Product.db().docStore().indexAll(Customer::class.java)
    val query = Order.find.where()
        .id.greaterOrEqualTo(600000)
        .id.lessThan(700000)
        .query()

    Product.db().docStore().indexByQuery(query, 500)
  }

  private fun runSomeQueries(runCount:Int, queryCount:Int) {

    for (i in 0..runCount) {
      val asDocStore = (i % 2) == 0
      runQuerySet(queryCount, asDocStore)
    }

  }


  private fun runQuerySet(count: Int, asDocStore: Boolean) {

    val random = Random()
    val asDate = LocalDate.of(2015, 12, 1)
    //val asDate = LocalDate.of(2015, 1, 1)

    for (i in 0..count) {

      val days = random.nextInt(25)
      val useDate = asDate.plusDays(days.toLong());

      val start = System.currentTimeMillis()
      val hits = findNewOrdersForCustomer(asDocStore, useDate)
      val exe = System.currentTimeMillis() - start

      val storeType = if (asDocStore) "es" else "db"
      val millis = padMillis(exe)
      System.out.println("" + millis + " ," + storeType)
      //System.out.println("exe: " + millis + " " + storeType + " hits:" + hits + " useDate:" + useDate)
    }
  }

  private fun padMillis(exe: Long): String {

    if (exe > 100) return exe.toString()
    if (exe > 9) return " " + exe
    return "  " + exe
  }

  private fun findNewOrdersForCustomer(asDocStore: Boolean, useDate: LocalDate): Int {

    val query = Order.find.where()
        .status.`in`(Order.Status.NEW)
        .orderDate.after(useDate)
        .order()
          .orderDate.desc()
          //.customer.id.desc()

        .setMaxRows(100)
        .setLazyLoadBatchSize(100)
        .setUseDocStore(asDocStore)
        .query()

//    if (!asDocStore) {
//      query
//          .fetch("customer", "id,name")
//          .fetch("details", FetchConfig().query())
//          .fetch("details.product","id,sku,name")
//    }

    val orders = query.findList()
    for (order in orders) {
      order.details.size
    }
    return orders.size
  }

}
