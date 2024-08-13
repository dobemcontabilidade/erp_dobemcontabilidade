package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EmpresaVinculadaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.EmpresaVinculada;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.domain.enumeration.DiasDaSemanaEnum;
import com.dobemcontabilidade.domain.enumeration.JornadaEspecialEnum;
import com.dobemcontabilidade.domain.enumeration.RegimePrevidenciarioEnum;
import com.dobemcontabilidade.domain.enumeration.TipoContratoTrabalhoEnum;
import com.dobemcontabilidade.domain.enumeration.TipoInscricaoEmpresaVinculadaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoJornadaEmpresaVinculadaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoRegimeTrabalhoEnum;
import com.dobemcontabilidade.domain.enumeration.UnidadePagamentoSalarioEnum;
import com.dobemcontabilidade.repository.EmpresaVinculadaRepository;
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
 * Integration tests for the {@link EmpresaVinculadaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpresaVinculadaResourceIT {

    private static final String DEFAULT_NOME_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_REMUNERACAO_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_REMUNERACAO_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SALARIO_FIXO = false;
    private static final Boolean UPDATED_SALARIO_FIXO = true;

    private static final Boolean DEFAULT_SALARIO_VARIAVEL = false;
    private static final Boolean UPDATED_SALARIO_VARIAVEL = true;

    private static final String DEFAULT_VALOR_SALARIO_FIXO = "AAAAAAAAAA";
    private static final String UPDATED_VALOR_SALARIO_FIXO = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_TERMINO_CONTRATO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_TERMINO_CONTRATO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_INSCRICAO = 1;
    private static final Integer UPDATED_NUMERO_INSCRICAO = 2;

    private static final Integer DEFAULT_CODIGO_LOTACAO = 1;
    private static final Integer UPDATED_CODIGO_LOTACAO = 2;

    private static final String DEFAULT_DESCRICAO_COMPLEMENTAR = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_COMPLEMENTAR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACAO_JORNADA_TRABALHO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO_JORNADA_TRABALHO = "BBBBBBBBBB";

    private static final Integer DEFAULT_MEDIA_HORAS_TRABALHADAS_SEMANA = 1;
    private static final Integer UPDATED_MEDIA_HORAS_TRABALHADAS_SEMANA = 2;

    private static final RegimePrevidenciarioEnum DEFAULT_REGIME_PREVIDENCIARIO = RegimePrevidenciarioEnum.RGPS;
    private static final RegimePrevidenciarioEnum UPDATED_REGIME_PREVIDENCIARIO = RegimePrevidenciarioEnum.RPPS;

    private static final UnidadePagamentoSalarioEnum DEFAULT_UNIDADE_PAGAMENTO_SALARIO = UnidadePagamentoSalarioEnum.HORA;
    private static final UnidadePagamentoSalarioEnum UPDATED_UNIDADE_PAGAMENTO_SALARIO = UnidadePagamentoSalarioEnum.DIA;

    private static final JornadaEspecialEnum DEFAULT_JORNADA_ESPECIAL = JornadaEspecialEnum.DOZEPORTRINTASEIS;
    private static final JornadaEspecialEnum UPDATED_JORNADA_ESPECIAL = JornadaEspecialEnum.VINTEQUATROPORSETENTADOIS;

    private static final TipoInscricaoEmpresaVinculadaEnum DEFAULT_TIPO_INSCRICAO_EMPRESA_VINCULADA =
        TipoInscricaoEmpresaVinculadaEnum.CNPJ;
    private static final TipoInscricaoEmpresaVinculadaEnum UPDATED_TIPO_INSCRICAO_EMPRESA_VINCULADA = TipoInscricaoEmpresaVinculadaEnum.CPF;

    private static final TipoContratoTrabalhoEnum DEFAULT_TIPO_CONTRATO_TRABALHO = TipoContratoTrabalhoEnum.INDETERMINADO;
    private static final TipoContratoTrabalhoEnum UPDATED_TIPO_CONTRATO_TRABALHO = TipoContratoTrabalhoEnum.DETERMINADO;

    private static final TipoRegimeTrabalhoEnum DEFAULT_TIPO_REGIME_TRABALHO = TipoRegimeTrabalhoEnum.CLT;
    private static final TipoRegimeTrabalhoEnum UPDATED_TIPO_REGIME_TRABALHO = TipoRegimeTrabalhoEnum.RJU;

    private static final DiasDaSemanaEnum DEFAULT_DIAS_DA_SEMANA = DiasDaSemanaEnum.SEGUNDAFEIRA;
    private static final DiasDaSemanaEnum UPDATED_DIAS_DA_SEMANA = DiasDaSemanaEnum.TERCAFEIRA;

    private static final TipoJornadaEmpresaVinculadaEnum DEFAULT_TIPO_JORNADA_EMPRESA_VINCULADA = TipoJornadaEmpresaVinculadaEnum.FIXA;
    private static final TipoJornadaEmpresaVinculadaEnum UPDATED_TIPO_JORNADA_EMPRESA_VINCULADA = TipoJornadaEmpresaVinculadaEnum.VARIAVEL;

    private static final String ENTITY_API_URL = "/api/empresa-vinculadas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmpresaVinculadaRepository empresaVinculadaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaVinculadaMockMvc;

    private EmpresaVinculada empresaVinculada;

    private EmpresaVinculada insertedEmpresaVinculada;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpresaVinculada createEntity(EntityManager em) {
        EmpresaVinculada empresaVinculada = new EmpresaVinculada()
            .nomeEmpresa(DEFAULT_NOME_EMPRESA)
            .cnpj(DEFAULT_CNPJ)
            .remuneracaoEmpresa(DEFAULT_REMUNERACAO_EMPRESA)
            .observacoes(DEFAULT_OBSERVACOES)
            .salarioFixo(DEFAULT_SALARIO_FIXO)
            .salarioVariavel(DEFAULT_SALARIO_VARIAVEL)
            .valorSalarioFixo(DEFAULT_VALOR_SALARIO_FIXO)
            .dataTerminoContrato(DEFAULT_DATA_TERMINO_CONTRATO)
            .numeroInscricao(DEFAULT_NUMERO_INSCRICAO)
            .codigoLotacao(DEFAULT_CODIGO_LOTACAO)
            .descricaoComplementar(DEFAULT_DESCRICAO_COMPLEMENTAR)
            .descricaoCargo(DEFAULT_DESCRICAO_CARGO)
            .observacaoJornadaTrabalho(DEFAULT_OBSERVACAO_JORNADA_TRABALHO)
            .mediaHorasTrabalhadasSemana(DEFAULT_MEDIA_HORAS_TRABALHADAS_SEMANA)
            .regimePrevidenciario(DEFAULT_REGIME_PREVIDENCIARIO)
            .unidadePagamentoSalario(DEFAULT_UNIDADE_PAGAMENTO_SALARIO)
            .jornadaEspecial(DEFAULT_JORNADA_ESPECIAL)
            .tipoInscricaoEmpresaVinculada(DEFAULT_TIPO_INSCRICAO_EMPRESA_VINCULADA)
            .tipoContratoTrabalho(DEFAULT_TIPO_CONTRATO_TRABALHO)
            .tipoRegimeTrabalho(DEFAULT_TIPO_REGIME_TRABALHO)
            .diasDaSemana(DEFAULT_DIAS_DA_SEMANA)
            .tipoJornadaEmpresaVinculada(DEFAULT_TIPO_JORNADA_EMPRESA_VINCULADA);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        empresaVinculada.setFuncionario(funcionario);
        return empresaVinculada;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmpresaVinculada createUpdatedEntity(EntityManager em) {
        EmpresaVinculada empresaVinculada = new EmpresaVinculada()
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .cnpj(UPDATED_CNPJ)
            .remuneracaoEmpresa(UPDATED_REMUNERACAO_EMPRESA)
            .observacoes(UPDATED_OBSERVACOES)
            .salarioFixo(UPDATED_SALARIO_FIXO)
            .salarioVariavel(UPDATED_SALARIO_VARIAVEL)
            .valorSalarioFixo(UPDATED_VALOR_SALARIO_FIXO)
            .dataTerminoContrato(UPDATED_DATA_TERMINO_CONTRATO)
            .numeroInscricao(UPDATED_NUMERO_INSCRICAO)
            .codigoLotacao(UPDATED_CODIGO_LOTACAO)
            .descricaoComplementar(UPDATED_DESCRICAO_COMPLEMENTAR)
            .descricaoCargo(UPDATED_DESCRICAO_CARGO)
            .observacaoJornadaTrabalho(UPDATED_OBSERVACAO_JORNADA_TRABALHO)
            .mediaHorasTrabalhadasSemana(UPDATED_MEDIA_HORAS_TRABALHADAS_SEMANA)
            .regimePrevidenciario(UPDATED_REGIME_PREVIDENCIARIO)
            .unidadePagamentoSalario(UPDATED_UNIDADE_PAGAMENTO_SALARIO)
            .jornadaEspecial(UPDATED_JORNADA_ESPECIAL)
            .tipoInscricaoEmpresaVinculada(UPDATED_TIPO_INSCRICAO_EMPRESA_VINCULADA)
            .tipoContratoTrabalho(UPDATED_TIPO_CONTRATO_TRABALHO)
            .tipoRegimeTrabalho(UPDATED_TIPO_REGIME_TRABALHO)
            .diasDaSemana(UPDATED_DIAS_DA_SEMANA)
            .tipoJornadaEmpresaVinculada(UPDATED_TIPO_JORNADA_EMPRESA_VINCULADA);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createUpdatedEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        empresaVinculada.setFuncionario(funcionario);
        return empresaVinculada;
    }

    @BeforeEach
    public void initTest() {
        empresaVinculada = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmpresaVinculada != null) {
            empresaVinculadaRepository.delete(insertedEmpresaVinculada);
            insertedEmpresaVinculada = null;
        }
    }

    @Test
    @Transactional
    void createEmpresaVinculada() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EmpresaVinculada
        var returnedEmpresaVinculada = om.readValue(
            restEmpresaVinculadaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresaVinculada)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmpresaVinculada.class
        );

        // Validate the EmpresaVinculada in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmpresaVinculadaUpdatableFieldsEquals(returnedEmpresaVinculada, getPersistedEmpresaVinculada(returnedEmpresaVinculada));

        insertedEmpresaVinculada = returnedEmpresaVinculada;
    }

    @Test
    @Transactional
    void createEmpresaVinculadaWithExistingId() throws Exception {
        // Create the EmpresaVinculada with an existing ID
        empresaVinculada.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaVinculadaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresaVinculada)))
            .andExpect(status().isBadRequest());

        // Validate the EmpresaVinculada in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpresaVinculadas() throws Exception {
        // Initialize the database
        insertedEmpresaVinculada = empresaVinculadaRepository.saveAndFlush(empresaVinculada);

        // Get all the empresaVinculadaList
        restEmpresaVinculadaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresaVinculada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeEmpresa").value(hasItem(DEFAULT_NOME_EMPRESA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].remuneracaoEmpresa").value(hasItem(DEFAULT_REMUNERACAO_EMPRESA)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].salarioFixo").value(hasItem(DEFAULT_SALARIO_FIXO.booleanValue())))
            .andExpect(jsonPath("$.[*].salarioVariavel").value(hasItem(DEFAULT_SALARIO_VARIAVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].valorSalarioFixo").value(hasItem(DEFAULT_VALOR_SALARIO_FIXO)))
            .andExpect(jsonPath("$.[*].dataTerminoContrato").value(hasItem(DEFAULT_DATA_TERMINO_CONTRATO)))
            .andExpect(jsonPath("$.[*].numeroInscricao").value(hasItem(DEFAULT_NUMERO_INSCRICAO)))
            .andExpect(jsonPath("$.[*].codigoLotacao").value(hasItem(DEFAULT_CODIGO_LOTACAO)))
            .andExpect(jsonPath("$.[*].descricaoComplementar").value(hasItem(DEFAULT_DESCRICAO_COMPLEMENTAR)))
            .andExpect(jsonPath("$.[*].descricaoCargo").value(hasItem(DEFAULT_DESCRICAO_CARGO)))
            .andExpect(jsonPath("$.[*].observacaoJornadaTrabalho").value(hasItem(DEFAULT_OBSERVACAO_JORNADA_TRABALHO)))
            .andExpect(jsonPath("$.[*].mediaHorasTrabalhadasSemana").value(hasItem(DEFAULT_MEDIA_HORAS_TRABALHADAS_SEMANA)))
            .andExpect(jsonPath("$.[*].regimePrevidenciario").value(hasItem(DEFAULT_REGIME_PREVIDENCIARIO.toString())))
            .andExpect(jsonPath("$.[*].unidadePagamentoSalario").value(hasItem(DEFAULT_UNIDADE_PAGAMENTO_SALARIO.toString())))
            .andExpect(jsonPath("$.[*].jornadaEspecial").value(hasItem(DEFAULT_JORNADA_ESPECIAL.toString())))
            .andExpect(jsonPath("$.[*].tipoInscricaoEmpresaVinculada").value(hasItem(DEFAULT_TIPO_INSCRICAO_EMPRESA_VINCULADA.toString())))
            .andExpect(jsonPath("$.[*].tipoContratoTrabalho").value(hasItem(DEFAULT_TIPO_CONTRATO_TRABALHO.toString())))
            .andExpect(jsonPath("$.[*].tipoRegimeTrabalho").value(hasItem(DEFAULT_TIPO_REGIME_TRABALHO.toString())))
            .andExpect(jsonPath("$.[*].diasDaSemana").value(hasItem(DEFAULT_DIAS_DA_SEMANA.toString())))
            .andExpect(jsonPath("$.[*].tipoJornadaEmpresaVinculada").value(hasItem(DEFAULT_TIPO_JORNADA_EMPRESA_VINCULADA.toString())));
    }

    @Test
    @Transactional
    void getEmpresaVinculada() throws Exception {
        // Initialize the database
        insertedEmpresaVinculada = empresaVinculadaRepository.saveAndFlush(empresaVinculada);

        // Get the empresaVinculada
        restEmpresaVinculadaMockMvc
            .perform(get(ENTITY_API_URL_ID, empresaVinculada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresaVinculada.getId().intValue()))
            .andExpect(jsonPath("$.nomeEmpresa").value(DEFAULT_NOME_EMPRESA))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.remuneracaoEmpresa").value(DEFAULT_REMUNERACAO_EMPRESA))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES))
            .andExpect(jsonPath("$.salarioFixo").value(DEFAULT_SALARIO_FIXO.booleanValue()))
            .andExpect(jsonPath("$.salarioVariavel").value(DEFAULT_SALARIO_VARIAVEL.booleanValue()))
            .andExpect(jsonPath("$.valorSalarioFixo").value(DEFAULT_VALOR_SALARIO_FIXO))
            .andExpect(jsonPath("$.dataTerminoContrato").value(DEFAULT_DATA_TERMINO_CONTRATO))
            .andExpect(jsonPath("$.numeroInscricao").value(DEFAULT_NUMERO_INSCRICAO))
            .andExpect(jsonPath("$.codigoLotacao").value(DEFAULT_CODIGO_LOTACAO))
            .andExpect(jsonPath("$.descricaoComplementar").value(DEFAULT_DESCRICAO_COMPLEMENTAR))
            .andExpect(jsonPath("$.descricaoCargo").value(DEFAULT_DESCRICAO_CARGO))
            .andExpect(jsonPath("$.observacaoJornadaTrabalho").value(DEFAULT_OBSERVACAO_JORNADA_TRABALHO))
            .andExpect(jsonPath("$.mediaHorasTrabalhadasSemana").value(DEFAULT_MEDIA_HORAS_TRABALHADAS_SEMANA))
            .andExpect(jsonPath("$.regimePrevidenciario").value(DEFAULT_REGIME_PREVIDENCIARIO.toString()))
            .andExpect(jsonPath("$.unidadePagamentoSalario").value(DEFAULT_UNIDADE_PAGAMENTO_SALARIO.toString()))
            .andExpect(jsonPath("$.jornadaEspecial").value(DEFAULT_JORNADA_ESPECIAL.toString()))
            .andExpect(jsonPath("$.tipoInscricaoEmpresaVinculada").value(DEFAULT_TIPO_INSCRICAO_EMPRESA_VINCULADA.toString()))
            .andExpect(jsonPath("$.tipoContratoTrabalho").value(DEFAULT_TIPO_CONTRATO_TRABALHO.toString()))
            .andExpect(jsonPath("$.tipoRegimeTrabalho").value(DEFAULT_TIPO_REGIME_TRABALHO.toString()))
            .andExpect(jsonPath("$.diasDaSemana").value(DEFAULT_DIAS_DA_SEMANA.toString()))
            .andExpect(jsonPath("$.tipoJornadaEmpresaVinculada").value(DEFAULT_TIPO_JORNADA_EMPRESA_VINCULADA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEmpresaVinculada() throws Exception {
        // Get the empresaVinculada
        restEmpresaVinculadaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpresaVinculada() throws Exception {
        // Initialize the database
        insertedEmpresaVinculada = empresaVinculadaRepository.saveAndFlush(empresaVinculada);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresaVinculada
        EmpresaVinculada updatedEmpresaVinculada = empresaVinculadaRepository.findById(empresaVinculada.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmpresaVinculada are not directly saved in db
        em.detach(updatedEmpresaVinculada);
        updatedEmpresaVinculada
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .cnpj(UPDATED_CNPJ)
            .remuneracaoEmpresa(UPDATED_REMUNERACAO_EMPRESA)
            .observacoes(UPDATED_OBSERVACOES)
            .salarioFixo(UPDATED_SALARIO_FIXO)
            .salarioVariavel(UPDATED_SALARIO_VARIAVEL)
            .valorSalarioFixo(UPDATED_VALOR_SALARIO_FIXO)
            .dataTerminoContrato(UPDATED_DATA_TERMINO_CONTRATO)
            .numeroInscricao(UPDATED_NUMERO_INSCRICAO)
            .codigoLotacao(UPDATED_CODIGO_LOTACAO)
            .descricaoComplementar(UPDATED_DESCRICAO_COMPLEMENTAR)
            .descricaoCargo(UPDATED_DESCRICAO_CARGO)
            .observacaoJornadaTrabalho(UPDATED_OBSERVACAO_JORNADA_TRABALHO)
            .mediaHorasTrabalhadasSemana(UPDATED_MEDIA_HORAS_TRABALHADAS_SEMANA)
            .regimePrevidenciario(UPDATED_REGIME_PREVIDENCIARIO)
            .unidadePagamentoSalario(UPDATED_UNIDADE_PAGAMENTO_SALARIO)
            .jornadaEspecial(UPDATED_JORNADA_ESPECIAL)
            .tipoInscricaoEmpresaVinculada(UPDATED_TIPO_INSCRICAO_EMPRESA_VINCULADA)
            .tipoContratoTrabalho(UPDATED_TIPO_CONTRATO_TRABALHO)
            .tipoRegimeTrabalho(UPDATED_TIPO_REGIME_TRABALHO)
            .diasDaSemana(UPDATED_DIAS_DA_SEMANA)
            .tipoJornadaEmpresaVinculada(UPDATED_TIPO_JORNADA_EMPRESA_VINCULADA);

        restEmpresaVinculadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpresaVinculada.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmpresaVinculada))
            )
            .andExpect(status().isOk());

        // Validate the EmpresaVinculada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmpresaVinculadaToMatchAllProperties(updatedEmpresaVinculada);
    }

    @Test
    @Transactional
    void putNonExistingEmpresaVinculada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaVinculada.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaVinculadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresaVinculada.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empresaVinculada))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpresaVinculada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpresaVinculada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaVinculada.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaVinculadaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empresaVinculada))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpresaVinculada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpresaVinculada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaVinculada.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaVinculadaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresaVinculada)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpresaVinculada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpresaVinculadaWithPatch() throws Exception {
        // Initialize the database
        insertedEmpresaVinculada = empresaVinculadaRepository.saveAndFlush(empresaVinculada);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresaVinculada using partial update
        EmpresaVinculada partialUpdatedEmpresaVinculada = new EmpresaVinculada();
        partialUpdatedEmpresaVinculada.setId(empresaVinculada.getId());

        partialUpdatedEmpresaVinculada
            .salarioVariavel(UPDATED_SALARIO_VARIAVEL)
            .codigoLotacao(UPDATED_CODIGO_LOTACAO)
            .descricaoComplementar(UPDATED_DESCRICAO_COMPLEMENTAR)
            .mediaHorasTrabalhadasSemana(UPDATED_MEDIA_HORAS_TRABALHADAS_SEMANA)
            .tipoInscricaoEmpresaVinculada(UPDATED_TIPO_INSCRICAO_EMPRESA_VINCULADA)
            .tipoContratoTrabalho(UPDATED_TIPO_CONTRATO_TRABALHO)
            .diasDaSemana(UPDATED_DIAS_DA_SEMANA)
            .tipoJornadaEmpresaVinculada(UPDATED_TIPO_JORNADA_EMPRESA_VINCULADA);

        restEmpresaVinculadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresaVinculada.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpresaVinculada))
            )
            .andExpect(status().isOk());

        // Validate the EmpresaVinculada in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpresaVinculadaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEmpresaVinculada, empresaVinculada),
            getPersistedEmpresaVinculada(empresaVinculada)
        );
    }

    @Test
    @Transactional
    void fullUpdateEmpresaVinculadaWithPatch() throws Exception {
        // Initialize the database
        insertedEmpresaVinculada = empresaVinculadaRepository.saveAndFlush(empresaVinculada);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresaVinculada using partial update
        EmpresaVinculada partialUpdatedEmpresaVinculada = new EmpresaVinculada();
        partialUpdatedEmpresaVinculada.setId(empresaVinculada.getId());

        partialUpdatedEmpresaVinculada
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .cnpj(UPDATED_CNPJ)
            .remuneracaoEmpresa(UPDATED_REMUNERACAO_EMPRESA)
            .observacoes(UPDATED_OBSERVACOES)
            .salarioFixo(UPDATED_SALARIO_FIXO)
            .salarioVariavel(UPDATED_SALARIO_VARIAVEL)
            .valorSalarioFixo(UPDATED_VALOR_SALARIO_FIXO)
            .dataTerminoContrato(UPDATED_DATA_TERMINO_CONTRATO)
            .numeroInscricao(UPDATED_NUMERO_INSCRICAO)
            .codigoLotacao(UPDATED_CODIGO_LOTACAO)
            .descricaoComplementar(UPDATED_DESCRICAO_COMPLEMENTAR)
            .descricaoCargo(UPDATED_DESCRICAO_CARGO)
            .observacaoJornadaTrabalho(UPDATED_OBSERVACAO_JORNADA_TRABALHO)
            .mediaHorasTrabalhadasSemana(UPDATED_MEDIA_HORAS_TRABALHADAS_SEMANA)
            .regimePrevidenciario(UPDATED_REGIME_PREVIDENCIARIO)
            .unidadePagamentoSalario(UPDATED_UNIDADE_PAGAMENTO_SALARIO)
            .jornadaEspecial(UPDATED_JORNADA_ESPECIAL)
            .tipoInscricaoEmpresaVinculada(UPDATED_TIPO_INSCRICAO_EMPRESA_VINCULADA)
            .tipoContratoTrabalho(UPDATED_TIPO_CONTRATO_TRABALHO)
            .tipoRegimeTrabalho(UPDATED_TIPO_REGIME_TRABALHO)
            .diasDaSemana(UPDATED_DIAS_DA_SEMANA)
            .tipoJornadaEmpresaVinculada(UPDATED_TIPO_JORNADA_EMPRESA_VINCULADA);

        restEmpresaVinculadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresaVinculada.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpresaVinculada))
            )
            .andExpect(status().isOk());

        // Validate the EmpresaVinculada in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpresaVinculadaUpdatableFieldsEquals(
            partialUpdatedEmpresaVinculada,
            getPersistedEmpresaVinculada(partialUpdatedEmpresaVinculada)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEmpresaVinculada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaVinculada.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaVinculadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empresaVinculada.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empresaVinculada))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpresaVinculada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpresaVinculada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaVinculada.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaVinculadaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empresaVinculada))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmpresaVinculada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpresaVinculada() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresaVinculada.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaVinculadaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(empresaVinculada)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmpresaVinculada in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpresaVinculada() throws Exception {
        // Initialize the database
        insertedEmpresaVinculada = empresaVinculadaRepository.saveAndFlush(empresaVinculada);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the empresaVinculada
        restEmpresaVinculadaMockMvc
            .perform(delete(ENTITY_API_URL_ID, empresaVinculada.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return empresaVinculadaRepository.count();
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

    protected EmpresaVinculada getPersistedEmpresaVinculada(EmpresaVinculada empresaVinculada) {
        return empresaVinculadaRepository.findById(empresaVinculada.getId()).orElseThrow();
    }

    protected void assertPersistedEmpresaVinculadaToMatchAllProperties(EmpresaVinculada expectedEmpresaVinculada) {
        assertEmpresaVinculadaAllPropertiesEquals(expectedEmpresaVinculada, getPersistedEmpresaVinculada(expectedEmpresaVinculada));
    }

    protected void assertPersistedEmpresaVinculadaToMatchUpdatableProperties(EmpresaVinculada expectedEmpresaVinculada) {
        assertEmpresaVinculadaAllUpdatablePropertiesEquals(
            expectedEmpresaVinculada,
            getPersistedEmpresaVinculada(expectedEmpresaVinculada)
        );
    }
}
