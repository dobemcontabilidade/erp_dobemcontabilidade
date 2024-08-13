package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PermisaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Permisao;
import com.dobemcontabilidade.repository.PermisaoRepository;
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
 * Integration tests for the {@link PermisaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PermisaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/permisaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PermisaoRepository permisaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPermisaoMockMvc;

    private Permisao permisao;

    private Permisao insertedPermisao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permisao createEntity(EntityManager em) {
        Permisao permisao = new Permisao().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO).label(DEFAULT_LABEL);
        return permisao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permisao createUpdatedEntity(EntityManager em) {
        Permisao permisao = new Permisao().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).label(UPDATED_LABEL);
        return permisao;
    }

    @BeforeEach
    public void initTest() {
        permisao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPermisao != null) {
            permisaoRepository.delete(insertedPermisao);
            insertedPermisao = null;
        }
    }

    @Test
    @Transactional
    void createPermisao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Permisao
        var returnedPermisao = om.readValue(
            restPermisaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(permisao)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Permisao.class
        );

        // Validate the Permisao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPermisaoUpdatableFieldsEquals(returnedPermisao, getPersistedPermisao(returnedPermisao));

        insertedPermisao = returnedPermisao;
    }

    @Test
    @Transactional
    void createPermisaoWithExistingId() throws Exception {
        // Create the Permisao with an existing ID
        permisao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermisaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(permisao)))
            .andExpect(status().isBadRequest());

        // Validate the Permisao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPermisaos() throws Exception {
        // Initialize the database
        insertedPermisao = permisaoRepository.saveAndFlush(permisao);

        // Get all the permisaoList
        restPermisaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permisao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)));
    }

    @Test
    @Transactional
    void getPermisao() throws Exception {
        // Initialize the database
        insertedPermisao = permisaoRepository.saveAndFlush(permisao);

        // Get the permisao
        restPermisaoMockMvc
            .perform(get(ENTITY_API_URL_ID, permisao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(permisao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL));
    }

    @Test
    @Transactional
    void getNonExistingPermisao() throws Exception {
        // Get the permisao
        restPermisaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPermisao() throws Exception {
        // Initialize the database
        insertedPermisao = permisaoRepository.saveAndFlush(permisao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the permisao
        Permisao updatedPermisao = permisaoRepository.findById(permisao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPermisao are not directly saved in db
        em.detach(updatedPermisao);
        updatedPermisao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).label(UPDATED_LABEL);

        restPermisaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPermisao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPermisao))
            )
            .andExpect(status().isOk());

        // Validate the Permisao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPermisaoToMatchAllProperties(updatedPermisao);
    }

    @Test
    @Transactional
    void putNonExistingPermisao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        permisao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermisaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, permisao.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(permisao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permisao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPermisao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        permisao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermisaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(permisao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permisao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPermisao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        permisao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermisaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(permisao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Permisao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePermisaoWithPatch() throws Exception {
        // Initialize the database
        insertedPermisao = permisaoRepository.saveAndFlush(permisao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the permisao using partial update
        Permisao partialUpdatedPermisao = new Permisao();
        partialUpdatedPermisao.setId(permisao.getId());

        partialUpdatedPermisao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).label(UPDATED_LABEL);

        restPermisaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPermisao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPermisao))
            )
            .andExpect(status().isOk());

        // Validate the Permisao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPermisaoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPermisao, permisao), getPersistedPermisao(permisao));
    }

    @Test
    @Transactional
    void fullUpdatePermisaoWithPatch() throws Exception {
        // Initialize the database
        insertedPermisao = permisaoRepository.saveAndFlush(permisao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the permisao using partial update
        Permisao partialUpdatedPermisao = new Permisao();
        partialUpdatedPermisao.setId(permisao.getId());

        partialUpdatedPermisao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).label(UPDATED_LABEL);

        restPermisaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPermisao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPermisao))
            )
            .andExpect(status().isOk());

        // Validate the Permisao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPermisaoUpdatableFieldsEquals(partialUpdatedPermisao, getPersistedPermisao(partialUpdatedPermisao));
    }

    @Test
    @Transactional
    void patchNonExistingPermisao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        permisao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermisaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, permisao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(permisao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permisao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPermisao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        permisao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermisaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(permisao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Permisao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPermisao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        permisao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPermisaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(permisao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Permisao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePermisao() throws Exception {
        // Initialize the database
        insertedPermisao = permisaoRepository.saveAndFlush(permisao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the permisao
        restPermisaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, permisao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return permisaoRepository.count();
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

    protected Permisao getPersistedPermisao(Permisao permisao) {
        return permisaoRepository.findById(permisao.getId()).orElseThrow();
    }

    protected void assertPersistedPermisaoToMatchAllProperties(Permisao expectedPermisao) {
        assertPermisaoAllPropertiesEquals(expectedPermisao, getPersistedPermisao(expectedPermisao));
    }

    protected void assertPersistedPermisaoToMatchUpdatableProperties(Permisao expectedPermisao) {
        assertPermisaoAllUpdatablePropertiesEquals(expectedPermisao, getPersistedPermisao(expectedPermisao));
    }
}
