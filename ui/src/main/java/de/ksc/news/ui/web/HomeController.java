package de.ksc.news.ui.web;

import de.ksc.news.ui.dto.Statistics;
import de.ksc.news.ui.service.FeedService;
import de.ksc.news.ui.web.form.LoadFeedForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    private final FeedService feedService;

    public HomeController(final FeedService feedService) {

        this.feedService = feedService;
    }

    @GetMapping
    public String home() {

        return "index";
    }

    @ModelAttribute("page")
    private String page() {

        return "home/home";
    }

    @ModelAttribute("statistics")
    private Statistics statistics() {

        LOG.info("*** Load statistics ***");
        return this.feedService.getStatistics();
    }

    @ModelAttribute("form")
    private LoadFeedForm form() {

        return new LoadFeedForm();
    }

}
