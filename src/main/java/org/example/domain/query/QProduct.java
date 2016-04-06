package org.example.domain.query;

import com.avaje.ebean.EbeanServer;
import org.avaje.ebean.typequery.PLong;
import org.avaje.ebean.typequery.PString;
import org.avaje.ebean.typequery.PTimestamp;
import org.avaje.ebean.typequery.TQRootBean;
import org.avaje.ebean.typequery.TypeQueryBean;
import org.example.domain.Product;

/**
 * Query bean for Product.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
public class QProduct extends TQRootBean<Product,QProduct> {

  private static final QProduct _alias = new QProduct(true);

  /**
   * Return the shared 'Alias' instance used to provide properties to 
   * <code>select()</code> and <code>fetch()</code> 
   */
  public static QProduct alias() {
    return _alias;
  }

  public PLong<QProduct> id;
  public PLong<QProduct> version;
  public PTimestamp<QProduct> whenCreated;
  public PTimestamp<QProduct> whenModified;
  public PString<QProduct> sku;
  public PString<QProduct> name;


  /**
   * Construct with a given EbeanServer.
   */
  public QProduct(EbeanServer server) {
    super(Product.class, server);
  }

  /**
   * Construct using the default EbeanServer.
   */
  public QProduct() {
    super(Product.class);
  }

  /**
   * Construct for Alias.
   */
  private QProduct(boolean dummy) {
    super(dummy);
  }
}
