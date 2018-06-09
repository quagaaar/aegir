package de.ksc.news.feed.core.service;

import de.ksc.news.feed.dto.FeedItemsPage;

import java.util.List;

public interface ItemService<T> {

    long countItems();

    List<T> getAllItems();

    FeedItemsPage<T> getItems(final int page, final int size);

    T getItem(final long id);

    T deleteItem(final long id);

    long deleteItems();
}
