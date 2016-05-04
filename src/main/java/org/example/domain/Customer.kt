package org.example.domain;

import com.avaje.ebean.annotation.Cache
import com.avaje.ebean.annotation.DocStore
import com.avaje.ebean.annotation.SoftDelete
import org.example.domain.finder.CustomerFinder
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * Customer entity bean.
 */
@DocStore
@Cache(enableQueryCache = true)
@Entity
@Table(name = "be_customer")
class Customer(

    // name is required property
    @NotNull @Size(max = 100)
    val name: String,

    @SoftDelete
    var deleted: Boolean = false,

    var registered: Date? = null,

    @Size(max = 1000)
    var comments: String? = null,

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var billingAddress: Address? = null,

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    var shippingAddress: Address? = null,

    @OneToMany(mappedBy = "customer", cascade = arrayOf(CascadeType.PERSIST))
    var contacts: MutableList<Contact> = ArrayList()

) : BaseModel() {

  companion object find : CustomerFinder() {}

  override fun toString(): String {
    return "customer(id:$id name:$name)";
  }

}
