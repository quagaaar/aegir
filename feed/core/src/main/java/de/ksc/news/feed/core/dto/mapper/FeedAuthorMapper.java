package de.ksc.news.feed.core.dto.mapper;

import de.ksc.news.feed.core.domain.entity.Author;
import de.ksc.news.feed.dto.FeedAuthor;

public class FeedAuthorMapper implements Mapper<Author, FeedAuthor> {

    @Override
    public Author toEntity(final FeedAuthor dto) {

        if (dto == null) {
            return null;
        }

        final Author entity = new Author();

        entity.setId(dto.id);
        entity.setName(dto.name);
        entity.setEmail(dto.email);
        entity.setUri(dto.uri);

        return entity;
    }

    @Override
    public FeedAuthor toDto(final Author entity) {

        final FeedAuthor dto = new FeedAuthor();

        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.email = entity.getEmail();
        dto.uri = entity.getUri();

        return dto;
    }
}
