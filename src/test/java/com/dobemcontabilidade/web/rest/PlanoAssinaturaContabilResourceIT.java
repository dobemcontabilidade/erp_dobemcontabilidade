package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PlanoAssinaturaContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContabilEnum;
import com.dobemcontabilidade.repository.PlanoAssinaturaContabilRepository;
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
 * Integration tests for the {@link PlanoAssinaturaContabilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanoAssinaturaContabilResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_ADICIONAL_SOCIO = 1D;
    private static final Double UPDATED_ADICIONAL_SOCIO = 2D;

    private static final Double DEFAULT_ADICIONAL_FUNCIONARIO = 1D;
    private static final Double UPDATED_ADICIONAL_FUNCIONARIO = 2D;

    private static final Integer DEFAULT_SOCIOS_ISENTOS = 1;
    private static final Integer UPDATED_SOCIOS_ISENTOS = 2;

    private static final Double DEFAULT_ADICIONAL_FATURAMENTO = 1D;
    private static final Double UPDATED_ADICIONAL_FATURAMENTO = 2D;

    private static final Double DEFAULT_VALOR_BASE_FATURAMENTO = 1D;
    private static final Double UPDATED_VALOR_BASE_FATURAMENTO = 2D;

    private static final Double DEFAULT_VALOR_BASE_ABERTURA = 1D;
    private static final Double UPDATED_VALOR_BASE_ABERTURA = 2D;

    private static final SituacaoPlanoContabilEnum DEFAULT_SITUACAO = SituacaoPlanoContabilEnum.ATIVO;
    private static final SituacaoPlanoContabilEnum UPDATED_SITUACAO = SituacaoPlanoContabilEnum.INATIVO;

    private static final String ENTITY_API_URL = "/api/plano-assinatura-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlanoAssinaturaContabilRepository planoAssinaturaContabilRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoAssinaturaContabilMockMvc;

    private PlanoAssinaturaContabil planoAssinaturaContabil;

    private PlanoAssinaturaContabil insertedPlanoAssinaturaContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAssinaturaContabil createEntity(EntityManager em) {
        PlanoAssinaturaContabil planoAssinaturaContabil = new PlanoAssinaturaContabil()
            .nome(DEFAULT_NOME)
            .adicionalSocio(DEFAULT_ADICIONAL_SOCIO)
            .adicionalFuncionario(DEFAULT_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(DEFAULT_SOCIOS_ISENTOS)
            .adicionalFaturamento(DEFAULT_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(DEFAULT_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(DEFAULT_VALOR_BASE_ABERTURA)
            .situacao(DEFAULT_SITUACAO);
        return planoAssinaturaContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAssinaturaContabil createUpdatedEntity(EntityManager em) {
        PlanoAssinaturaContabil planoAssinaturaContabil = new PlanoAssinaturaContabil()
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);
        return planoAssinaturaContabil;
    }

    @BeforeEach
    public void initTest() {
        planoAssinaturaContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPlanoAssinaturaContabil != null) {
            planoAssinaturaContabilRepository.delete(insertedPlanoAssinaturaContabil);
            insertedPlanoAssinaturaContabil = null;
        }
    }

    @Test
    @Transactional
    void createPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlanoAssinaturaContabil
        var returnedPlanoAssinaturaContabil = om.readValue(
            restPlanoAssinaturaContabilMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(planoAssinaturaContabil))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlanoAssinaturaContabil.class
        );

        // Validate the PlanoAssinaturaContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlanoAssinaturaContabilUpdatableFieldsEquals(
            returnedPlanoAssinaturaContabil,
            getPersistedPlanoAssinaturaContabil(returnedPlanoAssinaturaContabil)
        );

        insertedPlanoAssinaturaContabil = returnedPlanoAssinaturaContabil;
    }

    @Test
    @Transactional
    void createPlanoAssinaturaContabilWithExistingId() throws Exception {
        // Create the PlanoAssinaturaContabil with an existing ID
        planoAssinaturaContabil.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoAssinaturaContabilMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabils() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList
        restPlanoAssinaturaContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoAssinaturaContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].adicionalSocio").value(hasItem(DEFAULT_ADICIONAL_SOCIO.doubleValue())))
            .andExpect(jsonPath("$.[*].adicionalFuncionario").value(hasItem(DEFAULT_ADICIONAL_FUNCIONARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].sociosIsentos").value(hasItem(DEFAULT_SOCIOS_ISENTOS)))
            .andExpect(jsonPath("$.[*].adicionalFaturamento").value(hasItem(DEFAULT_ADICIONAL_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorBaseFaturamento").value(hasItem(DEFAULT_VALOR_BASE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorBaseAbertura").value(hasItem(DEFAULT_VALOR_BASE_ABERTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @Test
    @Transactional
    void getPlanoAssinaturaContabil() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get the planoAssinaturaContabil
        restPlanoAssinaturaContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, planoAssinaturaContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoAssinaturaContabil.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.adicionalSocio").value(DEFAULT_ADICIONAL_SOCIO.doubleValue()))
            .andExpect(jsonPath("$.adicionalFuncionario").value(DEFAULT_ADICIONAL_FUNCIONARIO.doubleValue()))
            .andExpect(jsonPath("$.sociosIsentos").value(DEFAULT_SOCIOS_ISENTOS))
            .andExpect(jsonPath("$.adicionalFaturamento").value(DEFAULT_ADICIONAL_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.valorBaseFaturamento").value(DEFAULT_VALOR_BASE_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.valorBaseAbertura").value(DEFAULT_VALOR_BASE_ABERTURA.doubleValue()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlanoAssinaturaContabil() throws Exception {
        // Get the planoAssinaturaContabil
        restPlanoAssinaturaContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoAssinaturaContabil() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoAssinaturaContabil
        PlanoAssinaturaContabil updatedPlanoAssinaturaContabil = planoAssinaturaContabilRepository
            .findById(planoAssinaturaContabil.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPlanoAssinaturaContabil are not directly saved in db
        em.detach(updatedPlanoAssinaturaContabil);
        updatedPlanoAssinaturaContabil
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);

        restPlanoAssinaturaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlanoAssinaturaContabil))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlanoAssinaturaContabilToMatchAllProperties(updatedPlanoAssinaturaContabil);
    }

    @Test
    @Transactional
    void putNonExistingPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoAssinaturaContabilWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoAssinaturaContabil using partial update
        PlanoAssinaturaContabil partialUpdatedPlanoAssinaturaContabil = new PlanoAssinaturaContabil();
        partialUpdatedPlanoAssinaturaContabil.setId(planoAssinaturaContabil.getId());

        partialUpdatedPlanoAssinaturaContabil
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA);

        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoAssinaturaContabil))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAssinaturaContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoAssinaturaContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlanoAssinaturaContabil, planoAssinaturaContabil),
            getPersistedPlanoAssinaturaContabil(planoAssinaturaContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlanoAssinaturaContabilWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoAssinaturaContabil using partial update
        PlanoAssinaturaContabil partialUpdatedPlanoAssinaturaContabil = new PlanoAssinaturaContabil();
        partialUpdatedPlanoAssinaturaContabil.setId(planoAssinaturaContabil.getId());

        partialUpdatedPlanoAssinaturaContabil
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);

        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoAssinaturaContabil))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAssinaturaContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoAssinaturaContabilUpdatableFieldsEquals(
            partialUpdatedPlanoAssinaturaContabil,
            getPersistedPlanoAssinaturaContabil(partialUpdatedPlanoAssinaturaContabil)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoAssinaturaContabil() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the planoAssinaturaContabil
        restPlanoAssinaturaContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoAssinaturaContabil.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return planoAssinaturaContabilRepository.count();
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

    protected PlanoAssinaturaContabil getPersistedPlanoAssinaturaContabil(PlanoAssinaturaContabil planoAssinaturaContabil) {
        return planoAssinaturaContabilRepository.findById(planoAssinaturaContabil.getId()).orElseThrow();
    }

    protected void assertPersistedPlanoAssinaturaContabilToMatchAllProperties(PlanoAssinaturaContabil expectedPlanoAssinaturaContabil) {
        assertPlanoAssinaturaContabilAllPropertiesEquals(
            expectedPlanoAssinaturaContabil,
            getPersistedPlanoAssinaturaContabil(expectedPlanoAssinaturaContabil)
        );
    }

    protected void assertPersistedPlanoAssinaturaContabilToMatchUpdatableProperties(
        PlanoAssinaturaContabil expectedPlanoAssinaturaContabil
    ) {
        assertPlanoAssinaturaContabilAllUpdatablePropertiesEquals(
            expectedPlanoAssinaturaContabil,
            getPersistedPlanoAssinaturaContabil(expectedPlanoAssinaturaContabil)
        );
    }
}
