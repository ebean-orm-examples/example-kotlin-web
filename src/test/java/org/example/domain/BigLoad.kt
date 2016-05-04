package org.example.domain

import com.avaje.ebean.annotation.Transactional
import java.time.LocalDate
import java.util.*

/**
 * Loads lots of customers and orders.
 */
class BigLoad {

  val random = Random()

  @Transactional(batchSize = 100)
  fun loadOrders() {
    for (i in 0..100000) {
      loadOrder();
    }
  }

  private fun loadOrder() {

    val order = Order()
    order.status = orderStatus()
    order.customer = orderCustomer()
    order.orderDate = orderDate()
    order.shipDate = shipDate(order.orderDate)
    val orderLines = random.nextInt(20)
    for (i in 0..orderLines) {
      addDetail(order.details)
    }

    order.save()
  }

  private fun addDetail(details: MutableList<OrderDetail>) {

    val product = Product.ref(1 + random.nextInt(30000).toLong())
    val quantity = 1 + random.nextInt(20)
    val price = 1 + random.nextInt(100)

    details.add(OrderDetail(product, quantity, price.toDouble()))
  }

  private fun shipDate(orderDate: LocalDate?): LocalDate? {

    val plusDays = 1 + random.nextInt(12);
    return orderDate?.plusDays(plusDays.toLong());
  }

  private fun orderDate(): LocalDate {

    val month = 1 + random.nextInt(12);
    val day = 1 + random.nextInt(28);

    return LocalDate.of(2015, month, day)
  }

  private fun orderCustomer(): Customer {

    val idx = 1 + random.nextInt(100000);
    return Customer.ref(idx.toLong())
  }

  private fun orderStatus(): Order.Status {

    val idx = random.nextInt(4);
    when (idx) {
      0 -> return Order.Status.NEW
      1 -> return Order.Status.APPROVED
      2 -> return Order.Status.SHIPPED
    }
    return Order.Status.COMPLETE
  }

  @Transactional(batchSize = 500)
  fun loadProducts() {
    val start = System.currentTimeMillis()
    val letters = arrayOf("A","B","C","D","E","F","G","H","I","J")
    for (letter in letters) {
      for (pos in 3001..3100) {
        loadProduct(letter, pos)
      }
    }
    val exe = System.currentTimeMillis()  - start
    System.out.println("products loaded in millis: "+exe)
  }

  private fun loadProduct(letter: String, pos: Int) {

    val sku = letter + pos
    val name = sku

    Product(sku, name).save()
  }

  @Transactional(batchSize = 500)
  fun loadCustomers() {

    val start = System.currentTimeMillis()
    for (i in 100000..100020) {
      loadCustomer(i)
    }
    val exe = System.currentTimeMillis()  - start
    System.out.println("exe: "+exe)
  }

  fun loadCustomer(pos:Int) {

    val name = "big "+pos

    val customer = Customer(name)
    customer.save()
  }

}