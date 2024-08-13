package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DepartamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.repository.DepartamentoRepository;
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
 * Integration tests for the {@link DepartamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepartamentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/departamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartamentoMockMvc;

    private Departamento departamento;

    private Departamento insertedDepartamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamento createEntity(EntityManager em) {
        Departamento departamento = new Departamento().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return departamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamento createUpdatedEntity(EntityManager em) {
        Departamento departamento = new Departamento().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return departamento;
    }

    @BeforeEach
    public void initTest() {
        departamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDepartamento != null) {
            departamentoRepository.delete(insertedDepartamento);
            insertedDepartamento = null;
        }
    }

    @Test
    @Transactional
    void createDepartamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Departamento
        var returnedDepartamento = om.readValue(
            restDepartamentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamento)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Departamento.class
        );

        // Validate the Departamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDepartamentoUpdatableFieldsEquals(returnedDepartamento, getPersistedDepartamento(returnedDepartamento));

        insertedDepartamento = returnedDepartamento;
    }

    @Test
    @Transactional
    void createDepartamentoWithExistingId() throws Exception {
        // Create the Departamento with an existing ID
        departamento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamento)))
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepartamentos() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList
        restDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getDepartamento() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get the departamento
        restDepartamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, departamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getDepartamentosByIdFiltering() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        Long id = departamento.getId();

        defaultDepartamentoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDepartamentoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDepartamentoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDepartamentosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList where nome equals to
        defaultDepartamentoFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDepartamentosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList where nome in
        defaultDepartamentoFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDepartamentosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList where nome is not null
        defaultDepartamentoFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList where nome contains
        defaultDepartamentoFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDepartamentosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList where nome does not contain
        defaultDepartamentoFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    private void defaultDepartamentoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDepartamentoShouldBeFound(shouldBeFound);
        defaultDepartamentoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartamentoShouldBeFound(String filter) throws Exception {
        restDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartamentoShouldNotBeFound(String filter) throws Exception {
        restDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDepartamento() throws Exception {
        // Get the departamento
        restDepartamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepartamento() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamento
        Departamento updatedDepartamento = departamentoRepository.findById(departamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDepartamento are not directly saved in db
        em.detach(updatedDepartamento);
        updatedDepartamento.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDepartamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepartamentoToMatchAllProperties(updatedDepartamento);
    }

    @Test
    @Transactional
    void putNonExistingDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartamentoWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamento using partial update
        Departamento partialUpdatedDepartamento = new Departamento();
        partialUpdatedDepartamento.setId(departamento.getId());

        partialUpdatedDepartamento.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the Departamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDepartamento, departamento),
            getPersistedDepartamento(departamento)
        );
    }

    @Test
    @Transactional
    void fullUpdateDepartamentoWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamento using partial update
        Departamento partialUpdatedDepartamento = new Departamento();
        partialUpdatedDepartamento.setId(departamento.getId());

        partialUpdatedDepartamento.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the Departamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoUpdatableFieldsEquals(partialUpdatedDepartamento, getPersistedDepartamento(partialUpdatedDepartamento));
    }

    @Test
    @Transactional
    void patchNonExistingDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(departamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartamento() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the departamento
        restDepartamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, departamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return departamentoRepository.count();
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

    protected Departamento getPersistedDepartamento(Departamento departamento) {
        return departamentoRepository.findById(departamento.getId()).orElseThrow();
    }

    protected void assertPersistedDepartamentoToMatchAllProperties(Departamento expectedDepartamento) {
        assertDepartamentoAllPropertiesEquals(expectedDepartamento, getPersistedDepartamento(expectedDepartamento));
    }

    protected void assertPersistedDepartamentoToMatchUpdatableProperties(Departamento expectedDepartamento) {
        assertDepartamentoAllUpdatablePropertiesEquals(expectedDepartamento, getPersistedDepartamento(expectedDepartamento));
    }
}
