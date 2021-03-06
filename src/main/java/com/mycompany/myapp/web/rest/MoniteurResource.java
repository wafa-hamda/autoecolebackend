package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Moniteur;
import com.mycompany.myapp.repository.MoniteurRepository;
import com.mycompany.myapp.repository.UserRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Moniteur}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MoniteurResource {

    private final Logger log = LoggerFactory.getLogger(MoniteurResource.class);

    private static final String ENTITY_NAME = "moniteur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoniteurRepository moniteurRepository;

    private final UserRepository userRepository;

    public MoniteurResource(MoniteurRepository moniteurRepository, UserRepository userRepository) {
        this.moniteurRepository = moniteurRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /moniteurs} : Create a new moniteur.
     *
     * @param moniteur the moniteur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moniteur, or with status {@code 400 (Bad Request)} if the moniteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moniteurs")
    public ResponseEntity<Moniteur> createMoniteur(@Valid @RequestBody Moniteur moniteur) throws URISyntaxException {
        log.debug("REST request to save Moniteur : {}", moniteur);
        if (moniteur.getId() != null) {
            throw new BadRequestAlertException("A new moniteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(moniteur.getUser())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Long userId = moniteur.getUser().getId();
        userRepository.findById(userId).ifPresent(moniteur::user);
        Moniteur result = moniteurRepository.save(moniteur);
        return ResponseEntity.created(new URI("/api/moniteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moniteurs} : Updates an existing moniteur.
     *
     * @param moniteur the moniteur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moniteur,
     * or with status {@code 400 (Bad Request)} if the moniteur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moniteur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moniteurs")
    public ResponseEntity<Moniteur> updateMoniteur(@Valid @RequestBody Moniteur moniteur) throws URISyntaxException {
        log.debug("REST request to update Moniteur : {}", moniteur);
        if (moniteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Moniteur result = moniteurRepository.save(moniteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moniteur.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /moniteurs} : get all the moniteurs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moniteurs in body.
     */
    @GetMapping("/moniteurs")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Moniteur>> getAllMoniteurs(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Moniteurs");
        Page<Moniteur> page;
        if (eagerload) {
            page = moniteurRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = moniteurRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /moniteurs/:id} : get the "id" moniteur.
     *
     * @param id the id of the moniteur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moniteur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moniteurs/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Moniteur> getMoniteur(@PathVariable Long id) {
        log.debug("REST request to get Moniteur : {}", id);
        Optional<Moniteur> moniteur = moniteurRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(moniteur);
    }

    /**
     * {@code DELETE  /moniteurs/:id} : delete the "id" moniteur.
     *
     * @param id the id of the moniteur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moniteurs/{id}")
    public ResponseEntity<Void> deleteMoniteur(@PathVariable Long id) {
        log.debug("REST request to delete Moniteur : {}", id);
        moniteurRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
