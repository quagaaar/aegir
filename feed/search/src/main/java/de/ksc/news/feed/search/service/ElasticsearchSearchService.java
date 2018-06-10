package de.ksc.news.feed.search.service;

import com.fasterxml.jackson.jr.ob.JSON;
import de.ksc.news.feed.search.exception.RuntimeIOException;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class ElasticsearchSearchService implements SearchService {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchSearchService.class);

    @Override
    public List<String> getIndexes() {

        final HttpHost[] hosts = { new HttpHost("localhost", 9200, "http") };

        try (final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(hosts))) {
            final Response response = client.getLowLevelClient().performRequest(HttpMethod.GET.name(), "_cat/indices");
            if (HttpStatus.resolve(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
                final String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                LOG.info("ES response: {}", json);
                return JSON.std.listOfFrom(String.class, json);
            }
        } catch (final IOException e) {
            throw new RuntimeIOException(e);
        }

        return emptyList();
    }
}
