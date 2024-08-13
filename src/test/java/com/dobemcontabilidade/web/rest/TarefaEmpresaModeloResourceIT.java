package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TarefaEmpresaModeloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.EmpresaModelo;
import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.domain.TarefaEmpresaModelo;
import com.dobemcontabilidade.repository.TarefaEmpresaModeloRepository;
import com.dobemcontabilidade.service.TarefaEmpresaModeloService;
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
 * Integration tests for the {@link TarefaEmpresaModeloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TarefaEmpresaModeloResourceIT {

    private static final Instant DEFAULT_DATA_ADMIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ADMIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_LEGAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_LEGAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tarefa-empresa-modelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TarefaEmpresaModeloRepository tarefaEmpresaModeloRepository;

    @Mock
    private TarefaEmpresaModeloRepository tarefaEmpresaModeloRepositoryMock;

    @Mock
    private TarefaEmpresaModeloService tarefaEmpresaModeloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarefaEmpresaModeloMockMvc;

    private TarefaEmpresaModelo tarefaEmpresaModelo;

    private TarefaEmpresaModelo insertedTarefaEmpresaModelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaEmpresaModelo createEntity(EntityManager em) {
        TarefaEmpresaModelo tarefaEmpresaModelo = new TarefaEmpresaModelo().dataAdmin(DEFAULT_DATA_ADMIN).dataLegal(DEFAULT_DATA_LEGAL);
        // Add required entity
        EmpresaModelo empresaModelo;
        if (TestUtil.findAll(em, EmpresaModelo.class).isEmpty()) {
            empresaModelo = EmpresaModeloResourceIT.createEntity(em);
            em.persist(empresaModelo);
            em.flush();
        } else {
            empresaModelo = TestUtil.findAll(em, EmpresaModelo.class).get(0);
        }
        tarefaEmpresaModelo.setEmpresaModelo(empresaModelo);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        tarefaEmpresaModelo.setServicoContabil(servicoContabil);
        return tarefaEmpresaModelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaEmpresaModelo createUpdatedEntity(EntityManager em) {
        TarefaEmpresaModelo tarefaEmpresaModelo = new TarefaEmpresaModelo().dataAdmin(UPDATED_DATA_ADMIN).dataLegal(UPDATED_DATA_LEGAL);
        // Add required entity
        EmpresaModelo empresaModelo;
        if (TestUtil.findAll(em, EmpresaModelo.class).isEmpty()) {
            empresaModelo = EmpresaModeloResourceIT.createUpdatedEntity(em);
            em.persist(empresaModelo);
            em.flush();
        } else {
            empresaModelo = TestUtil.findAll(em, EmpresaModelo.class).get(0);
        }
        tarefaEmpresaModelo.setEmpresaModelo(empresaModelo);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        tarefaEmpresaModelo.setServicoContabil(servicoContabil);
        return tarefaEmpresaModelo;
    }

    @BeforeEach
    public void initTest() {
        tarefaEmpresaModelo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTarefaEmpresaModelo != null) {
            tarefaEmpresaModeloRepository.delete(insertedTarefaEmpresaModelo);
            insertedTarefaEmpresaModelo = null;
        }
    }

    @Test
    @Transactional
    void createTarefaEmpresaModelo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TarefaEmpresaModelo
        var returnedTarefaEmpresaModelo = om.readValue(
            restTarefaEmpresaModeloMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaEmpresaModelo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TarefaEmpresaModelo.class
        );

        // Validate the TarefaEmpresaModelo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTarefaEmpresaModeloUpdatableFieldsEquals(
            returnedTarefaEmpresaModelo,
            getPersistedTarefaEmpresaModelo(returnedTarefaEmpresaModelo)
        );

        insertedTarefaEmpresaModelo = returnedTarefaEmpresaModelo;
    }

    @Test
    @Transactional
    void createTarefaEmpresaModeloWithExistingId() throws Exception {
        // Create the TarefaEmpresaModelo with an existing ID
        tarefaEmpresaModelo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaEmpresaModeloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaEmpresaModelo)))
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarefaEmpresaModelos() throws Exception {
        // Initialize the database
        insertedTarefaEmpresaModelo = tarefaEmpresaModeloRepository.saveAndFlush(tarefaEmpresaModelo);

        // Get all the tarefaEmpresaModeloList
        restTarefaEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefaEmpresaModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAdmin").value(hasItem(DEFAULT_DATA_ADMIN.toString())))
            .andExpect(jsonPath("$.[*].dataLegal").value(hasItem(DEFAULT_DATA_LEGAL.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefaEmpresaModelosWithEagerRelationshipsIsEnabled() throws Exception {
        when(tarefaEmpresaModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaEmpresaModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tarefaEmpresaModeloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefaEmpresaModelosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tarefaEmpresaModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaEmpresaModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tarefaEmpresaModeloRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTarefaEmpresaModelo() throws Exception {
        // Initialize the database
        insertedTarefaEmpresaModelo = tarefaEmpresaModeloRepository.saveAndFlush(tarefaEmpresaModelo);

        // Get the tarefaEmpresaModelo
        restTarefaEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL_ID, tarefaEmpresaModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarefaEmpresaModelo.getId().intValue()))
            .andExpect(jsonPath("$.dataAdmin").value(DEFAULT_DATA_ADMIN.toString()))
            .andExpect(jsonPath("$.dataLegal").value(DEFAULT_DATA_LEGAL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTarefaEmpresaModelo() throws Exception {
        // Get the tarefaEmpresaModelo
        restTarefaEmpresaModeloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarefaEmpresaModelo() throws Exception {
        // Initialize the database
        insertedTarefaEmpresaModelo = tarefaEmpresaModeloRepository.saveAndFlush(tarefaEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaEmpresaModelo
        TarefaEmpresaModelo updatedTarefaEmpresaModelo = tarefaEmpresaModeloRepository.findById(tarefaEmpresaModelo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTarefaEmpresaModelo are not directly saved in db
        em.detach(updatedTarefaEmpresaModelo);
        updatedTarefaEmpresaModelo.dataAdmin(UPDATED_DATA_ADMIN).dataLegal(UPDATED_DATA_LEGAL);

        restTarefaEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarefaEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTarefaEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the TarefaEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTarefaEmpresaModeloToMatchAllProperties(updatedTarefaEmpresaModelo);
    }

    @Test
    @Transactional
    void putNonExistingTarefaEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarefaEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarefaEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarefaEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaEmpresaModeloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaEmpresaModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarefaEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaEmpresaModelo = tarefaEmpresaModeloRepository.saveAndFlush(tarefaEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaEmpresaModelo using partial update
        TarefaEmpresaModelo partialUpdatedTarefaEmpresaModelo = new TarefaEmpresaModelo();
        partialUpdatedTarefaEmpresaModelo.setId(tarefaEmpresaModelo.getId());

        partialUpdatedTarefaEmpresaModelo.dataAdmin(UPDATED_DATA_ADMIN).dataLegal(UPDATED_DATA_LEGAL);

        restTarefaEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the TarefaEmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaEmpresaModeloUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTarefaEmpresaModelo, tarefaEmpresaModelo),
            getPersistedTarefaEmpresaModelo(tarefaEmpresaModelo)
        );
    }

    @Test
    @Transactional
    void fullUpdateTarefaEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaEmpresaModelo = tarefaEmpresaModeloRepository.saveAndFlush(tarefaEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaEmpresaModelo using partial update
        TarefaEmpresaModelo partialUpdatedTarefaEmpresaModelo = new TarefaEmpresaModelo();
        partialUpdatedTarefaEmpresaModelo.setId(tarefaEmpresaModelo.getId());

        partialUpdatedTarefaEmpresaModelo.dataAdmin(UPDATED_DATA_ADMIN).dataLegal(UPDATED_DATA_LEGAL);

        restTarefaEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the TarefaEmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaEmpresaModeloUpdatableFieldsEquals(
            partialUpdatedTarefaEmpresaModelo,
            getPersistedTarefaEmpresaModelo(partialUpdatedTarefaEmpresaModelo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTarefaEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarefaEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarefaEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarefaEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaEmpresaModeloMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarefaEmpresaModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarefaEmpresaModelo() throws Exception {
        // Initialize the database
        insertedTarefaEmpresaModelo = tarefaEmpresaModeloRepository.saveAndFlush(tarefaEmpresaModelo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarefaEmpresaModelo
        restTarefaEmpresaModeloMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarefaEmpresaModelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tarefaEmpresaModeloRepository.count();
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

    protected TarefaEmpresaModelo getPersistedTarefaEmpresaModelo(TarefaEmpresaModelo tarefaEmpresaModelo) {
        return tarefaEmpresaModeloRepository.findById(tarefaEmpresaModelo.getId()).orElseThrow();
    }

    protected void assertPersistedTarefaEmpresaModeloToMatchAllProperties(TarefaEmpresaModelo expectedTarefaEmpresaModelo) {
        assertTarefaEmpresaModeloAllPropertiesEquals(
            expectedTarefaEmpresaModelo,
            getPersistedTarefaEmpresaModelo(expectedTarefaEmpresaModelo)
        );
    }

    protected void assertPersistedTarefaEmpresaModeloToMatchUpdatableProperties(TarefaEmpresaModelo expectedTarefaEmpresaModelo) {
        assertTarefaEmpresaModeloAllUpdatablePropertiesEquals(
            expectedTarefaEmpresaModelo,
            getPersistedTarefaEmpresaModelo(expectedTarefaEmpresaModelo)
        );
    }
}
