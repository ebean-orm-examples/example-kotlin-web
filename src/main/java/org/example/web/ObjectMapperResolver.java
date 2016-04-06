package org.example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.avaje.ebeanorm.jackson.DelayEbeanModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Provides ObjectMapper with registered Ebean Jackson module.
 */
@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {

  private final ObjectMapper mapper;

  @Inject
  public ObjectMapperResolver() {
    mapper = new ObjectMapper();
    mapper.registerModule(new DelayEbeanModule());
  }

  @Override
  public ObjectMapper getContext(Class<?> aClass) {
    return mapper;
  }
}
