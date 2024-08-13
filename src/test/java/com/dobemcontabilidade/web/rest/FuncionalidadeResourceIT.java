package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FuncionalidadeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Funcionalidade;
import com.dobemcontabilidade.domain.Modulo;
import com.dobemcontabilidade.repository.FuncionalidadeRepository;
import com.dobemcontabilidade.service.FuncionalidadeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FuncionalidadeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FuncionalidadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVA = false;
    private static final Boolean UPDATED_ATIVA = true;

    private static final String ENTITY_API_URL = "/api/funcionalidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuncionalidadeRepository funcionalidadeRepository;

    @Mock
    private FuncionalidadeRepository funcionalidadeRepositoryMock;

    @Mock
    private FuncionalidadeService funcionalidadeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionalidadeMockMvc;

    private Funcionalidade funcionalidade;

    private Funcionalidade insertedFuncionalidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionalidade createEntity(EntityManager em) {
        Funcionalidade funcionalidade = new Funcionalidade().nome(DEFAULT_NOME).ativa(DEFAULT_ATIVA);
        // Add required entity
        Modulo modulo;
        if (TestUtil.findAll(em, Modulo.class).isEmpty()) {
            modulo = ModuloResourceIT.createEntity(em);
            em.persist(modulo);
            em.flush();
        } else {
            modulo = TestUtil.findAll(em, Modulo.class).get(0);
        }
        funcionalidade.setModulo(modulo);
        return funcionalidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionalidade createUpdatedEntity(EntityManager em) {
        Funcionalidade funcionalidade = new Funcionalidade().nome(UPDATED_NOME).ativa(UPDATED_ATIVA);
        // Add required entity
        Modulo modulo;
        if (TestUtil.findAll(em, Modulo.class).isEmpty()) {
            modulo = ModuloResourceIT.createUpdatedEntity(em);
            em.persist(modulo);
            em.flush();
        } else {
            modulo = TestUtil.findAll(em, Modulo.class).get(0);
        }
        funcionalidade.setModulo(modulo);
        return funcionalidade;
    }

    @BeforeEach
    public void initTest() {
        funcionalidade = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFuncionalidade != null) {
            funcionalidadeRepository.delete(insertedFuncionalidade);
            insertedFuncionalidade = null;
        }
    }

    @Test
    @Transactional
    void createFuncionalidade() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Funcionalidade
        var returnedFuncionalidade = om.readValue(
            restFuncionalidadeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionalidade)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Funcionalidade.class
        );

        // Validate the Funcionalidade in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuncionalidadeUpdatableFieldsEquals(returnedFuncionalidade, getPersistedFuncionalidade(returnedFuncionalidade));

        insertedFuncionalidade = returnedFuncionalidade;
    }

    @Test
    @Transactional
    void createFuncionalidadeWithExistingId() throws Exception {
        // Create the Funcionalidade with an existing ID
        funcionalidade.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionalidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionalidade)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuncionalidades() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList
        restFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionalidadesWithEagerRelationshipsIsEnabled() throws Exception {
        when(funcionalidadeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionalidadeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(funcionalidadeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionalidadesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(funcionalidadeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionalidadeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(funcionalidadeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFuncionalidade() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get the funcionalidade
        restFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionalidade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.ativa").value(DEFAULT_ATIVA.booleanValue()));
    }

    @Test
    @Transactional
    void getFuncionalidadesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        Long id = funcionalidade.getId();

        defaultFuncionalidadeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFuncionalidadeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFuncionalidadeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList where nome equals to
        defaultFuncionalidadeFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList where nome in
        defaultFuncionalidadeFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList where nome is not null
        defaultFuncionalidadeFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList where nome contains
        defaultFuncionalidadeFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList where nome does not contain
        defaultFuncionalidadeFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByAtivaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList where ativa equals to
        defaultFuncionalidadeFiltering("ativa.equals=" + DEFAULT_ATIVA, "ativa.equals=" + UPDATED_ATIVA);
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByAtivaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList where ativa in
        defaultFuncionalidadeFiltering("ativa.in=" + DEFAULT_ATIVA + "," + UPDATED_ATIVA, "ativa.in=" + UPDATED_ATIVA);
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByAtivaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList where ativa is not null
        defaultFuncionalidadeFiltering("ativa.specified=true", "ativa.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionalidadesByModuloIsEqualToSomething() throws Exception {
        Modulo modulo;
        if (TestUtil.findAll(em, Modulo.class).isEmpty()) {
            funcionalidadeRepository.saveAndFlush(funcionalidade);
            modulo = ModuloResourceIT.createEntity(em);
        } else {
            modulo = TestUtil.findAll(em, Modulo.class).get(0);
        }
        em.persist(modulo);
        em.flush();
        funcionalidade.setModulo(modulo);
        funcionalidadeRepository.saveAndFlush(funcionalidade);
        Long moduloId = modulo.getId();
        // Get all the funcionalidadeList where modulo equals to moduloId
        defaultFuncionalidadeShouldBeFound("moduloId.equals=" + moduloId);

        // Get all the funcionalidadeList where modulo equals to (moduloId + 1)
        defaultFuncionalidadeShouldNotBeFound("moduloId.equals=" + (moduloId + 1));
    }

    private void defaultFuncionalidadeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFuncionalidadeShouldBeFound(shouldBeFound);
        defaultFuncionalidadeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuncionalidadeShouldBeFound(String filter) throws Exception {
        restFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA.booleanValue())));

        // Check, that the count call also returns 1
        restFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuncionalidadeShouldNotBeFound(String filter) throws Exception {
        restFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuncionalidade() throws Exception {
        // Get the funcionalidade
        restFuncionalidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuncionalidade() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidade
        Funcionalidade updatedFuncionalidade = funcionalidadeRepository.findById(funcionalidade.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuncionalidade are not directly saved in db
        em.detach(updatedFuncionalidade);
        updatedFuncionalidade.nome(UPDATED_NOME).ativa(UPDATED_ATIVA);

        restFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuncionalidade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the Funcionalidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuncionalidadeToMatchAllProperties(updatedFuncionalidade);
    }

    @Test
    @Transactional
    void putNonExistingFuncionalidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidade.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionalidade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionalidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionalidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionalidade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionalidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionalidadeWithPatch() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidade using partial update
        Funcionalidade partialUpdatedFuncionalidade = new Funcionalidade();
        partialUpdatedFuncionalidade.setId(funcionalidade.getId());

        partialUpdatedFuncionalidade.nome(UPDATED_NOME);

        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the Funcionalidade in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuncionalidadeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuncionalidade, funcionalidade),
            getPersistedFuncionalidade(funcionalidade)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuncionalidadeWithPatch() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionalidade using partial update
        Funcionalidade partialUpdatedFuncionalidade = new Funcionalidade();
        partialUpdatedFuncionalidade.setId(funcionalidade.getId());

        partialUpdatedFuncionalidade.nome(UPDATED_NOME).ativa(UPDATED_ATIVA);

        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the Funcionalidade in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuncionalidadeUpdatableFieldsEquals(partialUpdatedFuncionalidade, getPersistedFuncionalidade(partialUpdatedFuncionalidade));
    }

    @Test
    @Transactional
    void patchNonExistingFuncionalidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidade.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionalidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionalidade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionalidade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(funcionalidade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionalidade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionalidade() throws Exception {
        // Initialize the database
        insertedFuncionalidade = funcionalidadeRepository.saveAndFlush(funcionalidade);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the funcionalidade
        restFuncionalidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionalidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return funcionalidadeRepository.count();
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

    protected Funcionalidade getPersistedFuncionalidade(Funcionalidade funcionalidade) {
        return funcionalidadeRepository.findById(funcionalidade.getId()).orElseThrow();
    }

    protected void assertPersistedFuncionalidadeToMatchAllProperties(Funcionalidade expectedFuncionalidade) {
        assertFuncionalidadeAllPropertiesEquals(expectedFuncionalidade, getPersistedFuncionalidade(expectedFuncionalidade));
    }

    protected void assertPersistedFuncionalidadeToMatchUpdatableProperties(Funcionalidade expectedFuncionalidade) {
        assertFuncionalidadeAllUpdatablePropertiesEquals(expectedFuncionalidade, getPersistedFuncionalidade(expectedFuncionalidade));
    }
}
