package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AutoEcoleApp;
import com.mycompany.myapp.domain.Payement;
import com.mycompany.myapp.repository.PayementRepository;

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
 * Integration tests for the {@link PayementResource} REST controller.
 */
@SpringBootTest(classes = AutoEcoleApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PayementResourceIT {

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final Integer DEFAULT_RESTE = 1;
    private static final Integer UPDATED_RESTE = 2;

    private static final Integer DEFAULT_VENDU = 1;
    private static final Integer UPDATED_VENDU = 2;

    private static final String DEFAULT_REMARQUE = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUE = "BBBBBBBBBB";

    @Autowired
    private PayementRepository payementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayementMockMvc;

    private Payement payement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payement createEntity(EntityManager em) {
        Payement payement = new Payement()
            .total(DEFAULT_TOTAL)
            .reste(DEFAULT_RESTE)
            .vendu(DEFAULT_VENDU)
            .remarque(DEFAULT_REMARQUE);
        return payement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payement createUpdatedEntity(EntityManager em) {
        Payement payement = new Payement()
            .total(UPDATED_TOTAL)
            .reste(UPDATED_RESTE)
            .vendu(UPDATED_VENDU)
            .remarque(UPDATED_REMARQUE);
        return payement;
    }

    @BeforeEach
    public void initTest() {
        payement = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayement() throws Exception {
        int databaseSizeBeforeCreate = payementRepository.findAll().size();

        // Create the Payement
        restPayementMockMvc.perform(post("/api/payements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payement)))
            .andExpect(status().isCreated());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeCreate + 1);
        Payement testPayement = payementList.get(payementList.size() - 1);
        assertThat(testPayement.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testPayement.getReste()).isEqualTo(DEFAULT_RESTE);
        assertThat(testPayement.getVendu()).isEqualTo(DEFAULT_VENDU);
        assertThat(testPayement.getRemarque()).isEqualTo(DEFAULT_REMARQUE);
    }

    @Test
    @Transactional
    public void createPayementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payementRepository.findAll().size();

        // Create the Payement with an existing ID
        payement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayementMockMvc.perform(post("/api/payements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payement)))
            .andExpect(status().isBadRequest());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPayements() throws Exception {
        // Initialize the database
        payementRepository.saveAndFlush(payement);

        // Get all the payementList
        restPayementMockMvc.perform(get("/api/payements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payement.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].reste").value(hasItem(DEFAULT_RESTE)))
            .andExpect(jsonPath("$.[*].vendu").value(hasItem(DEFAULT_VENDU)))
            .andExpect(jsonPath("$.[*].remarque").value(hasItem(DEFAULT_REMARQUE)));
    }
    
    @Test
    @Transactional
    public void getPayement() throws Exception {
        // Initialize the database
        payementRepository.saveAndFlush(payement);

        // Get the payement
        restPayementMockMvc.perform(get("/api/payements/{id}", payement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payement.getId().intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.reste").value(DEFAULT_RESTE))
            .andExpect(jsonPath("$.vendu").value(DEFAULT_VENDU))
            .andExpect(jsonPath("$.remarque").value(DEFAULT_REMARQUE));
    }

    @Test
    @Transactional
    public void getNonExistingPayement() throws Exception {
        // Get the payement
        restPayementMockMvc.perform(get("/api/payements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayement() throws Exception {
        // Initialize the database
        payementRepository.saveAndFlush(payement);

        int databaseSizeBeforeUpdate = payementRepository.findAll().size();

        // Update the payement
        Payement updatedPayement = payementRepository.findById(payement.getId()).get();
        // Disconnect from session so that the updates on updatedPayement are not directly saved in db
        em.detach(updatedPayement);
        updatedPayement
            .total(UPDATED_TOTAL)
            .reste(UPDATED_RESTE)
            .vendu(UPDATED_VENDU)
            .remarque(UPDATED_REMARQUE);

        restPayementMockMvc.perform(put("/api/payements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayement)))
            .andExpect(status().isOk());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
        Payement testPayement = payementList.get(payementList.size() - 1);
        assertThat(testPayement.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testPayement.getReste()).isEqualTo(UPDATED_RESTE);
        assertThat(testPayement.getVendu()).isEqualTo(UPDATED_VENDU);
        assertThat(testPayement.getRemarque()).isEqualTo(UPDATED_REMARQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPayement() throws Exception {
        int databaseSizeBeforeUpdate = payementRepository.findAll().size();

        // Create the Payement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayementMockMvc.perform(put("/api/payements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payement)))
            .andExpect(status().isBadRequest());

        // Validate the Payement in the database
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayement() throws Exception {
        // Initialize the database
        payementRepository.saveAndFlush(payement);

        int databaseSizeBeforeDelete = payementRepository.findAll().size();

        // Delete the payement
        restPayementMockMvc.perform(delete("/api/payements/{id}", payement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payement> payementList = payementRepository.findAll();
        assertThat(payementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
