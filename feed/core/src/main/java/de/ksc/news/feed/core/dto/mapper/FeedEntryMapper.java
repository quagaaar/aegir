package de.ksc.news.feed.core.dto.mapper;

import de.ksc.news.feed.core.domain.entity.Entry;
import de.ksc.news.feed.dto.FeedEntry;

public class FeedEntryMapper implements Mapper<Entry, FeedEntry> {

    @Override
    public Entry toEntity(final FeedEntry dto) {

        if (dto == null) {
            return null;
        }

        final Entry entity = new Entry();

        entity.setId(dto.id);
        entity.setEntryId(dto.entryId);
        entity.setTitle(dto.title);
        entity.setSummary(dto.summary);
        entity.setLink(dto.link);
        entity.setPublished(dto.published);
        entity.setUpdated(dto.updated);
        entity.setCopyright(dto.copyright);

        return entity;
    }

    @Override
    public FeedEntry toDto(final Entry entity) {

        final FeedEntry dto = new FeedEntry();

        dto.id = entity.getId();
        dto.entryId = entity.getEntryId();
        dto.title = entity.getTitle();
        dto.summary = entity.getSummary();
        dto.link = entity.getLink();
        dto.published = entity.getPublished();
        dto.updated = entity.getUpdated();
        dto.copyright = entity.getCopyright();

        return dto;
    }
}
