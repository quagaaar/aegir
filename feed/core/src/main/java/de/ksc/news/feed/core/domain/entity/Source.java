package de.ksc.news.feed.core.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String sourceId;

    @Column(nullable = false)
    private String title;

    private String subtitle;

    private String link;

    private String logo;

    @OneToMany(mappedBy = "source")
    private List<Entry> entries;

    public long getId() {

        return this.id;
    }

    public void setId(final long id) {

        this.id = id;
    }

    public String getSourceId() {

        return this.sourceId;
    }

    public void setSourceId(final String sourceId) {

        this.sourceId = sourceId;
    }

    public String getTitle() {

        return this.title;
    }

    public void setTitle(final String title) {

        this.title = title;
    }

    public String getSubtitle() {

        return this.subtitle;
    }

    public void setSubtitle(final String subtitle) {

        this.subtitle = subtitle;
    }

    public String getLink() {

        return this.link;
    }

    public void setLink(final String link) {

        this.link = link;
    }

    public String getLogo() {

        return this.logo;
    }

    public void setLogo(final String logo) {

        this.logo = logo;
    }

    public List<Entry> getEntries() {

        return this.entries;
    }

    public void setEntries(final List<Entry> entries) {

        this.entries = entries;
    }

    @Override
    public String toString() {

        return "Source{" +
                "id=" + id +
                ", sourceId='" + sourceId + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", link='" + link + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
