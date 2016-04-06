package main

import org.avaje.ebean.typequery.generator.Generator
import org.avaje.ebean.typequery.generator.GeneratorConfig
import java.io.IOException


object MainQueryBeanGenerator {

  @Throws(IOException::class)
  @JvmStatic fun main(args: Array<String>) {

    val config = GeneratorConfig()
    //config.setClassesDirectory("./target/classes");
    //config.setDestDirectory("./src/main/java");
    //config.setDestResourceDirectory("./src/main/resources");

    config.entityBeanPackage = "org.example.domain"
    //config.setDestPackage("org.example.domain.query");

    //config.setOverwriteExistingFinders(true);

    val generator = Generator(config)
    generator.generateQueryBeans()
    generator.generateFinders();
    generator.modifyEntityBeansAddFinderField();
  }
}