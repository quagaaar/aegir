package de.ksc.news.feed.core.domain.repository;

import de.ksc.news.feed.core.domain.entity.Source;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SourceRepository extends PagingAndSortingRepository<Source, Long> {

    Optional<Source> findBySourceId(final String sourceId);
}
