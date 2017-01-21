#!/bin/sh
cd ~/git/visual
pwd
#mvn -f hello-rs.pom clean package
mvn clean package
ls
cp target/infinispan-visualizer.war /u01/jboss-eap-7.0/standalone/deployments
pwd

