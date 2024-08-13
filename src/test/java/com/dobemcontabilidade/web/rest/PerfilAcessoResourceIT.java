package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PerfilAcessoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PerfilAcesso;
import com.dobemcontabilidade.repository.PerfilAcessoRepository;
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
 * Integration tests for the {@link PerfilAcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerfilAcessoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/perfil-acessos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PerfilAcessoRepository perfilAcessoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfilAcessoMockMvc;

    private PerfilAcesso perfilAcesso;

    private PerfilAcesso insertedPerfilAcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilAcesso createEntity(EntityManager em) {
        PerfilAcesso perfilAcesso = new PerfilAcesso().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return perfilAcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilAcesso createUpdatedEntity(EntityManager em) {
        PerfilAcesso perfilAcesso = new PerfilAcesso().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return perfilAcesso;
    }

    @BeforeEach
    public void initTest() {
        perfilAcesso = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPerfilAcesso != null) {
            perfilAcessoRepository.delete(insertedPerfilAcesso);
            insertedPerfilAcesso = null;
        }
    }

    @Test
    @Transactional
    void createPerfilAcesso() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PerfilAcesso
        var returnedPerfilAcesso = om.readValue(
            restPerfilAcessoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilAcesso)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PerfilAcesso.class
        );

        // Validate the PerfilAcesso in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPerfilAcessoUpdatableFieldsEquals(returnedPerfilAcesso, getPersistedPerfilAcesso(returnedPerfilAcesso));

        insertedPerfilAcesso = returnedPerfilAcesso;
    }

    @Test
    @Transactional
    void createPerfilAcessoWithExistingId() throws Exception {
        // Create the PerfilAcesso with an existing ID
        perfilAcesso.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilAcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilAcesso)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcesso in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerfilAcessos() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList
        restPerfilAcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilAcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getPerfilAcesso() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get the perfilAcesso
        restPerfilAcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, perfilAcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfilAcesso.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getPerfilAcessosByIdFiltering() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        Long id = perfilAcesso.getId();

        defaultPerfilAcessoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPerfilAcessoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPerfilAcessoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where nome equals to
        defaultPerfilAcessoFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where nome in
        defaultPerfilAcessoFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where nome is not null
        defaultPerfilAcessoFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where nome contains
        defaultPerfilAcessoFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where nome does not contain
        defaultPerfilAcessoFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where descricao equals to
        defaultPerfilAcessoFiltering("descricao.equals=" + DEFAULT_DESCRICAO, "descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where descricao in
        defaultPerfilAcessoFiltering("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO, "descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where descricao is not null
        defaultPerfilAcessoFiltering("descricao.specified=true", "descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where descricao contains
        defaultPerfilAcessoFiltering("descricao.contains=" + DEFAULT_DESCRICAO, "descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPerfilAcessosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        // Get all the perfilAcessoList where descricao does not contain
        defaultPerfilAcessoFiltering("descricao.doesNotContain=" + UPDATED_DESCRICAO, "descricao.doesNotContain=" + DEFAULT_DESCRICAO);
    }

    private void defaultPerfilAcessoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPerfilAcessoShouldBeFound(shouldBeFound);
        defaultPerfilAcessoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerfilAcessoShouldBeFound(String filter) throws Exception {
        restPerfilAcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilAcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restPerfilAcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerfilAcessoShouldNotBeFound(String filter) throws Exception {
        restPerfilAcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerfilAcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerfilAcesso() throws Exception {
        // Get the perfilAcesso
        restPerfilAcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerfilAcesso() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilAcesso
        PerfilAcesso updatedPerfilAcesso = perfilAcessoRepository.findById(perfilAcesso.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPerfilAcesso are not directly saved in db
        em.detach(updatedPerfilAcesso);
        updatedPerfilAcesso.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restPerfilAcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerfilAcesso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPerfilAcesso))
            )
            .andExpect(status().isOk());

        // Validate the PerfilAcesso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPerfilAcessoToMatchAllProperties(updatedPerfilAcesso);
    }

    @Test
    @Transactional
    void putNonExistingPerfilAcesso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcesso.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilAcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, perfilAcesso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcesso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerfilAcesso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcesso.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilAcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcesso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerfilAcesso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcesso.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilAcessoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilAcesso)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilAcesso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerfilAcessoWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilAcesso using partial update
        PerfilAcesso partialUpdatedPerfilAcesso = new PerfilAcesso();
        partialUpdatedPerfilAcesso.setId(perfilAcesso.getId());

        partialUpdatedPerfilAcesso.nome(UPDATED_NOME);

        restPerfilAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilAcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilAcesso))
            )
            .andExpect(status().isOk());

        // Validate the PerfilAcesso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilAcessoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPerfilAcesso, perfilAcesso),
            getPersistedPerfilAcesso(perfilAcesso)
        );
    }

    @Test
    @Transactional
    void fullUpdatePerfilAcessoWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilAcesso using partial update
        PerfilAcesso partialUpdatedPerfilAcesso = new PerfilAcesso();
        partialUpdatedPerfilAcesso.setId(perfilAcesso.getId());

        partialUpdatedPerfilAcesso.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restPerfilAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilAcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilAcesso))
            )
            .andExpect(status().isOk());

        // Validate the PerfilAcesso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilAcessoUpdatableFieldsEquals(partialUpdatedPerfilAcesso, getPersistedPerfilAcesso(partialUpdatedPerfilAcesso));
    }

    @Test
    @Transactional
    void patchNonExistingPerfilAcesso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcesso.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, perfilAcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcesso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerfilAcesso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcesso.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilAcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilAcesso))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcesso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerfilAcesso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcesso.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilAcessoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(perfilAcesso)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilAcesso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerfilAcesso() throws Exception {
        // Initialize the database
        insertedPerfilAcesso = perfilAcessoRepository.saveAndFlush(perfilAcesso);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the perfilAcesso
        restPerfilAcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, perfilAcesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return perfilAcessoRepository.count();
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

    protected PerfilAcesso getPersistedPerfilAcesso(PerfilAcesso perfilAcesso) {
        return perfilAcessoRepository.findById(perfilAcesso.getId()).orElseThrow();
    }

    protected void assertPersistedPerfilAcessoToMatchAllProperties(PerfilAcesso expectedPerfilAcesso) {
        assertPerfilAcessoAllPropertiesEquals(expectedPerfilAcesso, getPersistedPerfilAcesso(expectedPerfilAcesso));
    }

    protected void assertPersistedPerfilAcessoToMatchUpdatableProperties(PerfilAcesso expectedPerfilAcesso) {
        assertPerfilAcessoAllUpdatablePropertiesEquals(expectedPerfilAcesso, getPersistedPerfilAcesso(expectedPerfilAcesso));
    }
}
