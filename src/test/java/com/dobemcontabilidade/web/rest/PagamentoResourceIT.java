package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PagamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.Pagamento;
import com.dobemcontabilidade.domain.enumeration.SituacaoPagamentoEnum;
import com.dobemcontabilidade.repository.PagamentoRepository;
import com.dobemcontabilidade.service.PagamentoService;
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
 * Integration tests for the {@link PagamentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PagamentoResourceIT {

    private static final Instant DEFAULT_DATA_COBRANCA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_COBRANCA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_VENCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_VENCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_PAGAMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_PAGAMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_VALOR_PAGO = 1D;
    private static final Double UPDATED_VALOR_PAGO = 2D;
    private static final Double SMALLER_VALOR_PAGO = 1D - 1D;

    private static final Double DEFAULT_VALOR_COBRADO = 1D;
    private static final Double UPDATED_VALOR_COBRADO = 2D;
    private static final Double SMALLER_VALOR_COBRADO = 1D - 1D;

    private static final Double DEFAULT_ACRESCIMO = 1D;
    private static final Double UPDATED_ACRESCIMO = 2D;
    private static final Double SMALLER_ACRESCIMO = 1D - 1D;

    private static final Double DEFAULT_MULTA = 1D;
    private static final Double UPDATED_MULTA = 2D;
    private static final Double SMALLER_MULTA = 1D - 1D;

    private static final Double DEFAULT_JUROS = 1D;
    private static final Double UPDATED_JUROS = 2D;
    private static final Double SMALLER_JUROS = 1D - 1D;

    private static final SituacaoPagamentoEnum DEFAULT_SITUACAO = SituacaoPagamentoEnum.APAGAR;
    private static final SituacaoPagamentoEnum UPDATED_SITUACAO = SituacaoPagamentoEnum.PAGO;

    private static final String ENTITY_API_URL = "/api/pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Mock
    private PagamentoRepository pagamentoRepositoryMock;

    @Mock
    private PagamentoService pagamentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPagamentoMockMvc;

    private Pagamento pagamento;

    private Pagamento insertedPagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagamento createEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .dataCobranca(DEFAULT_DATA_COBRANCA)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .dataPagamento(DEFAULT_DATA_PAGAMENTO)
            .valorPago(DEFAULT_VALOR_PAGO)
            .valorCobrado(DEFAULT_VALOR_COBRADO)
            .acrescimo(DEFAULT_ACRESCIMO)
            .multa(DEFAULT_MULTA)
            .juros(DEFAULT_JUROS)
            .situacao(DEFAULT_SITUACAO);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        pagamento.setAssinaturaEmpresa(assinaturaEmpresa);
        return pagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagamento createUpdatedEntity(EntityManager em) {
        Pagamento pagamento = new Pagamento()
            .dataCobranca(UPDATED_DATA_COBRANCA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valorPago(UPDATED_VALOR_PAGO)
            .valorCobrado(UPDATED_VALOR_COBRADO)
            .acrescimo(UPDATED_ACRESCIMO)
            .multa(UPDATED_MULTA)
            .juros(UPDATED_JUROS)
            .situacao(UPDATED_SITUACAO);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        pagamento.setAssinaturaEmpresa(assinaturaEmpresa);
        return pagamento;
    }

    @BeforeEach
    public void initTest() {
        pagamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPagamento != null) {
            pagamentoRepository.delete(insertedPagamento);
            insertedPagamento = null;
        }
    }

    @Test
    @Transactional
    void createPagamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pagamento
        var returnedPagamento = om.readValue(
            restPagamentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pagamento)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pagamento.class
        );

        // Validate the Pagamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPagamentoUpdatableFieldsEquals(returnedPagamento, getPersistedPagamento(returnedPagamento));

        insertedPagamento = returnedPagamento;
    }

    @Test
    @Transactional
    void createPagamentoWithExistingId() throws Exception {
        // Create the Pagamento with an existing ID
        pagamento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pagamento)))
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSituacaoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pagamento.setSituacao(null);

        // Create the Pagamento, which fails.

        restPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pagamento)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPagamentos() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCobranca").value(hasItem(DEFAULT_DATA_COBRANCA.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].valorPago").value(hasItem(DEFAULT_VALOR_PAGO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorCobrado").value(hasItem(DEFAULT_VALOR_COBRADO.doubleValue())))
            .andExpect(jsonPath("$.[*].acrescimo").value(hasItem(DEFAULT_ACRESCIMO.doubleValue())))
            .andExpect(jsonPath("$.[*].multa").value(hasItem(DEFAULT_MULTA.doubleValue())))
            .andExpect(jsonPath("$.[*].juros").value(hasItem(DEFAULT_JUROS.doubleValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPagamentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(pagamentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPagamentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(pagamentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPagamentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(pagamentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPagamentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(pagamentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPagamento() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get the pagamento
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, pagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pagamento.getId().intValue()))
            .andExpect(jsonPath("$.dataCobranca").value(DEFAULT_DATA_COBRANCA.toString()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.dataPagamento").value(DEFAULT_DATA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.valorPago").value(DEFAULT_VALOR_PAGO.doubleValue()))
            .andExpect(jsonPath("$.valorCobrado").value(DEFAULT_VALOR_COBRADO.doubleValue()))
            .andExpect(jsonPath("$.acrescimo").value(DEFAULT_ACRESCIMO.doubleValue()))
            .andExpect(jsonPath("$.multa").value(DEFAULT_MULTA.doubleValue()))
            .andExpect(jsonPath("$.juros").value(DEFAULT_JUROS.doubleValue()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getPagamentosByIdFiltering() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        Long id = pagamento.getId();

        defaultPagamentoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPagamentoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPagamentoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataCobrancaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataCobranca equals to
        defaultPagamentoFiltering("dataCobranca.equals=" + DEFAULT_DATA_COBRANCA, "dataCobranca.equals=" + UPDATED_DATA_COBRANCA);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataCobrancaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataCobranca in
        defaultPagamentoFiltering(
            "dataCobranca.in=" + DEFAULT_DATA_COBRANCA + "," + UPDATED_DATA_COBRANCA,
            "dataCobranca.in=" + UPDATED_DATA_COBRANCA
        );
    }

    @Test
    @Transactional
    void getAllPagamentosByDataCobrancaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataCobranca is not null
        defaultPagamentoFiltering("dataCobranca.specified=true", "dataCobranca.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByDataVencimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataVencimento equals to
        defaultPagamentoFiltering("dataVencimento.equals=" + DEFAULT_DATA_VENCIMENTO, "dataVencimento.equals=" + UPDATED_DATA_VENCIMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataVencimentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataVencimento in
        defaultPagamentoFiltering(
            "dataVencimento.in=" + DEFAULT_DATA_VENCIMENTO + "," + UPDATED_DATA_VENCIMENTO,
            "dataVencimento.in=" + UPDATED_DATA_VENCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllPagamentosByDataVencimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataVencimento is not null
        defaultPagamentoFiltering("dataVencimento.specified=true", "dataVencimento.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByDataPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataPagamento equals to
        defaultPagamentoFiltering("dataPagamento.equals=" + DEFAULT_DATA_PAGAMENTO, "dataPagamento.equals=" + UPDATED_DATA_PAGAMENTO);
    }

    @Test
    @Transactional
    void getAllPagamentosByDataPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataPagamento in
        defaultPagamentoFiltering(
            "dataPagamento.in=" + DEFAULT_DATA_PAGAMENTO + "," + UPDATED_DATA_PAGAMENTO,
            "dataPagamento.in=" + UPDATED_DATA_PAGAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPagamentosByDataPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where dataPagamento is not null
        defaultPagamentoFiltering("dataPagamento.specified=true", "dataPagamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByValorPagoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorPago equals to
        defaultPagamentoFiltering("valorPago.equals=" + DEFAULT_VALOR_PAGO, "valorPago.equals=" + UPDATED_VALOR_PAGO);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorPagoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorPago in
        defaultPagamentoFiltering("valorPago.in=" + DEFAULT_VALOR_PAGO + "," + UPDATED_VALOR_PAGO, "valorPago.in=" + UPDATED_VALOR_PAGO);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorPagoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorPago is not null
        defaultPagamentoFiltering("valorPago.specified=true", "valorPago.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByValorPagoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorPago is greater than or equal to
        defaultPagamentoFiltering(
            "valorPago.greaterThanOrEqual=" + DEFAULT_VALOR_PAGO,
            "valorPago.greaterThanOrEqual=" + UPDATED_VALOR_PAGO
        );
    }

    @Test
    @Transactional
    void getAllPagamentosByValorPagoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorPago is less than or equal to
        defaultPagamentoFiltering("valorPago.lessThanOrEqual=" + DEFAULT_VALOR_PAGO, "valorPago.lessThanOrEqual=" + SMALLER_VALOR_PAGO);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorPagoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorPago is less than
        defaultPagamentoFiltering("valorPago.lessThan=" + UPDATED_VALOR_PAGO, "valorPago.lessThan=" + DEFAULT_VALOR_PAGO);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorPagoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorPago is greater than
        defaultPagamentoFiltering("valorPago.greaterThan=" + SMALLER_VALOR_PAGO, "valorPago.greaterThan=" + DEFAULT_VALOR_PAGO);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorCobradoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorCobrado equals to
        defaultPagamentoFiltering("valorCobrado.equals=" + DEFAULT_VALOR_COBRADO, "valorCobrado.equals=" + UPDATED_VALOR_COBRADO);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorCobradoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorCobrado in
        defaultPagamentoFiltering(
            "valorCobrado.in=" + DEFAULT_VALOR_COBRADO + "," + UPDATED_VALOR_COBRADO,
            "valorCobrado.in=" + UPDATED_VALOR_COBRADO
        );
    }

    @Test
    @Transactional
    void getAllPagamentosByValorCobradoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorCobrado is not null
        defaultPagamentoFiltering("valorCobrado.specified=true", "valorCobrado.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByValorCobradoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorCobrado is greater than or equal to
        defaultPagamentoFiltering(
            "valorCobrado.greaterThanOrEqual=" + DEFAULT_VALOR_COBRADO,
            "valorCobrado.greaterThanOrEqual=" + UPDATED_VALOR_COBRADO
        );
    }

    @Test
    @Transactional
    void getAllPagamentosByValorCobradoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorCobrado is less than or equal to
        defaultPagamentoFiltering(
            "valorCobrado.lessThanOrEqual=" + DEFAULT_VALOR_COBRADO,
            "valorCobrado.lessThanOrEqual=" + SMALLER_VALOR_COBRADO
        );
    }

    @Test
    @Transactional
    void getAllPagamentosByValorCobradoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorCobrado is less than
        defaultPagamentoFiltering("valorCobrado.lessThan=" + UPDATED_VALOR_COBRADO, "valorCobrado.lessThan=" + DEFAULT_VALOR_COBRADO);
    }

    @Test
    @Transactional
    void getAllPagamentosByValorCobradoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where valorCobrado is greater than
        defaultPagamentoFiltering("valorCobrado.greaterThan=" + SMALLER_VALOR_COBRADO, "valorCobrado.greaterThan=" + DEFAULT_VALOR_COBRADO);
    }

    @Test
    @Transactional
    void getAllPagamentosByAcrescimoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where acrescimo equals to
        defaultPagamentoFiltering("acrescimo.equals=" + DEFAULT_ACRESCIMO, "acrescimo.equals=" + UPDATED_ACRESCIMO);
    }

    @Test
    @Transactional
    void getAllPagamentosByAcrescimoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where acrescimo in
        defaultPagamentoFiltering("acrescimo.in=" + DEFAULT_ACRESCIMO + "," + UPDATED_ACRESCIMO, "acrescimo.in=" + UPDATED_ACRESCIMO);
    }

    @Test
    @Transactional
    void getAllPagamentosByAcrescimoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where acrescimo is not null
        defaultPagamentoFiltering("acrescimo.specified=true", "acrescimo.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByAcrescimoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where acrescimo is greater than or equal to
        defaultPagamentoFiltering("acrescimo.greaterThanOrEqual=" + DEFAULT_ACRESCIMO, "acrescimo.greaterThanOrEqual=" + UPDATED_ACRESCIMO);
    }

    @Test
    @Transactional
    void getAllPagamentosByAcrescimoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where acrescimo is less than or equal to
        defaultPagamentoFiltering("acrescimo.lessThanOrEqual=" + DEFAULT_ACRESCIMO, "acrescimo.lessThanOrEqual=" + SMALLER_ACRESCIMO);
    }

    @Test
    @Transactional
    void getAllPagamentosByAcrescimoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where acrescimo is less than
        defaultPagamentoFiltering("acrescimo.lessThan=" + UPDATED_ACRESCIMO, "acrescimo.lessThan=" + DEFAULT_ACRESCIMO);
    }

    @Test
    @Transactional
    void getAllPagamentosByAcrescimoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where acrescimo is greater than
        defaultPagamentoFiltering("acrescimo.greaterThan=" + SMALLER_ACRESCIMO, "acrescimo.greaterThan=" + DEFAULT_ACRESCIMO);
    }

    @Test
    @Transactional
    void getAllPagamentosByMultaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where multa equals to
        defaultPagamentoFiltering("multa.equals=" + DEFAULT_MULTA, "multa.equals=" + UPDATED_MULTA);
    }

    @Test
    @Transactional
    void getAllPagamentosByMultaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where multa in
        defaultPagamentoFiltering("multa.in=" + DEFAULT_MULTA + "," + UPDATED_MULTA, "multa.in=" + UPDATED_MULTA);
    }

    @Test
    @Transactional
    void getAllPagamentosByMultaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where multa is not null
        defaultPagamentoFiltering("multa.specified=true", "multa.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByMultaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where multa is greater than or equal to
        defaultPagamentoFiltering("multa.greaterThanOrEqual=" + DEFAULT_MULTA, "multa.greaterThanOrEqual=" + UPDATED_MULTA);
    }

    @Test
    @Transactional
    void getAllPagamentosByMultaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where multa is less than or equal to
        defaultPagamentoFiltering("multa.lessThanOrEqual=" + DEFAULT_MULTA, "multa.lessThanOrEqual=" + SMALLER_MULTA);
    }

    @Test
    @Transactional
    void getAllPagamentosByMultaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where multa is less than
        defaultPagamentoFiltering("multa.lessThan=" + UPDATED_MULTA, "multa.lessThan=" + DEFAULT_MULTA);
    }

    @Test
    @Transactional
    void getAllPagamentosByMultaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where multa is greater than
        defaultPagamentoFiltering("multa.greaterThan=" + SMALLER_MULTA, "multa.greaterThan=" + DEFAULT_MULTA);
    }

    @Test
    @Transactional
    void getAllPagamentosByJurosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where juros equals to
        defaultPagamentoFiltering("juros.equals=" + DEFAULT_JUROS, "juros.equals=" + UPDATED_JUROS);
    }

    @Test
    @Transactional
    void getAllPagamentosByJurosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where juros in
        defaultPagamentoFiltering("juros.in=" + DEFAULT_JUROS + "," + UPDATED_JUROS, "juros.in=" + UPDATED_JUROS);
    }

    @Test
    @Transactional
    void getAllPagamentosByJurosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where juros is not null
        defaultPagamentoFiltering("juros.specified=true", "juros.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByJurosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where juros is greater than or equal to
        defaultPagamentoFiltering("juros.greaterThanOrEqual=" + DEFAULT_JUROS, "juros.greaterThanOrEqual=" + UPDATED_JUROS);
    }

    @Test
    @Transactional
    void getAllPagamentosByJurosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where juros is less than or equal to
        defaultPagamentoFiltering("juros.lessThanOrEqual=" + DEFAULT_JUROS, "juros.lessThanOrEqual=" + SMALLER_JUROS);
    }

    @Test
    @Transactional
    void getAllPagamentosByJurosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where juros is less than
        defaultPagamentoFiltering("juros.lessThan=" + UPDATED_JUROS, "juros.lessThan=" + DEFAULT_JUROS);
    }

    @Test
    @Transactional
    void getAllPagamentosByJurosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where juros is greater than
        defaultPagamentoFiltering("juros.greaterThan=" + SMALLER_JUROS, "juros.greaterThan=" + DEFAULT_JUROS);
    }

    @Test
    @Transactional
    void getAllPagamentosBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where situacao equals to
        defaultPagamentoFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where situacao in
        defaultPagamentoFiltering("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO, "situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllPagamentosBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        // Get all the pagamentoList where situacao is not null
        defaultPagamentoFiltering("situacao.specified=true", "situacao.specified=false");
    }

    @Test
    @Transactional
    void getAllPagamentosByAssinaturaEmpresaIsEqualToSomething() throws Exception {
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            pagamentoRepository.saveAndFlush(pagamento);
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        em.persist(assinaturaEmpresa);
        em.flush();
        pagamento.setAssinaturaEmpresa(assinaturaEmpresa);
        pagamentoRepository.saveAndFlush(pagamento);
        Long assinaturaEmpresaId = assinaturaEmpresa.getId();
        // Get all the pagamentoList where assinaturaEmpresa equals to assinaturaEmpresaId
        defaultPagamentoShouldBeFound("assinaturaEmpresaId.equals=" + assinaturaEmpresaId);

        // Get all the pagamentoList where assinaturaEmpresa equals to (assinaturaEmpresaId + 1)
        defaultPagamentoShouldNotBeFound("assinaturaEmpresaId.equals=" + (assinaturaEmpresaId + 1));
    }

    private void defaultPagamentoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPagamentoShouldBeFound(shouldBeFound);
        defaultPagamentoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPagamentoShouldBeFound(String filter) throws Exception {
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCobranca").value(hasItem(DEFAULT_DATA_COBRANCA.toString())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].valorPago").value(hasItem(DEFAULT_VALOR_PAGO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorCobrado").value(hasItem(DEFAULT_VALOR_COBRADO.doubleValue())))
            .andExpect(jsonPath("$.[*].acrescimo").value(hasItem(DEFAULT_ACRESCIMO.doubleValue())))
            .andExpect(jsonPath("$.[*].multa").value(hasItem(DEFAULT_MULTA.doubleValue())))
            .andExpect(jsonPath("$.[*].juros").value(hasItem(DEFAULT_JUROS.doubleValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));

        // Check, that the count call also returns 1
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPagamentoShouldNotBeFound(String filter) throws Exception {
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPagamento() throws Exception {
        // Get the pagamento
        restPagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPagamento() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pagamento
        Pagamento updatedPagamento = pagamentoRepository.findById(pagamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPagamento are not directly saved in db
        em.detach(updatedPagamento);
        updatedPagamento
            .dataCobranca(UPDATED_DATA_COBRANCA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valorPago(UPDATED_VALOR_PAGO)
            .valorCobrado(UPDATED_VALOR_COBRADO)
            .acrescimo(UPDATED_ACRESCIMO)
            .multa(UPDATED_MULTA)
            .juros(UPDATED_JUROS)
            .situacao(UPDATED_SITUACAO);

        restPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPagamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPagamento))
            )
            .andExpect(status().isOk());

        // Validate the Pagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPagamentoToMatchAllProperties(updatedPagamento);
    }

    @Test
    @Transactional
    void putNonExistingPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pagamento.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pagamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pagamento using partial update
        Pagamento partialUpdatedPagamento = new Pagamento();
        partialUpdatedPagamento.setId(pagamento.getId());

        partialUpdatedPagamento.dataCobranca(UPDATED_DATA_COBRANCA).valorCobrado(UPDATED_VALOR_COBRADO);

        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPagamento))
            )
            .andExpect(status().isOk());

        // Validate the Pagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPagamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPagamento, pagamento),
            getPersistedPagamento(pagamento)
        );
    }

    @Test
    @Transactional
    void fullUpdatePagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pagamento using partial update
        Pagamento partialUpdatedPagamento = new Pagamento();
        partialUpdatedPagamento.setId(pagamento.getId());

        partialUpdatedPagamento
            .dataCobranca(UPDATED_DATA_COBRANCA)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .valorPago(UPDATED_VALOR_PAGO)
            .valorCobrado(UPDATED_VALOR_COBRADO)
            .acrescimo(UPDATED_ACRESCIMO)
            .multa(UPDATED_MULTA)
            .juros(UPDATED_JUROS)
            .situacao(UPDATED_SITUACAO);

        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPagamento))
            )
            .andExpect(status().isOk());

        // Validate the Pagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPagamentoUpdatableFieldsEquals(partialUpdatedPagamento, getPersistedPagamento(partialUpdatedPagamento));
    }

    @Test
    @Transactional
    void patchNonExistingPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPagamentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pagamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePagamento() throws Exception {
        // Initialize the database
        insertedPagamento = pagamentoRepository.saveAndFlush(pagamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pagamento
        restPagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pagamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pagamentoRepository.count();
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

    protected Pagamento getPersistedPagamento(Pagamento pagamento) {
        return pagamentoRepository.findById(pagamento.getId()).orElseThrow();
    }

    protected void assertPersistedPagamentoToMatchAllProperties(Pagamento expectedPagamento) {
        assertPagamentoAllPropertiesEquals(expectedPagamento, getPersistedPagamento(expectedPagamento));
    }

    protected void assertPersistedPagamentoToMatchUpdatableProperties(Pagamento expectedPagamento) {
        assertPagamentoAllUpdatablePropertiesEquals(expectedPagamento, getPersistedPagamento(expectedPagamento));
    }
}
