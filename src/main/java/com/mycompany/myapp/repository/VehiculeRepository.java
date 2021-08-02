package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vehicule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vehicule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}
