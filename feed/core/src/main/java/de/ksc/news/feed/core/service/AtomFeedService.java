package de.ksc.news.feed.core.service;

import de.ksc.news.feed.core.domain.entity.Author;
import de.ksc.news.feed.core.domain.entity.Entry;
import de.ksc.news.feed.core.domain.entity.Source;
import de.ksc.news.feed.core.domain.repository.AuthorRepository;
import de.ksc.news.feed.core.domain.repository.EntryRepository;
import de.ksc.news.feed.core.domain.repository.SourceRepository;
import de.ksc.news.feed.core.service.atom.AtomFeed;
import de.ksc.news.feed.core.service.atom.AtomFeedAuthor;
import de.ksc.news.feed.core.service.atom.AtomFeedEntry;
import de.ksc.news.feed.core.service.atom.AtomFeedLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.*;
import static org.springframework.util.Assert.notNull;

@Service
public class AtomFeedService implements FeedService {

    private static final Logger LOG = LoggerFactory.getLogger(AtomFeedService.class);

    public static final String FEED_LINK_REL_ALTERNATE = "alternate";

    private final SourceRepository sourceRepository;
    private final AuthorRepository authorRepository;
    private final EntryRepository entryRepository;
    private final RestTemplate restTemplate;

    public AtomFeedService(
            final SourceRepository sourceRepository,
            final AuthorRepository authorRepository,
            final EntryRepository entryRepository,
            final RestTemplateBuilder restTemplateBuilder) {

        this.sourceRepository = sourceRepository;
        this.authorRepository = authorRepository;
        this.entryRepository = entryRepository;

        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public long load(final String url) {

        LOG.info("Loading atom feed from URL '{}'", url);

        notNull(url, "AtomFeed URL must not be null");

        final AtomFeed feed = this.restTemplate.getForObject(url, AtomFeed.class);

        LOG.info("Loaded Atom feed from URL '{}'", url);

        LOG.debug("Atom feed: {}", feed);

        if (isNotBlank(feed.id)) {
            final Source source = createOrGetSource(feed);
            final Author defaultAuthor = createOrGetAuthor(feed.author);

            return Optional.ofNullable(feed.entries)
                    .orElse(emptyList())
                    .stream()
                    .peek(entry -> LOG.debug("Processing atom feed entry {} ({})", entry.id, entry.title))
                    .map(entry -> createEntryIfNotExists(source, defaultAuthor, entry))
                    .filter(Objects::nonNull)
                    .peek(entry -> LOG.debug("Created entry {} for atom feed entry {} ({})", entry.getId(), entry.getEntryId(), entry.getTitle()))
                    .count();
        }

        return 0;
    }

    private Source createOrGetSource(final AtomFeed atomFeed) {

        notNull(atomFeed, "Atom feed must not be null");
        notNull(atomFeed.id, "Atom feed ID must not be null");

        final Optional<Source> source = this.sourceRepository.findBySourceId(atomFeed.id);

        if (source.isPresent()) {
            return source.get();
        }

        final Source feedSource = new Source();

        feedSource.setSourceId(atomFeed.id);
        feedSource.setTitle(defaultString(atomFeed.title));
        feedSource.setSubtitle(atomFeed.subtitle);
        feedSource.setLink(
                Optional.ofNullable(atomFeed.links)
                        .orElse(emptyList())
                        .stream()
                        .filter(link -> FEED_LINK_REL_ALTERNATE.equals(link.rel))
                        .findFirst()
                        .orElse(new AtomFeedLink())
                        .href);
        feedSource.setLogo(atomFeed.logo);

        return this.sourceRepository.save(feedSource);
    }

    private Author createOrGetAuthor(final AtomFeedAuthor atomFeedAuthor) {

        if (atomFeedAuthor == null) {
            return null;
        }

        final Optional<Author> author = this.authorRepository.findByName(atomFeedAuthor.name);

        if (author.isPresent()) {
            return author.get();
        }

        final Author feedAuthor = new Author();

        feedAuthor.setName(atomFeedAuthor.name);
        feedAuthor.setEmail(atomFeedAuthor.email);
        feedAuthor.setUri(atomFeedAuthor.uri);

        return this.authorRepository.save(feedAuthor);
    }

    private Entry createEntryIfNotExists(final Source source, final Author defaultAuthor, final AtomFeedEntry atomFeedEntry) {

        if (atomFeedEntry == null) {
            return null;
        }

        final Optional<Entry> entry = this.entryRepository.findByEntryId(atomFeedEntry.id);

        if (entry.isPresent()) {
            return entry.get();
        }

        final Entry feedEntry = new Entry();

        feedEntry.setEntryId(atomFeedEntry.id);
        feedEntry.setTitle(abbreviate(atomFeedEntry.title, 255));
        feedEntry.setSummary(abbreviate(atomFeedEntry.summary, 2048));
        feedEntry.setLink(
                Optional.ofNullable(atomFeedEntry.links)
                        .orElse(emptyList())
                        .stream()
                        .filter(link -> FEED_LINK_REL_ALTERNATE.equals(link.rel))
                        .findFirst()
                        .orElse(new AtomFeedLink())
                        .href
        );
        feedEntry.setPublished(atomFeedEntry.published);
        feedEntry.setUpdated(atomFeedEntry.updated);
        feedEntry.setAuthor(Optional.ofNullable(createOrGetAuthor(atomFeedEntry.author)).orElse(defaultAuthor));
        feedEntry.setSource(source);

        return this.entryRepository.save(feedEntry);
    }
}
