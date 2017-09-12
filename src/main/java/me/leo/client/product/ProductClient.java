package me.leo.client.product;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;

import me.leo.api.Product;

/**
 * Created by leo on 9/10/17.
 */
public class ProductClient {
    private Client client;
    private String url = Optional.ofNullable(System.getenv("PRODUCT_URL")).orElse("http://localhost:8080");
    public ProductClient(Client client) {
        this.client = client;
    }

    public List<Product> list() {
        List<Map> list = client.target(url + "/products")
                .request()
                .buildGet()
                .invoke(List.class);
        return list.stream().map(m -> {
            return new Product(m.get("id").toString(),
                    (String) m.get("name"),
                    (String) m.get("sku"),
                    (String) m.get("description"),
                    Float.valueOf(m.get("price").toString()));
        }).collect(Collectors.toList());
    }
}
