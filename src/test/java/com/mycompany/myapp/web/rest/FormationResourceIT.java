package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Formation;
import com.mycompany.myapp.repository.FormationRepository;

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
 * Integration tests for the {@link FormationResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FormationResourceIT {

    private static final String DEFAULT_DATE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_DATE_FIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_NBR_HEURE_CODE = 1;
    private static final Integer UPDATED_NBR_HEURE_CODE = 2;

    private static final Integer DEFAULT_PRIXHEURE_CODE = 1;
    private static final Integer UPDATED_PRIXHEURE_CODE = 2;

    private static final Integer DEFAULT_NBR_HEURE_CONDUIT = 1;
    private static final Integer UPDATED_NBR_HEURE_CONDUIT = 2;

    private static final Integer DEFAULT_PRIX_HEURE_CONDUIT = 1;
    private static final Integer UPDATED_PRIX_HEURE_CONDUIT = 2;

    private static final String DEFAULT_DISPONOBILTE = "AAAAAAAAAA";
    private static final String UPDATED_DISPONOBILTE = "BBBBBBBBBB";

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormationMockMvc;

    private Formation formation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formation createEntity(EntityManager em) {
        Formation formation = new Formation()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .nbrHeureCode(DEFAULT_NBR_HEURE_CODE)
            .prixheureCode(DEFAULT_PRIXHEURE_CODE)
            .nbrHeureConduit(DEFAULT_NBR_HEURE_CONDUIT)
            .prixHeureConduit(DEFAULT_PRIX_HEURE_CONDUIT)
            .disponobilte(DEFAULT_DISPONOBILTE);
        return formation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formation createUpdatedEntity(EntityManager em) {
        Formation formation = new Formation()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nbrHeureCode(UPDATED_NBR_HEURE_CODE)
            .prixheureCode(UPDATED_PRIXHEURE_CODE)
            .nbrHeureConduit(UPDATED_NBR_HEURE_CONDUIT)
            .prixHeureConduit(UPDATED_PRIX_HEURE_CONDUIT)
            .disponobilte(UPDATED_DISPONOBILTE);
        return formation;
    }

    @BeforeEach
    public void initTest() {
        formation = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormation() throws Exception {
        int databaseSizeBeforeCreate = formationRepository.findAll().size();

        // Create the Formation
        restFormationMockMvc.perform(post("/api/formations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isCreated());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeCreate + 1);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testFormation.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testFormation.getNbrHeureCode()).isEqualTo(DEFAULT_NBR_HEURE_CODE);
        assertThat(testFormation.getPrixheureCode()).isEqualTo(DEFAULT_PRIXHEURE_CODE);
        assertThat(testFormation.getNbrHeureConduit()).isEqualTo(DEFAULT_NBR_HEURE_CONDUIT);
        assertThat(testFormation.getPrixHeureConduit()).isEqualTo(DEFAULT_PRIX_HEURE_CONDUIT);
        assertThat(testFormation.getDisponobilte()).isEqualTo(DEFAULT_DISPONOBILTE);
    }

    @Test
    @Transactional
    public void createFormationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formationRepository.findAll().size();

        // Create the Formation with an existing ID
        formation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormationMockMvc.perform(post("/api/formations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = formationRepository.findAll().size();
        // set the field null
        formation.setDateDebut(null);

        // Create the Formation, which fails.

        restFormationMockMvc.perform(post("/api/formations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isBadRequest());

        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormations() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        // Get all the formationList
        restFormationMockMvc.perform(get("/api/formations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN)))
            .andExpect(jsonPath("$.[*].nbrHeureCode").value(hasItem(DEFAULT_NBR_HEURE_CODE)))
            .andExpect(jsonPath("$.[*].prixheureCode").value(hasItem(DEFAULT_PRIXHEURE_CODE)))
            .andExpect(jsonPath("$.[*].nbrHeureConduit").value(hasItem(DEFAULT_NBR_HEURE_CONDUIT)))
            .andExpect(jsonPath("$.[*].prixHeureConduit").value(hasItem(DEFAULT_PRIX_HEURE_CONDUIT)))
            .andExpect(jsonPath("$.[*].disponobilte").value(hasItem(DEFAULT_DISPONOBILTE)));
    }
    
    @Test
    @Transactional
    public void getFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        // Get the formation
        restFormationMockMvc.perform(get("/api/formations/{id}", formation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formation.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN))
            .andExpect(jsonPath("$.nbrHeureCode").value(DEFAULT_NBR_HEURE_CODE))
            .andExpect(jsonPath("$.prixheureCode").value(DEFAULT_PRIXHEURE_CODE))
            .andExpect(jsonPath("$.nbrHeureConduit").value(DEFAULT_NBR_HEURE_CONDUIT))
            .andExpect(jsonPath("$.prixHeureConduit").value(DEFAULT_PRIX_HEURE_CONDUIT))
            .andExpect(jsonPath("$.disponobilte").value(DEFAULT_DISPONOBILTE));
    }

    @Test
    @Transactional
    public void getNonExistingFormation() throws Exception {
        // Get the formation
        restFormationMockMvc.perform(get("/api/formations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Update the formation
        Formation updatedFormation = formationRepository.findById(formation.getId()).get();
        // Disconnect from session so that the updates on updatedFormation are not directly saved in db
        em.detach(updatedFormation);
        updatedFormation
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nbrHeureCode(UPDATED_NBR_HEURE_CODE)
            .prixheureCode(UPDATED_PRIXHEURE_CODE)
            .nbrHeureConduit(UPDATED_NBR_HEURE_CONDUIT)
            .prixHeureConduit(UPDATED_PRIX_HEURE_CONDUIT)
            .disponobilte(UPDATED_DISPONOBILTE);

        restFormationMockMvc.perform(put("/api/formations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormation)))
            .andExpect(status().isOk());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testFormation.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testFormation.getNbrHeureCode()).isEqualTo(UPDATED_NBR_HEURE_CODE);
        assertThat(testFormation.getPrixheureCode()).isEqualTo(UPDATED_PRIXHEURE_CODE);
        assertThat(testFormation.getNbrHeureConduit()).isEqualTo(UPDATED_NBR_HEURE_CONDUIT);
        assertThat(testFormation.getPrixHeureConduit()).isEqualTo(UPDATED_PRIX_HEURE_CONDUIT);
        assertThat(testFormation.getDisponobilte()).isEqualTo(UPDATED_DISPONOBILTE);
    }

    @Test
    @Transactional
    public void updateNonExistingFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Create the Formation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationMockMvc.perform(put("/api/formations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeDelete = formationRepository.findAll().size();

        // Delete the formation
        restFormationMockMvc.perform(delete("/api/formations/{id}", formation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
