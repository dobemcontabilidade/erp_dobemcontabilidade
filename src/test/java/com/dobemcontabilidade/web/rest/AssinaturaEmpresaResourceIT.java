package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.enumeration.SituacaoContratoEmpresaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoContratoEnum;
import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.AssinaturaEmpresaService;
import com.dobemcontabilidade.service.dto.AssinaturaEmpresaDTO;
import com.dobemcontabilidade.service.mapper.AssinaturaEmpresaMapper;
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
 * Integration tests for the {@link AssinaturaEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssinaturaEmpresaResourceIT {

    private static final String DEFAULT_CODIGO_ASSINATURA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ASSINATURA = "BBBBBBBBBB";

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

    private static final Instant DEFAULT_DATA_CONTRATACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONTRATACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ENCERRAMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ENCERRAMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DIA_VENCIMENTO = 1;
    private static final Integer UPDATED_DIA_VENCIMENTO = 2;
    private static final Integer SMALLER_DIA_VENCIMENTO = 1 - 1;

    private static final SituacaoContratoEmpresaEnum DEFAULT_SITUACAO = SituacaoContratoEmpresaEnum.PROPOSTA;
    private static final SituacaoContratoEmpresaEnum UPDATED_SITUACAO = SituacaoContratoEmpresaEnum.ATIVO;

    private static final TipoContratoEnum DEFAULT_TIPO_CONTRATO = TipoContratoEnum.ABERTURA_EMPRESA;
    private static final TipoContratoEnum UPDATED_TIPO_CONTRATO = TipoContratoEnum.CONTRATACAO_PLANO;

    private static final String ENTITY_API_URL = "/api/assinatura-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssinaturaEmpresaRepository assinaturaEmpresaRepository;

    @Mock
    private AssinaturaEmpresaRepository assinaturaEmpresaRepositoryMock;

    @Autowired
    private AssinaturaEmpresaMapper assinaturaEmpresaMapper;

    @Mock
    private AssinaturaEmpresaService assinaturaEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssinaturaEmpresaMockMvc;

    private AssinaturaEmpresa assinaturaEmpresa;

    private AssinaturaEmpresa insertedAssinaturaEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssinaturaEmpresa createEntity(EntityManager em) {
        AssinaturaEmpresa assinaturaEmpresa = new AssinaturaEmpresa()
            .codigoAssinatura(DEFAULT_CODIGO_ASSINATURA)
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
            .valorAno(DEFAULT_VALOR_ANO)
            .dataContratacao(DEFAULT_DATA_CONTRATACAO)
            .dataEncerramento(DEFAULT_DATA_ENCERRAMENTO)
            .diaVencimento(DEFAULT_DIA_VENCIMENTO)
            .situacao(DEFAULT_SITUACAO)
            .tipoContrato(DEFAULT_TIPO_CONTRATO);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        assinaturaEmpresa.setPeriodoPagamento(periodoPagamento);
        // Add required entity
        FormaDePagamento formaDePagamento;
        if (TestUtil.findAll(em, FormaDePagamento.class).isEmpty()) {
            formaDePagamento = FormaDePagamentoResourceIT.createEntity(em);
            em.persist(formaDePagamento);
            em.flush();
        } else {
            formaDePagamento = TestUtil.findAll(em, FormaDePagamento.class).get(0);
        }
        assinaturaEmpresa.setFormaDePagamento(formaDePagamento);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        assinaturaEmpresa.setPlanoContabil(planoContabil);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        assinaturaEmpresa.setEmpresa(empresa);
        return assinaturaEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssinaturaEmpresa createUpdatedEntity(EntityManager em) {
        AssinaturaEmpresa assinaturaEmpresa = new AssinaturaEmpresa()
            .codigoAssinatura(UPDATED_CODIGO_ASSINATURA)
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
            .valorAno(UPDATED_VALOR_ANO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .dataEncerramento(UPDATED_DATA_ENCERRAMENTO)
            .diaVencimento(UPDATED_DIA_VENCIMENTO)
            .situacao(UPDATED_SITUACAO)
            .tipoContrato(UPDATED_TIPO_CONTRATO);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createUpdatedEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        assinaturaEmpresa.setPeriodoPagamento(periodoPagamento);
        // Add required entity
        FormaDePagamento formaDePagamento;
        if (TestUtil.findAll(em, FormaDePagamento.class).isEmpty()) {
            formaDePagamento = FormaDePagamentoResourceIT.createUpdatedEntity(em);
            em.persist(formaDePagamento);
            em.flush();
        } else {
            formaDePagamento = TestUtil.findAll(em, FormaDePagamento.class).get(0);
        }
        assinaturaEmpresa.setFormaDePagamento(formaDePagamento);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        assinaturaEmpresa.setPlanoContabil(planoContabil);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        assinaturaEmpresa.setEmpresa(empresa);
        return assinaturaEmpresa;
    }

    @BeforeEach
    public void initTest() {
        assinaturaEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAssinaturaEmpresa != null) {
            assinaturaEmpresaRepository.delete(insertedAssinaturaEmpresa);
            insertedAssinaturaEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AssinaturaEmpresa
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(assinaturaEmpresa);
        var returnedAssinaturaEmpresaDTO = om.readValue(
            restAssinaturaEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assinaturaEmpresaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssinaturaEmpresaDTO.class
        );

        // Validate the AssinaturaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAssinaturaEmpresa = assinaturaEmpresaMapper.toEntity(returnedAssinaturaEmpresaDTO);
        assertAssinaturaEmpresaUpdatableFieldsEquals(returnedAssinaturaEmpresa, getPersistedAssinaturaEmpresa(returnedAssinaturaEmpresa));

        insertedAssinaturaEmpresa = returnedAssinaturaEmpresa;
    }

    @Test
    @Transactional
    void createAssinaturaEmpresaWithExistingId() throws Exception {
        // Create the AssinaturaEmpresa with an existing ID
        assinaturaEmpresa.setId(1L);
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(assinaturaEmpresa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssinaturaEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assinaturaEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresas() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList
        restAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assinaturaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoAssinatura").value(hasItem(DEFAULT_CODIGO_ASSINATURA)))
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
            .andExpect(jsonPath("$.[*].valorAno").value(hasItem(DEFAULT_VALOR_ANO.doubleValue())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].dataEncerramento").value(hasItem(DEFAULT_DATA_ENCERRAMENTO.toString())))
            .andExpect(jsonPath("$.[*].diaVencimento").value(hasItem(DEFAULT_DIA_VENCIMENTO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].tipoContrato").value(hasItem(DEFAULT_TIPO_CONTRATO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssinaturaEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(assinaturaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assinaturaEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssinaturaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assinaturaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(assinaturaEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get the assinaturaEmpresa
        restAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, assinaturaEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assinaturaEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.codigoAssinatura").value(DEFAULT_CODIGO_ASSINATURA))
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
            .andExpect(jsonPath("$.valorAno").value(DEFAULT_VALOR_ANO.doubleValue()))
            .andExpect(jsonPath("$.dataContratacao").value(DEFAULT_DATA_CONTRATACAO.toString()))
            .andExpect(jsonPath("$.dataEncerramento").value(DEFAULT_DATA_ENCERRAMENTO.toString()))
            .andExpect(jsonPath("$.diaVencimento").value(DEFAULT_DIA_VENCIMENTO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.tipoContrato").value(DEFAULT_TIPO_CONTRATO.toString()));
    }

    @Test
    @Transactional
    void getAssinaturaEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        Long id = assinaturaEmpresa.getId();

        defaultAssinaturaEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAssinaturaEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAssinaturaEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByCodigoAssinaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where codigoAssinatura equals to
        defaultAssinaturaEmpresaFiltering(
            "codigoAssinatura.equals=" + DEFAULT_CODIGO_ASSINATURA,
            "codigoAssinatura.equals=" + UPDATED_CODIGO_ASSINATURA
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByCodigoAssinaturaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where codigoAssinatura in
        defaultAssinaturaEmpresaFiltering(
            "codigoAssinatura.in=" + DEFAULT_CODIGO_ASSINATURA + "," + UPDATED_CODIGO_ASSINATURA,
            "codigoAssinatura.in=" + UPDATED_CODIGO_ASSINATURA
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByCodigoAssinaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where codigoAssinatura is not null
        defaultAssinaturaEmpresaFiltering("codigoAssinatura.specified=true", "codigoAssinatura.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByCodigoAssinaturaContainsSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where codigoAssinatura contains
        defaultAssinaturaEmpresaFiltering(
            "codigoAssinatura.contains=" + DEFAULT_CODIGO_ASSINATURA,
            "codigoAssinatura.contains=" + UPDATED_CODIGO_ASSINATURA
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByCodigoAssinaturaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where codigoAssinatura does not contain
        defaultAssinaturaEmpresaFiltering(
            "codigoAssinatura.doesNotContain=" + UPDATED_CODIGO_ASSINATURA,
            "codigoAssinatura.doesNotContain=" + DEFAULT_CODIGO_ASSINATURA
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorEnquadramentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorEnquadramento equals to
        defaultAssinaturaEmpresaFiltering(
            "valorEnquadramento.equals=" + DEFAULT_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.equals=" + UPDATED_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorEnquadramentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorEnquadramento in
        defaultAssinaturaEmpresaFiltering(
            "valorEnquadramento.in=" + DEFAULT_VALOR_ENQUADRAMENTO + "," + UPDATED_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.in=" + UPDATED_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorEnquadramentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorEnquadramento is not null
        defaultAssinaturaEmpresaFiltering("valorEnquadramento.specified=true", "valorEnquadramento.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorEnquadramentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorEnquadramento is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorEnquadramento.greaterThanOrEqual=" + DEFAULT_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.greaterThanOrEqual=" + UPDATED_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorEnquadramentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorEnquadramento is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorEnquadramento.lessThanOrEqual=" + DEFAULT_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.lessThanOrEqual=" + SMALLER_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorEnquadramentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorEnquadramento is less than
        defaultAssinaturaEmpresaFiltering(
            "valorEnquadramento.lessThan=" + UPDATED_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.lessThan=" + DEFAULT_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorEnquadramentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorEnquadramento is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorEnquadramento.greaterThan=" + SMALLER_VALOR_ENQUADRAMENTO,
            "valorEnquadramento.greaterThan=" + DEFAULT_VALOR_ENQUADRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorTributacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorTributacao equals to
        defaultAssinaturaEmpresaFiltering(
            "valorTributacao.equals=" + DEFAULT_VALOR_TRIBUTACAO,
            "valorTributacao.equals=" + UPDATED_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorTributacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorTributacao in
        defaultAssinaturaEmpresaFiltering(
            "valorTributacao.in=" + DEFAULT_VALOR_TRIBUTACAO + "," + UPDATED_VALOR_TRIBUTACAO,
            "valorTributacao.in=" + UPDATED_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorTributacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorTributacao is not null
        defaultAssinaturaEmpresaFiltering("valorTributacao.specified=true", "valorTributacao.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorTributacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorTributacao is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorTributacao.greaterThanOrEqual=" + DEFAULT_VALOR_TRIBUTACAO,
            "valorTributacao.greaterThanOrEqual=" + UPDATED_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorTributacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorTributacao is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorTributacao.lessThanOrEqual=" + DEFAULT_VALOR_TRIBUTACAO,
            "valorTributacao.lessThanOrEqual=" + SMALLER_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorTributacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorTributacao is less than
        defaultAssinaturaEmpresaFiltering(
            "valorTributacao.lessThan=" + UPDATED_VALOR_TRIBUTACAO,
            "valorTributacao.lessThan=" + DEFAULT_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorTributacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorTributacao is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorTributacao.greaterThan=" + SMALLER_VALOR_TRIBUTACAO,
            "valorTributacao.greaterThan=" + DEFAULT_VALOR_TRIBUTACAO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorRamoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorRamo equals to
        defaultAssinaturaEmpresaFiltering("valorRamo.equals=" + DEFAULT_VALOR_RAMO, "valorRamo.equals=" + UPDATED_VALOR_RAMO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorRamoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorRamo in
        defaultAssinaturaEmpresaFiltering(
            "valorRamo.in=" + DEFAULT_VALOR_RAMO + "," + UPDATED_VALOR_RAMO,
            "valorRamo.in=" + UPDATED_VALOR_RAMO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorRamoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorRamo is not null
        defaultAssinaturaEmpresaFiltering("valorRamo.specified=true", "valorRamo.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorRamoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorRamo is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorRamo.greaterThanOrEqual=" + DEFAULT_VALOR_RAMO,
            "valorRamo.greaterThanOrEqual=" + UPDATED_VALOR_RAMO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorRamoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorRamo is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorRamo.lessThanOrEqual=" + DEFAULT_VALOR_RAMO,
            "valorRamo.lessThanOrEqual=" + SMALLER_VALOR_RAMO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorRamoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorRamo is less than
        defaultAssinaturaEmpresaFiltering("valorRamo.lessThan=" + UPDATED_VALOR_RAMO, "valorRamo.lessThan=" + DEFAULT_VALOR_RAMO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorRamoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorRamo is greater than
        defaultAssinaturaEmpresaFiltering("valorRamo.greaterThan=" + SMALLER_VALOR_RAMO, "valorRamo.greaterThan=" + DEFAULT_VALOR_RAMO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFuncionariosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFuncionarios equals to
        defaultAssinaturaEmpresaFiltering(
            "valorFuncionarios.equals=" + DEFAULT_VALOR_FUNCIONARIOS,
            "valorFuncionarios.equals=" + UPDATED_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFuncionariosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFuncionarios in
        defaultAssinaturaEmpresaFiltering(
            "valorFuncionarios.in=" + DEFAULT_VALOR_FUNCIONARIOS + "," + UPDATED_VALOR_FUNCIONARIOS,
            "valorFuncionarios.in=" + UPDATED_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFuncionariosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFuncionarios is not null
        defaultAssinaturaEmpresaFiltering("valorFuncionarios.specified=true", "valorFuncionarios.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFuncionariosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFuncionarios is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorFuncionarios.greaterThanOrEqual=" + DEFAULT_VALOR_FUNCIONARIOS,
            "valorFuncionarios.greaterThanOrEqual=" + UPDATED_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFuncionariosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFuncionarios is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorFuncionarios.lessThanOrEqual=" + DEFAULT_VALOR_FUNCIONARIOS,
            "valorFuncionarios.lessThanOrEqual=" + SMALLER_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFuncionariosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFuncionarios is less than
        defaultAssinaturaEmpresaFiltering(
            "valorFuncionarios.lessThan=" + UPDATED_VALOR_FUNCIONARIOS,
            "valorFuncionarios.lessThan=" + DEFAULT_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFuncionariosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFuncionarios is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorFuncionarios.greaterThan=" + SMALLER_VALOR_FUNCIONARIOS,
            "valorFuncionarios.greaterThan=" + DEFAULT_VALOR_FUNCIONARIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorSociosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorSocios equals to
        defaultAssinaturaEmpresaFiltering("valorSocios.equals=" + DEFAULT_VALOR_SOCIOS, "valorSocios.equals=" + UPDATED_VALOR_SOCIOS);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorSociosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorSocios in
        defaultAssinaturaEmpresaFiltering(
            "valorSocios.in=" + DEFAULT_VALOR_SOCIOS + "," + UPDATED_VALOR_SOCIOS,
            "valorSocios.in=" + UPDATED_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorSociosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorSocios is not null
        defaultAssinaturaEmpresaFiltering("valorSocios.specified=true", "valorSocios.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorSociosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorSocios is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorSocios.greaterThanOrEqual=" + DEFAULT_VALOR_SOCIOS,
            "valorSocios.greaterThanOrEqual=" + UPDATED_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorSociosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorSocios is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorSocios.lessThanOrEqual=" + DEFAULT_VALOR_SOCIOS,
            "valorSocios.lessThanOrEqual=" + SMALLER_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorSociosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorSocios is less than
        defaultAssinaturaEmpresaFiltering("valorSocios.lessThan=" + UPDATED_VALOR_SOCIOS, "valorSocios.lessThan=" + DEFAULT_VALOR_SOCIOS);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorSociosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorSocios is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorSocios.greaterThan=" + SMALLER_VALOR_SOCIOS,
            "valorSocios.greaterThan=" + DEFAULT_VALOR_SOCIOS
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFaturamento equals to
        defaultAssinaturaEmpresaFiltering(
            "valorFaturamento.equals=" + DEFAULT_VALOR_FATURAMENTO,
            "valorFaturamento.equals=" + UPDATED_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFaturamento in
        defaultAssinaturaEmpresaFiltering(
            "valorFaturamento.in=" + DEFAULT_VALOR_FATURAMENTO + "," + UPDATED_VALOR_FATURAMENTO,
            "valorFaturamento.in=" + UPDATED_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFaturamento is not null
        defaultAssinaturaEmpresaFiltering("valorFaturamento.specified=true", "valorFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFaturamento is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorFaturamento.greaterThanOrEqual=" + DEFAULT_VALOR_FATURAMENTO,
            "valorFaturamento.greaterThanOrEqual=" + UPDATED_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFaturamento is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorFaturamento.lessThanOrEqual=" + DEFAULT_VALOR_FATURAMENTO,
            "valorFaturamento.lessThanOrEqual=" + SMALLER_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFaturamento is less than
        defaultAssinaturaEmpresaFiltering(
            "valorFaturamento.lessThan=" + UPDATED_VALOR_FATURAMENTO,
            "valorFaturamento.lessThan=" + DEFAULT_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorFaturamento is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorFaturamento.greaterThan=" + SMALLER_VALOR_FATURAMENTO,
            "valorFaturamento.greaterThan=" + DEFAULT_VALOR_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabil equals to
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabil.equals=" + DEFAULT_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.equals=" + UPDATED_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabil in
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabil.in=" + DEFAULT_VALOR_PLANO_CONTABIL + "," + UPDATED_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.in=" + UPDATED_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabil is not null
        defaultAssinaturaEmpresaFiltering("valorPlanoContabil.specified=true", "valorPlanoContabil.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabil is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabil.greaterThanOrEqual=" + DEFAULT_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.greaterThanOrEqual=" + UPDATED_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabil is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabil.lessThanOrEqual=" + DEFAULT_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.lessThanOrEqual=" + SMALLER_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabil is less than
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabil.lessThan=" + UPDATED_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.lessThan=" + DEFAULT_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabil is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabil.greaterThan=" + SMALLER_VALOR_PLANO_CONTABIL,
            "valorPlanoContabil.greaterThan=" + DEFAULT_VALOR_PLANO_CONTABIL
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilComDescontoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabilComDesconto equals to
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabilComDesconto.equals=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.equals=" + UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilComDescontoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabilComDesconto in
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabilComDesconto.in=" +
            DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO +
            "," +
            UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.in=" + UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilComDescontoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabilComDesconto is not null
        defaultAssinaturaEmpresaFiltering("valorPlanoContabilComDesconto.specified=true", "valorPlanoContabilComDesconto.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilComDescontoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabilComDesconto is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabilComDesconto.greaterThanOrEqual=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.greaterThanOrEqual=" + UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilComDescontoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabilComDesconto is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabilComDesconto.lessThanOrEqual=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.lessThanOrEqual=" + SMALLER_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilComDescontoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabilComDesconto is less than
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabilComDesconto.lessThan=" + UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.lessThan=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPlanoContabilComDescontoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPlanoContabilComDesconto is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorPlanoContabilComDesconto.greaterThan=" + SMALLER_VALOR_PLANO_CONTABIL_COM_DESCONTO,
            "valorPlanoContabilComDesconto.greaterThan=" + DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorMensalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorMensalidade equals to
        defaultAssinaturaEmpresaFiltering(
            "valorMensalidade.equals=" + DEFAULT_VALOR_MENSALIDADE,
            "valorMensalidade.equals=" + UPDATED_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorMensalidadeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorMensalidade in
        defaultAssinaturaEmpresaFiltering(
            "valorMensalidade.in=" + DEFAULT_VALOR_MENSALIDADE + "," + UPDATED_VALOR_MENSALIDADE,
            "valorMensalidade.in=" + UPDATED_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorMensalidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorMensalidade is not null
        defaultAssinaturaEmpresaFiltering("valorMensalidade.specified=true", "valorMensalidade.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorMensalidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorMensalidade is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorMensalidade.greaterThanOrEqual=" + DEFAULT_VALOR_MENSALIDADE,
            "valorMensalidade.greaterThanOrEqual=" + UPDATED_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorMensalidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorMensalidade is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorMensalidade.lessThanOrEqual=" + DEFAULT_VALOR_MENSALIDADE,
            "valorMensalidade.lessThanOrEqual=" + SMALLER_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorMensalidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorMensalidade is less than
        defaultAssinaturaEmpresaFiltering(
            "valorMensalidade.lessThan=" + UPDATED_VALOR_MENSALIDADE,
            "valorMensalidade.lessThan=" + DEFAULT_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorMensalidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorMensalidade is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorMensalidade.greaterThan=" + SMALLER_VALOR_MENSALIDADE,
            "valorMensalidade.greaterThan=" + DEFAULT_VALOR_MENSALIDADE
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPeriodo equals to
        defaultAssinaturaEmpresaFiltering("valorPeriodo.equals=" + DEFAULT_VALOR_PERIODO, "valorPeriodo.equals=" + UPDATED_VALOR_PERIODO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPeriodo in
        defaultAssinaturaEmpresaFiltering(
            "valorPeriodo.in=" + DEFAULT_VALOR_PERIODO + "," + UPDATED_VALOR_PERIODO,
            "valorPeriodo.in=" + UPDATED_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPeriodo is not null
        defaultAssinaturaEmpresaFiltering("valorPeriodo.specified=true", "valorPeriodo.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPeriodoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPeriodo is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorPeriodo.greaterThanOrEqual=" + DEFAULT_VALOR_PERIODO,
            "valorPeriodo.greaterThanOrEqual=" + UPDATED_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPeriodoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPeriodo is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorPeriodo.lessThanOrEqual=" + DEFAULT_VALOR_PERIODO,
            "valorPeriodo.lessThanOrEqual=" + SMALLER_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPeriodoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPeriodo is less than
        defaultAssinaturaEmpresaFiltering(
            "valorPeriodo.lessThan=" + UPDATED_VALOR_PERIODO,
            "valorPeriodo.lessThan=" + DEFAULT_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorPeriodoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorPeriodo is greater than
        defaultAssinaturaEmpresaFiltering(
            "valorPeriodo.greaterThan=" + SMALLER_VALOR_PERIODO,
            "valorPeriodo.greaterThan=" + DEFAULT_VALOR_PERIODO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorAnoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorAno equals to
        defaultAssinaturaEmpresaFiltering("valorAno.equals=" + DEFAULT_VALOR_ANO, "valorAno.equals=" + UPDATED_VALOR_ANO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorAnoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorAno in
        defaultAssinaturaEmpresaFiltering("valorAno.in=" + DEFAULT_VALOR_ANO + "," + UPDATED_VALOR_ANO, "valorAno.in=" + UPDATED_VALOR_ANO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorAnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorAno is not null
        defaultAssinaturaEmpresaFiltering("valorAno.specified=true", "valorAno.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorAnoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorAno is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "valorAno.greaterThanOrEqual=" + DEFAULT_VALOR_ANO,
            "valorAno.greaterThanOrEqual=" + UPDATED_VALOR_ANO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorAnoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorAno is less than or equal to
        defaultAssinaturaEmpresaFiltering("valorAno.lessThanOrEqual=" + DEFAULT_VALOR_ANO, "valorAno.lessThanOrEqual=" + SMALLER_VALOR_ANO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorAnoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorAno is less than
        defaultAssinaturaEmpresaFiltering("valorAno.lessThan=" + UPDATED_VALOR_ANO, "valorAno.lessThan=" + DEFAULT_VALOR_ANO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByValorAnoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where valorAno is greater than
        defaultAssinaturaEmpresaFiltering("valorAno.greaterThan=" + SMALLER_VALOR_ANO, "valorAno.greaterThan=" + DEFAULT_VALOR_ANO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDataContratacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where dataContratacao equals to
        defaultAssinaturaEmpresaFiltering(
            "dataContratacao.equals=" + DEFAULT_DATA_CONTRATACAO,
            "dataContratacao.equals=" + UPDATED_DATA_CONTRATACAO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDataContratacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where dataContratacao in
        defaultAssinaturaEmpresaFiltering(
            "dataContratacao.in=" + DEFAULT_DATA_CONTRATACAO + "," + UPDATED_DATA_CONTRATACAO,
            "dataContratacao.in=" + UPDATED_DATA_CONTRATACAO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDataContratacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where dataContratacao is not null
        defaultAssinaturaEmpresaFiltering("dataContratacao.specified=true", "dataContratacao.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDataEncerramentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where dataEncerramento equals to
        defaultAssinaturaEmpresaFiltering(
            "dataEncerramento.equals=" + DEFAULT_DATA_ENCERRAMENTO,
            "dataEncerramento.equals=" + UPDATED_DATA_ENCERRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDataEncerramentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where dataEncerramento in
        defaultAssinaturaEmpresaFiltering(
            "dataEncerramento.in=" + DEFAULT_DATA_ENCERRAMENTO + "," + UPDATED_DATA_ENCERRAMENTO,
            "dataEncerramento.in=" + UPDATED_DATA_ENCERRAMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDataEncerramentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where dataEncerramento is not null
        defaultAssinaturaEmpresaFiltering("dataEncerramento.specified=true", "dataEncerramento.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDiaVencimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where diaVencimento equals to
        defaultAssinaturaEmpresaFiltering(
            "diaVencimento.equals=" + DEFAULT_DIA_VENCIMENTO,
            "diaVencimento.equals=" + UPDATED_DIA_VENCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDiaVencimentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where diaVencimento in
        defaultAssinaturaEmpresaFiltering(
            "diaVencimento.in=" + DEFAULT_DIA_VENCIMENTO + "," + UPDATED_DIA_VENCIMENTO,
            "diaVencimento.in=" + UPDATED_DIA_VENCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDiaVencimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where diaVencimento is not null
        defaultAssinaturaEmpresaFiltering("diaVencimento.specified=true", "diaVencimento.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDiaVencimentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where diaVencimento is greater than or equal to
        defaultAssinaturaEmpresaFiltering(
            "diaVencimento.greaterThanOrEqual=" + DEFAULT_DIA_VENCIMENTO,
            "diaVencimento.greaterThanOrEqual=" + UPDATED_DIA_VENCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDiaVencimentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where diaVencimento is less than or equal to
        defaultAssinaturaEmpresaFiltering(
            "diaVencimento.lessThanOrEqual=" + DEFAULT_DIA_VENCIMENTO,
            "diaVencimento.lessThanOrEqual=" + SMALLER_DIA_VENCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDiaVencimentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where diaVencimento is less than
        defaultAssinaturaEmpresaFiltering(
            "diaVencimento.lessThan=" + UPDATED_DIA_VENCIMENTO,
            "diaVencimento.lessThan=" + DEFAULT_DIA_VENCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByDiaVencimentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where diaVencimento is greater than
        defaultAssinaturaEmpresaFiltering(
            "diaVencimento.greaterThan=" + SMALLER_DIA_VENCIMENTO,
            "diaVencimento.greaterThan=" + DEFAULT_DIA_VENCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where situacao equals to
        defaultAssinaturaEmpresaFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where situacao in
        defaultAssinaturaEmpresaFiltering("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO, "situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where situacao is not null
        defaultAssinaturaEmpresaFiltering("situacao.specified=true", "situacao.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByTipoContratoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where tipoContrato equals to
        defaultAssinaturaEmpresaFiltering("tipoContrato.equals=" + DEFAULT_TIPO_CONTRATO, "tipoContrato.equals=" + UPDATED_TIPO_CONTRATO);
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByTipoContratoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where tipoContrato in
        defaultAssinaturaEmpresaFiltering(
            "tipoContrato.in=" + DEFAULT_TIPO_CONTRATO + "," + UPDATED_TIPO_CONTRATO,
            "tipoContrato.in=" + UPDATED_TIPO_CONTRATO
        );
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByTipoContratoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        // Get all the assinaturaEmpresaList where tipoContrato is not null
        defaultAssinaturaEmpresaFiltering("tipoContrato.specified=true", "tipoContrato.specified=false");
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByPeriodoPagamentoIsEqualToSomething() throws Exception {
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        em.persist(periodoPagamento);
        em.flush();
        assinaturaEmpresa.setPeriodoPagamento(periodoPagamento);
        assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);
        Long periodoPagamentoId = periodoPagamento.getId();
        // Get all the assinaturaEmpresaList where periodoPagamento equals to periodoPagamentoId
        defaultAssinaturaEmpresaShouldBeFound("periodoPagamentoId.equals=" + periodoPagamentoId);

        // Get all the assinaturaEmpresaList where periodoPagamento equals to (periodoPagamentoId + 1)
        defaultAssinaturaEmpresaShouldNotBeFound("periodoPagamentoId.equals=" + (periodoPagamentoId + 1));
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByFormaDePagamentoIsEqualToSomething() throws Exception {
        FormaDePagamento formaDePagamento;
        if (TestUtil.findAll(em, FormaDePagamento.class).isEmpty()) {
            assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);
            formaDePagamento = FormaDePagamentoResourceIT.createEntity(em);
        } else {
            formaDePagamento = TestUtil.findAll(em, FormaDePagamento.class).get(0);
        }
        em.persist(formaDePagamento);
        em.flush();
        assinaturaEmpresa.setFormaDePagamento(formaDePagamento);
        assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);
        Long formaDePagamentoId = formaDePagamento.getId();
        // Get all the assinaturaEmpresaList where formaDePagamento equals to formaDePagamentoId
        defaultAssinaturaEmpresaShouldBeFound("formaDePagamentoId.equals=" + formaDePagamentoId);

        // Get all the assinaturaEmpresaList where formaDePagamento equals to (formaDePagamentoId + 1)
        defaultAssinaturaEmpresaShouldNotBeFound("formaDePagamentoId.equals=" + (formaDePagamentoId + 1));
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByPlanoContabilIsEqualToSomething() throws Exception {
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);
            planoContabil = PlanoContabilResourceIT.createEntity(em);
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        em.persist(planoContabil);
        em.flush();
        assinaturaEmpresa.setPlanoContabil(planoContabil);
        assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);
        Long planoContabilId = planoContabil.getId();
        // Get all the assinaturaEmpresaList where planoContabil equals to planoContabilId
        defaultAssinaturaEmpresaShouldBeFound("planoContabilId.equals=" + planoContabilId);

        // Get all the assinaturaEmpresaList where planoContabil equals to (planoContabilId + 1)
        defaultAssinaturaEmpresaShouldNotBeFound("planoContabilId.equals=" + (planoContabilId + 1));
    }

    @Test
    @Transactional
    void getAllAssinaturaEmpresasByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        assinaturaEmpresa.setEmpresa(empresa);
        assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);
        Long empresaId = empresa.getId();
        // Get all the assinaturaEmpresaList where empresa equals to empresaId
        defaultAssinaturaEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the assinaturaEmpresaList where empresa equals to (empresaId + 1)
        defaultAssinaturaEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultAssinaturaEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAssinaturaEmpresaShouldBeFound(shouldBeFound);
        defaultAssinaturaEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssinaturaEmpresaShouldBeFound(String filter) throws Exception {
        restAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assinaturaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoAssinatura").value(hasItem(DEFAULT_CODIGO_ASSINATURA)))
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
            .andExpect(jsonPath("$.[*].valorAno").value(hasItem(DEFAULT_VALOR_ANO.doubleValue())))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].dataEncerramento").value(hasItem(DEFAULT_DATA_ENCERRAMENTO.toString())))
            .andExpect(jsonPath("$.[*].diaVencimento").value(hasItem(DEFAULT_DIA_VENCIMENTO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].tipoContrato").value(hasItem(DEFAULT_TIPO_CONTRATO.toString())));

        // Check, that the count call also returns 1
        restAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssinaturaEmpresaShouldNotBeFound(String filter) throws Exception {
        restAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAssinaturaEmpresa() throws Exception {
        // Get the assinaturaEmpresa
        restAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assinaturaEmpresa
        AssinaturaEmpresa updatedAssinaturaEmpresa = assinaturaEmpresaRepository.findById(assinaturaEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAssinaturaEmpresa are not directly saved in db
        em.detach(updatedAssinaturaEmpresa);
        updatedAssinaturaEmpresa
            .codigoAssinatura(UPDATED_CODIGO_ASSINATURA)
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
            .valorAno(UPDATED_VALOR_ANO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .dataEncerramento(UPDATED_DATA_ENCERRAMENTO)
            .diaVencimento(UPDATED_DIA_VENCIMENTO)
            .situacao(UPDATED_SITUACAO)
            .tipoContrato(UPDATED_TIPO_CONTRATO);
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(updatedAssinaturaEmpresa);

        restAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assinaturaEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assinaturaEmpresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssinaturaEmpresaToMatchAllProperties(updatedAssinaturaEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assinaturaEmpresa.setId(longCount.incrementAndGet());

        // Create the AssinaturaEmpresa
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(assinaturaEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assinaturaEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assinaturaEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assinaturaEmpresa.setId(longCount.incrementAndGet());

        // Create the AssinaturaEmpresa
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(assinaturaEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assinaturaEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assinaturaEmpresa.setId(longCount.incrementAndGet());

        // Create the AssinaturaEmpresa
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(assinaturaEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assinaturaEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assinaturaEmpresa using partial update
        AssinaturaEmpresa partialUpdatedAssinaturaEmpresa = new AssinaturaEmpresa();
        partialUpdatedAssinaturaEmpresa.setId(assinaturaEmpresa.getId());

        partialUpdatedAssinaturaEmpresa
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorRamo(UPDATED_VALOR_RAMO)
            .valorFuncionarios(UPDATED_VALOR_FUNCIONARIOS)
            .valorPlanoContabil(UPDATED_VALOR_PLANO_CONTABIL)
            .valorMensalidade(UPDATED_VALOR_MENSALIDADE)
            .valorPeriodo(UPDATED_VALOR_PERIODO)
            .valorAno(UPDATED_VALOR_ANO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO);

        restAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssinaturaEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssinaturaEmpresa, assinaturaEmpresa),
            getPersistedAssinaturaEmpresa(assinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assinaturaEmpresa using partial update
        AssinaturaEmpresa partialUpdatedAssinaturaEmpresa = new AssinaturaEmpresa();
        partialUpdatedAssinaturaEmpresa.setId(assinaturaEmpresa.getId());

        partialUpdatedAssinaturaEmpresa
            .codigoAssinatura(UPDATED_CODIGO_ASSINATURA)
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
            .valorAno(UPDATED_VALOR_ANO)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .dataEncerramento(UPDATED_DATA_ENCERRAMENTO)
            .diaVencimento(UPDATED_DIA_VENCIMENTO)
            .situacao(UPDATED_SITUACAO)
            .tipoContrato(UPDATED_TIPO_CONTRATO);

        restAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssinaturaEmpresaUpdatableFieldsEquals(
            partialUpdatedAssinaturaEmpresa,
            getPersistedAssinaturaEmpresa(partialUpdatedAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assinaturaEmpresa.setId(longCount.incrementAndGet());

        // Create the AssinaturaEmpresa
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(assinaturaEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assinaturaEmpresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assinaturaEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assinaturaEmpresa.setId(longCount.incrementAndGet());

        // Create the AssinaturaEmpresa
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(assinaturaEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assinaturaEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assinaturaEmpresa.setId(longCount.incrementAndGet());

        // Create the AssinaturaEmpresa
        AssinaturaEmpresaDTO assinaturaEmpresaDTO = assinaturaEmpresaMapper.toDto(assinaturaEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assinaturaEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedAssinaturaEmpresa = assinaturaEmpresaRepository.saveAndFlush(assinaturaEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assinaturaEmpresa
        restAssinaturaEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, assinaturaEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assinaturaEmpresaRepository.count();
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

    protected AssinaturaEmpresa getPersistedAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        return assinaturaEmpresaRepository.findById(assinaturaEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedAssinaturaEmpresaToMatchAllProperties(AssinaturaEmpresa expectedAssinaturaEmpresa) {
        assertAssinaturaEmpresaAllPropertiesEquals(expectedAssinaturaEmpresa, getPersistedAssinaturaEmpresa(expectedAssinaturaEmpresa));
    }

    protected void assertPersistedAssinaturaEmpresaToMatchUpdatableProperties(AssinaturaEmpresa expectedAssinaturaEmpresa) {
        assertAssinaturaEmpresaAllUpdatablePropertiesEquals(
            expectedAssinaturaEmpresa,
            getPersistedAssinaturaEmpresa(expectedAssinaturaEmpresa)
        );
    }
}
