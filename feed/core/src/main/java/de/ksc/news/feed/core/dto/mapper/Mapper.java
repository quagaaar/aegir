package de.ksc.news.feed.core.dto.mapper;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.notNull;

public interface Mapper<E, D> {

    default List<E> toEntity(final List<D> dtos) {

        notNull(dtos, "List of DTOs must not be null");

        if (dtos.isEmpty()) {
            return emptyList();
        }

        return dtos.stream().map(this::toEntity).collect(toList());
    }

    E toEntity(final D dto);

    default List<D> toDto(final List<E> entities) {

        notNull(entities, "List of entities must not be null");

        if (entities.isEmpty()) {
            return emptyList();
        }

        return entities.stream().map(this::toDto).collect(toList());
    }

    D toDto(final E entity);
}
