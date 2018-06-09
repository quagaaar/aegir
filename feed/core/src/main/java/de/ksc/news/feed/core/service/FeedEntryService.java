package de.ksc.news.feed.core.service;

import de.ksc.news.feed.core.domain.entity.Entry;
import de.ksc.news.feed.core.domain.repository.EntryRepository;
import de.ksc.news.feed.dto.FeedEntry;
import de.ksc.news.feed.dto.FeedItemsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static de.ksc.news.feed.core.dto.mapper.Mappers.entry;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Service
public class FeedEntryService implements ItemService<FeedEntry> {

    private final EntryRepository entryRepository;

    public FeedEntryService(final EntryRepository entryRepository) {

        this.entryRepository = entryRepository;
    }

    @Override
    public long countItems() {

        return this.entryRepository.count();
    }

    @Override
    public List<FeedEntry> getAllItems() {

        return stream(this.entryRepository.findAll().spliterator(), false)
                .map(entry()::toDto)
                .collect(toList());
    }

    @Override
    public FeedItemsPage<FeedEntry> getItems(final int page, final int size) {

        final Page<Entry> result = this.entryRepository.findAll(PageRequest.of(page, size));
        return FeedItemsPage.of(
                page,
                result.getTotalPages(),
                result.getTotalElements(),
                stream(result.spliterator(), false)
                    .map(entry()::toDto)
                    .collect(toList()));
    }

    @Override
    public FeedEntry getItem(final long id) {

        return entry().toDto(this.entryRepository.findById(id).orElse(null));
    }

    @Override
    public FeedEntry deleteItem(final long id) {

        final Optional<Entry> source = this.entryRepository.findById(id);

        if (source.isPresent()) {
            this.entryRepository.deleteById(id);
        }

        return entry().toDto(source.orElse(null));
    }

    @Override
    public long deleteItems() {

        final long numEntries = this.entryRepository.count();
        this.entryRepository.deleteAll();
        return numEntries;
    }
}
