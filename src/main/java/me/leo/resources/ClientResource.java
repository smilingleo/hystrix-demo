package me.leo.resources;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import me.leo.api.ProductVO;
import me.leo.api.Review;
import me.leo.client.detail.DetailClient;
import me.leo.client.detail.DetailCmd;
import me.leo.client.product.ProductClient;
import me.leo.client.product.ProductListCmd;
import me.leo.client.review.GetReviewsByProductCmd;
import me.leo.client.review.ReviewClient;

/**
 * Client is a bit confusing, by `client`, it means the client of `product service`, `review service`.
 * Created by leo on 9/10/17.
 */
@Path("/client/products")
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {
    private ProductClient productClient;
    private DetailClient detailClient;
    private ReviewClient reviewClient;

    public ClientResource(ProductClient productClient, DetailClient detailClient, ReviewClient reviewClient) {
        this.productClient = productClient;
        this.detailClient = detailClient;
        this.reviewClient = reviewClient;
    }

    @GET
    public List<ProductVO> list() {
        return new ProductListCmd(productClient).execute().stream()
                .map(product -> new DetailCmd(detailClient, product.getId()).execute())
                .map(fullProd -> {
                    List<Review> reviews = new GetReviewsByProductCmd(reviewClient, fullProd.getId()).execute(); // reviewClient.getReviews(fullProd.getId());
                    ProductVO vo = new ProductVO();
                    vo.setId(fullProd.getId());
                    vo.setName(fullProd.getName());
                    vo.setDescription(fullProd.getDescription());
                    vo.setPrice(fullProd.getPrice());
                    vo.setSku(fullProd.getSku());
                    vo.setReviews(reviews);
                    return vo;
                })
                .collect(Collectors.toList());
    }
}
