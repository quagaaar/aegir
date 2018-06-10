package de.ksc.news.feed.search;

import java.util.List;

public interface SearchConfig {

    List<Node> nodes();

    interface Node {

        String host();

        int port();

        String scheme();
    }
}
