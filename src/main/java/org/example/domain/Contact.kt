package org.example.domain;

import org.example.domain.finder.ContactFinder
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * Contact entity bean.
 */
@Entity
@Table(name = "be_contact")
class Contact() : BaseModel() {

  @Size(max = 50)
  var firstName: String? = null

  @Size(max = 50)
  var lastName: String? = null

  @Size(max = 200)
  var email: String? = null;

  @Size(max = 20)
  var phone: String? = null;

  @NotNull
  @ManyToOne(optional = false)
  var customer: Customer? = null;

  /**
   * Construct with firstName and lastName.
   */
  constructor(firstName: String, lastName: String) : this() {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  companion object : ContactFinder() {}
}
