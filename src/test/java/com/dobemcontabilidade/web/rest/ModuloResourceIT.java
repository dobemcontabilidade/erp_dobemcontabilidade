package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ModuloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Modulo;
import com.dobemcontabilidade.domain.Sistema;
import com.dobemcontabilidade.repository.ModuloRepository;
import com.dobemcontabilidade.service.ModuloService;
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
 * Integration tests for the {@link ModuloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ModuloResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/modulos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ModuloRepository moduloRepository;

    @Mock
    private ModuloRepository moduloRepositoryMock;

    @Mock
    private ModuloService moduloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModuloMockMvc;

    private Modulo modulo;

    private Modulo insertedModulo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modulo createEntity(EntityManager em) {
        Modulo modulo = new Modulo().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Sistema sistema;
        if (TestUtil.findAll(em, Sistema.class).isEmpty()) {
            sistema = SistemaResourceIT.createEntity(em);
            em.persist(sistema);
            em.flush();
        } else {
            sistema = TestUtil.findAll(em, Sistema.class).get(0);
        }
        modulo.setSistema(sistema);
        return modulo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modulo createUpdatedEntity(EntityManager em) {
        Modulo modulo = new Modulo().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        // Add required entity
        Sistema sistema;
        if (TestUtil.findAll(em, Sistema.class).isEmpty()) {
            sistema = SistemaResourceIT.createUpdatedEntity(em);
            em.persist(sistema);
            em.flush();
        } else {
            sistema = TestUtil.findAll(em, Sistema.class).get(0);
        }
        modulo.setSistema(sistema);
        return modulo;
    }

    @BeforeEach
    public void initTest() {
        modulo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedModulo != null) {
            moduloRepository.delete(insertedModulo);
            insertedModulo = null;
        }
    }

    @Test
    @Transactional
    void createModulo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Modulo
        var returnedModulo = om.readValue(
            restModuloMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modulo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Modulo.class
        );

        // Validate the Modulo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertModuloUpdatableFieldsEquals(returnedModulo, getPersistedModulo(returnedModulo));

        insertedModulo = returnedModulo;
    }

    @Test
    @Transactional
    void createModuloWithExistingId() throws Exception {
        // Create the Modulo with an existing ID
        modulo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modulo)))
            .andExpect(status().isBadRequest());

        // Validate the Modulo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModulos() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList
        restModuloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllModulosWithEagerRelationshipsIsEnabled() throws Exception {
        when(moduloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restModuloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(moduloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllModulosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(moduloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restModuloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(moduloRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getModulo() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get the modulo
        restModuloMockMvc
            .perform(get(ENTITY_API_URL_ID, modulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modulo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getModulosByIdFiltering() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        Long id = modulo.getId();

        defaultModuloFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultModuloFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultModuloFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllModulosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where nome equals to
        defaultModuloFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllModulosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where nome in
        defaultModuloFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllModulosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where nome is not null
        defaultModuloFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllModulosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where nome contains
        defaultModuloFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllModulosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where nome does not contain
        defaultModuloFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllModulosByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where descricao equals to
        defaultModuloFiltering("descricao.equals=" + DEFAULT_DESCRICAO, "descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllModulosByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where descricao in
        defaultModuloFiltering("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO, "descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllModulosByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where descricao is not null
        defaultModuloFiltering("descricao.specified=true", "descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllModulosByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where descricao contains
        defaultModuloFiltering("descricao.contains=" + DEFAULT_DESCRICAO, "descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllModulosByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        // Get all the moduloList where descricao does not contain
        defaultModuloFiltering("descricao.doesNotContain=" + UPDATED_DESCRICAO, "descricao.doesNotContain=" + DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllModulosBySistemaIsEqualToSomething() throws Exception {
        Sistema sistema;
        if (TestUtil.findAll(em, Sistema.class).isEmpty()) {
            moduloRepository.saveAndFlush(modulo);
            sistema = SistemaResourceIT.createEntity(em);
        } else {
            sistema = TestUtil.findAll(em, Sistema.class).get(0);
        }
        em.persist(sistema);
        em.flush();
        modulo.setSistema(sistema);
        moduloRepository.saveAndFlush(modulo);
        Long sistemaId = sistema.getId();
        // Get all the moduloList where sistema equals to sistemaId
        defaultModuloShouldBeFound("sistemaId.equals=" + sistemaId);

        // Get all the moduloList where sistema equals to (sistemaId + 1)
        defaultModuloShouldNotBeFound("sistemaId.equals=" + (sistemaId + 1));
    }

    private void defaultModuloFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultModuloShouldBeFound(shouldBeFound);
        defaultModuloShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultModuloShouldBeFound(String filter) throws Exception {
        restModuloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restModuloMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultModuloShouldNotBeFound(String filter) throws Exception {
        restModuloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restModuloMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingModulo() throws Exception {
        // Get the modulo
        restModuloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModulo() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modulo
        Modulo updatedModulo = moduloRepository.findById(modulo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedModulo are not directly saved in db
        em.detach(updatedModulo);
        updatedModulo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restModuloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModulo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedModulo))
            )
            .andExpect(status().isOk());

        // Validate the Modulo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedModuloToMatchAllProperties(updatedModulo);
    }

    @Test
    @Transactional
    void putNonExistingModulo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modulo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuloMockMvc
            .perform(put(ENTITY_API_URL_ID, modulo.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modulo)))
            .andExpect(status().isBadRequest());

        // Validate the Modulo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModulo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modulo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(modulo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modulo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModulo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modulo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(modulo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Modulo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModuloWithPatch() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modulo using partial update
        Modulo partialUpdatedModulo = new Modulo();
        partialUpdatedModulo.setId(modulo.getId());

        restModuloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModulo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModulo))
            )
            .andExpect(status().isOk());

        // Validate the Modulo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModuloUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedModulo, modulo), getPersistedModulo(modulo));
    }

    @Test
    @Transactional
    void fullUpdateModuloWithPatch() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the modulo using partial update
        Modulo partialUpdatedModulo = new Modulo();
        partialUpdatedModulo.setId(modulo.getId());

        partialUpdatedModulo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restModuloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModulo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedModulo))
            )
            .andExpect(status().isOk());

        // Validate the Modulo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertModuloUpdatableFieldsEquals(partialUpdatedModulo, getPersistedModulo(partialUpdatedModulo));
    }

    @Test
    @Transactional
    void patchNonExistingModulo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modulo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modulo.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(modulo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modulo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModulo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modulo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(modulo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modulo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModulo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        modulo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModuloMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(modulo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Modulo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModulo() throws Exception {
        // Initialize the database
        insertedModulo = moduloRepository.saveAndFlush(modulo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the modulo
        restModuloMockMvc
            .perform(delete(ENTITY_API_URL_ID, modulo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return moduloRepository.count();
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

    protected Modulo getPersistedModulo(Modulo modulo) {
        return moduloRepository.findById(modulo.getId()).orElseThrow();
    }

    protected void assertPersistedModuloToMatchAllProperties(Modulo expectedModulo) {
        assertModuloAllPropertiesEquals(expectedModulo, getPersistedModulo(expectedModulo));
    }

    protected void assertPersistedModuloToMatchUpdatableProperties(Modulo expectedModulo) {
        assertModuloAllUpdatablePropertiesEquals(expectedModulo, getPersistedModulo(expectedModulo));
    }
}
