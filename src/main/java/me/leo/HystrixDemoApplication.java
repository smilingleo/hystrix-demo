package me.leo;

import com.netflix.hystrix.contrib.codahalemetricspublisher.HystrixCodaHaleMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

import me.leo.client.detail.DetailClient;
import me.leo.client.product.ProductClient;
import me.leo.client.review.ReviewClient;
import me.leo.resources.ClientResource;
import me.leo.resources.ServerResource;
import me.leo.task.ConfigTask;

public class HystrixDemoApplication extends Application<HystrixDemoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HystrixDemoApplication().run(args);
    }

    @Override
    public String getName() {
        return "HystrixDemo";
    }

    @Override
    public void initialize(final Bootstrap<HystrixDemoConfiguration> bootstrap) {
        // publish Coda format metrics.
        HystrixCodaHaleMetricsPublisher metricsPublisher = new HystrixCodaHaleMetricsPublisher(bootstrap.getMetricRegistry());
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
    }

    @Override
    public void run(final HystrixDemoConfiguration configuration,
                    final Environment environment) {
        ServerResource productResource = new ServerResource();
        environment.jersey().register(productResource);

        // tasks
        environment.admin().addTask(new ConfigTask());

        // clients
        final Client productC = new JerseyClientBuilder(environment).using(configuration.getProductJerseyClientConfiguration())
                .build("product-client");
        ProductClient productClient = new ProductClient(productC);
        environment.jersey().register(productClient);

        final Client detailC = new JerseyClientBuilder(environment).using(configuration.getDetailJerseyClientConfiguration())
                .build("detail-client");

        DetailClient detailClient = new DetailClient(detailC);
        environment.jersey().register(detailClient);

        final Client reviewC = new JerseyClientBuilder(environment).using(configuration.getReviewJerseyClientConfiguration())
                .build("review-client");

        ReviewClient reviewClient = new ReviewClient(reviewC);
        environment.jersey().register(reviewClient);

        // a client endpoint which implementation is nothing but calling the above clients.
        environment.jersey().register(new ClientResource(productClient, detailClient, reviewClient));

        // to enable metric stream.
        environment.getApplicationContext()
                .addServlet("com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet", "/hystrix.stream");
    }
}
