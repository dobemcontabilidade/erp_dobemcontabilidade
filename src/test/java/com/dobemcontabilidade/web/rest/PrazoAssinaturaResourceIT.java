package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PrazoAssinaturaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PrazoAssinatura;
import com.dobemcontabilidade.repository.PrazoAssinaturaRepository;
import com.dobemcontabilidade.service.dto.PrazoAssinaturaDTO;
import com.dobemcontabilidade.service.mapper.PrazoAssinaturaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PrazoAssinaturaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrazoAssinaturaResourceIT {

    private static final String DEFAULT_PRAZO = "AAAAAAAAAA";
    private static final String UPDATED_PRAZO = "BBBBBBBBBB";

    private static final Integer DEFAULT_MESES = 1;
    private static final Integer UPDATED_MESES = 2;
    private static final Integer SMALLER_MESES = 1 - 1;

    private static final String ENTITY_API_URL = "/api/prazo-assinaturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PrazoAssinaturaRepository prazoAssinaturaRepository;

    @Autowired
    private PrazoAssinaturaMapper prazoAssinaturaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrazoAssinaturaMockMvc;

    private PrazoAssinatura prazoAssinatura;

    private PrazoAssinatura insertedPrazoAssinatura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrazoAssinatura createEntity(EntityManager em) {
        PrazoAssinatura prazoAssinatura = new PrazoAssinatura().prazo(DEFAULT_PRAZO).meses(DEFAULT_MESES);
        return prazoAssinatura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrazoAssinatura createUpdatedEntity(EntityManager em) {
        PrazoAssinatura prazoAssinatura = new PrazoAssinatura().prazo(UPDATED_PRAZO).meses(UPDATED_MESES);
        return prazoAssinatura;
    }

    @BeforeEach
    public void initTest() {
        prazoAssinatura = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPrazoAssinatura != null) {
            prazoAssinaturaRepository.delete(insertedPrazoAssinatura);
            insertedPrazoAssinatura = null;
        }
    }

    @Test
    @Transactional
    void createPrazoAssinatura() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PrazoAssinatura
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(prazoAssinatura);
        var returnedPrazoAssinaturaDTO = om.readValue(
            restPrazoAssinaturaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prazoAssinaturaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PrazoAssinaturaDTO.class
        );

        // Validate the PrazoAssinatura in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPrazoAssinatura = prazoAssinaturaMapper.toEntity(returnedPrazoAssinaturaDTO);
        assertPrazoAssinaturaUpdatableFieldsEquals(returnedPrazoAssinatura, getPersistedPrazoAssinatura(returnedPrazoAssinatura));

        insertedPrazoAssinatura = returnedPrazoAssinatura;
    }

    @Test
    @Transactional
    void createPrazoAssinaturaWithExistingId() throws Exception {
        // Create the PrazoAssinatura with an existing ID
        prazoAssinatura.setId(1L);
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(prazoAssinatura);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrazoAssinaturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prazoAssinaturaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrazoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturas() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList
        restPrazoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prazoAssinatura.getId().intValue())))
            .andExpect(jsonPath("$.[*].prazo").value(hasItem(DEFAULT_PRAZO)))
            .andExpect(jsonPath("$.[*].meses").value(hasItem(DEFAULT_MESES)));
    }

    @Test
    @Transactional
    void getPrazoAssinatura() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get the prazoAssinatura
        restPrazoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL_ID, prazoAssinatura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prazoAssinatura.getId().intValue()))
            .andExpect(jsonPath("$.prazo").value(DEFAULT_PRAZO))
            .andExpect(jsonPath("$.meses").value(DEFAULT_MESES));
    }

    @Test
    @Transactional
    void getPrazoAssinaturasByIdFiltering() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        Long id = prazoAssinatura.getId();

        defaultPrazoAssinaturaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPrazoAssinaturaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPrazoAssinaturaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByPrazoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where prazo equals to
        defaultPrazoAssinaturaFiltering("prazo.equals=" + DEFAULT_PRAZO, "prazo.equals=" + UPDATED_PRAZO);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByPrazoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where prazo in
        defaultPrazoAssinaturaFiltering("prazo.in=" + DEFAULT_PRAZO + "," + UPDATED_PRAZO, "prazo.in=" + UPDATED_PRAZO);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByPrazoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where prazo is not null
        defaultPrazoAssinaturaFiltering("prazo.specified=true", "prazo.specified=false");
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByPrazoContainsSomething() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where prazo contains
        defaultPrazoAssinaturaFiltering("prazo.contains=" + DEFAULT_PRAZO, "prazo.contains=" + UPDATED_PRAZO);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByPrazoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where prazo does not contain
        defaultPrazoAssinaturaFiltering("prazo.doesNotContain=" + UPDATED_PRAZO, "prazo.doesNotContain=" + DEFAULT_PRAZO);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByMesesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where meses equals to
        defaultPrazoAssinaturaFiltering("meses.equals=" + DEFAULT_MESES, "meses.equals=" + UPDATED_MESES);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByMesesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where meses in
        defaultPrazoAssinaturaFiltering("meses.in=" + DEFAULT_MESES + "," + UPDATED_MESES, "meses.in=" + UPDATED_MESES);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByMesesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where meses is not null
        defaultPrazoAssinaturaFiltering("meses.specified=true", "meses.specified=false");
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByMesesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where meses is greater than or equal to
        defaultPrazoAssinaturaFiltering("meses.greaterThanOrEqual=" + DEFAULT_MESES, "meses.greaterThanOrEqual=" + UPDATED_MESES);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByMesesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where meses is less than or equal to
        defaultPrazoAssinaturaFiltering("meses.lessThanOrEqual=" + DEFAULT_MESES, "meses.lessThanOrEqual=" + SMALLER_MESES);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByMesesIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where meses is less than
        defaultPrazoAssinaturaFiltering("meses.lessThan=" + UPDATED_MESES, "meses.lessThan=" + DEFAULT_MESES);
    }

    @Test
    @Transactional
    void getAllPrazoAssinaturasByMesesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        // Get all the prazoAssinaturaList where meses is greater than
        defaultPrazoAssinaturaFiltering("meses.greaterThan=" + SMALLER_MESES, "meses.greaterThan=" + DEFAULT_MESES);
    }

    private void defaultPrazoAssinaturaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPrazoAssinaturaShouldBeFound(shouldBeFound);
        defaultPrazoAssinaturaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrazoAssinaturaShouldBeFound(String filter) throws Exception {
        restPrazoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prazoAssinatura.getId().intValue())))
            .andExpect(jsonPath("$.[*].prazo").value(hasItem(DEFAULT_PRAZO)))
            .andExpect(jsonPath("$.[*].meses").value(hasItem(DEFAULT_MESES)));

        // Check, that the count call also returns 1
        restPrazoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrazoAssinaturaShouldNotBeFound(String filter) throws Exception {
        restPrazoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrazoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPrazoAssinatura() throws Exception {
        // Get the prazoAssinatura
        restPrazoAssinaturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrazoAssinatura() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prazoAssinatura
        PrazoAssinatura updatedPrazoAssinatura = prazoAssinaturaRepository.findById(prazoAssinatura.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrazoAssinatura are not directly saved in db
        em.detach(updatedPrazoAssinatura);
        updatedPrazoAssinatura.prazo(UPDATED_PRAZO).meses(UPDATED_MESES);
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(updatedPrazoAssinatura);

        restPrazoAssinaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prazoAssinaturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prazoAssinaturaDTO))
            )
            .andExpect(status().isOk());

        // Validate the PrazoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPrazoAssinaturaToMatchAllProperties(updatedPrazoAssinatura);
    }

    @Test
    @Transactional
    void putNonExistingPrazoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prazoAssinatura.setId(longCount.incrementAndGet());

        // Create the PrazoAssinatura
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(prazoAssinatura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrazoAssinaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prazoAssinaturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prazoAssinaturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrazoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrazoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prazoAssinatura.setId(longCount.incrementAndGet());

        // Create the PrazoAssinatura
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(prazoAssinatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrazoAssinaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(prazoAssinaturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrazoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrazoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prazoAssinatura.setId(longCount.incrementAndGet());

        // Create the PrazoAssinatura
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(prazoAssinatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrazoAssinaturaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(prazoAssinaturaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrazoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrazoAssinaturaWithPatch() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prazoAssinatura using partial update
        PrazoAssinatura partialUpdatedPrazoAssinatura = new PrazoAssinatura();
        partialUpdatedPrazoAssinatura.setId(prazoAssinatura.getId());

        partialUpdatedPrazoAssinatura.prazo(UPDATED_PRAZO);

        restPrazoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrazoAssinatura.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrazoAssinatura))
            )
            .andExpect(status().isOk());

        // Validate the PrazoAssinatura in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrazoAssinaturaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPrazoAssinatura, prazoAssinatura),
            getPersistedPrazoAssinatura(prazoAssinatura)
        );
    }

    @Test
    @Transactional
    void fullUpdatePrazoAssinaturaWithPatch() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prazoAssinatura using partial update
        PrazoAssinatura partialUpdatedPrazoAssinatura = new PrazoAssinatura();
        partialUpdatedPrazoAssinatura.setId(prazoAssinatura.getId());

        partialUpdatedPrazoAssinatura.prazo(UPDATED_PRAZO).meses(UPDATED_MESES);

        restPrazoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrazoAssinatura.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrazoAssinatura))
            )
            .andExpect(status().isOk());

        // Validate the PrazoAssinatura in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrazoAssinaturaUpdatableFieldsEquals(
            partialUpdatedPrazoAssinatura,
            getPersistedPrazoAssinatura(partialUpdatedPrazoAssinatura)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPrazoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prazoAssinatura.setId(longCount.incrementAndGet());

        // Create the PrazoAssinatura
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(prazoAssinatura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrazoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prazoAssinaturaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prazoAssinaturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrazoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrazoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prazoAssinatura.setId(longCount.incrementAndGet());

        // Create the PrazoAssinatura
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(prazoAssinatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrazoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(prazoAssinaturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrazoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrazoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prazoAssinatura.setId(longCount.incrementAndGet());

        // Create the PrazoAssinatura
        PrazoAssinaturaDTO prazoAssinaturaDTO = prazoAssinaturaMapper.toDto(prazoAssinatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrazoAssinaturaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(prazoAssinaturaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrazoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrazoAssinatura() throws Exception {
        // Initialize the database
        insertedPrazoAssinatura = prazoAssinaturaRepository.saveAndFlush(prazoAssinatura);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prazoAssinatura
        restPrazoAssinaturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, prazoAssinatura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return prazoAssinaturaRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected PrazoAssinatura getPersistedPrazoAssinatura(PrazoAssinatura prazoAssinatura) {
        return prazoAssinaturaRepository.findById(prazoAssinatura.getId()).orElseThrow();
    }

    protected void assertPersistedPrazoAssinaturaToMatchAllProperties(PrazoAssinatura expectedPrazoAssinatura) {
        assertPrazoAssinaturaAllPropertiesEquals(expectedPrazoAssinatura, getPersistedPrazoAssinatura(expectedPrazoAssinatura));
    }

    protected void assertPersistedPrazoAssinaturaToMatchUpdatableProperties(PrazoAssinatura expectedPrazoAssinatura) {
        assertPrazoAssinaturaAllUpdatablePropertiesEquals(expectedPrazoAssinatura, getPersistedPrazoAssinatura(expectedPrazoAssinatura));
    }
}
