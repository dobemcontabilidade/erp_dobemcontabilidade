package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ServicoContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.repository.ServicoContabilRepository;
import com.dobemcontabilidade.service.ServicoContabilService;
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
 * Integration tests for the {@link ServicoContabilResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServicoContabilResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DIAS_EXECUCAO = 1;
    private static final Integer UPDATED_DIAS_EXECUCAO = 2;

    private static final Boolean DEFAULT_GERA_MULTA = false;
    private static final Boolean UPDATED_GERA_MULTA = true;

    private static final Integer DEFAULT_PERIODO_EXECUCAO = 1;
    private static final Integer UPDATED_PERIODO_EXECUCAO = 2;

    private static final Integer DEFAULT_DIA_LEGAL = 1;
    private static final Integer UPDATED_DIA_LEGAL = 2;

    private static final Integer DEFAULT_MES_LEGAL = 1;
    private static final Integer UPDATED_MES_LEGAL = 2;

    private static final Double DEFAULT_VALOR_REF_MULTA = 1D;
    private static final Double UPDATED_VALOR_REF_MULTA = 2D;

    private static final String ENTITY_API_URL = "/api/servico-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicoContabilRepository servicoContabilRepository;

    @Mock
    private ServicoContabilRepository servicoContabilRepositoryMock;

    @Mock
    private ServicoContabilService servicoContabilServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicoContabilMockMvc;

    private ServicoContabil servicoContabil;

    private ServicoContabil insertedServicoContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabil createEntity(EntityManager em) {
        ServicoContabil servicoContabil = new ServicoContabil()
            .nome(DEFAULT_NOME)
            .valor(DEFAULT_VALOR)
            .descricao(DEFAULT_DESCRICAO)
            .diasExecucao(DEFAULT_DIAS_EXECUCAO)
            .geraMulta(DEFAULT_GERA_MULTA)
            .periodoExecucao(DEFAULT_PERIODO_EXECUCAO)
            .diaLegal(DEFAULT_DIA_LEGAL)
            .mesLegal(DEFAULT_MES_LEGAL)
            .valorRefMulta(DEFAULT_VALOR_REF_MULTA);
        // Add required entity
        AreaContabil areaContabil;
        if (TestUtil.findAll(em, AreaContabil.class).isEmpty()) {
            areaContabil = AreaContabilResourceIT.createEntity(em);
            em.persist(areaContabil);
            em.flush();
        } else {
            areaContabil = TestUtil.findAll(em, AreaContabil.class).get(0);
        }
        servicoContabil.setAreaContabil(areaContabil);
        // Add required entity
        Esfera esfera;
        if (TestUtil.findAll(em, Esfera.class).isEmpty()) {
            esfera = EsferaResourceIT.createEntity(em);
            em.persist(esfera);
            em.flush();
        } else {
            esfera = TestUtil.findAll(em, Esfera.class).get(0);
        }
        servicoContabil.setEsfera(esfera);
        return servicoContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabil createUpdatedEntity(EntityManager em) {
        ServicoContabil servicoContabil = new ServicoContabil()
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .descricao(UPDATED_DESCRICAO)
            .diasExecucao(UPDATED_DIAS_EXECUCAO)
            .geraMulta(UPDATED_GERA_MULTA)
            .periodoExecucao(UPDATED_PERIODO_EXECUCAO)
            .diaLegal(UPDATED_DIA_LEGAL)
            .mesLegal(UPDATED_MES_LEGAL)
            .valorRefMulta(UPDATED_VALOR_REF_MULTA);
        // Add required entity
        AreaContabil areaContabil;
        if (TestUtil.findAll(em, AreaContabil.class).isEmpty()) {
            areaContabil = AreaContabilResourceIT.createUpdatedEntity(em);
            em.persist(areaContabil);
            em.flush();
        } else {
            areaContabil = TestUtil.findAll(em, AreaContabil.class).get(0);
        }
        servicoContabil.setAreaContabil(areaContabil);
        // Add required entity
        Esfera esfera;
        if (TestUtil.findAll(em, Esfera.class).isEmpty()) {
            esfera = EsferaResourceIT.createUpdatedEntity(em);
            em.persist(esfera);
            em.flush();
        } else {
            esfera = TestUtil.findAll(em, Esfera.class).get(0);
        }
        servicoContabil.setEsfera(esfera);
        return servicoContabil;
    }

    @BeforeEach
    public void initTest() {
        servicoContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicoContabil != null) {
            servicoContabilRepository.delete(insertedServicoContabil);
            insertedServicoContabil = null;
        }
    }

    @Test
    @Transactional
    void createServicoContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServicoContabil
        var returnedServicoContabil = om.readValue(
            restServicoContabilMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabil)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServicoContabil.class
        );

        // Validate the ServicoContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServicoContabilUpdatableFieldsEquals(returnedServicoContabil, getPersistedServicoContabil(returnedServicoContabil));

        insertedServicoContabil = returnedServicoContabil;
    }

    @Test
    @Transactional
    void createServicoContabilWithExistingId() throws Exception {
        // Create the ServicoContabil with an existing ID
        servicoContabil.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicoContabilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabil)))
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicoContabils() throws Exception {
        // Initialize the database
        insertedServicoContabil = servicoContabilRepository.saveAndFlush(servicoContabil);

        // Get all the servicoContabilList
        restServicoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].diasExecucao").value(hasItem(DEFAULT_DIAS_EXECUCAO)))
            .andExpect(jsonPath("$.[*].geraMulta").value(hasItem(DEFAULT_GERA_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].periodoExecucao").value(hasItem(DEFAULT_PERIODO_EXECUCAO)))
            .andExpect(jsonPath("$.[*].diaLegal").value(hasItem(DEFAULT_DIA_LEGAL)))
            .andExpect(jsonPath("$.[*].mesLegal").value(hasItem(DEFAULT_MES_LEGAL)))
            .andExpect(jsonPath("$.[*].valorRefMulta").value(hasItem(DEFAULT_VALOR_REF_MULTA.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilsWithEagerRelationshipsIsEnabled() throws Exception {
        when(servicoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(servicoContabilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(servicoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(servicoContabilRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getServicoContabil() throws Exception {
        // Initialize the database
        insertedServicoContabil = servicoContabilRepository.saveAndFlush(servicoContabil);

        // Get the servicoContabil
        restServicoContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, servicoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicoContabil.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.diasExecucao").value(DEFAULT_DIAS_EXECUCAO))
            .andExpect(jsonPath("$.geraMulta").value(DEFAULT_GERA_MULTA.booleanValue()))
            .andExpect(jsonPath("$.periodoExecucao").value(DEFAULT_PERIODO_EXECUCAO))
            .andExpect(jsonPath("$.diaLegal").value(DEFAULT_DIA_LEGAL))
            .andExpect(jsonPath("$.mesLegal").value(DEFAULT_MES_LEGAL))
            .andExpect(jsonPath("$.valorRefMulta").value(DEFAULT_VALOR_REF_MULTA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingServicoContabil() throws Exception {
        // Get the servicoContabil
        restServicoContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicoContabil() throws Exception {
        // Initialize the database
        insertedServicoContabil = servicoContabilRepository.saveAndFlush(servicoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabil
        ServicoContabil updatedServicoContabil = servicoContabilRepository.findById(servicoContabil.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedServicoContabil are not directly saved in db
        em.detach(updatedServicoContabil);
        updatedServicoContabil
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .descricao(UPDATED_DESCRICAO)
            .diasExecucao(UPDATED_DIAS_EXECUCAO)
            .geraMulta(UPDATED_GERA_MULTA)
            .periodoExecucao(UPDATED_PERIODO_EXECUCAO)
            .diaLegal(UPDATED_DIA_LEGAL)
            .mesLegal(UPDATED_MES_LEGAL)
            .valorRefMulta(UPDATED_VALOR_REF_MULTA);

        restServicoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicoContabil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServicoContabil))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicoContabilToMatchAllProperties(updatedServicoContabil);
    }

    @Test
    @Transactional
    void putNonExistingServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicoContabil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabil = servicoContabilRepository.saveAndFlush(servicoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabil using partial update
        ServicoContabil partialUpdatedServicoContabil = new ServicoContabil();
        partialUpdatedServicoContabil.setId(servicoContabil.getId());

        partialUpdatedServicoContabil
            .nome(UPDATED_NOME)
            .geraMulta(UPDATED_GERA_MULTA)
            .periodoExecucao(UPDATED_PERIODO_EXECUCAO)
            .diaLegal(UPDATED_DIA_LEGAL)
            .mesLegal(UPDATED_MES_LEGAL);

        restServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabil))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicoContabil, servicoContabil),
            getPersistedServicoContabil(servicoContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdateServicoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabil = servicoContabilRepository.saveAndFlush(servicoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabil using partial update
        ServicoContabil partialUpdatedServicoContabil = new ServicoContabil();
        partialUpdatedServicoContabil.setId(servicoContabil.getId());

        partialUpdatedServicoContabil
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .descricao(UPDATED_DESCRICAO)
            .diasExecucao(UPDATED_DIAS_EXECUCAO)
            .geraMulta(UPDATED_GERA_MULTA)
            .periodoExecucao(UPDATED_PERIODO_EXECUCAO)
            .diaLegal(UPDATED_DIA_LEGAL)
            .mesLegal(UPDATED_MES_LEGAL)
            .valorRefMulta(UPDATED_VALOR_REF_MULTA);

        restServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabil))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilUpdatableFieldsEquals(
            partialUpdatedServicoContabil,
            getPersistedServicoContabil(partialUpdatedServicoContabil)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(servicoContabil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicoContabil() throws Exception {
        // Initialize the database
        insertedServicoContabil = servicoContabilRepository.saveAndFlush(servicoContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicoContabil
        restServicoContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicoContabil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicoContabilRepository.count();
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

    protected ServicoContabil getPersistedServicoContabil(ServicoContabil servicoContabil) {
        return servicoContabilRepository.findById(servicoContabil.getId()).orElseThrow();
    }

    protected void assertPersistedServicoContabilToMatchAllProperties(ServicoContabil expectedServicoContabil) {
        assertServicoContabilAllPropertiesEquals(expectedServicoContabil, getPersistedServicoContabil(expectedServicoContabil));
    }

    protected void assertPersistedServicoContabilToMatchUpdatableProperties(ServicoContabil expectedServicoContabil) {
        assertServicoContabilAllUpdatablePropertiesEquals(expectedServicoContabil, getPersistedServicoContabil(expectedServicoContabil));
    }
}
