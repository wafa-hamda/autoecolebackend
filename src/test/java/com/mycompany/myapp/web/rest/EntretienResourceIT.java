package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Entretien;
import com.mycompany.myapp.repository.EntretienRepository;

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
 * Integration tests for the {@link EntretienResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EntretienResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIX = 1;
    private static final Integer UPDATED_PRIX = 2;

    @Autowired
    private EntretienRepository entretienRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntretienMockMvc;

    private Entretien entretien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entretien createEntity(EntityManager em) {
        Entretien entretien = new Entretien()
            .libelle(DEFAULT_LIBELLE)
            .prix(DEFAULT_PRIX);
        return entretien;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entretien createUpdatedEntity(EntityManager em) {
        Entretien entretien = new Entretien()
            .libelle(UPDATED_LIBELLE)
            .prix(UPDATED_PRIX);
        return entretien;
    }

    @BeforeEach
    public void initTest() {
        entretien = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntretien() throws Exception {
        int databaseSizeBeforeCreate = entretienRepository.findAll().size();

        // Create the Entretien
        restEntretienMockMvc.perform(post("/api/entretiens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isCreated());

        // Validate the Entretien in the database
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeCreate + 1);
        Entretien testEntretien = entretienList.get(entretienList.size() - 1);
        assertThat(testEntretien.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEntretien.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createEntretienWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entretienRepository.findAll().size();

        // Create the Entretien with an existing ID
        entretien.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntretienMockMvc.perform(post("/api/entretiens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isBadRequest());

        // Validate the Entretien in the database
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEntretiens() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get all the entretienList
        restEntretienMockMvc.perform(get("/api/entretiens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entretien.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)));
    }
    
    @Test
    @Transactional
    public void getEntretien() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        // Get the entretien
        restEntretienMockMvc.perform(get("/api/entretiens/{id}", entretien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entretien.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX));
    }

    @Test
    @Transactional
    public void getNonExistingEntretien() throws Exception {
        // Get the entretien
        restEntretienMockMvc.perform(get("/api/entretiens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntretien() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        int databaseSizeBeforeUpdate = entretienRepository.findAll().size();

        // Update the entretien
        Entretien updatedEntretien = entretienRepository.findById(entretien.getId()).get();
        // Disconnect from session so that the updates on updatedEntretien are not directly saved in db
        em.detach(updatedEntretien);
        updatedEntretien
            .libelle(UPDATED_LIBELLE)
            .prix(UPDATED_PRIX);

        restEntretienMockMvc.perform(put("/api/entretiens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntretien)))
            .andExpect(status().isOk());

        // Validate the Entretien in the database
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeUpdate);
        Entretien testEntretien = entretienList.get(entretienList.size() - 1);
        assertThat(testEntretien.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEntretien.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingEntretien() throws Exception {
        int databaseSizeBeforeUpdate = entretienRepository.findAll().size();

        // Create the Entretien

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntretienMockMvc.perform(put("/api/entretiens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entretien)))
            .andExpect(status().isBadRequest());

        // Validate the Entretien in the database
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntretien() throws Exception {
        // Initialize the database
        entretienRepository.saveAndFlush(entretien);

        int databaseSizeBeforeDelete = entretienRepository.findAll().size();

        // Delete the entretien
        restEntretienMockMvc.perform(delete("/api/entretiens/{id}", entretien.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entretien> entretienList = entretienRepository.findAll();
        assertThat(entretienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
