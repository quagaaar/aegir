package de.ksc.news.ui.service;

import de.ksc.news.ui.dto.Statistics;

public interface FeedService {

    Statistics getStatistics();

    long load(final String url);
}
