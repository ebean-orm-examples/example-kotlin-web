#!/bin/sh
java -Dwebapp.http.port=8082 -Dlogback.configurationFile=logback2.xml -Debean.props.file=node2-ebean.properties -jar target/example-kotlin-web-1.1-SNAPSHOT.war
