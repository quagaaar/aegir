package de.ksc.news.feed.search.service;

import de.ksc.news.feed.search.dto.FeedDocument;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IndexService {

    void createIndex();

    void deleteIndex();

    Mono<Boolean> indexExists();

    void index(final List<FeedDocument> documents);
}
