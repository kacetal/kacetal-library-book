package com.kacetal.library.book.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/**
 * A Publisher.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "publisher")
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "publisher", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Book> books = new HashSet<>();

    public void addBook(final Book book) {
        this.books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(final Book book) {
        this.books.remove(book);
        book.setPublisher(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publisher)) {
            return false;
        }
        return id != null && id.equals(((Publisher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
