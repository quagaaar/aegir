package de.ksc.news.feed.core.dto.mapper;

public final class Mappers {

    private Mappers() {

        // Empty
    }

    public static FeedSourceMapper source() {

        return new FeedSourceMapper();
    }

    public static FeedEntryMapper entry() {

        return new FeedEntryMapper();
    }

    public static FeedAuthorMapper author() {

        return new FeedAuthorMapper();
    }
}
