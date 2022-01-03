
#!/bin/bash
#build ,tag and push image
docker build -t sourcedata-transform:1.0 .
docker tag sourcedata-transform:1.0 harbor-repo.vmware.com/scdfrepo/sourcedata-transform:1.0
docker login harbor-repo.vmware.com/scdfrepo
docker push harbor-repo.vmware.com/scdfrepo/sourcedata-transform:1.0
#Connect to shell and create stream

java -jar spring-cloud-dataflow-shell-2.6.2.jar --spring.shell.commandFile=\
register-app.txt

#command dataflow config server http://10.216.52.96/
#Register Image App
#command app register --name mysource --type source --uri docker://harbor-repo.vmware.com/scdfrepo/sourcedata-transform:1.0
#Register needed apps
#app register --name jdbc --type source --uri docker:springcloudstream/jdbc-source-kafka:2.1.6.RELEASE
#app register --name http --type source --uri docker:springcloudstream/http-source-kafka:2.1.4.RELEASE
#app register --name httpclient --type processor --uri docker:springcloudstream/httpclient-processor-kafka:2.1.4.RELEASE
#app register --name transform --type processor --uri docker:springcloudstream/transform-processor-kafka:2.1.3.RELEASE
#app register --name log --type sink --uri docker:springcloudstream/log-sink-kafka:2.1.4.RELEASE
#create stream


