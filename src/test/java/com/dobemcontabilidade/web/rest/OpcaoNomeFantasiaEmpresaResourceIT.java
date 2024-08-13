package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa;
import com.dobemcontabilidade.repository.OpcaoNomeFantasiaEmpresaRepository;
import com.dobemcontabilidade.service.OpcaoNomeFantasiaEmpresaService;
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
 * Integration tests for the {@link OpcaoNomeFantasiaEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OpcaoNomeFantasiaEmpresaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;
    private static final Integer SMALLER_ORDEM = 1 - 1;

    private static final Boolean DEFAULT_SELECIONADO = false;
    private static final Boolean UPDATED_SELECIONADO = true;

    private static final String ENTITY_API_URL = "/api/opcao-nome-fantasia-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpcaoNomeFantasiaEmpresaRepository opcaoNomeFantasiaEmpresaRepository;

    @Mock
    private OpcaoNomeFantasiaEmpresaRepository opcaoNomeFantasiaEmpresaRepositoryMock;

    @Mock
    private OpcaoNomeFantasiaEmpresaService opcaoNomeFantasiaEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpcaoNomeFantasiaEmpresaMockMvc;

    private OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa;

    private OpcaoNomeFantasiaEmpresa insertedOpcaoNomeFantasiaEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpcaoNomeFantasiaEmpresa createEntity(EntityManager em) {
        OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa = new OpcaoNomeFantasiaEmpresa()
            .nome(DEFAULT_NOME)
            .ordem(DEFAULT_ORDEM)
            .selecionado(DEFAULT_SELECIONADO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        opcaoNomeFantasiaEmpresa.setEmpresa(empresa);
        return opcaoNomeFantasiaEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpcaoNomeFantasiaEmpresa createUpdatedEntity(EntityManager em) {
        OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa = new OpcaoNomeFantasiaEmpresa()
            .nome(UPDATED_NOME)
            .ordem(UPDATED_ORDEM)
            .selecionado(UPDATED_SELECIONADO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        opcaoNomeFantasiaEmpresa.setEmpresa(empresa);
        return opcaoNomeFantasiaEmpresa;
    }

    @BeforeEach
    public void initTest() {
        opcaoNomeFantasiaEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOpcaoNomeFantasiaEmpresa != null) {
            opcaoNomeFantasiaEmpresaRepository.delete(insertedOpcaoNomeFantasiaEmpresa);
            insertedOpcaoNomeFantasiaEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createOpcaoNomeFantasiaEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OpcaoNomeFantasiaEmpresa
        var returnedOpcaoNomeFantasiaEmpresa = om.readValue(
            restOpcaoNomeFantasiaEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OpcaoNomeFantasiaEmpresa.class
        );

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpcaoNomeFantasiaEmpresaUpdatableFieldsEquals(
            returnedOpcaoNomeFantasiaEmpresa,
            getPersistedOpcaoNomeFantasiaEmpresa(returnedOpcaoNomeFantasiaEmpresa)
        );

        insertedOpcaoNomeFantasiaEmpresa = returnedOpcaoNomeFantasiaEmpresa;
    }

    @Test
    @Transactional
    void createOpcaoNomeFantasiaEmpresaWithExistingId() throws Exception {
        // Create the OpcaoNomeFantasiaEmpresa with an existing ID
        opcaoNomeFantasiaEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        opcaoNomeFantasiaEmpresa.setNome(null);

        // Create the OpcaoNomeFantasiaEmpresa, which fails.

        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresas() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opcaoNomeFantasiaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].selecionado").value(hasItem(DEFAULT_SELECIONADO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOpcaoNomeFantasiaEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(opcaoNomeFantasiaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOpcaoNomeFantasiaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(opcaoNomeFantasiaEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOpcaoNomeFantasiaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(opcaoNomeFantasiaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOpcaoNomeFantasiaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(opcaoNomeFantasiaEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOpcaoNomeFantasiaEmpresa() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get the opcaoNomeFantasiaEmpresa
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, opcaoNomeFantasiaEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opcaoNomeFantasiaEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.selecionado").value(DEFAULT_SELECIONADO.booleanValue()));
    }

    @Test
    @Transactional
    void getOpcaoNomeFantasiaEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        Long id = opcaoNomeFantasiaEmpresa.getId();

        defaultOpcaoNomeFantasiaEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultOpcaoNomeFantasiaEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultOpcaoNomeFantasiaEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where nome equals to
        defaultOpcaoNomeFantasiaEmpresaFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where nome in
        defaultOpcaoNomeFantasiaEmpresaFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where nome is not null
        defaultOpcaoNomeFantasiaEmpresaFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where nome contains
        defaultOpcaoNomeFantasiaEmpresaFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where nome does not contain
        defaultOpcaoNomeFantasiaEmpresaFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByOrdemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where ordem equals to
        defaultOpcaoNomeFantasiaEmpresaFiltering("ordem.equals=" + DEFAULT_ORDEM, "ordem.equals=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByOrdemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where ordem in
        defaultOpcaoNomeFantasiaEmpresaFiltering("ordem.in=" + DEFAULT_ORDEM + "," + UPDATED_ORDEM, "ordem.in=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByOrdemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where ordem is not null
        defaultOpcaoNomeFantasiaEmpresaFiltering("ordem.specified=true", "ordem.specified=false");
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByOrdemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where ordem is greater than or equal to
        defaultOpcaoNomeFantasiaEmpresaFiltering("ordem.greaterThanOrEqual=" + DEFAULT_ORDEM, "ordem.greaterThanOrEqual=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByOrdemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where ordem is less than or equal to
        defaultOpcaoNomeFantasiaEmpresaFiltering("ordem.lessThanOrEqual=" + DEFAULT_ORDEM, "ordem.lessThanOrEqual=" + SMALLER_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByOrdemIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where ordem is less than
        defaultOpcaoNomeFantasiaEmpresaFiltering("ordem.lessThan=" + UPDATED_ORDEM, "ordem.lessThan=" + DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByOrdemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where ordem is greater than
        defaultOpcaoNomeFantasiaEmpresaFiltering("ordem.greaterThan=" + SMALLER_ORDEM, "ordem.greaterThan=" + DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasBySelecionadoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where selecionado equals to
        defaultOpcaoNomeFantasiaEmpresaFiltering("selecionado.equals=" + DEFAULT_SELECIONADO, "selecionado.equals=" + UPDATED_SELECIONADO);
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasBySelecionadoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where selecionado in
        defaultOpcaoNomeFantasiaEmpresaFiltering(
            "selecionado.in=" + DEFAULT_SELECIONADO + "," + UPDATED_SELECIONADO,
            "selecionado.in=" + UPDATED_SELECIONADO
        );
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasBySelecionadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        // Get all the opcaoNomeFantasiaEmpresaList where selecionado is not null
        defaultOpcaoNomeFantasiaEmpresaFiltering("selecionado.specified=true", "selecionado.specified=false");
    }

    @Test
    @Transactional
    void getAllOpcaoNomeFantasiaEmpresasByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        opcaoNomeFantasiaEmpresa.setEmpresa(empresa);
        opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);
        Long empresaId = empresa.getId();
        // Get all the opcaoNomeFantasiaEmpresaList where empresa equals to empresaId
        defaultOpcaoNomeFantasiaEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the opcaoNomeFantasiaEmpresaList where empresa equals to (empresaId + 1)
        defaultOpcaoNomeFantasiaEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultOpcaoNomeFantasiaEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultOpcaoNomeFantasiaEmpresaShouldBeFound(shouldBeFound);
        defaultOpcaoNomeFantasiaEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOpcaoNomeFantasiaEmpresaShouldBeFound(String filter) throws Exception {
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opcaoNomeFantasiaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].selecionado").value(hasItem(DEFAULT_SELECIONADO.booleanValue())));

        // Check, that the count call also returns 1
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOpcaoNomeFantasiaEmpresaShouldNotBeFound(String filter) throws Exception {
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOpcaoNomeFantasiaEmpresa() throws Exception {
        // Get the opcaoNomeFantasiaEmpresa
        restOpcaoNomeFantasiaEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpcaoNomeFantasiaEmpresa() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opcaoNomeFantasiaEmpresa
        OpcaoNomeFantasiaEmpresa updatedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository
            .findById(opcaoNomeFantasiaEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOpcaoNomeFantasiaEmpresa are not directly saved in db
        em.detach(updatedOpcaoNomeFantasiaEmpresa);
        updatedOpcaoNomeFantasiaEmpresa.nome(UPDATED_NOME).ordem(UPDATED_ORDEM).selecionado(UPDATED_SELECIONADO);

        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpcaoNomeFantasiaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOpcaoNomeFantasiaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpcaoNomeFantasiaEmpresaToMatchAllProperties(updatedOpcaoNomeFantasiaEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingOpcaoNomeFantasiaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoNomeFantasiaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opcaoNomeFantasiaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpcaoNomeFantasiaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoNomeFantasiaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpcaoNomeFantasiaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoNomeFantasiaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpcaoNomeFantasiaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opcaoNomeFantasiaEmpresa using partial update
        OpcaoNomeFantasiaEmpresa partialUpdatedOpcaoNomeFantasiaEmpresa = new OpcaoNomeFantasiaEmpresa();
        partialUpdatedOpcaoNomeFantasiaEmpresa.setId(opcaoNomeFantasiaEmpresa.getId());

        partialUpdatedOpcaoNomeFantasiaEmpresa.nome(UPDATED_NOME).ordem(UPDATED_ORDEM).selecionado(UPDATED_SELECIONADO);

        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpcaoNomeFantasiaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpcaoNomeFantasiaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoNomeFantasiaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpcaoNomeFantasiaEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOpcaoNomeFantasiaEmpresa, opcaoNomeFantasiaEmpresa),
            getPersistedOpcaoNomeFantasiaEmpresa(opcaoNomeFantasiaEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateOpcaoNomeFantasiaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opcaoNomeFantasiaEmpresa using partial update
        OpcaoNomeFantasiaEmpresa partialUpdatedOpcaoNomeFantasiaEmpresa = new OpcaoNomeFantasiaEmpresa();
        partialUpdatedOpcaoNomeFantasiaEmpresa.setId(opcaoNomeFantasiaEmpresa.getId());

        partialUpdatedOpcaoNomeFantasiaEmpresa.nome(UPDATED_NOME).ordem(UPDATED_ORDEM).selecionado(UPDATED_SELECIONADO);

        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpcaoNomeFantasiaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpcaoNomeFantasiaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoNomeFantasiaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpcaoNomeFantasiaEmpresaUpdatableFieldsEquals(
            partialUpdatedOpcaoNomeFantasiaEmpresa,
            getPersistedOpcaoNomeFantasiaEmpresa(partialUpdatedOpcaoNomeFantasiaEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOpcaoNomeFantasiaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoNomeFantasiaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opcaoNomeFantasiaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpcaoNomeFantasiaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoNomeFantasiaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpcaoNomeFantasiaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoNomeFantasiaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(opcaoNomeFantasiaEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpcaoNomeFantasiaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpcaoNomeFantasiaEmpresa() throws Exception {
        // Initialize the database
        insertedOpcaoNomeFantasiaEmpresa = opcaoNomeFantasiaEmpresaRepository.saveAndFlush(opcaoNomeFantasiaEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the opcaoNomeFantasiaEmpresa
        restOpcaoNomeFantasiaEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, opcaoNomeFantasiaEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return opcaoNomeFantasiaEmpresaRepository.count();
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

    protected OpcaoNomeFantasiaEmpresa getPersistedOpcaoNomeFantasiaEmpresa(OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa) {
        return opcaoNomeFantasiaEmpresaRepository.findById(opcaoNomeFantasiaEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedOpcaoNomeFantasiaEmpresaToMatchAllProperties(OpcaoNomeFantasiaEmpresa expectedOpcaoNomeFantasiaEmpresa) {
        assertOpcaoNomeFantasiaEmpresaAllPropertiesEquals(
            expectedOpcaoNomeFantasiaEmpresa,
            getPersistedOpcaoNomeFantasiaEmpresa(expectedOpcaoNomeFantasiaEmpresa)
        );
    }

    protected void assertPersistedOpcaoNomeFantasiaEmpresaToMatchUpdatableProperties(
        OpcaoNomeFantasiaEmpresa expectedOpcaoNomeFantasiaEmpresa
    ) {
        assertOpcaoNomeFantasiaEmpresaAllUpdatablePropertiesEquals(
            expectedOpcaoNomeFantasiaEmpresa,
            getPersistedOpcaoNomeFantasiaEmpresa(expectedOpcaoNomeFantasiaEmpresa)
        );
    }
}
