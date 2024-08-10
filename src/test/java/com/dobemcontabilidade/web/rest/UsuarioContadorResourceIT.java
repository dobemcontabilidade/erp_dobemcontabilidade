package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.UsuarioContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioContadorEnum;
import com.dobemcontabilidade.repository.UsuarioContadorRepository;
import com.dobemcontabilidade.service.UsuarioContadorService;
import com.dobemcontabilidade.service.dto.UsuarioContadorDTO;
import com.dobemcontabilidade.service.mapper.UsuarioContadorMapper;
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
 * Integration tests for the {@link UsuarioContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UsuarioContadorResourceIT {

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

    private static final SituacaoUsuarioContadorEnum DEFAULT_SITUACAO = SituacaoUsuarioContadorEnum.ATIVO;
    private static final SituacaoUsuarioContadorEnum UPDATED_SITUACAO = SituacaoUsuarioContadorEnum.INATIVO;

    private static final String ENTITY_API_URL = "/api/usuario-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UsuarioContadorRepository usuarioContadorRepository;

    @Mock
    private UsuarioContadorRepository usuarioContadorRepositoryMock;

    @Autowired
    private UsuarioContadorMapper usuarioContadorMapper;

    @Mock
    private UsuarioContadorService usuarioContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioContadorMockMvc;

    private UsuarioContador usuarioContador;

    private UsuarioContador insertedUsuarioContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioContador createEntity(EntityManager em) {
        UsuarioContador usuarioContador = new UsuarioContador()
            .email(DEFAULT_EMAIL)
            .senha(DEFAULT_SENHA)
            .token(DEFAULT_TOKEN)
            .dataHoraAtivacao(DEFAULT_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(DEFAULT_DATA_LIMITE_ACESSO)
            .situacao(DEFAULT_SITUACAO);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        usuarioContador.setContador(contador);
        // Add required entity
        Administrador administrador;
        if (TestUtil.findAll(em, Administrador.class).isEmpty()) {
            administrador = AdministradorResourceIT.createEntity(em);
            em.persist(administrador);
            em.flush();
        } else {
            administrador = TestUtil.findAll(em, Administrador.class).get(0);
        }
        usuarioContador.setAdministrador(administrador);
        return usuarioContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioContador createUpdatedEntity(EntityManager em) {
        UsuarioContador usuarioContador = new UsuarioContador()
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        usuarioContador.setContador(contador);
        // Add required entity
        Administrador administrador;
        if (TestUtil.findAll(em, Administrador.class).isEmpty()) {
            administrador = AdministradorResourceIT.createUpdatedEntity(em);
            em.persist(administrador);
            em.flush();
        } else {
            administrador = TestUtil.findAll(em, Administrador.class).get(0);
        }
        usuarioContador.setAdministrador(administrador);
        return usuarioContador;
    }

    @BeforeEach
    public void initTest() {
        usuarioContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedUsuarioContador != null) {
            usuarioContadorRepository.delete(insertedUsuarioContador);
            insertedUsuarioContador = null;
        }
    }

    @Test
    @Transactional
    void createUsuarioContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UsuarioContador
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);
        var returnedUsuarioContadorDTO = om.readValue(
            restUsuarioContadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioContadorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UsuarioContadorDTO.class
        );

        // Validate the UsuarioContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUsuarioContador = usuarioContadorMapper.toEntity(returnedUsuarioContadorDTO);
        assertUsuarioContadorUpdatableFieldsEquals(returnedUsuarioContador, getPersistedUsuarioContador(returnedUsuarioContador));

        insertedUsuarioContador = returnedUsuarioContador;
    }

    @Test
    @Transactional
    void createUsuarioContadorWithExistingId() throws Exception {
        // Create the UsuarioContador with an existing ID
        usuarioContador.setId(1L);
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioContadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        usuarioContador.setEmail(null);

        // Create the UsuarioContador, which fails.
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);

        restUsuarioContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioContadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarioContadors() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList
        restUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].dataHoraAtivacao").value(hasItem(DEFAULT_DATA_HORA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataLimiteAcesso").value(hasItem(DEFAULT_DATA_LIMITE_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(usuarioContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(usuarioContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUsuarioContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(usuarioContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUsuarioContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(usuarioContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUsuarioContador() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get the usuarioContador
        restUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, usuarioContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioContador.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.senha").value(DEFAULT_SENHA.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.dataHoraAtivacao").value(DEFAULT_DATA_HORA_ATIVACAO.toString()))
            .andExpect(jsonPath("$.dataLimiteAcesso").value(DEFAULT_DATA_LIMITE_ACESSO.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getUsuarioContadorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        Long id = usuarioContador.getId();

        defaultUsuarioContadorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultUsuarioContadorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultUsuarioContadorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where email equals to
        defaultUsuarioContadorFiltering("email.equals=" + DEFAULT_EMAIL, "email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where email in
        defaultUsuarioContadorFiltering("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL, "email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where email is not null
        defaultUsuarioContadorFiltering("email.specified=true", "email.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where email contains
        defaultUsuarioContadorFiltering("email.contains=" + DEFAULT_EMAIL, "email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where email does not contain
        defaultUsuarioContadorFiltering("email.doesNotContain=" + UPDATED_EMAIL, "email.doesNotContain=" + DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByDataHoraAtivacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where dataHoraAtivacao equals to
        defaultUsuarioContadorFiltering(
            "dataHoraAtivacao.equals=" + DEFAULT_DATA_HORA_ATIVACAO,
            "dataHoraAtivacao.equals=" + UPDATED_DATA_HORA_ATIVACAO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByDataHoraAtivacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where dataHoraAtivacao in
        defaultUsuarioContadorFiltering(
            "dataHoraAtivacao.in=" + DEFAULT_DATA_HORA_ATIVACAO + "," + UPDATED_DATA_HORA_ATIVACAO,
            "dataHoraAtivacao.in=" + UPDATED_DATA_HORA_ATIVACAO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByDataHoraAtivacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where dataHoraAtivacao is not null
        defaultUsuarioContadorFiltering("dataHoraAtivacao.specified=true", "dataHoraAtivacao.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByDataLimiteAcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where dataLimiteAcesso equals to
        defaultUsuarioContadorFiltering(
            "dataLimiteAcesso.equals=" + DEFAULT_DATA_LIMITE_ACESSO,
            "dataLimiteAcesso.equals=" + UPDATED_DATA_LIMITE_ACESSO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByDataLimiteAcessoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where dataLimiteAcesso in
        defaultUsuarioContadorFiltering(
            "dataLimiteAcesso.in=" + DEFAULT_DATA_LIMITE_ACESSO + "," + UPDATED_DATA_LIMITE_ACESSO,
            "dataLimiteAcesso.in=" + UPDATED_DATA_LIMITE_ACESSO
        );
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByDataLimiteAcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where dataLimiteAcesso is not null
        defaultUsuarioContadorFiltering("dataLimiteAcesso.specified=true", "dataLimiteAcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where situacao equals to
        defaultUsuarioContadorFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where situacao in
        defaultUsuarioContadorFiltering("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO, "situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        // Get all the usuarioContadorList where situacao is not null
        defaultUsuarioContadorFiltering("situacao.specified=true", "situacao.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByContadorIsEqualToSomething() throws Exception {
        // Get already existing entity
        Contador contador = usuarioContador.getContador();
        usuarioContadorRepository.saveAndFlush(usuarioContador);
        Long contadorId = contador.getId();
        // Get all the usuarioContadorList where contador equals to contadorId
        defaultUsuarioContadorShouldBeFound("contadorId.equals=" + contadorId);

        // Get all the usuarioContadorList where contador equals to (contadorId + 1)
        defaultUsuarioContadorShouldNotBeFound("contadorId.equals=" + (contadorId + 1));
    }

    @Test
    @Transactional
    void getAllUsuarioContadorsByAdministradorIsEqualToSomething() throws Exception {
        Administrador administrador;
        if (TestUtil.findAll(em, Administrador.class).isEmpty()) {
            usuarioContadorRepository.saveAndFlush(usuarioContador);
            administrador = AdministradorResourceIT.createEntity(em);
        } else {
            administrador = TestUtil.findAll(em, Administrador.class).get(0);
        }
        em.persist(administrador);
        em.flush();
        usuarioContador.setAdministrador(administrador);
        usuarioContadorRepository.saveAndFlush(usuarioContador);
        Long administradorId = administrador.getId();
        // Get all the usuarioContadorList where administrador equals to administradorId
        defaultUsuarioContadorShouldBeFound("administradorId.equals=" + administradorId);

        // Get all the usuarioContadorList where administrador equals to (administradorId + 1)
        defaultUsuarioContadorShouldNotBeFound("administradorId.equals=" + (administradorId + 1));
    }

    private void defaultUsuarioContadorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultUsuarioContadorShouldBeFound(shouldBeFound);
        defaultUsuarioContadorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuarioContadorShouldBeFound(String filter) throws Exception {
        restUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].senha").value(hasItem(DEFAULT_SENHA.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].dataHoraAtivacao").value(hasItem(DEFAULT_DATA_HORA_ATIVACAO.toString())))
            .andExpect(jsonPath("$.[*].dataLimiteAcesso").value(hasItem(DEFAULT_DATA_LIMITE_ACESSO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));

        // Check, that the count call also returns 1
        restUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuarioContadorShouldNotBeFound(String filter) throws Exception {
        restUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUsuarioContador() throws Exception {
        // Get the usuarioContador
        restUsuarioContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUsuarioContador() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioContador
        UsuarioContador updatedUsuarioContador = usuarioContadorRepository.findById(usuarioContador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUsuarioContador are not directly saved in db
        em.detach(updatedUsuarioContador);
        updatedUsuarioContador
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(updatedUsuarioContador);

        restUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioContadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUsuarioContadorToMatchAllProperties(updatedUsuarioContador);
    }

    @Test
    @Transactional
    void putNonExistingUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioContador.setId(longCount.incrementAndGet());

        // Create the UsuarioContador
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioContador.setId(longCount.incrementAndGet());

        // Create the UsuarioContador
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(usuarioContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioContador.setId(longCount.incrementAndGet());

        // Create the UsuarioContador
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(usuarioContadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioContadorWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioContador using partial update
        UsuarioContador partialUpdatedUsuarioContador = new UsuarioContador();
        partialUpdatedUsuarioContador.setId(usuarioContador.getId());

        partialUpdatedUsuarioContador.email(UPDATED_EMAIL).senha(UPDATED_SENHA);

        restUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioContador))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUsuarioContador, usuarioContador),
            getPersistedUsuarioContador(usuarioContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateUsuarioContadorWithPatch() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the usuarioContador using partial update
        UsuarioContador partialUpdatedUsuarioContador = new UsuarioContador();
        partialUpdatedUsuarioContador.setId(usuarioContador.getId());

        partialUpdatedUsuarioContador
            .email(UPDATED_EMAIL)
            .senha(UPDATED_SENHA)
            .token(UPDATED_TOKEN)
            .dataHoraAtivacao(UPDATED_DATA_HORA_ATIVACAO)
            .dataLimiteAcesso(UPDATED_DATA_LIMITE_ACESSO)
            .situacao(UPDATED_SITUACAO);

        restUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUsuarioContador))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUsuarioContadorUpdatableFieldsEquals(
            partialUpdatedUsuarioContador,
            getPersistedUsuarioContador(partialUpdatedUsuarioContador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioContador.setId(longCount.incrementAndGet());

        // Create the UsuarioContador
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioContadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioContador.setId(longCount.incrementAndGet());

        // Create the UsuarioContador
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(usuarioContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        usuarioContador.setId(longCount.incrementAndGet());

        // Create the UsuarioContador
        UsuarioContadorDTO usuarioContadorDTO = usuarioContadorMapper.toDto(usuarioContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioContadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(usuarioContadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuarioContador() throws Exception {
        // Initialize the database
        insertedUsuarioContador = usuarioContadorRepository.saveAndFlush(usuarioContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the usuarioContador
        restUsuarioContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuarioContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return usuarioContadorRepository.count();
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

    protected UsuarioContador getPersistedUsuarioContador(UsuarioContador usuarioContador) {
        return usuarioContadorRepository.findById(usuarioContador.getId()).orElseThrow();
    }

    protected void assertPersistedUsuarioContadorToMatchAllProperties(UsuarioContador expectedUsuarioContador) {
        assertUsuarioContadorAllPropertiesEquals(expectedUsuarioContador, getPersistedUsuarioContador(expectedUsuarioContador));
    }

    protected void assertPersistedUsuarioContadorToMatchUpdatableProperties(UsuarioContador expectedUsuarioContador) {
        assertUsuarioContadorAllUpdatablePropertiesEquals(expectedUsuarioContador, getPersistedUsuarioContador(expectedUsuarioContador));
    }
}
