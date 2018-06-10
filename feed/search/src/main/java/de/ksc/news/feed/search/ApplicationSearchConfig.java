package de.ksc.news.feed.search;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@Validated
@ConfigurationProperties("news.feed.search")
public class ApplicationSearchConfig implements SearchConfig {

    @NotNull
    @Size(min = 1)
    private final List<NodeConfig> nodes;

    public ApplicationSearchConfig() {

        this.nodes = new ArrayList<>();
    }

    public List<NodeConfig> getNodes() {

        return this.nodes;
    }

    @Override
    public List<Node> nodes() {

        return this.nodes.stream().map(Node.class::cast).collect(toList());
    }

    public static final class NodeConfig implements Node {

        @NotBlank
        private String host;

        @Min(1024)
        @Max(0xffff)
        private int port;

        @Pattern(regexp = "(?i)^https?$")
        private String scheme;

        public String getHost() {

            return this.host;
        }

        public void setHost(final String host) {

            this.host = host;
        }

        public int getPort() {

            return this.port;
        }

        public void setPort(final int port) {

            this.port = port;
        }

        public String getScheme() {

            return this.scheme;
        }

        public void setScheme(final String scheme) {

            this.scheme = scheme;
        }

        @Override
        public String host() {

            return this.host;
        }

        @Override
        public int port() {

            return this.port;
        }

        @Override
        public String scheme() {

            return this.scheme;
        }
    }
}
