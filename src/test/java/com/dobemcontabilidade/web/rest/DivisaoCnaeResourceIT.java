package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DivisaoCnaeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DivisaoCnae;
import com.dobemcontabilidade.domain.SecaoCnae;
import com.dobemcontabilidade.repository.DivisaoCnaeRepository;
import com.dobemcontabilidade.service.DivisaoCnaeService;
import com.dobemcontabilidade.service.dto.DivisaoCnaeDTO;
import com.dobemcontabilidade.service.mapper.DivisaoCnaeMapper;
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
 * Integration tests for the {@link DivisaoCnaeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DivisaoCnaeResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/divisao-cnaes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DivisaoCnaeRepository divisaoCnaeRepository;

    @Mock
    private DivisaoCnaeRepository divisaoCnaeRepositoryMock;

    @Autowired
    private DivisaoCnaeMapper divisaoCnaeMapper;

    @Mock
    private DivisaoCnaeService divisaoCnaeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDivisaoCnaeMockMvc;

    private DivisaoCnae divisaoCnae;

    private DivisaoCnae insertedDivisaoCnae;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DivisaoCnae createEntity(EntityManager em) {
        DivisaoCnae divisaoCnae = new DivisaoCnae().codigo(DEFAULT_CODIGO).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        SecaoCnae secaoCnae;
        if (TestUtil.findAll(em, SecaoCnae.class).isEmpty()) {
            secaoCnae = SecaoCnaeResourceIT.createEntity(em);
            em.persist(secaoCnae);
            em.flush();
        } else {
            secaoCnae = TestUtil.findAll(em, SecaoCnae.class).get(0);
        }
        divisaoCnae.setSecao(secaoCnae);
        return divisaoCnae;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DivisaoCnae createUpdatedEntity(EntityManager em) {
        DivisaoCnae divisaoCnae = new DivisaoCnae().codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        // Add required entity
        SecaoCnae secaoCnae;
        if (TestUtil.findAll(em, SecaoCnae.class).isEmpty()) {
            secaoCnae = SecaoCnaeResourceIT.createUpdatedEntity(em);
            em.persist(secaoCnae);
            em.flush();
        } else {
            secaoCnae = TestUtil.findAll(em, SecaoCnae.class).get(0);
        }
        divisaoCnae.setSecao(secaoCnae);
        return divisaoCnae;
    }

    @BeforeEach
    public void initTest() {
        divisaoCnae = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDivisaoCnae != null) {
            divisaoCnaeRepository.delete(insertedDivisaoCnae);
            insertedDivisaoCnae = null;
        }
    }

    @Test
    @Transactional
    void createDivisaoCnae() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DivisaoCnae
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(divisaoCnae);
        var returnedDivisaoCnaeDTO = om.readValue(
            restDivisaoCnaeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisaoCnaeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DivisaoCnaeDTO.class
        );

        // Validate the DivisaoCnae in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDivisaoCnae = divisaoCnaeMapper.toEntity(returnedDivisaoCnaeDTO);
        assertDivisaoCnaeUpdatableFieldsEquals(returnedDivisaoCnae, getPersistedDivisaoCnae(returnedDivisaoCnae));

        insertedDivisaoCnae = returnedDivisaoCnae;
    }

    @Test
    @Transactional
    void createDivisaoCnaeWithExistingId() throws Exception {
        // Create the DivisaoCnae with an existing ID
        divisaoCnae.setId(1L);
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(divisaoCnae);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisaoCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisaoCnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DivisaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDivisaoCnaes() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        // Get all the divisaoCnaeList
        restDivisaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisaoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDivisaoCnaesWithEagerRelationshipsIsEnabled() throws Exception {
        when(divisaoCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDivisaoCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(divisaoCnaeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDivisaoCnaesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(divisaoCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDivisaoCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(divisaoCnaeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDivisaoCnae() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        // Get the divisaoCnae
        restDivisaoCnaeMockMvc
            .perform(get(ENTITY_API_URL_ID, divisaoCnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(divisaoCnae.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getDivisaoCnaesByIdFiltering() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        Long id = divisaoCnae.getId();

        defaultDivisaoCnaeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDivisaoCnaeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDivisaoCnaeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDivisaoCnaesByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        // Get all the divisaoCnaeList where codigo equals to
        defaultDivisaoCnaeFiltering("codigo.equals=" + DEFAULT_CODIGO, "codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllDivisaoCnaesByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        // Get all the divisaoCnaeList where codigo in
        defaultDivisaoCnaeFiltering("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO, "codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllDivisaoCnaesByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        // Get all the divisaoCnaeList where codigo is not null
        defaultDivisaoCnaeFiltering("codigo.specified=true", "codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllDivisaoCnaesByCodigoContainsSomething() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        // Get all the divisaoCnaeList where codigo contains
        defaultDivisaoCnaeFiltering("codigo.contains=" + DEFAULT_CODIGO, "codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllDivisaoCnaesByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        // Get all the divisaoCnaeList where codigo does not contain
        defaultDivisaoCnaeFiltering("codigo.doesNotContain=" + UPDATED_CODIGO, "codigo.doesNotContain=" + DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    void getAllDivisaoCnaesBySecaoIsEqualToSomething() throws Exception {
        SecaoCnae secao;
        if (TestUtil.findAll(em, SecaoCnae.class).isEmpty()) {
            divisaoCnaeRepository.saveAndFlush(divisaoCnae);
            secao = SecaoCnaeResourceIT.createEntity(em);
        } else {
            secao = TestUtil.findAll(em, SecaoCnae.class).get(0);
        }
        em.persist(secao);
        em.flush();
        divisaoCnae.setSecao(secao);
        divisaoCnaeRepository.saveAndFlush(divisaoCnae);
        Long secaoId = secao.getId();
        // Get all the divisaoCnaeList where secao equals to secaoId
        defaultDivisaoCnaeShouldBeFound("secaoId.equals=" + secaoId);

        // Get all the divisaoCnaeList where secao equals to (secaoId + 1)
        defaultDivisaoCnaeShouldNotBeFound("secaoId.equals=" + (secaoId + 1));
    }

    private void defaultDivisaoCnaeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDivisaoCnaeShouldBeFound(shouldBeFound);
        defaultDivisaoCnaeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDivisaoCnaeShouldBeFound(String filter) throws Exception {
        restDivisaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisaoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restDivisaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDivisaoCnaeShouldNotBeFound(String filter) throws Exception {
        restDivisaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDivisaoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDivisaoCnae() throws Exception {
        // Get the divisaoCnae
        restDivisaoCnaeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDivisaoCnae() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the divisaoCnae
        DivisaoCnae updatedDivisaoCnae = divisaoCnaeRepository.findById(divisaoCnae.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDivisaoCnae are not directly saved in db
        em.detach(updatedDivisaoCnae);
        updatedDivisaoCnae.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(updatedDivisaoCnae);

        restDivisaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisaoCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(divisaoCnaeDTO))
            )
            .andExpect(status().isOk());

        // Validate the DivisaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDivisaoCnaeToMatchAllProperties(updatedDivisaoCnae);
    }

    @Test
    @Transactional
    void putNonExistingDivisaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        divisaoCnae.setId(longCount.incrementAndGet());

        // Create the DivisaoCnae
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(divisaoCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, divisaoCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(divisaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DivisaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDivisaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        divisaoCnae.setId(longCount.incrementAndGet());

        // Create the DivisaoCnae
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(divisaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(divisaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DivisaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDivisaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        divisaoCnae.setId(longCount.incrementAndGet());

        // Create the DivisaoCnae
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(divisaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaoCnaeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(divisaoCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DivisaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDivisaoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the divisaoCnae using partial update
        DivisaoCnae partialUpdatedDivisaoCnae = new DivisaoCnae();
        partialUpdatedDivisaoCnae.setId(divisaoCnae.getId());

        restDivisaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivisaoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDivisaoCnae))
            )
            .andExpect(status().isOk());

        // Validate the DivisaoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDivisaoCnaeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDivisaoCnae, divisaoCnae),
            getPersistedDivisaoCnae(divisaoCnae)
        );
    }

    @Test
    @Transactional
    void fullUpdateDivisaoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the divisaoCnae using partial update
        DivisaoCnae partialUpdatedDivisaoCnae = new DivisaoCnae();
        partialUpdatedDivisaoCnae.setId(divisaoCnae.getId());

        partialUpdatedDivisaoCnae.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restDivisaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDivisaoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDivisaoCnae))
            )
            .andExpect(status().isOk());

        // Validate the DivisaoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDivisaoCnaeUpdatableFieldsEquals(partialUpdatedDivisaoCnae, getPersistedDivisaoCnae(partialUpdatedDivisaoCnae));
    }

    @Test
    @Transactional
    void patchNonExistingDivisaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        divisaoCnae.setId(longCount.incrementAndGet());

        // Create the DivisaoCnae
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(divisaoCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDivisaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, divisaoCnaeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(divisaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DivisaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDivisaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        divisaoCnae.setId(longCount.incrementAndGet());

        // Create the DivisaoCnae
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(divisaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(divisaoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DivisaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDivisaoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        divisaoCnae.setId(longCount.incrementAndGet());

        // Create the DivisaoCnae
        DivisaoCnaeDTO divisaoCnaeDTO = divisaoCnaeMapper.toDto(divisaoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDivisaoCnaeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(divisaoCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DivisaoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDivisaoCnae() throws Exception {
        // Initialize the database
        insertedDivisaoCnae = divisaoCnaeRepository.saveAndFlush(divisaoCnae);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the divisaoCnae
        restDivisaoCnaeMockMvc
            .perform(delete(ENTITY_API_URL_ID, divisaoCnae.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return divisaoCnaeRepository.count();
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

    protected DivisaoCnae getPersistedDivisaoCnae(DivisaoCnae divisaoCnae) {
        return divisaoCnaeRepository.findById(divisaoCnae.getId()).orElseThrow();
    }

    protected void assertPersistedDivisaoCnaeToMatchAllProperties(DivisaoCnae expectedDivisaoCnae) {
        assertDivisaoCnaeAllPropertiesEquals(expectedDivisaoCnae, getPersistedDivisaoCnae(expectedDivisaoCnae));
    }

    protected void assertPersistedDivisaoCnaeToMatchUpdatableProperties(DivisaoCnae expectedDivisaoCnae) {
        assertDivisaoCnaeAllUpdatablePropertiesEquals(expectedDivisaoCnae, getPersistedDivisaoCnae(expectedDivisaoCnae));
    }
}
