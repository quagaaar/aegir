package de.ksc.news.feed.core.domain.repository;

import de.ksc.news.feed.core.domain.entity.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntryRepository extends PagingAndSortingRepository<Entry, Long> {

    Optional<Entry> findByEntryId(final String entryId);
}
