package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Assurance;
import com.mycompany.myapp.repository.AssuranceRepository;

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
 * Integration tests for the {@link AssuranceResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AssuranceResourceIT {

    private static final String DEFAULT_DATE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_DATE_FIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIX = 1;
    private static final Integer UPDATED_PRIX = 2;

    @Autowired
    private AssuranceRepository assuranceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssuranceMockMvc;

    private Assurance assurance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assurance createEntity(EntityManager em) {
        Assurance assurance = new Assurance()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .prix(DEFAULT_PRIX);
        return assurance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assurance createUpdatedEntity(EntityManager em) {
        Assurance assurance = new Assurance()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .prix(UPDATED_PRIX);
        return assurance;
    }

    @BeforeEach
    public void initTest() {
        assurance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssurance() throws Exception {
        int databaseSizeBeforeCreate = assuranceRepository.findAll().size();

        // Create the Assurance
        restAssuranceMockMvc.perform(post("/api/assurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assurance)))
            .andExpect(status().isCreated());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeCreate + 1);
        Assurance testAssurance = assuranceList.get(assuranceList.size() - 1);
        assertThat(testAssurance.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testAssurance.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testAssurance.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createAssuranceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assuranceRepository.findAll().size();

        // Create the Assurance with an existing ID
        assurance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssuranceMockMvc.perform(post("/api/assurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assurance)))
            .andExpect(status().isBadRequest());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAssurances() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        // Get all the assuranceList
        restAssuranceMockMvc.perform(get("/api/assurances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)));
    }
    
    @Test
    @Transactional
    public void getAssurance() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        // Get the assurance
        restAssuranceMockMvc.perform(get("/api/assurances/{id}", assurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assurance.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX));
    }

    @Test
    @Transactional
    public void getNonExistingAssurance() throws Exception {
        // Get the assurance
        restAssuranceMockMvc.perform(get("/api/assurances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssurance() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();

        // Update the assurance
        Assurance updatedAssurance = assuranceRepository.findById(assurance.getId()).get();
        // Disconnect from session so that the updates on updatedAssurance are not directly saved in db
        em.detach(updatedAssurance);
        updatedAssurance
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .prix(UPDATED_PRIX);

        restAssuranceMockMvc.perform(put("/api/assurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssurance)))
            .andExpect(status().isOk());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
        Assurance testAssurance = assuranceList.get(assuranceList.size() - 1);
        assertThat(testAssurance.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testAssurance.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testAssurance.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingAssurance() throws Exception {
        int databaseSizeBeforeUpdate = assuranceRepository.findAll().size();

        // Create the Assurance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssuranceMockMvc.perform(put("/api/assurances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assurance)))
            .andExpect(status().isBadRequest());

        // Validate the Assurance in the database
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssurance() throws Exception {
        // Initialize the database
        assuranceRepository.saveAndFlush(assurance);

        int databaseSizeBeforeDelete = assuranceRepository.findAll().size();

        // Delete the assurance
        restAssuranceMockMvc.perform(delete("/api/assurances/{id}", assurance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assurance> assuranceList = assuranceRepository.findAll();
        assertThat(assuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
