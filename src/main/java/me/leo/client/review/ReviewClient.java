package me.leo.client.review;

import java.util.List;
import java.util.Optional;
import javax.ws.rs.client.Client;

import me.leo.api.Review;

/**
 * Created by leo on 9/10/17.
 */
public class ReviewClient {
    private Client client;
    private String url = Optional.ofNullable(System.getenv("REVIEW_URL")).orElse("http://localhost:8080");
    public ReviewClient(Client client) {
        this.client = client;
    }

    public List<Review> getReviews(String id) {
        List rtn = client.target(url + "/products/" + id + "/reviews")
                .request()
                .buildGet()
                .invoke(List.class);
        return rtn;
    }
}
