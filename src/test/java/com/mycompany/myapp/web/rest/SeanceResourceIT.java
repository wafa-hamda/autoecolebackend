package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Seance;
import com.mycompany.myapp.repository.SeanceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SeanceResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SeanceResourceIT {

    private static final String DEFAULT_DATE_HEURE_PREVU = "AAAAAAAAAA";
    private static final String UPDATED_DATE_HEURE_PREVU = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_HEURE_REEL = "AAAAAAAAAA";
    private static final String UPDATED_DATE_HEURE_REEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_NBR_HEURE = 1;
    private static final Integer UPDATED_NBR_HEURE = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeanceMockMvc;

    private Seance seance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seance createEntity(EntityManager em) {
        Seance seance = new Seance()
            .dateHeurePrevu(DEFAULT_DATE_HEURE_PREVU)
            .dateHeureReel(DEFAULT_DATE_HEURE_REEL)
            .nbrHeure(DEFAULT_NBR_HEURE)
            .type(DEFAULT_TYPE);
        return seance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seance createUpdatedEntity(EntityManager em) {
        Seance seance = new Seance()
            .dateHeurePrevu(UPDATED_DATE_HEURE_PREVU)
            .dateHeureReel(UPDATED_DATE_HEURE_REEL)
            .nbrHeure(UPDATED_NBR_HEURE)
            .type(UPDATED_TYPE);
        return seance;
    }

    @BeforeEach
    public void initTest() {
        seance = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeance() throws Exception {
        int databaseSizeBeforeCreate = seanceRepository.findAll().size();

        // Create the Seance
        restSeanceMockMvc.perform(post("/api/seances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seance)))
            .andExpect(status().isCreated());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeCreate + 1);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getDateHeurePrevu()).isEqualTo(DEFAULT_DATE_HEURE_PREVU);
        assertThat(testSeance.getDateHeureReel()).isEqualTo(DEFAULT_DATE_HEURE_REEL);
        assertThat(testSeance.getNbrHeure()).isEqualTo(DEFAULT_NBR_HEURE);
        assertThat(testSeance.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createSeanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seanceRepository.findAll().size();

        // Create the Seance with an existing ID
        seance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeanceMockMvc.perform(post("/api/seances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seance)))
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSeances() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        // Get all the seanceList
        restSeanceMockMvc.perform(get("/api/seances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seance.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateHeurePrevu").value(hasItem(DEFAULT_DATE_HEURE_PREVU)))
            .andExpect(jsonPath("$.[*].dateHeureReel").value(hasItem(DEFAULT_DATE_HEURE_REEL)))
            .andExpect(jsonPath("$.[*].nbrHeure").value(hasItem(DEFAULT_NBR_HEURE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        // Get the seance
        restSeanceMockMvc.perform(get("/api/seances/{id}", seance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seance.getId().intValue()))
            .andExpect(jsonPath("$.dateHeurePrevu").value(DEFAULT_DATE_HEURE_PREVU))
            .andExpect(jsonPath("$.dateHeureReel").value(DEFAULT_DATE_HEURE_REEL))
            .andExpect(jsonPath("$.nbrHeure").value(DEFAULT_NBR_HEURE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    public void getNonExistingSeance() throws Exception {
        // Get the seance
        restSeanceMockMvc.perform(get("/api/seances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Update the seance
        Seance updatedSeance = seanceRepository.findById(seance.getId()).get();
        // Disconnect from session so that the updates on updatedSeance are not directly saved in db
        em.detach(updatedSeance);
        updatedSeance
            .dateHeurePrevu(UPDATED_DATE_HEURE_PREVU)
            .dateHeureReel(UPDATED_DATE_HEURE_REEL)
            .nbrHeure(UPDATED_NBR_HEURE)
            .type(UPDATED_TYPE);

        restSeanceMockMvc.perform(put("/api/seances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSeance)))
            .andExpect(status().isOk());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
        Seance testSeance = seanceList.get(seanceList.size() - 1);
        assertThat(testSeance.getDateHeurePrevu()).isEqualTo(UPDATED_DATE_HEURE_PREVU);
        assertThat(testSeance.getDateHeureReel()).isEqualTo(UPDATED_DATE_HEURE_REEL);
        assertThat(testSeance.getNbrHeure()).isEqualTo(UPDATED_NBR_HEURE);
        assertThat(testSeance.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSeance() throws Exception {
        int databaseSizeBeforeUpdate = seanceRepository.findAll().size();

        // Create the Seance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeanceMockMvc.perform(put("/api/seances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(seance)))
            .andExpect(status().isBadRequest());

        // Validate the Seance in the database
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeance() throws Exception {
        // Initialize the database
        seanceRepository.saveAndFlush(seance);

        int databaseSizeBeforeDelete = seanceRepository.findAll().size();

        // Delete the seance
        restSeanceMockMvc.perform(delete("/api/seances/{id}", seance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seance> seanceList = seanceRepository.findAll();
        assertThat(seanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
