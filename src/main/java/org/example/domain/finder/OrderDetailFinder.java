package org.example.domain.finder;

import com.avaje.ebean.Finder;
import org.example.domain.OrderDetail;
import org.example.domain.query.QOrderDetail;

public class OrderDetailFinder extends Finder<Long,OrderDetail> {

  /**
   * Construct using the default EbeanServer.
   */
  public OrderDetailFinder() {
    super(OrderDetail.class);
  }

  /**
   * Construct with a given EbeanServer.
   */
  public OrderDetailFinder(String serverName) {
    super(OrderDetail.class, serverName);
  }

  /**
   * Start a new typed query.
   */
  protected QOrderDetail where() {
     return new QOrderDetail(db());
  }
}
