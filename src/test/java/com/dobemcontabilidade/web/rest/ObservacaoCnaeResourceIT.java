package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ObservacaoCnaeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ObservacaoCnae;
import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.repository.ObservacaoCnaeRepository;
import com.dobemcontabilidade.service.ObservacaoCnaeService;
import com.dobemcontabilidade.service.dto.ObservacaoCnaeDTO;
import com.dobemcontabilidade.service.mapper.ObservacaoCnaeMapper;
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
 * Integration tests for the {@link ObservacaoCnaeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ObservacaoCnaeResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/observacao-cnaes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ObservacaoCnaeRepository observacaoCnaeRepository;

    @Mock
    private ObservacaoCnaeRepository observacaoCnaeRepositoryMock;

    @Autowired
    private ObservacaoCnaeMapper observacaoCnaeMapper;

    @Mock
    private ObservacaoCnaeService observacaoCnaeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObservacaoCnaeMockMvc;

    private ObservacaoCnae observacaoCnae;

    private ObservacaoCnae insertedObservacaoCnae;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservacaoCnae createEntity(EntityManager em) {
        ObservacaoCnae observacaoCnae = new ObservacaoCnae().descricao(DEFAULT_DESCRICAO);
        // Add required entity
        SubclasseCnae subclasseCnae;
        if (TestUtil.findAll(em, SubclasseCnae.class).isEmpty()) {
            subclasseCnae = SubclasseCnaeResourceIT.createEntity(em);
            em.persist(subclasseCnae);
            em.flush();
        } else {
            subclasseCnae = TestUtil.findAll(em, SubclasseCnae.class).get(0);
        }
        observacaoCnae.setSubclasse(subclasseCnae);
        return observacaoCnae;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservacaoCnae createUpdatedEntity(EntityManager em) {
        ObservacaoCnae observacaoCnae = new ObservacaoCnae().descricao(UPDATED_DESCRICAO);
        // Add required entity
        SubclasseCnae subclasseCnae;
        if (TestUtil.findAll(em, SubclasseCnae.class).isEmpty()) {
            subclasseCnae = SubclasseCnaeResourceIT.createUpdatedEntity(em);
            em.persist(subclasseCnae);
            em.flush();
        } else {
            subclasseCnae = TestUtil.findAll(em, SubclasseCnae.class).get(0);
        }
        observacaoCnae.setSubclasse(subclasseCnae);
        return observacaoCnae;
    }

    @BeforeEach
    public void initTest() {
        observacaoCnae = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedObservacaoCnae != null) {
            observacaoCnaeRepository.delete(insertedObservacaoCnae);
            insertedObservacaoCnae = null;
        }
    }

    @Test
    @Transactional
    void createObservacaoCnae() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ObservacaoCnae
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(observacaoCnae);
        var returnedObservacaoCnaeDTO = om.readValue(
            restObservacaoCnaeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(observacaoCnaeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ObservacaoCnaeDTO.class
        );

        // Validate the ObservacaoCnae in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedObservacaoCnae = observacaoCnaeMapper.toEntity(returnedObservacaoCnaeDTO);
        assertObservacaoCnaeUpdatableFieldsEquals(returnedObservacaoCnae, getPersistedObservacaoCnae(returnedObservacaoCnae));

        insertedObservacaoCnae = returnedObservacaoCnae;
    }

    @Test
    @Transactional
    void createObservacaoCnaeWithExistingId() throws Exception {
        // Create the ObservacaoCnae with an existing ID
        observacaoCnae.setId(1L);
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(observacaoCnae);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservacaoCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(observacaoCnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllObservacaoCnaes() throws Exception {
        // Initialize the database
        insertedObservacaoCnae = observacaoCnaeRepository.saveAndFlush(observacaoCnae);

        // Get all the observacaoCnaeList
        restObservacaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observacaoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllObservacaoCnaesWithEagerRelationshipsIsEnabled() throws Exception {
        when(observacaoCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restObservacaoCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(observacaoCnaeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllObservacaoCnaesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(observacaoCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restObservacaoCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(observacaoCnaeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getObservacaoCnae() throws Exception {
        // Initialize the database
        insertedObservacaoCnae = observacaoCnaeRepository.saveAndFlush(observacaoCnae);

        // Get the observacaoCnae
        restObservacaoCnaeMockMvc
            .perform(get(ENTITY_API_URL_ID, observacaoCnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observacaoCnae.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getObservacaoCnaesByIdFiltering() throws Exception {
        // Initialize the database
        insertedObservacaoCnae = observacaoCnaeRepository.saveAndFlush(observacaoCnae);

        Long id = observacaoCnae.getId();

        defaultObservacaoCnaeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultObservacaoCnaeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultObservacaoCnaeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllObservacaoCnaesBySubclasseIsEqualToSomething() throws Exception {
        SubclasseCnae subclasse;
        if (TestUtil.findAll(em, SubclasseCnae.class).isEmpty()) {
            observacaoCnaeRepository.saveAndFlush(observacaoCnae);
            subclasse = SubclasseCnaeResourceIT.createEntity(em);
        } else {
            subclasse = TestUtil.findAll(em, SubclasseCnae.class).get(0);
        }
        em.persist(subclasse);
        em.flush();
        observacaoCnae.setSubclasse(subclasse);
        observacaoCnaeRepository.saveAndFlush(observacaoCnae);
        Long subclasseId = subclasse.getId();
        // Get all the observacaoCnaeList where subclasse equals to subclasseId
        defaultObservacaoCnaeShouldBeFound("subclasseId.equals=" + subclasseId);

        // Get all the observacaoCnaeList where subclasse equals to (subclasseId + 1)
        defaultObservacaoCnaeShouldNotBeFound("subclasseId.equals=" + (subclasseId + 1));
    }

    private void defaultObservacaoCnaeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultObservacaoCnaeShouldBeFound(shouldBeFound);
        defaultObservacaoCnaeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultObservacaoCnaeShouldBeFound(String filter) throws Exception {
        restObservacaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observacaoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restObservacaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultObservacaoCnaeShouldNotBeFound(String filter) throws Exception {
        restObservacaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restObservacaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingObservacaoCnae() throws Exception {
        // Get the observacaoCnae
        restObservacaoCnaeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingObservacaoCnae() throws Exception {
        // Initialize the database
        insertedObservacaoCnae = observacaoCnaeRepository.saveAndFlush(observacaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the observacaoCnae
        ObservacaoCnae updatedObservacaoCnae = observacaoCnaeRepository.findById(observacaoCnae.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedObservacaoCnae are not directly saved in db
        em.detach(updatedObservacaoCnae);
        updatedObservacaoCnae.descricao(UPDATED_DESCRICAO);
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(updatedObservacaoCnae);

        restObservacaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, observacaoCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(observacaoCnaeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ObservacaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedObservacaoCnaeToMatchAllProperties(updatedObservacaoCnae);
    }

    @Test
    @Transactional
    void putNonExistingObservacaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        observacaoCnae.setId(longCount.incrementAndGet());

        // Create the ObservacaoCnae
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(observacaoCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservacaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, observacaoCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(observacaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObservacaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        observacaoCnae.setId(longCount.incrementAndGet());

        // Create the ObservacaoCnae
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(observacaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservacaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(observacaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObservacaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        observacaoCnae.setId(longCount.incrementAndGet());

        // Create the ObservacaoCnae
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(observacaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservacaoCnaeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(observacaoCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObservacaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObservacaoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedObservacaoCnae = observacaoCnaeRepository.saveAndFlush(observacaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the observacaoCnae using partial update
        ObservacaoCnae partialUpdatedObservacaoCnae = new ObservacaoCnae();
        partialUpdatedObservacaoCnae.setId(observacaoCnae.getId());

        partialUpdatedObservacaoCnae.descricao(UPDATED_DESCRICAO);

        restObservacaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObservacaoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedObservacaoCnae))
            )
            .andExpect(status().isOk());

        // Validate the ObservacaoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertObservacaoCnaeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedObservacaoCnae, observacaoCnae),
            getPersistedObservacaoCnae(observacaoCnae)
        );
    }

    @Test
    @Transactional
    void fullUpdateObservacaoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedObservacaoCnae = observacaoCnaeRepository.saveAndFlush(observacaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the observacaoCnae using partial update
        ObservacaoCnae partialUpdatedObservacaoCnae = new ObservacaoCnae();
        partialUpdatedObservacaoCnae.setId(observacaoCnae.getId());

        partialUpdatedObservacaoCnae.descricao(UPDATED_DESCRICAO);

        restObservacaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObservacaoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedObservacaoCnae))
            )
            .andExpect(status().isOk());

        // Validate the ObservacaoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertObservacaoCnaeUpdatableFieldsEquals(partialUpdatedObservacaoCnae, getPersistedObservacaoCnae(partialUpdatedObservacaoCnae));
    }

    @Test
    @Transactional
    void patchNonExistingObservacaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        observacaoCnae.setId(longCount.incrementAndGet());

        // Create the ObservacaoCnae
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(observacaoCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservacaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, observacaoCnaeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(observacaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObservacaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        observacaoCnae.setId(longCount.incrementAndGet());

        // Create the ObservacaoCnae
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(observacaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservacaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(observacaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObservacaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObservacaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        observacaoCnae.setId(longCount.incrementAndGet());

        // Create the ObservacaoCnae
        ObservacaoCnaeDTO observacaoCnaeDTO = observacaoCnaeMapper.toDto(observacaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObservacaoCnaeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(observacaoCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObservacaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObservacaoCnae() throws Exception {
        // Initialize the database
        insertedObservacaoCnae = observacaoCnaeRepository.saveAndFlush(observacaoCnae);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the observacaoCnae
        restObservacaoCnaeMockMvc
            .perform(delete(ENTITY_API_URL_ID, observacaoCnae.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return observacaoCnaeRepository.count();
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

    protected ObservacaoCnae getPersistedObservacaoCnae(ObservacaoCnae observacaoCnae) {
        return observacaoCnaeRepository.findById(observacaoCnae.getId()).orElseThrow();
    }

    protected void assertPersistedObservacaoCnaeToMatchAllProperties(ObservacaoCnae expectedObservacaoCnae) {
        assertObservacaoCnaeAllPropertiesEquals(expectedObservacaoCnae, getPersistedObservacaoCnae(expectedObservacaoCnae));
    }

    protected void assertPersistedObservacaoCnaeToMatchUpdatableProperties(ObservacaoCnae expectedObservacaoCnae) {
        assertObservacaoCnaeAllUpdatablePropertiesEquals(expectedObservacaoCnae, getPersistedObservacaoCnae(expectedObservacaoCnae));
    }
}
