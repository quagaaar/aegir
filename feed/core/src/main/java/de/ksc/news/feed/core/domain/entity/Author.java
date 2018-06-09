package de.ksc.news.feed.core.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private String email;

    private String uri;

    @OneToMany(mappedBy = "author")
    private List<Entry> entries;

    public long getId() {

        return this.id;
    }

    public void setId(final long id) {

        this.id = id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public String getEmail() {

        return this.email;
    }

    public void setEmail(final String email) {

        this.email = email;
    }

    public String getUri() {

        return this.uri;
    }

    public void setUri(final String uri) {

        this.uri = uri;
    }

    public List<Entry> getEntries() {

        return this.entries;
    }

    public void setEntries(final List<Entry> entries) {

        this.entries = entries;
    }

    @Override
    public String toString() {

        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
