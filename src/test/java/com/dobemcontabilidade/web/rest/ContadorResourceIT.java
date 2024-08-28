package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.enumeration.EstadoCivilEnum;
import com.dobemcontabilidade.domain.enumeration.PessoaComDeficienciaEnum;
import com.dobemcontabilidade.domain.enumeration.RacaECorEnum;
import com.dobemcontabilidade.domain.enumeration.SexoEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoContadorEnum;
import com.dobemcontabilidade.repository.ContadorRepository;
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
 * Integration tests for the {@link ContadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContadorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_NASCIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_NASCIMENTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TITULO_ELEITOR = 1;
    private static final Integer UPDATED_TITULO_ELEITOR = 2;

    private static final String DEFAULT_RG = "AAAAAAAAAA";
    private static final String UPDATED_RG = "BBBBBBBBBB";

    private static final String DEFAULT_RG_ORGAO_EXPEDITOR = "AAAAAAAAAA";
    private static final String UPDATED_RG_ORGAO_EXPEDITOR = "BBBBBBBBBB";

    private static final String DEFAULT_RG_UF_EXPEDICAO = "AAAAAAAAAA";
    private static final String UPDATED_RG_UF_EXPEDICAO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_PAI = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_NASCIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_NASCIMENTO = "BBBBBBBBBB";

    private static final RacaECorEnum DEFAULT_RACA_E_COR = RacaECorEnum.AMARELA;
    private static final RacaECorEnum UPDATED_RACA_E_COR = RacaECorEnum.BRANCO;

    private static final PessoaComDeficienciaEnum DEFAULT_PESSOA_COM_DEFICIENCIA = PessoaComDeficienciaEnum.DEFICIENCIAMOTORA;
    private static final PessoaComDeficienciaEnum UPDATED_PESSOA_COM_DEFICIENCIA = PessoaComDeficienciaEnum.DEFICIENCIAVISUAL;

    private static final EstadoCivilEnum DEFAULT_ESTADO_CIVIL = EstadoCivilEnum.SOLTERO;
    private static final EstadoCivilEnum UPDATED_ESTADO_CIVIL = EstadoCivilEnum.CASADO;

    private static final SexoEnum DEFAULT_SEXO = SexoEnum.MASCULINO;
    private static final SexoEnum UPDATED_SEXO = SexoEnum.FEMININO;

    private static final String DEFAULT_URL_FOTO_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_URL_FOTO_PERFIL = "BBBBBBBBBB";

    private static final String DEFAULT_RG_ORGAO_EXPDITOR = "AAAAAAAAAA";
    private static final String UPDATED_RG_ORGAO_EXPDITOR = "BBBBBBBBBB";

    private static final String DEFAULT_CRC = "AAAAAAAAAA";
    private static final String UPDATED_CRC = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIMITE_EMPRESAS = 1;
    private static final Integer UPDATED_LIMITE_EMPRESAS = 2;

    private static final Integer DEFAULT_LIMITE_AREA_CONTABILS = 1;
    private static final Integer UPDATED_LIMITE_AREA_CONTABILS = 2;

    private static final Double DEFAULT_LIMITE_FATURAMENTO = 1D;
    private static final Double UPDATED_LIMITE_FATURAMENTO = 2D;

    private static final Integer DEFAULT_LIMITE_DEPARTAMENTOS = 1;
    private static final Integer UPDATED_LIMITE_DEPARTAMENTOS = 2;

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
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .tituloEleitor(DEFAULT_TITULO_ELEITOR)
            .rg(DEFAULT_RG)
            .rgOrgaoExpeditor(DEFAULT_RG_ORGAO_EXPEDITOR)
            .rgUfExpedicao(DEFAULT_RG_UF_EXPEDICAO)
            .nomeMae(DEFAULT_NOME_MAE)
            .nomePai(DEFAULT_NOME_PAI)
            .localNascimento(DEFAULT_LOCAL_NASCIMENTO)
            .racaECor(DEFAULT_RACA_E_COR)
            .pessoaComDeficiencia(DEFAULT_PESSOA_COM_DEFICIENCIA)
            .estadoCivil(DEFAULT_ESTADO_CIVIL)
            .sexo(DEFAULT_SEXO)
            .urlFotoPerfil(DEFAULT_URL_FOTO_PERFIL)
            .rgOrgaoExpditor(DEFAULT_RG_ORGAO_EXPDITOR)
            .crc(DEFAULT_CRC)
            .limiteEmpresas(DEFAULT_LIMITE_EMPRESAS)
            .limiteAreaContabils(DEFAULT_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(DEFAULT_LIMITE_FATURAMENTO)
            .limiteDepartamentos(DEFAULT_LIMITE_DEPARTAMENTOS)
            .situacaoContador(DEFAULT_SITUACAO_CONTADOR);
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
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpeditor(UPDATED_RG_ORGAO_EXPEDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .localNascimento(UPDATED_LOCAL_NASCIMENTO)
            .racaECor(UPDATED_RACA_E_COR)
            .pessoaComDeficiencia(UPDATED_PESSOA_COM_DEFICIENCIA)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL)
            .rgOrgaoExpditor(UPDATED_RG_ORGAO_EXPDITOR)
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteAreaContabils(UPDATED_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .situacaoContador(UPDATED_SITUACAO_CONTADOR);
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
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
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
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        contador.setNome(null);

        // Create the Contador, which fails.

        restContadorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        contador.setCpf(null);

        // Create the Contador, which fails.

        restContadorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRgIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        contador.setRg(null);

        // Create the Contador, which fails.

        restContadorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        contador.setSexo(null);

        // Create the Contador, which fails.

        restContadorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCrcIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        contador.setCrc(null);

        // Create the Contador, which fails.

        restContadorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].tituloEleitor").value(hasItem(DEFAULT_TITULO_ELEITOR)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].rgOrgaoExpeditor").value(hasItem(DEFAULT_RG_ORGAO_EXPEDITOR)))
            .andExpect(jsonPath("$.[*].rgUfExpedicao").value(hasItem(DEFAULT_RG_UF_EXPEDICAO)))
            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE)))
            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI)))
            .andExpect(jsonPath("$.[*].localNascimento").value(hasItem(DEFAULT_LOCAL_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].racaECor").value(hasItem(DEFAULT_RACA_E_COR.toString())))
            .andExpect(jsonPath("$.[*].pessoaComDeficiencia").value(hasItem(DEFAULT_PESSOA_COM_DEFICIENCIA.toString())))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].urlFotoPerfil").value(hasItem(DEFAULT_URL_FOTO_PERFIL)))
            .andExpect(jsonPath("$.[*].rgOrgaoExpditor").value(hasItem(DEFAULT_RG_ORGAO_EXPDITOR)))
            .andExpect(jsonPath("$.[*].crc").value(hasItem(DEFAULT_CRC)))
            .andExpect(jsonPath("$.[*].limiteEmpresas").value(hasItem(DEFAULT_LIMITE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].limiteAreaContabils").value(hasItem(DEFAULT_LIMITE_AREA_CONTABILS)))
            .andExpect(jsonPath("$.[*].limiteFaturamento").value(hasItem(DEFAULT_LIMITE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].limiteDepartamentos").value(hasItem(DEFAULT_LIMITE_DEPARTAMENTOS)))
            .andExpect(jsonPath("$.[*].situacaoContador").value(hasItem(DEFAULT_SITUACAO_CONTADOR.toString())));
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
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO))
            .andExpect(jsonPath("$.tituloEleitor").value(DEFAULT_TITULO_ELEITOR))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG))
            .andExpect(jsonPath("$.rgOrgaoExpeditor").value(DEFAULT_RG_ORGAO_EXPEDITOR))
            .andExpect(jsonPath("$.rgUfExpedicao").value(DEFAULT_RG_UF_EXPEDICAO))
            .andExpect(jsonPath("$.nomeMae").value(DEFAULT_NOME_MAE))
            .andExpect(jsonPath("$.nomePai").value(DEFAULT_NOME_PAI))
            .andExpect(jsonPath("$.localNascimento").value(DEFAULT_LOCAL_NASCIMENTO))
            .andExpect(jsonPath("$.racaECor").value(DEFAULT_RACA_E_COR.toString()))
            .andExpect(jsonPath("$.pessoaComDeficiencia").value(DEFAULT_PESSOA_COM_DEFICIENCIA.toString()))
            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.urlFotoPerfil").value(DEFAULT_URL_FOTO_PERFIL))
            .andExpect(jsonPath("$.rgOrgaoExpditor").value(DEFAULT_RG_ORGAO_EXPDITOR))
            .andExpect(jsonPath("$.crc").value(DEFAULT_CRC))
            .andExpect(jsonPath("$.limiteEmpresas").value(DEFAULT_LIMITE_EMPRESAS))
            .andExpect(jsonPath("$.limiteAreaContabils").value(DEFAULT_LIMITE_AREA_CONTABILS))
            .andExpect(jsonPath("$.limiteFaturamento").value(DEFAULT_LIMITE_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.limiteDepartamentos").value(DEFAULT_LIMITE_DEPARTAMENTOS))
            .andExpect(jsonPath("$.situacaoContador").value(DEFAULT_SITUACAO_CONTADOR.toString()));
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
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpeditor(UPDATED_RG_ORGAO_EXPEDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .localNascimento(UPDATED_LOCAL_NASCIMENTO)
            .racaECor(UPDATED_RACA_E_COR)
            .pessoaComDeficiencia(UPDATED_PESSOA_COM_DEFICIENCIA)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL)
            .rgOrgaoExpditor(UPDATED_RG_ORGAO_EXPDITOR)
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteAreaContabils(UPDATED_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .situacaoContador(UPDATED_SITUACAO_CONTADOR);

        restContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContador.getId())
                    .with(csrf())
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
                put(ENTITY_API_URL_ID, contador.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contador))
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
                    .with(csrf())
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
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contador)))
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
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .racaECor(UPDATED_RACA_E_COR)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL)
            .rgOrgaoExpditor(UPDATED_RG_ORGAO_EXPDITOR)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteAreaContabils(UPDATED_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .situacaoContador(UPDATED_SITUACAO_CONTADOR);

        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContador.getId())
                    .with(csrf())
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
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpeditor(UPDATED_RG_ORGAO_EXPEDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .localNascimento(UPDATED_LOCAL_NASCIMENTO)
            .racaECor(UPDATED_RACA_E_COR)
            .pessoaComDeficiencia(UPDATED_PESSOA_COM_DEFICIENCIA)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL)
            .rgOrgaoExpditor(UPDATED_RG_ORGAO_EXPDITOR)
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteAreaContabils(UPDATED_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .situacaoContador(UPDATED_SITUACAO_CONTADOR);

        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContador.getId())
                    .with(csrf())
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
                    .with(csrf())
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
                    .with(csrf())
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
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contador)))
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
            .perform(delete(ENTITY_API_URL_ID, contador.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
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
