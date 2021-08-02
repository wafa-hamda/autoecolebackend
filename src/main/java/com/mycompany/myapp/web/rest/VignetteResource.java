package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Vignette;
import com.mycompany.myapp.repository.VignetteRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Vignette}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VignetteResource {

    private final Logger log = LoggerFactory.getLogger(VignetteResource.class);

    private static final String ENTITY_NAME = "vignette";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VignetteRepository vignetteRepository;

    public VignetteResource(VignetteRepository vignetteRepository) {
        this.vignetteRepository = vignetteRepository;
    }

    /**
     * {@code POST  /vignettes} : Create a new vignette.
     *
     * @param vignette the vignette to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vignette, or with status {@code 400 (Bad Request)} if the vignette has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vignettes")
    public ResponseEntity<Vignette> createVignette(@RequestBody Vignette vignette) throws URISyntaxException {
        log.debug("REST request to save Vignette : {}", vignette);
        if (vignette.getId() != null) {
            throw new BadRequestAlertException("A new vignette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vignette result = vignetteRepository.save(vignette);
        return ResponseEntity.created(new URI("/api/vignettes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vignettes} : Updates an existing vignette.
     *
     * @param vignette the vignette to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vignette,
     * or with status {@code 400 (Bad Request)} if the vignette is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vignette couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vignettes")
    public ResponseEntity<Vignette> updateVignette(@RequestBody Vignette vignette) throws URISyntaxException {
        log.debug("REST request to update Vignette : {}", vignette);
        if (vignette.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vignette result = vignetteRepository.save(vignette);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vignette.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vignettes} : get all the vignettes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vignettes in body.
     */
    @GetMapping("/vignettes")
    public ResponseEntity<List<Vignette>> getAllVignettes(Pageable pageable) {
        log.debug("REST request to get a page of Vignettes");
        Page<Vignette> page = vignetteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vignettes/:id} : get the "id" vignette.
     *
     * @param id the id of the vignette to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vignette, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vignettes/{id}")
    public ResponseEntity<Vignette> getVignette(@PathVariable Long id) {
        log.debug("REST request to get Vignette : {}", id);
        Optional<Vignette> vignette = vignetteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vignette);
    }

    /**
     * {@code DELETE  /vignettes/:id} : delete the "id" vignette.
     *
     * @param id the id of the vignette to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vignettes/{id}")
    public ResponseEntity<Void> deleteVignette(@PathVariable Long id) {
        log.debug("REST request to delete Vignette : {}", id);
        vignetteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
