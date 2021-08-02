package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Charge;
import com.mycompany.myapp.repository.ChargeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Charge}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChargeResource {

    private final Logger log = LoggerFactory.getLogger(ChargeResource.class);

    private static final String ENTITY_NAME = "charge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChargeRepository chargeRepository;

    public ChargeResource(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    /**
     * {@code POST  /charges} : Create a new charge.
     *
     * @param charge the charge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new charge, or with status {@code 400 (Bad Request)} if the charge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/charges")
    public ResponseEntity<Charge> createCharge(@RequestBody Charge charge) throws URISyntaxException {
        log.debug("REST request to save Charge : {}", charge);
        if (charge.getId() != null) {
            throw new BadRequestAlertException("A new charge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Charge result = chargeRepository.save(charge);
        return ResponseEntity.created(new URI("/api/charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /charges} : Updates an existing charge.
     *
     * @param charge the charge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated charge,
     * or with status {@code 400 (Bad Request)} if the charge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the charge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/charges")
    public ResponseEntity<Charge> updateCharge(@RequestBody Charge charge) throws URISyntaxException {
        log.debug("REST request to update Charge : {}", charge);
        if (charge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Charge result = chargeRepository.save(charge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, charge.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /charges} : get all the charges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of charges in body.
     */
    @GetMapping("/charges")
    public ResponseEntity<List<Charge>> getAllCharges(Pageable pageable) {
        log.debug("REST request to get a page of Charges");
        Page<Charge> page = chargeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /charges/:id} : get the "id" charge.
     *
     * @param id the id of the charge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the charge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/charges/{id}")
    public ResponseEntity<Charge> getCharge(@PathVariable Long id) {
        log.debug("REST request to get Charge : {}", id);
        Optional<Charge> charge = chargeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(charge);
    }

    /**
     * {@code DELETE  /charges/:id} : delete the "id" charge.
     *
     * @param id the id of the charge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/charges/{id}")
    public ResponseEntity<Void> deleteCharge(@PathVariable Long id) {
        log.debug("REST request to delete Charge : {}", id);
        chargeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
