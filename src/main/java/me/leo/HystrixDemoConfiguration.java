package me.leo;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class HystrixDemoConfiguration extends Configuration {
    @Valid
    @NotNull
    private JerseyClientConfiguration productJerseyClient = new JerseyClientConfiguration();

    @Valid
    @NotNull
    private JerseyClientConfiguration detailJerseyClient = new JerseyClientConfiguration();

    @Valid
    @NotNull
    private JerseyClientConfiguration reviewJerseyClient = new JerseyClientConfiguration();

    @JsonProperty("productJerseyClient")
    public JerseyClientConfiguration getProductJerseyClientConfiguration() {
        return productJerseyClient;
    }

    public void setProductJerseyClient(JerseyClientConfiguration productJerseyClient) {
        this.productJerseyClient = productJerseyClient;
    }

    @JsonProperty("detailJerseyClient")
    public JerseyClientConfiguration getDetailJerseyClientConfiguration() {
        return detailJerseyClient;
    }

    public void setDetailJerseyClient(JerseyClientConfiguration detailJerseyClient) {
        this.detailJerseyClient = detailJerseyClient;
    }

    @JsonProperty("reviewJerseyClient")
    public JerseyClientConfiguration getReviewJerseyClientConfiguration() {
        return reviewJerseyClient;
    }

    public void setReviewJerseyClient(JerseyClientConfiguration reviewJerseyClient) {
        this.reviewJerseyClient = reviewJerseyClient;
    }
}
