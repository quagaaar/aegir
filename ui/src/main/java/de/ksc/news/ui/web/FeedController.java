package de.ksc.news.ui.web;

import de.ksc.news.ui.service.FeedService;
import de.ksc.news.ui.web.form.LoadFeedForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/feed")
public class FeedController {

    private static final Logger LOG = LoggerFactory.getLogger(FeedController.class);

    private final FeedService feedService;

    public FeedController(final FeedService feedService) {

        this.feedService = feedService;
    }

    @PostMapping("/load")
    public String load(@Valid @ModelAttribute("form") final LoadFeedForm form, final BindingResult result, final RedirectAttributes flash) {

        if (!result.hasErrors()) {
            final long numEntriesLoaded = this.feedService.load(form.getUrl());

            if (numEntriesLoaded == -1) {
                result.rejectValue("url", "Action");
            }
        }

        if (result.hasErrors()) {
            flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "form", result);
            flash.addFlashAttribute("form", form);
        }

        return "redirect:/";
    }
}
