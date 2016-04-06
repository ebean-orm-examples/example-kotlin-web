package org.example.domain.finder;

import com.avaje.ebean.Finder;
import org.example.domain.Country;
import org.example.domain.query.QCountry;

public class CountryFinder extends Finder<String,Country> {

  /**
   * Construct using the default EbeanServer.
   */
  public CountryFinder() {
    super(Country.class);
  }

  /**
   * Construct with a given EbeanServer.
   */
  public CountryFinder(String serverName) {
    super(Country.class, serverName);
  }

  /**
   * Start a new typed query.
   */
  protected QCountry where() {
     return new QCountry(db());
  }
}
