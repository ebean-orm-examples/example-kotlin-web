#!/bin/sh
java -Dwebapp.http.port=8081 -Dlogback.configurationFile=logback1.xml -Debean.props.file=node1-ebean.properties -jar target/example-kotlin-web-1.1-SNAPSHOT.war
