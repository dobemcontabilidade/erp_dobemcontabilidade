package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AdicionalEnquadramentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AdicionalEnquadramento;
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.repository.AdicionalEnquadramentoRepository;
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
 * Integration tests for the {@link AdicionalEnquadramentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AdicionalEnquadramentoResourceIT {

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    private static final String ENTITY_API_URL = "/api/adicional-enquadramentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdicionalEnquadramentoRepository adicionalEnquadramentoRepository;

    @Mock
    private AdicionalEnquadramentoRepository adicionalEnquadramentoRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdicionalEnquadramentoMockMvc;

    private AdicionalEnquadramento adicionalEnquadramento;

    private AdicionalEnquadramento insertedAdicionalEnquadramento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdicionalEnquadramento createEntity(EntityManager em) {
        AdicionalEnquadramento adicionalEnquadramento = new AdicionalEnquadramento().valor(DEFAULT_VALOR);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        adicionalEnquadramento.setEnquadramento(enquadramento);
        // Add required entity
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createEntity(em);
            em.persist(planoAssinaturaContabil);
            em.flush();
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        adicionalEnquadramento.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return adicionalEnquadramento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdicionalEnquadramento createUpdatedEntity(EntityManager em) {
        AdicionalEnquadramento adicionalEnquadramento = new AdicionalEnquadramento().valor(UPDATED_VALOR);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createUpdatedEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        adicionalEnquadramento.setEnquadramento(enquadramento);
        // Add required entity
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoAssinaturaContabil);
            em.flush();
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        adicionalEnquadramento.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return adicionalEnquadramento;
    }

    @BeforeEach
    public void initTest() {
        adicionalEnquadramento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAdicionalEnquadramento != null) {
            adicionalEnquadramentoRepository.delete(insertedAdicionalEnquadramento);
            insertedAdicionalEnquadramento = null;
        }
    }

    @Test
    @Transactional
    void createAdicionalEnquadramento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AdicionalEnquadramento
        var returnedAdicionalEnquadramento = om.readValue(
            restAdicionalEnquadramentoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(adicionalEnquadramento))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdicionalEnquadramento.class
        );

        // Validate the AdicionalEnquadramento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdicionalEnquadramentoUpdatableFieldsEquals(
            returnedAdicionalEnquadramento,
            getPersistedAdicionalEnquadramento(returnedAdicionalEnquadramento)
        );

        insertedAdicionalEnquadramento = returnedAdicionalEnquadramento;
    }

    @Test
    @Transactional
    void createAdicionalEnquadramentoWithExistingId() throws Exception {
        // Create the AdicionalEnquadramento with an existing ID
        adicionalEnquadramento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdicionalEnquadramentoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalEnquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalEnquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdicionalEnquadramentos() throws Exception {
        // Initialize the database
        insertedAdicionalEnquadramento = adicionalEnquadramentoRepository.saveAndFlush(adicionalEnquadramento);

        // Get all the adicionalEnquadramentoList
        restAdicionalEnquadramentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adicionalEnquadramento.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdicionalEnquadramentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(adicionalEnquadramentoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdicionalEnquadramentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(adicionalEnquadramentoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdicionalEnquadramentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(adicionalEnquadramentoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdicionalEnquadramentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(adicionalEnquadramentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAdicionalEnquadramento() throws Exception {
        // Initialize the database
        insertedAdicionalEnquadramento = adicionalEnquadramentoRepository.saveAndFlush(adicionalEnquadramento);

        // Get the adicionalEnquadramento
        restAdicionalEnquadramentoMockMvc
            .perform(get(ENTITY_API_URL_ID, adicionalEnquadramento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adicionalEnquadramento.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingAdicionalEnquadramento() throws Exception {
        // Get the adicionalEnquadramento
        restAdicionalEnquadramentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdicionalEnquadramento() throws Exception {
        // Initialize the database
        insertedAdicionalEnquadramento = adicionalEnquadramentoRepository.saveAndFlush(adicionalEnquadramento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalEnquadramento
        AdicionalEnquadramento updatedAdicionalEnquadramento = adicionalEnquadramentoRepository
            .findById(adicionalEnquadramento.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAdicionalEnquadramento are not directly saved in db
        em.detach(updatedAdicionalEnquadramento);
        updatedAdicionalEnquadramento.valor(UPDATED_VALOR);

        restAdicionalEnquadramentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdicionalEnquadramento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdicionalEnquadramento))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalEnquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdicionalEnquadramentoToMatchAllProperties(updatedAdicionalEnquadramento);
    }

    @Test
    @Transactional
    void putNonExistingAdicionalEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalEnquadramento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdicionalEnquadramentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adicionalEnquadramento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalEnquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalEnquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdicionalEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalEnquadramento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalEnquadramentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalEnquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalEnquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdicionalEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalEnquadramento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalEnquadramentoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalEnquadramento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdicionalEnquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdicionalEnquadramentoWithPatch() throws Exception {
        // Initialize the database
        insertedAdicionalEnquadramento = adicionalEnquadramentoRepository.saveAndFlush(adicionalEnquadramento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalEnquadramento using partial update
        AdicionalEnquadramento partialUpdatedAdicionalEnquadramento = new AdicionalEnquadramento();
        partialUpdatedAdicionalEnquadramento.setId(adicionalEnquadramento.getId());

        partialUpdatedAdicionalEnquadramento.valor(UPDATED_VALOR);

        restAdicionalEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdicionalEnquadramento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdicionalEnquadramento))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalEnquadramento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdicionalEnquadramentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdicionalEnquadramento, adicionalEnquadramento),
            getPersistedAdicionalEnquadramento(adicionalEnquadramento)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdicionalEnquadramentoWithPatch() throws Exception {
        // Initialize the database
        insertedAdicionalEnquadramento = adicionalEnquadramentoRepository.saveAndFlush(adicionalEnquadramento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalEnquadramento using partial update
        AdicionalEnquadramento partialUpdatedAdicionalEnquadramento = new AdicionalEnquadramento();
        partialUpdatedAdicionalEnquadramento.setId(adicionalEnquadramento.getId());

        partialUpdatedAdicionalEnquadramento.valor(UPDATED_VALOR);

        restAdicionalEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdicionalEnquadramento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdicionalEnquadramento))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalEnquadramento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdicionalEnquadramentoUpdatableFieldsEquals(
            partialUpdatedAdicionalEnquadramento,
            getPersistedAdicionalEnquadramento(partialUpdatedAdicionalEnquadramento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAdicionalEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalEnquadramento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdicionalEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adicionalEnquadramento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalEnquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalEnquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdicionalEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalEnquadramento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalEnquadramento))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalEnquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdicionalEnquadramento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalEnquadramento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalEnquadramentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalEnquadramento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdicionalEnquadramento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdicionalEnquadramento() throws Exception {
        // Initialize the database
        insertedAdicionalEnquadramento = adicionalEnquadramentoRepository.saveAndFlush(adicionalEnquadramento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adicionalEnquadramento
        restAdicionalEnquadramentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, adicionalEnquadramento.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adicionalEnquadramentoRepository.count();
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

    protected AdicionalEnquadramento getPersistedAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        return adicionalEnquadramentoRepository.findById(adicionalEnquadramento.getId()).orElseThrow();
    }

    protected void assertPersistedAdicionalEnquadramentoToMatchAllProperties(AdicionalEnquadramento expectedAdicionalEnquadramento) {
        assertAdicionalEnquadramentoAllPropertiesEquals(
            expectedAdicionalEnquadramento,
            getPersistedAdicionalEnquadramento(expectedAdicionalEnquadramento)
        );
    }

    protected void assertPersistedAdicionalEnquadramentoToMatchUpdatableProperties(AdicionalEnquadramento expectedAdicionalEnquadramento) {
        assertAdicionalEnquadramentoAllUpdatablePropertiesEquals(
            expectedAdicionalEnquadramento,
            getPersistedAdicionalEnquadramento(expectedAdicionalEnquadramento)
        );
    }
}
