# SCDF Installation Steps - Local

This document refers to local installation of SCDF on Mac ,

https://medium.com/@Ankitthakur/apache-kafka-installation-on-mac-using-homebrew-a367cdefd273
kafka and zookeeper https://www.javatpoint.com/installing-apache-kafka-on-macos

## Steps to follow

1.Use 'brew install kafka' and press enter key to install Kafka. Wait a while, and Kafka will be installed on the system. To test, use any kafka command.

```
rajamuthua-a01:~ rajamuthua$ brew install kafka
Updating Homebrew...
==> Auto-updated Homebrew!
Updated 3 taps (homebrew/core, homebrew/services and mongodb/brew).
==> New Formulae
leaf                                              rustscan                                          vivid                                             webify
==> Updated Formulae
Updated 416 formulae.
==> Renamed Formulae
gst-validate -> gst-devtools
==> Downloading https://homebrew.bintray.com/bottles/zookeeper-3.6.2.mojave.bottle.tar.gz
==> Downloading from https://d29vzk4ow07wi7.cloudfront.net/799dcedfc580923f05b1634fead93e5b4d570bf499f4c52e732cb123d3d9a928?response-content-disposition=attachment%3Bfilename%3D%22zookeeper-3.6.2.mo
######################################################################## 100.0%
==> Downloading https://homebrew.bintray.com/bottles/kafka-2.6.0.mojave.bottle.tar.gz
==> Downloading from https://d29vzk4ow07wi7.cloudfront.net/027a1d06325c8b98cca97cc2922fdbf7d980fb52917d1791861032cd501e5428?response-content-disposition=attachment%3Bfilename%3D%22kafka-2.6.0.mojave
######################################################################## 100.0%
==> Installing dependencies for kafka: zookeeper
==> Installing kafka dependency: zookeeper
==> Pouring zookeeper-3.6.2.mojave.bottle.tar.gz
==> Caveats
To have launchd start zookeeper now and restart at login:
  brew services start zookeeper
Or, if you don't want/need a background service you can just run:
  zkServer start
==> Summary
🍺  /usr/local/Cellar/zookeeper/3.6.2: 1,025 files, 37.0MB
==> Installing kafka
==> Pouring kafka-2.6.0.mojave.bottle.tar.gz
==> Caveats
To have launchd start kafka now and restart at login:
  brew services start kafka
Or, if you don't want/need a background service you can just run:
  zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties
==> Summary
🍺  /usr/local/Cellar/kafka/2.6.0: 186 files, 62.4MB
==> Caveats
==> zookeeper
To have launchd start zookeeper now and restart at login:
  brew services start zookeeper
Or, if you don't want/need a background service you can just run:
  zkServer start
==> kafka
To have launchd start kafka now and restart at login:
  brew services start kafka
Or, if you don't want/need a background service you can just run:
  zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties
```
Note :  Within the data directory, make two new directories: 'zookeeper' and 'kafka'.open zookeper property file 'nano config/zookeeper.properties' edit datadir path to data folder and open server property file - nano config/server.properties update log.dirs to kafka folder path

2. brew services start zookeeper

3. brew services start kafka

4. Download requried jars to local folder /SCDF and execute SCDF server,skipper,shell from local

```
wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-server/2.6.2/spring-cloud-dataflow-server-2.6.2.jar
wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/2.6.2/spring-cloud-dataflow-shell-2.6.2.jar
wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-skipper-server/2.5.2/spring-cloud-skipper-server-2.5.2.jar
```

From /SCDF folder run below commands

=> Command To Start SCDF skipper  ```java -jar spring-cloud-skipper-server-2.5.2.jar```
=> Command To Start SCDF server  ```java -jar spring-cloud-dataflow-server-2.6.2.jar```
=> Command To Start SCDF shell   ```java -jar spring-cloud-dataflow-shell-2.6.2.jar``` //only if you want to use SCDF CLI for stream creation

Access SCDF on local using below URLS,
```http://localhost:9393/dashboard```
```http://localhost:7577/api```











