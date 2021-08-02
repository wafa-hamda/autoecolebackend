package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Entretien;
import com.mycompany.myapp.repository.EntretienRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Entretien}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntretienResource {

    private final Logger log = LoggerFactory.getLogger(EntretienResource.class);

    private static final String ENTITY_NAME = "entretien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntretienRepository entretienRepository;

    public EntretienResource(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    /**
     * {@code POST  /entretiens} : Create a new entretien.
     *
     * @param entretien the entretien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entretien, or with status {@code 400 (Bad Request)} if the entretien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entretiens")
    public ResponseEntity<Entretien> createEntretien(@RequestBody Entretien entretien) throws URISyntaxException {
        log.debug("REST request to save Entretien : {}", entretien);
        if (entretien.getId() != null) {
            throw new BadRequestAlertException("A new entretien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entretien result = entretienRepository.save(entretien);
        return ResponseEntity.created(new URI("/api/entretiens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entretiens} : Updates an existing entretien.
     *
     * @param entretien the entretien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entretien,
     * or with status {@code 400 (Bad Request)} if the entretien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entretien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entretiens")
    public ResponseEntity<Entretien> updateEntretien(@RequestBody Entretien entretien) throws URISyntaxException {
        log.debug("REST request to update Entretien : {}", entretien);
        if (entretien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Entretien result = entretienRepository.save(entretien);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entretien.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entretiens} : get all the entretiens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entretiens in body.
     */
    @GetMapping("/entretiens")
    public ResponseEntity<List<Entretien>> getAllEntretiens(Pageable pageable) {
        log.debug("REST request to get a page of Entretiens");
        Page<Entretien> page = entretienRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entretiens/:id} : get the "id" entretien.
     *
     * @param id the id of the entretien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entretien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entretiens/{id}")
    public ResponseEntity<Entretien> getEntretien(@PathVariable Long id) {
        log.debug("REST request to get Entretien : {}", id);
        Optional<Entretien> entretien = entretienRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entretien);
    }

    /**
     * {@code DELETE  /entretiens/:id} : delete the "id" entretien.
     *
     * @param id the id of the entretien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entretiens/{id}")
    public ResponseEntity<Void> deleteEntretien(@PathVariable Long id) {
        log.debug("REST request to delete Entretien : {}", id);
        entretienRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
