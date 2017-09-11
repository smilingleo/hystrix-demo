package me.leo.client.product;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.List;

import me.leo.Config;
import me.leo.api.Product;

/**
 * We don't need to change the implementation of `ProductClient`, only wrap it up, and change the invocation of the productClient
 * Created by leo on 9/10/17.
 */
public class ProductListCmd extends HystrixCommand<List<Product>> {
    ProductClient client;

    public ProductListCmd(ProductClient client) {
        super(HystrixCommandGroupKey.Factory.asKey("ProductListCmd"));
        HystrixCommandProperties.Setter()
                .withExecutionTimeoutInMilliseconds(Config.LIST_TIMEOUT);

        if (Config.LIST_CB_OPEN != null) {
            if (Config.LIST_CB_OPEN) {
                ConfigurationManager.getConfigInstance().setProperty("hystrix.command.ProductListCmd.circuitBreaker.forceOpen", true);
            } else {
                ConfigurationManager.getConfigInstance().setProperty("hystrix.command.ProductListCmd.circuitBreaker.forceClosed", true);
            }
        } else {
            ConfigurationManager.getConfigInstance().setProperty("hystrix.command.ProductListCmd.circuitBreaker.forceOpen", false);
            ConfigurationManager.getConfigInstance().setProperty("hystrix.command.ProductListCmd.circuitBreaker.forceClosed", false);
        }

        this.client = client;
    }

    @Override
    protected List<Product> run() throws Exception {
        return client.list();
    }
}
