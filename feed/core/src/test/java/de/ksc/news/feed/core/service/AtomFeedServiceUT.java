package de.ksc.news.feed.core.service;

import de.ksc.news.feed.core.domain.entity.Author;
import de.ksc.news.feed.core.domain.entity.Entry;
import de.ksc.news.feed.core.domain.entity.Source;
import de.ksc.news.feed.core.domain.repository.AuthorRepository;
import de.ksc.news.feed.core.domain.repository.EntryRepository;
import de.ksc.news.feed.core.domain.repository.SourceRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(AtomFeedService.class)
public class AtomFeedServiceUT {

    private static final Logger LOG = LoggerFactory.getLogger(AtomFeedServiceUT.class);

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private FeedService service;

    @MockBean
    private SourceRepository sourceRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private EntryRepository entryRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private final Random rnd = new Random();

    @Test
    public void emptyFeed() {

        final String url = createUrl();

        this.server
                .expect(requestTo(url))
                .andRespond(withSuccess(new ClassPathResource("/atom_feed_empty.xml"), MediaType.APPLICATION_ATOM_XML));

        final long result = service.load(url);

        assertThat(result, is(0L));

        verifyZeroInteractions(this.sourceRepository, this.authorRepository, this.entryRepository);
    }

    @Test
    public void invalidFeedContentType() {

        this.exception.expect(RestClientException.class);
        this.exception.expectMessage("no suitable HttpMessageConverter found");

        final String url = createUrl();

        this.server
                .expect(requestTo(url))
                .andRespond(withSuccess(UUID.randomUUID().toString(), MediaType.TEXT_PLAIN));

        service.load(url);

        fail("Expected Exception");
    }

    @Test
    public void failedFeedRequestClientError() {

        final HttpStatus errorStatus = HttpStatus.NOT_FOUND;

        this.exception.expect(HttpClientErrorException.class);
        this.exception.expectMessage(getHttpStatusMessage(errorStatus));

        final String url = createUrl();

        this.server
                .expect(requestTo(url))
                .andRespond(withStatus(errorStatus));

        service.load(url);

        fail("Expected Exception");
    }

    @Test
    public void failedFeedRequestServerError() {

        final HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        this.exception.expect(HttpServerErrorException.class);
        this.exception.expectMessage(getHttpStatusMessage(errorStatus));

        final String url = createUrl();

        this.server
                .expect(requestTo(url))
                .andRespond(withStatus(errorStatus));

        service.load(url);

        fail("Expected Exception");
    }

    @Test
    public void validFeed() {

        final String url = createUrl();

        this.server
                .expect(requestTo(url))
                .andRespond(withSuccess(new ClassPathResource("/atom_feed_valid.xml"), MediaType.APPLICATION_ATOM_XML));

        final Source savedSource = new Source();
        savedSource.setId(this.rnd.nextLong());
        when(this.sourceRepository.save(any(Source.class))).thenReturn(savedSource);

        final Author savedAuthor1 = new Author();
        savedAuthor1.setId(this.rnd.nextLong());
        savedAuthor1.setName(AtomFeedConstants.ENTRY_AUTHOR_NAME_1);
        when(this.authorRepository.save(refEq(savedAuthor1, "id", "email", "uri"))).thenReturn(savedAuthor1);

        final Author savedAuthor2 = new Author();
        savedAuthor2.setId(this.rnd.nextLong());
        savedAuthor2.setName(AtomFeedConstants.ENTRY_AUTHOR_NAME_2);
        when(this.authorRepository.save(refEq(savedAuthor2, "id", "email", "uri"))).thenReturn(savedAuthor2);

        final Entry savedEntry1 = new Entry();
        savedEntry1.setId(this.rnd.nextLong());

        final Entry savedEntry2 = new Entry();
        savedEntry2.setId(this.rnd.nextLong());

        final Iterator<Entry> savedEntries = Arrays.asList(savedEntry1, savedEntry2).iterator();
        when(this.entryRepository.save(any(Entry.class))).thenAnswer(__ -> savedEntries.hasNext() ? savedEntries.next() : null);

        final long result = service.load(url);

        assertThat(result, is(2L));

        final ArgumentCaptor<Source> sourceArg = ArgumentCaptor.forClass(Source.class);
        verify(this.sourceRepository).findBySourceId(AtomFeedConstants.FEED_ID);
        verify(this.sourceRepository).save(sourceArg.capture());
        verifyNoMoreInteractions(this.sourceRepository);

        final Source source = sourceArg.getValue();
        assertThat(source, is(notNullValue()));
        assertThat(source.getId(), is(0L));
        assertThat(source.getSourceId(), is(AtomFeedConstants.FEED_ID));
        assertThat(source.getTitle(), is(AtomFeedConstants.FEED_TITLE));
        assertThat(source.getSubtitle(), is(AtomFeedConstants.FEED_SUBTITLE));
        assertThat(source.getLink(), is(AtomFeedConstants.FEED_LINK_ALTERNATE));
        assertThat(source.getLogo(), is(AtomFeedConstants.FEED_LOGO));

        final ArgumentCaptor<Author> authorArg = ArgumentCaptor.forClass(Author.class);
        verify(this.authorRepository).findByName(AtomFeedConstants.ENTRY_AUTHOR_NAME_1);
        verify(this.authorRepository).findByName(AtomFeedConstants.ENTRY_AUTHOR_NAME_2);
        verify(this.authorRepository, times(2)).save(authorArg.capture());
        verifyNoMoreInteractions(this.authorRepository);

        final List<Author> authors = authorArg.getAllValues();
        assertThat(authors, is(notNullValue()));
        assertThat(authors.size(), is(2));
        assertThat(authors.get(0).getName(), is(AtomFeedConstants.ENTRY_AUTHOR_NAME_1));
        assertThat(authors.get(0).getEmail(), is(nullValue()));
        assertThat(authors.get(0).getUri(), is(nullValue()));
        assertThat(authors.get(1).getName(), is(AtomFeedConstants.ENTRY_AUTHOR_NAME_2));
        assertThat(authors.get(1).getEmail(), is(AtomFeedConstants.ENTRY_AUTHOR_EMAIL_2));
        assertThat(authors.get(1).getUri(), is(AtomFeedConstants.ENTRY_AUTHOR_URI_2));

        final ArgumentCaptor<Entry> entryArg = ArgumentCaptor.forClass(Entry.class);
        verify(this.entryRepository).findByEntryId(AtomFeedConstants.ENTRY_ID_1);
        verify(this.entryRepository).findByEntryId(AtomFeedConstants.ENTRY_ID_2);
        verify(this.entryRepository, times(2)).save(entryArg.capture());
        verifyNoMoreInteractions(this.entryRepository);

        final List<Entry> entries = entryArg.getAllValues();
        assertThat(entries, is(notNullValue()));
        assertThat(entries.size(), is(2));
        assertThat(entries.get(0).getEntryId(), is(AtomFeedConstants.ENTRY_ID_1));
        assertThat(entries.get(0).getTitle(), is(AtomFeedConstants.ENTRY_TITLE_1));
        assertThat(entries.get(0).getSummary(), is(AtomFeedConstants.ENTRY_SUMMARY_1));
        assertThat(entries.get(0).getLink(), is(AtomFeedConstants.ENTRY_LINK_ALTERNATE_1));
        assertThat(entries.get(0).getPublished(), is(AtomFeedConstants.ENTRY_PUBLISHED_1));
        assertThat(entries.get(0).getUpdated(), is(AtomFeedConstants.ENTRY_UPDATED_1));
        assertThat(entries.get(1).getEntryId(), is(AtomFeedConstants.ENTRY_ID_2));
        assertThat(entries.get(1).getTitle(), is(AtomFeedConstants.ENTRY_TITLE_2));
        assertThat(entries.get(1).getSummary(), is(AtomFeedConstants.ENTRY_SUMMARY_2));
        assertThat(entries.get(1).getLink(), is(AtomFeedConstants.ENTRY_LINK_ALTERNATE_2));
        assertThat(entries.get(1).getPublished(), is(AtomFeedConstants.ENTRY_PUBLISHED_2));
        assertThat(entries.get(1).getUpdated(), is(AtomFeedConstants.ENTRY_UPDATED_2));
    }

    private String createUrl() {

        return "http://" + UUID.randomUUID();
    }

    private String getHttpStatusMessage(final HttpStatus status) {

        return status.value() + " " + status.getReasonPhrase();
    }

    private Source createValidFeedSource() {

        final Source source = new Source();

        source.setSourceId(AtomFeedConstants.FEED_ID);

        return source;
    }
}
