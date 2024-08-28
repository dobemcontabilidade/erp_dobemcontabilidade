package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PessoaFisicaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PessoaFisica;
import com.dobemcontabilidade.domain.enumeration.EstadoCivilEnum;
import com.dobemcontabilidade.domain.enumeration.SexoEnum;
import com.dobemcontabilidade.repository.PessoaFisicaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PessoaFisicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PessoaFisicaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITULO_ELEITOR = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_ELEITOR = "BBBBBBBBBB";

    private static final String DEFAULT_RG = "AAAAAAAAAA";
    private static final String UPDATED_RG = "BBBBBBBBBB";

    private static final String DEFAULT_RG_ORGAO_EXPDITOR = "AAAAAAAAAA";
    private static final String UPDATED_RG_ORGAO_EXPDITOR = "BBBBBBBBBB";

    private static final String DEFAULT_RG_UF_EXPEDICAO = "AAAAAAAAAA";
    private static final String UPDATED_RG_UF_EXPEDICAO = "BBBBBBBBBB";

    private static final EstadoCivilEnum DEFAULT_ESTADO_CIVIL = EstadoCivilEnum.SOLTERO;
    private static final EstadoCivilEnum UPDATED_ESTADO_CIVIL = EstadoCivilEnum.CASADO;

    private static final SexoEnum DEFAULT_SEXO = SexoEnum.MASCULINO;
    private static final SexoEnum UPDATED_SEXO = SexoEnum.FEMININO;

    private static final String DEFAULT_URL_FOTO_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_URL_FOTO_PERFIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pessoa-fisicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaFisicaMockMvc;

    private PessoaFisica pessoaFisica;

    private PessoaFisica insertedPessoaFisica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PessoaFisica createEntity(EntityManager em) {
        PessoaFisica pessoaFisica = new PessoaFisica()
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .tituloEleitor(DEFAULT_TITULO_ELEITOR)
            .rg(DEFAULT_RG)
            .rgOrgaoExpditor(DEFAULT_RG_ORGAO_EXPDITOR)
            .rgUfExpedicao(DEFAULT_RG_UF_EXPEDICAO)
            .estadoCivil(DEFAULT_ESTADO_CIVIL)
            .sexo(DEFAULT_SEXO)
            .urlFotoPerfil(DEFAULT_URL_FOTO_PERFIL);
        return pessoaFisica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PessoaFisica createUpdatedEntity(EntityManager em) {
        PessoaFisica pessoaFisica = new PessoaFisica()
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpditor(UPDATED_RG_ORGAO_EXPDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL);
        return pessoaFisica;
    }

    @BeforeEach
    public void initTest() {
        pessoaFisica = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPessoaFisica != null) {
            pessoaFisicaRepository.delete(insertedPessoaFisica);
            insertedPessoaFisica = null;
        }
    }

    @Test
    @Transactional
    void createPessoaFisica() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PessoaFisica
        var returnedPessoaFisica = om.readValue(
            restPessoaFisicaMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoaFisica))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PessoaFisica.class
        );

        // Validate the PessoaFisica in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPessoaFisicaUpdatableFieldsEquals(returnedPessoaFisica, getPersistedPessoaFisica(returnedPessoaFisica));

        insertedPessoaFisica = returnedPessoaFisica;
    }

    @Test
    @Transactional
    void createPessoaFisicaWithExistingId() throws Exception {
        // Create the PessoaFisica with an existing ID
        pessoaFisica.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaFisicaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoaFisica)))
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoaFisica.setNome(null);

        // Create the PessoaFisica, which fails.

        restPessoaFisicaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoaFisica)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoaFisica.setCpf(null);

        // Create the PessoaFisica, which fails.

        restPessoaFisicaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoaFisica)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRgIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoaFisica.setRg(null);

        // Create the PessoaFisica, which fails.

        restPessoaFisicaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoaFisica)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoaFisica.setSexo(null);

        // Create the PessoaFisica, which fails.

        restPessoaFisicaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoaFisica)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPessoaFisicas() throws Exception {
        // Initialize the database
        insertedPessoaFisica = pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        // Get all the pessoaFisicaList
        restPessoaFisicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoaFisica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].tituloEleitor").value(hasItem(DEFAULT_TITULO_ELEITOR)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].rgOrgaoExpditor").value(hasItem(DEFAULT_RG_ORGAO_EXPDITOR)))
            .andExpect(jsonPath("$.[*].rgUfExpedicao").value(hasItem(DEFAULT_RG_UF_EXPEDICAO)))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].urlFotoPerfil").value(hasItem(DEFAULT_URL_FOTO_PERFIL)));
    }

    @Test
    @Transactional
    void getPessoaFisica() throws Exception {
        // Initialize the database
        insertedPessoaFisica = pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        // Get the pessoaFisica
        restPessoaFisicaMockMvc
            .perform(get(ENTITY_API_URL_ID, pessoaFisica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoaFisica.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.tituloEleitor").value(DEFAULT_TITULO_ELEITOR))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG))
            .andExpect(jsonPath("$.rgOrgaoExpditor").value(DEFAULT_RG_ORGAO_EXPDITOR))
            .andExpect(jsonPath("$.rgUfExpedicao").value(DEFAULT_RG_UF_EXPEDICAO))
            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.urlFotoPerfil").value(DEFAULT_URL_FOTO_PERFIL));
    }

    @Test
    @Transactional
    void getNonExistingPessoaFisica() throws Exception {
        // Get the pessoaFisica
        restPessoaFisicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPessoaFisica() throws Exception {
        // Initialize the database
        insertedPessoaFisica = pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoaFisica
        PessoaFisica updatedPessoaFisica = pessoaFisicaRepository.findById(pessoaFisica.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPessoaFisica are not directly saved in db
        em.detach(updatedPessoaFisica);
        updatedPessoaFisica
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpditor(UPDATED_RG_ORGAO_EXPDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL);

        restPessoaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPessoaFisica.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPessoaFisica))
            )
            .andExpect(status().isOk());

        // Validate the PessoaFisica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPessoaFisicaToMatchAllProperties(updatedPessoaFisica);
    }

    @Test
    @Transactional
    void putNonExistingPessoaFisica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoaFisica.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaFisica.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pessoaFisica))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPessoaFisica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoaFisica.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pessoaFisica))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPessoaFisica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoaFisica.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoaFisica)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PessoaFisica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePessoaFisicaWithPatch() throws Exception {
        // Initialize the database
        insertedPessoaFisica = pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoaFisica using partial update
        PessoaFisica partialUpdatedPessoaFisica = new PessoaFisica();
        partialUpdatedPessoaFisica.setId(pessoaFisica.getId());

        partialUpdatedPessoaFisica
            .rg(UPDATED_RG)
            .rgOrgaoExpditor(UPDATED_RG_ORGAO_EXPDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .estadoCivil(UPDATED_ESTADO_CIVIL);

        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoaFisica.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPessoaFisica))
            )
            .andExpect(status().isOk());

        // Validate the PessoaFisica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPessoaFisicaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPessoaFisica, pessoaFisica),
            getPersistedPessoaFisica(pessoaFisica)
        );
    }

    @Test
    @Transactional
    void fullUpdatePessoaFisicaWithPatch() throws Exception {
        // Initialize the database
        insertedPessoaFisica = pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoaFisica using partial update
        PessoaFisica partialUpdatedPessoaFisica = new PessoaFisica();
        partialUpdatedPessoaFisica.setId(pessoaFisica.getId());

        partialUpdatedPessoaFisica
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpditor(UPDATED_RG_ORGAO_EXPDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL);

        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoaFisica.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPessoaFisica))
            )
            .andExpect(status().isOk());

        // Validate the PessoaFisica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPessoaFisicaUpdatableFieldsEquals(partialUpdatedPessoaFisica, getPersistedPessoaFisica(partialUpdatedPessoaFisica));
    }

    @Test
    @Transactional
    void patchNonExistingPessoaFisica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoaFisica.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pessoaFisica.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pessoaFisica))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPessoaFisica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoaFisica.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pessoaFisica))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPessoaFisica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoaFisica.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pessoaFisica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PessoaFisica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePessoaFisica() throws Exception {
        // Initialize the database
        insertedPessoaFisica = pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pessoaFisica
        restPessoaFisicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, pessoaFisica.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pessoaFisicaRepository.count();
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

    protected PessoaFisica getPersistedPessoaFisica(PessoaFisica pessoaFisica) {
        return pessoaFisicaRepository.findById(pessoaFisica.getId()).orElseThrow();
    }

    protected void assertPersistedPessoaFisicaToMatchAllProperties(PessoaFisica expectedPessoaFisica) {
        assertPessoaFisicaAllPropertiesEquals(expectedPessoaFisica, getPersistedPessoaFisica(expectedPessoaFisica));
    }

    protected void assertPersistedPessoaFisicaToMatchUpdatableProperties(PessoaFisica expectedPessoaFisica) {
        assertPessoaFisicaAllUpdatablePropertiesEquals(expectedPessoaFisica, getPersistedPessoaFisica(expectedPessoaFisica));
    }
}
