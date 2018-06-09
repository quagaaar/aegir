package de.ksc.news.ui.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoadFeedForm {

    @NotNull
    @Pattern(regexp = "^(?i)\\s*https?://.+$")
    private String url;

    public String getUrl() {

        return this.url;
    }

    public void setUrl(final String url) {

        this.url = url;
    }

    @Override
    public String toString() {

        return "LoadFeedForm{" +
                "url='" + url + '\'' +
                '}';
    }

}
