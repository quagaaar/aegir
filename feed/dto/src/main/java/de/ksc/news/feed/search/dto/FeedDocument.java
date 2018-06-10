package de.ksc.news.feed.search.dto;

import java.time.ZonedDateTime;

public class FeedDocument {

    public long id;
    public String entryId;
    public String title;
    public String summary;
    public String link;
    public ZonedDateTime published;
    public ZonedDateTime updated;
    public long authorId;
    public String authorName;
    public String authorEmail;
    public String authorUri;
    public String copyright;
    public long sourceId;
    public String sourceSourceId;
    public String sourceTitle;
    public String sourceSubtitle;
    public String sourceLink;
    public String sourceLogo;
}
