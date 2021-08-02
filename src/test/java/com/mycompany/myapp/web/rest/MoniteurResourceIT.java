package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Moniteur;
import com.mycompany.myapp.repository.MoniteurRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MoniteurResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MoniteurResourceIT {

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_DATENAIS = "AAAAAAAAAA";
    private static final String UPDATED_DATENAIS = "BBBBBBBBBB";

    @Autowired
    private MoniteurRepository moniteurRepository;

    @Mock
    private MoniteurRepository moniteurRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoniteurMockMvc;

    private Moniteur moniteur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moniteur createEntity(EntityManager em) {
        Moniteur moniteur = new Moniteur()
            .cin(DEFAULT_CIN)
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .type(DEFAULT_TYPE)
            .adresse(DEFAULT_ADRESSE)
            .datenais(DEFAULT_DATENAIS);
        return moniteur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moniteur createUpdatedEntity(EntityManager em) {
        Moniteur moniteur = new Moniteur()
            .cin(UPDATED_CIN)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .type(UPDATED_TYPE)
            .adresse(UPDATED_ADRESSE)
            .datenais(UPDATED_DATENAIS);
        return moniteur;
    }

    @BeforeEach
    public void initTest() {
        moniteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createMoniteur() throws Exception {
        int databaseSizeBeforeCreate = moniteurRepository.findAll().size();

        // Create the Moniteur
        restMoniteurMockMvc.perform(post("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moniteur)))
            .andExpect(status().isCreated());

        // Validate the Moniteur in the database
        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeCreate + 1);
        Moniteur testMoniteur = moniteurList.get(moniteurList.size() - 1);
        assertThat(testMoniteur.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testMoniteur.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testMoniteur.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMoniteur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMoniteur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMoniteur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testMoniteur.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMoniteur.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testMoniteur.getDatenais()).isEqualTo(DEFAULT_DATENAIS);
    }

    @Test
    @Transactional
    public void createMoniteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moniteurRepository.findAll().size();

        // Create the Moniteur with an existing ID
        moniteur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoniteurMockMvc.perform(post("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moniteur)))
            .andExpect(status().isBadRequest());

        // Validate the Moniteur in the database
        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCinIsRequired() throws Exception {
        int databaseSizeBeforeTest = moniteurRepository.findAll().size();
        // set the field null
        moniteur.setCin(null);

        // Create the Moniteur, which fails.

        restMoniteurMockMvc.perform(post("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moniteur)))
            .andExpect(status().isBadRequest());

        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = moniteurRepository.findAll().size();
        // set the field null
        moniteur.setLogin(null);

        // Create the Moniteur, which fails.

        restMoniteurMockMvc.perform(post("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moniteur)))
            .andExpect(status().isBadRequest());

        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = moniteurRepository.findAll().size();
        // set the field null
        moniteur.setPassword(null);

        // Create the Moniteur, which fails.

        restMoniteurMockMvc.perform(post("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moniteur)))
            .andExpect(status().isBadRequest());

        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = moniteurRepository.findAll().size();
        // set the field null
        moniteur.setNom(null);

        // Create the Moniteur, which fails.

        restMoniteurMockMvc.perform(post("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moniteur)))
            .andExpect(status().isBadRequest());

        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = moniteurRepository.findAll().size();
        // set the field null
        moniteur.setPrenom(null);

        // Create the Moniteur, which fails.

        restMoniteurMockMvc.perform(post("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moniteur)))
            .andExpect(status().isBadRequest());

        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMoniteurs() throws Exception {
        // Initialize the database
        moniteurRepository.saveAndFlush(moniteur);

        // Get all the moniteurList
        restMoniteurMockMvc.perform(get("/api/moniteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moniteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].datenais").value(hasItem(DEFAULT_DATENAIS)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMoniteursWithEagerRelationshipsIsEnabled() throws Exception {
        MoniteurResource moniteurResource = new MoniteurResource(moniteurRepositoryMock);
        when(moniteurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMoniteurMockMvc.perform(get("/api/moniteurs?eagerload=true"))
            .andExpect(status().isOk());

        verify(moniteurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMoniteursWithEagerRelationshipsIsNotEnabled() throws Exception {
        MoniteurResource moniteurResource = new MoniteurResource(moniteurRepositoryMock);
        when(moniteurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMoniteurMockMvc.perform(get("/api/moniteurs?eagerload=true"))
            .andExpect(status().isOk());

        verify(moniteurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMoniteur() throws Exception {
        // Initialize the database
        moniteurRepository.saveAndFlush(moniteur);

        // Get the moniteur
        restMoniteurMockMvc.perform(get("/api/moniteurs/{id}", moniteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moniteur.getId().intValue()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.datenais").value(DEFAULT_DATENAIS));
    }

    @Test
    @Transactional
    public void getNonExistingMoniteur() throws Exception {
        // Get the moniteur
        restMoniteurMockMvc.perform(get("/api/moniteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMoniteur() throws Exception {
        // Initialize the database
        moniteurRepository.saveAndFlush(moniteur);

        int databaseSizeBeforeUpdate = moniteurRepository.findAll().size();

        // Update the moniteur
        Moniteur updatedMoniteur = moniteurRepository.findById(moniteur.getId()).get();
        // Disconnect from session so that the updates on updatedMoniteur are not directly saved in db
        em.detach(updatedMoniteur);
        updatedMoniteur
            .cin(UPDATED_CIN)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .type(UPDATED_TYPE)
            .adresse(UPDATED_ADRESSE)
            .datenais(UPDATED_DATENAIS);

        restMoniteurMockMvc.perform(put("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMoniteur)))
            .andExpect(status().isOk());

        // Validate the Moniteur in the database
        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeUpdate);
        Moniteur testMoniteur = moniteurList.get(moniteurList.size() - 1);
        assertThat(testMoniteur.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testMoniteur.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testMoniteur.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMoniteur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMoniteur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMoniteur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMoniteur.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMoniteur.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testMoniteur.getDatenais()).isEqualTo(UPDATED_DATENAIS);
    }

    @Test
    @Transactional
    public void updateNonExistingMoniteur() throws Exception {
        int databaseSizeBeforeUpdate = moniteurRepository.findAll().size();

        // Create the Moniteur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoniteurMockMvc.perform(put("/api/moniteurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(moniteur)))
            .andExpect(status().isBadRequest());

        // Validate the Moniteur in the database
        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMoniteur() throws Exception {
        // Initialize the database
        moniteurRepository.saveAndFlush(moniteur);

        int databaseSizeBeforeDelete = moniteurRepository.findAll().size();

        // Delete the moniteur
        restMoniteurMockMvc.perform(delete("/api/moniteurs/{id}", moniteur.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Moniteur> moniteurList = moniteurRepository.findAll();
        assertThat(moniteurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
