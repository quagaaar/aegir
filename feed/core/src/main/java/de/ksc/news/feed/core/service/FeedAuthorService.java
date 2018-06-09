package de.ksc.news.feed.core.service;

import de.ksc.news.feed.core.domain.entity.Author;
import de.ksc.news.feed.core.domain.repository.AuthorRepository;
import de.ksc.news.feed.dto.FeedAuthor;
import de.ksc.news.feed.dto.FeedItemsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static de.ksc.news.feed.core.dto.mapper.Mappers.author;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Service
public class FeedAuthorService implements ItemService<FeedAuthor> {

    private final AuthorRepository authorRepository;

    public FeedAuthorService(final AuthorRepository authorRepository) {

        this.authorRepository = authorRepository;
    }

    @Override
    public long countItems() {

        return this.authorRepository.count();
    }

    @Override
    public List<FeedAuthor> getAllItems() {

        return stream(this.authorRepository.findAll().spliterator(), false)
                .map(author()::toDto)
                .collect(toList());
    }

    @Override
    public FeedItemsPage<FeedAuthor> getItems(final int page, final int size) {

        final Page<Author> result = this.authorRepository.findAll(PageRequest.of(page, size));
        return FeedItemsPage.of(
                page,
                result.getTotalPages(),
                result.getTotalElements(),
                stream(result.spliterator(), false)
                        .map(author()::toDto)
                        .collect(toList()));
    }

    @Override
    public FeedAuthor getItem(final long id) {

        return author().toDto(this.authorRepository.findById(id).orElse(null));
    }

    @Override
    public FeedAuthor deleteItem(final long id) {

        final Optional<Author> author = this.authorRepository.findById(id);

        if (author.isPresent()) {
            this.authorRepository.deleteById(id);
        }
        
        return author().toDto(author.orElse(null));
    }

    @Override
    public long deleteItems() {

        final long numAuthors = this.authorRepository.count();
        this.authorRepository.deleteAll();
        return numAuthors;
    }
}
