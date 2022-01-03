FROM amd64/photon:3.0
RUN yum -y install openjre8
MAINTAINER Anitha Rajamuthu <rajamuthua@vmware.com>
COPY target/sourcedata-transform-latest.jar sourcedata-transform.jar
ENTRYPOINT ["java","-jar","sourcedata-transform.jar"]