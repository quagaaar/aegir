package de.ksc.news.feed.core.service;

import de.ksc.news.feed.core.domain.entity.Source;
import de.ksc.news.feed.core.domain.repository.SourceRepository;
import de.ksc.news.feed.dto.FeedItemsPage;
import de.ksc.news.feed.dto.FeedSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static de.ksc.news.feed.core.dto.mapper.Mappers.source;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Service
public class FeedSourceService implements ItemService<FeedSource> {

    private final SourceRepository sourceRepository;

    public FeedSourceService(final SourceRepository sourceRepository) {

        this.sourceRepository = sourceRepository;
    }

    @Override
    public long countItems() {

        return this.sourceRepository.count();
    }

    @Override
    public List<FeedSource> getAllItems() {

        return stream(this.sourceRepository.findAll().spliterator(), false)
                .map(source()::toDto)
                .collect(toList());
    }

    @Override
    public FeedItemsPage<FeedSource> getItems(final int page, final int size) {

        final Page<Source> result = this.sourceRepository.findAll(PageRequest.of(page, size));

        return FeedItemsPage.of(
                page,
                result.getTotalPages(),
                result.getTotalElements(),
                stream(result.spliterator(), false)
                        .map(source()::toDto)
                        .collect(toList()));
    }

    @Override
    public FeedSource getItem(final long id) {

        return source().toDto(this.sourceRepository.findById(id).orElse(null));
    }

    @Override
    public FeedSource deleteItem(final long id) {

        final Optional<Source> source = this.sourceRepository.findById(id);

        if (source.isPresent()) {
            this.sourceRepository.deleteById(id);
        }

        return source().toDto(source.orElse(null));
    }

    @Override
    public long deleteItems() {

        final long numSources = this.sourceRepository.count();
        this.sourceRepository.deleteAll();
        return numSources;
    }
}
