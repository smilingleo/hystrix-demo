package me.leo.client.review;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.Collections;
import java.util.List;

import me.leo.Config;
import me.leo.api.Review;

/**
 * Created by leo on 9/10/17.
 */
public class GetReviewsByProductCmd extends HystrixCommand<List<Review>> {
    ReviewClient client;
    String id;

    public GetReviewsByProductCmd(ReviewClient client, String id) {
        super(HystrixCommandGroupKey.Factory.asKey("ReviewClients"));
        HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter();
        setter.withExecutionTimeoutInMilliseconds(Config.REVIEW_TIMEOUT);
        if (Config.REVIEW_CB_OPEN) {
            ConfigurationManager.getConfigInstance().setProperty("hystrix.command.ReviewClients.circuitBreaker.forceOpen", true);
        } else {
            ConfigurationManager.getConfigInstance().setProperty("hystrix.command.ReviewClients.circuitBreaker.forceClosed", true);
        }

        this.client = client;
        this.id = id;
    }

    @Override
    protected List<Review> run() throws Exception {
        return client.getReviews(id);
    }

    @Override
    protected List<Review> getFallback() {
        if (Config.REVIEW_FALLBACK) {
            return Collections.emptyList();
        } else {
            throw new UnsupportedOperationException("No fallback available.");
        }
    }
}
