package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Candidat;
import com.mycompany.myapp.repository.CandidatRepository;

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
 * Integration tests for the {@link CandidatResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CandidatResourceIT {

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_SITUATION = "AAAAAAAAAA";
    private static final String UPDATED_SITUATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidatMockMvc;

    private Candidat candidat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidat createEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .cin(DEFAULT_CIN)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .adresse(DEFAULT_ADRESSE)
            .situation(DEFAULT_SITUATION)
            .age(DEFAULT_AGE);
        return candidat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidat createUpdatedEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .cin(UPDATED_CIN)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .adresse(UPDATED_ADRESSE)
            .situation(UPDATED_SITUATION)
            .age(UPDATED_AGE);
        return candidat;
    }

    @BeforeEach
    public void initTest() {
        candidat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidat() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isCreated());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate + 1);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testCandidat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCandidat.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCandidat.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCandidat.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testCandidat.getSituation()).isEqualTo(DEFAULT_SITUATION);
        assertThat(testCandidat.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    public void createCandidatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat with an existing ID
        candidat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCinIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setCin(null);

        // Create the Candidat, which fails.

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setNom(null);

        // Create the Candidat, which fails.

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setPrenom(null);

        // Create the Candidat, which fails.

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setTelephone(null);

        // Create the Candidat, which fails.

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCandidats() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].situation").value(hasItem(DEFAULT_SITUATION)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }
    
    @Test
    @Transactional
    public void getCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", candidat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidat.getId().intValue()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.situation").value(DEFAULT_SITUATION))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    public void getNonExistingCandidat() throws Exception {
        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Update the candidat
        Candidat updatedCandidat = candidatRepository.findById(candidat.getId()).get();
        // Disconnect from session so that the updates on updatedCandidat are not directly saved in db
        em.detach(updatedCandidat);
        updatedCandidat
            .cin(UPDATED_CIN)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .adresse(UPDATED_ADRESSE)
            .situation(UPDATED_SITUATION)
            .age(UPDATED_AGE);

        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidat)))
            .andExpect(status().isOk());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testCandidat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCandidat.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCandidat.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCandidat.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testCandidat.getSituation()).isEqualTo(UPDATED_SITUATION);
        assertThat(testCandidat.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidat() throws Exception {
        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Create the Candidat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        int databaseSizeBeforeDelete = candidatRepository.findAll().size();

        // Delete the candidat
        restCandidatMockMvc.perform(delete("/api/candidats/{id}", candidat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
