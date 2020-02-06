package com.kacetal.library.book.web.rest;

import com.kacetal.library.book.domain.Publisher;
import com.kacetal.library.book.service.PublisherService;
import com.kacetal.library.book.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kacetal.library.book.domain.Publisher}.
 */
@RestController
@RequestMapping("/api")
public class PublisherResource {

    private static final String ENTITY_NAME = "kacetalLibraryBookPublisher";

    private final Logger log = LoggerFactory.getLogger(PublisherResource.class);

    private final PublisherService publisherService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public PublisherResource(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    /**
     * {@code POST  /publishers} : Create a new publisher.
     *
     * @param publisher the publisher to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publisher, or with status {@code 400 (Bad Request)} if the publisher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/publishers")
    public ResponseEntity<Publisher> createPublisher(@Valid @RequestBody Publisher publisher) throws URISyntaxException {
        log.debug("REST request to save Publisher : {}", publisher);
        if (publisher.getId() != null) {
            throw new BadRequestAlertException("A new publisher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Publisher result = publisherService.save(publisher);
        return ResponseEntity.created(new URI("/api/publishers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /publishers} : Updates an existing publisher.
     *
     * @param publisher the publisher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publisher,
     * or with status {@code 400 (Bad Request)} if the publisher is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publisher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/publishers")
    public ResponseEntity<Publisher> updatePublisher(@Valid @RequestBody Publisher publisher) throws URISyntaxException {
        log.debug("REST request to update Publisher : {}", publisher);
        if (publisher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Publisher result = publisherService.save(publisher);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publisher.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /publishers} : get all the publishers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publishers in body.
     */
    @GetMapping("/publishers")
    public ResponseEntity<List<Publisher>> getAllPublishers(Pageable pageable) {
        log.debug("REST request to get a page of Publishers");
        Page<Publisher> page = publisherService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /publishers/:id} : get the "id" publisher.
     *
     * @param id the id of the publisher to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publisher, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/publishers/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable Long id) {
        log.debug("REST request to get Publisher : {}", id);
        Optional<Publisher> publisher = publisherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publisher);
    }

    /**
     * {@code DELETE  /publishers/:id} : delete the "id" publisher.
     *
     * @param id the id of the publisher to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        log.debug("REST request to delete Publisher : {}", id);
        publisherService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
