package com.kacetal.library.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

/**
 * A Book.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 13)
    @Column(name = "isbn", length = 13, nullable = false, unique = true)
    private String isbn;

    @NotNull
    @Size(max = 100)
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotNull
    @Column(name = "publish_date", nullable = false)
    private ZonedDateTime publishDate;

    @Lob
    @Column(name = "cover")
    private byte[] cover;

    @Column(name = "cover_content_type")
    private String coverContentType;

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "book_authors",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "authors_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("books")
    @ToString.Exclude
    private Set<Author> authors = new HashSet<>();

    @ManyToOne(fetch = LAZY)
    @JsonIgnoreProperties("books")
    private Publisher publisher;

    public void addAuthor(final Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(final Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
