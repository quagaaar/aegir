package de.ksc.news.feed.search.web.api;

import de.ksc.news.feed.search.service.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.*;
import static reactor.core.publisher.Mono.just;

@RestController
@RequestMapping("/index")
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    private final IndexService indexService;

    public IndexController(final IndexService indexService) {

        this.indexService = indexService;
    }

    @RequestMapping(method = RequestMethod.HEAD)
    public Mono<ResponseEntity<Void>> indexExists() {

        return this.indexService
                .indexExists()
                .flatMap(exists -> just(exists ? ok().<Void> build() : notFound().<Void> build()))
                .onErrorResume(error -> {
                    LOG.error("Error checking if search index exists: {}", error.getMessage(), error);
                    return just(status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
}
