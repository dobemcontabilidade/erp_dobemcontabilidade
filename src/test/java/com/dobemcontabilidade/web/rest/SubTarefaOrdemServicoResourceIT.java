package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SubTarefaOrdemServicoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.SubTarefaOrdemServico;
import com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao;
import com.dobemcontabilidade.repository.SubTarefaOrdemServicoRepository;
import com.dobemcontabilidade.service.SubTarefaOrdemServicoService;
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
 * Integration tests for the {@link SubTarefaOrdemServicoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SubTarefaOrdemServicoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final Boolean DEFAULT_CONCLUIDA = false;
    private static final Boolean UPDATED_CONCLUIDA = true;

    private static final String ENTITY_API_URL = "/api/sub-tarefa-ordem-servicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubTarefaOrdemServicoRepository subTarefaOrdemServicoRepository;

    @Mock
    private SubTarefaOrdemServicoRepository subTarefaOrdemServicoRepositoryMock;

    @Mock
    private SubTarefaOrdemServicoService subTarefaOrdemServicoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubTarefaOrdemServicoMockMvc;

    private SubTarefaOrdemServico subTarefaOrdemServico;

    private SubTarefaOrdemServico insertedSubTarefaOrdemServico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubTarefaOrdemServico createEntity(EntityManager em) {
        SubTarefaOrdemServico subTarefaOrdemServico = new SubTarefaOrdemServico()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .ordem(DEFAULT_ORDEM)
            .concluida(DEFAULT_CONCLUIDA);
        // Add required entity
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).isEmpty()) {
            tarefaOrdemServicoExecucao = TarefaOrdemServicoExecucaoResourceIT.createEntity(em);
            em.persist(tarefaOrdemServicoExecucao);
            em.flush();
        } else {
            tarefaOrdemServicoExecucao = TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).get(0);
        }
        subTarefaOrdemServico.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        return subTarefaOrdemServico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubTarefaOrdemServico createUpdatedEntity(EntityManager em) {
        SubTarefaOrdemServico subTarefaOrdemServico = new SubTarefaOrdemServico()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ordem(UPDATED_ORDEM)
            .concluida(UPDATED_CONCLUIDA);
        // Add required entity
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).isEmpty()) {
            tarefaOrdemServicoExecucao = TarefaOrdemServicoExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaOrdemServicoExecucao);
            em.flush();
        } else {
            tarefaOrdemServicoExecucao = TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).get(0);
        }
        subTarefaOrdemServico.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        return subTarefaOrdemServico;
    }

    @BeforeEach
    public void initTest() {
        subTarefaOrdemServico = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSubTarefaOrdemServico != null) {
            subTarefaOrdemServicoRepository.delete(insertedSubTarefaOrdemServico);
            insertedSubTarefaOrdemServico = null;
        }
    }

    @Test
    @Transactional
    void createSubTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SubTarefaOrdemServico
        var returnedSubTarefaOrdemServico = om.readValue(
            restSubTarefaOrdemServicoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subTarefaOrdemServico)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SubTarefaOrdemServico.class
        );

        // Validate the SubTarefaOrdemServico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubTarefaOrdemServicoUpdatableFieldsEquals(
            returnedSubTarefaOrdemServico,
            getPersistedSubTarefaOrdemServico(returnedSubTarefaOrdemServico)
        );

        insertedSubTarefaOrdemServico = returnedSubTarefaOrdemServico;
    }

    @Test
    @Transactional
    void createSubTarefaOrdemServicoWithExistingId() throws Exception {
        // Create the SubTarefaOrdemServico with an existing ID
        subTarefaOrdemServico.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubTarefaOrdemServicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subTarefaOrdemServico)))
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubTarefaOrdemServicos() throws Exception {
        // Initialize the database
        insertedSubTarefaOrdemServico = subTarefaOrdemServicoRepository.saveAndFlush(subTarefaOrdemServico);

        // Get all the subTarefaOrdemServicoList
        restSubTarefaOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subTarefaOrdemServico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].concluida").value(hasItem(DEFAULT_CONCLUIDA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubTarefaOrdemServicosWithEagerRelationshipsIsEnabled() throws Exception {
        when(subTarefaOrdemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubTarefaOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(subTarefaOrdemServicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubTarefaOrdemServicosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(subTarefaOrdemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubTarefaOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(subTarefaOrdemServicoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSubTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedSubTarefaOrdemServico = subTarefaOrdemServicoRepository.saveAndFlush(subTarefaOrdemServico);

        // Get the subTarefaOrdemServico
        restSubTarefaOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL_ID, subTarefaOrdemServico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subTarefaOrdemServico.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.concluida").value(DEFAULT_CONCLUIDA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSubTarefaOrdemServico() throws Exception {
        // Get the subTarefaOrdemServico
        restSubTarefaOrdemServicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedSubTarefaOrdemServico = subTarefaOrdemServicoRepository.saveAndFlush(subTarefaOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subTarefaOrdemServico
        SubTarefaOrdemServico updatedSubTarefaOrdemServico = subTarefaOrdemServicoRepository
            .findById(subTarefaOrdemServico.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedSubTarefaOrdemServico are not directly saved in db
        em.detach(updatedSubTarefaOrdemServico);
        updatedSubTarefaOrdemServico.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ordem(UPDATED_ORDEM).concluida(UPDATED_CONCLUIDA);

        restSubTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubTarefaOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the SubTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubTarefaOrdemServicoToMatchAllProperties(updatedSubTarefaOrdemServico);
    }

    @Test
    @Transactional
    void putNonExistingSubTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subTarefaOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTarefaOrdemServicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subTarefaOrdemServico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubTarefaOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedSubTarefaOrdemServico = subTarefaOrdemServicoRepository.saveAndFlush(subTarefaOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subTarefaOrdemServico using partial update
        SubTarefaOrdemServico partialUpdatedSubTarefaOrdemServico = new SubTarefaOrdemServico();
        partialUpdatedSubTarefaOrdemServico.setId(subTarefaOrdemServico.getId());

        partialUpdatedSubTarefaOrdemServico.descricao(UPDATED_DESCRICAO).ordem(UPDATED_ORDEM);

        restSubTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubTarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the SubTarefaOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubTarefaOrdemServicoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubTarefaOrdemServico, subTarefaOrdemServico),
            getPersistedSubTarefaOrdemServico(subTarefaOrdemServico)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubTarefaOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedSubTarefaOrdemServico = subTarefaOrdemServicoRepository.saveAndFlush(subTarefaOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subTarefaOrdemServico using partial update
        SubTarefaOrdemServico partialUpdatedSubTarefaOrdemServico = new SubTarefaOrdemServico();
        partialUpdatedSubTarefaOrdemServico.setId(subTarefaOrdemServico.getId());

        partialUpdatedSubTarefaOrdemServico
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ordem(UPDATED_ORDEM)
            .concluida(UPDATED_CONCLUIDA);

        restSubTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubTarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the SubTarefaOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubTarefaOrdemServicoUpdatableFieldsEquals(
            partialUpdatedSubTarefaOrdemServico,
            getPersistedSubTarefaOrdemServico(partialUpdatedSubTarefaOrdemServico)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSubTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subTarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subTarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTarefaOrdemServicoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subTarefaOrdemServico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubTarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedSubTarefaOrdemServico = subTarefaOrdemServicoRepository.saveAndFlush(subTarefaOrdemServico);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subTarefaOrdemServico
        restSubTarefaOrdemServicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, subTarefaOrdemServico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subTarefaOrdemServicoRepository.count();
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

    protected SubTarefaOrdemServico getPersistedSubTarefaOrdemServico(SubTarefaOrdemServico subTarefaOrdemServico) {
        return subTarefaOrdemServicoRepository.findById(subTarefaOrdemServico.getId()).orElseThrow();
    }

    protected void assertPersistedSubTarefaOrdemServicoToMatchAllProperties(SubTarefaOrdemServico expectedSubTarefaOrdemServico) {
        assertSubTarefaOrdemServicoAllPropertiesEquals(
            expectedSubTarefaOrdemServico,
            getPersistedSubTarefaOrdemServico(expectedSubTarefaOrdemServico)
        );
    }

    protected void assertPersistedSubTarefaOrdemServicoToMatchUpdatableProperties(SubTarefaOrdemServico expectedSubTarefaOrdemServico) {
        assertSubTarefaOrdemServicoAllUpdatablePropertiesEquals(
            expectedSubTarefaOrdemServico,
            getPersistedSubTarefaOrdemServico(expectedSubTarefaOrdemServico)
        );
    }
}
