package main

import com.avaje.ebean.config.dbplatform.DbPlatformName
import com.avaje.ebean.dbmigration.DbMigration
import java.io.IOException


object MainDbMigration {

  @Throws(IOException::class)
  @JvmStatic fun main(args: Array<String>) {


    //System.setProperty("ddl.migration.version", "1.1");
    //System.setProperty("ddl.migration.name", "support end dating");

    val dbMigration = DbMigration()
    dbMigration.setPlatform(DbPlatformName.POSTGRES);
    dbMigration.generateMigration();
  }
}