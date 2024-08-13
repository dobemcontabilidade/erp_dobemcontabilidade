package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.domain.enumeration.SituacaoContadorEnum;
import com.dobemcontabilidade.repository.ContadorRepository;
import com.dobemcontabilidade.service.ContadorService;
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
 * Integration tests for the {@link ContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContadorResourceIT {

    private static final String DEFAULT_CRC = "AAAAAAAAAA";
    private static final String UPDATED_CRC = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIMITE_EMPRESAS = 1;
    private static final Integer UPDATED_LIMITE_EMPRESAS = 2;
    private static final Integer SMALLER_LIMITE_EMPRESAS = 1 - 1;

    private static final Integer DEFAULT_LIMITE_DEPARTAMENTOS = 1;
    private static final Integer UPDATED_LIMITE_DEPARTAMENTOS = 2;
    private static final Integer SMALLER_LIMITE_DEPARTAMENTOS = 1 - 1;

    private static final Double DEFAULT_LIMITE_FATURAMENTO = 1D;
    private static final Double UPDATED_LIMITE_FATURAMENTO = 2D;
    private static final Double SMALLER_LIMITE_FATURAMENTO = 1D - 1D;

    private static final SituacaoContadorEnum DEFAULT_SITUACAO_CONTADOR = SituacaoContadorEnum.BANIDO;
    private static final SituacaoContadorEnum UPDATED_SITUACAO_CONTADOR = SituacaoContadorEnum.BLOQUEADO;

    private static final String ENTITY_API_URL = "/api/contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContadorRepository contadorRepository;

    @Mock
    private ContadorRepository contadorRepositoryMock;

    @Mock
    private ContadorService contadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContadorMockMvc;

    private Contador contador;

    private Contador insertedContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contador createEntity(EntityManager em) {
        Contador contador = new Contador()
            .crc(DEFAULT_CRC)
            .limiteEmpresas(DEFAULT_LIMITE_EMPRESAS)
            .limiteDepartamentos(DEFAULT_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(DEFAULT_LIMITE_FATURAMENTO)
            .situacaoContador(DEFAULT_SITUACAO_CONTADOR);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        contador.setPessoa(pessoa);
        // Add required entity
        UsuarioContador usuarioContador;
        if (TestUtil.findAll(em, UsuarioContador.class).isEmpty()) {
            usuarioContador = UsuarioContadorResourceIT.createEntity(em);
            em.persist(usuarioContador);
            em.flush();
        } else {
            usuarioContador = TestUtil.findAll(em, UsuarioContador.class).get(0);
        }
        contador.setUsuarioContador(usuarioContador);
        // Add required entity
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContador = PerfilContadorResourceIT.createEntity(em);
            em.persist(perfilContador);
            em.flush();
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        contador.setPerfilContador(perfilContador);
        return contador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contador createUpdatedEntity(EntityManager em) {
        Contador contador = new Contador()
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .situacaoContador(UPDATED_SITUACAO_CONTADOR);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        contador.setPessoa(pessoa);
        // Add required entity
        UsuarioContador usuarioContador;
        if (TestUtil.findAll(em, UsuarioContador.class).isEmpty()) {
            usuarioContador = UsuarioContadorResourceIT.createUpdatedEntity(em);
            em.persist(usuarioContador);
            em.flush();
        } else {
            usuarioContador = TestUtil.findAll(em, UsuarioContador.class).get(0);
        }
        contador.setUsuarioContador(usuarioContador);
        // Add required entity
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContador = PerfilContadorResourceIT.createUpdatedEntity(em);
            em.persist(perfilContador);
            em.flush();
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        contador.setPerfilContador(perfilContador);
        return contador;
    }

    @BeforeEach
    public void initTest() {
        contador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedContador != null) {
            contadorRepository.delete(insertedContador);
            insertedContador = null;
        }
    }

    @Test
    @Transactional
    void createContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Contador
        var returnedContador = om.readValue(
            restContadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Contador.class
        );

        // Validate the Contador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContadorUpdatableFieldsEquals(returnedContador, getPersistedContador(returnedContador));

        insertedContador = returnedContador;
    }

    @Test
    @Transactional
    void createContadorWithExistingId() throws Exception {
        // Create the Contador with an existing ID
        contador.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContadors() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contador.getId().intValue())))
            .andExpect(jsonPath("$.[*].crc").value(hasItem(DEFAULT_CRC)))
            .andExpect(jsonPath("$.[*].limiteEmpresas").value(hasItem(DEFAULT_LIMITE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].limiteDepartamentos").value(hasItem(DEFAULT_LIMITE_DEPARTAMENTOS)))
            .andExpect(jsonPath("$.[*].limiteFaturamento").value(hasItem(DEFAULT_LIMITE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].situacaoContador").value(hasItem(DEFAULT_SITUACAO_CONTADOR.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(contadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getContador() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get the contador
        restContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, contador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contador.getId().intValue()))
            .andExpect(jsonPath("$.crc").value(DEFAULT_CRC))
            .andExpect(jsonPath("$.limiteEmpresas").value(DEFAULT_LIMITE_EMPRESAS))
            .andExpect(jsonPath("$.limiteDepartamentos").value(DEFAULT_LIMITE_DEPARTAMENTOS))
            .andExpect(jsonPath("$.limiteFaturamento").value(DEFAULT_LIMITE_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.situacaoContador").value(DEFAULT_SITUACAO_CONTADOR.toString()));
    }

    @Test
    @Transactional
    void getContadorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        Long id = contador.getId();

        defaultContadorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultContadorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultContadorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContadorsByCrcIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc equals to
        defaultContadorFiltering("crc.equals=" + DEFAULT_CRC, "crc.equals=" + UPDATED_CRC);
    }

    @Test
    @Transactional
    void getAllContadorsByCrcIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc in
        defaultContadorFiltering("crc.in=" + DEFAULT_CRC + "," + UPDATED_CRC, "crc.in=" + UPDATED_CRC);
    }

    @Test
    @Transactional
    void getAllContadorsByCrcIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc is not null
        defaultContadorFiltering("crc.specified=true", "crc.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByCrcContainsSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc contains
        defaultContadorFiltering("crc.contains=" + DEFAULT_CRC, "crc.contains=" + UPDATED_CRC);
    }

    @Test
    @Transactional
    void getAllContadorsByCrcNotContainsSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc does not contain
        defaultContadorFiltering("crc.doesNotContain=" + UPDATED_CRC, "crc.doesNotContain=" + DEFAULT_CRC);
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas equals to
        defaultContadorFiltering("limiteEmpresas.equals=" + DEFAULT_LIMITE_EMPRESAS, "limiteEmpresas.equals=" + UPDATED_LIMITE_EMPRESAS);
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas in
        defaultContadorFiltering(
            "limiteEmpresas.in=" + DEFAULT_LIMITE_EMPRESAS + "," + UPDATED_LIMITE_EMPRESAS,
            "limiteEmpresas.in=" + UPDATED_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is not null
        defaultContadorFiltering("limiteEmpresas.specified=true", "limiteEmpresas.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is greater than or equal to
        defaultContadorFiltering(
            "limiteEmpresas.greaterThanOrEqual=" + DEFAULT_LIMITE_EMPRESAS,
            "limiteEmpresas.greaterThanOrEqual=" + UPDATED_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is less than or equal to
        defaultContadorFiltering(
            "limiteEmpresas.lessThanOrEqual=" + DEFAULT_LIMITE_EMPRESAS,
            "limiteEmpresas.lessThanOrEqual=" + SMALLER_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is less than
        defaultContadorFiltering(
            "limiteEmpresas.lessThan=" + UPDATED_LIMITE_EMPRESAS,
            "limiteEmpresas.lessThan=" + DEFAULT_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is greater than
        defaultContadorFiltering(
            "limiteEmpresas.greaterThan=" + SMALLER_LIMITE_EMPRESAS,
            "limiteEmpresas.greaterThan=" + DEFAULT_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos equals to
        defaultContadorFiltering(
            "limiteDepartamentos.equals=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.equals=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos in
        defaultContadorFiltering(
            "limiteDepartamentos.in=" + DEFAULT_LIMITE_DEPARTAMENTOS + "," + UPDATED_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.in=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is not null
        defaultContadorFiltering("limiteDepartamentos.specified=true", "limiteDepartamentos.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is greater than or equal to
        defaultContadorFiltering(
            "limiteDepartamentos.greaterThanOrEqual=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.greaterThanOrEqual=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is less than or equal to
        defaultContadorFiltering(
            "limiteDepartamentos.lessThanOrEqual=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.lessThanOrEqual=" + SMALLER_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is less than
        defaultContadorFiltering(
            "limiteDepartamentos.lessThan=" + UPDATED_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.lessThan=" + DEFAULT_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is greater than
        defaultContadorFiltering(
            "limiteDepartamentos.greaterThan=" + SMALLER_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.greaterThan=" + DEFAULT_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento equals to
        defaultContadorFiltering(
            "limiteFaturamento.equals=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.equals=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento in
        defaultContadorFiltering(
            "limiteFaturamento.in=" + DEFAULT_LIMITE_FATURAMENTO + "," + UPDATED_LIMITE_FATURAMENTO,
            "limiteFaturamento.in=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is not null
        defaultContadorFiltering("limiteFaturamento.specified=true", "limiteFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is greater than or equal to
        defaultContadorFiltering(
            "limiteFaturamento.greaterThanOrEqual=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.greaterThanOrEqual=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is less than or equal to
        defaultContadorFiltering(
            "limiteFaturamento.lessThanOrEqual=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.lessThanOrEqual=" + SMALLER_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is less than
        defaultContadorFiltering(
            "limiteFaturamento.lessThan=" + UPDATED_LIMITE_FATURAMENTO,
            "limiteFaturamento.lessThan=" + DEFAULT_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is greater than
        defaultContadorFiltering(
            "limiteFaturamento.greaterThan=" + SMALLER_LIMITE_FATURAMENTO,
            "limiteFaturamento.greaterThan=" + DEFAULT_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsBySituacaoContadorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where situacaoContador equals to
        defaultContadorFiltering(
            "situacaoContador.equals=" + DEFAULT_SITUACAO_CONTADOR,
            "situacaoContador.equals=" + UPDATED_SITUACAO_CONTADOR
        );
    }

    @Test
    @Transactional
    void getAllContadorsBySituacaoContadorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where situacaoContador in
        defaultContadorFiltering(
            "situacaoContador.in=" + DEFAULT_SITUACAO_CONTADOR + "," + UPDATED_SITUACAO_CONTADOR,
            "situacaoContador.in=" + UPDATED_SITUACAO_CONTADOR
        );
    }

    @Test
    @Transactional
    void getAllContadorsBySituacaoContadorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where situacaoContador is not null
        defaultContadorFiltering("situacaoContador.specified=true", "situacaoContador.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByPessoaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Pessoa pessoa = contador.getPessoa();
        contadorRepository.saveAndFlush(contador);
        Long pessoaId = pessoa.getId();
        // Get all the contadorList where pessoa equals to pessoaId
        defaultContadorShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the contadorList where pessoa equals to (pessoaId + 1)
        defaultContadorShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    @Test
    @Transactional
    void getAllContadorsByUsuarioContadorIsEqualToSomething() throws Exception {
        // Get already existing entity
        UsuarioContador usuarioContador = contador.getUsuarioContador();
        contadorRepository.saveAndFlush(contador);
        Long usuarioContadorId = usuarioContador.getId();
        // Get all the contadorList where usuarioContador equals to usuarioContadorId
        defaultContadorShouldBeFound("usuarioContadorId.equals=" + usuarioContadorId);

        // Get all the contadorList where usuarioContador equals to (usuarioContadorId + 1)
        defaultContadorShouldNotBeFound("usuarioContadorId.equals=" + (usuarioContadorId + 1));
    }

    @Test
    @Transactional
    void getAllContadorsByPerfilContadorIsEqualToSomething() throws Exception {
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            contadorRepository.saveAndFlush(contador);
            perfilContador = PerfilContadorResourceIT.createEntity(em);
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        em.persist(perfilContador);
        em.flush();
        contador.setPerfilContador(perfilContador);
        contadorRepository.saveAndFlush(contador);
        Long perfilContadorId = perfilContador.getId();
        // Get all the contadorList where perfilContador equals to perfilContadorId
        defaultContadorShouldBeFound("perfilContadorId.equals=" + perfilContadorId);

        // Get all the contadorList where perfilContador equals to (perfilContadorId + 1)
        defaultContadorShouldNotBeFound("perfilContadorId.equals=" + (perfilContadorId + 1));
    }

    private void defaultContadorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultContadorShouldBeFound(shouldBeFound);
        defaultContadorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContadorShouldBeFound(String filter) throws Exception {
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contador.getId().intValue())))
            .andExpect(jsonPath("$.[*].crc").value(hasItem(DEFAULT_CRC)))
            .andExpect(jsonPath("$.[*].limiteEmpresas").value(hasItem(DEFAULT_LIMITE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].limiteDepartamentos").value(hasItem(DEFAULT_LIMITE_DEPARTAMENTOS)))
            .andExpect(jsonPath("$.[*].limiteFaturamento").value(hasItem(DEFAULT_LIMITE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].situacaoContador").value(hasItem(DEFAULT_SITUACAO_CONTADOR.toString())));

        // Check, that the count call also returns 1
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContadorShouldNotBeFound(String filter) throws Exception {
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContador() throws Exception {
        // Get the contador
        restContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContador() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contador
        Contador updatedContador = contadorRepository.findById(contador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContador are not directly saved in db
        em.detach(updatedContador);
        updatedContador
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .situacaoContador(UPDATED_SITUACAO_CONTADOR);

        restContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContador))
            )
            .andExpect(status().isOk());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContadorToMatchAllProperties(updatedContador);
    }

    @Test
    @Transactional
    void putNonExistingContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contador.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContadorWithPatch() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contador using partial update
        Contador partialUpdatedContador = new Contador();
        partialUpdatedContador.setId(contador.getId());

        partialUpdatedContador
            .crc(UPDATED_CRC)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO);

        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContador))
            )
            .andExpect(status().isOk());

        // Validate the Contador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContadorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedContador, contador), getPersistedContador(contador));
    }

    @Test
    @Transactional
    void fullUpdateContadorWithPatch() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contador using partial update
        Contador partialUpdatedContador = new Contador();
        partialUpdatedContador.setId(contador.getId());

        partialUpdatedContador
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .situacaoContador(UPDATED_SITUACAO_CONTADOR);

        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContador))
            )
            .andExpect(status().isOk());

        // Validate the Contador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContadorUpdatableFieldsEquals(partialUpdatedContador, getPersistedContador(partialUpdatedContador));
    }

    @Test
    @Transactional
    void patchNonExistingContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContador() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contador
        restContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, contador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contadorRepository.count();
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

    protected Contador getPersistedContador(Contador contador) {
        return contadorRepository.findById(contador.getId()).orElseThrow();
    }

    protected void assertPersistedContadorToMatchAllProperties(Contador expectedContador) {
        assertContadorAllPropertiesEquals(expectedContador, getPersistedContador(expectedContador));
    }

    protected void assertPersistedContadorToMatchUpdatableProperties(Contador expectedContador) {
        assertContadorAllUpdatablePropertiesEquals(expectedContador, getPersistedContador(expectedContador));
    }
}
