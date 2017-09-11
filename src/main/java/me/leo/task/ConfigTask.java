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
        ImmutableList<String> list = immutableMultimap.get("product_latency").asList();
        if (!list.isEmpty()) {
            String latencyStr = list.get(0);
            Integer latency = Integer.valueOf(latencyStr);
            Config.LIST_LATENCY = latency;
            printWriter.write("ProductListCmd latency set\n\n");
        }
        list = immutableMultimap.get("product_timeout").asList();
        if (!list.isEmpty()) {
            String timeoutStr = list.get(0);
            Integer timeout = Integer.valueOf(timeoutStr);
            Config.LIST_TIMEOUT = timeout;
            printWriter.write("ProductListCmd timeout set\n\n");
        }
        list = immutableMultimap.get("product_cb_open").asList();
        if (!list.isEmpty()) {
            String cbOpenStr = list.get(0);
            if (cbOpenStr.equalsIgnoreCase("null")) {
                Config.LIST_CB_OPEN = null;
            } else {
                boolean cbOpen = Boolean.valueOf(cbOpenStr);
                Config.LIST_CB_OPEN = cbOpen;
            }
            printWriter.write("ProductListCmd Circuit Breaker " + (Config.LIST_CB_OPEN == null ? "unset" : (Config.LIST_CB_OPEN ? "opened" : "closed")) + "\n\n");
        }

        // detail command
        list = immutableMultimap.get("detail_latency").asList();
        if (!list.isEmpty()) {
            String latencyStr = list.get(0);
            Integer latency = Integer.valueOf(latencyStr);
            Config.DETAIL_LATENCY = latency;
            printWriter.write("DetailCmd latency set\n\n");
        }
        list = immutableMultimap.get("detail_timeout").asList();
        if (!list.isEmpty()) {
            String timeoutStr = list.get(0);
            Integer timeout = Integer.valueOf(timeoutStr);
            Config.DETAIL_TIMEOUT = timeout;
            printWriter.write("DetailCmd timeout set\n\n");
        }
        list = immutableMultimap.get("detail_cb_open").asList();
        if (!list.isEmpty()) {
            String cbOpenStr = list.get(0);
            if (cbOpenStr.equalsIgnoreCase("null")) {
                Config.DETAIL_CB_OPEN = null;
            } else {
                boolean cbOpen = Boolean.valueOf(cbOpenStr);
                Config.DETAIL_CB_OPEN = cbOpen;
            }
            printWriter.write("DetailCmd Circuit Breaker " + (Config.DETAIL_CB_OPEN == null ? "unset" : (Config.DETAIL_CB_OPEN ? "opened" : "closed")) + "\n\n");
        }

        // review command
        list = immutableMultimap.get("review_latency").asList();
        if (!list.isEmpty()) {
            String latencyStr = list.get(0);
            Integer latency = Integer.valueOf(latencyStr);
            Config.REVIEW_LATENCY = latency;
            printWriter.write("GetReviewsByProductCmd latency set\n\n");
        }
        list = immutableMultimap.get("review_timeout").asList();
        if (!list.isEmpty()) {
            String timeoutStr = list.get(0);
            Integer timeout = Integer.valueOf(timeoutStr);
            Config.REVIEW_TIMEOUT = timeout;
            printWriter.write("GetReviewsByProductCmd timeout set\n\n");
        }
        list = immutableMultimap.get("review_cb_open").asList();
        if (!list.isEmpty()) {
            String cbOpenStr = list.get(0);
            if (cbOpenStr.equalsIgnoreCase("null")) {
                Config.REVIEW_CB_OPEN = null;
            } else {
                boolean cbOpen = Boolean.valueOf(cbOpenStr);
                Config.REVIEW_CB_OPEN = cbOpen;
            }
            printWriter.write("GetReviewsByProductCmd Circuit Breaker " + (Config.REVIEW_CB_OPEN == null ? "unset" : (Config.REVIEW_CB_OPEN ? "opened" : "closed")) + "\n\n");
        }
        list = immutableMultimap.get("review_fallback").asList();
        if (!list.isEmpty()) {
            String fallbackStr = list.get(0);
            Config.REVIEW_FALLBACK = Boolean.valueOf(fallbackStr);
            printWriter.write("GetReviewsByProductCmd Fallback " + (Config.REVIEW_FALLBACK ? "enabled" : "disabled") + "\n\n");
        }

    }
}
