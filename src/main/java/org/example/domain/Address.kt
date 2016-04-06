package org.example.domain;

import com.avaje.ebean.Model
import org.example.domain.finder.AddressFinder
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Address entity bean.
 */
@Entity
@Table(name = "o_address")
class Address : BaseModel() {

  @Size(max = 100)
  var line1: String? = null;

  @Size(max = 100)
  var line2: String? = null;

  @Size(max = 100)
  var city: String? = null;

  @ManyToOne(optional = false)
  var country: Country? = null;

  companion object : AddressFinder() {}

}


