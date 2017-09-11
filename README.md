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

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/hystrix-demo-server-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

## Health Check

To see your applications health enter url `http://localhost:8081/healthcheck`


## Tasks

Run the following script to control the system behaviour:
```bash
http post localhost:8081/tasks/config review_latency==500 review_fallback==false
```

* product_latency, default 10 ms;
* product_timeout, default 1000 ms;
* product_cb_open, default `false` , set `true` to force open circuit breaker.
* detail_latency, default 10 ms;
* detail_timeout, default 1000 ms;
* detail_cb_open, default `false` , set `true` to force open circuit breaker.
* review_latency, default 10 ms;
* review_timeout, default 1000 ms;
* review_cb_open, default `false` , set `true` to force open circuit breaker.
* review_fallback, default `true`
    - If fallback is not enabled, when latency is increased, the API of `GET /client/products` will fail with 500 error.
    
## Reference
1. [Resilience Engineering in a Microservice Landscape • Maurice Zeijen](https://www.youtube.com/watch?v=Rduky8rzTwc&t=514s)