package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ContratoFuncionarioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ContratoFuncionario;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.domain.enumeration.CategoriaTrabalhadorEnum;
import com.dobemcontabilidade.domain.enumeration.FgtsOpcaoEnum;
import com.dobemcontabilidade.domain.enumeration.IndicativoAdmissaoEnum;
import com.dobemcontabilidade.domain.enumeration.NaturezaEstagioEnum;
import com.dobemcontabilidade.domain.enumeration.PeriodoExperienciaEnum;
import com.dobemcontabilidade.domain.enumeration.PeriodoIntermitenteEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoFuncionarioEnum;
import com.dobemcontabilidade.domain.enumeration.TipoAdmisaoEnum;
import com.dobemcontabilidade.domain.enumeration.TipoDocumentoEnum;
import com.dobemcontabilidade.domain.enumeration.TipoVinculoTrabalhoEnum;
import com.dobemcontabilidade.repository.ContratoFuncionarioRepository;
import com.dobemcontabilidade.service.ContratoFuncionarioService;
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
 * Integration tests for the {@link ContratoFuncionarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContratoFuncionarioResourceIT {

    private static final Boolean DEFAULT_SALARIO_FIXO = false;
    private static final Boolean UPDATED_SALARIO_FIXO = true;

    private static final Boolean DEFAULT_SALARIO_VARIAVEL = false;
    private static final Boolean UPDATED_SALARIO_VARIAVEL = true;

    private static final Boolean DEFAULT_ESTAGIO = false;
    private static final Boolean UPDATED_ESTAGIO = true;

    private static final NaturezaEstagioEnum DEFAULT_NATUREZA_ESTAGIO_ENUM = NaturezaEstagioEnum.OBRIGATORIO;
    private static final NaturezaEstagioEnum UPDATED_NATUREZA_ESTAGIO_ENUM = NaturezaEstagioEnum.NAOOBRIGATORIO;

    private static final String DEFAULT_CTPS = "AAAAAAAAAA";
    private static final String UPDATED_CTPS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SERIE_CTPS = 1;
    private static final Integer UPDATED_SERIE_CTPS = 2;

    private static final String DEFAULT_ORGAO_EMISSOR_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ORGAO_EMISSOR_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_VALIDADE_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_VALIDADE_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_ADMISSAO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ADMISSAO = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_ATIVIDADES = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_ATIVIDADES = "BBBBBBBBBB";

    private static final SituacaoFuncionarioEnum DEFAULT_SITUACAO = SituacaoFuncionarioEnum.CADASTROEMANDAMENTO;
    private static final SituacaoFuncionarioEnum UPDATED_SITUACAO = SituacaoFuncionarioEnum.CADASTRADO;

    private static final String DEFAULT_VALOR_SALARIO_FIXO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_SALARIO_FIXO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR_SALARIO_VARIAVEL = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_SALARIO_VARIAVEL = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_TERMINO_CONTRATO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_TERMINO_CONTRATO = "BBBBBBBBBB";

    private static final String DEFAULT_DATAINICIO_CONTRATO = "AAAAAAAAAA";
    private static final String UPDATED_DATAINICIO_CONTRATO = "BBBBBBBBBB";

    private static final Integer DEFAULT_HORAS_A_TRABALHADAR = 1;
    private static final Integer UPDATED_HORAS_A_TRABALHADAR = 2;

    private static final String DEFAULT_CODIGO_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_CARGO = "BBBBBBBBBB";

    private static final CategoriaTrabalhadorEnum DEFAULT_CATEGORIA_TRABALHADOR = CategoriaTrabalhadorEnum.EMPREGADO;
    private static final CategoriaTrabalhadorEnum UPDATED_CATEGORIA_TRABALHADOR = CategoriaTrabalhadorEnum.ESTAGIARIO;

    private static final TipoVinculoTrabalhoEnum DEFAULT_TIPO_VINCULO_TRABALHO = TipoVinculoTrabalhoEnum.URBANO;
    private static final TipoVinculoTrabalhoEnum UPDATED_TIPO_VINCULO_TRABALHO = TipoVinculoTrabalhoEnum.RURAL;

    private static final FgtsOpcaoEnum DEFAULT_FGTS_OPCAO = FgtsOpcaoEnum.OPTANTE;
    private static final FgtsOpcaoEnum UPDATED_FGTS_OPCAO = FgtsOpcaoEnum.NAOOPTANTE;

    private static final TipoDocumentoEnum DEFAULT_T_IPO_DOCUMENTO_ENUM = TipoDocumentoEnum.RG;
    private static final TipoDocumentoEnum UPDATED_T_IPO_DOCUMENTO_ENUM = TipoDocumentoEnum.CNH;

    private static final PeriodoExperienciaEnum DEFAULT_PERIODO_EXPERIENCIA = PeriodoExperienciaEnum.QUINZE;
    private static final PeriodoExperienciaEnum UPDATED_PERIODO_EXPERIENCIA = PeriodoExperienciaEnum.TRINTA;

    private static final TipoAdmisaoEnum DEFAULT_TIPO_ADMISAO_ENUM = TipoAdmisaoEnum.ADMISSAO;
    private static final TipoAdmisaoEnum UPDATED_TIPO_ADMISAO_ENUM = TipoAdmisaoEnum.TRANSFERENCIA;

    private static final PeriodoIntermitenteEnum DEFAULT_PERIODO_INTERMITENTE = PeriodoIntermitenteEnum.QUATROMESES;
    private static final PeriodoIntermitenteEnum UPDATED_PERIODO_INTERMITENTE = PeriodoIntermitenteEnum.CINCOMESES;

    private static final IndicativoAdmissaoEnum DEFAULT_INDICATIVO_ADMISSAO = IndicativoAdmissaoEnum.NORMAL;
    private static final IndicativoAdmissaoEnum UPDATED_INDICATIVO_ADMISSAO = IndicativoAdmissaoEnum.ACAOFISCAL;

    private static final Integer DEFAULT_NUMERO_PIS_NIS_PASEP = 1;
    private static final Integer UPDATED_NUMERO_PIS_NIS_PASEP = 2;

    private static final String ENTITY_API_URL = "/api/contrato-funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContratoFuncionarioRepository contratoFuncionarioRepository;

    @Mock
    private ContratoFuncionarioRepository contratoFuncionarioRepositoryMock;

    @Mock
    private ContratoFuncionarioService contratoFuncionarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContratoFuncionarioMockMvc;

    private ContratoFuncionario contratoFuncionario;

    private ContratoFuncionario insertedContratoFuncionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContratoFuncionario createEntity(EntityManager em) {
        ContratoFuncionario contratoFuncionario = new ContratoFuncionario()
            .salarioFixo(DEFAULT_SALARIO_FIXO)
            .salarioVariavel(DEFAULT_SALARIO_VARIAVEL)
            .estagio(DEFAULT_ESTAGIO)
            .naturezaEstagioEnum(DEFAULT_NATUREZA_ESTAGIO_ENUM)
            .ctps(DEFAULT_CTPS)
            .serieCtps(DEFAULT_SERIE_CTPS)
            .orgaoEmissorDocumento(DEFAULT_ORGAO_EMISSOR_DOCUMENTO)
            .dataValidadeDocumento(DEFAULT_DATA_VALIDADE_DOCUMENTO)
            .dataAdmissao(DEFAULT_DATA_ADMISSAO)
            .cargo(DEFAULT_CARGO)
            .descricaoAtividades(DEFAULT_DESCRICAO_ATIVIDADES)
            .situacao(DEFAULT_SITUACAO)
            .valorSalarioFixo(DEFAULT_VALOR_SALARIO_FIXO)
            .valorSalarioVariavel(DEFAULT_VALOR_SALARIO_VARIAVEL)
            .dataTerminoContrato(DEFAULT_DATA_TERMINO_CONTRATO)
            .datainicioContrato(DEFAULT_DATAINICIO_CONTRATO)
            .horasATrabalhadar(DEFAULT_HORAS_A_TRABALHADAR)
            .codigoCargo(DEFAULT_CODIGO_CARGO)
            .categoriaTrabalhador(DEFAULT_CATEGORIA_TRABALHADOR)
            .tipoVinculoTrabalho(DEFAULT_TIPO_VINCULO_TRABALHO)
            .fgtsOpcao(DEFAULT_FGTS_OPCAO)
            .tIpoDocumentoEnum(DEFAULT_T_IPO_DOCUMENTO_ENUM)
            .periodoExperiencia(DEFAULT_PERIODO_EXPERIENCIA)
            .tipoAdmisaoEnum(DEFAULT_TIPO_ADMISAO_ENUM)
            .periodoIntermitente(DEFAULT_PERIODO_INTERMITENTE)
            .indicativoAdmissao(DEFAULT_INDICATIVO_ADMISSAO)
            .numeroPisNisPasep(DEFAULT_NUMERO_PIS_NIS_PASEP);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        contratoFuncionario.setFuncionario(funcionario);
        return contratoFuncionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContratoFuncionario createUpdatedEntity(EntityManager em) {
        ContratoFuncionario contratoFuncionario = new ContratoFuncionario()
            .salarioFixo(UPDATED_SALARIO_FIXO)
            .salarioVariavel(UPDATED_SALARIO_VARIAVEL)
            .estagio(UPDATED_ESTAGIO)
            .naturezaEstagioEnum(UPDATED_NATUREZA_ESTAGIO_ENUM)
            .ctps(UPDATED_CTPS)
            .serieCtps(UPDATED_SERIE_CTPS)
            .orgaoEmissorDocumento(UPDATED_ORGAO_EMISSOR_DOCUMENTO)
            .dataValidadeDocumento(UPDATED_DATA_VALIDADE_DOCUMENTO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .cargo(UPDATED_CARGO)
            .descricaoAtividades(UPDATED_DESCRICAO_ATIVIDADES)
            .situacao(UPDATED_SITUACAO)
            .valorSalarioFixo(UPDATED_VALOR_SALARIO_FIXO)
            .valorSalarioVariavel(UPDATED_VALOR_SALARIO_VARIAVEL)
            .dataTerminoContrato(UPDATED_DATA_TERMINO_CONTRATO)
            .datainicioContrato(UPDATED_DATAINICIO_CONTRATO)
            .horasATrabalhadar(UPDATED_HORAS_A_TRABALHADAR)
            .codigoCargo(UPDATED_CODIGO_CARGO)
            .categoriaTrabalhador(UPDATED_CATEGORIA_TRABALHADOR)
            .tipoVinculoTrabalho(UPDATED_TIPO_VINCULO_TRABALHO)
            .fgtsOpcao(UPDATED_FGTS_OPCAO)
            .tIpoDocumentoEnum(UPDATED_T_IPO_DOCUMENTO_ENUM)
            .periodoExperiencia(UPDATED_PERIODO_EXPERIENCIA)
            .tipoAdmisaoEnum(UPDATED_TIPO_ADMISAO_ENUM)
            .periodoIntermitente(UPDATED_PERIODO_INTERMITENTE)
            .indicativoAdmissao(UPDATED_INDICATIVO_ADMISSAO)
            .numeroPisNisPasep(UPDATED_NUMERO_PIS_NIS_PASEP);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createUpdatedEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        contratoFuncionario.setFuncionario(funcionario);
        return contratoFuncionario;
    }

    @BeforeEach
    public void initTest() {
        contratoFuncionario = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedContratoFuncionario != null) {
            contratoFuncionarioRepository.delete(insertedContratoFuncionario);
            insertedContratoFuncionario = null;
        }
    }

    @Test
    @Transactional
    void createContratoFuncionario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContratoFuncionario
        var returnedContratoFuncionario = om.readValue(
            restContratoFuncionarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contratoFuncionario)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContratoFuncionario.class
        );

        // Validate the ContratoFuncionario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContratoFuncionarioUpdatableFieldsEquals(
            returnedContratoFuncionario,
            getPersistedContratoFuncionario(returnedContratoFuncionario)
        );

        insertedContratoFuncionario = returnedContratoFuncionario;
    }

    @Test
    @Transactional
    void createContratoFuncionarioWithExistingId() throws Exception {
        // Create the ContratoFuncionario with an existing ID
        contratoFuncionario.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratoFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contratoFuncionario)))
            .andExpect(status().isBadRequest());

        // Validate the ContratoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContratoFuncionarios() throws Exception {
        // Initialize the database
        insertedContratoFuncionario = contratoFuncionarioRepository.saveAndFlush(contratoFuncionario);

        // Get all the contratoFuncionarioList
        restContratoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contratoFuncionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].salarioFixo").value(hasItem(DEFAULT_SALARIO_FIXO.booleanValue())))
            .andExpect(jsonPath("$.[*].salarioVariavel").value(hasItem(DEFAULT_SALARIO_VARIAVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].estagio").value(hasItem(DEFAULT_ESTAGIO.booleanValue())))
            .andExpect(jsonPath("$.[*].naturezaEstagioEnum").value(hasItem(DEFAULT_NATUREZA_ESTAGIO_ENUM.toString())))
            .andExpect(jsonPath("$.[*].ctps").value(hasItem(DEFAULT_CTPS)))
            .andExpect(jsonPath("$.[*].serieCtps").value(hasItem(DEFAULT_SERIE_CTPS)))
            .andExpect(jsonPath("$.[*].orgaoEmissorDocumento").value(hasItem(DEFAULT_ORGAO_EMISSOR_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].dataValidadeDocumento").value(hasItem(DEFAULT_DATA_VALIDADE_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].dataAdmissao").value(hasItem(DEFAULT_DATA_ADMISSAO)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].descricaoAtividades").value(hasItem(DEFAULT_DESCRICAO_ATIVIDADES.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].valorSalarioFixo").value(hasItem(DEFAULT_VALOR_SALARIO_FIXO)))
            .andExpect(jsonPath("$.[*].valorSalarioVariavel").value(hasItem(DEFAULT_VALOR_SALARIO_VARIAVEL)))
            .andExpect(jsonPath("$.[*].dataTerminoContrato").value(hasItem(DEFAULT_DATA_TERMINO_CONTRATO)))
            .andExpect(jsonPath("$.[*].datainicioContrato").value(hasItem(DEFAULT_DATAINICIO_CONTRATO)))
            .andExpect(jsonPath("$.[*].horasATrabalhadar").value(hasItem(DEFAULT_HORAS_A_TRABALHADAR)))
            .andExpect(jsonPath("$.[*].codigoCargo").value(hasItem(DEFAULT_CODIGO_CARGO)))
            .andExpect(jsonPath("$.[*].categoriaTrabalhador").value(hasItem(DEFAULT_CATEGORIA_TRABALHADOR.toString())))
            .andExpect(jsonPath("$.[*].tipoVinculoTrabalho").value(hasItem(DEFAULT_TIPO_VINCULO_TRABALHO.toString())))
            .andExpect(jsonPath("$.[*].fgtsOpcao").value(hasItem(DEFAULT_FGTS_OPCAO.toString())))
            .andExpect(jsonPath("$.[*].tIpoDocumentoEnum").value(hasItem(DEFAULT_T_IPO_DOCUMENTO_ENUM.toString())))
            .andExpect(jsonPath("$.[*].periodoExperiencia").value(hasItem(DEFAULT_PERIODO_EXPERIENCIA.toString())))
            .andExpect(jsonPath("$.[*].tipoAdmisaoEnum").value(hasItem(DEFAULT_TIPO_ADMISAO_ENUM.toString())))
            .andExpect(jsonPath("$.[*].periodoIntermitente").value(hasItem(DEFAULT_PERIODO_INTERMITENTE.toString())))
            .andExpect(jsonPath("$.[*].indicativoAdmissao").value(hasItem(DEFAULT_INDICATIVO_ADMISSAO.toString())))
            .andExpect(jsonPath("$.[*].numeroPisNisPasep").value(hasItem(DEFAULT_NUMERO_PIS_NIS_PASEP)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContratoFuncionariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(contratoFuncionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContratoFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contratoFuncionarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContratoFuncionariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contratoFuncionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContratoFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contratoFuncionarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getContratoFuncionario() throws Exception {
        // Initialize the database
        insertedContratoFuncionario = contratoFuncionarioRepository.saveAndFlush(contratoFuncionario);

        // Get the contratoFuncionario
        restContratoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, contratoFuncionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contratoFuncionario.getId().intValue()))
            .andExpect(jsonPath("$.salarioFixo").value(DEFAULT_SALARIO_FIXO.booleanValue()))
            .andExpect(jsonPath("$.salarioVariavel").value(DEFAULT_SALARIO_VARIAVEL.booleanValue()))
            .andExpect(jsonPath("$.estagio").value(DEFAULT_ESTAGIO.booleanValue()))
            .andExpect(jsonPath("$.naturezaEstagioEnum").value(DEFAULT_NATUREZA_ESTAGIO_ENUM.toString()))
            .andExpect(jsonPath("$.ctps").value(DEFAULT_CTPS))
            .andExpect(jsonPath("$.serieCtps").value(DEFAULT_SERIE_CTPS))
            .andExpect(jsonPath("$.orgaoEmissorDocumento").value(DEFAULT_ORGAO_EMISSOR_DOCUMENTO))
            .andExpect(jsonPath("$.dataValidadeDocumento").value(DEFAULT_DATA_VALIDADE_DOCUMENTO))
            .andExpect(jsonPath("$.dataAdmissao").value(DEFAULT_DATA_ADMISSAO))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO))
            .andExpect(jsonPath("$.descricaoAtividades").value(DEFAULT_DESCRICAO_ATIVIDADES.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.valorSalarioFixo").value(DEFAULT_VALOR_SALARIO_FIXO))
            .andExpect(jsonPath("$.valorSalarioVariavel").value(DEFAULT_VALOR_SALARIO_VARIAVEL))
            .andExpect(jsonPath("$.dataTerminoContrato").value(DEFAULT_DATA_TERMINO_CONTRATO))
            .andExpect(jsonPath("$.datainicioContrato").value(DEFAULT_DATAINICIO_CONTRATO))
            .andExpect(jsonPath("$.horasATrabalhadar").value(DEFAULT_HORAS_A_TRABALHADAR))
            .andExpect(jsonPath("$.codigoCargo").value(DEFAULT_CODIGO_CARGO))
            .andExpect(jsonPath("$.categoriaTrabalhador").value(DEFAULT_CATEGORIA_TRABALHADOR.toString()))
            .andExpect(jsonPath("$.tipoVinculoTrabalho").value(DEFAULT_TIPO_VINCULO_TRABALHO.toString()))
            .andExpect(jsonPath("$.fgtsOpcao").value(DEFAULT_FGTS_OPCAO.toString()))
            .andExpect(jsonPath("$.tIpoDocumentoEnum").value(DEFAULT_T_IPO_DOCUMENTO_ENUM.toString()))
            .andExpect(jsonPath("$.periodoExperiencia").value(DEFAULT_PERIODO_EXPERIENCIA.toString()))
            .andExpect(jsonPath("$.tipoAdmisaoEnum").value(DEFAULT_TIPO_ADMISAO_ENUM.toString()))
            .andExpect(jsonPath("$.periodoIntermitente").value(DEFAULT_PERIODO_INTERMITENTE.toString()))
            .andExpect(jsonPath("$.indicativoAdmissao").value(DEFAULT_INDICATIVO_ADMISSAO.toString()))
            .andExpect(jsonPath("$.numeroPisNisPasep").value(DEFAULT_NUMERO_PIS_NIS_PASEP));
    }

    @Test
    @Transactional
    void getNonExistingContratoFuncionario() throws Exception {
        // Get the contratoFuncionario
        restContratoFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContratoFuncionario() throws Exception {
        // Initialize the database
        insertedContratoFuncionario = contratoFuncionarioRepository.saveAndFlush(contratoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratoFuncionario
        ContratoFuncionario updatedContratoFuncionario = contratoFuncionarioRepository.findById(contratoFuncionario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContratoFuncionario are not directly saved in db
        em.detach(updatedContratoFuncionario);
        updatedContratoFuncionario
            .salarioFixo(UPDATED_SALARIO_FIXO)
            .salarioVariavel(UPDATED_SALARIO_VARIAVEL)
            .estagio(UPDATED_ESTAGIO)
            .naturezaEstagioEnum(UPDATED_NATUREZA_ESTAGIO_ENUM)
            .ctps(UPDATED_CTPS)
            .serieCtps(UPDATED_SERIE_CTPS)
            .orgaoEmissorDocumento(UPDATED_ORGAO_EMISSOR_DOCUMENTO)
            .dataValidadeDocumento(UPDATED_DATA_VALIDADE_DOCUMENTO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .cargo(UPDATED_CARGO)
            .descricaoAtividades(UPDATED_DESCRICAO_ATIVIDADES)
            .situacao(UPDATED_SITUACAO)
            .valorSalarioFixo(UPDATED_VALOR_SALARIO_FIXO)
            .valorSalarioVariavel(UPDATED_VALOR_SALARIO_VARIAVEL)
            .dataTerminoContrato(UPDATED_DATA_TERMINO_CONTRATO)
            .datainicioContrato(UPDATED_DATAINICIO_CONTRATO)
            .horasATrabalhadar(UPDATED_HORAS_A_TRABALHADAR)
            .codigoCargo(UPDATED_CODIGO_CARGO)
            .categoriaTrabalhador(UPDATED_CATEGORIA_TRABALHADOR)
            .tipoVinculoTrabalho(UPDATED_TIPO_VINCULO_TRABALHO)
            .fgtsOpcao(UPDATED_FGTS_OPCAO)
            .tIpoDocumentoEnum(UPDATED_T_IPO_DOCUMENTO_ENUM)
            .periodoExperiencia(UPDATED_PERIODO_EXPERIENCIA)
            .tipoAdmisaoEnum(UPDATED_TIPO_ADMISAO_ENUM)
            .periodoIntermitente(UPDATED_PERIODO_INTERMITENTE)
            .indicativoAdmissao(UPDATED_INDICATIVO_ADMISSAO)
            .numeroPisNisPasep(UPDATED_NUMERO_PIS_NIS_PASEP);

        restContratoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContratoFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContratoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the ContratoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContratoFuncionarioToMatchAllProperties(updatedContratoFuncionario);
    }

    @Test
    @Transactional
    void putNonExistingContratoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratoFuncionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contratoFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContratoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contratoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContratoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratoFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contratoFuncionario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContratoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContratoFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedContratoFuncionario = contratoFuncionarioRepository.saveAndFlush(contratoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratoFuncionario using partial update
        ContratoFuncionario partialUpdatedContratoFuncionario = new ContratoFuncionario();
        partialUpdatedContratoFuncionario.setId(contratoFuncionario.getId());

        partialUpdatedContratoFuncionario
            .salarioFixo(UPDATED_SALARIO_FIXO)
            .estagio(UPDATED_ESTAGIO)
            .naturezaEstagioEnum(UPDATED_NATUREZA_ESTAGIO_ENUM)
            .dataValidadeDocumento(UPDATED_DATA_VALIDADE_DOCUMENTO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .cargo(UPDATED_CARGO)
            .descricaoAtividades(UPDATED_DESCRICAO_ATIVIDADES)
            .valorSalarioVariavel(UPDATED_VALOR_SALARIO_VARIAVEL)
            .dataTerminoContrato(UPDATED_DATA_TERMINO_CONTRATO)
            .datainicioContrato(UPDATED_DATAINICIO_CONTRATO)
            .codigoCargo(UPDATED_CODIGO_CARGO)
            .categoriaTrabalhador(UPDATED_CATEGORIA_TRABALHADOR)
            .tipoVinculoTrabalho(UPDATED_TIPO_VINCULO_TRABALHO)
            .periodoExperiencia(UPDATED_PERIODO_EXPERIENCIA)
            .tipoAdmisaoEnum(UPDATED_TIPO_ADMISAO_ENUM);

        restContratoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContratoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContratoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the ContratoFuncionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContratoFuncionarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContratoFuncionario, contratoFuncionario),
            getPersistedContratoFuncionario(contratoFuncionario)
        );
    }

    @Test
    @Transactional
    void fullUpdateContratoFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedContratoFuncionario = contratoFuncionarioRepository.saveAndFlush(contratoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contratoFuncionario using partial update
        ContratoFuncionario partialUpdatedContratoFuncionario = new ContratoFuncionario();
        partialUpdatedContratoFuncionario.setId(contratoFuncionario.getId());

        partialUpdatedContratoFuncionario
            .salarioFixo(UPDATED_SALARIO_FIXO)
            .salarioVariavel(UPDATED_SALARIO_VARIAVEL)
            .estagio(UPDATED_ESTAGIO)
            .naturezaEstagioEnum(UPDATED_NATUREZA_ESTAGIO_ENUM)
            .ctps(UPDATED_CTPS)
            .serieCtps(UPDATED_SERIE_CTPS)
            .orgaoEmissorDocumento(UPDATED_ORGAO_EMISSOR_DOCUMENTO)
            .dataValidadeDocumento(UPDATED_DATA_VALIDADE_DOCUMENTO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .cargo(UPDATED_CARGO)
            .descricaoAtividades(UPDATED_DESCRICAO_ATIVIDADES)
            .situacao(UPDATED_SITUACAO)
            .valorSalarioFixo(UPDATED_VALOR_SALARIO_FIXO)
            .valorSalarioVariavel(UPDATED_VALOR_SALARIO_VARIAVEL)
            .dataTerminoContrato(UPDATED_DATA_TERMINO_CONTRATO)
            .datainicioContrato(UPDATED_DATAINICIO_CONTRATO)
            .horasATrabalhadar(UPDATED_HORAS_A_TRABALHADAR)
            .codigoCargo(UPDATED_CODIGO_CARGO)
            .categoriaTrabalhador(UPDATED_CATEGORIA_TRABALHADOR)
            .tipoVinculoTrabalho(UPDATED_TIPO_VINCULO_TRABALHO)
            .fgtsOpcao(UPDATED_FGTS_OPCAO)
            .tIpoDocumentoEnum(UPDATED_T_IPO_DOCUMENTO_ENUM)
            .periodoExperiencia(UPDATED_PERIODO_EXPERIENCIA)
            .tipoAdmisaoEnum(UPDATED_TIPO_ADMISAO_ENUM)
            .periodoIntermitente(UPDATED_PERIODO_INTERMITENTE)
            .indicativoAdmissao(UPDATED_INDICATIVO_ADMISSAO)
            .numeroPisNisPasep(UPDATED_NUMERO_PIS_NIS_PASEP);

        restContratoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContratoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContratoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the ContratoFuncionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContratoFuncionarioUpdatableFieldsEquals(
            partialUpdatedContratoFuncionario,
            getPersistedContratoFuncionario(partialUpdatedContratoFuncionario)
        );
    }

    @Test
    @Transactional
    void patchNonExistingContratoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratoFuncionario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contratoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contratoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContratoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contratoFuncionario))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContratoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContratoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contratoFuncionario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContratoFuncionarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contratoFuncionario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContratoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContratoFuncionario() throws Exception {
        // Initialize the database
        insertedContratoFuncionario = contratoFuncionarioRepository.saveAndFlush(contratoFuncionario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contratoFuncionario
        restContratoFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, contratoFuncionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contratoFuncionarioRepository.count();
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

    protected ContratoFuncionario getPersistedContratoFuncionario(ContratoFuncionario contratoFuncionario) {
        return contratoFuncionarioRepository.findById(contratoFuncionario.getId()).orElseThrow();
    }

    protected void assertPersistedContratoFuncionarioToMatchAllProperties(ContratoFuncionario expectedContratoFuncionario) {
        assertContratoFuncionarioAllPropertiesEquals(
            expectedContratoFuncionario,
            getPersistedContratoFuncionario(expectedContratoFuncionario)
        );
    }

    protected void assertPersistedContratoFuncionarioToMatchUpdatableProperties(ContratoFuncionario expectedContratoFuncionario) {
        assertContratoFuncionarioAllUpdatablePropertiesEquals(
            expectedContratoFuncionario,
            getPersistedContratoFuncionario(expectedContratoFuncionario)
        );
    }
}
