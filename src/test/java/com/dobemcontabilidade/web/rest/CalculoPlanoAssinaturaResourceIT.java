package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.CalculoPlanoAssinatura;
import com.dobemcontabilidade.domain.DescontoPlanoContaAzul;
import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.CalculoPlanoAssinaturaRepository;
import com.dobemcontabilidade.service.CalculoPlanoAssinaturaService;
import com.dobemcontabilidade.service.dto.CalculoPlanoAssinaturaDTO;
import com.dobemcontabilidade.service.mapper.CalculoPlanoAssinaturaMapper;
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
 * Integration tests for the {@link CalculoPlanoAssinaturaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CalculoPlanoAssinaturaResourceIT {

    private static final String DEFAULT_CODIGO_ATENDIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ATENDIMENTO = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR_ENQUADRAMENTO = 1D;
    private static final Double UPDATED_VALOR_ENQUADRAMENTO = 2D;
    private static final Double SMALLER_VALOR_ENQUADRAMENTO = 1D - 1D;

    private static final Double DEFAULT_VALOR_TRIBUTACAO = 1D;
    private static final Double UPDATED_VALOR_TRIBUTACAO = 2D;
    private static final Double SMALLER_VALOR_TRIBUTACAO = 1D - 1D;

    private static final Double DEFAULT_VALOR_RAMO = 1D;
    private static final Double UPDATED_VALOR_RAMO = 2D;
    private static final Double SMALLER_VALOR_RAMO = 1D - 1D;

    private static final Double DEFAULT_VALOR_FUNCIONARIOS = 1D;
    private static final Double UPDATED_VALOR_FUNCIONARIOS = 2D;
    private static final Double SMALLER_VALOR_FUNCIONARIOS = 1D - 1D;

    private static final Double DEFAULT_VALOR_SOCIOS = 1D;
    private static final Double UPDATED_VALOR_SOCIOS = 2D;
    private static final Double SMALLER_VALOR_SOCIOS = 1D - 1D;

    private static final Double DEFAULT_VALOR_FATURAMENTO = 1D;
    private static final Double UPDATED_VALOR_FATURAMENTO = 2D;
    private static final Double SMALLER_VALOR_FATURAMENTO = 1D - 1D;

    private static final Double DEFAULT_VALOR_PLANO_CONTABIL = 1D;
    private static final Double UPDATED_VALOR_PLANO_CONTABIL = 2D;
    private static final Double SMALLER_VALOR_PLANO_CONTABIL = 1D - 1D;

    private static final Double DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO = 1D;
    private static final Double UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO = 2D;
    private static final Double SMALLER_VALOR_PLANO_CONTABIL_COM_DESCONTO = 1D - 1D;

    private static final Double DEFAULT_VALOR_MENSALIDADE = 1D;
    private static final Double UPDATED_VALOR_MENSALIDADE = 2D;
    private static final Double SMALLER_VALOR_MENSALIDADE = 1D - 1D;

    private static final Double DEFAULT_VALOR_PERIODO = 1D;
    private static final Double UPDATED_VALOR_PERIODO = 2D;
    private static final Double SMALLER_VALOR_PERIODO = 1D - 1D;

    private static final Double DEFAULT_VALOR_ANO = 1D;
    private static final Double UPDATED_VALOR_ANO = 2D;
    private static final Double SMALLER_VALOR_ANO = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/calculo-plano-assinaturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository;

    @Mock
    private CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepositoryMock;

    @Autowired
    private CalculoPlanoAssinaturaMapper calculoPlanoAssinaturaMapper;

    @Mock
    private CalculoPlanoAssinaturaService calculoPlanoAssinaturaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalculoPlanoAssinaturaMockMvc;

    private CalculoPlanoAssinatura calculoPlanoAssinatura;

    private CalculoPlanoAssinatura insertedCalculoPlanoAssinatura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalculoPlanoAssinatura createEntity(EntityManager em) {
        CalculoPlanoAssinatura calculoPlanoAssinatura = new CalculoPlanoAssinatura()
            .codigoAtendimento(DEFAULT_CODIGO_ATENDIMENTO)
            .valorEnquadramento(DEFAULT_VALOR_ENQUADRAMENTO)
            .valorTributacao(DEFAULT_VALOR_TRIBUTACAO)
            .valorRamo(DEFAULT_VALOR_RAMO)
            .valorFuncionarios(DEFAULT_VALOR_FUNCIONARIOS)
            .valorSocios(DEFAULT_VALOR_SOCIOS)
            .valorFaturamento(DEFAULT_VALOR_FATURAMENTO)
            .valorPlanoContabil(DEFAULT_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorMensalidade(DEFAULT_VALOR_MENSALIDADE)
            .valorPeriodo(DEFAULT_VALOR_PERIODO)
            .valorAno(DEFAULT_VALOR_ANO);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        calculoPlanoAssinatura.setPeriodoPagamento(periodoPagamento);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        calculoPlanoAssinatura.setPlanoContabil(planoContabil);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        calculoPlanoAssinatura.setRamo(ramo);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        calculoPlanoAssinatura.setTributacao(tributacao);
        // Add required entity
        DescontoPlanoContabil descontoPlanoContabil;
        if (TestUtil.findAll(em, DescontoPlanoContabil.class).isEmpty()) {
            descontoPlanoContabil = DescontoPlanoContabilResourceIT.createEntity(em);
            em.persist(descontoPlanoContabil);
            em.flush();
        } else {
            descontoPlanoContabil = TestUtil.findAll(em, DescontoPlanoContabil.class).get(0);
        }
        calculoPlanoAssinatura.setDescontoPlanoContabil(descontoPlanoContabil);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        calculoPlanoAssinatura.setAssinaturaEmpresa(assinaturaEmpresa);
        return calculoPlanoAssinatura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalculoPlanoAssinatura createUpdatedEntity(EntityManager em) {
        CalculoPlanoAssinatura calculoPlanoAssinatura = new CalculoPlanoAssinatura()
            .codigoAtendimento(UPDATED_CODIGO_ATENDIMENTO)
            .valorEnquadramento(UPDATED_VALOR_ENQUADRAMENTO)
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorRamo(UPDATED_VALOR_RAMO)
            .valorFuncionarios(UPDATED_VALOR_FUNCIONARIOS)
            .valorSocios(UPDATED_VALOR_SOCIOS)
            .valorFaturamento(UPDATED_VALOR_FATURAMENTO)
            .valorPlanoContabil(UPDATED_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorMensalidade(UPDATED_VALOR_MENSALIDADE)
            .valorPeriodo(UPDATED_VALOR_PERIODO)
            .valorAno(UPDATED_VALOR_ANO);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createUpdatedEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        calculoPlanoAssinatura.setPeriodoPagamento(periodoPagamento);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        calculoPlanoAssinatura.setPlanoContabil(planoContabil);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createUpdatedEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        calculoPlanoAssinatura.setRamo(ramo);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createUpdatedEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        calculoPlanoAssinatura.setTributacao(tributacao);
        // Add required entity
        DescontoPlanoContabil descontoPlanoContabil;
        if (TestUtil.findAll(em, DescontoPlanoContabil.class).isEmpty()) {
            descontoPlanoContabil = DescontoPlanoContabilResourceIT.createUpdatedEntity(em);
            em.persist(descontoPlanoContabil);
            em.flush();
        } else {
            descontoPlanoContabil = TestUtil.findAll(em, DescontoPlanoContabil.class).get(0);
        }
        calculoPlanoAssinatura.setDescontoPlanoContabil(descontoPlanoContabil);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        calculoPlanoAssinatura.setAssinaturaEmpresa(assinaturaEmpresa);
        return calculoPlanoAssinatura;
    }

    @BeforeEach
    public void initTest() {
        calculoPlanoAssinatura = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCalculoPlanoAssinatura != null) {
            calculoPlanoAssinaturaRepository.delete(insertedCalculoPlanoAssinatura);
            insertedCalculoPlanoAssinatura = null;
        }
    }

    @Test
    @Transactional
    void createCalculoPlanoAssinatura() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CalculoPlanoAssinatura
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);
        var returnedCalculoPlanoAssinaturaDTO = om.readValue(
            restCalculoPlanoAssinaturaMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CalculoPlanoAssinaturaDTO.class
        );

        // Validate the CalculoPlanoAssinatura in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCalculoPlanoAssinatura = calculoPlanoAssinaturaMapper.toEntity(returnedCalculoPlanoAssinaturaDTO);
        assertCalculoPlanoAssinaturaUpdatableFieldsEquals(
            returnedCalculoPlanoAssinatura,
            getPersistedCalculoPlanoAssinatura(returnedCalculoPlanoAssinatura)
        );

        insertedCalculoPlanoAssinatura = returnedCalculoPlanoAssinatura;
    }

    @Test
    @Transactional
    void createCalculoPlanoAssinaturaWithExistingId() throws Exception {
        // Create the CalculoPlanoAssinatura with an existing ID
        calculoPlanoAssinatura.setId(1L);
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalculoPlanoAssinaturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalculoPlanoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturas() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList
        restCalculoPlanoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calculoPlanoAssinatura.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoAtendimento").value(hasItem(DEFAULT_CODIGO_ATENDIMENTO)))
            .andExpect(jsonPath("$.[*].valorEnquadramento").value(hasItem(DEFAULT_VALOR_ENQUADRAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorTributacao").value(hasItem(DEFAULT_VALOR_TRIBUTACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorRamo").value(hasItem(DEFAULT_VALOR_RAMO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorFuncionarios").value(hasItem(DEFAULT_VALOR_FUNCIONARIOS.doubleValue())))
            .andExpect(jsonPath("$.[*].valorSocios").value(hasItem(DEFAULT_VALOR_SOCIOS.doubleValue())))
            .andExpect(jsonPath("$.[*].valorFaturamento").value(hasItem(DEFAULT_VALOR_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorPlanoContabil").value(hasItem(DEFAULT_VALOR_PLANO_CONTABIL.doubleValue())))
            .andExpect(
                jsonPath("$.[*].valorPlanoContabilComDesconto").value(hasItem(DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].valorMensalidade").value(hasItem(DEFAULT_VALOR_MENSALIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valorPeriodo").value(hasItem(DEFAULT_VALOR_PERIODO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorAno").value(hasItem(DEFAULT_VALOR_ANO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCalculoPlanoAssinaturasWithEagerRelationshipsIsEnabled() throws Exception {
        when(calculoPlanoAssinaturaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCalculoPlanoAssinaturaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(calculoPlanoAssinaturaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCalculoPlanoAssinaturasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(calculoPlanoAssinaturaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCalculoPlanoAssinaturaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(calculoPlanoAssinaturaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCalculoPlanoAssinatura() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get the calculoPlanoAssinatura
        restCalculoPlanoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL_ID, calculoPlanoAssinatura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calculoPlanoAssinatura.getId().intValue()))
            .andExpect(jsonPath("$.codigoAtendimento").value(DEFAULT_CODIGO_ATENDIMENTO))
            .andExpect(jsonPath("$.valorEnquadramento").value(DEFAULT_VALOR_ENQUADRAMENTO.doubleValue()))
            .andExpect(jsonPath("$.valorTributacao").value(DEFAULT_VALOR_TRIBUTACAO.doubleValue()))
            .andExpect(jsonPath("$.valorRamo").value(DEFAULT_VALOR_RAMO.doubleValue()))
            .andExpect(jsonPath("$.valorFuncionarios").value(DEFAULT_VALOR_FUNCIONARIOS.doubleValue()))
            .andExpect(jsonPath("$.valorSocios").value(DEFAULT_VALOR_SOCIOS.doubleValue()))
            .andExpect(jsonPath("$.valorFaturamento").value(DEFAULT_VALOR_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.valorPlanoContabil").value(DEFAULT_VALOR_PLANO_CONTABIL.doubleValue()))
            .andExpect(jsonPath("$.valorPlanoContabilComDesconto").value(DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO.doubleValue()))
            .andExpect(jsonPath("$.valorMensalidade").value(DEFAULT_VALOR_MENSALIDADE.doubleValue()))
            .andExpect(jsonPath("$.valorPeriodo").value(DEFAULT_VALOR_PERIODO.doubleValue()))
            .andExpect(jsonPath("$.valorAno").value(DEFAULT_VALOR_ANO.doubleValue()));
    }

    @Test
    @Transactional
    void getCalculoPlanoAssinaturasByIdFiltering() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        Long id = calculoPlanoAssinatura.getId();

        defaultCalculoPlanoAssinaturaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCalculoPlanoAssinaturaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCalculoPlanoAssinaturaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByCodigoAtendimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where codigoAtendimento equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "codigoAtendimento.equals=" + DEFAULT_CODIGO_ATENDIMENTO,
            "codigoAtendimento.equals=" + UPDATED_CODIGO_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByCodigoAtendimentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where codigoAtendimento in
        defaultCalculoPlanoAssinaturaFiltering(
            "codigoAtendimento.in=" + DEFAULT_CODIGO_ATENDIMENTO + "," + UPDATED_CODIGO_ATENDIMENTO,
            "codigoAtendimento.in=" + UPDATED_CODIGO_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByCodigoAtendimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where codigoAtendimento is not null
        defaultCalculoPlanoAssinaturaFiltering("codigoAtendimento.specified=true", "codigoAtendimento.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByCodigoAtendimentoContainsSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where codigoAtendimento contains
        defaultCalculoPlanoAssinaturaFiltering(
            "codigoAtendimento.contains=" + DEFAULT_CODIGO_ATENDIMENTO,
            "codigoAtendimento.contains=" + UPDATED_CODIGO_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByCodigoAtendimentoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where codigoAtendimento does not contain
        defaultCalculoPlanoAssinaturaFiltering(
            "codigoAtendimento.doesNotContain=" + UPDATED_CODIGO_ATENDIMENTO,
            "codigoAtendimento.doesNotContain=" + DEFAULT_CODIGO_ATENDIMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorEnquadramentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorEnquadramento equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorEnquadramento.equals=" + DEFAULT_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.equals=" + UPDATED_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorEnquadramentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorEnquadramento in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorEnquadramento.in=" + DEFAULT_VALOR_ENQUADRAMENTO + "," + UPDATED_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.in=" + UPDATED_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorEnquadramentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorEnquadramento is not null
        defaultCalculoPlanoAssinaturaFiltering("valorEnquadramento.specified=true", "valorEnquadramento.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorEnquadramentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorEnquadramento is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorEnquadramento.greaterThanOrEqual=" + DEFAULT_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.greaterThanOrEqual=" + UPDATED_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorEnquadramentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorEnquadramento is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorEnquadramento.lessThanOrEqual=" + DEFAULT_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.lessThanOrEqual=" + SMALLER_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorEnquadramentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorEnquadramento is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorEnquadramento.lessThan=" + UPDATED_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.lessThan=" + DEFAULT_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorEnquadramentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorEnquadramento is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorEnquadramento.greaterThan=" + SMALLER_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.greaterThan=" + DEFAULT_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorTributacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorTributacao equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorTributacao.equals=" + DEFAULT_VALOR_TRIBUTACAO,
            "valorTributacao.equals=" + UPDATED_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorTributacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorTributacao in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorTributacao.in=" + DEFAULT_VALOR_TRIBUTACAO + "," + UPDATED_VALOR_TRIBUTACAO,
            "valorTributacao.in=" + UPDATED_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorTributacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorTributacao is not null
        defaultCalculoPlanoAssinaturaFiltering("valorTributacao.specified=true", "valorTributacao.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorTributacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorTributacao is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorTributacao.greaterThanOrEqual=" + DEFAULT_VALOR_TRIBUTACAO,
            "valorTributacao.greaterThanOrEqual=" + UPDATED_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorTributacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorTributacao is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorTributacao.lessThanOrEqual=" + DEFAULT_VALOR_TRIBUTACAO,
            "valorTributacao.lessThanOrEqual=" + SMALLER_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorTributacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorTributacao is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorTributacao.lessThan=" + UPDATED_VALOR_TRIBUTACAO,
            "valorTributacao.lessThan=" + DEFAULT_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorTributacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorTributacao is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorTributacao.greaterThan=" + SMALLER_VALOR_TRIBUTACAO,
            "valorTributacao.greaterThan=" + DEFAULT_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorRamoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorRamo equals to
        defaultCalculoPlanoAssinaturaFiltering("valorRamo.equals=" + DEFAULT_VALOR_RAMO, "valorRamo.equals=" + UPDATED_VALOR_RAMO);
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorRamoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorRamo in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorRamo.in=" + DEFAULT_VALOR_RAMO + "," + UPDATED_VALOR_RAMO,
            "valorRamo.in=" + UPDATED_VALOR_RAMO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorRamoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorRamo is not null
        defaultCalculoPlanoAssinaturaFiltering("valorRamo.specified=true", "valorRamo.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorRamoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorRamo is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorRamo.greaterThanOrEqual=" + DEFAULT_VALOR_RAMO,
            "valorRamo.greaterThanOrEqual=" + UPDATED_VALOR_RAMO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorRamoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorRamo is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorRamo.lessThanOrEqual=" + DEFAULT_VALOR_RAMO,
            "valorRamo.lessThanOrEqual=" + SMALLER_VALOR_RAMO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorRamoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorRamo is less than
        defaultCalculoPlanoAssinaturaFiltering("valorRamo.lessThan=" + UPDATED_VALOR_RAMO, "valorRamo.lessThan=" + DEFAULT_VALOR_RAMO);
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorRamoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorRamo is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorRamo.greaterThan=" + SMALLER_VALOR_RAMO,
            "valorRamo.greaterThan=" + DEFAULT_VALOR_RAMO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFuncionariosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFuncionarios equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFuncionarios.equals=" + DEFAULT_VALOR_FUNCIONARIOS,
            "valorFuncionarios.equals=" + UPDATED_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFuncionariosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFuncionarios in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFuncionarios.in=" + DEFAULT_VALOR_FUNCIONARIOS + "," + UPDATED_VALOR_FUNCIONARIOS,
            "valorFuncionarios.in=" + UPDATED_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFuncionariosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFuncionarios is not null
        defaultCalculoPlanoAssinaturaFiltering("valorFuncionarios.specified=true", "valorFuncionarios.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFuncionariosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFuncionarios is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFuncionarios.greaterThanOrEqual=" + DEFAULT_VALOR_FUNCIONARIOS,
            "valorFuncionarios.greaterThanOrEqual=" + UPDATED_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFuncionariosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFuncionarios is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFuncionarios.lessThanOrEqual=" + DEFAULT_VALOR_FUNCIONARIOS,
            "valorFuncionarios.lessThanOrEqual=" + SMALLER_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFuncionariosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFuncionarios is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFuncionarios.lessThan=" + UPDATED_VALOR_FUNCIONARIOS,
            "valorFuncionarios.lessThan=" + DEFAULT_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFuncionariosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFuncionarios is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFuncionarios.greaterThan=" + SMALLER_VALOR_FUNCIONARIOS,
            "valorFuncionarios.greaterThan=" + DEFAULT_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorSociosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorSocios equals to
        defaultCalculoPlanoAssinaturaFiltering("valorSocios.equals=" + DEFAULT_VALOR_SOCIOS, "valorSocios.equals=" + UPDATED_VALOR_SOCIOS);
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorSociosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorSocios in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorSocios.in=" + DEFAULT_VALOR_SOCIOS + "," + UPDATED_VALOR_SOCIOS,
            "valorSocios.in=" + UPDATED_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorSociosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorSocios is not null
        defaultCalculoPlanoAssinaturaFiltering("valorSocios.specified=true", "valorSocios.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorSociosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorSocios is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorSocios.greaterThanOrEqual=" + DEFAULT_VALOR_SOCIOS,
            "valorSocios.greaterThanOrEqual=" + UPDATED_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorSociosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorSocios is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorSocios.lessThanOrEqual=" + DEFAULT_VALOR_SOCIOS,
            "valorSocios.lessThanOrEqual=" + SMALLER_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorSociosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorSocios is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorSocios.lessThan=" + UPDATED_VALOR_SOCIOS,
            "valorSocios.lessThan=" + DEFAULT_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorSociosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorSocios is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorSocios.greaterThan=" + SMALLER_VALOR_SOCIOS,
            "valorSocios.greaterThan=" + DEFAULT_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFaturamento equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFaturamento.equals=" + DEFAULT_VALOR_FATURAMENTO,
            "valorFaturamento.equals=" + UPDATED_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFaturamento in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFaturamento.in=" + DEFAULT_VALOR_FATURAMENTO + "," + UPDATED_VALOR_FATURAMENTO,
            "valorFaturamento.in=" + UPDATED_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFaturamento is not null
        defaultCalculoPlanoAssinaturaFiltering("valorFaturamento.specified=true", "valorFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFaturamento is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFaturamento.greaterThanOrEqual=" + DEFAULT_VALOR_FATURAMENTO,
            "valorFaturamento.greaterThanOrEqual=" + UPDATED_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFaturamento is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFaturamento.lessThanOrEqual=" + DEFAULT_VALOR_FATURAMENTO,
            "valorFaturamento.lessThanOrEqual=" + SMALLER_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFaturamento is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFaturamento.lessThan=" + UPDATED_VALOR_FATURAMENTO,
            "valorFaturamento.lessThan=" + DEFAULT_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorFaturamento is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorFaturamento.greaterThan=" + SMALLER_VALOR_FATURAMENTO,
            "valorFaturamento.greaterThan=" + DEFAULT_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabil equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabil.equals=" + DEFAULT_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.equals=" + UPDATED_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabil in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabil.in=" + DEFAULT_VALOR_PLANO_CONTABIL + "," + UPDATED_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.in=" + UPDATED_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabil is not null
        defaultCalculoPlanoAssinaturaFiltering("valorPlanoContabil.specified=true", "valorPlanoContabil.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabil is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabil.greaterThanOrEqual=" + DEFAULT_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.greaterThanOrEqual=" + UPDATED_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabil is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabil.lessThanOrEqual=" + DEFAULT_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.lessThanOrEqual=" + SMALLER_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabil is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabil.lessThan=" + UPDATED_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.lessThan=" + DEFAULT_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabil is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabil.greaterThan=" + SMALLER_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.greaterThan=" + DEFAULT_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilComDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabilComDesconto equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabilComDesconto.equals=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.equals=" + UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilComDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabilComDesconto in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabilComDesconto.in=" +
            DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO +
            "," +
            UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.in=" + UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilComDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabilComDesconto is not null
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabilComDesconto.specified=true",
            "valorPlanoContabilComDesconto.specified=false"
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilComDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabilComDesconto is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabilComDesconto.greaterThanOrEqual=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.greaterThanOrEqual=" + UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilComDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabilComDesconto is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabilComDesconto.lessThanOrEqual=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.lessThanOrEqual=" + SMALLER_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilComDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabilComDesconto is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabilComDesconto.lessThan=" + UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.lessThan=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPlanoContabilComDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPlanoContabilComDesconto is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPlanoContabilComDesconto.greaterThan=" + SMALLER_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.greaterThan=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorMensalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorMensalidade equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorMensalidade.equals=" + DEFAULT_VALOR_MENSALIDADE,
            "valorMensalidade.equals=" + UPDATED_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorMensalidadeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorMensalidade in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorMensalidade.in=" + DEFAULT_VALOR_MENSALIDADE + "," + UPDATED_VALOR_MENSALIDADE,
            "valorMensalidade.in=" + UPDATED_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorMensalidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorMensalidade is not null
        defaultCalculoPlanoAssinaturaFiltering("valorMensalidade.specified=true", "valorMensalidade.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorMensalidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorMensalidade is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorMensalidade.greaterThanOrEqual=" + DEFAULT_VALOR_MENSALIDADE,
            "valorMensalidade.greaterThanOrEqual=" + UPDATED_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorMensalidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorMensalidade is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorMensalidade.lessThanOrEqual=" + DEFAULT_VALOR_MENSALIDADE,
            "valorMensalidade.lessThanOrEqual=" + SMALLER_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorMensalidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorMensalidade is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorMensalidade.lessThan=" + UPDATED_VALOR_MENSALIDADE,
            "valorMensalidade.lessThan=" + DEFAULT_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorMensalidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorMensalidade is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorMensalidade.greaterThan=" + SMALLER_VALOR_MENSALIDADE,
            "valorMensalidade.greaterThan=" + DEFAULT_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPeriodo equals to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPeriodo.equals=" + DEFAULT_VALOR_PERIODO,
            "valorPeriodo.equals=" + UPDATED_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPeriodo in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPeriodo.in=" + DEFAULT_VALOR_PERIODO + "," + UPDATED_VALOR_PERIODO,
            "valorPeriodo.in=" + UPDATED_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPeriodo is not null
        defaultCalculoPlanoAssinaturaFiltering("valorPeriodo.specified=true", "valorPeriodo.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPeriodoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPeriodo is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPeriodo.greaterThanOrEqual=" + DEFAULT_VALOR_PERIODO,
            "valorPeriodo.greaterThanOrEqual=" + UPDATED_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPeriodoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPeriodo is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPeriodo.lessThanOrEqual=" + DEFAULT_VALOR_PERIODO,
            "valorPeriodo.lessThanOrEqual=" + SMALLER_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPeriodoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPeriodo is less than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPeriodo.lessThan=" + UPDATED_VALOR_PERIODO,
            "valorPeriodo.lessThan=" + DEFAULT_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorPeriodoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorPeriodo is greater than
        defaultCalculoPlanoAssinaturaFiltering(
            "valorPeriodo.greaterThan=" + SMALLER_VALOR_PERIODO,
            "valorPeriodo.greaterThan=" + DEFAULT_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorAnoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorAno equals to
        defaultCalculoPlanoAssinaturaFiltering("valorAno.equals=" + DEFAULT_VALOR_ANO, "valorAno.equals=" + UPDATED_VALOR_ANO);
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorAnoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorAno in
        defaultCalculoPlanoAssinaturaFiltering(
            "valorAno.in=" + DEFAULT_VALOR_ANO + "," + UPDATED_VALOR_ANO,
            "valorAno.in=" + UPDATED_VALOR_ANO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorAnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorAno is not null
        defaultCalculoPlanoAssinaturaFiltering("valorAno.specified=true", "valorAno.specified=false");
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorAnoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorAno is greater than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorAno.greaterThanOrEqual=" + DEFAULT_VALOR_ANO,
            "valorAno.greaterThanOrEqual=" + UPDATED_VALOR_ANO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorAnoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorAno is less than or equal to
        defaultCalculoPlanoAssinaturaFiltering(
            "valorAno.lessThanOrEqual=" + DEFAULT_VALOR_ANO,
            "valorAno.lessThanOrEqual=" + SMALLER_VALOR_ANO
        );
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorAnoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorAno is less than
        defaultCalculoPlanoAssinaturaFiltering("valorAno.lessThan=" + UPDATED_VALOR_ANO, "valorAno.lessThan=" + DEFAULT_VALOR_ANO);
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByValorAnoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        // Get all the calculoPlanoAssinaturaList where valorAno is greater than
        defaultCalculoPlanoAssinaturaFiltering("valorAno.greaterThan=" + SMALLER_VALOR_ANO, "valorAno.greaterThan=" + DEFAULT_VALOR_ANO);
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByPeriodoPagamentoIsEqualToSomething() throws Exception {
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        em.persist(periodoPagamento);
        em.flush();
        calculoPlanoAssinatura.setPeriodoPagamento(periodoPagamento);
        calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
        Long periodoPagamentoId = periodoPagamento.getId();
        // Get all the calculoPlanoAssinaturaList where periodoPagamento equals to periodoPagamentoId
        defaultCalculoPlanoAssinaturaShouldBeFound("periodoPagamentoId.equals=" + periodoPagamentoId);

        // Get all the calculoPlanoAssinaturaList where periodoPagamento equals to (periodoPagamentoId + 1)
        defaultCalculoPlanoAssinaturaShouldNotBeFound("periodoPagamentoId.equals=" + (periodoPagamentoId + 1));
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByPlanoContabilIsEqualToSomething() throws Exception {
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
            planoContabil = PlanoContabilResourceIT.createEntity(em);
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        em.persist(planoContabil);
        em.flush();
        calculoPlanoAssinatura.setPlanoContabil(planoContabil);
        calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
        Long planoContabilId = planoContabil.getId();
        // Get all the calculoPlanoAssinaturaList where planoContabil equals to planoContabilId
        defaultCalculoPlanoAssinaturaShouldBeFound("planoContabilId.equals=" + planoContabilId);

        // Get all the calculoPlanoAssinaturaList where planoContabil equals to (planoContabilId + 1)
        defaultCalculoPlanoAssinaturaShouldNotBeFound("planoContabilId.equals=" + (planoContabilId + 1));
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByRamoIsEqualToSomething() throws Exception {
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
            ramo = RamoResourceIT.createEntity(em);
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        em.persist(ramo);
        em.flush();
        calculoPlanoAssinatura.setRamo(ramo);
        calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
        Long ramoId = ramo.getId();
        // Get all the calculoPlanoAssinaturaList where ramo equals to ramoId
        defaultCalculoPlanoAssinaturaShouldBeFound("ramoId.equals=" + ramoId);

        // Get all the calculoPlanoAssinaturaList where ramo equals to (ramoId + 1)
        defaultCalculoPlanoAssinaturaShouldNotBeFound("ramoId.equals=" + (ramoId + 1));
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByTributacaoIsEqualToSomething() throws Exception {
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
            tributacao = TributacaoResourceIT.createEntity(em);
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        em.persist(tributacao);
        em.flush();
        calculoPlanoAssinatura.setTributacao(tributacao);
        calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
        Long tributacaoId = tributacao.getId();
        // Get all the calculoPlanoAssinaturaList where tributacao equals to tributacaoId
        defaultCalculoPlanoAssinaturaShouldBeFound("tributacaoId.equals=" + tributacaoId);

        // Get all the calculoPlanoAssinaturaList where tributacao equals to (tributacaoId + 1)
        defaultCalculoPlanoAssinaturaShouldNotBeFound("tributacaoId.equals=" + (tributacaoId + 1));
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByDescontoPlanoContabilIsEqualToSomething() throws Exception {
        DescontoPlanoContabil descontoPlanoContabil;
        if (TestUtil.findAll(em, DescontoPlanoContabil.class).isEmpty()) {
            calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
            descontoPlanoContabil = DescontoPlanoContabilResourceIT.createEntity(em);
        } else {
            descontoPlanoContabil = TestUtil.findAll(em, DescontoPlanoContabil.class).get(0);
        }
        em.persist(descontoPlanoContabil);
        em.flush();
        calculoPlanoAssinatura.setDescontoPlanoContabil(descontoPlanoContabil);
        calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
        Long descontoPlanoContabilId = descontoPlanoContabil.getId();
        // Get all the calculoPlanoAssinaturaList where descontoPlanoContabil equals to descontoPlanoContabilId
        defaultCalculoPlanoAssinaturaShouldBeFound("descontoPlanoContabilId.equals=" + descontoPlanoContabilId);

        // Get all the calculoPlanoAssinaturaList where descontoPlanoContabil equals to (descontoPlanoContabilId + 1)
        defaultCalculoPlanoAssinaturaShouldNotBeFound("descontoPlanoContabilId.equals=" + (descontoPlanoContabilId + 1));
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByAssinaturaEmpresaIsEqualToSomething() throws Exception {
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        em.persist(assinaturaEmpresa);
        em.flush();
        calculoPlanoAssinatura.setAssinaturaEmpresa(assinaturaEmpresa);
        calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
        Long assinaturaEmpresaId = assinaturaEmpresa.getId();
        // Get all the calculoPlanoAssinaturaList where assinaturaEmpresa equals to assinaturaEmpresaId
        defaultCalculoPlanoAssinaturaShouldBeFound("assinaturaEmpresaId.equals=" + assinaturaEmpresaId);

        // Get all the calculoPlanoAssinaturaList where assinaturaEmpresa equals to (assinaturaEmpresaId + 1)
        defaultCalculoPlanoAssinaturaShouldNotBeFound("assinaturaEmpresaId.equals=" + (assinaturaEmpresaId + 1));
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByDescontoPlanoContaAzulIsEqualToSomething() throws Exception {
        DescontoPlanoContaAzul descontoPlanoContaAzul;
        if (TestUtil.findAll(em, DescontoPlanoContaAzul.class).isEmpty()) {
            calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
            descontoPlanoContaAzul = DescontoPlanoContaAzulResourceIT.createEntity(em);
        } else {
            descontoPlanoContaAzul = TestUtil.findAll(em, DescontoPlanoContaAzul.class).get(0);
        }
        em.persist(descontoPlanoContaAzul);
        em.flush();
        calculoPlanoAssinatura.setDescontoPlanoContaAzul(descontoPlanoContaAzul);
        calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
        Long descontoPlanoContaAzulId = descontoPlanoContaAzul.getId();
        // Get all the calculoPlanoAssinaturaList where descontoPlanoContaAzul equals to descontoPlanoContaAzulId
        defaultCalculoPlanoAssinaturaShouldBeFound("descontoPlanoContaAzulId.equals=" + descontoPlanoContaAzulId);

        // Get all the calculoPlanoAssinaturaList where descontoPlanoContaAzul equals to (descontoPlanoContaAzulId + 1)
        defaultCalculoPlanoAssinaturaShouldNotBeFound("descontoPlanoContaAzulId.equals=" + (descontoPlanoContaAzulId + 1));
    }

    @Test
    @Transactional
    void getAllCalculoPlanoAssinaturasByPlanoContaAzulIsEqualToSomething() throws Exception {
        PlanoContaAzul planoContaAzul;
        if (TestUtil.findAll(em, PlanoContaAzul.class).isEmpty()) {
            calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
            planoContaAzul = PlanoContaAzulResourceIT.createEntity(em);
        } else {
            planoContaAzul = TestUtil.findAll(em, PlanoContaAzul.class).get(0);
        }
        em.persist(planoContaAzul);
        em.flush();
        calculoPlanoAssinatura.setPlanoContaAzul(planoContaAzul);
        calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);
        Long planoContaAzulId = planoContaAzul.getId();
        // Get all the calculoPlanoAssinaturaList where planoContaAzul equals to planoContaAzulId
        defaultCalculoPlanoAssinaturaShouldBeFound("planoContaAzulId.equals=" + planoContaAzulId);

        // Get all the calculoPlanoAssinaturaList where planoContaAzul equals to (planoContaAzulId + 1)
        defaultCalculoPlanoAssinaturaShouldNotBeFound("planoContaAzulId.equals=" + (planoContaAzulId + 1));
    }

    private void defaultCalculoPlanoAssinaturaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCalculoPlanoAssinaturaShouldBeFound(shouldBeFound);
        defaultCalculoPlanoAssinaturaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCalculoPlanoAssinaturaShouldBeFound(String filter) throws Exception {
        restCalculoPlanoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calculoPlanoAssinatura.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoAtendimento").value(hasItem(DEFAULT_CODIGO_ATENDIMENTO)))
            .andExpect(jsonPath("$.[*].valorEnquadramento").value(hasItem(DEFAULT_VALOR_ENQUADRAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorTributacao").value(hasItem(DEFAULT_VALOR_TRIBUTACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorRamo").value(hasItem(DEFAULT_VALOR_RAMO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorFuncionarios").value(hasItem(DEFAULT_VALOR_FUNCIONARIOS.doubleValue())))
            .andExpect(jsonPath("$.[*].valorSocios").value(hasItem(DEFAULT_VALOR_SOCIOS.doubleValue())))
            .andExpect(jsonPath("$.[*].valorFaturamento").value(hasItem(DEFAULT_VALOR_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorPlanoContabil").value(hasItem(DEFAULT_VALOR_PLANO_CONTABIL.doubleValue())))
            .andExpect(
                jsonPath("$.[*].valorPlanoContabilComDesconto").value(hasItem(DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO.doubleValue()))
            )
            .andExpect(jsonPath("$.[*].valorMensalidade").value(hasItem(DEFAULT_VALOR_MENSALIDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].valorPeriodo").value(hasItem(DEFAULT_VALOR_PERIODO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorAno").value(hasItem(DEFAULT_VALOR_ANO.doubleValue())));

        // Check, that the count call also returns 1
        restCalculoPlanoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCalculoPlanoAssinaturaShouldNotBeFound(String filter) throws Exception {
        restCalculoPlanoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCalculoPlanoAssinaturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCalculoPlanoAssinatura() throws Exception {
        // Get the calculoPlanoAssinatura
        restCalculoPlanoAssinaturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCalculoPlanoAssinatura() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calculoPlanoAssinatura
        CalculoPlanoAssinatura updatedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository
            .findById(calculoPlanoAssinatura.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCalculoPlanoAssinatura are not directly saved in db
        em.detach(updatedCalculoPlanoAssinatura);
        updatedCalculoPlanoAssinatura
            .codigoAtendimento(UPDATED_CODIGO_ATENDIMENTO)
            .valorEnquadramento(UPDATED_VALOR_ENQUADRAMENTO)
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorRamo(UPDATED_VALOR_RAMO)
            .valorFuncionarios(UPDATED_VALOR_FUNCIONARIOS)
            .valorSocios(UPDATED_VALOR_SOCIOS)
            .valorFaturamento(UPDATED_VALOR_FATURAMENTO)
            .valorPlanoContabil(UPDATED_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorMensalidade(UPDATED_VALOR_MENSALIDADE)
            .valorPeriodo(UPDATED_VALOR_PERIODO)
            .valorAno(UPDATED_VALOR_ANO);
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(updatedCalculoPlanoAssinatura);

        restCalculoPlanoAssinaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calculoPlanoAssinaturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO))
            )
            .andExpect(status().isOk());

        // Validate the CalculoPlanoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCalculoPlanoAssinaturaToMatchAllProperties(updatedCalculoPlanoAssinatura);
    }

    @Test
    @Transactional
    void putNonExistingCalculoPlanoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calculoPlanoAssinatura.setId(longCount.incrementAndGet());

        // Create the CalculoPlanoAssinatura
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalculoPlanoAssinaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calculoPlanoAssinaturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalculoPlanoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCalculoPlanoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calculoPlanoAssinatura.setId(longCount.incrementAndGet());

        // Create the CalculoPlanoAssinatura
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalculoPlanoAssinaturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalculoPlanoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCalculoPlanoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calculoPlanoAssinatura.setId(longCount.incrementAndGet());

        // Create the CalculoPlanoAssinatura
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalculoPlanoAssinaturaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalculoPlanoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCalculoPlanoAssinaturaWithPatch() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calculoPlanoAssinatura using partial update
        CalculoPlanoAssinatura partialUpdatedCalculoPlanoAssinatura = new CalculoPlanoAssinatura();
        partialUpdatedCalculoPlanoAssinatura.setId(calculoPlanoAssinatura.getId());

        partialUpdatedCalculoPlanoAssinatura
            .codigoAtendimento(UPDATED_CODIGO_ATENDIMENTO)
            .valorEnquadramento(UPDATED_VALOR_ENQUADRAMENTO)
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorSocios(UPDATED_VALOR_SOCIOS)
            .valorMensalidade(UPDATED_VALOR_MENSALIDADE)
            .valorAno(UPDATED_VALOR_ANO);

        restCalculoPlanoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalculoPlanoAssinatura.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCalculoPlanoAssinatura))
            )
            .andExpect(status().isOk());

        // Validate the CalculoPlanoAssinatura in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCalculoPlanoAssinaturaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCalculoPlanoAssinatura, calculoPlanoAssinatura),
            getPersistedCalculoPlanoAssinatura(calculoPlanoAssinatura)
        );
    }

    @Test
    @Transactional
    void fullUpdateCalculoPlanoAssinaturaWithPatch() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the calculoPlanoAssinatura using partial update
        CalculoPlanoAssinatura partialUpdatedCalculoPlanoAssinatura = new CalculoPlanoAssinatura();
        partialUpdatedCalculoPlanoAssinatura.setId(calculoPlanoAssinatura.getId());

        partialUpdatedCalculoPlanoAssinatura
            .codigoAtendimento(UPDATED_CODIGO_ATENDIMENTO)
            .valorEnquadramento(UPDATED_VALOR_ENQUADRAMENTO)
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorRamo(UPDATED_VALOR_RAMO)
            .valorFuncionarios(UPDATED_VALOR_FUNCIONARIOS)
            .valorSocios(UPDATED_VALOR_SOCIOS)
            .valorFaturamento(UPDATED_VALOR_FATURAMENTO)
            .valorPlanoContabil(UPDATED_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorMensalidade(UPDATED_VALOR_MENSALIDADE)
            .valorPeriodo(UPDATED_VALOR_PERIODO)
            .valorAno(UPDATED_VALOR_ANO);

        restCalculoPlanoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalculoPlanoAssinatura.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCalculoPlanoAssinatura))
            )
            .andExpect(status().isOk());

        // Validate the CalculoPlanoAssinatura in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCalculoPlanoAssinaturaUpdatableFieldsEquals(
            partialUpdatedCalculoPlanoAssinatura,
            getPersistedCalculoPlanoAssinatura(partialUpdatedCalculoPlanoAssinatura)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCalculoPlanoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calculoPlanoAssinatura.setId(longCount.incrementAndGet());

        // Create the CalculoPlanoAssinatura
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalculoPlanoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calculoPlanoAssinaturaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalculoPlanoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCalculoPlanoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calculoPlanoAssinatura.setId(longCount.incrementAndGet());

        // Create the CalculoPlanoAssinatura
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalculoPlanoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalculoPlanoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCalculoPlanoAssinatura() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        calculoPlanoAssinatura.setId(longCount.incrementAndGet());

        // Create the CalculoPlanoAssinatura
        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalculoPlanoAssinaturaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(calculoPlanoAssinaturaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalculoPlanoAssinatura in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCalculoPlanoAssinatura() throws Exception {
        // Initialize the database
        insertedCalculoPlanoAssinatura = calculoPlanoAssinaturaRepository.saveAndFlush(calculoPlanoAssinatura);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the calculoPlanoAssinatura
        restCalculoPlanoAssinaturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, calculoPlanoAssinatura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return calculoPlanoAssinaturaRepository.count();
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

    protected CalculoPlanoAssinatura getPersistedCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        return calculoPlanoAssinaturaRepository.findById(calculoPlanoAssinatura.getId()).orElseThrow();
    }

    protected void assertPersistedCalculoPlanoAssinaturaToMatchAllProperties(CalculoPlanoAssinatura expectedCalculoPlanoAssinatura) {
        assertCalculoPlanoAssinaturaAllPropertiesEquals(
            expectedCalculoPlanoAssinatura,
            getPersistedCalculoPlanoAssinatura(expectedCalculoPlanoAssinatura)
        );
    }

    protected void assertPersistedCalculoPlanoAssinaturaToMatchUpdatableProperties(CalculoPlanoAssinatura expectedCalculoPlanoAssinatura) {
        assertCalculoPlanoAssinaturaAllUpdatablePropertiesEquals(
            expectedCalculoPlanoAssinatura,
            getPersistedCalculoPlanoAssinatura(expectedCalculoPlanoAssinatura)
        );
    }
}
