package de.ksc.news.feed.search.service;

import de.ksc.news.feed.search.SearchConfig;
import de.ksc.news.feed.search.dto.FeedDocument;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.io.IOException;
import java.util.List;

@Service
public class ElasticsearchIndexService implements IndexService {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final SearchConfig config;

    public ElasticsearchIndexService(final SearchConfig config) {

        this.config = config;
    }

    @Override
    public void createIndex() {

    }

    @Override
    public void deleteIndex() {

    }

    @Override
    public Mono<Boolean> indexExists() {

        return Mono.create(this::indexExistsAsync);
    }

    @Override
    public void index(final List<FeedDocument> documents) {

    }

    private void indexExistsAsync(final MonoSink<Boolean> sink) {

        final RestHighLevelClient client = ElasticsearchClient.from(this.config);

        client.getLowLevelClient().performRequestAsync("HEAD"/*HttpMethod.HEAD.name()*/, "/news", new ResponseListener() {
            @Override
            public void onSuccess(final Response response) {

                closeConnection();
                sink.success(HttpStatus.resolve(response.getStatusLine().getStatusCode()) == HttpStatus.OK);
            }

            @Override
            public void onFailure(final Exception e) {

                closeConnection();
                sink.error(e);
            }

            private void closeConnection() {

                try {
                    client.close();
                } catch (final IOException e) {
                    LOG.error("Error closing Elasticsearch connection: {}", e.getMessage(), e);
                }
            }
        });
    }
}
