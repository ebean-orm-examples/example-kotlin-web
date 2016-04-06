package main

import com.avaje.ebean.config.dbplatform.DbPlatformName
import com.avaje.ebean.dbmigration.DbMigration
import java.io.IOException


object MainDbMigration {

  @Throws(IOException::class)
  @JvmStatic fun main(args: Array<String>) {


    System.setProperty("ddl.migration.generate", "true");
    //System.setProperty("ddl.migration.version", "4.5.6_12");
    //System.setProperty("ddl.migration.name", "support end dating");

    // migration will be generated when EbeanServer instance starts
    //Ebean.getDefaultServer();

    val dbMigration = DbMigration()
    dbMigration.setPlatform(DbPlatformName.POSTGRES);
    dbMigration.generateMigration();
  }
}