package de.ksc.news.feed.core.service.atom;

import de.ksc.news.feed.core.service.util.ZonedDateTimeXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.ZonedDateTime;
import java.util.List;

@XmlRootElement(name = "entry")
public class AtomFeedEntry {

    @XmlElement
    public String id;

    @XmlElement
    public String title;

    @XmlElement
    public String summary;

    @XmlElement
    @XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
    public ZonedDateTime published;

    @XmlElement
    @XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
    public ZonedDateTime updated;

    @XmlElement(name = "link")
    public List<AtomFeedLink> links;

    @XmlElement
    public AtomFeedAuthor author;

    @Override
    public String toString() {

        return "AtomFeedEntry{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", published=" + published +
                ", updated=" + updated +
                ", links=" + links +
                ", author=" + author +
                '}';
    }
}
