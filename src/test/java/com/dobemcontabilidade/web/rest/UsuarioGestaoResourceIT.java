package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.UsuarioGestaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.domain.UsuarioGestao;
import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioGestaoEnum;
import com.dobemcontabilidade.repository.UsuarioGestaoRepository;
import com.dobemcontabilidade.service.UsuarioGestaoService;
import com.dobemcontabilidade.service.dto.UsuarioGestaoDTO;
import com.dobemcontabilidade.service.mapper.UsuarioGestaoMapper;
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
 * Integration tests for the {@link UsuarioGestaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UsuarioGestaoResourceIT {

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

    private static final SituacaoUsuarioGestaoEnum DEFAULT_SITUACAO = SituacaoUsuarioGestaoEnum.ATIVO;
    private static final SituacaoUsuarioGestaoEnum UPDATED_SITUACAO = SituacaoUsuarioGestaoEnum.INATIVO;

    private static final String ENTITY_API_URL = "/api/usuario-gestaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UsuarioGestaoRepository usuarioGestaoRepository;

    @Mock
    private UsuarioGestaoRepository usuarioGestaoRepositoryMock;

    @Autowired
    private UsuarioGestaoMapper usuarioGestaoMapper;

    @Mock
    private UsuarioGestaoService usuarioGestaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioGestaoMockMvc;

    private UsuarioGestao usuarioGestao;

    private UsuarioGestao insertedUsuarioGestao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioGestao createEntity(EntityManager em) {
        UsuarioGestao usuarioGestao = new UsuarioGestao()
            .email(DEFAULT_EMAIL)
            .senha(DEFAULT_SENHA)
            .token(DEFAULT_TOKEN)
            .dataHoraAtivacao(DEFAULT_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(DEFAULT_DATA_LIMITE_ACESSO)
            .situacao(DEFAULT_SITUACAO);
        // Add required entity
        Administrador administrador;
        if (TestUtil.findAll(em, Administrador.class).isEmpty()) {
            administrador = AdministradorResourceIT.createEntity(em);
            em.persist(administrador);
            em.flush();
        } else {
            administrador = TestUtil.findAll(em, Administrador.class).get(0);
        }
        usuarioGestao.setAdministrador(administrador);
        return usuarioGestao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioGestao createUpdatedEntity(EntityManager em) {
        UsuarioGestao usuarioGestao = new UsuarioGestao()
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);
        // Add required entity
        Administrador administrador;
        if (TestUtil.findAll(em, Administrador.class).isEmpty()) {
            administrador = AdministradorResourceIT.createUpdatedEntity(em);
            em.persist(administrador);
            em.flush();
        } else {
            administrador = TestUtil.findAll(em, Administrador.class).get(0);
        }
        usuarioGestao.setAdministrador(administrador);
        return usuarioGestao;
    }

    @BeforeEach
    public void initTest() {
        usuarioGestao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedUsuarioGestao != null) {
            usuarioGestaoRepository.delete(insertedUsuarioGestao);
            insertedUsuarioGestao = null;
        }
    }

    @Test
    @Transactional
    void createUsuarioGestao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UsuarioGestao
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);
        var returnedUsuarioGestaoDTO = om.readValue(
            restUsuarioGestaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioGestaoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UsuarioGestaoDTO.class
        );

        // Validate the UsuarioGestao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUsuarioGestao = usuarioGestaoMapper.toEntity(returnedUsuarioGestaoDTO);
        assertUsuarioGestaoUpdatableFieldsEquals(returnedUsuarioGestao, getPersistedUsuarioGestao(returnedUsuarioGestao));

        insertedUsuarioGestao = returnedUsuarioGestao;
    }

    @Test
    @Transactional
    void createUsuarioGestaoWithExistingId() throws Exception {
        // Create the UsuarioGestao with an existing ID
        usuarioGestao.setId(1L);
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioGestaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioGestaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioGestao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuarioGestao.setEmail(null);

        // Create the UsuarioGestao, which fails.
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);

        restUsuarioGestaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioGestaoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarioGestaos() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList
        restUsuarioGestaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioGestao.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].dataHoraAtivacao").value(hasItem(DEFAULT_DATA_HORA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataLimiteAcesso").value(hasItem(DEFAULT_DATA_LIMITE_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioGestaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(usuarioGestaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioGestaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(usuarioGestaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioGestaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(usuarioGestaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioGestaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(usuarioGestaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUsuarioGestao() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get the usuarioGestao
        restUsuarioGestaoMockMvc
            .perform(get(ENTITY_API_URL_ID, usuarioGestao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioGestao.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.dataHoraAtivacao").value(DEFAULT_DATA_HORA_ATIVACAO.toString()))
            .andExpect(jsonPath("$.dataLimiteAcesso").value(DEFAULT_DATA_LIMITE_ACESSO.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getUsuarioGestaosByIdFiltering() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        Long id = usuarioGestao.getId();

        defaultUsuarioGestaoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultUsuarioGestaoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultUsuarioGestaoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where email equals to
        defaultUsuarioGestaoFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where email in
        defaultUsuarioGestaoFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where email is not null
        defaultUsuarioGestaoFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where email contains
        defaultUsuarioGestaoFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where email does not contain
        defaultUsuarioGestaoFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByDataHoraAtivacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where dataHoraAtivacao equals to
        defaultUsuarioGestaoFiltering(
            "dataHoraAtivacao.equals=" + DEFAULT_DATA_HORA_ATIVACAO,
            "dataHoraAtivacao.equals=" + UPDATED_DATA_HORA_ATIVACAO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByDataHoraAtivacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where dataHoraAtivacao in
        defaultUsuarioGestaoFiltering(
            "dataHoraAtivacao.in=" + DEFAULT_DATA_HORA_ATIVACAO + "," + UPDATED_DATA_HORA_ATIVACAO,
            "dataHoraAtivacao.in=" + UPDATED_DATA_HORA_ATIVACAO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByDataHoraAtivacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where dataHoraAtivacao is not null
        defaultUsuarioGestaoFiltering("dataHoraAtivacao.specified=true", "dataHoraAtivacao.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByDataLimiteAcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where dataLimiteAcesso equals to
        defaultUsuarioGestaoFiltering(
            "dataLimiteAcesso.equals=" + DEFAULT_DATA_LIMITE_ACESSO,
            "dataLimiteAcesso.equals=" + UPDATED_DATA_LIMITE_ACESSO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByDataLimiteAcessoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where dataLimiteAcesso in
        defaultUsuarioGestaoFiltering(
            "dataLimiteAcesso.in=" + DEFAULT_DATA_LIMITE_ACESSO + "," + UPDATED_DATA_LIMITE_ACESSO,
            "dataLimiteAcesso.in=" + UPDATED_DATA_LIMITE_ACESSO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByDataLimiteAcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where dataLimiteAcesso is not null
        defaultUsuarioGestaoFiltering("dataLimiteAcesso.specified=true", "dataLimiteAcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where situacao equals to
        defaultUsuarioGestaoFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where situacao in
        defaultUsuarioGestaoFiltering("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO, "situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        // Get all the usuarioGestaoList where situacao is not null
        defaultUsuarioGestaoFiltering("situacao.specified=true", "situacao.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioGestaosByAdministradorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Administrador administrador = usuarioGestao.getAdministrador();
        usuarioGestaoRepository.saveAndFlush(usuarioGestao);
        Long administradorId = administrador.getId();
        // Get all the usuarioGestaoList where administrador equals to administradorId
        defaultUsuarioGestaoShouldBeFound("administradorId.equals=" + administradorId);

        // Get all the usuarioGestaoList where administrador equals to (administradorId + 1)
        defaultUsuarioGestaoShouldNotBeFound("administradorId.equals=" + (administradorId + 1));
    }

    private void defaultUsuarioGestaoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultUsuarioGestaoShouldBeFound(shouldBeFound);
        defaultUsuarioGestaoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuarioGestaoShouldBeFound(String filter) throws Exception {
        restUsuarioGestaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioGestao.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].dataHoraAtivacao").value(hasItem(DEFAULT_DATA_HORA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataLimiteAcesso").value(hasItem(DEFAULT_DATA_LIMITE_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));

        // Check, that the count call also returns 1
        restUsuarioGestaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuarioGestaoShouldNotBeFound(String filter) throws Exception {
        restUsuarioGestaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuarioGestaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUsuarioGestao() throws Exception {
        // Get the usuarioGestao
        restUsuarioGestaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUsuarioGestao() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioGestao
        UsuarioGestao updatedUsuarioGestao = usuarioGestaoRepository.findById(usuarioGestao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUsuarioGestao are not directly saved in db
        em.detach(updatedUsuarioGestao);
        updatedUsuarioGestao
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(updatedUsuarioGestao);

        restUsuarioGestaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioGestaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioGestaoDTO))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioGestao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUsuarioGestaoToMatchAllProperties(updatedUsuarioGestao);
    }

    @Test
    @Transactional
    void putNonExistingUsuarioGestao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioGestao.setId(longCount.incrementAndGet());

        // Create the UsuarioGestao
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioGestaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioGestaoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioGestaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioGestao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuarioGestao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioGestao.setId(longCount.incrementAndGet());

        // Create the UsuarioGestao
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioGestaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioGestaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioGestao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuarioGestao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioGestao.setId(longCount.incrementAndGet());

        // Create the UsuarioGestao
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioGestaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioGestaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioGestao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioGestaoWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioGestao using partial update
        UsuarioGestao partialUpdatedUsuarioGestao = new UsuarioGestao();
        partialUpdatedUsuarioGestao.setId(usuarioGestao.getId());

        partialUpdatedUsuarioGestao.dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO).dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO);

        restUsuarioGestaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioGestao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioGestao))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioGestao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioGestaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUsuarioGestao, usuarioGestao),
            getPersistedUsuarioGestao(usuarioGestao)
        );
    }

    @Test
    @Transactional
    void fullUpdateUsuarioGestaoWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioGestao using partial update
        UsuarioGestao partialUpdatedUsuarioGestao = new UsuarioGestao();
        partialUpdatedUsuarioGestao.setId(usuarioGestao.getId());

        partialUpdatedUsuarioGestao
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);

        restUsuarioGestaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioGestao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioGestao))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioGestao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioGestaoUpdatableFieldsEquals(partialUpdatedUsuarioGestao, getPersistedUsuarioGestao(partialUpdatedUsuarioGestao));
    }

    @Test
    @Transactional
    void patchNonExistingUsuarioGestao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioGestao.setId(longCount.incrementAndGet());

        // Create the UsuarioGestao
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioGestaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioGestaoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioGestaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioGestao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuarioGestao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioGestao.setId(longCount.incrementAndGet());

        // Create the UsuarioGestao
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioGestaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioGestaoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioGestao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuarioGestao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioGestao.setId(longCount.incrementAndGet());

        // Create the UsuarioGestao
        UsuarioGestaoDTO usuarioGestaoDTO = usuarioGestaoMapper.toDto(usuarioGestao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioGestaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(usuarioGestaoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioGestao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuarioGestao() throws Exception {
        // Initialize the database
        insertedUsuarioGestao = usuarioGestaoRepository.saveAndFlush(usuarioGestao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the usuarioGestao
        restUsuarioGestaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuarioGestao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return usuarioGestaoRepository.count();
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

    protected UsuarioGestao getPersistedUsuarioGestao(UsuarioGestao usuarioGestao) {
        return usuarioGestaoRepository.findById(usuarioGestao.getId()).orElseThrow();
    }

    protected void assertPersistedUsuarioGestaoToMatchAllProperties(UsuarioGestao expectedUsuarioGestao) {
        assertUsuarioGestaoAllPropertiesEquals(expectedUsuarioGestao, getPersistedUsuarioGestao(expectedUsuarioGestao));
    }

    protected void assertPersistedUsuarioGestaoToMatchUpdatableProperties(UsuarioGestao expectedUsuarioGestao) {
        assertUsuarioGestaoAllUpdatablePropertiesEquals(expectedUsuarioGestao, getPersistedUsuarioGestao(expectedUsuarioGestao));
    }
}
