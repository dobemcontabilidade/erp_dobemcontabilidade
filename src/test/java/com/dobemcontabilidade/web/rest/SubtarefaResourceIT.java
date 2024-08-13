package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SubtarefaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Subtarefa;
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.repository.SubtarefaRepository;
import com.dobemcontabilidade.service.SubtarefaService;
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
 * Integration tests for the {@link SubtarefaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SubtarefaResourceIT {

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;
    private static final Integer SMALLER_ORDEM = 1 - 1;

    private static final String DEFAULT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subtarefas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubtarefaRepository subtarefaRepository;

    @Mock
    private SubtarefaRepository subtarefaRepositoryMock;

    @Mock
    private SubtarefaService subtarefaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubtarefaMockMvc;

    private Subtarefa subtarefa;

    private Subtarefa insertedSubtarefa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subtarefa createEntity(EntityManager em) {
        Subtarefa subtarefa = new Subtarefa().ordem(DEFAULT_ORDEM).item(DEFAULT_ITEM).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            tarefa = TarefaResourceIT.createEntity(em);
            em.persist(tarefa);
            em.flush();
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        subtarefa.setTarefa(tarefa);
        return subtarefa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subtarefa createUpdatedEntity(EntityManager em) {
        Subtarefa subtarefa = new Subtarefa().ordem(UPDATED_ORDEM).item(UPDATED_ITEM).descricao(UPDATED_DESCRICAO);
        // Add required entity
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            tarefa = TarefaResourceIT.createUpdatedEntity(em);
            em.persist(tarefa);
            em.flush();
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        subtarefa.setTarefa(tarefa);
        return subtarefa;
    }

    @BeforeEach
    public void initTest() {
        subtarefa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSubtarefa != null) {
            subtarefaRepository.delete(insertedSubtarefa);
            insertedSubtarefa = null;
        }
    }

    @Test
    @Transactional
    void createSubtarefa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Subtarefa
        var returnedSubtarefa = om.readValue(
            restSubtarefaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subtarefa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Subtarefa.class
        );

        // Validate the Subtarefa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubtarefaUpdatableFieldsEquals(returnedSubtarefa, getPersistedSubtarefa(returnedSubtarefa));

        insertedSubtarefa = returnedSubtarefa;
    }

    @Test
    @Transactional
    void createSubtarefaWithExistingId() throws Exception {
        // Create the Subtarefa with an existing ID
        subtarefa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubtarefaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subtarefa)))
            .andExpect(status().isBadRequest());

        // Validate the Subtarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubtarefas() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList
        restSubtarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subtarefa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubtarefasWithEagerRelationshipsIsEnabled() throws Exception {
        when(subtarefaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubtarefaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(subtarefaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubtarefasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(subtarefaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubtarefaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(subtarefaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSubtarefa() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get the subtarefa
        restSubtarefaMockMvc
            .perform(get(ENTITY_API_URL_ID, subtarefa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subtarefa.getId().intValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getSubtarefasByIdFiltering() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        Long id = subtarefa.getId();

        defaultSubtarefaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSubtarefaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSubtarefaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSubtarefasByOrdemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where ordem equals to
        defaultSubtarefaFiltering("ordem.equals=" + DEFAULT_ORDEM, "ordem.equals=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByOrdemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where ordem in
        defaultSubtarefaFiltering("ordem.in=" + DEFAULT_ORDEM + "," + UPDATED_ORDEM, "ordem.in=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByOrdemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where ordem is not null
        defaultSubtarefaFiltering("ordem.specified=true", "ordem.specified=false");
    }

    @Test
    @Transactional
    void getAllSubtarefasByOrdemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where ordem is greater than or equal to
        defaultSubtarefaFiltering("ordem.greaterThanOrEqual=" + DEFAULT_ORDEM, "ordem.greaterThanOrEqual=" + UPDATED_ORDEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByOrdemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where ordem is less than or equal to
        defaultSubtarefaFiltering("ordem.lessThanOrEqual=" + DEFAULT_ORDEM, "ordem.lessThanOrEqual=" + SMALLER_ORDEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByOrdemIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where ordem is less than
        defaultSubtarefaFiltering("ordem.lessThan=" + UPDATED_ORDEM, "ordem.lessThan=" + DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByOrdemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where ordem is greater than
        defaultSubtarefaFiltering("ordem.greaterThan=" + SMALLER_ORDEM, "ordem.greaterThan=" + DEFAULT_ORDEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByItemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where item equals to
        defaultSubtarefaFiltering("item.equals=" + DEFAULT_ITEM, "item.equals=" + UPDATED_ITEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByItemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where item in
        defaultSubtarefaFiltering("item.in=" + DEFAULT_ITEM + "," + UPDATED_ITEM, "item.in=" + UPDATED_ITEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByItemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where item is not null
        defaultSubtarefaFiltering("item.specified=true", "item.specified=false");
    }

    @Test
    @Transactional
    void getAllSubtarefasByItemContainsSomething() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where item contains
        defaultSubtarefaFiltering("item.contains=" + DEFAULT_ITEM, "item.contains=" + UPDATED_ITEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByItemNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        // Get all the subtarefaList where item does not contain
        defaultSubtarefaFiltering("item.doesNotContain=" + UPDATED_ITEM, "item.doesNotContain=" + DEFAULT_ITEM);
    }

    @Test
    @Transactional
    void getAllSubtarefasByTarefaIsEqualToSomething() throws Exception {
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            subtarefaRepository.saveAndFlush(subtarefa);
            tarefa = TarefaResourceIT.createEntity(em);
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        em.persist(tarefa);
        em.flush();
        subtarefa.setTarefa(tarefa);
        subtarefaRepository.saveAndFlush(subtarefa);
        Long tarefaId = tarefa.getId();
        // Get all the subtarefaList where tarefa equals to tarefaId
        defaultSubtarefaShouldBeFound("tarefaId.equals=" + tarefaId);

        // Get all the subtarefaList where tarefa equals to (tarefaId + 1)
        defaultSubtarefaShouldNotBeFound("tarefaId.equals=" + (tarefaId + 1));
    }

    private void defaultSubtarefaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSubtarefaShouldBeFound(shouldBeFound);
        defaultSubtarefaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubtarefaShouldBeFound(String filter) throws Exception {
        restSubtarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subtarefa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restSubtarefaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubtarefaShouldNotBeFound(String filter) throws Exception {
        restSubtarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubtarefaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSubtarefa() throws Exception {
        // Get the subtarefa
        restSubtarefaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubtarefa() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subtarefa
        Subtarefa updatedSubtarefa = subtarefaRepository.findById(subtarefa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubtarefa are not directly saved in db
        em.detach(updatedSubtarefa);
        updatedSubtarefa.ordem(UPDATED_ORDEM).item(UPDATED_ITEM).descricao(UPDATED_DESCRICAO);

        restSubtarefaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubtarefa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubtarefa))
            )
            .andExpect(status().isOk());

        // Validate the Subtarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubtarefaToMatchAllProperties(updatedSubtarefa);
    }

    @Test
    @Transactional
    void putNonExistingSubtarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subtarefa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubtarefaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subtarefa.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subtarefa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subtarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubtarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subtarefa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubtarefaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subtarefa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subtarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubtarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subtarefa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubtarefaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subtarefa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subtarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubtarefaWithPatch() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subtarefa using partial update
        Subtarefa partialUpdatedSubtarefa = new Subtarefa();
        partialUpdatedSubtarefa.setId(subtarefa.getId());

        partialUpdatedSubtarefa.item(UPDATED_ITEM).descricao(UPDATED_DESCRICAO);

        restSubtarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubtarefa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubtarefa))
            )
            .andExpect(status().isOk());

        // Validate the Subtarefa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubtarefaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubtarefa, subtarefa),
            getPersistedSubtarefa(subtarefa)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubtarefaWithPatch() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subtarefa using partial update
        Subtarefa partialUpdatedSubtarefa = new Subtarefa();
        partialUpdatedSubtarefa.setId(subtarefa.getId());

        partialUpdatedSubtarefa.ordem(UPDATED_ORDEM).item(UPDATED_ITEM).descricao(UPDATED_DESCRICAO);

        restSubtarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubtarefa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubtarefa))
            )
            .andExpect(status().isOk());

        // Validate the Subtarefa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubtarefaUpdatableFieldsEquals(partialUpdatedSubtarefa, getPersistedSubtarefa(partialUpdatedSubtarefa));
    }

    @Test
    @Transactional
    void patchNonExistingSubtarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subtarefa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubtarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subtarefa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subtarefa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subtarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubtarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subtarefa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubtarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subtarefa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subtarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubtarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subtarefa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubtarefaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subtarefa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subtarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubtarefa() throws Exception {
        // Initialize the database
        insertedSubtarefa = subtarefaRepository.saveAndFlush(subtarefa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subtarefa
        restSubtarefaMockMvc
            .perform(delete(ENTITY_API_URL_ID, subtarefa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subtarefaRepository.count();
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

    protected Subtarefa getPersistedSubtarefa(Subtarefa subtarefa) {
        return subtarefaRepository.findById(subtarefa.getId()).orElseThrow();
    }

    protected void assertPersistedSubtarefaToMatchAllProperties(Subtarefa expectedSubtarefa) {
        assertSubtarefaAllPropertiesEquals(expectedSubtarefa, getPersistedSubtarefa(expectedSubtarefa));
    }

    protected void assertPersistedSubtarefaToMatchUpdatableProperties(Subtarefa expectedSubtarefa) {
        assertSubtarefaAllUpdatablePropertiesEquals(expectedSubtarefa, getPersistedSubtarefa(expectedSubtarefa));
    }
}
