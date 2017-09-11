package me.leo.task;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import me.leo.Config;

/**
 * Created by leo on 9/10/17.
 */
public class ConfigTask extends Task {
    public ConfigTask() {
        super("config");
    }

    @Override
    public void execute(ImmutableMultimap<String, String> immutableMultimap, PrintWriter printWriter) throws Exception {
        ImmutableList<String> latencyList = immutableMultimap.get("product_latency").asList();
        if (!latencyList.isEmpty()) {
            String latencyStr = latencyList.get(0);
            Integer latency = Integer.valueOf(latencyStr);
            Config.LIST_LATENCY = latency;
            printWriter.write("ProductListCmd latency set\n\n");
        }
        latencyList = immutableMultimap.get("product_timeout").asList();
        if (!latencyList.isEmpty()) {
            String timeoutStr = latencyList.get(0);
            Integer timeout = Integer.valueOf(timeoutStr);
            Config.LIST_TIMEOUT = timeout;
            printWriter.write("ProductListCmd timeout set\n\n");
        }
        latencyList = immutableMultimap.get("product_cb_open").asList();
        if (!latencyList.isEmpty()) {
            String cbOpenStr = latencyList.get(0);
            boolean cbOpen = Boolean.valueOf(cbOpenStr);
            Config.LIST_CB_OPEN = cbOpen;
            printWriter.write("ProductListCmd Circuit Breaker " + (Config.LIST_CB_OPEN ? "opened" : "closed") + "\n\n");
        }

        ImmutableList<String> detail = immutableMultimap.get("detail_latency").asList();
        if (!detail.isEmpty()) {
            String latencyStr = detail.get(0);
            Integer latency = Integer.valueOf(latencyStr);
            Config.DETAIL_LATENCY = latency;
            printWriter.write("DetailCmd latency set\n\n");
        }
        detail = immutableMultimap.get("detail_timeout").asList();
        if (!detail.isEmpty()) {
            String timeoutStr = detail.get(0);
            Integer timeout = Integer.valueOf(timeoutStr);
            Config.DETAIL_TIMEOUT = timeout;
            printWriter.write("DetailCmd timeout set\n\n");
        }
        detail = immutableMultimap.get("detail_cb_open").asList();
        if (!detail.isEmpty()) {
            String cbOpenStr = latencyList.get(0);
            boolean cbOpen = Boolean.valueOf(cbOpenStr);
            Config.DETAIL_CB_OPEN = cbOpen;
            printWriter.write("DetailCmd Circuit Breaker " + (Config.DETAIL_CB_OPEN ? "opened" : "closed") + "\n\n");
        }


        ImmutableList<String> review = immutableMultimap.get("review_latency").asList();
        if (!review.isEmpty()) {
            String latencyStr = review.get(0);
            Integer latency = Integer.valueOf(latencyStr);
            Config.REVIEW_LATENCY = latency;
            printWriter.write("GetReviewsByProductCmd latency set\n\n");
        }
        review = immutableMultimap.get("review_timeout").asList();
        if (!review.isEmpty()) {
            String timeoutStr = review.get(0);
            Integer timeout = Integer.valueOf(timeoutStr);
            Config.REVIEW_TIMEOUT = timeout;
            printWriter.write("GetReviewsByProductCmd timeout set\n\n");
        }
        review = immutableMultimap.get("review_cb_open").asList();
        if (!review.isEmpty()) {
            String cbOpenStr = review.get(0);
            boolean cbOpen = Boolean.valueOf(cbOpenStr);
            Config.REVIEW_CB_OPEN = cbOpen;
            printWriter.write("GetReviewsByProductCmd Circuit Breaker " + (Config.REVIEW_CB_OPEN ? "opened" : "closed") + "\n\n");
        }
        review = immutableMultimap.get("review_fallback").asList();
        if (!review.isEmpty()) {
            String fallbackStr = review.get(0);
            Config.REVIEW_FALLBACK = Boolean.valueOf(fallbackStr);
            printWriter.write("GetReviewsByProductCmd Fallback " + (Config.REVIEW_FALLBACK ? "enabled" : "disabled") + "\n\n");
        }

    }
}
