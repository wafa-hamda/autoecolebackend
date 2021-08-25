package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Code;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Code}.
 */
public interface CodeService {

    /**
     * Save a code.
     *
     * @param code the entity to save.
     * @return the persisted entity.
     */
    Code save(Code code);

    /**
     * Get all the codes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Code> findAll(Pageable pageable);


    /**
     * Get the "id" code.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Code> findOne(Long id);

    /**
     * Delete the "id" code.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
