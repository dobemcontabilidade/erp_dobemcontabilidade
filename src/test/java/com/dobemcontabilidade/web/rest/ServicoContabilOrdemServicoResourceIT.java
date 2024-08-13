package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ServicoContabilOrdemServicoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.OrdemServico;
import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.domain.ServicoContabilOrdemServico;
import com.dobemcontabilidade.repository.ServicoContabilOrdemServicoRepository;
import com.dobemcontabilidade.service.ServicoContabilOrdemServicoService;
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
 * Integration tests for the {@link ServicoContabilOrdemServicoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServicoContabilOrdemServicoResourceIT {

    private static final Instant DEFAULT_DATA_ADMIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ADMIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_LEGAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_LEGAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/servico-contabil-ordem-servicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicoContabilOrdemServicoRepository servicoContabilOrdemServicoRepository;

    @Mock
    private ServicoContabilOrdemServicoRepository servicoContabilOrdemServicoRepositoryMock;

    @Mock
    private ServicoContabilOrdemServicoService servicoContabilOrdemServicoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicoContabilOrdemServicoMockMvc;

    private ServicoContabilOrdemServico servicoContabilOrdemServico;

    private ServicoContabilOrdemServico insertedServicoContabilOrdemServico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilOrdemServico createEntity(EntityManager em) {
        ServicoContabilOrdemServico servicoContabilOrdemServico = new ServicoContabilOrdemServico()
            .dataAdmin(DEFAULT_DATA_ADMIN)
            .dataLegal(DEFAULT_DATA_LEGAL);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilOrdemServico.setServicoContabil(servicoContabil);
        // Add required entity
        OrdemServico ordemServico;
        if (TestUtil.findAll(em, OrdemServico.class).isEmpty()) {
            ordemServico = OrdemServicoResourceIT.createEntity(em);
            em.persist(ordemServico);
            em.flush();
        } else {
            ordemServico = TestUtil.findAll(em, OrdemServico.class).get(0);
        }
        servicoContabilOrdemServico.setOrdemServico(ordemServico);
        return servicoContabilOrdemServico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilOrdemServico createUpdatedEntity(EntityManager em) {
        ServicoContabilOrdemServico servicoContabilOrdemServico = new ServicoContabilOrdemServico()
            .dataAdmin(UPDATED_DATA_ADMIN)
            .dataLegal(UPDATED_DATA_LEGAL);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilOrdemServico.setServicoContabil(servicoContabil);
        // Add required entity
        OrdemServico ordemServico;
        if (TestUtil.findAll(em, OrdemServico.class).isEmpty()) {
            ordemServico = OrdemServicoResourceIT.createUpdatedEntity(em);
            em.persist(ordemServico);
            em.flush();
        } else {
            ordemServico = TestUtil.findAll(em, OrdemServico.class).get(0);
        }
        servicoContabilOrdemServico.setOrdemServico(ordemServico);
        return servicoContabilOrdemServico;
    }

    @BeforeEach
    public void initTest() {
        servicoContabilOrdemServico = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicoContabilOrdemServico != null) {
            servicoContabilOrdemServicoRepository.delete(insertedServicoContabilOrdemServico);
            insertedServicoContabilOrdemServico = null;
        }
    }

    @Test
    @Transactional
    void createServicoContabilOrdemServico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServicoContabilOrdemServico
        var returnedServicoContabilOrdemServico = om.readValue(
            restServicoContabilOrdemServicoMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilOrdemServico))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServicoContabilOrdemServico.class
        );

        // Validate the ServicoContabilOrdemServico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServicoContabilOrdemServicoUpdatableFieldsEquals(
            returnedServicoContabilOrdemServico,
            getPersistedServicoContabilOrdemServico(returnedServicoContabilOrdemServico)
        );

        insertedServicoContabilOrdemServico = returnedServicoContabilOrdemServico;
    }

    @Test
    @Transactional
    void createServicoContabilOrdemServicoWithExistingId() throws Exception {
        // Create the ServicoContabilOrdemServico with an existing ID
        servicoContabilOrdemServico.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicoContabilOrdemServicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicoContabilOrdemServicos() throws Exception {
        // Initialize the database
        insertedServicoContabilOrdemServico = servicoContabilOrdemServicoRepository.saveAndFlush(servicoContabilOrdemServico);

        // Get all the servicoContabilOrdemServicoList
        restServicoContabilOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicoContabilOrdemServico.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAdmin").value(hasItem(DEFAULT_DATA_ADMIN.toString())))
            .andExpect(jsonPath("$.[*].dataLegal").value(hasItem(DEFAULT_DATA_LEGAL.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilOrdemServicosWithEagerRelationshipsIsEnabled() throws Exception {
        when(servicoContabilOrdemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(servicoContabilOrdemServicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilOrdemServicosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(servicoContabilOrdemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(servicoContabilOrdemServicoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getServicoContabilOrdemServico() throws Exception {
        // Initialize the database
        insertedServicoContabilOrdemServico = servicoContabilOrdemServicoRepository.saveAndFlush(servicoContabilOrdemServico);

        // Get the servicoContabilOrdemServico
        restServicoContabilOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL_ID, servicoContabilOrdemServico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicoContabilOrdemServico.getId().intValue()))
            .andExpect(jsonPath("$.dataAdmin").value(DEFAULT_DATA_ADMIN.toString()))
            .andExpect(jsonPath("$.dataLegal").value(DEFAULT_DATA_LEGAL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingServicoContabilOrdemServico() throws Exception {
        // Get the servicoContabilOrdemServico
        restServicoContabilOrdemServicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicoContabilOrdemServico() throws Exception {
        // Initialize the database
        insertedServicoContabilOrdemServico = servicoContabilOrdemServicoRepository.saveAndFlush(servicoContabilOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilOrdemServico
        ServicoContabilOrdemServico updatedServicoContabilOrdemServico = servicoContabilOrdemServicoRepository
            .findById(servicoContabilOrdemServico.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedServicoContabilOrdemServico are not directly saved in db
        em.detach(updatedServicoContabilOrdemServico);
        updatedServicoContabilOrdemServico.dataAdmin(UPDATED_DATA_ADMIN).dataLegal(UPDATED_DATA_LEGAL);

        restServicoContabilOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicoContabilOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServicoContabilOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicoContabilOrdemServicoToMatchAllProperties(updatedServicoContabilOrdemServico);
    }

    @Test
    @Transactional
    void putNonExistingServicoContabilOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicoContabilOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicoContabilOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicoContabilOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilOrdemServicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilOrdemServico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicoContabilOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilOrdemServico = servicoContabilOrdemServicoRepository.saveAndFlush(servicoContabilOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilOrdemServico using partial update
        ServicoContabilOrdemServico partialUpdatedServicoContabilOrdemServico = new ServicoContabilOrdemServico();
        partialUpdatedServicoContabilOrdemServico.setId(servicoContabilOrdemServico.getId());

        partialUpdatedServicoContabilOrdemServico.dataAdmin(UPDATED_DATA_ADMIN).dataLegal(UPDATED_DATA_LEGAL);

        restServicoContabilOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilOrdemServicoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicoContabilOrdemServico, servicoContabilOrdemServico),
            getPersistedServicoContabilOrdemServico(servicoContabilOrdemServico)
        );
    }

    @Test
    @Transactional
    void fullUpdateServicoContabilOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilOrdemServico = servicoContabilOrdemServicoRepository.saveAndFlush(servicoContabilOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilOrdemServico using partial update
        ServicoContabilOrdemServico partialUpdatedServicoContabilOrdemServico = new ServicoContabilOrdemServico();
        partialUpdatedServicoContabilOrdemServico.setId(servicoContabilOrdemServico.getId());

        partialUpdatedServicoContabilOrdemServico.dataAdmin(UPDATED_DATA_ADMIN).dataLegal(UPDATED_DATA_LEGAL);

        restServicoContabilOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilOrdemServicoUpdatableFieldsEquals(
            partialUpdatedServicoContabilOrdemServico,
            getPersistedServicoContabilOrdemServico(partialUpdatedServicoContabilOrdemServico)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServicoContabilOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicoContabilOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicoContabilOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicoContabilOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(servicoContabilOrdemServico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicoContabilOrdemServico() throws Exception {
        // Initialize the database
        insertedServicoContabilOrdemServico = servicoContabilOrdemServicoRepository.saveAndFlush(servicoContabilOrdemServico);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicoContabilOrdemServico
        restServicoContabilOrdemServicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicoContabilOrdemServico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicoContabilOrdemServicoRepository.count();
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

    protected ServicoContabilOrdemServico getPersistedServicoContabilOrdemServico(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        return servicoContabilOrdemServicoRepository.findById(servicoContabilOrdemServico.getId()).orElseThrow();
    }

    protected void assertPersistedServicoContabilOrdemServicoToMatchAllProperties(
        ServicoContabilOrdemServico expectedServicoContabilOrdemServico
    ) {
        assertServicoContabilOrdemServicoAllPropertiesEquals(
            expectedServicoContabilOrdemServico,
            getPersistedServicoContabilOrdemServico(expectedServicoContabilOrdemServico)
        );
    }

    protected void assertPersistedServicoContabilOrdemServicoToMatchUpdatableProperties(
        ServicoContabilOrdemServico expectedServicoContabilOrdemServico
    ) {
        assertServicoContabilOrdemServicoAllUpdatablePropertiesEquals(
            expectedServicoContabilOrdemServico,
            getPersistedServicoContabilOrdemServico(expectedServicoContabilOrdemServico)
        );
    }
}
