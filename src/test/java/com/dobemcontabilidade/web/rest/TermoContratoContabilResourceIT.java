package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TermoContratoContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.repository.TermoContratoContabilRepository;
import com.dobemcontabilidade.service.TermoContratoContabilService;
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
 * Integration tests for the {@link TermoContratoContabilResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TermoContratoContabilResourceIT {

    private static final String DEFAULT_LINK_TERMO = "AAAAAAAAAA";
    private static final String UPDATED_LINK_TERMO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/termo-contrato-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TermoContratoContabilRepository termoContratoContabilRepository;

    @Mock
    private TermoContratoContabilRepository termoContratoContabilRepositoryMock;

    @Mock
    private TermoContratoContabilService termoContratoContabilServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermoContratoContabilMockMvc;

    private TermoContratoContabil termoContratoContabil;

    private TermoContratoContabil insertedTermoContratoContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoContratoContabil createEntity(EntityManager em) {
        TermoContratoContabil termoContratoContabil = new TermoContratoContabil()
            .linkTermo(DEFAULT_LINK_TERMO)
            .descricao(DEFAULT_DESCRICAO)
            .titulo(DEFAULT_TITULO);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        termoContratoContabil.setPlanoContabil(planoContabil);
        return termoContratoContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoContratoContabil createUpdatedEntity(EntityManager em) {
        TermoContratoContabil termoContratoContabil = new TermoContratoContabil()
            .linkTermo(UPDATED_LINK_TERMO)
            .descricao(UPDATED_DESCRICAO)
            .titulo(UPDATED_TITULO);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        termoContratoContabil.setPlanoContabil(planoContabil);
        return termoContratoContabil;
    }

    @BeforeEach
    public void initTest() {
        termoContratoContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTermoContratoContabil != null) {
            termoContratoContabilRepository.delete(insertedTermoContratoContabil);
            insertedTermoContratoContabil = null;
        }
    }

    @Test
    @Transactional
    void createTermoContratoContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TermoContratoContabil
        var returnedTermoContratoContabil = om.readValue(
            restTermoContratoContabilMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoContratoContabil)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TermoContratoContabil.class
        );

        // Validate the TermoContratoContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTermoContratoContabilUpdatableFieldsEquals(
            returnedTermoContratoContabil,
            getPersistedTermoContratoContabil(returnedTermoContratoContabil)
        );

        insertedTermoContratoContabil = returnedTermoContratoContabil;
    }

    @Test
    @Transactional
    void createTermoContratoContabilWithExistingId() throws Exception {
        // Create the TermoContratoContabil with an existing ID
        termoContratoContabil.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermoContratoContabilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoContratoContabil)))
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabils() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList
        restTermoContratoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoContratoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].linkTermo").value(hasItem(DEFAULT_LINK_TERMO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoContratoContabilsWithEagerRelationshipsIsEnabled() throws Exception {
        when(termoContratoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoContratoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(termoContratoContabilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoContratoContabilsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(termoContratoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoContratoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(termoContratoContabilRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTermoContratoContabil() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get the termoContratoContabil
        restTermoContratoContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, termoContratoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termoContratoContabil.getId().intValue()))
            .andExpect(jsonPath("$.linkTermo").value(DEFAULT_LINK_TERMO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    void getNonExistingTermoContratoContabil() throws Exception {
        // Get the termoContratoContabil
        restTermoContratoContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTermoContratoContabil() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoContabil
        TermoContratoContabil updatedTermoContratoContabil = termoContratoContabilRepository
            .findById(termoContratoContabil.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTermoContratoContabil are not directly saved in db
        em.detach(updatedTermoContratoContabil);
        updatedTermoContratoContabil.linkTermo(UPDATED_LINK_TERMO).descricao(UPDATED_DESCRICAO).titulo(UPDATED_TITULO);

        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTermoContratoContabil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTermoContratoContabil))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTermoContratoContabilToMatchAllProperties(updatedTermoContratoContabil);
    }

    @Test
    @Transactional
    void putNonExistingTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termoContratoContabil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoContratoContabil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTermoContratoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoContabil using partial update
        TermoContratoContabil partialUpdatedTermoContratoContabil = new TermoContratoContabil();
        partialUpdatedTermoContratoContabil.setId(termoContratoContabil.getId());

        partialUpdatedTermoContratoContabil.titulo(UPDATED_TITULO);

        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoContratoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoContratoContabil))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoContratoContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTermoContratoContabil, termoContratoContabil),
            getPersistedTermoContratoContabil(termoContratoContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdateTermoContratoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoContabil using partial update
        TermoContratoContabil partialUpdatedTermoContratoContabil = new TermoContratoContabil();
        partialUpdatedTermoContratoContabil.setId(termoContratoContabil.getId());

        partialUpdatedTermoContratoContabil.linkTermo(UPDATED_LINK_TERMO).descricao(UPDATED_DESCRICAO).titulo(UPDATED_TITULO);

        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoContratoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoContratoContabil))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoContratoContabilUpdatableFieldsEquals(
            partialUpdatedTermoContratoContabil,
            getPersistedTermoContratoContabil(partialUpdatedTermoContratoContabil)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, termoContratoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(termoContratoContabil)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTermoContratoContabil() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the termoContratoContabil
        restTermoContratoContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, termoContratoContabil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return termoContratoContabilRepository.count();
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

    protected TermoContratoContabil getPersistedTermoContratoContabil(TermoContratoContabil termoContratoContabil) {
        return termoContratoContabilRepository.findById(termoContratoContabil.getId()).orElseThrow();
    }

    protected void assertPersistedTermoContratoContabilToMatchAllProperties(TermoContratoContabil expectedTermoContratoContabil) {
        assertTermoContratoContabilAllPropertiesEquals(
            expectedTermoContratoContabil,
            getPersistedTermoContratoContabil(expectedTermoContratoContabil)
        );
    }

    protected void assertPersistedTermoContratoContabilToMatchUpdatableProperties(TermoContratoContabil expectedTermoContratoContabil) {
        assertTermoContratoContabilAllUpdatablePropertiesEquals(
            expectedTermoContratoContabil,
            getPersistedTermoContratoContabil(expectedTermoContratoContabil)
        );
    }
}
