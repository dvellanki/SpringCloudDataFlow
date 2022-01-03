# Getting Started with Spring Cloud Data Flow

Use the following link to get started: 

https://dataflow.spring.io/getting-started/


Use the following link to get started: 

https://docs.pivotal.io/scdf/1-8/getting-started.html


## Deployment on Kubernetes
Please follow the steps mentioned in [here](https://gitlab.eng.vmware.com/gkulkarni/spring-cloud-data-flow/blob/master/infra/bitnami/README.md) to deploy SCDF on kubernetes. 

Below is the link to SCDF documentation for deployment on kubernetes: 
https://dataflow.spring.io/docs/installation/kubernetes/


## Deployment on Local
Alternatively, you can have a local development using the jar files. 
Follow this section for local deployment: 
https://dataflow.spring.io/docs/2.5.x/installation/local/manual/

Spring Cloud Dataflow server

`wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-server/2.5.3.RELEASE/spring-cloud-dataflow-server-2.5.3.RELEASE.jar`

Spring Cloud Dataflow shell

`wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/2.5.3.RELEASE/spring-cloud-dataflow-shell-2.5.3.RELEASE.jar`

Spring cloud dataflow skipper

`wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-skipper-server/2.4.3.RELEASE/spring-cloud-skipper-server-2.4.3.RELEASE.jar`



## Getting started with the Development

### Using Dashboard


### Using Shell
You can use shell for development of Streams. 

Download the jar from : 

https://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/2.5.3.RELEASE/

`wget https://repo.spring.io/release/org/springframework/cloud/spring-cloud-dataflow-shell/2.5.3.RELEASE/spring-cloud-dataflow-shell-2.5.3.RELEASE.jar`


Go to the command line and to the directory which has the shell jar file and execute the following command:

`java -jar spring-cloud-dataflow-shell-2.5.3.RELEASE.jar`


Use the following command to configure the dataflow server host. This can be a local host or a kubernetes deployment.

`dataflow config server http://10.186.148.195:8080/`

you will be logged in to the shell.

If you want to point this to the kubernetes deployment host,from the kubernetes cluster, get the external ip of the data flow server service: 

`kubectl get svc external-scdf -n scdf-2 `


#### Types of application
#### Stream

Streams have to be used when there is a flow that requires a lot of data transfer. Example - Application B fetches data from application A and sends the data to application C. Now application B is connected to two applications and this would be a perfect use case for stream implementation since we just want to pass the data between the 2 applications(more the number of such connections involved more sense it makes to use streams) without having to worry about how to setup RMQ/Kafka connection and maintain the connection pool.

***Create a Stream***

Can be done via dataflow shell or using the dashboard http://10.186.148.195:8080/dashboard/#/tasks/definitions

DataFlowShell:
Below are some examples of streams that were created 

a) samplemarketdatastream:
   HTTP->FILE (Push data to a http url and load the payload to file)
   `stream create --name samplemarketdatastream --definition "http --server.port=8080 | file --file.name=marketdataspring --file.directory=/tmp"`
   It is important that the stream is externalized using load balancer else it will always connect to localhost by default
   stream deploy samplemarketdatastream  --properties "deployer.http.kubernetes.createLoadBalancer=true"

b) httpToMysql:
   HTTP->MYSQL (Push data to a http url and load the payload to a specifc table )
   `stream create --name httpTomysql --definition "http --server.port=8080 | jdbc --tableName=names --columns=name --spring.datasource.driver-class-name=org.mariadb.jdbc.Driver --spring.datasource.url='jdbc:mysql://10.186.148.163:3306/test' --spring.datasource.username=dataflow  --spring.datasource.password=change-me"`
   It is important that the stream is externalized using load balancer else it will always connect to localhost by default
   It is also important to externalize the default mysql host provided by scdf for pushing data
   `stream deploy httpTomysql --properties "deployer.http.kubernetes.createLoadBalancer=true"`

c) HTTPClientStream:
   httpclient->log (Post or Get calls to/from external API to log file)
   `stream create httpclientstream --definition "trigger --fixed-delay=10 | uaa-get-token: httpclient --url-expression="'----'" --http-method=POST --logging.level.org=DEBUG --headers-expression="{'Authorization':'Basic ','Content-Type':'application/json'}" | log`

d)SendgridEventProcess
  This stream uses customized services built and pushed as docker images to the repo.
  `EventSendSendgrid -> EmailStatusProcessor -> EmailStatusLogger`
  Once the image is pushed to docker repo, it needs to be registered in SCDF
  From Dashboard, navigate to Apps-> add  application -> Register one or more applications -> Enter name,Type(Source/Processor/Sink/Task) -> URI (For Kub, only docker repo is accepted. Maven is not supported) -> Register the application

e)EmailDeliveryService
  
 
***Deploy a Stream***
From shell:
`stream deploy streamname`

To use external endpoints:
`stream deploy streamname  --properties "deployer.http.kubernetes.createLoadBalancer=true"`

From dashboard:
Navigate to particular stream and click on the deploy stream option
Each component of the stream gets deployed as a deployment/pod with version.


***How to Externalize the properties***

In the deployment, instead of specifying the values manually, we can use configmaps/secrets and pass them to the pod.

***How to destroy***
From shell:
`stream destroy streamname`

From dashboard:
Navigate to particular stream and click on the destroy stream option

***Where does the metadata get stored***
Can be defined in the yaml file
By default SCDF stores the metadata in H2 in memory. If required it can be configured to use Mysql or any other datastore


#### Task

Task should be invoked usually in cases where there are 1 or less than one RMQ connections involved since for tasks RMQ connections have to be invoked manually. Also in case of a scheduled job tasks can be useful, example invoking a task monthly that fetches data from 2 usage databases, performs a difference and saves the overage to the database.

***Create Task***
From dashboard or shell

***Deploy Task***
each component of the task gets deployed as a deployment/pod with version.

***How to Externalize the properties***

In the deployment, instead of specifying the values manually, we can use configmaps/secrets and pass them to the pod

***How to deploy***
From dashboard, using the launch task option

***Metadata***

Can be defined in the yaml file
By default SCDF stores the metadata in H2 in memory. If required it can be configured to use Mysql or any other datastore


Below tables are created based on the datastore selected

TASK_EXECUTION
TASK_EXECUTION_PARAMS
TASK_LOCK
TASK_SEQ
TASK_TASK_BATCH
