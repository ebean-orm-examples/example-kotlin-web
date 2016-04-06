package org.example.domain;

import com.avaje.ebean.annotation.CacheStrategy
import com.avaje.ebean.annotation.SoftDelete
import org.example.domain.finder.CustomerFinder
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

/**
 * Customer entity bean.
 */
@CacheStrategy//(useBeanCache = false)
@Entity
@Table(name = "be_customer")
class Customer() : BaseModel() {

  @SoftDelete
  var deleted: Boolean = false;

  @Size(max = 100)
  var name: String? = null;

  var registered: Date? = null;

  @Size(max = 1000)
  var comments: String? = null;

  @ManyToOne(cascade = arrayOf(CascadeType.ALL))
  var billingAddress: Address? = null;

  @ManyToOne(cascade = arrayOf(CascadeType.ALL))
  var shippingAddress: Address? = null;

  @OneToMany(mappedBy = "customer", cascade = arrayOf(CascadeType.PERSIST))
  var contacts: MutableList<Contact> = ArrayList();

  constructor (name: String) : this() {
    this.name = name;
  }

  companion object find : CustomerFinder() {}

  override fun toString(): String {
    return "customer(id:$id name:$name)";
  }

}
