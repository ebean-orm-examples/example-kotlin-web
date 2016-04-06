# example-kotlin-web
Kotlin, Guice, RestEasy, JAX-RS, Ebean runnable war example application

# Main's

`src/test/main/RunApp` ... runs webapp using Jetty on port 9090

`src/test/main/MainDbMigration` ... runs DB migration without offline (using EbeanServer in offline mode, no DB connection required)

`src/test/main/MainQueryBeanGenerator` ... generates "Query Beans", because the entity beans are written in Kotlin (not java) then we can't use the java annotation processor (yet).


# Guice Modules
- org/example/module/DbModule
- org/example/web/module/WebModule

# Maven

mvn clean package ... to build the runnable war in /target

# Maven tiles

Uses tiles for Ebean enhancement, Java compile, Kotlin compile
