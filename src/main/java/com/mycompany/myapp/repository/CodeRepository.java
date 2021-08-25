package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Code;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Code entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
}
