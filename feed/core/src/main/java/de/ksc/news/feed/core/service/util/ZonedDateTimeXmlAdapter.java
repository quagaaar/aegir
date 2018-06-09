package de.ksc.news.feed.core.service.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;

public class ZonedDateTimeXmlAdapter extends XmlAdapter<String, ZonedDateTime> {

    @Override
    public ZonedDateTime unmarshal(final String v) throws Exception {

        return ZonedDateTime.parse(v);
    }

    @Override
    public String marshal(final ZonedDateTime v) throws Exception {

        return v.toString();
    }
}
