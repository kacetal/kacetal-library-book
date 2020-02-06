package com.kacetal.library.book.repository;

import com.kacetal.library.book.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Author entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
