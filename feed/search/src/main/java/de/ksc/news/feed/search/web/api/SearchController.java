package de.ksc.news.feed.search.web.api;

import de.ksc.news.feed.search.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(final SearchService searchService) {

        this.searchService = searchService;
    }

    @GetMapping("/indexes")
    public List<String> indexes() {

        return this.searchService.getIndexes();
    }
}
