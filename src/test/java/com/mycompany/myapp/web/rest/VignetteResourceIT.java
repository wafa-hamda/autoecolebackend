package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Vignette;
import com.mycompany.myapp.repository.VignetteRepository;
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
 * Integration tests for the {@link VignetteResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class VignetteResourceIT {

    private static final String DEFAULT_DATE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_DATE_FIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIX = 1;
    private static final Integer UPDATED_PRIX = 2;

    @Autowired
    private VignetteRepository vignetteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVignetteMockMvc;

    private Vignette vignette;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vignette createEntity(EntityManager em) {
        Vignette vignette = new Vignette()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .prix(DEFAULT_PRIX);
        return vignette;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vignette createUpdatedEntity(EntityManager em) {
        Vignette vignette = new Vignette()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .prix(UPDATED_PRIX);
        return vignette;
    }

    @BeforeEach
    public void initTest() {
        vignette = createEntity(em);
    }

    @Test
    @Transactional
    public void createVignette() throws Exception {
        int databaseSizeBeforeCreate = vignetteRepository.findAll().size();

        // Create the Vignette
        restVignetteMockMvc.perform(post("/api/vignettes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vignette)))
            .andExpect(status().isCreated());

        // Validate the Vignette in the database
        List<Vignette> vignetteList = vignetteRepository.findAll();
        assertThat(vignetteList).hasSize(databaseSizeBeforeCreate + 1);
        Vignette testVignette = vignetteList.get(vignetteList.size() - 1);
        assertThat(testVignette.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testVignette.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testVignette.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createVignetteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vignetteRepository.findAll().size();

        // Create the Vignette with an existing ID
        vignette.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVignetteMockMvc.perform(post("/api/vignettes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vignette)))
            .andExpect(status().isBadRequest());

        // Validate the Vignette in the database
        List<Vignette> vignetteList = vignetteRepository.findAll();
        assertThat(vignetteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVignettes() throws Exception {
        // Initialize the database
        vignetteRepository.saveAndFlush(vignette);

        // Get all the vignetteList
        restVignetteMockMvc.perform(get("/api/vignettes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vignette.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)));
    }
    
    @Test
    @Transactional
    public void getVignette() throws Exception {
        // Initialize the database
        vignetteRepository.saveAndFlush(vignette);

        // Get the vignette
        restVignetteMockMvc.perform(get("/api/vignettes/{id}", vignette.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vignette.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX));
    }

    @Test
    @Transactional
    public void getNonExistingVignette() throws Exception {
        // Get the vignette
        restVignetteMockMvc.perform(get("/api/vignettes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVignette() throws Exception {
        // Initialize the database
        vignetteRepository.saveAndFlush(vignette);

        int databaseSizeBeforeUpdate = vignetteRepository.findAll().size();

        // Update the vignette
        Vignette updatedVignette = vignetteRepository.findById(vignette.getId()).get();
        // Disconnect from session so that the updates on updatedVignette are not directly saved in db
        em.detach(updatedVignette);
        updatedVignette
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .prix(UPDATED_PRIX);

        restVignetteMockMvc.perform(put("/api/vignettes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVignette)))
            .andExpect(status().isOk());

        // Validate the Vignette in the database
        List<Vignette> vignetteList = vignetteRepository.findAll();
        assertThat(vignetteList).hasSize(databaseSizeBeforeUpdate);
        Vignette testVignette = vignetteList.get(vignetteList.size() - 1);
        assertThat(testVignette.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testVignette.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testVignette.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingVignette() throws Exception {
        int databaseSizeBeforeUpdate = vignetteRepository.findAll().size();

        // Create the Vignette

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVignetteMockMvc.perform(put("/api/vignettes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vignette)))
            .andExpect(status().isBadRequest());

        // Validate the Vignette in the database
        List<Vignette> vignetteList = vignetteRepository.findAll();
        assertThat(vignetteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVignette() throws Exception {
        // Initialize the database
        vignetteRepository.saveAndFlush(vignette);

        int databaseSizeBeforeDelete = vignetteRepository.findAll().size();

        // Delete the vignette
        restVignetteMockMvc.perform(delete("/api/vignettes/{id}", vignette.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vignette> vignetteList = vignetteRepository.findAll();
        assertThat(vignetteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
