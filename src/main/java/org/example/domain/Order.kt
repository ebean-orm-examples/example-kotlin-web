package org.example.domain;

import com.avaje.ebean.annotation.DocCode
import com.avaje.ebean.annotation.DocEmbedded
import com.avaje.ebean.annotation.DocStore
import org.example.domain.finder.OrderFinder
import java.sql.Date
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * Order entity bean.
 */
@DocStore
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

  @DocCode
  var status: Status = Status.NEW;

  var orderDate: LocalDate? = null;

  var shipDate: LocalDate? = null;

  @DocEmbedded(doc = "id,name")
  @ManyToOne @NotNull
  var customer: Customer? = null;

  @ManyToOne
  var shippingAddress: Address? = null;

  @DocEmbedded(doc = "*,product(id,sku,name)")
  @OneToMany(mappedBy = "order", cascade = arrayOf(CascadeType.PERSIST))
  @OrderBy("id asc")
  var details: MutableList<OrderDetail> = ArrayList();


}
