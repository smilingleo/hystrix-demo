package me.leo.client.detail;

import com.netflix.config.ConfigurationManager;
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
        super(HystrixCommandGroupKey.Factory.asKey("DetailCmd"));
        HystrixCommandProperties.Setter()
                .withExecutionTimeoutInMilliseconds(Config.DETAIL_TIMEOUT);

        if (Config.DETAIL_CB_OPEN != null) {
            if (Config.DETAIL_CB_OPEN) {
                ConfigurationManager.getConfigInstance().setProperty("hystrix.command.DetailCmd.circuitBreaker.forceOpen", true);
            } else {
                ConfigurationManager.getConfigInstance().setProperty("hystrix.command.DetailCmd.circuitBreaker.forceClosed", true);
            }
        } else {
            ConfigurationManager.getConfigInstance().setProperty("hystrix.command.DetailCmd.circuitBreaker.forceOpen", false);
            ConfigurationManager.getConfigInstance().setProperty("hystrix.command.DetailCmd.circuitBreaker.forceClosed", false);
        }

        this.client = client;
        this.id = id;
    }

    @Override
    protected Product run() throws Exception {
        return client.getProduct(id);
    }
}
