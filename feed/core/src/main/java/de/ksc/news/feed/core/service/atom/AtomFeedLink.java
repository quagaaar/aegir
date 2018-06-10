package de.ksc.news.feed.core.service.atom;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "link")
public class AtomFeedLink {

    @XmlAttribute
    public String rel;

    @XmlAttribute
    public String type;

    @XmlAttribute
    public String href;

    @Override
    public String toString() {

        return "AtomFeedLink{" +
                "rel='" + rel + '\'' +
                ", type='" + type + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
