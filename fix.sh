#!/bin/sh
pwd
mvn clean package
mkdir -p tmp
rm -fr tmp/*
cp target/jdg-visualizer.war tmp
cd tmp
ls
unzip jdg-visualizer.war
ls
rm jdg-visualizer.war
cp /home/gnunn/Development/jdg/demo/jboss-datagrid-7.1.0-server/bin/client/jboss-client.jar WEB-INF/lib
ls WEB-INF/lib
jar -cvf jdg-visualizer.war .
ls jdg-visualizer.war
cp jdg-visualizer.war /home/gnunn/Development/jdg/demo/jboss-eap-7.0/standalone/deployments/
cd ..


