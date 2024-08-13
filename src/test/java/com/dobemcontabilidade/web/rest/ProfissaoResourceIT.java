package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ProfissaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Profissao;
import com.dobemcontabilidade.repository.ProfissaoRepository;
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
 * Integration tests for the {@link ProfissaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfissaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CBO = 1;
    private static final Integer UPDATED_CBO = 2;
    private static final Integer SMALLER_CBO = 1 - 1;

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profissaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProfissaoRepository profissaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfissaoMockMvc;

    private Profissao profissao;

    private Profissao insertedProfissao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissao createEntity(EntityManager em) {
        Profissao profissao = new Profissao().nome(DEFAULT_NOME).cbo(DEFAULT_CBO).categoria(DEFAULT_CATEGORIA).descricao(DEFAULT_DESCRICAO);
        return profissao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissao createUpdatedEntity(EntityManager em) {
        Profissao profissao = new Profissao().nome(UPDATED_NOME).cbo(UPDATED_CBO).categoria(UPDATED_CATEGORIA).descricao(UPDATED_DESCRICAO);
        return profissao;
    }

    @BeforeEach
    public void initTest() {
        profissao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProfissao != null) {
            profissaoRepository.delete(insertedProfissao);
            insertedProfissao = null;
        }
    }

    @Test
    @Transactional
    void createProfissao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Profissao
        var returnedProfissao = om.readValue(
            restProfissaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissao)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Profissao.class
        );

        // Validate the Profissao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProfissaoUpdatableFieldsEquals(returnedProfissao, getPersistedProfissao(returnedProfissao));

        insertedProfissao = returnedProfissao;
    }

    @Test
    @Transactional
    void createProfissaoWithExistingId() throws Exception {
        // Create the Profissao with an existing ID
        profissao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfissaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissao)))
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProfissaos() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profissao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cbo").value(hasItem(DEFAULT_CBO)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getProfissao() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get the profissao
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL_ID, profissao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profissao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cbo").value(DEFAULT_CBO))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getProfissaosByIdFiltering() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        Long id = profissao.getId();

        defaultProfissaoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultProfissaoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultProfissaoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome equals to
        defaultProfissaoFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome in
        defaultProfissaoFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome is not null
        defaultProfissaoFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome contains
        defaultProfissaoFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllProfissaosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where nome does not contain
        defaultProfissaoFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllProfissaosByCboIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where cbo equals to
        defaultProfissaoFiltering("cbo.equals=" + DEFAULT_CBO, "cbo.equals=" + UPDATED_CBO);
    }

    @Test
    @Transactional
    void getAllProfissaosByCboIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where cbo in
        defaultProfissaoFiltering("cbo.in=" + DEFAULT_CBO + "," + UPDATED_CBO, "cbo.in=" + UPDATED_CBO);
    }

    @Test
    @Transactional
    void getAllProfissaosByCboIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where cbo is not null
        defaultProfissaoFiltering("cbo.specified=true", "cbo.specified=false");
    }

    @Test
    @Transactional
    void getAllProfissaosByCboIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where cbo is greater than or equal to
        defaultProfissaoFiltering("cbo.greaterThanOrEqual=" + DEFAULT_CBO, "cbo.greaterThanOrEqual=" + UPDATED_CBO);
    }

    @Test
    @Transactional
    void getAllProfissaosByCboIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where cbo is less than or equal to
        defaultProfissaoFiltering("cbo.lessThanOrEqual=" + DEFAULT_CBO, "cbo.lessThanOrEqual=" + SMALLER_CBO);
    }

    @Test
    @Transactional
    void getAllProfissaosByCboIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where cbo is less than
        defaultProfissaoFiltering("cbo.lessThan=" + UPDATED_CBO, "cbo.lessThan=" + DEFAULT_CBO);
    }

    @Test
    @Transactional
    void getAllProfissaosByCboIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where cbo is greater than
        defaultProfissaoFiltering("cbo.greaterThan=" + SMALLER_CBO, "cbo.greaterThan=" + DEFAULT_CBO);
    }

    @Test
    @Transactional
    void getAllProfissaosByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where categoria equals to
        defaultProfissaoFiltering("categoria.equals=" + DEFAULT_CATEGORIA, "categoria.equals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void getAllProfissaosByCategoriaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where categoria in
        defaultProfissaoFiltering("categoria.in=" + DEFAULT_CATEGORIA + "," + UPDATED_CATEGORIA, "categoria.in=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void getAllProfissaosByCategoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where categoria is not null
        defaultProfissaoFiltering("categoria.specified=true", "categoria.specified=false");
    }

    @Test
    @Transactional
    void getAllProfissaosByCategoriaContainsSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where categoria contains
        defaultProfissaoFiltering("categoria.contains=" + DEFAULT_CATEGORIA, "categoria.contains=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void getAllProfissaosByCategoriaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        // Get all the profissaoList where categoria does not contain
        defaultProfissaoFiltering("categoria.doesNotContain=" + UPDATED_CATEGORIA, "categoria.doesNotContain=" + DEFAULT_CATEGORIA);
    }

    private void defaultProfissaoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultProfissaoShouldBeFound(shouldBeFound);
        defaultProfissaoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProfissaoShouldBeFound(String filter) throws Exception {
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profissao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cbo").value(hasItem(DEFAULT_CBO)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProfissaoShouldNotBeFound(String filter) throws Exception {
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfissaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProfissao() throws Exception {
        // Get the profissao
        restProfissaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfissao() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissao
        Profissao updatedProfissao = profissaoRepository.findById(profissao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProfissao are not directly saved in db
        em.detach(updatedProfissao);
        updatedProfissao.nome(UPDATED_NOME).cbo(UPDATED_CBO).categoria(UPDATED_CATEGORIA).descricao(UPDATED_DESCRICAO);

        restProfissaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfissao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProfissao))
            )
            .andExpect(status().isOk());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProfissaoToMatchAllProperties(updatedProfissao);
    }

    @Test
    @Transactional
    void putNonExistingProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profissao.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(profissao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(profissao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfissaoWithPatch() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissao using partial update
        Profissao partialUpdatedProfissao = new Profissao();
        partialUpdatedProfissao.setId(profissao.getId());

        partialUpdatedProfissao.nome(UPDATED_NOME).cbo(UPDATED_CBO).categoria(UPDATED_CATEGORIA);

        restProfissaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfissao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfissao))
            )
            .andExpect(status().isOk());

        // Validate the Profissao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfissaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProfissao, profissao),
            getPersistedProfissao(profissao)
        );
    }

    @Test
    @Transactional
    void fullUpdateProfissaoWithPatch() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profissao using partial update
        Profissao partialUpdatedProfissao = new Profissao();
        partialUpdatedProfissao.setId(profissao.getId());

        partialUpdatedProfissao.nome(UPDATED_NOME).cbo(UPDATED_CBO).categoria(UPDATED_CATEGORIA).descricao(UPDATED_DESCRICAO);

        restProfissaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfissao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfissao))
            )
            .andExpect(status().isOk());

        // Validate the Profissao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfissaoUpdatableFieldsEquals(partialUpdatedProfissao, getPersistedProfissao(partialUpdatedProfissao));
    }

    @Test
    @Transactional
    void patchNonExistingProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profissao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(profissao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(profissao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfissao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profissao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfissaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(profissao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profissao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfissao() throws Exception {
        // Initialize the database
        insertedProfissao = profissaoRepository.saveAndFlush(profissao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the profissao
        restProfissaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, profissao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return profissaoRepository.count();
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

    protected Profissao getPersistedProfissao(Profissao profissao) {
        return profissaoRepository.findById(profissao.getId()).orElseThrow();
    }

    protected void assertPersistedProfissaoToMatchAllProperties(Profissao expectedProfissao) {
        assertProfissaoAllPropertiesEquals(expectedProfissao, getPersistedProfissao(expectedProfissao));
    }

    protected void assertPersistedProfissaoToMatchUpdatableProperties(Profissao expectedProfissao) {
        assertProfissaoAllUpdatablePropertiesEquals(expectedProfissao, getPersistedProfissao(expectedProfissao));
    }
}
