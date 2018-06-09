package de.ksc.news.feed.dto;

import java.util.ArrayList;
import java.util.List;

public class FeedItemsPage<T> {

    public int page;
    public int totalPages;
    public long totalItems;
    public List<T> items;

    public static <T> FeedItemsPage<T> of(final int page, final int totalPages, final long totalItems, final List<T> items) {

        final FeedItemsPage<T> itemsPage = new FeedItemsPage<>();

        itemsPage.page = page;
        itemsPage.totalPages = totalPages;
        itemsPage.totalItems = totalItems;
        itemsPage.items = new ArrayList<>();

        if (items != null) {
            itemsPage.items.addAll(items);
        }

        return itemsPage;
    }
}
