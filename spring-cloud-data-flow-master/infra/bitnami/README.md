## Bitnami 

***Use the following link for reference on Bitnami helm chart:***

https://github.com/bitnami/charts/tree/master/bitnami/spring-cloud-dataflow/#installing-the-chart


Before running the helm command, ensure you create a namespace and rolebinding. If your cluster needs PSP, ensure you add that in the rolebinding.

`kubectl create namespace operator`

`kubectl create rolebinding rolebinding-default-privileged-sa-ns_bitnami --namespace=operator --clusterrole=psp:vmware-system-privileged --group=system:serviceaccounts`


***update the storage class in values.yaml***

Use the values.yaml file checked into this directory. Ensure you change the storage class as the PVC need to be dynamically provisioned. 

Ensure you update the values.yaml file with the correct storage class. Use the values.yaml which is working.
This is required to enable dynamic PVC creation.
Example:
global:
storageClass: sc2-01-vc16c01-wcp-mgmt
Have a look at the values.yaml file to change other parameters like replicas etc.


***get bitnami repo in helm***

`helm repo add bitnami https://charts.bitnami.com/bitnami`


***helm install command using values.yaml with updated storage class***

`helm install my-release bitnami/spring-cloud-dataflow -f values.yaml -n scdf-2`


***Output***

NAME: my-release
LAST DEPLOYED: Wed Jul 29 14:57:56 2020
NAMESPACE: scdf-2
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
** Please be patient while the chart is being deployed **

Spring Cloud Data Flow chart was deployed enabling the following components:

- Spring Cloud Data Flow server
- Spring Cloud Skipper server

Spring Cloud Data Flow can be accessed through the following DNS name from within your cluster:

    my-release-spring-cloud-dataflow-server.scdf-2.svc.cluster.local (port 8080)

To access Spring Cloud Data Flow dashboard from outside the cluster execute the following commands:

1. Get the Data Flow dashboard URL by running these commands:

    export SERVICE_PORT=$(kubectl get --namespace scdf-2 -o jsonpath="{.spec.ports[0].port}" services my-release-spring-cloud-dataflow-server)
    kubectl port-forward --namespace scdf-2 svc/my-release-spring-cloud-dataflow-server ${SERVICE_PORT}:${SERVICE_PORT} &
    echo "http://127.0.0.1:${SERVICE_PORT}/dashboard"

2. Open a browser and access the Data Flow dashboard using the obtained URL.


***Checking Kafka Installation ***

***create a kafka client***

`kubectl run my-release-kafka-client --restart='Never' --image docker.io/bitnami/kafka:2.5.0-debian-10-r96 --namespace scdf-2 --command -- sleep infinity`


***get into the pod bash***

`kubectl exec my-release-kafka-client -n scdf-2 -it -- bash`


***run the following command to write into the topic***

`kafka-console-producer.sh --bootstrap-server my-release-kafka.scdf-2.svc.cluster.local:9092 --topic test`


***run the following command to read the topic data***

`kafka-console-consumer.sh --bootstrap-server my-release-kafka.scdf-2.svc.cluster.local:9092 --topic test --from-beginning`


## Changes for TKG

Changes done to values.yaml
1. specified the serviceaccount name, create: false
2. added limits
3. specified rbac create:false
4. no global storage class specified it takes it by default

```gkulkarni@gkulkarni-a01 bitnami % helm install my-release bitnami/spring-cloud-dataflow -f values.yaml
NAME: my-release
LAST DEPLOYED: Tue Aug  4 07:03:48 2020
NAMESPACE: tkgdev-saasml-dev
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
** Please be patient while the chart is being deployed **
---------------------------------------------------------------------------------------------
 WARNING

    By specifying "rbac.create=false" and "serviceAccount.create=false" it's 
    likely your pods enter into a "Init:CrashLoopBackOff" status if your
    K8s cluster has RBAC enabled.

---------------------------------------------------------------------------------------------

Spring Cloud Data Flow chart was deployed enabling the following components:

- Spring Cloud Data Flow server
- Spring Cloud Skipper server

Spring Cloud Data Flow can be accessed through the following DNS name from within your cluster:

    my-release-spring-cloud-dataflow-server.tkgdev-saasml-dev.svc.cluster.local (port 8080)

To access Spring Cloud Data Flow dashboard from outside the cluster execute the following commands:

1. Get the Data Flow dashboard URL by running these commands:

    export SERVICE_PORT=$(kubectl get --namespace tkgdev-saasml-dev -o jsonpath="{.spec.ports[0].port}" services my-release-spring-cloud-dataflow-server)
    kubectl port-forward --namespace tkgdev-saasml-dev svc/my-release-spring-cloud-dataflow-server ${SERVICE_PORT}:${SERVICE_PORT} &
    echo "http://127.0.0.1:${SERVICE_PORT}/dashboard"

2. Open a browser and access the Data Flow dashboard using the obtained URL.```


## Stable release
***References****


https://dataflow.spring.io/docs/installation/kubernetes/helm/


***Create a namespace***

`kubectl create namespace scdf`


***role binding***

`kubectl create rolebinding rolebinding-default-privileged-sa-scdf --namespace=scdf --clusterrole=psp:vmware-system-privileged  --group=system:serviceaccounts`


***helm install***

`helm repo update`

`helm repo add stable https://kubernetes-charts.storage.googleapis.com`

`helm install my-release stable/spring-cloud-data-flow --set kafka.enabled=true,rabbitmq.enabled=false -n scdf 
NAME: my-release
LAST DEPLOYED: Wed Jul 29 14:05:08 2020
NAMESPACE: scdf
STATUS: deployed
REVISION: 1
NOTES:
1. Get the application URL by running these commands:
     NOTE: It may take a few minutes for the LoadBalancer IP to be available.
           You can watch the status of the server by running 'kubectl get svc -n scdf -w my-release-data-flow-server'
  export SERVICE_IP=$(kubectl get svc --namespace scdf my-release-data-flow-server -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
  echo http://$SERVICE_IP:80
`

helm install stable/spring-cloud-data-flow --version 2.4.0 --generate-name