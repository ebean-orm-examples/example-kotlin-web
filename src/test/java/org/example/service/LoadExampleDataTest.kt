package org.example.service

import org.assertj.core.api.Assertions
import org.example.domain.Customer
import org.testng.Assert
import org.testng.annotations.Test

class LoadExampleDataTest {

  @Test
  fun load() {

    LoadExampleData().load()

    val all = Customer.all()
    Assert.assertTrue(!all.isEmpty())
  }

  @Test(dependsOnMethods = arrayOf("load"))
  fun query() {

    val customers =
        Customer.find.where()
          .name.istartsWith("rob")
          .findList()

    Assertions.assertThat(customers).isNotEmpty()
  }
}


