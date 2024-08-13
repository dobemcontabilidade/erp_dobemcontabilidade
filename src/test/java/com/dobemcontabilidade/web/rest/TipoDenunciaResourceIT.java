package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TipoDenunciaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.TipoDenuncia;
import com.dobemcontabilidade.repository.TipoDenunciaRepository;
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
 * Integration tests for the {@link TipoDenunciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoDenunciaResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-denuncias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TipoDenunciaRepository tipoDenunciaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDenunciaMockMvc;

    private TipoDenuncia tipoDenuncia;

    private TipoDenuncia insertedTipoDenuncia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDenuncia createEntity(EntityManager em) {
        TipoDenuncia tipoDenuncia = new TipoDenuncia().tipo(DEFAULT_TIPO).descricao(DEFAULT_DESCRICAO);
        return tipoDenuncia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDenuncia createUpdatedEntity(EntityManager em) {
        TipoDenuncia tipoDenuncia = new TipoDenuncia().tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);
        return tipoDenuncia;
    }

    @BeforeEach
    public void initTest() {
        tipoDenuncia = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTipoDenuncia != null) {
            tipoDenunciaRepository.delete(insertedTipoDenuncia);
            insertedTipoDenuncia = null;
        }
    }

    @Test
    @Transactional
    void createTipoDenuncia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TipoDenuncia
        var returnedTipoDenuncia = om.readValue(
            restTipoDenunciaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoDenuncia)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TipoDenuncia.class
        );

        // Validate the TipoDenuncia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTipoDenunciaUpdatableFieldsEquals(returnedTipoDenuncia, getPersistedTipoDenuncia(returnedTipoDenuncia));

        insertedTipoDenuncia = returnedTipoDenuncia;
    }

    @Test
    @Transactional
    void createTipoDenunciaWithExistingId() throws Exception {
        // Create the TipoDenuncia with an existing ID
        tipoDenuncia.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDenunciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoDenuncia)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDenuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoDenuncias() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        // Get all the tipoDenunciaList
        restTipoDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDenuncia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getTipoDenuncia() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        // Get the tipoDenuncia
        restTipoDenunciaMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoDenuncia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDenuncia.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getTipoDenunciasByIdFiltering() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        Long id = tipoDenuncia.getId();

        defaultTipoDenunciaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTipoDenunciaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTipoDenunciaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTipoDenunciasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        // Get all the tipoDenunciaList where tipo equals to
        defaultTipoDenunciaFiltering("tipo.equals=" + DEFAULT_TIPO, "tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllTipoDenunciasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        // Get all the tipoDenunciaList where tipo in
        defaultTipoDenunciaFiltering("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO, "tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllTipoDenunciasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        // Get all the tipoDenunciaList where tipo is not null
        defaultTipoDenunciaFiltering("tipo.specified=true", "tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllTipoDenunciasByTipoContainsSomething() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        // Get all the tipoDenunciaList where tipo contains
        defaultTipoDenunciaFiltering("tipo.contains=" + DEFAULT_TIPO, "tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllTipoDenunciasByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        // Get all the tipoDenunciaList where tipo does not contain
        defaultTipoDenunciaFiltering("tipo.doesNotContain=" + UPDATED_TIPO, "tipo.doesNotContain=" + DEFAULT_TIPO);
    }

    private void defaultTipoDenunciaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTipoDenunciaShouldBeFound(shouldBeFound);
        defaultTipoDenunciaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoDenunciaShouldBeFound(String filter) throws Exception {
        restTipoDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDenuncia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restTipoDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoDenunciaShouldNotBeFound(String filter) throws Exception {
        restTipoDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTipoDenuncia() throws Exception {
        // Get the tipoDenuncia
        restTipoDenunciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoDenuncia() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoDenuncia
        TipoDenuncia updatedTipoDenuncia = tipoDenunciaRepository.findById(tipoDenuncia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTipoDenuncia are not directly saved in db
        em.detach(updatedTipoDenuncia);
        updatedTipoDenuncia.tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

        restTipoDenunciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoDenuncia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTipoDenuncia))
            )
            .andExpect(status().isOk());

        // Validate the TipoDenuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTipoDenunciaToMatchAllProperties(updatedTipoDenuncia);
    }

    @Test
    @Transactional
    void putNonExistingTipoDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoDenuncia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDenunciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoDenuncia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tipoDenuncia))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDenuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoDenuncia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDenunciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tipoDenuncia))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDenuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoDenuncia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDenunciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoDenuncia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoDenuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoDenunciaWithPatch() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoDenuncia using partial update
        TipoDenuncia partialUpdatedTipoDenuncia = new TipoDenuncia();
        partialUpdatedTipoDenuncia.setId(tipoDenuncia.getId());

        partialUpdatedTipoDenuncia.descricao(UPDATED_DESCRICAO);

        restTipoDenunciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoDenuncia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTipoDenuncia))
            )
            .andExpect(status().isOk());

        // Validate the TipoDenuncia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTipoDenunciaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTipoDenuncia, tipoDenuncia),
            getPersistedTipoDenuncia(tipoDenuncia)
        );
    }

    @Test
    @Transactional
    void fullUpdateTipoDenunciaWithPatch() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoDenuncia using partial update
        TipoDenuncia partialUpdatedTipoDenuncia = new TipoDenuncia();
        partialUpdatedTipoDenuncia.setId(tipoDenuncia.getId());

        partialUpdatedTipoDenuncia.tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

        restTipoDenunciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoDenuncia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTipoDenuncia))
            )
            .andExpect(status().isOk());

        // Validate the TipoDenuncia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTipoDenunciaUpdatableFieldsEquals(partialUpdatedTipoDenuncia, getPersistedTipoDenuncia(partialUpdatedTipoDenuncia));
    }

    @Test
    @Transactional
    void patchNonExistingTipoDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoDenuncia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDenunciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoDenuncia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tipoDenuncia))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDenuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoDenuncia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDenunciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tipoDenuncia))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoDenuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoDenuncia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoDenunciaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tipoDenuncia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoDenuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoDenuncia() throws Exception {
        // Initialize the database
        insertedTipoDenuncia = tipoDenunciaRepository.saveAndFlush(tipoDenuncia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tipoDenuncia
        restTipoDenunciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoDenuncia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tipoDenunciaRepository.count();
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

    protected TipoDenuncia getPersistedTipoDenuncia(TipoDenuncia tipoDenuncia) {
        return tipoDenunciaRepository.findById(tipoDenuncia.getId()).orElseThrow();
    }

    protected void assertPersistedTipoDenunciaToMatchAllProperties(TipoDenuncia expectedTipoDenuncia) {
        assertTipoDenunciaAllPropertiesEquals(expectedTipoDenuncia, getPersistedTipoDenuncia(expectedTipoDenuncia));
    }

    protected void assertPersistedTipoDenunciaToMatchUpdatableProperties(TipoDenuncia expectedTipoDenuncia) {
        assertTipoDenunciaAllUpdatablePropertiesEquals(expectedTipoDenuncia, getPersistedTipoDenuncia(expectedTipoDenuncia));
    }
}
