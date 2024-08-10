package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PerfilContadorAreaContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.domain.PerfilContadorAreaContabil;
import com.dobemcontabilidade.repository.PerfilContadorAreaContabilRepository;
import com.dobemcontabilidade.service.PerfilContadorAreaContabilService;
import com.dobemcontabilidade.service.dto.PerfilContadorAreaContabilDTO;
import com.dobemcontabilidade.service.mapper.PerfilContadorAreaContabilMapper;
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
 * Integration tests for the {@link PerfilContadorAreaContabilResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PerfilContadorAreaContabilResourceIT {

    private static final Integer DEFAULT_QUANTIDADE_EMPRESAS = 1;
    private static final Integer UPDATED_QUANTIDADE_EMPRESAS = 2;

    private static final Double DEFAULT_PERCENTUAL_EXPERIENCIA = 1D;
    private static final Double UPDATED_PERCENTUAL_EXPERIENCIA = 2D;

    private static final String ENTITY_API_URL = "/api/perfil-contador-area-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PerfilContadorAreaContabilRepository perfilContadorAreaContabilRepository;

    @Mock
    private PerfilContadorAreaContabilRepository perfilContadorAreaContabilRepositoryMock;

    @Autowired
    private PerfilContadorAreaContabilMapper perfilContadorAreaContabilMapper;

    @Mock
    private PerfilContadorAreaContabilService perfilContadorAreaContabilServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfilContadorAreaContabilMockMvc;

    private PerfilContadorAreaContabil perfilContadorAreaContabil;

    private PerfilContadorAreaContabil insertedPerfilContadorAreaContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilContadorAreaContabil createEntity(EntityManager em) {
        PerfilContadorAreaContabil perfilContadorAreaContabil = new PerfilContadorAreaContabil()
            .quantidadeEmpresas(DEFAULT_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(DEFAULT_PERCENTUAL_EXPERIENCIA);
        // Add required entity
        AreaContabil areaContabil;
        if (TestUtil.findAll(em, AreaContabil.class).isEmpty()) {
            areaContabil = AreaContabilResourceIT.createEntity(em);
            em.persist(areaContabil);
            em.flush();
        } else {
            areaContabil = TestUtil.findAll(em, AreaContabil.class).get(0);
        }
        perfilContadorAreaContabil.setAreaContabil(areaContabil);
        // Add required entity
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContador = PerfilContadorResourceIT.createEntity(em);
            em.persist(perfilContador);
            em.flush();
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        perfilContadorAreaContabil.setPerfilContador(perfilContador);
        return perfilContadorAreaContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilContadorAreaContabil createUpdatedEntity(EntityManager em) {
        PerfilContadorAreaContabil perfilContadorAreaContabil = new PerfilContadorAreaContabil()
            .quantidadeEmpresas(UPDATED_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA);
        // Add required entity
        AreaContabil areaContabil;
        if (TestUtil.findAll(em, AreaContabil.class).isEmpty()) {
            areaContabil = AreaContabilResourceIT.createUpdatedEntity(em);
            em.persist(areaContabil);
            em.flush();
        } else {
            areaContabil = TestUtil.findAll(em, AreaContabil.class).get(0);
        }
        perfilContadorAreaContabil.setAreaContabil(areaContabil);
        // Add required entity
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContador = PerfilContadorResourceIT.createUpdatedEntity(em);
            em.persist(perfilContador);
            em.flush();
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        perfilContadorAreaContabil.setPerfilContador(perfilContador);
        return perfilContadorAreaContabil;
    }

    @BeforeEach
    public void initTest() {
        perfilContadorAreaContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPerfilContadorAreaContabil != null) {
            perfilContadorAreaContabilRepository.delete(insertedPerfilContadorAreaContabil);
            insertedPerfilContadorAreaContabil = null;
        }
    }

    @Test
    @Transactional
    void createPerfilContadorAreaContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PerfilContadorAreaContabil
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);
        var returnedPerfilContadorAreaContabilDTO = om.readValue(
            restPerfilContadorAreaContabilMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PerfilContadorAreaContabilDTO.class
        );

        // Validate the PerfilContadorAreaContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPerfilContadorAreaContabil = perfilContadorAreaContabilMapper.toEntity(returnedPerfilContadorAreaContabilDTO);
        assertPerfilContadorAreaContabilUpdatableFieldsEquals(
            returnedPerfilContadorAreaContabil,
            getPersistedPerfilContadorAreaContabil(returnedPerfilContadorAreaContabil)
        );

        insertedPerfilContadorAreaContabil = returnedPerfilContadorAreaContabil;
    }

    @Test
    @Transactional
    void createPerfilContadorAreaContabilWithExistingId() throws Exception {
        // Create the PerfilContadorAreaContabil with an existing ID
        perfilContadorAreaContabil.setId(1L);
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilContadorAreaContabilMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorAreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerfilContadorAreaContabils() throws Exception {
        // Initialize the database
        insertedPerfilContadorAreaContabil = perfilContadorAreaContabilRepository.saveAndFlush(perfilContadorAreaContabil);

        // Get all the perfilContadorAreaContabilList
        restPerfilContadorAreaContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilContadorAreaContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeEmpresas").value(hasItem(DEFAULT_QUANTIDADE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].percentualExperiencia").value(hasItem(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPerfilContadorAreaContabilsWithEagerRelationshipsIsEnabled() throws Exception {
        when(perfilContadorAreaContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPerfilContadorAreaContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(perfilContadorAreaContabilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPerfilContadorAreaContabilsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(perfilContadorAreaContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPerfilContadorAreaContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(perfilContadorAreaContabilRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPerfilContadorAreaContabil() throws Exception {
        // Initialize the database
        insertedPerfilContadorAreaContabil = perfilContadorAreaContabilRepository.saveAndFlush(perfilContadorAreaContabil);

        // Get the perfilContadorAreaContabil
        restPerfilContadorAreaContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, perfilContadorAreaContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfilContadorAreaContabil.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeEmpresas").value(DEFAULT_QUANTIDADE_EMPRESAS))
            .andExpect(jsonPath("$.percentualExperiencia").value(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPerfilContadorAreaContabil() throws Exception {
        // Get the perfilContadorAreaContabil
        restPerfilContadorAreaContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerfilContadorAreaContabil() throws Exception {
        // Initialize the database
        insertedPerfilContadorAreaContabil = perfilContadorAreaContabilRepository.saveAndFlush(perfilContadorAreaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContadorAreaContabil
        PerfilContadorAreaContabil updatedPerfilContadorAreaContabil = perfilContadorAreaContabilRepository
            .findById(perfilContadorAreaContabil.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPerfilContadorAreaContabil are not directly saved in db
        em.detach(updatedPerfilContadorAreaContabil);
        updatedPerfilContadorAreaContabil
            .quantidadeEmpresas(UPDATED_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA);
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(
            updatedPerfilContadorAreaContabil
        );

        restPerfilContadorAreaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, perfilContadorAreaContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContadorAreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPerfilContadorAreaContabilToMatchAllProperties(updatedPerfilContadorAreaContabil);
    }

    @Test
    @Transactional
    void putNonExistingPerfilContadorAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorAreaContabil.setId(longCount.incrementAndGet());

        // Create the PerfilContadorAreaContabil
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilContadorAreaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, perfilContadorAreaContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorAreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerfilContadorAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorAreaContabil.setId(longCount.incrementAndGet());

        // Create the PerfilContadorAreaContabil
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorAreaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorAreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerfilContadorAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorAreaContabil.setId(longCount.incrementAndGet());

        // Create the PerfilContadorAreaContabil
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorAreaContabilMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilContadorAreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerfilContadorAreaContabilWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilContadorAreaContabil = perfilContadorAreaContabilRepository.saveAndFlush(perfilContadorAreaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContadorAreaContabil using partial update
        PerfilContadorAreaContabil partialUpdatedPerfilContadorAreaContabil = new PerfilContadorAreaContabil();
        partialUpdatedPerfilContadorAreaContabil.setId(perfilContadorAreaContabil.getId());

        partialUpdatedPerfilContadorAreaContabil
            .quantidadeEmpresas(UPDATED_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA);

        restPerfilContadorAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilContadorAreaContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilContadorAreaContabil))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContadorAreaContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilContadorAreaContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPerfilContadorAreaContabil, perfilContadorAreaContabil),
            getPersistedPerfilContadorAreaContabil(perfilContadorAreaContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdatePerfilContadorAreaContabilWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilContadorAreaContabil = perfilContadorAreaContabilRepository.saveAndFlush(perfilContadorAreaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContadorAreaContabil using partial update
        PerfilContadorAreaContabil partialUpdatedPerfilContadorAreaContabil = new PerfilContadorAreaContabil();
        partialUpdatedPerfilContadorAreaContabil.setId(perfilContadorAreaContabil.getId());

        partialUpdatedPerfilContadorAreaContabil
            .quantidadeEmpresas(UPDATED_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA);

        restPerfilContadorAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilContadorAreaContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilContadorAreaContabil))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContadorAreaContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilContadorAreaContabilUpdatableFieldsEquals(
            partialUpdatedPerfilContadorAreaContabil,
            getPersistedPerfilContadorAreaContabil(partialUpdatedPerfilContadorAreaContabil)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPerfilContadorAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorAreaContabil.setId(longCount.incrementAndGet());

        // Create the PerfilContadorAreaContabil
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilContadorAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, perfilContadorAreaContabilDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorAreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerfilContadorAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorAreaContabil.setId(longCount.incrementAndGet());

        // Create the PerfilContadorAreaContabil
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorAreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerfilContadorAreaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorAreaContabil.setId(longCount.incrementAndGet());

        // Create the PerfilContadorAreaContabil
        PerfilContadorAreaContabilDTO perfilContadorAreaContabilDTO = perfilContadorAreaContabilMapper.toDto(perfilContadorAreaContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorAreaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilContadorAreaContabilDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilContadorAreaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerfilContadorAreaContabil() throws Exception {
        // Initialize the database
        insertedPerfilContadorAreaContabil = perfilContadorAreaContabilRepository.saveAndFlush(perfilContadorAreaContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the perfilContadorAreaContabil
        restPerfilContadorAreaContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, perfilContadorAreaContabil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return perfilContadorAreaContabilRepository.count();
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

    protected PerfilContadorAreaContabil getPersistedPerfilContadorAreaContabil(PerfilContadorAreaContabil perfilContadorAreaContabil) {
        return perfilContadorAreaContabilRepository.findById(perfilContadorAreaContabil.getId()).orElseThrow();
    }

    protected void assertPersistedPerfilContadorAreaContabilToMatchAllProperties(
        PerfilContadorAreaContabil expectedPerfilContadorAreaContabil
    ) {
        assertPerfilContadorAreaContabilAllPropertiesEquals(
            expectedPerfilContadorAreaContabil,
            getPersistedPerfilContadorAreaContabil(expectedPerfilContadorAreaContabil)
        );
    }

    protected void assertPersistedPerfilContadorAreaContabilToMatchUpdatableProperties(
        PerfilContadorAreaContabil expectedPerfilContadorAreaContabil
    ) {
        assertPerfilContadorAreaContabilAllUpdatablePropertiesEquals(
            expectedPerfilContadorAreaContabil,
            getPersistedPerfilContadorAreaContabil(expectedPerfilContadorAreaContabil)
        );
    }
}
