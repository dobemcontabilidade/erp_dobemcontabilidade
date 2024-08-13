package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DemissaoFuncionarioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DemissaoFuncionario;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.domain.enumeration.AvisoPrevioEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoDemissaoEnum;
import com.dobemcontabilidade.domain.enumeration.TipoDemissaoEnum;
import com.dobemcontabilidade.repository.DemissaoFuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DemissaoFuncionarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemissaoFuncionarioResourceIT {

    private static final String DEFAULT_NUMERO_CERTIDAO_OBITO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CERTIDAO_OBITO = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ_EMPRESA_SUCESSORA = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ_EMPRESA_SUCESSORA = "BBBBBBBBBB";

    private static final String DEFAULT_SALDO_FGTS = "AAAAAAAAAA";
    private static final String UPDATED_SALDO_FGTS = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_PENSAO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_PENSAO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_PENSAO_FGTS = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_PENSAO_FGTS = "BBBBBBBBBB";

    private static final String DEFAULT_PERCENTUAL_PENSAO = "AAAAAAAAAA";
    private static final String UPDATED_PERCENTUAL_PENSAO = "BBBBBBBBBB";

    private static final String DEFAULT_PERCENTUAL_FGTS = "AAAAAAAAAA";
    private static final String UPDATED_PERCENTUAL_FGTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIAS_AVISO_PREVIO = 1;
    private static final Integer UPDATED_DIAS_AVISO_PREVIO = 2;

    private static final String DEFAULT_DATA_AVISO_PREVIO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_AVISO_PREVIO = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_PAGAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_PAGAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_AFASTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_AFASTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_URL_DEMISSIONAL = "AAAAAAAAAA";
    private static final String UPDATED_URL_DEMISSIONAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CALCULAR_RECISAO = false;
    private static final Boolean UPDATED_CALCULAR_RECISAO = true;

    private static final Boolean DEFAULT_PAGAR_13_RECISAO = false;
    private static final Boolean UPDATED_PAGAR_13_RECISAO = true;

    private static final Boolean DEFAULT_JORNADA_TRABALHO_CUMPRIDA_SEMANA = false;
    private static final Boolean UPDATED_JORNADA_TRABALHO_CUMPRIDA_SEMANA = true;

    private static final Boolean DEFAULT_SABADO_COMPESADO = false;
    private static final Boolean UPDATED_SABADO_COMPESADO = true;

    private static final Boolean DEFAULT_NOVO_VINCULO_COMPROVADO = false;
    private static final Boolean UPDATED_NOVO_VINCULO_COMPROVADO = true;

    private static final Boolean DEFAULT_DISPENSA_AVISO_PREVIO = false;
    private static final Boolean UPDATED_DISPENSA_AVISO_PREVIO = true;

    private static final Boolean DEFAULT_FGTS_ARRECADADO_GUIA = false;
    private static final Boolean UPDATED_FGTS_ARRECADADO_GUIA = true;

    private static final Boolean DEFAULT_AVISO_PREVIO_TRABALHADO_RECEBIDO = false;
    private static final Boolean UPDATED_AVISO_PREVIO_TRABALHADO_RECEBIDO = true;

    private static final Boolean DEFAULT_RECOLHER_FGTS_MES_ANTERIOR = false;
    private static final Boolean UPDATED_RECOLHER_FGTS_MES_ANTERIOR = true;

    private static final Boolean DEFAULT_AVISO_PREVIO_INDENIZADO = false;
    private static final Boolean UPDATED_AVISO_PREVIO_INDENIZADO = true;

    private static final Integer DEFAULT_CUMPRIMENTO_AVISO_PREVIO = 1;
    private static final Integer UPDATED_CUMPRIMENTO_AVISO_PREVIO = 2;

    private static final AvisoPrevioEnum DEFAULT_AVISO_PREVIO = AvisoPrevioEnum.NAOPOSSUI;
    private static final AvisoPrevioEnum UPDATED_AVISO_PREVIO = AvisoPrevioEnum.VINTEDIAS;

    private static final SituacaoDemissaoEnum DEFAULT_SITUACAO_DEMISSAO = SituacaoDemissaoEnum.SOLICITADO;
    private static final SituacaoDemissaoEnum UPDATED_SITUACAO_DEMISSAO = SituacaoDemissaoEnum.GERADO;

    private static final TipoDemissaoEnum DEFAULT_TIPO_DEMISSAO = TipoDemissaoEnum.JUSTACAUSA;
    private static final TipoDemissaoEnum UPDATED_TIPO_DEMISSAO = TipoDemissaoEnum.DESPEDIDASEMJUSTACAUSA;

    private static final String ENTITY_API_URL = "/api/demissao-funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DemissaoFuncionarioRepository demissaoFuncionarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemissaoFuncionarioMockMvc;

    private DemissaoFuncionario demissaoFuncionario;

    private DemissaoFuncionario insertedDemissaoFuncionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemissaoFuncionario createEntity(EntityManager em) {
        DemissaoFuncionario demissaoFuncionario = new DemissaoFuncionario()
            .numeroCertidaoObito(DEFAULT_NUMERO_CERTIDAO_OBITO)
            .cnpjEmpresaSucessora(DEFAULT_CNPJ_EMPRESA_SUCESSORA)
            .saldoFGTS(DEFAULT_SALDO_FGTS)
            .valorPensao(DEFAULT_VALOR_PENSAO)
            .valorPensaoFgts(DEFAULT_VALOR_PENSAO_FGTS)
            .percentualPensao(DEFAULT_PERCENTUAL_PENSAO)
            .percentualFgts(DEFAULT_PERCENTUAL_FGTS)
            .diasAvisoPrevio(DEFAULT_DIAS_AVISO_PREVIO)
            .dataAvisoPrevio(DEFAULT_DATA_AVISO_PREVIO)
            .dataPagamento(DEFAULT_DATA_PAGAMENTO)
            .dataAfastamento(DEFAULT_DATA_AFASTAMENTO)
            .urlDemissional(DEFAULT_URL_DEMISSIONAL)
            .calcularRecisao(DEFAULT_CALCULAR_RECISAO)
            .pagar13Recisao(DEFAULT_PAGAR_13_RECISAO)
            .jornadaTrabalhoCumpridaSemana(DEFAULT_JORNADA_TRABALHO_CUMPRIDA_SEMANA)
            .sabadoCompesado(DEFAULT_SABADO_COMPESADO)
            .novoVinculoComprovado(DEFAULT_NOVO_VINCULO_COMPROVADO)
            .dispensaAvisoPrevio(DEFAULT_DISPENSA_AVISO_PREVIO)
            .fgtsArrecadadoGuia(DEFAULT_FGTS_ARRECADADO_GUIA)
            .avisoPrevioTrabalhadoRecebido(DEFAULT_AVISO_PREVIO_TRABALHADO_RECEBIDO)
            .recolherFgtsMesAnterior(DEFAULT_RECOLHER_FGTS_MES_ANTERIOR)
            .avisoPrevioIndenizado(DEFAULT_AVISO_PREVIO_INDENIZADO)
            .cumprimentoAvisoPrevio(DEFAULT_CUMPRIMENTO_AVISO_PREVIO)
            .avisoPrevio(DEFAULT_AVISO_PREVIO)
            .situacaoDemissao(DEFAULT_SITUACAO_DEMISSAO)
            .tipoDemissao(DEFAULT_TIPO_DEMISSAO);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        demissaoFuncionario.setFuncionario(funcionario);
        return demissaoFuncionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemissaoFuncionario createUpdatedEntity(EntityManager em) {
        DemissaoFuncionario demissaoFuncionario = new DemissaoFuncionario()
            .numeroCertidaoObito(UPDATED_NUMERO_CERTIDAO_OBITO)
            .cnpjEmpresaSucessora(UPDATED_CNPJ_EMPRESA_SUCESSORA)
            .saldoFGTS(UPDATED_SALDO_FGTS)
            .valorPensao(UPDATED_VALOR_PENSAO)
            .valorPensaoFgts(UPDATED_VALOR_PENSAO_FGTS)
            .percentualPensao(UPDATED_PERCENTUAL_PENSAO)
            .percentualFgts(UPDATED_PERCENTUAL_FGTS)
            .diasAvisoPrevio(UPDATED_DIAS_AVISO_PREVIO)
            .dataAvisoPrevio(UPDATED_DATA_AVISO_PREVIO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .dataAfastamento(UPDATED_DATA_AFASTAMENTO)
            .urlDemissional(UPDATED_URL_DEMISSIONAL)
            .calcularRecisao(UPDATED_CALCULAR_RECISAO)
            .pagar13Recisao(UPDATED_PAGAR_13_RECISAO)
            .jornadaTrabalhoCumpridaSemana(UPDATED_JORNADA_TRABALHO_CUMPRIDA_SEMANA)
            .sabadoCompesado(UPDATED_SABADO_COMPESADO)
            .novoVinculoComprovado(UPDATED_NOVO_VINCULO_COMPROVADO)
            .dispensaAvisoPrevio(UPDATED_DISPENSA_AVISO_PREVIO)
            .fgtsArrecadadoGuia(UPDATED_FGTS_ARRECADADO_GUIA)
            .avisoPrevioTrabalhadoRecebido(UPDATED_AVISO_PREVIO_TRABALHADO_RECEBIDO)
            .recolherFgtsMesAnterior(UPDATED_RECOLHER_FGTS_MES_ANTERIOR)
            .avisoPrevioIndenizado(UPDATED_AVISO_PREVIO_INDENIZADO)
            .cumprimentoAvisoPrevio(UPDATED_CUMPRIMENTO_AVISO_PREVIO)
            .avisoPrevio(UPDATED_AVISO_PREVIO)
            .situacaoDemissao(UPDATED_SITUACAO_DEMISSAO)
            .tipoDemissao(UPDATED_TIPO_DEMISSAO);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createUpdatedEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        demissaoFuncionario.setFuncionario(funcionario);
        return demissaoFuncionario;
    }

    @BeforeEach
    public void initTest() {
        demissaoFuncionario = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDemissaoFuncionario != null) {
            demissaoFuncionarioRepository.delete(insertedDemissaoFuncionario);
            insertedDemissaoFuncionario = null;
        }
    }

    @Test
    @Transactional
    void createDemissaoFuncionario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DemissaoFuncionario
        var returnedDemissaoFuncionario = om.readValue(
            restDemissaoFuncionarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demissaoFuncionario)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DemissaoFuncionario.class
        );

        // Validate the DemissaoFuncionario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDemissaoFuncionarioUpdatableFieldsEquals(
            returnedDemissaoFuncionario,
            getPersistedDemissaoFuncionario(returnedDemissaoFuncionario)
        );

        insertedDemissaoFuncionario = returnedDemissaoFuncionario;
    }

    @Test
    @Transactional
    void createDemissaoFuncionarioWithExistingId() throws Exception {
        // Create the DemissaoFuncionario with an existing ID
        demissaoFuncionario.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemissaoFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demissaoFuncionario)))
            .andExpect(status().isBadRequest());

        // Validate the DemissaoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemissaoFuncionarios() throws Exception {
        // Initialize the database
        insertedDemissaoFuncionario = demissaoFuncionarioRepository.saveAndFlush(demissaoFuncionario);

        // Get all the demissaoFuncionarioList
        restDemissaoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demissaoFuncionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroCertidaoObito").value(hasItem(DEFAULT_NUMERO_CERTIDAO_OBITO)))
            .andExpect(jsonPath("$.[*].cnpjEmpresaSucessora").value(hasItem(DEFAULT_CNPJ_EMPRESA_SUCESSORA)))
            .andExpect(jsonPath("$.[*].saldoFGTS").value(hasItem(DEFAULT_SALDO_FGTS)))
            .andExpect(jsonPath("$.[*].valorPensao").value(hasItem(DEFAULT_VALOR_PENSAO)))
            .andExpect(jsonPath("$.[*].valorPensaoFgts").value(hasItem(DEFAULT_VALOR_PENSAO_FGTS)))
            .andExpect(jsonPath("$.[*].percentualPensao").value(hasItem(DEFAULT_PERCENTUAL_PENSAO)))
            .andExpect(jsonPath("$.[*].percentualFgts").value(hasItem(DEFAULT_PERCENTUAL_FGTS)))
            .andExpect(jsonPath("$.[*].diasAvisoPrevio").value(hasItem(DEFAULT_DIAS_AVISO_PREVIO)))
            .andExpect(jsonPath("$.[*].dataAvisoPrevio").value(hasItem(DEFAULT_DATA_AVISO_PREVIO)))
            .andExpect(jsonPath("$.[*].dataPagamento").value(hasItem(DEFAULT_DATA_PAGAMENTO)))
            .andExpect(jsonPath("$.[*].dataAfastamento").value(hasItem(DEFAULT_DATA_AFASTAMENTO)))
            .andExpect(jsonPath("$.[*].urlDemissional").value(hasItem(DEFAULT_URL_DEMISSIONAL)))
            .andExpect(jsonPath("$.[*].calcularRecisao").value(hasItem(DEFAULT_CALCULAR_RECISAO.booleanValue())))
            .andExpect(jsonPath("$.[*].pagar13Recisao").value(hasItem(DEFAULT_PAGAR_13_RECISAO.booleanValue())))
            .andExpect(
                jsonPath("$.[*].jornadaTrabalhoCumpridaSemana").value(hasItem(DEFAULT_JORNADA_TRABALHO_CUMPRIDA_SEMANA.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].sabadoCompesado").value(hasItem(DEFAULT_SABADO_COMPESADO.booleanValue())))
            .andExpect(jsonPath("$.[*].novoVinculoComprovado").value(hasItem(DEFAULT_NOVO_VINCULO_COMPROVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].dispensaAvisoPrevio").value(hasItem(DEFAULT_DISPENSA_AVISO_PREVIO.booleanValue())))
            .andExpect(jsonPath("$.[*].fgtsArrecadadoGuia").value(hasItem(DEFAULT_FGTS_ARRECADADO_GUIA.booleanValue())))
            .andExpect(
                jsonPath("$.[*].avisoPrevioTrabalhadoRecebido").value(hasItem(DEFAULT_AVISO_PREVIO_TRABALHADO_RECEBIDO.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].recolherFgtsMesAnterior").value(hasItem(DEFAULT_RECOLHER_FGTS_MES_ANTERIOR.booleanValue())))
            .andExpect(jsonPath("$.[*].avisoPrevioIndenizado").value(hasItem(DEFAULT_AVISO_PREVIO_INDENIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].cumprimentoAvisoPrevio").value(hasItem(DEFAULT_CUMPRIMENTO_AVISO_PREVIO)))
            .andExpect(jsonPath("$.[*].avisoPrevio").value(hasItem(DEFAULT_AVISO_PREVIO.toString())))
            .andExpect(jsonPath("$.[*].situacaoDemissao").value(hasItem(DEFAULT_SITUACAO_DEMISSAO.toString())))
            .andExpect(jsonPath("$.[*].tipoDemissao").value(hasItem(DEFAULT_TIPO_DEMISSAO.toString())));
    }

    @Test
    @Transactional
    void getDemissaoFuncionario() throws Exception {
        // Initialize the database
        insertedDemissaoFuncionario = demissaoFuncionarioRepository.saveAndFlush(demissaoFuncionario);

        // Get the demissaoFuncionario
        restDemissaoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, demissaoFuncionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demissaoFuncionario.getId().intValue()))
            .andExpect(jsonPath("$.numeroCertidaoObito").value(DEFAULT_NUMERO_CERTIDAO_OBITO))
            .andExpect(jsonPath("$.cnpjEmpresaSucessora").value(DEFAULT_CNPJ_EMPRESA_SUCESSORA))
            .andExpect(jsonPath("$.saldoFGTS").value(DEFAULT_SALDO_FGTS))
            .andExpect(jsonPath("$.valorPensao").value(DEFAULT_VALOR_PENSAO))
            .andExpect(jsonPath("$.valorPensaoFgts").value(DEFAULT_VALOR_PENSAO_FGTS))
            .andExpect(jsonPath("$.percentualPensao").value(DEFAULT_PERCENTUAL_PENSAO))
            .andExpect(jsonPath("$.percentualFgts").value(DEFAULT_PERCENTUAL_FGTS))
            .andExpect(jsonPath("$.diasAvisoPrevio").value(DEFAULT_DIAS_AVISO_PREVIO))
            .andExpect(jsonPath("$.dataAvisoPrevio").value(DEFAULT_DATA_AVISO_PREVIO))
            .andExpect(jsonPath("$.dataPagamento").value(DEFAULT_DATA_PAGAMENTO))
            .andExpect(jsonPath("$.dataAfastamento").value(DEFAULT_DATA_AFASTAMENTO))
            .andExpect(jsonPath("$.urlDemissional").value(DEFAULT_URL_DEMISSIONAL))
            .andExpect(jsonPath("$.calcularRecisao").value(DEFAULT_CALCULAR_RECISAO.booleanValue()))
            .andExpect(jsonPath("$.pagar13Recisao").value(DEFAULT_PAGAR_13_RECISAO.booleanValue()))
            .andExpect(jsonPath("$.jornadaTrabalhoCumpridaSemana").value(DEFAULT_JORNADA_TRABALHO_CUMPRIDA_SEMANA.booleanValue()))
            .andExpect(jsonPath("$.sabadoCompesado").value(DEFAULT_SABADO_COMPESADO.booleanValue()))
            .andExpect(jsonPath("$.novoVinculoComprovado").value(DEFAULT_NOVO_VINCULO_COMPROVADO.booleanValue()))
            .andExpect(jsonPath("$.dispensaAvisoPrevio").value(DEFAULT_DISPENSA_AVISO_PREVIO.booleanValue()))
            .andExpect(jsonPath("$.fgtsArrecadadoGuia").value(DEFAULT_FGTS_ARRECADADO_GUIA.booleanValue()))
            .andExpect(jsonPath("$.avisoPrevioTrabalhadoRecebido").value(DEFAULT_AVISO_PREVIO_TRABALHADO_RECEBIDO.booleanValue()))
            .andExpect(jsonPath("$.recolherFgtsMesAnterior").value(DEFAULT_RECOLHER_FGTS_MES_ANTERIOR.booleanValue()))
            .andExpect(jsonPath("$.avisoPrevioIndenizado").value(DEFAULT_AVISO_PREVIO_INDENIZADO.booleanValue()))
            .andExpect(jsonPath("$.cumprimentoAvisoPrevio").value(DEFAULT_CUMPRIMENTO_AVISO_PREVIO))
            .andExpect(jsonPath("$.avisoPrevio").value(DEFAULT_AVISO_PREVIO.toString()))
            .andExpect(jsonPath("$.situacaoDemissao").value(DEFAULT_SITUACAO_DEMISSAO.toString()))
            .andExpect(jsonPath("$.tipoDemissao").value(DEFAULT_TIPO_DEMISSAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemissaoFuncionario() throws Exception {
        // Get the demissaoFuncionario
        restDemissaoFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemissaoFuncionario() throws Exception {
        // Initialize the database
        insertedDemissaoFuncionario = demissaoFuncionarioRepository.saveAndFlush(demissaoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demissaoFuncionario
        DemissaoFuncionario updatedDemissaoFuncionario = demissaoFuncionarioRepository.findById(demissaoFuncionario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDemissaoFuncionario are not directly saved in db
        em.detach(updatedDemissaoFuncionario);
        updatedDemissaoFuncionario
            .numeroCertidaoObito(UPDATED_NUMERO_CERTIDAO_OBITO)
            .cnpjEmpresaSucessora(UPDATED_CNPJ_EMPRESA_SUCESSORA)
            .saldoFGTS(UPDATED_SALDO_FGTS)
            .valorPensao(UPDATED_VALOR_PENSAO)
            .valorPensaoFgts(UPDATED_VALOR_PENSAO_FGTS)
            .percentualPensao(UPDATED_PERCENTUAL_PENSAO)
            .percentualFgts(UPDATED_PERCENTUAL_FGTS)
            .diasAvisoPrevio(UPDATED_DIAS_AVISO_PREVIO)
            .dataAvisoPrevio(UPDATED_DATA_AVISO_PREVIO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .dataAfastamento(UPDATED_DATA_AFASTAMENTO)
            .urlDemissional(UPDATED_URL_DEMISSIONAL)
            .calcularRecisao(UPDATED_CALCULAR_RECISAO)
            .pagar13Recisao(UPDATED_PAGAR_13_RECISAO)
            .jornadaTrabalhoCumpridaSemana(UPDATED_JORNADA_TRABALHO_CUMPRIDA_SEMANA)
            .sabadoCompesado(UPDATED_SABADO_COMPESADO)
            .novoVinculoComprovado(UPDATED_NOVO_VINCULO_COMPROVADO)
            .dispensaAvisoPrevio(UPDATED_DISPENSA_AVISO_PREVIO)
            .fgtsArrecadadoGuia(UPDATED_FGTS_ARRECADADO_GUIA)
            .avisoPrevioTrabalhadoRecebido(UPDATED_AVISO_PREVIO_TRABALHADO_RECEBIDO)
            .recolherFgtsMesAnterior(UPDATED_RECOLHER_FGTS_MES_ANTERIOR)
            .avisoPrevioIndenizado(UPDATED_AVISO_PREVIO_INDENIZADO)
            .cumprimentoAvisoPrevio(UPDATED_CUMPRIMENTO_AVISO_PREVIO)
            .avisoPrevio(UPDATED_AVISO_PREVIO)
            .situacaoDemissao(UPDATED_SITUACAO_DEMISSAO)
            .tipoDemissao(UPDATED_TIPO_DEMISSAO);

        restDemissaoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemissaoFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDemissaoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the DemissaoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDemissaoFuncionarioToMatchAllProperties(updatedDemissaoFuncionario);
    }

    @Test
    @Transactional
    void putNonExistingDemissaoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demissaoFuncionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemissaoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demissaoFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demissaoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemissaoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemissaoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demissaoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemissaoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demissaoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemissaoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemissaoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demissaoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemissaoFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demissaoFuncionario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemissaoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemissaoFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedDemissaoFuncionario = demissaoFuncionarioRepository.saveAndFlush(demissaoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demissaoFuncionario using partial update
        DemissaoFuncionario partialUpdatedDemissaoFuncionario = new DemissaoFuncionario();
        partialUpdatedDemissaoFuncionario.setId(demissaoFuncionario.getId());

        partialUpdatedDemissaoFuncionario
            .valorPensaoFgts(UPDATED_VALOR_PENSAO_FGTS)
            .dataAvisoPrevio(UPDATED_DATA_AVISO_PREVIO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .dataAfastamento(UPDATED_DATA_AFASTAMENTO)
            .urlDemissional(UPDATED_URL_DEMISSIONAL)
            .pagar13Recisao(UPDATED_PAGAR_13_RECISAO)
            .sabadoCompesado(UPDATED_SABADO_COMPESADO)
            .novoVinculoComprovado(UPDATED_NOVO_VINCULO_COMPROVADO)
            .fgtsArrecadadoGuia(UPDATED_FGTS_ARRECADADO_GUIA)
            .avisoPrevioTrabalhadoRecebido(UPDATED_AVISO_PREVIO_TRABALHADO_RECEBIDO)
            .recolherFgtsMesAnterior(UPDATED_RECOLHER_FGTS_MES_ANTERIOR)
            .avisoPrevioIndenizado(UPDATED_AVISO_PREVIO_INDENIZADO)
            .cumprimentoAvisoPrevio(UPDATED_CUMPRIMENTO_AVISO_PREVIO)
            .avisoPrevio(UPDATED_AVISO_PREVIO)
            .tipoDemissao(UPDATED_TIPO_DEMISSAO);

        restDemissaoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemissaoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemissaoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the DemissaoFuncionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemissaoFuncionarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDemissaoFuncionario, demissaoFuncionario),
            getPersistedDemissaoFuncionario(demissaoFuncionario)
        );
    }

    @Test
    @Transactional
    void fullUpdateDemissaoFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedDemissaoFuncionario = demissaoFuncionarioRepository.saveAndFlush(demissaoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demissaoFuncionario using partial update
        DemissaoFuncionario partialUpdatedDemissaoFuncionario = new DemissaoFuncionario();
        partialUpdatedDemissaoFuncionario.setId(demissaoFuncionario.getId());

        partialUpdatedDemissaoFuncionario
            .numeroCertidaoObito(UPDATED_NUMERO_CERTIDAO_OBITO)
            .cnpjEmpresaSucessora(UPDATED_CNPJ_EMPRESA_SUCESSORA)
            .saldoFGTS(UPDATED_SALDO_FGTS)
            .valorPensao(UPDATED_VALOR_PENSAO)
            .valorPensaoFgts(UPDATED_VALOR_PENSAO_FGTS)
            .percentualPensao(UPDATED_PERCENTUAL_PENSAO)
            .percentualFgts(UPDATED_PERCENTUAL_FGTS)
            .diasAvisoPrevio(UPDATED_DIAS_AVISO_PREVIO)
            .dataAvisoPrevio(UPDATED_DATA_AVISO_PREVIO)
            .dataPagamento(UPDATED_DATA_PAGAMENTO)
            .dataAfastamento(UPDATED_DATA_AFASTAMENTO)
            .urlDemissional(UPDATED_URL_DEMISSIONAL)
            .calcularRecisao(UPDATED_CALCULAR_RECISAO)
            .pagar13Recisao(UPDATED_PAGAR_13_RECISAO)
            .jornadaTrabalhoCumpridaSemana(UPDATED_JORNADA_TRABALHO_CUMPRIDA_SEMANA)
            .sabadoCompesado(UPDATED_SABADO_COMPESADO)
            .novoVinculoComprovado(UPDATED_NOVO_VINCULO_COMPROVADO)
            .dispensaAvisoPrevio(UPDATED_DISPENSA_AVISO_PREVIO)
            .fgtsArrecadadoGuia(UPDATED_FGTS_ARRECADADO_GUIA)
            .avisoPrevioTrabalhadoRecebido(UPDATED_AVISO_PREVIO_TRABALHADO_RECEBIDO)
            .recolherFgtsMesAnterior(UPDATED_RECOLHER_FGTS_MES_ANTERIOR)
            .avisoPrevioIndenizado(UPDATED_AVISO_PREVIO_INDENIZADO)
            .cumprimentoAvisoPrevio(UPDATED_CUMPRIMENTO_AVISO_PREVIO)
            .avisoPrevio(UPDATED_AVISO_PREVIO)
            .situacaoDemissao(UPDATED_SITUACAO_DEMISSAO)
            .tipoDemissao(UPDATED_TIPO_DEMISSAO);

        restDemissaoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemissaoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemissaoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the DemissaoFuncionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemissaoFuncionarioUpdatableFieldsEquals(
            partialUpdatedDemissaoFuncionario,
            getPersistedDemissaoFuncionario(partialUpdatedDemissaoFuncionario)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDemissaoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demissaoFuncionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemissaoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demissaoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demissaoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemissaoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemissaoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demissaoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemissaoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demissaoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemissaoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemissaoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demissaoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemissaoFuncionarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(demissaoFuncionario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemissaoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemissaoFuncionario() throws Exception {
        // Initialize the database
        insertedDemissaoFuncionario = demissaoFuncionarioRepository.saveAndFlush(demissaoFuncionario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the demissaoFuncionario
        restDemissaoFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, demissaoFuncionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return demissaoFuncionarioRepository.count();
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

    protected DemissaoFuncionario getPersistedDemissaoFuncionario(DemissaoFuncionario demissaoFuncionario) {
        return demissaoFuncionarioRepository.findById(demissaoFuncionario.getId()).orElseThrow();
    }

    protected void assertPersistedDemissaoFuncionarioToMatchAllProperties(DemissaoFuncionario expectedDemissaoFuncionario) {
        assertDemissaoFuncionarioAllPropertiesEquals(
            expectedDemissaoFuncionario,
            getPersistedDemissaoFuncionario(expectedDemissaoFuncionario)
        );
    }

    protected void assertPersistedDemissaoFuncionarioToMatchUpdatableProperties(DemissaoFuncionario expectedDemissaoFuncionario) {
        assertDemissaoFuncionarioAllUpdatablePropertiesEquals(
            expectedDemissaoFuncionario,
            getPersistedDemissaoFuncionario(expectedDemissaoFuncionario)
        );
    }
}
