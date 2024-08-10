package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.repository.AnexoEmpresaRepository;
import com.dobemcontabilidade.service.AnexoEmpresaService;
import com.dobemcontabilidade.service.dto.AnexoEmpresaDTO;
import com.dobemcontabilidade.service.mapper.AnexoEmpresaMapper;
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
 * Integration tests for the {@link AnexoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoEmpresaResourceIT {

    private static final String DEFAULT_URL_ANEXO = "AAAAAAAAAA";
    private static final String UPDATED_URL_ANEXO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/anexo-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoEmpresaRepository anexoEmpresaRepository;

    @Mock
    private AnexoEmpresaRepository anexoEmpresaRepositoryMock;

    @Autowired
    private AnexoEmpresaMapper anexoEmpresaMapper;

    @Mock
    private AnexoEmpresaService anexoEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoEmpresaMockMvc;

    private AnexoEmpresa anexoEmpresa;

    private AnexoEmpresa insertedAnexoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoEmpresa createEntity(EntityManager em) {
        AnexoEmpresa anexoEmpresa = new AnexoEmpresa().urlAnexo(DEFAULT_URL_ANEXO).tipo(DEFAULT_TIPO).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        anexoEmpresa.setEmpresa(empresa);
        return anexoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoEmpresa createUpdatedEntity(EntityManager em) {
        AnexoEmpresa anexoEmpresa = new AnexoEmpresa().urlAnexo(UPDATED_URL_ANEXO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        anexoEmpresa.setEmpresa(empresa);
        return anexoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        anexoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoEmpresa != null) {
            anexoEmpresaRepository.delete(insertedAnexoEmpresa);
            insertedAnexoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createAnexoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoEmpresa
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);
        var returnedAnexoEmpresaDTO = om.readValue(
            restAnexoEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoEmpresaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoEmpresaDTO.class
        );

        // Validate the AnexoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAnexoEmpresa = anexoEmpresaMapper.toEntity(returnedAnexoEmpresaDTO);
        assertAnexoEmpresaUpdatableFieldsEquals(returnedAnexoEmpresa, getPersistedAnexoEmpresa(returnedAnexoEmpresa));

        insertedAnexoEmpresa = returnedAnexoEmpresa;
    }

    @Test
    @Transactional
    void createAnexoEmpresaWithExistingId() throws Exception {
        // Create the AnexoEmpresa with an existing ID
        anexoEmpresa.setId(1L);
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnexoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        anexoEmpresa.setTipo(null);

        // Create the AnexoEmpresa, which fails.
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);

        restAnexoEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnexoEmpresas() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        // Get all the anexoEmpresaList
        restAnexoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlAnexo").value(hasItem(DEFAULT_URL_ANEXO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        // Get the anexoEmpresa
        restAnexoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.urlAnexo").value(DEFAULT_URL_ANEXO.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getAnexoEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        Long id = anexoEmpresa.getId();

        defaultAnexoEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAnexoEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAnexoEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAnexoEmpresasByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        // Get all the anexoEmpresaList where tipo equals to
        defaultAnexoEmpresaFiltering("tipo.equals=" + DEFAULT_TIPO, "tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoEmpresasByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        // Get all the anexoEmpresaList where tipo in
        defaultAnexoEmpresaFiltering("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO, "tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoEmpresasByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        // Get all the anexoEmpresaList where tipo is not null
        defaultAnexoEmpresaFiltering("tipo.specified=true", "tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllAnexoEmpresasByTipoContainsSomething() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        // Get all the anexoEmpresaList where tipo contains
        defaultAnexoEmpresaFiltering("tipo.contains=" + DEFAULT_TIPO, "tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoEmpresasByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        // Get all the anexoEmpresaList where tipo does not contain
        defaultAnexoEmpresaFiltering("tipo.doesNotContain=" + UPDATED_TIPO, "tipo.doesNotContain=" + DEFAULT_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoEmpresasByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            anexoEmpresaRepository.saveAndFlush(anexoEmpresa);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        anexoEmpresa.setEmpresa(empresa);
        anexoEmpresaRepository.saveAndFlush(anexoEmpresa);
        Long empresaId = empresa.getId();
        // Get all the anexoEmpresaList where empresa equals to empresaId
        defaultAnexoEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the anexoEmpresaList where empresa equals to (empresaId + 1)
        defaultAnexoEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultAnexoEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAnexoEmpresaShouldBeFound(shouldBeFound);
        defaultAnexoEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnexoEmpresaShouldBeFound(String filter) throws Exception {
        restAnexoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlAnexo").value(hasItem(DEFAULT_URL_ANEXO.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restAnexoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnexoEmpresaShouldNotBeFound(String filter) throws Exception {
        restAnexoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnexoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAnexoEmpresa() throws Exception {
        // Get the anexoEmpresa
        restAnexoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoEmpresa
        AnexoEmpresa updatedAnexoEmpresa = anexoEmpresaRepository.findById(anexoEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoEmpresa are not directly saved in db
        em.detach(updatedAnexoEmpresa);
        updatedAnexoEmpresa.urlAnexo(UPDATED_URL_ANEXO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(updatedAnexoEmpresa);

        restAnexoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoEmpresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnexoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoEmpresaToMatchAllProperties(updatedAnexoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingAnexoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoEmpresa.setId(longCount.incrementAndGet());

        // Create the AnexoEmpresa
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoEmpresa.setId(longCount.incrementAndGet());

        // Create the AnexoEmpresa
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoEmpresa.setId(longCount.incrementAndGet());

        // Create the AnexoEmpresa
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoEmpresa using partial update
        AnexoEmpresa partialUpdatedAnexoEmpresa = new AnexoEmpresa();
        partialUpdatedAnexoEmpresa.setId(anexoEmpresa.getId());

        partialUpdatedAnexoEmpresa.urlAnexo(UPDATED_URL_ANEXO);

        restAnexoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoEmpresa, anexoEmpresa),
            getPersistedAnexoEmpresa(anexoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoEmpresa using partial update
        AnexoEmpresa partialUpdatedAnexoEmpresa = new AnexoEmpresa();
        partialUpdatedAnexoEmpresa.setId(anexoEmpresa.getId());

        partialUpdatedAnexoEmpresa.urlAnexo(UPDATED_URL_ANEXO).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

        restAnexoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AnexoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoEmpresaUpdatableFieldsEquals(partialUpdatedAnexoEmpresa, getPersistedAnexoEmpresa(partialUpdatedAnexoEmpresa));
    }

    @Test
    @Transactional
    void patchNonExistingAnexoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoEmpresa.setId(longCount.incrementAndGet());

        // Create the AnexoEmpresa
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoEmpresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoEmpresa.setId(longCount.incrementAndGet());

        // Create the AnexoEmpresa
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoEmpresa.setId(longCount.incrementAndGet());

        // Create the AnexoEmpresa
        AnexoEmpresaDTO anexoEmpresaDTO = anexoEmpresaMapper.toDto(anexoEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anexoEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoEmpresa() throws Exception {
        // Initialize the database
        insertedAnexoEmpresa = anexoEmpresaRepository.saveAndFlush(anexoEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoEmpresa
        restAnexoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoEmpresaRepository.count();
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

    protected AnexoEmpresa getPersistedAnexoEmpresa(AnexoEmpresa anexoEmpresa) {
        return anexoEmpresaRepository.findById(anexoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoEmpresaToMatchAllProperties(AnexoEmpresa expectedAnexoEmpresa) {
        assertAnexoEmpresaAllPropertiesEquals(expectedAnexoEmpresa, getPersistedAnexoEmpresa(expectedAnexoEmpresa));
    }

    protected void assertPersistedAnexoEmpresaToMatchUpdatableProperties(AnexoEmpresa expectedAnexoEmpresa) {
        assertAnexoEmpresaAllUpdatablePropertiesEquals(expectedAnexoEmpresa, getPersistedAnexoEmpresa(expectedAnexoEmpresa));
    }
}
