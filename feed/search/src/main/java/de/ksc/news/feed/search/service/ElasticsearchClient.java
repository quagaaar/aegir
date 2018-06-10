package de.ksc.news.feed.search.service;

import de.ksc.news.feed.search.SearchConfig;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.notNull;

public final class ElasticsearchClient {

    private ElasticsearchClient() {

        // Empty
    }

    public static RestHighLevelClient from(final SearchConfig config) {

        return new RestHighLevelClient(
                RestClient.builder(
                        config
                                .nodes()
                                .stream()
                                .map(ElasticsearchClient::toHttpHost)
                                .toArray(HttpHost[]::new))
        .setRequestConfigCallback(request -> request
                .setConnectTimeout(2000)
                .setConnectionRequestTimeout(2000)
                .setSocketTimeout(2000)
        )
        .setMaxRetryTimeoutMillis(2000));
    }

    private static HttpHost toHttpHost(final SearchConfig.Node node) {

        notNull(node, "Node must not be null");

        return new HttpHost(node.host(), node.port(), node.scheme());
    }
}
