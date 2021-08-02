package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Payement;
import com.mycompany.myapp.repository.PayementRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Payement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PayementResource {

    private final Logger log = LoggerFactory.getLogger(PayementResource.class);

    private static final String ENTITY_NAME = "payement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayementRepository payementRepository;

    public PayementResource(PayementRepository payementRepository) {
        this.payementRepository = payementRepository;
    }

    /**
     * {@code POST  /payements} : Create a new payement.
     *
     * @param payement the payement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payement, or with status {@code 400 (Bad Request)} if the payement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payements")
    public ResponseEntity<Payement> createPayement(@RequestBody Payement payement) throws URISyntaxException {
        log.debug("REST request to save Payement : {}", payement);
        if (payement.getId() != null) {
            throw new BadRequestAlertException("A new payement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Payement result = payementRepository.save(payement);
        return ResponseEntity.created(new URI("/api/payements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payements} : Updates an existing payement.
     *
     * @param payement the payement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payement,
     * or with status {@code 400 (Bad Request)} if the payement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payements")
    public ResponseEntity<Payement> updatePayement(@RequestBody Payement payement) throws URISyntaxException {
        log.debug("REST request to update Payement : {}", payement);
        if (payement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Payement result = payementRepository.save(payement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payements} : get all the payements.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payements in body.
     */
    @GetMapping("/payements")
    public ResponseEntity<List<Payement>> getAllPayements(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("candidat-is-null".equals(filter)) {
            log.debug("REST request to get all Payements where candidat is null");
            return new ResponseEntity<>(StreamSupport
                .stream(payementRepository.findAll().spliterator(), false)
                .filter(payement -> payement.getCandidat() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Payements");
        Page<Payement> page = payementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payements/:id} : get the "id" payement.
     *
     * @param id the id of the payement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payements/{id}")
    public ResponseEntity<Payement> getPayement(@PathVariable Long id) {
        log.debug("REST request to get Payement : {}", id);
        Optional<Payement> payement = payementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payement);
    }

    /**
     * {@code DELETE  /payements/:id} : delete the "id" payement.
     *
     * @param id the id of the payement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payements/{id}")
    public ResponseEntity<Void> deletePayement(@PathVariable Long id) {
        log.debug("REST request to delete Payement : {}", id);
        payementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
