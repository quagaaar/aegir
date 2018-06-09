package de.ksc.news.feed.core.service.atom;

import de.ksc.news.feed.core.service.util.ZonedDateTimeXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.List;

@XmlRootElement(name = "feed")
public class AtomFeed {

    @XmlElement
    public String id;

    @XmlElement
    public String title;

    @XmlElement
    public String subtitle;

    @XmlElement(name = "link")
    public List<AtomFeedLink> links;

    @XmlElement
    public String logo;

    @XmlElement
    @XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
    public ZonedDateTime updated;

    @XmlElement
    public AtomFeedAuthor author;

    @XmlElement
    public String rights;

    @XmlElement(name = "entry")
    public List<AtomFeedEntry> entries;

    @Override
    public String toString() {

        return "AtomFeed{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", links=" + links +
                ", logo='" + logo + '\'' +
                ", updated=" + updated +
                ", author=" + author +
                ", rights='" + rights + '\'' +
                ", entries=" + entries +
                '}';
    }
}
