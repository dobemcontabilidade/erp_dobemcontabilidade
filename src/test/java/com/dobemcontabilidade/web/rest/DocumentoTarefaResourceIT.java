package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DocumentoTarefaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DocumentoTarefa;
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.repository.DocumentoTarefaRepository;
import com.dobemcontabilidade.service.DocumentoTarefaService;
import com.dobemcontabilidade.service.dto.DocumentoTarefaDTO;
import com.dobemcontabilidade.service.mapper.DocumentoTarefaMapper;
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
 * Integration tests for the {@link DocumentoTarefaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DocumentoTarefaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/documento-tarefas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentoTarefaRepository documentoTarefaRepository;

    @Mock
    private DocumentoTarefaRepository documentoTarefaRepositoryMock;

    @Autowired
    private DocumentoTarefaMapper documentoTarefaMapper;

    @Mock
    private DocumentoTarefaService documentoTarefaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentoTarefaMockMvc;

    private DocumentoTarefa documentoTarefa;

    private DocumentoTarefa insertedDocumentoTarefa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentoTarefa createEntity(EntityManager em) {
        DocumentoTarefa documentoTarefa = new DocumentoTarefa().nome(DEFAULT_NOME);
        // Add required entity
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            tarefa = TarefaResourceIT.createEntity(em);
            em.persist(tarefa);
            em.flush();
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        documentoTarefa.setTarefa(tarefa);
        return documentoTarefa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentoTarefa createUpdatedEntity(EntityManager em) {
        DocumentoTarefa documentoTarefa = new DocumentoTarefa().nome(UPDATED_NOME);
        // Add required entity
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            tarefa = TarefaResourceIT.createUpdatedEntity(em);
            em.persist(tarefa);
            em.flush();
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        documentoTarefa.setTarefa(tarefa);
        return documentoTarefa;
    }

    @BeforeEach
    public void initTest() {
        documentoTarefa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDocumentoTarefa != null) {
            documentoTarefaRepository.delete(insertedDocumentoTarefa);
            insertedDocumentoTarefa = null;
        }
    }

    @Test
    @Transactional
    void createDocumentoTarefa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocumentoTarefa
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(documentoTarefa);
        var returnedDocumentoTarefaDTO = om.readValue(
            restDocumentoTarefaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentoTarefaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocumentoTarefaDTO.class
        );

        // Validate the DocumentoTarefa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDocumentoTarefa = documentoTarefaMapper.toEntity(returnedDocumentoTarefaDTO);
        assertDocumentoTarefaUpdatableFieldsEquals(returnedDocumentoTarefa, getPersistedDocumentoTarefa(returnedDocumentoTarefa));

        insertedDocumentoTarefa = returnedDocumentoTarefa;
    }

    @Test
    @Transactional
    void createDocumentoTarefaWithExistingId() throws Exception {
        // Create the DocumentoTarefa with an existing ID
        documentoTarefa.setId(1L);
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(documentoTarefa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoTarefaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentoTarefaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentoTarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentoTarefas() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        // Get all the documentoTarefaList
        restDocumentoTarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoTarefa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocumentoTarefasWithEagerRelationshipsIsEnabled() throws Exception {
        when(documentoTarefaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocumentoTarefaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(documentoTarefaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDocumentoTarefasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(documentoTarefaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDocumentoTarefaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(documentoTarefaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDocumentoTarefa() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        // Get the documentoTarefa
        restDocumentoTarefaMockMvc
            .perform(get(ENTITY_API_URL_ID, documentoTarefa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentoTarefa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getDocumentoTarefasByIdFiltering() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        Long id = documentoTarefa.getId();

        defaultDocumentoTarefaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDocumentoTarefaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDocumentoTarefaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentoTarefasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        // Get all the documentoTarefaList where nome equals to
        defaultDocumentoTarefaFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDocumentoTarefasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        // Get all the documentoTarefaList where nome in
        defaultDocumentoTarefaFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDocumentoTarefasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        // Get all the documentoTarefaList where nome is not null
        defaultDocumentoTarefaFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentoTarefasByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        // Get all the documentoTarefaList where nome contains
        defaultDocumentoTarefaFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllDocumentoTarefasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        // Get all the documentoTarefaList where nome does not contain
        defaultDocumentoTarefaFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllDocumentoTarefasByTarefaIsEqualToSomething() throws Exception {
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            documentoTarefaRepository.saveAndFlush(documentoTarefa);
            tarefa = TarefaResourceIT.createEntity(em);
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        em.persist(tarefa);
        em.flush();
        documentoTarefa.setTarefa(tarefa);
        documentoTarefaRepository.saveAndFlush(documentoTarefa);
        Long tarefaId = tarefa.getId();
        // Get all the documentoTarefaList where tarefa equals to tarefaId
        defaultDocumentoTarefaShouldBeFound("tarefaId.equals=" + tarefaId);

        // Get all the documentoTarefaList where tarefa equals to (tarefaId + 1)
        defaultDocumentoTarefaShouldNotBeFound("tarefaId.equals=" + (tarefaId + 1));
    }

    private void defaultDocumentoTarefaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDocumentoTarefaShouldBeFound(shouldBeFound);
        defaultDocumentoTarefaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentoTarefaShouldBeFound(String filter) throws Exception {
        restDocumentoTarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentoTarefa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));

        // Check, that the count call also returns 1
        restDocumentoTarefaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentoTarefaShouldNotBeFound(String filter) throws Exception {
        restDocumentoTarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentoTarefaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocumentoTarefa() throws Exception {
        // Get the documentoTarefa
        restDocumentoTarefaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentoTarefa() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentoTarefa
        DocumentoTarefa updatedDocumentoTarefa = documentoTarefaRepository.findById(documentoTarefa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentoTarefa are not directly saved in db
        em.detach(updatedDocumentoTarefa);
        updatedDocumentoTarefa.nome(UPDATED_NOME);
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(updatedDocumentoTarefa);

        restDocumentoTarefaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentoTarefaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentoTarefaDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentoTarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentoTarefaToMatchAllProperties(updatedDocumentoTarefa);
    }

    @Test
    @Transactional
    void putNonExistingDocumentoTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentoTarefa.setId(longCount.incrementAndGet());

        // Create the DocumentoTarefa
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(documentoTarefa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoTarefaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentoTarefaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentoTarefaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoTarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentoTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentoTarefa.setId(longCount.incrementAndGet());

        // Create the DocumentoTarefa
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(documentoTarefa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoTarefaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentoTarefaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoTarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentoTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentoTarefa.setId(longCount.incrementAndGet());

        // Create the DocumentoTarefa
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(documentoTarefa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoTarefaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentoTarefaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentoTarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentoTarefaWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentoTarefa using partial update
        DocumentoTarefa partialUpdatedDocumentoTarefa = new DocumentoTarefa();
        partialUpdatedDocumentoTarefa.setId(documentoTarefa.getId());

        restDocumentoTarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentoTarefa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentoTarefa))
            )
            .andExpect(status().isOk());

        // Validate the DocumentoTarefa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentoTarefaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentoTarefa, documentoTarefa),
            getPersistedDocumentoTarefa(documentoTarefa)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentoTarefaWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentoTarefa using partial update
        DocumentoTarefa partialUpdatedDocumentoTarefa = new DocumentoTarefa();
        partialUpdatedDocumentoTarefa.setId(documentoTarefa.getId());

        partialUpdatedDocumentoTarefa.nome(UPDATED_NOME);

        restDocumentoTarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentoTarefa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentoTarefa))
            )
            .andExpect(status().isOk());

        // Validate the DocumentoTarefa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentoTarefaUpdatableFieldsEquals(
            partialUpdatedDocumentoTarefa,
            getPersistedDocumentoTarefa(partialUpdatedDocumentoTarefa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDocumentoTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentoTarefa.setId(longCount.incrementAndGet());

        // Create the DocumentoTarefa
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(documentoTarefa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoTarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentoTarefaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentoTarefaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoTarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentoTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentoTarefa.setId(longCount.incrementAndGet());

        // Create the DocumentoTarefa
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(documentoTarefa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoTarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentoTarefaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentoTarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentoTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentoTarefa.setId(longCount.incrementAndGet());

        // Create the DocumentoTarefa
        DocumentoTarefaDTO documentoTarefaDTO = documentoTarefaMapper.toDto(documentoTarefa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoTarefaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentoTarefaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentoTarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentoTarefa() throws Exception {
        // Initialize the database
        insertedDocumentoTarefa = documentoTarefaRepository.saveAndFlush(documentoTarefa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentoTarefa
        restDocumentoTarefaMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentoTarefa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentoTarefaRepository.count();
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

    protected DocumentoTarefa getPersistedDocumentoTarefa(DocumentoTarefa documentoTarefa) {
        return documentoTarefaRepository.findById(documentoTarefa.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentoTarefaToMatchAllProperties(DocumentoTarefa expectedDocumentoTarefa) {
        assertDocumentoTarefaAllPropertiesEquals(expectedDocumentoTarefa, getPersistedDocumentoTarefa(expectedDocumentoTarefa));
    }

    protected void assertPersistedDocumentoTarefaToMatchUpdatableProperties(DocumentoTarefa expectedDocumentoTarefa) {
        assertDocumentoTarefaAllUpdatablePropertiesEquals(expectedDocumentoTarefa, getPersistedDocumentoTarefa(expectedDocumentoTarefa));
    }
}
