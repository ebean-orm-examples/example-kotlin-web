package org.example.domain.query.assoc;

import org.avaje.ebean.typequery.PLong;
import org.avaje.ebean.typequery.PString;
import org.avaje.ebean.typequery.PTimestamp;
import org.avaje.ebean.typequery.TQAssocBean;
import org.avaje.ebean.typequery.TQProperty;
import org.avaje.ebean.typequery.TypeQueryBean;
import org.example.domain.Contact;
import org.example.domain.query.QContact;

/**
 * Association query bean for AssocContact.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
public class QAssocContact<R> extends TQAssocBean<Contact,R> {

  public PLong<R> id;
  public PLong<R> version;
  public PTimestamp<R> whenCreated;
  public PTimestamp<R> whenModified;
  public PString<R> firstName;
  public PString<R> lastName;
  public PString<R> email;
  public PString<R> phone;
  public QAssocCustomer<R> customer;

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  @SafeVarargs
  public final R fetch(TQProperty<QContact>... properties) {
    return fetchProperties(properties);
  }

  public QAssocContact(String name, R root) {
    super(name, root);
  }
}
