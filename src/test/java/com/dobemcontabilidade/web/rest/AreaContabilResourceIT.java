package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AreaContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.repository.AreaContabilRepository;
import com.dobemcontabilidade.service.dto.AreaContabilDTO;
import com.dobemcontabilidade.service.mapper.AreaContabilMapper;
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
 * Integration tests for the {@link AreaContabilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AreaContabilResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/area-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AreaContabilRepository areaContabilRepository;

    @Autowired
    private AreaContabilMapper areaContabilMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaContabilMockMvc;

    private AreaContabil areaContabil;

    private AreaContabil insertedAreaContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaContabil createEntity(EntityManager em) {
        AreaContabil areaContabil = new AreaContabil().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return areaContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaContabil createUpdatedEntity(EntityManager em) {
        AreaContabil areaContabil = new AreaContabil().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return areaContabil;
    }

    @BeforeEach
    public void initTest() {
        areaContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAreaContabil != null) {
            areaContabilRepository.delete(insertedAreaContabil);
            insertedAreaContabil = null;
        }
    }

    @Test
    @Transactional
    void createAreaContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AreaContabil
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(areaContabil);
        var returnedAreaContabilDTO = om.readValue(
            restAreaContabilMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AreaContabilDTO.class
        );

        // Validate the AreaContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAreaContabil = areaContabilMapper.toEntity(returnedAreaContabilDTO);
        assertAreaContabilUpdatableFieldsEquals(returnedAreaContabil, getPersistedAreaContabil(returnedAreaContabil));

        insertedAreaContabil = returnedAreaContabil;
    }

    @Test
    @Transactional
    void createAreaContabilWithExistingId() throws Exception {
        // Create the AreaContabil with an existing ID
        areaContabil.setId(1L);
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(areaContabil);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaContabilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAreaContabils() throws Exception {
        // Initialize the database
        insertedAreaContabil = areaContabilRepository.saveAndFlush(areaContabil);

        // Get all the areaContabilList
        restAreaContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getAreaContabil() throws Exception {
        // Initialize the database
        insertedAreaContabil = areaContabilRepository.saveAndFlush(areaContabil);

        // Get the areaContabil
        restAreaContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, areaContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaContabil.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAreaContabil() throws Exception {
        // Get the areaContabil
        restAreaContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAreaContabil() throws Exception {
        // Initialize the database
        insertedAreaContabil = areaContabilRepository.saveAndFlush(areaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabil
        AreaContabil updatedAreaContabil = areaContabilRepository.findById(areaContabil.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAreaContabil are not directly saved in db
        em.detach(updatedAreaContabil);
        updatedAreaContabil.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(updatedAreaContabil);

        restAreaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilDTO))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAreaContabilToMatchAllProperties(updatedAreaContabil);
    }

    @Test
    @Transactional
    void putNonExistingAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabil.setId(longCount.incrementAndGet());

        // Create the AreaContabil
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(areaContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabil.setId(longCount.incrementAndGet());

        // Create the AreaContabil
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(areaContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabil.setId(longCount.incrementAndGet());

        // Create the AreaContabil
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(areaContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaContabilWithPatch() throws Exception {
        // Initialize the database
        insertedAreaContabil = areaContabilRepository.saveAndFlush(areaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabil using partial update
        AreaContabil partialUpdatedAreaContabil = new AreaContabil();
        partialUpdatedAreaContabil.setId(areaContabil.getId());

        partialUpdatedAreaContabil.descricao(UPDATED_DESCRICAO);

        restAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaContabil))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAreaContabil, areaContabil),
            getPersistedAreaContabil(areaContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdateAreaContabilWithPatch() throws Exception {
        // Initialize the database
        insertedAreaContabil = areaContabilRepository.saveAndFlush(areaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabil using partial update
        AreaContabil partialUpdatedAreaContabil = new AreaContabil();
        partialUpdatedAreaContabil.setId(areaContabil.getId());

        partialUpdatedAreaContabil.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaContabil))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaContabilUpdatableFieldsEquals(partialUpdatedAreaContabil, getPersistedAreaContabil(partialUpdatedAreaContabil));
    }

    @Test
    @Transactional
    void patchNonExistingAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabil.setId(longCount.incrementAndGet());

        // Create the AreaContabil
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(areaContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaContabilDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabil.setId(longCount.incrementAndGet());

        // Create the AreaContabil
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(areaContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabil.setId(longCount.incrementAndGet());

        // Create the AreaContabil
        AreaContabilDTO areaContabilDTO = areaContabilMapper.toDto(areaContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(areaContabilDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAreaContabil() throws Exception {
        // Initialize the database
        insertedAreaContabil = areaContabilRepository.saveAndFlush(areaContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the areaContabil
        restAreaContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, areaContabil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return areaContabilRepository.count();
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

    protected AreaContabil getPersistedAreaContabil(AreaContabil areaContabil) {
        return areaContabilRepository.findById(areaContabil.getId()).orElseThrow();
    }

    protected void assertPersistedAreaContabilToMatchAllProperties(AreaContabil expectedAreaContabil) {
        assertAreaContabilAllPropertiesEquals(expectedAreaContabil, getPersistedAreaContabil(expectedAreaContabil));
    }

    protected void assertPersistedAreaContabilToMatchUpdatableProperties(AreaContabil expectedAreaContabil) {
        assertAreaContabilAllUpdatablePropertiesEquals(expectedAreaContabil, getPersistedAreaContabil(expectedAreaContabil));
    }
}
