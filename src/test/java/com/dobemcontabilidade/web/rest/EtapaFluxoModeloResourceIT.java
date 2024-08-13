package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EtapaFluxoModeloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.EtapaFluxoModelo;
import com.dobemcontabilidade.domain.FluxoModelo;
import com.dobemcontabilidade.repository.EtapaFluxoModeloRepository;
import com.dobemcontabilidade.service.EtapaFluxoModeloService;
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
 * Integration tests for the {@link EtapaFluxoModeloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EtapaFluxoModeloResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final String ENTITY_API_URL = "/api/etapa-fluxo-modelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EtapaFluxoModeloRepository etapaFluxoModeloRepository;

    @Mock
    private EtapaFluxoModeloRepository etapaFluxoModeloRepositoryMock;

    @Mock
    private EtapaFluxoModeloService etapaFluxoModeloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtapaFluxoModeloMockMvc;

    private EtapaFluxoModelo etapaFluxoModelo;

    private EtapaFluxoModelo insertedEtapaFluxoModelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapaFluxoModelo createEntity(EntityManager em) {
        EtapaFluxoModelo etapaFluxoModelo = new EtapaFluxoModelo().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO).ordem(DEFAULT_ORDEM);
        // Add required entity
        FluxoModelo fluxoModelo;
        if (TestUtil.findAll(em, FluxoModelo.class).isEmpty()) {
            fluxoModelo = FluxoModeloResourceIT.createEntity(em);
            em.persist(fluxoModelo);
            em.flush();
        } else {
            fluxoModelo = TestUtil.findAll(em, FluxoModelo.class).get(0);
        }
        etapaFluxoModelo.setFluxoModelo(fluxoModelo);
        return etapaFluxoModelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapaFluxoModelo createUpdatedEntity(EntityManager em) {
        EtapaFluxoModelo etapaFluxoModelo = new EtapaFluxoModelo().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ordem(UPDATED_ORDEM);
        // Add required entity
        FluxoModelo fluxoModelo;
        if (TestUtil.findAll(em, FluxoModelo.class).isEmpty()) {
            fluxoModelo = FluxoModeloResourceIT.createUpdatedEntity(em);
            em.persist(fluxoModelo);
            em.flush();
        } else {
            fluxoModelo = TestUtil.findAll(em, FluxoModelo.class).get(0);
        }
        etapaFluxoModelo.setFluxoModelo(fluxoModelo);
        return etapaFluxoModelo;
    }

    @BeforeEach
    public void initTest() {
        etapaFluxoModelo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEtapaFluxoModelo != null) {
            etapaFluxoModeloRepository.delete(insertedEtapaFluxoModelo);
            insertedEtapaFluxoModelo = null;
        }
    }

    @Test
    @Transactional
    void createEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EtapaFluxoModelo
        var returnedEtapaFluxoModelo = om.readValue(
            restEtapaFluxoModeloMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etapaFluxoModelo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EtapaFluxoModelo.class
        );

        // Validate the EtapaFluxoModelo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEtapaFluxoModeloUpdatableFieldsEquals(returnedEtapaFluxoModelo, getPersistedEtapaFluxoModelo(returnedEtapaFluxoModelo));

        insertedEtapaFluxoModelo = returnedEtapaFluxoModelo;
    }

    @Test
    @Transactional
    void createEtapaFluxoModeloWithExistingId() throws Exception {
        // Create the EtapaFluxoModelo with an existing ID
        etapaFluxoModelo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapaFluxoModeloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etapaFluxoModelo)))
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtapaFluxoModelos() throws Exception {
        // Initialize the database
        insertedEtapaFluxoModelo = etapaFluxoModeloRepository.saveAndFlush(etapaFluxoModelo);

        // Get all the etapaFluxoModeloList
        restEtapaFluxoModeloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapaFluxoModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEtapaFluxoModelosWithEagerRelationshipsIsEnabled() throws Exception {
        when(etapaFluxoModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEtapaFluxoModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(etapaFluxoModeloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEtapaFluxoModelosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(etapaFluxoModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEtapaFluxoModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(etapaFluxoModeloRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEtapaFluxoModelo() throws Exception {
        // Initialize the database
        insertedEtapaFluxoModelo = etapaFluxoModeloRepository.saveAndFlush(etapaFluxoModelo);

        // Get the etapaFluxoModelo
        restEtapaFluxoModeloMockMvc
            .perform(get(ENTITY_API_URL_ID, etapaFluxoModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etapaFluxoModelo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM));
    }

    @Test
    @Transactional
    void getNonExistingEtapaFluxoModelo() throws Exception {
        // Get the etapaFluxoModelo
        restEtapaFluxoModeloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEtapaFluxoModelo() throws Exception {
        // Initialize the database
        insertedEtapaFluxoModelo = etapaFluxoModeloRepository.saveAndFlush(etapaFluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etapaFluxoModelo
        EtapaFluxoModelo updatedEtapaFluxoModelo = etapaFluxoModeloRepository.findById(etapaFluxoModelo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEtapaFluxoModelo are not directly saved in db
        em.detach(updatedEtapaFluxoModelo);
        updatedEtapaFluxoModelo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ordem(UPDATED_ORDEM);

        restEtapaFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtapaFluxoModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEtapaFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the EtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEtapaFluxoModeloToMatchAllProperties(updatedEtapaFluxoModelo);
    }

    @Test
    @Transactional
    void putNonExistingEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapaFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etapaFluxoModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapaFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapaFluxoModeloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etapaFluxoModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtapaFluxoModeloWithPatch() throws Exception {
        // Initialize the database
        insertedEtapaFluxoModelo = etapaFluxoModeloRepository.saveAndFlush(etapaFluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etapaFluxoModelo using partial update
        EtapaFluxoModelo partialUpdatedEtapaFluxoModelo = new EtapaFluxoModelo();
        partialUpdatedEtapaFluxoModelo.setId(etapaFluxoModelo.getId());

        partialUpdatedEtapaFluxoModelo.descricao(UPDATED_DESCRICAO);

        restEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtapaFluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEtapaFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the EtapaFluxoModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEtapaFluxoModeloUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEtapaFluxoModelo, etapaFluxoModelo),
            getPersistedEtapaFluxoModelo(etapaFluxoModelo)
        );
    }

    @Test
    @Transactional
    void fullUpdateEtapaFluxoModeloWithPatch() throws Exception {
        // Initialize the database
        insertedEtapaFluxoModelo = etapaFluxoModeloRepository.saveAndFlush(etapaFluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etapaFluxoModelo using partial update
        EtapaFluxoModelo partialUpdatedEtapaFluxoModelo = new EtapaFluxoModelo();
        partialUpdatedEtapaFluxoModelo.setId(etapaFluxoModelo.getId());

        partialUpdatedEtapaFluxoModelo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ordem(UPDATED_ORDEM);

        restEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtapaFluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEtapaFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the EtapaFluxoModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEtapaFluxoModeloUpdatableFieldsEquals(
            partialUpdatedEtapaFluxoModelo,
            getPersistedEtapaFluxoModelo(partialUpdatedEtapaFluxoModelo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etapaFluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(etapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(etapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapaFluxoModeloMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(etapaFluxoModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtapaFluxoModelo() throws Exception {
        // Initialize the database
        insertedEtapaFluxoModelo = etapaFluxoModeloRepository.saveAndFlush(etapaFluxoModelo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the etapaFluxoModelo
        restEtapaFluxoModeloMockMvc
            .perform(delete(ENTITY_API_URL_ID, etapaFluxoModelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return etapaFluxoModeloRepository.count();
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

    protected EtapaFluxoModelo getPersistedEtapaFluxoModelo(EtapaFluxoModelo etapaFluxoModelo) {
        return etapaFluxoModeloRepository.findById(etapaFluxoModelo.getId()).orElseThrow();
    }

    protected void assertPersistedEtapaFluxoModeloToMatchAllProperties(EtapaFluxoModelo expectedEtapaFluxoModelo) {
        assertEtapaFluxoModeloAllPropertiesEquals(expectedEtapaFluxoModelo, getPersistedEtapaFluxoModelo(expectedEtapaFluxoModelo));
    }

    protected void assertPersistedEtapaFluxoModeloToMatchUpdatableProperties(EtapaFluxoModelo expectedEtapaFluxoModelo) {
        assertEtapaFluxoModeloAllUpdatablePropertiesEquals(
            expectedEtapaFluxoModelo,
            getPersistedEtapaFluxoModelo(expectedEtapaFluxoModelo)
        );
    }
}
