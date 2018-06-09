package de.ksc.news.feed.core.service.atom;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "author")
public class AtomFeedAuthor {

    @XmlElement
    public String name;

    @XmlElement
    public String email;

    @XmlElement
    public String uri;

    @Override
    public String toString() {

        return "AtomFeedAuthor{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
