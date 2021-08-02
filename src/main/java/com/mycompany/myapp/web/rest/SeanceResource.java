package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Seance;
import com.mycompany.myapp.repository.SeanceRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Seance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SeanceResource {

    private final Logger log = LoggerFactory.getLogger(SeanceResource.class);

    private static final String ENTITY_NAME = "seance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeanceRepository seanceRepository;

    public SeanceResource(SeanceRepository seanceRepository) {
        this.seanceRepository = seanceRepository;
    }

    /**
     * {@code POST  /seances} : Create a new seance.
     *
     * @param seance the seance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seance, or with status {@code 400 (Bad Request)} if the seance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seances")
    public ResponseEntity<Seance> createSeance(@RequestBody Seance seance) throws URISyntaxException {
        log.debug("REST request to save Seance : {}", seance);
        if (seance.getId() != null) {
            throw new BadRequestAlertException("A new seance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Seance result = seanceRepository.save(seance);
        return ResponseEntity.created(new URI("/api/seances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seances} : Updates an existing seance.
     *
     * @param seance the seance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seance,
     * or with status {@code 400 (Bad Request)} if the seance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seances")
    public ResponseEntity<Seance> updateSeance(@RequestBody Seance seance) throws URISyntaxException {
        log.debug("REST request to update Seance : {}", seance);
        if (seance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Seance result = seanceRepository.save(seance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /seances} : get all the seances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seances in body.
     */
    @GetMapping("/seances")
    public ResponseEntity<List<Seance>> getAllSeances(Pageable pageable) {
        log.debug("REST request to get a page of Seances");
        Page<Seance> page = seanceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seances/:id} : get the "id" seance.
     *
     * @param id the id of the seance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seances/{id}")
    public ResponseEntity<Seance> getSeance(@PathVariable Long id) {
        log.debug("REST request to get Seance : {}", id);
        Optional<Seance> seance = seanceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(seance);
    }

    /**
     * {@code DELETE  /seances/:id} : delete the "id" seance.
     *
     * @param id the id of the seance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seances/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable Long id) {
        log.debug("REST request to delete Seance : {}", id);
        seanceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
