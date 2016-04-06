package org.example.service;

import com.avaje.ebean.Ebean
import com.avaje.ebean.EbeanServer
import org.example.domain.*

class LoadExampleData {

  var runOnce: Boolean = false;

  val server: EbeanServer = Ebean.getDefaultServer();

  fun load() {

    if (runOnce) {
      return;
    }

    server.execute({
      deleteAll();
      insertCountries();
      insertProducts();
      insertTestCustAndOrders();
    });
    runOnce = true;
  }

  fun deleteAll() {

    Ebean.execute({

      // orm update use bean name and bean properties
      OrderDetail.find.query().delete()
      Order.find.query().delete()
      Contact.query().delete()
      Customer.find.query().delete()
      Address.query().delete()
      Country.query().delete()
      Product.query().delete()

      //      server.createUpdate(OrderDetail::class.java, "delete from orderDetail").execute();
      //      server.createUpdate(Order::class.java, "delete from order").execute();
      //      server.createUpdate(Contact::class.java, "delete from contact").execute();
      //      server.createUpdate(Customer::class.java, "delete from Customer").execute();
      //      server.createUpdate(Address::class.java, "delete from address").execute();

      //      // sql update uses table and column names
      //      server.createSqlUpdate("delete from o_country").execute();
      //      server.createSqlUpdate("delete from o_product").execute();
    })
  }

  fun insertCountries() {

    Country("NZ", "New Zealand").save()
    Country("AU", "Australia").save()
  }

  fun insertProducts() {

    server.execute({

      // use alternate constructor function ...
      Product("Chair", "C001").save()
      Product("Desk", "DSK1").save()
      Product("C002", "Computer").save()

      // use set properties style ...
      val product = Product()
      product.name = "Printer"
      product.sku = "C003"
      product.save()
    });
  }

  fun insertTestCustAndOrders() {

    // TxRunnable is not really required ...
    server.execute({
      val cust1 = insertCustomer("Rob");
      val cust2 = insertCustomerNoAddress();
      insertCustomerFiona();
      insertCustomerNoContacts("NoContactsCust");

      createOrder1(cust1);
      createOrder2(cust2);
      createOrder3(cust1);
      createOrder4(cust1);
    });
  }


  var contactEmailNum: Int = 1;

  fun insertCustomerFiona(): Customer {

    val c = createCustomer("Fiona", "12 Apple St", "West Coast Rd", 1)
    c.contacts.add(createContact("Fiona", "Black"))
    c.contacts.add(createContact("Tracy", "Red"))
    c.save()
    return c;
  }

  fun createContact(firstName: String, lastName: String): Contact {

    val contact = Contact();
    contact.firstName = firstName;
    contact.lastName = lastName;
    contact.email = (contact.lastName + (contactEmailNum++) + "@test.com").toLowerCase();
    return contact;
  }

  fun insertCustomerNoContacts(name: String): Customer {

    val customer = createCustomer("Roger", "15 Kumera Way", "Bos town", 1);
    customer.name = name;

    customer.save()
    return customer;
  }

  fun insertCustomerNoAddress(): Customer {

    val customer = Customer();
    customer.name = "Customer NoAddress";
    customer.contacts.add(createContact("Jack", "Black"));
    customer.save()
    return customer;
  }

  fun insertCustomer(name: String): Customer {
    val customer = createCustomer(name, "1 Banana St", "P.O.Box 1234", 1);
    customer.save()
    return customer;
  }

  fun createCustomer(name: String, shippingStreet: String?, billingStreet: String?, contactSuffix: Int): Customer {

    val customer = Customer();
    customer.name = name;
    if (contactSuffix > 0) {
      customer.contacts.add(Contact("Jim" + contactSuffix, "Cricket"));
      customer.contacts.add(Contact("Fred" + contactSuffix, "Blue"));
      customer.contacts.add(Contact("Bugs" + contactSuffix, "Bunny"));
    }

    if (shippingStreet != null) {
      val shipAddress = Address();
      shipAddress.line1 = shippingStreet;
      shipAddress.line2 = "Sandringham";
      shipAddress.city = "Auckland";
      shipAddress.country = Country.ref("NZ")

      customer.shippingAddress = shipAddress;
    }

    if (billingStreet != null) {
      val billAddress = Address();
      billAddress.line1 = billingStreet;
      billAddress.line2 = "St Lukes";
      billAddress.city = "Auckland";
      billAddress.country = Country.ref("NZ")

      customer.billingAddress = billAddress;
    }

    return customer;
  }

  fun createOrder1(customer: Customer): Order {

    return with(Order()) {
      this.customer = customer;
      details.add(OrderDetail(Product.ref(1), 5, 10.50))
      details.add(OrderDetail(Product.ref(2), 3, 1.10))
      details.add(OrderDetail(Product.ref(3), 1, 2.00))
      save()
      this
    }
  }

  fun createOrder2(customer: Customer) {

    with(Order()) {
      status = Order.Status.SHIPPED
      this.customer = customer
      this.details.add(OrderDetail(Product.ref(1), 4, 10.50))
      save();
    }
  }

  fun createOrder3(customer: Customer) {

    with(Order()) {
      status = Order.Status.COMPLETE
      this.customer = customer;
      this.details.add(OrderDetail(Product.ref(1), 3, 10.50))
      this.details.add(OrderDetail(Product.ref(3), 40, 2.10))
      save()
    }
  }

  fun createOrder4(customer: Customer) {

    with(Order()) {
      this.customer = customer;
      save();
    }
  }
}
