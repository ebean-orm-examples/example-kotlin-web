package org.example.domain;

import com.avaje.ebean.Model
import com.avaje.ebean.annotation.DocStore
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size;


/**
 * Product entity bean.
 */
@DocStore
@Entity
@Table(name = "o_product")
class Product (

 @NotNull @Size(max = 20)
 var sku: String = "",

 @NotNull @Size(max = 100)
 var name: String = ""

) : BaseModel() {

  companion object : Model.Find<Long, Product>() {}

}
