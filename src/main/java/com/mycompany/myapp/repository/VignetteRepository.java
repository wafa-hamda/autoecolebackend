package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vignette;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vignette entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VignetteRepository extends JpaRepository<Vignette, Long> {
}
