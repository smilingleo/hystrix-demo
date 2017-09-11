package me.leo.client.detail;

import javax.ws.rs.client.Client;

import me.leo.api.Product;

/**
 * Created by leo on 9/10/17.
 */
public class DetailClient {
    private Client client;

    public DetailClient(Client client) {
        this.client = client;
    }

    public Product getProduct(String id) {
        Product rtn = client.target("http://localhost:8080/products/" + id)
                .request()
                .buildGet()
                .invoke(Product.class);
        return rtn;
    }
}
