package org.example.domain;

import org.example.domain.finder.OrderFinder
import java.sql.Date
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * Order entity bean.
 */
@Entity
@Table(name = "o_order")
class Order : BaseModel() {

  companion object find : OrderFinder() {}

  enum class Status {
    NEW,
    APPROVED,
    SHIPPED,
    COMPLETE
  }

  var status: Status = Status.NEW;

  var orderDate: Date? = null;

  var shipDate: Date? = null;

  @ManyToOne @NotNull
  var customer: Customer? = null;

  @ManyToOne
  var shippingAddress: Address? = null;

  @OneToMany(mappedBy = "order", cascade = arrayOf(CascadeType.PERSIST))
  @OrderBy("id asc")
  var details: MutableList<OrderDetail> = ArrayList();


}
