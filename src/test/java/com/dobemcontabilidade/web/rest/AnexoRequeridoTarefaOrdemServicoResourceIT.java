package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServicoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServico;
import com.dobemcontabilidade.domain.TarefaOrdemServico;
import com.dobemcontabilidade.repository.AnexoRequeridoTarefaOrdemServicoRepository;
import com.dobemcontabilidade.service.AnexoRequeridoTarefaOrdemServicoService;
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
 * Integration tests for the {@link AnexoRequeridoTarefaOrdemServicoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoRequeridoTarefaOrdemServicoResourceIT {

    private static final Boolean DEFAULT_OBRIGATORIO = false;
    private static final Boolean UPDATED_OBRIGATORIO = true;

    private static final String ENTITY_API_URL = "/api/anexo-requerido-tarefa-ordem-servicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoRequeridoTarefaOrdemServicoRepository anexoRequeridoTarefaOrdemServicoRepository;

    @Mock
    private AnexoRequeridoTarefaOrdemServicoRepository anexoRequeridoTarefaOrdemServicoRepositoryMock;

    @Mock
    private AnexoRequeridoTarefaOrdemServicoService anexoRequeridoTarefaOrdemServicoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoRequeridoTarefaOrdemServicoMockMvc;

    private AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico;

    private AnexoRequeridoTarefaOrdemServico insertedAnexoRequeridoTarefaOrdemServico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoTarefaOrdemServico createEntity(EntityManager em) {
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico = new AnexoRequeridoTarefaOrdemServico()
            .obrigatorio(DEFAULT_OBRIGATORIO);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoTarefaOrdemServico.setAnexoRequerido(anexoRequerido);
        // Add required entity
        TarefaOrdemServico tarefaOrdemServico;
        if (TestUtil.findAll(em, TarefaOrdemServico.class).isEmpty()) {
            tarefaOrdemServico = TarefaOrdemServicoResourceIT.createEntity(em);
            em.persist(tarefaOrdemServico);
            em.flush();
        } else {
            tarefaOrdemServico = TestUtil.findAll(em, TarefaOrdemServico.class).get(0);
        }
        anexoRequeridoTarefaOrdemServico.setTarefaOrdemServico(tarefaOrdemServico);
        return anexoRequeridoTarefaOrdemServico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoTarefaOrdemServico createUpdatedEntity(EntityManager em) {
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico = new AnexoRequeridoTarefaOrdemServico()
            .obrigatorio(UPDATED_OBRIGATORIO);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createUpdatedEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoTarefaOrdemServico.setAnexoRequerido(anexoRequerido);
        // Add required entity
        TarefaOrdemServico tarefaOrdemServico;
        if (TestUtil.findAll(em, TarefaOrdemServico.class).isEmpty()) {
            tarefaOrdemServico = TarefaOrdemServicoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaOrdemServico);
            em.flush();
        } else {
            tarefaOrdemServico = TestUtil.findAll(em, TarefaOrdemServico.class).get(0);
        }
        anexoRequeridoTarefaOrdemServico.setTarefaOrdemServico(tarefaOrdemServico);
        return anexoRequeridoTarefaOrdemServico;
    }

    @BeforeEach
    public void initTest() {
        anexoRequeridoTarefaOrdemServico = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoRequeridoTarefaOrdemServico != null) {
            anexoRequeridoTarefaOrdemServicoRepository.delete(insertedAnexoRequeridoTarefaOrdemServico);
            insertedAnexoRequeridoTarefaOrdemServico = null;
        }
    }

    @Test
    @Transactional
    void createAnexoRequeridoTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoRequeridoTarefaOrdemServico
        var returnedAnexoRequeridoTarefaOrdemServico = om.readValue(
            restAnexoRequeridoTarefaOrdemServicoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(anexoRequeridoTarefaOrdemServico))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoRequeridoTarefaOrdemServico.class
        );

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoRequeridoTarefaOrdemServicoUpdatableFieldsEquals(
            returnedAnexoRequeridoTarefaOrdemServico,
            getPersistedAnexoRequeridoTarefaOrdemServico(returnedAnexoRequeridoTarefaOrdemServico)
        );

        insertedAnexoRequeridoTarefaOrdemServico = returnedAnexoRequeridoTarefaOrdemServico;
    }

    @Test
    @Transactional
    void createAnexoRequeridoTarefaOrdemServicoWithExistingId() throws Exception {
        // Create the AnexoRequeridoTarefaOrdemServico with an existing ID
        anexoRequeridoTarefaOrdemServico.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridoTarefaOrdemServicos() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoRepository.saveAndFlush(
            anexoRequeridoTarefaOrdemServico
        );

        // Get all the anexoRequeridoTarefaOrdemServicoList
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoRequeridoTarefaOrdemServico.getId().intValue())))
            .andExpect(jsonPath("$.[*].obrigatorio").value(hasItem(DEFAULT_OBRIGATORIO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoTarefaOrdemServicosWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoRequeridoTarefaOrdemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoTarefaOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoRequeridoTarefaOrdemServicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoTarefaOrdemServicosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoRequeridoTarefaOrdemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoTarefaOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoRequeridoTarefaOrdemServicoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoRequeridoTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoRepository.saveAndFlush(
            anexoRequeridoTarefaOrdemServico
        );

        // Get the anexoRequeridoTarefaOrdemServico
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoRequeridoTarefaOrdemServico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoRequeridoTarefaOrdemServico.getId().intValue()))
            .andExpect(jsonPath("$.obrigatorio").value(DEFAULT_OBRIGATORIO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoRequeridoTarefaOrdemServico() throws Exception {
        // Get the anexoRequeridoTarefaOrdemServico
        restAnexoRequeridoTarefaOrdemServicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoRequeridoTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoRepository.saveAndFlush(
            anexoRequeridoTarefaOrdemServico
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoTarefaOrdemServico
        AnexoRequeridoTarefaOrdemServico updatedAnexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoRepository
            .findById(anexoRequeridoTarefaOrdemServico.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoRequeridoTarefaOrdemServico are not directly saved in db
        em.detach(updatedAnexoRequeridoTarefaOrdemServico);
        updatedAnexoRequeridoTarefaOrdemServico.obrigatorio(UPDATED_OBRIGATORIO);

        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoRequeridoTarefaOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoRequeridoTarefaOrdemServicoToMatchAllProperties(updatedAnexoRequeridoTarefaOrdemServico);
    }

    @Test
    @Transactional
    void putNonExistingAnexoRequeridoTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoRequeridoTarefaOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoRequeridoTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoRequeridoTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoRequeridoTarefaOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoRepository.saveAndFlush(
            anexoRequeridoTarefaOrdemServico
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoTarefaOrdemServico using partial update
        AnexoRequeridoTarefaOrdemServico partialUpdatedAnexoRequeridoTarefaOrdemServico = new AnexoRequeridoTarefaOrdemServico();
        partialUpdatedAnexoRequeridoTarefaOrdemServico.setId(anexoRequeridoTarefaOrdemServico.getId());

        partialUpdatedAnexoRequeridoTarefaOrdemServico.obrigatorio(UPDATED_OBRIGATORIO);

        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoTarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoTarefaOrdemServicoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoRequeridoTarefaOrdemServico, anexoRequeridoTarefaOrdemServico),
            getPersistedAnexoRequeridoTarefaOrdemServico(anexoRequeridoTarefaOrdemServico)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoRequeridoTarefaOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoRepository.saveAndFlush(
            anexoRequeridoTarefaOrdemServico
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoTarefaOrdemServico using partial update
        AnexoRequeridoTarefaOrdemServico partialUpdatedAnexoRequeridoTarefaOrdemServico = new AnexoRequeridoTarefaOrdemServico();
        partialUpdatedAnexoRequeridoTarefaOrdemServico.setId(anexoRequeridoTarefaOrdemServico.getId());

        partialUpdatedAnexoRequeridoTarefaOrdemServico.obrigatorio(UPDATED_OBRIGATORIO);

        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoTarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoTarefaOrdemServicoUpdatableFieldsEquals(
            partialUpdatedAnexoRequeridoTarefaOrdemServico,
            getPersistedAnexoRequeridoTarefaOrdemServico(partialUpdatedAnexoRequeridoTarefaOrdemServico)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnexoRequeridoTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoRequeridoTarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoRequeridoTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoRequeridoTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaOrdemServico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoRequeridoTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaOrdemServico = anexoRequeridoTarefaOrdemServicoRepository.saveAndFlush(
            anexoRequeridoTarefaOrdemServico
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoRequeridoTarefaOrdemServico
        restAnexoRequeridoTarefaOrdemServicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoRequeridoTarefaOrdemServico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoRequeridoTarefaOrdemServicoRepository.count();
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

    protected AnexoRequeridoTarefaOrdemServico getPersistedAnexoRequeridoTarefaOrdemServico(
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico
    ) {
        return anexoRequeridoTarefaOrdemServicoRepository.findById(anexoRequeridoTarefaOrdemServico.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoRequeridoTarefaOrdemServicoToMatchAllProperties(
        AnexoRequeridoTarefaOrdemServico expectedAnexoRequeridoTarefaOrdemServico
    ) {
        assertAnexoRequeridoTarefaOrdemServicoAllPropertiesEquals(
            expectedAnexoRequeridoTarefaOrdemServico,
            getPersistedAnexoRequeridoTarefaOrdemServico(expectedAnexoRequeridoTarefaOrdemServico)
        );
    }

    protected void assertPersistedAnexoRequeridoTarefaOrdemServicoToMatchUpdatableProperties(
        AnexoRequeridoTarefaOrdemServico expectedAnexoRequeridoTarefaOrdemServico
    ) {
        assertAnexoRequeridoTarefaOrdemServicoAllUpdatablePropertiesEquals(
            expectedAnexoRequeridoTarefaOrdemServico,
            getPersistedAnexoRequeridoTarefaOrdemServico(expectedAnexoRequeridoTarefaOrdemServico)
        );
    }
}
