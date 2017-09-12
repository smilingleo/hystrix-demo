FROM java:8u72-jdk

ADD target/hystrix-demo-server-1.0-SNAPSHOT.jar /
ADD config.yml /

WORKDIR /

CMD java -jar hystrix-demo-server-1.0-SNAPSHOT.jar server config.yml