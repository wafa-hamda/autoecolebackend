package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Moniteur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Moniteur entity.
 */
@Repository
public interface MoniteurRepository extends JpaRepository<Moniteur, Long> {

    @Query(value = "select distinct moniteur from Moniteur moniteur left join fetch moniteur.vehicules left join fetch moniteur.candidats",
        countQuery = "select count(distinct moniteur) from Moniteur moniteur")
    Page<Moniteur> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct moniteur from Moniteur moniteur left join fetch moniteur.vehicules left join fetch moniteur.candidats")
    List<Moniteur> findAllWithEagerRelationships();

    @Query("select moniteur from Moniteur moniteur left join fetch moniteur.vehicules left join fetch moniteur.candidats where moniteur.id =:id")
    Optional<Moniteur> findOneWithEagerRelationships(@Param("id") Long id);
}
