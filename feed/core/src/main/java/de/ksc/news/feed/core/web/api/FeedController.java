package de.ksc.news.feed.core.web.api;

import de.ksc.news.feed.dto.FeedAuthor;
import de.ksc.news.feed.dto.FeedEntry;
import de.ksc.news.feed.dto.FeedItemsPage;
import de.ksc.news.feed.dto.FeedSource;
import de.ksc.news.feed.core.service.FeedService;
import de.ksc.news.feed.core.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedController.class);

    private final FeedService feedService;
    private final ItemService<FeedSource> sourceService;
    private final ItemService<FeedAuthor> authorService;
    private final ItemService<FeedEntry> entryService;

    public FeedController(
            final FeedService feedService,
            final ItemService<FeedSource> sourceService,
            final ItemService<FeedAuthor> authorService,
            final ItemService<FeedEntry> entryService) {

        this.feedService = feedService;
        this.sourceService = sourceService;
        this.authorService = authorService;
        this.entryService = entryService;
    }

    @PostMapping("/load")
    public long load(@RequestParam final String url) {

        return this.feedService.load(url);
    }

    @GetMapping("/sources")
    public List<FeedSource> sources() {

        return this.sourceService.getAllItems();
    }

    @GetMapping("/sources/page")
    public FeedItemsPage<FeedSource> sources(final Pageable pageable) {

        LOG.info("*** Pageable: {} ***", pageable);

        return this.sourceService.getItems(pageable.getPageNumber(), pageable.getPageSize());
    }

    @GetMapping("/authors")
    public List<FeedAuthor> authors() {

        return this.authorService.getAllItems();
    }

    @GetMapping("/authors/page")
    public FeedItemsPage<FeedAuthor> authors(final Pageable pageable) {

        return this.authorService.getItems(pageable.getPageNumber(), pageable.getPageSize());
    }

    @GetMapping("/entries")
    public List<FeedEntry> entries() {

        return this.entryService.getAllItems();
    }

    @GetMapping("/entries/page")
    public FeedItemsPage<FeedEntry> entries(final Pageable pageable) {

        return this.entryService.getItems(pageable.getPageNumber(), pageable.getPageSize());
    }
}
