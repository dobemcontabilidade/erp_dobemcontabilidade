package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.enumeration.SituacaoContratoEmpresaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoContratoEnum;
import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
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

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_ASSINATURA = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_ASSINATURA = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR_ENQUADRAMENTO = 1D;
    private static final Double UPDATED_VALOR_ENQUADRAMENTO = 2D;

    private static final Double DEFAULT_VALOR_TRIBUTACAO = 1D;
    private static final Double UPDATED_VALOR_TRIBUTACAO = 2D;

    private static final Double DEFAULT_VALOR_RAMO = 1D;
    private static final Double UPDATED_VALOR_RAMO = 2D;

    private static final Double DEFAULT_VALOR_FUNCIONARIOS = 1D;
    private static final Double UPDATED_VALOR_FUNCIONARIOS = 2D;

    private static final Double DEFAULT_VALOR_SOCIOS = 1D;
    private static final Double UPDATED_VALOR_SOCIOS = 2D;

    private static final Double DEFAULT_VALOR_FATURAMENTO = 1D;
    private static final Double UPDATED_VALOR_FATURAMENTO = 2D;

    private static final Double DEFAULT_VALOR_PLANO_CONTABIL = 1D;
    private static final Double UPDATED_VALOR_PLANO_CONTABIL = 2D;

    private static final Double DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO = 1D;
    private static final Double UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO = 2D;

    private static final Double DEFAULT_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO = 1D;
    private static final Double UPDATED_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO = 2D;

    private static final Double DEFAULT_VALOR_MENSALIDADE = 1D;
    private static final Double UPDATED_VALOR_MENSALIDADE = 2D;

    private static final Double DEFAULT_VALOR_PERIODO = 1D;
    private static final Double UPDATED_VALOR_PERIODO = 2D;

    private static final Double DEFAULT_VALOR_ANO = 1D;
    private static final Double UPDATED_VALOR_ANO = 2D;

    private static final Instant DEFAULT_DATA_CONTRATACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONTRATACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ENCERRAMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ENCERRAMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DIA_VENCIMENTO = 1;
    private static final Integer UPDATED_DIA_VENCIMENTO = 2;

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
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .codigoAssinatura(DEFAULT_CODIGO_ASSINATURA)
            .valorEnquadramento(DEFAULT_VALOR_ENQUADRAMENTO)
            .valorTributacao(DEFAULT_VALOR_TRIBUTACAO)
            .valorRamo(DEFAULT_VALOR_RAMO)
            .valorFuncionarios(DEFAULT_VALOR_FUNCIONARIOS)
            .valorSocios(DEFAULT_VALOR_SOCIOS)
            .valorFaturamento(DEFAULT_VALOR_FATURAMENTO)
            .valorPlanoContabil(DEFAULT_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorPlanoContaAzulComDesconto(DEFAULT_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO)
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
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .codigoAssinatura(UPDATED_CODIGO_ASSINATURA)
            .valorEnquadramento(UPDATED_VALOR_ENQUADRAMENTO)
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorRamo(UPDATED_VALOR_RAMO)
            .valorFuncionarios(UPDATED_VALOR_FUNCIONARIOS)
            .valorSocios(UPDATED_VALOR_SOCIOS)
            .valorFaturamento(UPDATED_VALOR_FATURAMENTO)
            .valorPlanoContabil(UPDATED_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorPlanoContaAzulComDesconto(UPDATED_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO)
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
        var returnedAssinaturaEmpresa = om.readValue(
            restAssinaturaEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(assinaturaEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssinaturaEmpresa.class
        );

        // Validate the AssinaturaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAssinaturaEmpresaUpdatableFieldsEquals(returnedAssinaturaEmpresa, getPersistedAssinaturaEmpresa(returnedAssinaturaEmpresa));

        insertedAssinaturaEmpresa = returnedAssinaturaEmpresa;
    }

    @Test
    @Transactional
    void createAssinaturaEmpresaWithExistingId() throws Exception {
        // Create the AssinaturaEmpresa with an existing ID
        assinaturaEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssinaturaEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRazaoSocialIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        assinaturaEmpresa.setRazaoSocial(null);

        // Create the AssinaturaEmpresa, which fails.

        restAssinaturaEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
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
            .andExpect(
                jsonPath("$.[*].valorPlanoContaAzulComDesconto").value(hasItem(DEFAULT_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO.doubleValue()))
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
        when(assinaturaEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assinaturaEmpresaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssinaturaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assinaturaEmpresaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

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
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.codigoAssinatura").value(DEFAULT_CODIGO_ASSINATURA))
            .andExpect(jsonPath("$.valorEnquadramento").value(DEFAULT_VALOR_ENQUADRAMENTO.doubleValue()))
            .andExpect(jsonPath("$.valorTributacao").value(DEFAULT_VALOR_TRIBUTACAO.doubleValue()))
            .andExpect(jsonPath("$.valorRamo").value(DEFAULT_VALOR_RAMO.doubleValue()))
            .andExpect(jsonPath("$.valorFuncionarios").value(DEFAULT_VALOR_FUNCIONARIOS.doubleValue()))
            .andExpect(jsonPath("$.valorSocios").value(DEFAULT_VALOR_SOCIOS.doubleValue()))
            .andExpect(jsonPath("$.valorFaturamento").value(DEFAULT_VALOR_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.valorPlanoContabil").value(DEFAULT_VALOR_PLANO_CONTABIL.doubleValue()))
            .andExpect(jsonPath("$.valorPlanoContabilComDesconto").value(DEFAULT_VALOR_PLANO_CONTABIL_COM_DESCONTO.doubleValue()))
            .andExpect(jsonPath("$.valorPlanoContaAzulComDesconto").value(DEFAULT_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO.doubleValue()))
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
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .codigoAssinatura(UPDATED_CODIGO_ASSINATURA)
            .valorEnquadramento(UPDATED_VALOR_ENQUADRAMENTO)
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorRamo(UPDATED_VALOR_RAMO)
            .valorFuncionarios(UPDATED_VALOR_FUNCIONARIOS)
            .valorSocios(UPDATED_VALOR_SOCIOS)
            .valorFaturamento(UPDATED_VALOR_FATURAMENTO)
            .valorPlanoContabil(UPDATED_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorPlanoContaAzulComDesconto(UPDATED_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO)
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
                put(ENTITY_API_URL_ID, updatedAssinaturaEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAssinaturaEmpresa))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assinaturaEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assinaturaEmpresa))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assinaturaEmpresa))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assinaturaEmpresa))
            )
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
            .codigoAssinatura(UPDATED_CODIGO_ASSINATURA)
            .valorEnquadramento(UPDATED_VALOR_ENQUADRAMENTO)
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorSocios(UPDATED_VALOR_SOCIOS)
            .valorPlanoContabil(UPDATED_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorPlanoContaAzulComDesconto(UPDATED_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO)
            .valorMensalidade(UPDATED_VALOR_MENSALIDADE)
            .valorPeriodo(UPDATED_VALOR_PERIODO)
            .valorAno(UPDATED_VALOR_ANO)
            .diaVencimento(UPDATED_DIA_VENCIMENTO);

        restAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssinaturaEmpresa.getId())
                    .with(csrf())
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
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .codigoAssinatura(UPDATED_CODIGO_ASSINATURA)
            .valorEnquadramento(UPDATED_VALOR_ENQUADRAMENTO)
            .valorTributacao(UPDATED_VALOR_TRIBUTACAO)
            .valorRamo(UPDATED_VALOR_RAMO)
            .valorFuncionarios(UPDATED_VALOR_FUNCIONARIOS)
            .valorSocios(UPDATED_VALOR_SOCIOS)
            .valorFaturamento(UPDATED_VALOR_FATURAMENTO)
            .valorPlanoContabil(UPDATED_VALOR_PLANO_CONTABIL)
            .valorPlanoContabilComDesconto(UPDATED_VALOR_PLANO_CONTABIL_COM_DESCONTO)
            .valorPlanoContaAzulComDesconto(UPDATED_VALOR_PLANO_CONTA_AZUL_COM_DESCONTO)
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
                    .with(csrf())
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assinaturaEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assinaturaEmpresa))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assinaturaEmpresa))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assinaturaEmpresa))
            )
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
            .perform(delete(ENTITY_API_URL_ID, assinaturaEmpresa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
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
