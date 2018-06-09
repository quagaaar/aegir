package de.ksc.news.ui.service.client;

import de.ksc.news.feed.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedClientFallback implements FeedClient {

    private static final Logger LOG = LoggerFactory.getLogger(FeedClientFallback.class);

    @Override
    public long load(final String url) {

        LOG.warn("Feed load request failed - fallback invoked");
        return -1;
    }

    @Override
    public List<FeedSource> sources() {

        LOG.warn("Feed sources request failed - fallback invoked");
        return null;
    }

    @Override
    public FeedItemsPage<FeedSource> sources(final int page, final int size) {

        LOG.warn("Feed sources(page = {}, size = {}) request failed - fallback invoked", page, size);
        return null;
    }

    @Override
    public List<FeedAuthor> authors() {

        LOG.warn("Feed authors request failed - fallback invoked");
        return null;
    }

    @Override
    public FeedItemsPage<FeedAuthor> authors(final int page, final int size) {

        LOG.warn("Feed authors(page = {}, size = {}) request failed - fallback invoked", page, size);
        return null;
    }

    @Override
    public List<FeedEntry> entries() {

        LOG.warn("Feed entries request failed - fallback invoked");
        return null;
    }

    @Override
    public FeedItemsPage<FeedEntry> entries(final int page, final int size) {

        LOG.warn("Feed entries(page = {}, size = {}) request failed - fallback invoked", page, size);
        return null;
    }
}
