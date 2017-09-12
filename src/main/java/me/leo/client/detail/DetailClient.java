package me.leo.client.detail;

import java.util.Optional;
import javax.ws.rs.client.Client;

import me.leo.api.Product;

/**
 * Created by leo on 9/10/17.
 */
public class DetailClient {
    private Client client;
    private String url = Optional.ofNullable(System.getenv("DETAIL_URL")).orElse("http://localhost:8080");
    public DetailClient(Client client) {
        this.client = client;
    }

    public Product getProduct(String id) {
        Product rtn = client.target(url + "/products/" + id)
                .request()
                .buildGet()
                .invoke(Product.class);
        return rtn;
    }
}
