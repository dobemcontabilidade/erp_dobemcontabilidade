package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SecaoCnaeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.SecaoCnae;
import com.dobemcontabilidade.repository.SecaoCnaeRepository;
import com.dobemcontabilidade.service.dto.SecaoCnaeDTO;
import com.dobemcontabilidade.service.mapper.SecaoCnaeMapper;
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
 * Integration tests for the {@link SecaoCnaeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SecaoCnaeResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/secao-cnaes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SecaoCnaeRepository secaoCnaeRepository;

    @Autowired
    private SecaoCnaeMapper secaoCnaeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecaoCnaeMockMvc;

    private SecaoCnae secaoCnae;

    private SecaoCnae insertedSecaoCnae;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecaoCnae createEntity(EntityManager em) {
        SecaoCnae secaoCnae = new SecaoCnae().codigo(DEFAULT_CODIGO).descricao(DEFAULT_DESCRICAO);
        return secaoCnae;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecaoCnae createUpdatedEntity(EntityManager em) {
        SecaoCnae secaoCnae = new SecaoCnae().codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        return secaoCnae;
    }

    @BeforeEach
    public void initTest() {
        secaoCnae = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSecaoCnae != null) {
            secaoCnaeRepository.delete(insertedSecaoCnae);
            insertedSecaoCnae = null;
        }
    }

    @Test
    @Transactional
    void createSecaoCnae() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SecaoCnae
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(secaoCnae);
        var returnedSecaoCnaeDTO = om.readValue(
            restSecaoCnaeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secaoCnaeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SecaoCnaeDTO.class
        );

        // Validate the SecaoCnae in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSecaoCnae = secaoCnaeMapper.toEntity(returnedSecaoCnaeDTO);
        assertSecaoCnaeUpdatableFieldsEquals(returnedSecaoCnae, getPersistedSecaoCnae(returnedSecaoCnae));

        insertedSecaoCnae = returnedSecaoCnae;
    }

    @Test
    @Transactional
    void createSecaoCnaeWithExistingId() throws Exception {
        // Create the SecaoCnae with an existing ID
        secaoCnae.setId(1L);
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(secaoCnae);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecaoCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secaoCnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SecaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSecaoCnaes() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        // Get all the secaoCnaeList
        restSecaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secaoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getSecaoCnae() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        // Get the secaoCnae
        restSecaoCnaeMockMvc
            .perform(get(ENTITY_API_URL_ID, secaoCnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(secaoCnae.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getSecaoCnaesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        Long id = secaoCnae.getId();

        defaultSecaoCnaeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSecaoCnaeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSecaoCnaeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSecaoCnaesByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        // Get all the secaoCnaeList where codigo equals to
        defaultSecaoCnaeFiltering("codigo.equals=" + DEFAULT_CODIGO, "codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllSecaoCnaesByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        // Get all the secaoCnaeList where codigo in
        defaultSecaoCnaeFiltering("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO, "codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllSecaoCnaesByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        // Get all the secaoCnaeList where codigo is not null
        defaultSecaoCnaeFiltering("codigo.specified=true", "codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllSecaoCnaesByCodigoContainsSomething() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        // Get all the secaoCnaeList where codigo contains
        defaultSecaoCnaeFiltering("codigo.contains=" + DEFAULT_CODIGO, "codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllSecaoCnaesByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        // Get all the secaoCnaeList where codigo does not contain
        defaultSecaoCnaeFiltering("codigo.doesNotContain=" + UPDATED_CODIGO, "codigo.doesNotContain=" + DEFAULT_CODIGO);
    }

    private void defaultSecaoCnaeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSecaoCnaeShouldBeFound(shouldBeFound);
        defaultSecaoCnaeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSecaoCnaeShouldBeFound(String filter) throws Exception {
        restSecaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secaoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restSecaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSecaoCnaeShouldNotBeFound(String filter) throws Exception {
        restSecaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSecaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSecaoCnae() throws Exception {
        // Get the secaoCnae
        restSecaoCnaeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSecaoCnae() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the secaoCnae
        SecaoCnae updatedSecaoCnae = secaoCnaeRepository.findById(secaoCnae.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSecaoCnae are not directly saved in db
        em.detach(updatedSecaoCnae);
        updatedSecaoCnae.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(updatedSecaoCnae);

        restSecaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, secaoCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(secaoCnaeDTO))
            )
            .andExpect(status().isOk());

        // Validate the SecaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSecaoCnaeToMatchAllProperties(updatedSecaoCnae);
    }

    @Test
    @Transactional
    void putNonExistingSecaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secaoCnae.setId(longCount.incrementAndGet());

        // Create the SecaoCnae
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(secaoCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, secaoCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(secaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSecaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secaoCnae.setId(longCount.incrementAndGet());

        // Create the SecaoCnae
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(secaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(secaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSecaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secaoCnae.setId(longCount.incrementAndGet());

        // Create the SecaoCnae
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(secaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecaoCnaeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(secaoCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSecaoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the secaoCnae using partial update
        SecaoCnae partialUpdatedSecaoCnae = new SecaoCnae();
        partialUpdatedSecaoCnae.setId(secaoCnae.getId());

        partialUpdatedSecaoCnae.descricao(UPDATED_DESCRICAO);

        restSecaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecaoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSecaoCnae))
            )
            .andExpect(status().isOk());

        // Validate the SecaoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSecaoCnaeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSecaoCnae, secaoCnae),
            getPersistedSecaoCnae(secaoCnae)
        );
    }

    @Test
    @Transactional
    void fullUpdateSecaoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the secaoCnae using partial update
        SecaoCnae partialUpdatedSecaoCnae = new SecaoCnae();
        partialUpdatedSecaoCnae.setId(secaoCnae.getId());

        partialUpdatedSecaoCnae.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restSecaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSecaoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSecaoCnae))
            )
            .andExpect(status().isOk());

        // Validate the SecaoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSecaoCnaeUpdatableFieldsEquals(partialUpdatedSecaoCnae, getPersistedSecaoCnae(partialUpdatedSecaoCnae));
    }

    @Test
    @Transactional
    void patchNonExistingSecaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secaoCnae.setId(longCount.incrementAndGet());

        // Create the SecaoCnae
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(secaoCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, secaoCnaeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(secaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSecaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secaoCnae.setId(longCount.incrementAndGet());

        // Create the SecaoCnae
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(secaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(secaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SecaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSecaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        secaoCnae.setId(longCount.incrementAndGet());

        // Create the SecaoCnae
        SecaoCnaeDTO secaoCnaeDTO = secaoCnaeMapper.toDto(secaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSecaoCnaeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(secaoCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SecaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSecaoCnae() throws Exception {
        // Initialize the database
        insertedSecaoCnae = secaoCnaeRepository.saveAndFlush(secaoCnae);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the secaoCnae
        restSecaoCnaeMockMvc
            .perform(delete(ENTITY_API_URL_ID, secaoCnae.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return secaoCnaeRepository.count();
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

    protected SecaoCnae getPersistedSecaoCnae(SecaoCnae secaoCnae) {
        return secaoCnaeRepository.findById(secaoCnae.getId()).orElseThrow();
    }

    protected void assertPersistedSecaoCnaeToMatchAllProperties(SecaoCnae expectedSecaoCnae) {
        assertSecaoCnaeAllPropertiesEquals(expectedSecaoCnae, getPersistedSecaoCnae(expectedSecaoCnae));
    }

    protected void assertPersistedSecaoCnaeToMatchUpdatableProperties(SecaoCnae expectedSecaoCnae) {
        assertSecaoCnaeAllUpdatablePropertiesEquals(expectedSecaoCnae, getPersistedSecaoCnae(expectedSecaoCnae));
    }
}
