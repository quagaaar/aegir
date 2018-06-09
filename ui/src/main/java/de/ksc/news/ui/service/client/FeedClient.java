package de.ksc.news.ui.service.client;

import de.ksc.news.feed.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "feeds", fallback = FeedClientFallback.class)
public interface FeedClient {

    @PostMapping("/feed/load")
    long load(@RequestParam("url") final String url);

    @GetMapping("/feed/sources")
    List<FeedSource> sources();

    @GetMapping("/feed/sources/page")
    FeedItemsPage<FeedSource> sources(@RequestParam("page") final int page, @RequestParam("size") final int size);

    @GetMapping("/feed/authors")
    List<FeedAuthor> authors();

    @GetMapping("/feed/authors/page")
    FeedItemsPage<FeedAuthor> authors(@RequestParam("page") final int page, @RequestParam("size") final int size);

    @GetMapping("/feed/entries")
    List<FeedEntry> entries();

    @GetMapping("/feed/entries/page")
    FeedItemsPage<FeedEntry> entries(@RequestParam("page") final int page, @RequestParam("size") final int size);
}
