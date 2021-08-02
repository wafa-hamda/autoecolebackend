package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Ecole;
import com.mycompany.myapp.repository.EcoleRepository;

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
 * Integration tests for the {@link EcoleResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EcoleResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_CREATION = "AAAAAAAAAA";
    private static final String UPDATED_DATE_CREATION = "BBBBBBBBBB";

    private static final String DEFAULT_TARIFS = "AAAAAAAAAA";
    private static final String UPDATED_TARIFS = "BBBBBBBBBB";

    @Autowired
    private EcoleRepository ecoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEcoleMockMvc;

    private Ecole ecole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecole createEntity(EntityManager em) {
        Ecole ecole = new Ecole()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .tarifs(DEFAULT_TARIFS);
        return ecole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecole createUpdatedEntity(EntityManager em) {
        Ecole ecole = new Ecole()
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .dateCreation(UPDATED_DATE_CREATION)
            .tarifs(UPDATED_TARIFS);
        return ecole;
    }

    @BeforeEach
    public void initTest() {
        ecole = createEntity(em);
    }

    @Test
    @Transactional
    public void createEcole() throws Exception {
        int databaseSizeBeforeCreate = ecoleRepository.findAll().size();

        // Create the Ecole
        restEcoleMockMvc.perform(post("/api/ecoles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecole)))
            .andExpect(status().isCreated());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeCreate + 1);
        Ecole testEcole = ecoleList.get(ecoleList.size() - 1);
        assertThat(testEcole.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEcole.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEcole.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testEcole.getTarifs()).isEqualTo(DEFAULT_TARIFS);
    }

    @Test
    @Transactional
    public void createEcoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ecoleRepository.findAll().size();

        // Create the Ecole with an existing ID
        ecole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcoleMockMvc.perform(post("/api/ecoles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecole)))
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEcoles() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);

        // Get all the ecoleList
        restEcoleMockMvc.perform(get("/api/ecoles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecole.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION)))
            .andExpect(jsonPath("$.[*].tarifs").value(hasItem(DEFAULT_TARIFS)));
    }
    
    @Test
    @Transactional
    public void getEcole() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);

        // Get the ecole
        restEcoleMockMvc.perform(get("/api/ecoles/{id}", ecole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecole.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION))
            .andExpect(jsonPath("$.tarifs").value(DEFAULT_TARIFS));
    }

    @Test
    @Transactional
    public void getNonExistingEcole() throws Exception {
        // Get the ecole
        restEcoleMockMvc.perform(get("/api/ecoles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEcole() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);

        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();

        // Update the ecole
        Ecole updatedEcole = ecoleRepository.findById(ecole.getId()).get();
        // Disconnect from session so that the updates on updatedEcole are not directly saved in db
        em.detach(updatedEcole);
        updatedEcole
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .dateCreation(UPDATED_DATE_CREATION)
            .tarifs(UPDATED_TARIFS);

        restEcoleMockMvc.perform(put("/api/ecoles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEcole)))
            .andExpect(status().isOk());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
        Ecole testEcole = ecoleList.get(ecoleList.size() - 1);
        assertThat(testEcole.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEcole.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEcole.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testEcole.getTarifs()).isEqualTo(UPDATED_TARIFS);
    }

    @Test
    @Transactional
    public void updateNonExistingEcole() throws Exception {
        int databaseSizeBeforeUpdate = ecoleRepository.findAll().size();

        // Create the Ecole

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcoleMockMvc.perform(put("/api/ecoles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecole)))
            .andExpect(status().isBadRequest());

        // Validate the Ecole in the database
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEcole() throws Exception {
        // Initialize the database
        ecoleRepository.saveAndFlush(ecole);

        int databaseSizeBeforeDelete = ecoleRepository.findAll().size();

        // Delete the ecole
        restEcoleMockMvc.perform(delete("/api/ecoles/{id}", ecole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ecole> ecoleList = ecoleRepository.findAll();
        assertThat(ecoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
