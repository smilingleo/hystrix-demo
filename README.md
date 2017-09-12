# HystrixDemo
The use case is really simple, to show a product list page, product detail and its reviews.  
There are 3 APIs:
* `GET /products` to get a list of products (without description)
* `GET /products/{id}` to get product detail (with description)
* `GET /products/{id}/reviews` to get all reviews of the product.

Let's assume the 3 APIs are served from 3 different services.
 
 The `GET /client/products` is the API running on a client of above 3 services, say it Product UI backend, 
 it calls the above 3 APIs to render the product page. 

## How to start the HystrixDemo application

1. Run `mvn clean package` to build your application
1. Start application with `java -jar target/hystrix-demo-server-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

## Health Check

To see your applications health enter url `http://localhost:8081/healthcheck`

## Runtime Configuration

Run the following script to control the system behaviour:
```bash
http post localhost:8081/tasks/config review_latency==500 review_fallback==false
```

* product_latency, default 10 ms;
* product_timeout, default 1000 ms;
* product_cb_open, default `null` , set `true` to force open circuit breaker, `false` to force close it.
* detail_latency, default 10 ms;
* detail_timeout, default 1000 ms;
* detail_cb_open, default `null` , set `true` to force open circuit breaker, `false` to force close it.
* review_latency, default 10 ms;
* review_timeout, default 1000 ms;
* review_cb_open, default `null` , set `true` to force open circuit breaker, `false` to force close it.
* review_fallback, default `true`
    - If fallback is not enabled, when latency is increased, the API of `GET /client/products` will fail with 500 error.

## Hystrix Dashboard
Check out Hystrix repo, goto `hystrix-dashboard` folder and run `../gradlew appRun`, add `http://localhost:8080/hystrix.stream` and monitor.


## Run with Docker
1. Run `mvn clean package` to build the shaded jar file
1. Run `docker build -t hystrix-demo .` to build the docker image
1. Run `docker-compose up` to bring up the services.
1. Give it some workload by `ab -c 5 -n 10000 localhost:8080/client/products`
1. Register the metric stream to Hystrix dashboard.

## How to Adopt Hystrix
1. To wrap API invocation with a `HystrixCommand` 
For each API invocation, wrap it with a `HystrixCommand`, the original client SDK doesn't need to be changed.
For example, `ProductClient` is the original client SDK. Keep it untouched, add `ProductListCmd`.

1. To change the way you call the client SDK.
For example, in `ClientResource`, without Hystrix, you use the `productClient.list()` to get product list, 
with Hystrix, you just need to replace above invocation to `new ProductListCmd(productClient).execute()`.


## Reference
1. [Resilience Engineering in a Microservice Landscape • Maurice Zeijen](https://www.youtube.com/watch?v=Rduky8rzTwc&t=514s)