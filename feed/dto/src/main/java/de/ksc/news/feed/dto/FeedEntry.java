package de.ksc.news.feed.dto;

import java.time.ZonedDateTime;

public class FeedEntry {

    public long id;
    public String entryId;
    public String title;
    public String summary;
    public String link;
    public ZonedDateTime published;
    public ZonedDateTime updated;
    public FeedAuthor author;
    public String copyright;
    public FeedSource source;
}
