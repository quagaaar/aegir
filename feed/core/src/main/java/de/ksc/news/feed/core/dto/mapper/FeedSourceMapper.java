package de.ksc.news.feed.core.dto.mapper;

import de.ksc.news.feed.core.domain.entity.Source;
import de.ksc.news.feed.dto.FeedSource;

public class FeedSourceMapper implements Mapper<Source, FeedSource> {

    @Override
    public Source toEntity(final FeedSource dto) {

        if (dto == null) {
            return null;
        }

        final Source entity = new Source();

        entity.setId(dto.id);
        entity.setSourceId(dto.sourceId);
        entity.setTitle(dto.title);
        entity.setSubtitle(dto.subtitle);
        entity.setLink(dto.link);
        entity.setLogo(dto.logo);

        return entity;
    }

    @Override
    public FeedSource toDto(final Source entity) {

        final FeedSource dto = new FeedSource();

        dto.id = entity.getId();
        dto.sourceId = entity.getSourceId();
        dto.title = entity.getTitle();
        dto.subtitle = entity.getSubtitle();
        dto.link = entity.getLink();
        dto.logo = entity.getLogo();

        return dto;
    }
}
