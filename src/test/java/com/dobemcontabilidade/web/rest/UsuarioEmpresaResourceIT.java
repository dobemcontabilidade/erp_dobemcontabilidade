package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.UsuarioEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioEmpresaEnum;
import com.dobemcontabilidade.repository.UsuarioEmpresaRepository;
import com.dobemcontabilidade.service.UsuarioEmpresaService;
import com.dobemcontabilidade.service.dto.UsuarioEmpresaDTO;
import com.dobemcontabilidade.service.mapper.UsuarioEmpresaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link UsuarioEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UsuarioEmpresaResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_SENHA = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_ATIVACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_ATIVACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_LIMITE_ACESSO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_LIMITE_ACESSO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SituacaoUsuarioEmpresaEnum DEFAULT_SITUACAO = SituacaoUsuarioEmpresaEnum.ATIVO;
    private static final SituacaoUsuarioEmpresaEnum UPDATED_SITUACAO = SituacaoUsuarioEmpresaEnum.INATIVO;

    private static final String ENTITY_API_URL = "/api/usuario-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UsuarioEmpresaRepository usuarioEmpresaRepository;

    @Mock
    private UsuarioEmpresaRepository usuarioEmpresaRepositoryMock;

    @Autowired
    private UsuarioEmpresaMapper usuarioEmpresaMapper;

    @Mock
    private UsuarioEmpresaService usuarioEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioEmpresaMockMvc;

    private UsuarioEmpresa usuarioEmpresa;

    private UsuarioEmpresa insertedUsuarioEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioEmpresa createEntity(EntityManager em) {
        UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa()
            .email(DEFAULT_EMAIL)
            .senha(DEFAULT_SENHA)
            .token(DEFAULT_TOKEN)
            .dataHoraAtivacao(DEFAULT_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(DEFAULT_DATA_LIMITE_ACESSO)
            .situacao(DEFAULT_SITUACAO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        usuarioEmpresa.setEmpresa(empresa);
        return usuarioEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioEmpresa createUpdatedEntity(EntityManager em) {
        UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa()
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        usuarioEmpresa.setEmpresa(empresa);
        return usuarioEmpresa;
    }

    @BeforeEach
    public void initTest() {
        usuarioEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedUsuarioEmpresa != null) {
            usuarioEmpresaRepository.delete(insertedUsuarioEmpresa);
            insertedUsuarioEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UsuarioEmpresa
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);
        var returnedUsuarioEmpresaDTO = om.readValue(
            restUsuarioEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioEmpresaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UsuarioEmpresaDTO.class
        );

        // Validate the UsuarioEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUsuarioEmpresa = usuarioEmpresaMapper.toEntity(returnedUsuarioEmpresaDTO);
        assertUsuarioEmpresaUpdatableFieldsEquals(returnedUsuarioEmpresa, getPersistedUsuarioEmpresa(returnedUsuarioEmpresa));

        insertedUsuarioEmpresa = returnedUsuarioEmpresa;
    }

    @Test
    @Transactional
    void createUsuarioEmpresaWithExistingId() throws Exception {
        // Create the UsuarioEmpresa with an existing ID
        usuarioEmpresa.setId(1L);
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuarioEmpresa.setEmail(null);

        // Create the UsuarioEmpresa, which fails.
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);

        restUsuarioEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioEmpresaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresas() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList
        restUsuarioEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].dataHoraAtivacao").value(hasItem(DEFAULT_DATA_HORA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataLimiteAcesso").value(hasItem(DEFAULT_DATA_LIMITE_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(usuarioEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(usuarioEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(usuarioEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(usuarioEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUsuarioEmpresa() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get the usuarioEmpresa
        restUsuarioEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, usuarioEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.dataHoraAtivacao").value(DEFAULT_DATA_HORA_ATIVACAO.toString()))
            .andExpect(jsonPath("$.dataLimiteAcesso").value(DEFAULT_DATA_LIMITE_ACESSO.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getUsuarioEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        Long id = usuarioEmpresa.getId();

        defaultUsuarioEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultUsuarioEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultUsuarioEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where email equals to
        defaultUsuarioEmpresaFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where email in
        defaultUsuarioEmpresaFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where email is not null
        defaultUsuarioEmpresaFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where email contains
        defaultUsuarioEmpresaFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where email does not contain
        defaultUsuarioEmpresaFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByDataHoraAtivacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where dataHoraAtivacao equals to
        defaultUsuarioEmpresaFiltering(
            "dataHoraAtivacao.equals=" + DEFAULT_DATA_HORA_ATIVACAO,
            "dataHoraAtivacao.equals=" + UPDATED_DATA_HORA_ATIVACAO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByDataHoraAtivacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where dataHoraAtivacao in
        defaultUsuarioEmpresaFiltering(
            "dataHoraAtivacao.in=" + DEFAULT_DATA_HORA_ATIVACAO + "," + UPDATED_DATA_HORA_ATIVACAO,
            "dataHoraAtivacao.in=" + UPDATED_DATA_HORA_ATIVACAO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByDataHoraAtivacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where dataHoraAtivacao is not null
        defaultUsuarioEmpresaFiltering("dataHoraAtivacao.specified=true", "dataHoraAtivacao.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByDataLimiteAcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where dataLimiteAcesso equals to
        defaultUsuarioEmpresaFiltering(
            "dataLimiteAcesso.equals=" + DEFAULT_DATA_LIMITE_ACESSO,
            "dataLimiteAcesso.equals=" + UPDATED_DATA_LIMITE_ACESSO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByDataLimiteAcessoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where dataLimiteAcesso in
        defaultUsuarioEmpresaFiltering(
            "dataLimiteAcesso.in=" + DEFAULT_DATA_LIMITE_ACESSO + "," + UPDATED_DATA_LIMITE_ACESSO,
            "dataLimiteAcesso.in=" + UPDATED_DATA_LIMITE_ACESSO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByDataLimiteAcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where dataLimiteAcesso is not null
        defaultUsuarioEmpresaFiltering("dataLimiteAcesso.specified=true", "dataLimiteAcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where situacao equals to
        defaultUsuarioEmpresaFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where situacao in
        defaultUsuarioEmpresaFiltering("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO, "situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        // Get all the usuarioEmpresaList where situacao is not null
        defaultUsuarioEmpresaFiltering("situacao.specified=true", "situacao.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByPessoaIsEqualToSomething() throws Exception {
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);
            pessoa = PessoaResourceIT.createEntity(em);
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(pessoa);
        em.flush();
        usuarioEmpresa.setPessoa(pessoa);
        usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);
        Long pessoaId = pessoa.getId();
        // Get all the usuarioEmpresaList where pessoa equals to pessoaId
        defaultUsuarioEmpresaShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the usuarioEmpresaList where pessoa equals to (pessoaId + 1)
        defaultUsuarioEmpresaShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    @Test
    @Transactional
    void getAllUsuarioEmpresasByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        usuarioEmpresa.setEmpresa(empresa);
        usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);
        Long empresaId = empresa.getId();
        // Get all the usuarioEmpresaList where empresa equals to empresaId
        defaultUsuarioEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the usuarioEmpresaList where empresa equals to (empresaId + 1)
        defaultUsuarioEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultUsuarioEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultUsuarioEmpresaShouldBeFound(shouldBeFound);
        defaultUsuarioEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuarioEmpresaShouldBeFound(String filter) throws Exception {
        restUsuarioEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].dataHoraAtivacao").value(hasItem(DEFAULT_DATA_HORA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataLimiteAcesso").value(hasItem(DEFAULT_DATA_LIMITE_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));

        // Check, that the count call also returns 1
        restUsuarioEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuarioEmpresaShouldNotBeFound(String filter) throws Exception {
        restUsuarioEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuarioEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUsuarioEmpresa() throws Exception {
        // Get the usuarioEmpresa
        restUsuarioEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUsuarioEmpresa() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioEmpresa
        UsuarioEmpresa updatedUsuarioEmpresa = usuarioEmpresaRepository.findById(usuarioEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUsuarioEmpresa are not directly saved in db
        em.detach(updatedUsuarioEmpresa);
        updatedUsuarioEmpresa
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(updatedUsuarioEmpresa);

        restUsuarioEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioEmpresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUsuarioEmpresaToMatchAllProperties(updatedUsuarioEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioEmpresa.setId(longCount.incrementAndGet());

        // Create the UsuarioEmpresa
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioEmpresa.setId(longCount.incrementAndGet());

        // Create the UsuarioEmpresa
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioEmpresa.setId(longCount.incrementAndGet());

        // Create the UsuarioEmpresa
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioEmpresa using partial update
        UsuarioEmpresa partialUpdatedUsuarioEmpresa = new UsuarioEmpresa();
        partialUpdatedUsuarioEmpresa.setId(usuarioEmpresa.getId());

        partialUpdatedUsuarioEmpresa.email(UPDATED_EMAIL).token(UPDATED_TOKEN);

        restUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUsuarioEmpresa, usuarioEmpresa),
            getPersistedUsuarioEmpresa(usuarioEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateUsuarioEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioEmpresa using partial update
        UsuarioEmpresa partialUpdatedUsuarioEmpresa = new UsuarioEmpresa();
        partialUpdatedUsuarioEmpresa.setId(usuarioEmpresa.getId());

        partialUpdatedUsuarioEmpresa
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);

        restUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioEmpresaUpdatableFieldsEquals(partialUpdatedUsuarioEmpresa, getPersistedUsuarioEmpresa(partialUpdatedUsuarioEmpresa));
    }

    @Test
    @Transactional
    void patchNonExistingUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioEmpresa.setId(longCount.incrementAndGet());

        // Create the UsuarioEmpresa
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioEmpresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioEmpresa.setId(longCount.incrementAndGet());

        // Create the UsuarioEmpresa
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioEmpresa.setId(longCount.incrementAndGet());

        // Create the UsuarioEmpresa
        UsuarioEmpresaDTO usuarioEmpresaDTO = usuarioEmpresaMapper.toDto(usuarioEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(usuarioEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuarioEmpresa() throws Exception {
        // Initialize the database
        insertedUsuarioEmpresa = usuarioEmpresaRepository.saveAndFlush(usuarioEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the usuarioEmpresa
        restUsuarioEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuarioEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return usuarioEmpresaRepository.count();
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

    protected UsuarioEmpresa getPersistedUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        return usuarioEmpresaRepository.findById(usuarioEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedUsuarioEmpresaToMatchAllProperties(UsuarioEmpresa expectedUsuarioEmpresa) {
        assertUsuarioEmpresaAllPropertiesEquals(expectedUsuarioEmpresa, getPersistedUsuarioEmpresa(expectedUsuarioEmpresa));
    }

    protected void assertPersistedUsuarioEmpresaToMatchUpdatableProperties(UsuarioEmpresa expectedUsuarioEmpresa) {
        assertUsuarioEmpresaAllUpdatablePropertiesEquals(expectedUsuarioEmpresa, getPersistedUsuarioEmpresa(expectedUsuarioEmpresa));
    }
}
