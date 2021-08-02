package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Payement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Payement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayementRepository extends JpaRepository<Payement, Long> {
}
