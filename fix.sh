#!/bin/sh
cd ~/git/visual
pwd
#mvn -f hello-rs.pom clean package
mvn clean package
mkdir -p tmp
rm -fr tmp/*
cp target/jdg-visualizer.war tmp
cd tmp
ls
unzip jdg-visualizer.war
ls
rm jdg-visualizer.war
cp ../jboss-client.jar WEB-INF/lib
ls WEB-INF/lib
jar -cvf jdg-visualizer.war .
ls jdg-visualizer.war
cp jdg-visualizer.war /u01/jboss-eap-7.0/standalone/deployments
cd ..


