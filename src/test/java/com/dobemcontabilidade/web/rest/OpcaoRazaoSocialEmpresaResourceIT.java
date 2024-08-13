package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa;
import com.dobemcontabilidade.repository.OpcaoRazaoSocialEmpresaRepository;
import com.dobemcontabilidade.service.OpcaoRazaoSocialEmpresaService;
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
 * Integration tests for the {@link OpcaoRazaoSocialEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OpcaoRazaoSocialEmpresaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;
    private static final Integer SMALLER_ORDEM = 1 - 1;

    private static final Boolean DEFAULT_SELECIONADO = false;
    private static final Boolean UPDATED_SELECIONADO = true;

    private static final String ENTITY_API_URL = "/api/opcao-razao-social-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository;

    @Mock
    private OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepositoryMock;

    @Mock
    private OpcaoRazaoSocialEmpresaService opcaoRazaoSocialEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpcaoRazaoSocialEmpresaMockMvc;

    private OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa;

    private OpcaoRazaoSocialEmpresa insertedOpcaoRazaoSocialEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpcaoRazaoSocialEmpresa createEntity(EntityManager em) {
        OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa = new OpcaoRazaoSocialEmpresa()
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
        opcaoRazaoSocialEmpresa.setEmpresa(empresa);
        return opcaoRazaoSocialEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpcaoRazaoSocialEmpresa createUpdatedEntity(EntityManager em) {
        OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa = new OpcaoRazaoSocialEmpresa()
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
        opcaoRazaoSocialEmpresa.setEmpresa(empresa);
        return opcaoRazaoSocialEmpresa;
    }

    @BeforeEach
    public void initTest() {
        opcaoRazaoSocialEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOpcaoRazaoSocialEmpresa != null) {
            opcaoRazaoSocialEmpresaRepository.delete(insertedOpcaoRazaoSocialEmpresa);
            insertedOpcaoRazaoSocialEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createOpcaoRazaoSocialEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OpcaoRazaoSocialEmpresa
        var returnedOpcaoRazaoSocialEmpresa = om.readValue(
            restOpcaoRazaoSocialEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OpcaoRazaoSocialEmpresa.class
        );

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOpcaoRazaoSocialEmpresaUpdatableFieldsEquals(
            returnedOpcaoRazaoSocialEmpresa,
            getPersistedOpcaoRazaoSocialEmpresa(returnedOpcaoRazaoSocialEmpresa)
        );

        insertedOpcaoRazaoSocialEmpresa = returnedOpcaoRazaoSocialEmpresa;
    }

    @Test
    @Transactional
    void createOpcaoRazaoSocialEmpresaWithExistingId() throws Exception {
        // Create the OpcaoRazaoSocialEmpresa with an existing ID
        opcaoRazaoSocialEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        opcaoRazaoSocialEmpresa.setNome(null);

        // Create the OpcaoRazaoSocialEmpresa, which fails.

        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresas() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opcaoRazaoSocialEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].selecionado").value(hasItem(DEFAULT_SELECIONADO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOpcaoRazaoSocialEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(opcaoRazaoSocialEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOpcaoRazaoSocialEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(opcaoRazaoSocialEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOpcaoRazaoSocialEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(opcaoRazaoSocialEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOpcaoRazaoSocialEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(opcaoRazaoSocialEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOpcaoRazaoSocialEmpresa() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get the opcaoRazaoSocialEmpresa
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, opcaoRazaoSocialEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opcaoRazaoSocialEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.selecionado").value(DEFAULT_SELECIONADO.booleanValue()));
    }

    @Test
    @Transactional
    void getOpcaoRazaoSocialEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        Long id = opcaoRazaoSocialEmpresa.getId();

        defaultOpcaoRazaoSocialEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultOpcaoRazaoSocialEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultOpcaoRazaoSocialEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where nome equals to
        defaultOpcaoRazaoSocialEmpresaFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where nome in
        defaultOpcaoRazaoSocialEmpresaFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where nome is not null
        defaultOpcaoRazaoSocialEmpresaFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where nome contains
        defaultOpcaoRazaoSocialEmpresaFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where nome does not contain
        defaultOpcaoRazaoSocialEmpresaFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByOrdemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where ordem equals to
        defaultOpcaoRazaoSocialEmpresaFiltering("ordem.equals=" + DEFAULT_ORDEM, "ordem.equals=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByOrdemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where ordem in
        defaultOpcaoRazaoSocialEmpresaFiltering("ordem.in=" + DEFAULT_ORDEM + "," + UPDATED_ORDEM, "ordem.in=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByOrdemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where ordem is not null
        defaultOpcaoRazaoSocialEmpresaFiltering("ordem.specified=true", "ordem.specified=false");
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByOrdemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where ordem is greater than or equal to
        defaultOpcaoRazaoSocialEmpresaFiltering("ordem.greaterThanOrEqual=" + DEFAULT_ORDEM, "ordem.greaterThanOrEqual=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByOrdemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where ordem is less than or equal to
        defaultOpcaoRazaoSocialEmpresaFiltering("ordem.lessThanOrEqual=" + DEFAULT_ORDEM, "ordem.lessThanOrEqual=" + SMALLER_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByOrdemIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where ordem is less than
        defaultOpcaoRazaoSocialEmpresaFiltering("ordem.lessThan=" + UPDATED_ORDEM, "ordem.lessThan=" + DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByOrdemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where ordem is greater than
        defaultOpcaoRazaoSocialEmpresaFiltering("ordem.greaterThan=" + SMALLER_ORDEM, "ordem.greaterThan=" + DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasBySelecionadoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where selecionado equals to
        defaultOpcaoRazaoSocialEmpresaFiltering("selecionado.equals=" + DEFAULT_SELECIONADO, "selecionado.equals=" + UPDATED_SELECIONADO);
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasBySelecionadoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where selecionado in
        defaultOpcaoRazaoSocialEmpresaFiltering(
            "selecionado.in=" + DEFAULT_SELECIONADO + "," + UPDATED_SELECIONADO,
            "selecionado.in=" + UPDATED_SELECIONADO
        );
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasBySelecionadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        // Get all the opcaoRazaoSocialEmpresaList where selecionado is not null
        defaultOpcaoRazaoSocialEmpresaFiltering("selecionado.specified=true", "selecionado.specified=false");
    }

    @Test
    @Transactional
    void getAllOpcaoRazaoSocialEmpresasByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        opcaoRazaoSocialEmpresa.setEmpresa(empresa);
        opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);
        Long empresaId = empresa.getId();
        // Get all the opcaoRazaoSocialEmpresaList where empresa equals to empresaId
        defaultOpcaoRazaoSocialEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the opcaoRazaoSocialEmpresaList where empresa equals to (empresaId + 1)
        defaultOpcaoRazaoSocialEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultOpcaoRazaoSocialEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultOpcaoRazaoSocialEmpresaShouldBeFound(shouldBeFound);
        defaultOpcaoRazaoSocialEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOpcaoRazaoSocialEmpresaShouldBeFound(String filter) throws Exception {
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opcaoRazaoSocialEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].selecionado").value(hasItem(DEFAULT_SELECIONADO.booleanValue())));

        // Check, that the count call also returns 1
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOpcaoRazaoSocialEmpresaShouldNotBeFound(String filter) throws Exception {
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOpcaoRazaoSocialEmpresa() throws Exception {
        // Get the opcaoRazaoSocialEmpresa
        restOpcaoRazaoSocialEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOpcaoRazaoSocialEmpresa() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opcaoRazaoSocialEmpresa
        OpcaoRazaoSocialEmpresa updatedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository
            .findById(opcaoRazaoSocialEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOpcaoRazaoSocialEmpresa are not directly saved in db
        em.detach(updatedOpcaoRazaoSocialEmpresa);
        updatedOpcaoRazaoSocialEmpresa.nome(UPDATED_NOME).ordem(UPDATED_ORDEM).selecionado(UPDATED_SELECIONADO);

        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpcaoRazaoSocialEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOpcaoRazaoSocialEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOpcaoRazaoSocialEmpresaToMatchAllProperties(updatedOpcaoRazaoSocialEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingOpcaoRazaoSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoRazaoSocialEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opcaoRazaoSocialEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpcaoRazaoSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoRazaoSocialEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpcaoRazaoSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoRazaoSocialEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpcaoRazaoSocialEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opcaoRazaoSocialEmpresa using partial update
        OpcaoRazaoSocialEmpresa partialUpdatedOpcaoRazaoSocialEmpresa = new OpcaoRazaoSocialEmpresa();
        partialUpdatedOpcaoRazaoSocialEmpresa.setId(opcaoRazaoSocialEmpresa.getId());

        partialUpdatedOpcaoRazaoSocialEmpresa.nome(UPDATED_NOME).ordem(UPDATED_ORDEM);

        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpcaoRazaoSocialEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpcaoRazaoSocialEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoRazaoSocialEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpcaoRazaoSocialEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOpcaoRazaoSocialEmpresa, opcaoRazaoSocialEmpresa),
            getPersistedOpcaoRazaoSocialEmpresa(opcaoRazaoSocialEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateOpcaoRazaoSocialEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the opcaoRazaoSocialEmpresa using partial update
        OpcaoRazaoSocialEmpresa partialUpdatedOpcaoRazaoSocialEmpresa = new OpcaoRazaoSocialEmpresa();
        partialUpdatedOpcaoRazaoSocialEmpresa.setId(opcaoRazaoSocialEmpresa.getId());

        partialUpdatedOpcaoRazaoSocialEmpresa.nome(UPDATED_NOME).ordem(UPDATED_ORDEM).selecionado(UPDATED_SELECIONADO);

        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpcaoRazaoSocialEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOpcaoRazaoSocialEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the OpcaoRazaoSocialEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOpcaoRazaoSocialEmpresaUpdatableFieldsEquals(
            partialUpdatedOpcaoRazaoSocialEmpresa,
            getPersistedOpcaoRazaoSocialEmpresa(partialUpdatedOpcaoRazaoSocialEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOpcaoRazaoSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoRazaoSocialEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opcaoRazaoSocialEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpcaoRazaoSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoRazaoSocialEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpcaoRazaoSocialEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        opcaoRazaoSocialEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(opcaoRazaoSocialEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpcaoRazaoSocialEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpcaoRazaoSocialEmpresa() throws Exception {
        // Initialize the database
        insertedOpcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaRepository.saveAndFlush(opcaoRazaoSocialEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the opcaoRazaoSocialEmpresa
        restOpcaoRazaoSocialEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, opcaoRazaoSocialEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return opcaoRazaoSocialEmpresaRepository.count();
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

    protected OpcaoRazaoSocialEmpresa getPersistedOpcaoRazaoSocialEmpresa(OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa) {
        return opcaoRazaoSocialEmpresaRepository.findById(opcaoRazaoSocialEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedOpcaoRazaoSocialEmpresaToMatchAllProperties(OpcaoRazaoSocialEmpresa expectedOpcaoRazaoSocialEmpresa) {
        assertOpcaoRazaoSocialEmpresaAllPropertiesEquals(
            expectedOpcaoRazaoSocialEmpresa,
            getPersistedOpcaoRazaoSocialEmpresa(expectedOpcaoRazaoSocialEmpresa)
        );
    }

    protected void assertPersistedOpcaoRazaoSocialEmpresaToMatchUpdatableProperties(
        OpcaoRazaoSocialEmpresa expectedOpcaoRazaoSocialEmpresa
    ) {
        assertOpcaoRazaoSocialEmpresaAllUpdatablePropertiesEquals(
            expectedOpcaoRazaoSocialEmpresa,
            getPersistedOpcaoRazaoSocialEmpresa(expectedOpcaoRazaoSocialEmpresa)
        );
    }
}
