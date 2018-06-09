package de.ksc.news.feed.core.domain.entity;

import de.ksc.news.feed.dto.FeedAuthor;
import de.ksc.news.feed.dto.FeedSource;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "entryId"))
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String entryId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String link;

    private ZonedDateTime published;

    private ZonedDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "authorId", nullable = false)
    private Author author;

    private String copyright;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sourceId", nullable = false)
    private Source source;

    public long getId() {

        return this.id;
    }

    public void setId(final long id) {

        this.id = id;
    }

    public String getEntryId() {

        return this.entryId;
    }

    public void setEntryId(final String entryId) {

        this.entryId = entryId;
    }

    public String getTitle() {

        return this.title;
    }

    public void setTitle(final String title) {

        this.title = title;
    }

    public String getSummary() {

        return this.summary;
    }

    public void setSummary(final String summary) {

        this.summary = summary;
    }

    public String getLink() {

        return this.link;
    }

    public void setLink(final String link) {

        this.link = link;
    }

    public ZonedDateTime getPublished() {

        return this.published;
    }

    public void setPublished(final ZonedDateTime published) {

        this.published = published;
    }

    public ZonedDateTime getUpdated() {

        return this.updated;
    }

    public void setUpdated(final ZonedDateTime updated) {

        this.updated = updated;
    }

    public Author getAuthor() {

        return this.author;
    }

    public void setAuthor(final Author author) {

        this.author = author;
    }

    public String getCopyright() {

        return this.copyright;
    }

    public void setCopyright(final String copyright) {

        this.copyright = copyright;
    }

    public Source getSource() {

        return this.source;
    }

    public void setSource(final Source source) {

        this.source = source;
    }


    @Override
    public String toString() {

        return "AtomFeedEntry{" +
                "id=" + id +
                ", entryId='" + entryId + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", link='" + link + '\'' +
                ", published=" + published +
                ", updated=" + updated +
                ", copyright='" + copyright + '\'' +
                '}';
    }
}
