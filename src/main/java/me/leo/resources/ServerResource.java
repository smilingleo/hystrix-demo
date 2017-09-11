package me.leo.resources;

import org.apache.commons.lang3.RandomUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import me.leo.Config;
import me.leo.api.Product;
import me.leo.api.Review;

/**
 * Just imaging these 3 APIs are running on different hosts, e.g., 3 different services.
 * Created by leo on 9/10/17.
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ServerResource {

    @GET
    @Path("/{id}")
    public Product getDetail(@PathParam("id") String id) {
        addlatency(Config.DETAIL_LATENCY);
        Product product = buildProduct("description ---  " + RandomUtils.nextLong());
        return product;
    }

    @GET
    @Path("/{id}/reviews")
    public List<Review> getReview(@PathParam("id") String id) {
        addlatency(Config.REVIEW_LATENCY);

        List<Review> rtn = new LinkedList<Review>();
        int limit = RandomUtils.nextInt(1, 5);
        for (int i = 0; i < limit; i++) {
            rtn.add(buildReview(id));
        }
        return rtn;
    }

    private Review buildReview(String id) {
        Review review = new Review();
        review.setId(UUID.randomUUID().toString());
        review.setProductId(id);
        review.setComment("some comments");
        review.setScore(RandomUtils.nextInt(1, 10));
        review.setReviewer("user--" + RandomUtils.nextInt());
        return review;
    }

    private Product buildProduct(String description) {
        Product product = new Product();
        String uuid = UUID.randomUUID().toString();
        product.setId(uuid);
        product.setName("product-" + uuid);
        product.setSku("sku");
        product.setPrice(10);
        product.setDescription(description);
        return product;
    }

    @GET
    public List<Product> list() {
        addlatency(Config.LIST_LATENCY);
        List<Product> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(buildProduct(null));
        }
        return list;
    }


    private void addlatency(int time) {
        try {
            Thread.currentThread().sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
