package de.ksc.news.ui.service;

import de.ksc.news.feed.dto.FeedAuthor;
import de.ksc.news.feed.dto.FeedEntry;
import de.ksc.news.feed.dto.FeedItemsPage;
import de.ksc.news.feed.dto.FeedSource;
import de.ksc.news.ui.dto.Statistics;
import de.ksc.news.ui.service.client.FeedClient;
import org.springframework.stereotype.Service;

@Service
public class HttpFeedService implements FeedService {

    private final FeedClient feedClient;

    public HttpFeedService(final FeedClient feedClient) {

        this.feedClient = feedClient;
    }

    @Override
    public Statistics getStatistics() {

        final FeedItemsPage<FeedSource> sources = this.feedClient.sources(0, 0);
        final FeedItemsPage<FeedAuthor> authors = this.feedClient.authors(0, 0);
        final FeedItemsPage<FeedEntry> entries = this.feedClient.entries(0, 0);

        final Statistics statistics = new Statistics();

        statistics.totalSources = sources == null ? -1 : sources.totalItems;
        statistics.totalAuthors = authors == null ? -1 : authors.totalItems;
        statistics.totalEntries = entries == null ? -1 : entries.totalItems;

        return statistics;
    }

    @Override
    public long load(final String url) {

        return this.feedClient.load(url);
    }
}
