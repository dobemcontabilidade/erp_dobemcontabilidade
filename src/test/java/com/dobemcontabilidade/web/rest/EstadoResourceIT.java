package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EstadoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Estado;
import com.dobemcontabilidade.domain.Pais;
import com.dobemcontabilidade.repository.EstadoRepository;
import com.dobemcontabilidade.service.EstadoService;
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
 * Integration tests for the {@link EstadoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EstadoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NATURALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NATURALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EstadoRepository estadoRepository;

    @Mock
    private EstadoRepository estadoRepositoryMock;

    @Mock
    private EstadoService estadoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoMockMvc;

    private Estado estado;

    private Estado insertedEstado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estado createEntity(EntityManager em) {
        Estado estado = new Estado().nome(DEFAULT_NOME).naturalidade(DEFAULT_NATURALIDADE).sigla(DEFAULT_SIGLA);
        // Add required entity
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        estado.setPais(pais);
        return estado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estado createUpdatedEntity(EntityManager em) {
        Estado estado = new Estado().nome(UPDATED_NOME).naturalidade(UPDATED_NATURALIDADE).sigla(UPDATED_SIGLA);
        // Add required entity
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            pais = PaisResourceIT.createUpdatedEntity(em);
            em.persist(pais);
            em.flush();
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        estado.setPais(pais);
        return estado;
    }

    @BeforeEach
    public void initTest() {
        estado = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEstado != null) {
            estadoRepository.delete(insertedEstado);
            insertedEstado = null;
        }
    }

    @Test
    @Transactional
    void createEstado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Estado
        var returnedEstado = om.readValue(
            restEstadoMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estado)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Estado.class
        );

        // Validate the Estado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEstadoUpdatableFieldsEquals(returnedEstado, getPersistedEstado(returnedEstado));

        insertedEstado = returnedEstado;
    }

    @Test
    @Transactional
    void createEstadoWithExistingId() throws Exception {
        // Create the Estado with an existing ID
        estado.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estado)))
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstados() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList
        restEstadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEstadosWithEagerRelationshipsIsEnabled() throws Exception {
        when(estadoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEstadoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(estadoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEstadosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(estadoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEstadoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(estadoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEstado() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get the estado
        restEstadoMockMvc
            .perform(get(ENTITY_API_URL_ID, estado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estado.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.naturalidade").value(DEFAULT_NATURALIDADE))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    void getEstadosByIdFiltering() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        Long id = estado.getId();

        defaultEstadoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEstadoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEstadoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEstadosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where nome equals to
        defaultEstadoFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstadosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where nome in
        defaultEstadoFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstadosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where nome is not null
        defaultEstadoFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where nome contains
        defaultEstadoFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEstadosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where nome does not contain
        defaultEstadoFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllEstadosByNaturalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where naturalidade equals to
        defaultEstadoFiltering("naturalidade.equals=" + DEFAULT_NATURALIDADE, "naturalidade.equals=" + UPDATED_NATURALIDADE);
    }

    @Test
    @Transactional
    void getAllEstadosByNaturalidadeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where naturalidade in
        defaultEstadoFiltering(
            "naturalidade.in=" + DEFAULT_NATURALIDADE + "," + UPDATED_NATURALIDADE,
            "naturalidade.in=" + UPDATED_NATURALIDADE
        );
    }

    @Test
    @Transactional
    void getAllEstadosByNaturalidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where naturalidade is not null
        defaultEstadoFiltering("naturalidade.specified=true", "naturalidade.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadosByNaturalidadeContainsSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where naturalidade contains
        defaultEstadoFiltering("naturalidade.contains=" + DEFAULT_NATURALIDADE, "naturalidade.contains=" + UPDATED_NATURALIDADE);
    }

    @Test
    @Transactional
    void getAllEstadosByNaturalidadeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where naturalidade does not contain
        defaultEstadoFiltering(
            "naturalidade.doesNotContain=" + UPDATED_NATURALIDADE,
            "naturalidade.doesNotContain=" + DEFAULT_NATURALIDADE
        );
    }

    @Test
    @Transactional
    void getAllEstadosBySiglaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where sigla equals to
        defaultEstadoFiltering("sigla.equals=" + DEFAULT_SIGLA, "sigla.equals=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllEstadosBySiglaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where sigla in
        defaultEstadoFiltering("sigla.in=" + DEFAULT_SIGLA + "," + UPDATED_SIGLA, "sigla.in=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllEstadosBySiglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where sigla is not null
        defaultEstadoFiltering("sigla.specified=true", "sigla.specified=false");
    }

    @Test
    @Transactional
    void getAllEstadosBySiglaContainsSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where sigla contains
        defaultEstadoFiltering("sigla.contains=" + DEFAULT_SIGLA, "sigla.contains=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllEstadosBySiglaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        // Get all the estadoList where sigla does not contain
        defaultEstadoFiltering("sigla.doesNotContain=" + UPDATED_SIGLA, "sigla.doesNotContain=" + DEFAULT_SIGLA);
    }

    @Test
    @Transactional
    void getAllEstadosByPaisIsEqualToSomething() throws Exception {
        Pais pais;
        if (TestUtil.findAll(em, Pais.class).isEmpty()) {
            estadoRepository.saveAndFlush(estado);
            pais = PaisResourceIT.createEntity(em);
        } else {
            pais = TestUtil.findAll(em, Pais.class).get(0);
        }
        em.persist(pais);
        em.flush();
        estado.setPais(pais);
        estadoRepository.saveAndFlush(estado);
        Long paisId = pais.getId();
        // Get all the estadoList where pais equals to paisId
        defaultEstadoShouldBeFound("paisId.equals=" + paisId);

        // Get all the estadoList where pais equals to (paisId + 1)
        defaultEstadoShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }

    private void defaultEstadoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEstadoShouldBeFound(shouldBeFound);
        defaultEstadoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEstadoShouldBeFound(String filter) throws Exception {
        restEstadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].naturalidade").value(hasItem(DEFAULT_NATURALIDADE)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));

        // Check, that the count call also returns 1
        restEstadoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEstadoShouldNotBeFound(String filter) throws Exception {
        restEstadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEstadoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEstado() throws Exception {
        // Get the estado
        restEstadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstado() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estado
        Estado updatedEstado = estadoRepository.findById(estado.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEstado are not directly saved in db
        em.detach(updatedEstado);
        updatedEstado.nome(UPDATED_NOME).naturalidade(UPDATED_NATURALIDADE).sigla(UPDATED_SIGLA);

        restEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstado.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEstado))
            )
            .andExpect(status().isOk());

        // Validate the Estado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEstadoToMatchAllProperties(updatedEstado);
    }

    @Test
    @Transactional
    void putNonExistingEstado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estado.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estado.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoWithPatch() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estado using partial update
        Estado partialUpdatedEstado = new Estado();
        partialUpdatedEstado.setId(estado.getId());

        partialUpdatedEstado.nome(UPDATED_NOME).naturalidade(UPDATED_NATURALIDADE).sigla(UPDATED_SIGLA);

        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstado.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstado))
            )
            .andExpect(status().isOk());

        // Validate the Estado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstadoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEstado, estado), getPersistedEstado(estado));
    }

    @Test
    @Transactional
    void fullUpdateEstadoWithPatch() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estado using partial update
        Estado partialUpdatedEstado = new Estado();
        partialUpdatedEstado.setId(estado.getId());

        partialUpdatedEstado.nome(UPDATED_NOME).naturalidade(UPDATED_NATURALIDADE).sigla(UPDATED_SIGLA);

        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstado.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstado))
            )
            .andExpect(status().isOk());

        // Validate the Estado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstadoUpdatableFieldsEquals(partialUpdatedEstado, getPersistedEstado(partialUpdatedEstado));
    }

    @Test
    @Transactional
    void patchNonExistingEstado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estado.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estado.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(estado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(estado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(estado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstado() throws Exception {
        // Initialize the database
        insertedEstado = estadoRepository.saveAndFlush(estado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the estado
        restEstadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estado.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return estadoRepository.count();
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

    protected Estado getPersistedEstado(Estado estado) {
        return estadoRepository.findById(estado.getId()).orElseThrow();
    }

    protected void assertPersistedEstadoToMatchAllProperties(Estado expectedEstado) {
        assertEstadoAllPropertiesEquals(expectedEstado, getPersistedEstado(expectedEstado));
    }

    protected void assertPersistedEstadoToMatchUpdatableProperties(Estado expectedEstado) {
        assertEstadoAllUpdatablePropertiesEquals(expectedEstado, getPersistedEstado(expectedEstado));
    }
}
