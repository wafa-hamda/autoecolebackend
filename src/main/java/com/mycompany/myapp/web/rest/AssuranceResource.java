package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Assurance;
import com.mycompany.myapp.repository.AssuranceRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Assurance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AssuranceResource {

    private final Logger log = LoggerFactory.getLogger(AssuranceResource.class);

    private static final String ENTITY_NAME = "assurance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssuranceRepository assuranceRepository;

    public AssuranceResource(AssuranceRepository assuranceRepository) {
        this.assuranceRepository = assuranceRepository;
    }

    /**
     * {@code POST  /assurances} : Create a new assurance.
     *
     * @param assurance the assurance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assurance, or with status {@code 400 (Bad Request)} if the assurance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assurances")
    public ResponseEntity<Assurance> createAssurance(@RequestBody Assurance assurance) throws URISyntaxException {
        log.debug("REST request to save Assurance : {}", assurance);
        if (assurance.getId() != null) {
            throw new BadRequestAlertException("A new assurance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Assurance result = assuranceRepository.save(assurance);
        return ResponseEntity.created(new URI("/api/assurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /assurances} : Updates an existing assurance.
     *
     * @param assurance the assurance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assurance,
     * or with status {@code 400 (Bad Request)} if the assurance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assurance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assurances")
    public ResponseEntity<Assurance> updateAssurance(@RequestBody Assurance assurance) throws URISyntaxException {
        log.debug("REST request to update Assurance : {}", assurance);
        if (assurance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Assurance result = assuranceRepository.save(assurance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assurance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /assurances} : get all the assurances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assurances in body.
     */
    @GetMapping("/assurances")
    public ResponseEntity<List<Assurance>> getAllAssurances(Pageable pageable) {
        log.debug("REST request to get a page of Assurances");
        Page<Assurance> page = assuranceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assurances/:id} : get the "id" assurance.
     *
     * @param id the id of the assurance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assurance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assurances/{id}")
    public ResponseEntity<Assurance> getAssurance(@PathVariable Long id) {
        log.debug("REST request to get Assurance : {}", id);
        Optional<Assurance> assurance = assuranceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(assurance);
    }

    /**
     * {@code DELETE  /assurances/:id} : delete the "id" assurance.
     *
     * @param id the id of the assurance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assurances/{id}")
    public ResponseEntity<Void> deleteAssurance(@PathVariable Long id) {
        log.debug("REST request to delete Assurance : {}", id);
        assuranceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
