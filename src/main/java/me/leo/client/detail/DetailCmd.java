package me.leo.client.detail;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import me.leo.Config;
import me.leo.api.Product;

/**
 * Created by leo on 9/10/17.
 */
public class DetailCmd extends HystrixCommand<Product> {
    DetailClient client;
    String id;

    /**
     * Need to create constructor with method argument since `run` method has no argument.
     *
     * @param client
     * @param id
     */
    public DetailCmd(DetailClient client, String id) {
        super(HystrixCommandGroupKey.Factory.asKey("DetailClients"));
        HystrixCommandProperties.Setter()
                .withExecutionTimeoutInMilliseconds(Config.DETAIL_TIMEOUT);
        if (Config.DETAIL_CB_OPEN) {
            HystrixCommandProperties.Setter()
                    .withCircuitBreakerForceOpen(true);
        } else {
            HystrixCommandProperties.Setter()
                    .withCircuitBreakerForceClosed(true);
        }
        this.client = client;
        this.id = id;
    }

    @Override
    protected Product run() throws Exception {
        return client.getProduct(id);
    }
}
